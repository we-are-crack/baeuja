package xyz.baeuja.api.auth.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.baeuja.api.auth.exception.*;
import xyz.baeuja.api.global.response.ResultResponse;

@Slf4j
@Order(1)
@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * 존재하지 않는 사용자 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleUserNotFoundException(UserNotFoundException exception) {
        log.info("🚫userNotFoundExceptionHandler handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ResultResponse.failure(UserNotFoundException.CODE, exception.getMessage()));
    }

    /**
     * 회원 가입 요청 본문 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleInvalidSignUpRequestException(InvalidSignUpRequestException exception) {
        log.info("🚫InvalidSignUpRequestExceptionHandler handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResultResponse.failure(InvalidSignUpRequestException.CODE, exception.getMessage()));
    }

    /**
     * 이메일 중복 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleDuplicateEmailException(DuplicateEmailException exception) {
        log.info("🚫duplicateEmailExceptionHandler handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ResultResponse.failure(DuplicateEmailException.CODE, exception.getMessage()));
    }

    /**
     * 닉네임 중복 예외 처리
     */
    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<ResultResponse<Void>> handleDuplicateNicknameException(DuplicateNicknameException exception) {
        log.info("🚫DuplicateNicknameException handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResultResponse.failure(DuplicateNicknameException.CODE, exception.getMessage()));
    }

    /**
     * 닉네임 유효성 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ResultResponse<Void>> handleInvalidNicknameException(InvalidNicknameException exception) {
        log.info("🚫invalidNicknameExceptionHandler handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResultResponse.failure(InvalidNicknameException.CODE, exception.getMessage()));
    }
}
