package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;

public class DuplicateNicknameException extends BaseException {

    public static final String CODE = "DUPLICATE_NICKNAME";

    public DuplicateNicknameException() {
        super("This nickname is already in use.");
    }

    public DuplicateNicknameException(String message) {
        super(message);
    }

    public DuplicateNicknameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateNicknameException(Throwable cause) {
        super(cause);
    }
}
