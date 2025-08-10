package xyz.baeuja.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.auth.dto.AuthDto;
import xyz.baeuja.api.global.util.jwt.JwtProvider;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.user.domain.Role;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;

    /**
     * Access Token 발행
     *
     * @param userInfo JwtUserInfo must include (id, timezone, role)
     * @return access token string
     */
    public String issueAccessToken(JwtUserInfo userInfo) {
        String accessToken;

        if (userInfo.getRole() == Role.GUEST) {
            accessToken = jwtProvider.createAccessToken(userInfo, null);
        } else {
            accessToken = jwtProvider.createAccessToken(userInfo);
        }

        return accessToken;
    }

    /**
     * Refresh Token 발행
     *
     * @param userInfo JwtUserInfo must include (id, timezone, role)
     * @return refresh token string
     */
    public String issueRefreshToken(JwtUserInfo userInfo) {
        String refreshToken;

        if (userInfo.getRole() == Role.GUEST) {
            refreshToken = jwtProvider.createRefreshToken(userInfo, null);
        } else {
            refreshToken = jwtProvider.createRefreshToken(userInfo);
        }

        return refreshToken;
    }

    /**
     * access token & refresh token 재발급
     *
     * @param request AuthDto (access token & refresh token)
     * @return AuthDto (access token & refresh token)
     */
    public AuthDto renewAccessTokenAndRefreshToken(AuthDto request) {
        String accessToken = request.getAccessToken();
        String refreshToken = request.getRefreshToken();

        jwtProvider.validateRefreshToken(accessToken, refreshToken);

        JwtUserInfo jwtUserInfo = new JwtUserInfo(jwtProvider.getUserId(refreshToken),
                jwtProvider.getUserTimezone(refreshToken),
                jwtProvider.getRole(refreshToken)
        );

        String renewAccessToken = jwtProvider.createAccessToken(jwtUserInfo);
        String renewRefreshToken = jwtProvider.createRefreshToken(jwtUserInfo);

        return new AuthDto(renewAccessToken, renewRefreshToken);
    }
}
