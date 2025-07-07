package xyz.baeuja.api.user.exception;

import xyz.baeuja.api.global.exception.BaseException;

public class DuplicateNicknameException extends BaseException {

    public static final String CODE = "DUPLICATE_NICKNAME";

    public DuplicateNicknameException() {
        super("이미 사용 중인 닉네임입니다.");
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
