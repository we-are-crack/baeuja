package xyz.baeuja.api.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.baeuja.api.user.exception.DuplicateEmailException;
import xyz.baeuja.api.user.exception.DuplicateNicknameException;
import xyz.baeuja.api.user.exception.InvalidNicknameException;
import xyz.baeuja.api.global.response.Result;
import xyz.baeuja.api.user.controller.UserApiController;

@RestControllerAdvice(assignableTypes = {UserApiController.class})
public class UserExceptionHandler {

    /**
     * 이메일 중복 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> duplicateEmailExceptionHandler(DuplicateEmailException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Result.failure(DuplicateEmailException.CODE, exception.getMessage()));
    }

    /**
     * 닉네임 중복 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> duplicateNicknameExceptionHandler(DuplicateNicknameException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Result.failure(DuplicateNicknameException.CODE, exception.getMessage()));
    }

    /**
     * 닉네임 유효성 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> invalidNicknameExceptionHandler(InvalidNicknameException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.failure(InvalidNicknameException.CODE, exception.getMessage()));
    }
}
