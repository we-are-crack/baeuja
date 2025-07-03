package we_are_crack.baeuja.global.exception;

public class DuplicateEmailException extends RuntimeException {

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
