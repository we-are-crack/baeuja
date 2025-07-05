package xyz.baeuja.api.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.baeuja.api.global.exception.DuplicateEmailException;
import xyz.baeuja.api.global.exception.DuplicateNicknameException;
import xyz.baeuja.api.global.exception.InvalidNicknameException;
import xyz.baeuja.api.global.exception.UnexpectedException;
import xyz.baeuja.api.global.response.Result;
import xyz.baeuja.api.user.dto.UserRequest;
import xyz.baeuja.api.user.dto.UserResponse;
import xyz.baeuja.api.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /**
     * 이메일 중복 예외
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> duplicateEmailExceptionHandler(DuplicateEmailException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Result.failure(DuplicateEmailException.CODE, exception.getMessage()));
    }

    /**
     * 닉네임 중복 예외
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> duplicateNicknameExceptionHandler(DuplicateNicknameException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Result.failure(DuplicateNicknameException.CODE, exception.getMessage()));
    }

    /**
     * 닉네임 유효성 예외
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> invalidNicknameExceptionHandler(InvalidNicknameException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Result.failure(InvalidNicknameException.CODE, exception.getMessage()));
    }

    /**
     * 디폴트 예외 (서버 내부 오류)
     */
    @ExceptionHandler
    public ResponseEntity<Result<Void>> defaultExceptionHandler(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.failure(UnexpectedException.CODE, "서버 내부 오류가 발생했습니다."));
    }

    /**
     * 회원 가입
     */
    @PostMapping
    public ResponseEntity<Result<?>> saveUser(@Validated @RequestBody UserRequest request) {
        Long savedId = userService.singUp(request);

        UserResponse response = new UserResponse(savedId, request.getEmail(), request.getNickname(), request.getLoginType());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Result.success(response));
    }

    /**
     * 닉네임 유효성 검사
     */
    @GetMapping("/check-nickname")
    public ResponseEntity<Result<Void>> checkNickname(@RequestParam String nickname) {
        userService.validateNickname(nickname);

        return ResponseEntity.ok(Result.success());
    }
}
