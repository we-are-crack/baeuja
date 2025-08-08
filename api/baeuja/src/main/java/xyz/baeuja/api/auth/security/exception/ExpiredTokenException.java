package xyz.baeuja.api.auth.security.exception;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;
import xyz.baeuja.api.global.exception.ErrorCode;

@Getter
public class ExpiredTokenException extends AuthenticationException {

    public static final String CODE = ErrorCode.TOKEN_EXPIRED.name();

    private Claims claims;

    public ExpiredTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ExpiredTokenException(Claims claims, String msg) {
        super(msg);
        this.claims = claims;
    }
}
