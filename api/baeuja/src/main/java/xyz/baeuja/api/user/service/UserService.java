package xyz.baeuja.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.auth.dto.SignUpRequest;
import xyz.baeuja.api.auth.exception.*;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.Role;
import xyz.baeuja.api.user.domain.User;
import xyz.baeuja.api.user.dto.UserDto;
import xyz.baeuja.api.user.repository.jpa.UserJpaRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private static final String NICKNAME_MATCH_REGEX = "^[a-zA-Z0-9가-힣]+$";

    /**
     * 로그인 시 Access Token 생성을 위한 User 데이터 조회
     *
     * @param email String
     * @return JwtUserInfo
     * @throws UserNotFoundException 토큰에서 조회한 userId 를 가진 사용자가 DB에 존재하지 않는다면
     */
    public JwtUserInfo loadUserForSignIn(String email) throws UserNotFoundException {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return new JwtUserInfo(user.getId(), user.getTimezone(), user.getRole());
    }

    /**
     * 회원 가입
     *
     * @param request UserSignUpRequest must include (nickname, language, timezone, loginType)
     * @return JwtUserInfo
     */
    @Transactional
    public JwtUserInfo signUp(SignUpRequest request) throws InvalidSignUpRequestException {
        if (request.getLoginType() == LoginType.GUEST && request.getEmail() != null
                || request.getLoginType() == LoginType.GOOGLE && request.getEmail() == null) {
            throw new InvalidSignUpRequestException("Email is required when signing up for Google.");
        }

        User user;
        Role role;

        if (request.getLoginType() == LoginType.GUEST) {
            user = request.toEntity(createGuestNickname());
            role = Role.GUEST;
        } else {
            user = request.toEntity();
            validateDuplicateEmail(request.getEmail());
            user.convertToGoogleAccount(request.getEmail());
            role = Role.MEMBER;
        }

        Long savedId = userJpaRepository.save(user);

        return new JwtUserInfo(savedId, request.getTimezone(), role);
    }

    /**
     * 사용자 정보 조회
     *
     * @param userId 조회하고자 하는 user id
     * @return 조회한 User 로부터 생성한 UserDto
     */
    public UserDto getUserInfo(Long userId) {
        User findUser = userJpaRepository.findOne(userId);
        return UserDto.from(findUser);
    }

    /**
     * 랜덤한 닉네임 생성
     *
     * @return random nickname string
     */
    private static String createGuestNickname() {
        return "G" + UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 19);
    }

    /**
     * 닉네임 유효성 검사
     */
    public void validateNickname(String nickname) throws InvalidNicknameException, DuplicateNicknameException {
        if (nickname.length() < 2 || nickname.length() > 20) {
            throw new InvalidNicknameException("Please enter a nickname of at least 2 and no more than 20 characters.");
        }

        if (!nickname.matches(NICKNAME_MATCH_REGEX)) {
            throw new InvalidNicknameException("Nicknames can only use Korean, English, and numbers.");
        }

        if (userJpaRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }

    /**
     * 이메일 중복 검사
     */
    private void validateDuplicateEmail(String email) throws DuplicateEmailException {
        if (userJpaRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
