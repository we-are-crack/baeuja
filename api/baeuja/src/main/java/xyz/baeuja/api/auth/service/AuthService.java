package xyz.baeuja.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.global.util.jwt.JwtProvider;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;

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
        return jwtProvider.createAccessToken(userInfo);
    }
}
