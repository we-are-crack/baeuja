package xyz.baeuja.api.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("새로운 사용자 회원가입(저장) 성공")
    void saveTest() {
        // given
        String nickname = "test1";
        User guest = getGuest(nickname);

        // when
        Long savedId = userRepository.save(guest);

        // then
        assertThat(guest.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("사용자 ID로 조회 성공")
    void findOneTest() {
        // given
        String nickname = "test1";
        User guest = getGuest(nickname);
        Long savedId = userRepository.save(guest);

        // when
        User findUser = userRepository.findOne(savedId);

        // then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getId()).isEqualTo(savedId);
        assertThat(findUser.getNickname()).isEqualTo("test1");
    }

    @Test
    @DisplayName("닉네임이 null일 때 예외 발생")
    void nicknameNullTest() {
        // given
        User invalidUser = getGuest(null);

        // when & then
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(invalidUser);
        });
    }


    private static User getGuest(String nickname) {
        return new User(nickname, "ko", "Asia/Seoul", LoginType.GUEST);
    }
}