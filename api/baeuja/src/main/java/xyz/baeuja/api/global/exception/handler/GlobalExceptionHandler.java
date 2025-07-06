package xyz.baeuja.api.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.baeuja.api.global.exception.UnexpectedException;
import xyz.baeuja.api.global.response.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 디폴트 예외 (서버 내부 오류)
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> defaultExceptionHandler(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.failure(UnexpectedException.CODE, "서버 내부 오류가 발생했습니다."));
    }
}
