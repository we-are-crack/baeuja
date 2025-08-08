package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;
import xyz.baeuja.api.global.exception.ErrorCode;

public class InvalidSignUpRequestException extends BaseException {

    public static final String CODE = ErrorCode.INVALID_SIGN_UP_REQUEST_BODY.name();

    public InvalidSignUpRequestException() {
        super("Please double check the sign-up request fields.");
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
