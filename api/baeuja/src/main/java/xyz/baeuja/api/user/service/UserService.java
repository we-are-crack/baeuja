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
import xyz.baeuja.api.user.repository.UserRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private static final String NICKNAME_MATCH_REGEX = "^[a-zA-Z0-9가-힣]+$";

    /**
     * 로그인 시 Access Token 생성을 위한 User 데이터 조회
     *
     * @param email String
     * @return JwtUserInfo
     * @throws UserNotFoundException
     */
    public JwtUserInfo loadUserForSignIn(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
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
            user = request.toEntity(getGuestNickname());
            role = Role.GUEST;
        } else {
            user = request.toEntity();
            validateDuplicateEmail(request.getEmail());
            user.convertToGoogleAccount(request.getEmail());
            role = Role.MEMBER;
        }

        Long savedId = userRepository.save(user);

        return new JwtUserInfo(savedId, request.getTimezone(), role);
    }

    /**
     * 랜덤한 닉네임 생성
     *
     * @return random nickname string
     */
    private static String getGuestNickname() {
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

        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }

    /**
     * 이메일 중복 검사
     */
    private void validateDuplicateEmail(String email) throws DuplicateEmailException {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
