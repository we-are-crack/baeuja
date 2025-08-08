package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;
import xyz.baeuja.api.global.exception.ErrorCode;

public class InvalidNicknameException extends BaseException {

    public static final String CODE = ErrorCode.INVALID_NICKNAME.name();

    public InvalidNicknameException() {
        super("Invalid nickname. Please check again.");
    }

    public InvalidNicknameException(String message) {
        super(message);
    }

    public InvalidNicknameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNicknameException(Throwable cause) {
        super(cause);
    }
}
