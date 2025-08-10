package xyz.baeuja.api.user.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.Role;
import xyz.baeuja.api.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserJpaRepositoryTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("새로운 사용자 회원가입(저장) 성공")
    void saveTest() {
        // given
        String nickname = "test1";
        User guest = getGuest(nickname);

        // when
        Long savedId = userJpaRepository.save(guest);

        // then
        assertThat(guest.getId()).isEqualTo(savedId);
        assertThat(guest.getRole()).isEqualTo(Role.GUEST);
    }

    @Test
    @DisplayName("사용자 ID로 조회 성공")
    void findOneTest() {
        // given
        String nickname = "test1";
        User guest = getGuest(nickname);
        Long savedId = userJpaRepository.save(guest);

        // when
        User findUser = userJpaRepository.findOne(savedId);

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
            userJpaRepository.save(invalidUser);
        });
    }


    private static User getGuest(String nickname) {
        return new User(nickname, "ko", "Asia/Seoul", LoginType.GUEST);
    }
}