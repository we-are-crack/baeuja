package xyz.baeuja.api.global.exception;

public class UnexpectedException extends BaseException {

    public static final String CODE = ErrorCode.INTERNAL_ERROR.name();
    public static final String MESSAGE = "An internal server error occurred. Please try again later.";
}
