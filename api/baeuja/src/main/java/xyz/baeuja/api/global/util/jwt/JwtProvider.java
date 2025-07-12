package xyz.baeuja.api.global.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtProvider {

    private final long accessTokenExp;
    private final SecretKey secretKey;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.exp}") long accessTokenExp
    ) {
        this.accessTokenExp = accessTokenExp;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(JwtUserInfo userInfo) {
        return createToken(userInfo, accessTokenExp);
    }

    /**
     *
     * @param userInfo UserForAuthDto must include id, timezone, role
     * @param exp long, token expire time
     * @return JWT
     */
    public String createToken(JwtUserInfo userInfo, long exp) {
        Claims claims = Jwts.claims();
        claims.put("userId", userInfo.getId());
        claims.put("role", userInfo.getRole());

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of(userInfo.getTimezone()));
        ZonedDateTime tokenValidity = now.plusSeconds(exp);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

}
