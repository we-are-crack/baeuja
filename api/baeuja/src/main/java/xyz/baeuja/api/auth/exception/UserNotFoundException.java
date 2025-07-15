package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public static final String CODE = "USER_NOT_FOUND";

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
