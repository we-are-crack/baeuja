package xyz.baeuja.api.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.baeuja.api.global.exception.UnexpectedException;
import xyz.baeuja.api.global.response.Result;

@Slf4j
@Order(100)
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 디폴트 예외 (서버 내부 오류)
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> defaultExceptionHandler(Exception exception) {
        log.error("🔥예상치 못한 예외 발생: {}", exception.getClass().getName(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.failure(UnexpectedException.CODE, "서버 내부 오류가 발생했습니다."));
    }

    /**
     * DTO 필드 제약조건 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        log.info("🚫methodArgumentNotValidExceptionHandler handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.failure("BAD_REQUEST", exception.getMessage()));
    }
}
