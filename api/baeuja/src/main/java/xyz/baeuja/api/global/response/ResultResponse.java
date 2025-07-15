package xyz.baeuja.api.global.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultResponse<T> {

    private final String code;
    private final String message;
    private T data;

    public static ResultResponse<Void> success() {
        return new ResultResponse<>("SUCCESS", "The request was successful.");
    }

    public static <T> ResultResponse<T> success(T data) {
        return new ResultResponse<>("SUCCESS", "The request was successful.", data);
    }

    public static ResultResponse<Void> success(String message) {
        return new ResultResponse<>("SUCCESS", message);
    }

    public static ResultResponse<Void> failure(String code, String message) {
        return new ResultResponse<>(code, message);
    }
}
