package xyz.baeuja.api.global.exception.jwt;

import xyz.baeuja.api.global.exception.BaseException;

public class InvalidJwtException extends BaseException {

    public static final String CODE = "INVALID_TOKEN";

    public InvalidJwtException() {
        super("유효하지 않은 토큰입니다.");
    }

    public InvalidJwtException(String message) {
        super(message);
    }

    public InvalidJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidJwtException(Throwable cause) {
        super(cause);
    }
}
