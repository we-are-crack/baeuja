package xyz.baeuja.api.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import xyz.baeuja.api.global.exception.ErrorCode;
import xyz.baeuja.api.global.exception.InvalidQueryParameterException;
import xyz.baeuja.api.global.exception.UnexpectedException;
import xyz.baeuja.api.auth.security.exception.ExpiredTokenException;
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
        log.info("🚫MethodArgumentNotValidExceptionHandler handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.failure(ErrorCode.INVALID_REQUEST_BODY.name(), "The request body is invalid."));
    }

    /**
     * 잘못된 요청 쿼리 파라미터
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleInvalidQueryParameterException(InvalidQueryParameterException exception) {
        log.info("🚫InvalidQueryParameterExceptionHandler handled: {} ", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.failure(InvalidQueryParameterException.CODE, "The request query parameter is invalid."));
    }

    /**
     * 요청 쿼리 파라미터 누락 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.info("🚫MissingServletRequestParameterExceptionHandler handled: {} ", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.failure(ErrorCode.MISSING_QUERY_PARAMETER.name(), "The request query parameter is missing."));
    }

    /**
     * 요청 파라미터 타입 불일치 <br>
     * 쿼리 파리미터 타입 불일치 -> INVALID_QUERY_PARAMETER_TYPE <br>
     * 경로 파라미터 타입 불일치 -> INVALID_PATH_PARAMETER
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.info("🚫MethodArgumentTypeMismatchExceptionHandler handled: {} ", exception.getMessage());

        if (exception.getCause() instanceof ConversionFailedException) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResultResponse.failure(ErrorCode.INVALID_PATH_PARAMETER.name(), "The request path parameter is invalid."));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.failure(ErrorCode.INVALID_QUERY_PARAMETER_TYPE.name(), "The request query parameter is invalid."));
    }

    /**
     * token 만료 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleExpireTokenException(ExpiredTokenException exception) {
        log.info("🚫ExpiredTokenExceptionHandler handled: {} ", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResultResponse.failure(ExpiredTokenException.CODE, exception.getMessage()));
    }

    /**
     * token 검증 실패 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleInvalidJwtException(InvalidJwtException exception) {
        log.info("🚫InvalidJwtExceptionHandler handled: {} ", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResultResponse.failure(InvalidJwtException.CODE, exception.getMessage()));
    }
}
