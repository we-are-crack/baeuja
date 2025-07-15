package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;

public class DuplicateEmailException extends BaseException {

    public static final String CODE = "DUPLICATE_EMAIL";

    public DuplicateEmailException() {
        super("This email is already in use.");
    }

    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEmailException(Throwable cause) {
        super(cause);
    }
}
