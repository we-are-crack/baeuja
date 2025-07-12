package xyz.baeuja.api.auth.exception;

import xyz.baeuja.api.global.exception.BaseException;

public class InvalidSignUpRequestException extends BaseException {

    public static final String CODE = "INVALID_SIGN_UP_REQUEST";

    public InvalidSignUpRequestException() {
        super("회원 가입 요청 필드를 다시 한 번 확인해 주세요.");
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
