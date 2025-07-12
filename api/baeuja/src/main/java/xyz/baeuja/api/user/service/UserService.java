package xyz.baeuja.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import xyz.baeuja.api.auth.dto.SignUpRequest;
import xyz.baeuja.api.auth.exception.*;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.Role;
import xyz.baeuja.api.user.domain.User;
import xyz.baeuja.api.user.repository.UserRepository;

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
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
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
        User user = request.toEntity();
        Role role = Role.GUEST;

        if (request.getLoginType() == LoginType.GOOGLE) {
            if (request.getEmail() == null) {
                throw new InvalidSignUpRequestException("구글 회원가입 시 이메일은 필수입니다.");
            }

            validateDuplicateEmail(request.getEmail());
            user.convertToGoogleAccount(request.getEmail());
            role = Role.MEMBER;
        }

        Long savedId = userRepository.save(user);

        return new JwtUserInfo(savedId, request.getTimezone(), role);
    }

    /**
     * 닉네임 유효성 검사
     */
    public void validateNickname(String nickname) throws InvalidNicknameException, DuplicateNicknameException {
        if (nickname.length() < 2 || nickname.length() > 20) {
            throw new InvalidNicknameException("닉네임은 2자 이상 20자 이하로 입력해주세요.");
        }

        if (!nickname.matches(NICKNAME_MATCH_REGEX)) {
            throw new InvalidNicknameException("닉네임은 한글, 영문, 숫자만 사용할 수 있습니다.");
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
