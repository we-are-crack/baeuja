package xyz.baeuja.api.global.exception.jwt;

import xyz.baeuja.api.global.exception.BaseException;

public class ExpiredAccessTokenException extends BaseException {

    public static final String CODE = "EXPIRED_ACCESS_TOKEN";


    public ExpiredAccessTokenException() {
        super("access token 유효기간이 만료되었습니다.");
    }

    public ExpiredAccessTokenException(String message) {
        super(message);
    }

    public ExpiredAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredAccessTokenException(Throwable cause) {
        super(cause);
    }
}
