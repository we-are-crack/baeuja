package xyz.baeuja.api.global.exception;

public class UnexpectedException extends BaseException {

    public static final String CODE = "INTERNAL_ERROR";

    public UnexpectedException() {
        super("서버 내부 오류가 발생했습니다.");
    }

    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedException(Throwable cause) {
        super(cause);
    }
}
