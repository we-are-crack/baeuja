package xyz.baeuja.api.global.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public static final String CODE = HttpStatus.NOT_FOUND.name();

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
