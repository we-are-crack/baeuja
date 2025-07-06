package xyz.baeuja.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.exception.DuplicateEmailException;
import xyz.baeuja.api.user.exception.DuplicateNicknameException;
import xyz.baeuja.api.user.exception.InvalidNicknameException;
import xyz.baeuja.api.user.domain.User;
import xyz.baeuja.api.user.dto.UserRequest;
import xyz.baeuja.api.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long singUp(UserRequest request) {
        User user = request.toEntity();

        if (request.getLoginType() == LoginType.GOOGLE && request.getEmail() != null) {
            validateDuplicateEmail(request.getEmail());
            user.convertToGoogleAccount(request.getEmail());
        }

        return userRepository.save(user);
    }

    /**
     * 닉네임 유효성 검사
     */
    public void validateNickname(String nickname) {
        if (nickname.length() < 2 || nickname.length() > 20) {
            throw new InvalidNicknameException("닉네임은 2자 이상 20자 이하로 입력해주세요.");
        }

        if (!nickname.matches("^[a-zA-Z0-9가-힣]+$")) {
            throw new InvalidNicknameException("닉네임은 한글, 영문, 숫자만 사용할 수 있습니다.");
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
    }
        }

    /**
     * 이메일 중복 검사
     */
    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
