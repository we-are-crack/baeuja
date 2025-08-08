package xyz.baeuja.api.auth.security.exception;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import xyz.baeuja.api.global.exception.ErrorCode;

public class InvalidJwtException extends AuthenticationException {

    public static final String CODE = ErrorCode.INVALID_TOKEN.name();

    public InvalidJwtException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidJwtException(String msg) {
        super(msg);
    }

    @Override
    public Authentication getAuthenticationRequest() {
        return super.getAuthenticationRequest();
    }

    @Override
    public void setAuthenticationRequest(Authentication authenticationRequest) {
        super.setAuthenticationRequest(authenticationRequest);
    }
}
