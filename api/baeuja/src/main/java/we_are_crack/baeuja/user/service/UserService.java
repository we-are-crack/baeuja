package we_are_crack.baeuja.user.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import we_are_crack.baeuja.global.exception.DuplicateEmailException;
import we_are_crack.baeuja.global.exception.DuplicateNicknameException;
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
            // 증복 검사
            try {
                userRepository.findByEmail(request.getEmail());
            } catch (NoResultException exception) {
                throw new DuplicateEmailException();
            }

            user.convertToGoogleAccount(request.getEmail());
        }

        return userRepository.save(user);
    }
}
