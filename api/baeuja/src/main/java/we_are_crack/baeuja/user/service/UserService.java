package we_are_crack.baeuja.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we_are_crack.baeuja.global.exception.DuplicateEmailException;
import we_are_crack.baeuja.global.exception.DuplicateNicknameException;
import we_are_crack.baeuja.global.exception.InvalidNicknameException;
import we_are_crack.baeuja.user.domain.User;
import we_are_crack.baeuja.user.dto.UserRequest;
import we_are_crack.baeuja.user.repository.UserRepository;

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

        if (request.getEmail() != null) {
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
