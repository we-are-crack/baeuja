package we_are_crack.baeuja.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import we_are_crack.baeuja.global.exception.DuplicateEmailException;
import we_are_crack.baeuja.global.exception.DuplicateNicknameException;
import we_are_crack.baeuja.global.exception.InvalidNicknameException;
import we_are_crack.baeuja.global.exception.UnexpectedException;
import we_are_crack.baeuja.global.response.Result;
import we_are_crack.baeuja.user.dto.UserRequest;
import we_are_crack.baeuja.user.dto.UserResponse;
import we_are_crack.baeuja.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /**
     * 회원 가입
     */
    @PostMapping
    public ResponseEntity<Result<?>> saveUser(@Validated @RequestBody UserRequest request) {
        try {
            Long savedId = userService.singUp(request);

            UserResponse response = new UserResponse(savedId, request.getEmail(), request.getNickname(), request.getLoginType());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Result.success(response));

        } catch (DuplicateEmailException exception) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Result.failure(DuplicateEmailException.CODE, exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.failure(UnexpectedException.CODE, "서버 내부 오류가 발생했습니다."));
        }
    }

    /**
     * 닉네임 유효성 검사
     */
    @GetMapping("/check-nickname")
    public ResponseEntity<Result<Void>> checkNickname(@RequestParam String nickname) {
        try {
            userService.validateNickname(nickname);

            return ResponseEntity
                    .ok(Result.success());
        } catch (InvalidNicknameException exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Result.failure(InvalidNicknameException.CODE, exception.getMessage()));
        } catch (DuplicateNicknameException exception) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Result.failure(DuplicateNicknameException.CODE, exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.failure(UnexpectedException.CODE, "서버 내부 오류가 발생했습니다."));
        }
    }
}
