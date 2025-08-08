package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;
import xyz.baeuja.api.global.exception.ErrorCode;

public class UserNotFoundException extends BaseException {

    public static final String CODE = ErrorCode.USER_NOT_FOUND.name();

    public UserNotFoundException() {
        super("You are not a registered user. Please sign up.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
