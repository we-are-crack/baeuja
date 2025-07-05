package xyz.baeuja.api.global.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> {

    private final String code;
    private final String message;
    private final T data;

    public static <T> Result<T> success(T data) {
        return new Result<>("SUCCESS", "요청 성공", data);
    }

    public static Result<Void> success() {
        return new Result<>("SUCCESS", "요청 성공", null);
    }

    public static Result<Void> failure(String code, String message) {
        return new Result<>(code, message, null);
    }
}
