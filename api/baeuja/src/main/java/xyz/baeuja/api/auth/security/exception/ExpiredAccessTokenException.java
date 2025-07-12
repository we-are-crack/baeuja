package xyz.baeuja.api.auth.security.exception;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class ExpiredAccessTokenException extends AuthenticationException {

    public static final String CODE = "EXPIRED_ACCESS_TOKEN";

    public ExpiredAccessTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ExpiredAccessTokenException(String msg) {
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
