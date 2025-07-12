package xyz.baeuja.api.global.util.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.baeuja.api.global.exception.jwt.ExpiredAccessTokenException;
import xyz.baeuja.api.global.exception.jwt.InvalidJwtException;
import xyz.baeuja.api.user.domain.Role;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private JwtProvider jwtProvider;
    private JwtUserInfo testUser;

    @BeforeEach
    void setUp() {
        String SECRET = "2f2fbab983a4d177f0e5dd6f26efee185c64aa7542fbcac45112ebe481d29a44";
        long EXP = 60L;

        jwtProvider = new JwtProvider(SECRET, EXP);
        testUser = new JwtUserInfo(1L, "Asia/Seoul", Role.GUEST);
    }

    @Test
    @DisplayName("access token 생성 성공")
    void createAccessToken_success() {
        // when
        String token = jwtProvider.createAccessToken(testUser);

        // then
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("토큰에서 user id 조회 성공")
    void getUserId_success() {
        // given
        String token = jwtProvider.createAccessToken(testUser);

        // when
        Long userId = jwtProvider.getUserId(token);

        // then
        assertThat(userId).isEqualTo(testUser.getId());
    }

    @Test
    @DisplayName("토큰에서 role 조회 성공")
    void getRole_success() {
        // given
        String token = jwtProvider.createAccessToken(testUser);

        // when
        Role role = jwtProvider.getRole(token);

        // then
        assertThat(role).isEqualTo(testUser.getRole());
    }

    @Test
    @DisplayName("토큰 검증 성공")
    void validate_success() {
        // given
        String token = jwtProvider.createAccessToken(testUser);

        // when & then
        jwtProvider.validate(token);
    }

    @Test
    @DisplayName("토큰 검증 실패 - 만료된 토큰")
    void validate_fail_ExpiredAccessTokenException() {
        // given
        long expiredExp = -1L; // 이미 만료된 토큰
        String expiredToken = jwtProvider.createAccessToken(testUser, expiredExp);

        // when & then
        assertThrows(ExpiredAccessTokenException.class,
                () -> jwtProvider.validate(expiredToken)
        );
    }

    @Test
    @DisplayName("토큰 검증 실패 - 유효하지 않은 토큰")
    void validate_fail_InvalidJwtException() {
        // given
        String invalidToken = "invalid.token.value";

        // when & then
        assertThrows(InvalidJwtException.class,
                () -> jwtProvider.validate(invalidToken)
        );
    }
}