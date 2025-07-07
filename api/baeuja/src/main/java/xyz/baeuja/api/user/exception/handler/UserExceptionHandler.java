package xyz.baeuja.api.user.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.baeuja.api.user.exception.DuplicateEmailException;
import xyz.baeuja.api.user.exception.DuplicateNicknameException;
import xyz.baeuja.api.user.exception.InvalidNicknameException;
import xyz.baeuja.api.global.response.Result;
import xyz.baeuja.api.user.controller.UserApiController;

@Slf4j
@Order(1)
@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * 이메일 중복 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> duplicateEmailExceptionHandler(DuplicateEmailException exception) {
        log.info("🚫duplicateEmailExceptionHandler handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Result.failure(DuplicateEmailException.CODE, exception.getMessage()));
    }

    /**
     * 닉네임 중복 예외 처리
     */
    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<Result<Void>> duplicateNicknameExceptionHandler(DuplicateNicknameException exception) {
        log.info("🚫DuplicateNicknameException handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Result.failure(DuplicateNicknameException.CODE, exception.getMessage()));
    }

    /**
     * 닉네임 유효성 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> invalidNicknameExceptionHandler(InvalidNicknameException exception) {
        log.info("🚫invalidNicknameExceptionHandler handled: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.failure(InvalidNicknameException.CODE, exception.getMessage()));
    }
}
