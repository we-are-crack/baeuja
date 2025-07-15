package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;

public class InvalidSignUpRequestException extends BaseException {

    public static final String CODE = "INVALID_SIGN_UP_REQUEST";

    public InvalidSignUpRequestException() {
        super("Please double check the membership request fields.");
    }

    public InvalidSignUpRequestException(String message) {
        super(message);
    }

    public InvalidSignUpRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSignUpRequestException(Throwable cause) {
        super(cause);
    }
}
