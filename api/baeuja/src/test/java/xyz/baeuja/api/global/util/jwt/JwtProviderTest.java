package xyz.baeuja.api.global.util.jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.baeuja.api.auth.security.exception.ExpiredTokenException;
import xyz.baeuja.api.auth.security.exception.InvalidJwtException;
import xyz.baeuja.api.user.domain.Role;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private JwtProvider jwtProvider;
    private JwtUserInfo testUser;
    private JwtUserInfo testUser2;

    @BeforeEach
    void setUp() {
        String SECRET = "2f2fbab983a4d177f0e5dd6f26efee185c64aa7542fbcac45112ebe481d29a44";
        long ACCESS_TOKEN_EXP = 60L;
        long REFRESH_TOKEN_EXP = 600L;

        jwtProvider = new JwtProvider(SECRET, ACCESS_TOKEN_EXP, REFRESH_TOKEN_EXP);
        testUser = new JwtUserInfo(1L, "Asia/Seoul", Role.GUEST);
        testUser2 = new JwtUserInfo(2L, "Asia/Seoul", Role.GUEST);
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
    @DisplayName("무제한 access token 생성 성공")
    void createInfinityAccessToken_success() {
        // when
        String token = jwtProvider.createAccessToken(testUser, null);

        // then
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("refresh token 생성 성공")
    void createRefreshToken_success() {
        // when
        String token = jwtProvider.createRefreshToken(testUser);

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

        // when
        Claims claims = jwtProvider.parseAndValidate(token);

        // then
        assertThat(claims.get("userId", Long.class)).isEqualTo(testUser.getId());
    }

    @Test
    @DisplayName("refresh token 검증 성공")
    void validateRefreshToken_fail() {
        // given
        String accessToken = jwtProvider.createAccessToken(testUser, -1L);
        String refreshToken = jwtProvider.createRefreshToken(testUser);

        // when & then
        jwtProvider.validateRefreshToken(accessToken, refreshToken);
    }

    @Test
    @DisplayName("refresh token 검증 실패")
    void validateRefreshToken_success() {
        // given
        String accessToken = jwtProvider.createAccessToken(testUser, -1L);
        String refreshToken = jwtProvider.createRefreshToken(testUser2);

        // when & then
        assertThrows(InvalidJwtException.class,
                () -> jwtProvider.validateRefreshToken(accessToken, refreshToken));
    }

    @Test
    @DisplayName("토큰 검증 실패 - 만료된 토큰")
    void validate_fail_ExpiredAccessTokenException() {
        // given
        Long expiredExp = -1l; // 이미 만료된 토큰
        String expiredToken = jwtProvider.createAccessToken(testUser, expiredExp);

        // when & then
        assertThrows(ExpiredTokenException.class,
                () -> jwtProvider.parseAndValidate(expiredToken)
        );
    }

    @Test
    @DisplayName("토큰 검증 실패 - 유효하지 않은 토큰")
    void validate_fail_InvalidJwtException() {
        // given
        String invalidToken = "invalid.token.value";

        // when & then
        assertThrows(InvalidJwtException.class,
                () -> jwtProvider.parseAndValidate(invalidToken)
        );
    }
}