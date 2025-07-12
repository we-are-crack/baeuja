package xyz.baeuja.api.global.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    public static <T> ResultResponse<T> success(T data) {
        return new ResultResponse<>("SUCCESS", "요청 성공", data);
    }

    public static ResultResponse<Void> success(String message) {
        return new ResultResponse<>("SUCCESS", message, null);
    }

    public static ResultResponse<Void> success() {
        return new ResultResponse<>("SUCCESS", "요청 성공", null);
    }


    public static ResultResponse<Void> failure(String code, String message) {
        return new ResultResponse<>(code, message, null);
    }
}
