package xyz.baeuja.api.global.exception;

public class InvalidQueryParameterException extends BaseException {

    public static final String CODE = ErrorCode.INVALID_QUERY_PARAMETER.name();

    public InvalidQueryParameterException() {
    }

    public InvalidQueryParameterException(String message) {
        super(message);
    }

    public InvalidQueryParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidQueryParameterException(Throwable cause) {
        super(cause);
    }
}
