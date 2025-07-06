package xyz.baeuja.api.user.exception;

import xyz.baeuja.api.global.exception.BaseException;

public class DuplicateEmailException extends BaseException {

    public static final String CODE = "DUPLICATE_EMAIL";

    public DuplicateEmailException() {
        super("이미 사용 중인 이메일입니다.");
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
