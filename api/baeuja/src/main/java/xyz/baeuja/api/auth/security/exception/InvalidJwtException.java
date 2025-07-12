package xyz.baeuja.api.auth.security.exception;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {

    public static final String CODE = "INVALID_TOKEN";

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
