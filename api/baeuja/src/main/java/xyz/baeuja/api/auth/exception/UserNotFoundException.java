package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public static final String CODE = "USER_NOT_FOUND";

    public UserNotFoundException() {
        super("등록되지 않은 사용자입니다. 회원 가입을 해주세요.");
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
