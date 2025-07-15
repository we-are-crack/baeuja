package xyz.baeuja.api.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.baeuja.api.global.exception.UnexpectedException;
import xyz.baeuja.api.auth.security.exception.ExpiredAccessTokenException;
import xyz.baeuja.api.auth.security.exception.InvalidJwtException;
import xyz.baeuja.api.global.response.ResultResponse;

@Slf4j
@Order(100)
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 디폴트 예외 (서버 내부 오류)
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleDefaultException(Exception exception) {
        log.error("🔥예상치 못한 예외 발생: {}", exception.getClass().getName(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResultResponse.failure(UnexpectedException.CODE, UnexpectedException.MESSAGE));
    }

    /**
     * DTO 필드 제약조건 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info("🚫methodArgumentNotValidExceptionHandler handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.failure("BAD_REQUEST_PARAM_OR_BODY", "The request parameter or body was invalid."));
    }

    /**
     * 요청 쿼리 파라미터 누락 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.info("🚫missingServletRequestParameterExceptionHandler handled: {} ", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.failure("MISSING_PARAMETER", "The request parameter was missing."));
    }

    /**
     * access token 만료 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleExpiredAccessTokenException(ExpiredAccessTokenException exception) {
        log.info("🚫expiredAccessTokenExceptionHandler handled: {} ", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResultResponse.failure(ExpiredAccessTokenException.CODE, exception.getMessage()));
    }

    /**
     * access token 검증 실패 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleInvalidJwtException(InvalidJwtException exception) {
        log.info("🚫handleInvalidJwtException handled: {} ", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResultResponse.failure(InvalidJwtException.CODE, exception.getMessage()));
    }
}
