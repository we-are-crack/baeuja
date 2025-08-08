package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;
import xyz.baeuja.api.global.exception.ErrorCode;

public class DuplicateNicknameException extends BaseException {

    public static final String CODE = ErrorCode.DUPLICATE_NICKNAME.name();

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
