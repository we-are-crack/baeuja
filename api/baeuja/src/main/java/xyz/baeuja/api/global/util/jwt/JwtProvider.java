package xyz.baeuja.api.global.util.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.baeuja.api.auth.security.exception.ExpiredAccessTokenException;
import xyz.baeuja.api.auth.security.exception.InvalidJwtException;
import xyz.baeuja.api.user.domain.Role;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

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

    /**
     * access token 생성
     *
     * @param userInfo JwtUserInfo must include (id, timezone, role)
     * @return access token string
     */
    public String createAccessToken(JwtUserInfo userInfo) {
        return createToken(userInfo, accessTokenExp);
    }

    /**
     * @param userInfo JwtUserInfo must include (id, timezone, role)
     * @param exp      token expire time
     * @return access token string
     */
    public String createAccessToken(JwtUserInfo userInfo, long exp) {
        return createToken(userInfo, exp);
    }

    /**
     * JWT 생성
     *
     * @param userInfo UserForAuthDto must include id, timezone, role
     * @param exp      token expire time
     * @return jwt string
     */
    public String createToken(JwtUserInfo userInfo, long exp) {
        Claims claims = Jwts.claims();
        claims.put("userId", userInfo.getId());
        claims.put("role", userInfo.getRole().name());

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of(userInfo.getTimezone()));
        ZonedDateTime tokenValidity = now.plusSeconds(exp);

        log.info("created jwt, userId = {}, expiration = {}", userInfo.getId(), exp);

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuer("api.baeuja.xyz")
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰 id(jti) 조회
     *
     * @param token jwt string
     * @return jti string
     */
    public String getJti(String token) {
        return parseAndValidate(token).getId();
    }

    /**
     * 토큰에서 user id 조회
     *
     * @param token access token string
     * @return user id
     */
    public Long getUserId(String token) {
        return parseAndValidate(token).get("userId", Long.class);
    }

    /**
     * 토큰에서 user role 조회
     *
     * @param token access token string
     * @return user id
     */
    public Role getRole(String token) {
        String roleString = parseAndValidate(token).get("role", String.class);
        return Enum.valueOf(Role.class, roleString);
    }

    /**
     * 토큰 파싱 및 검증
     *
     * @param token jwt string
     * @return Claims 검증된 토큰 claims
     * @throws ExpiredAccessTokenException 토큰 유효기간 만료
     * @throws InvalidJwtException         토큰 검증 실패
     */
    public Claims parseAndValidate(String token) throws ExpiredAccessTokenException, InvalidJwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException("Access Token 유효기간이 만료되었습니다.");
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.warn("token validate exception = {}", e.getMessage());
            throw new InvalidJwtException("유효하지 않은 토큰입니다.");
        }
    }
}
