package xyz.baeuja.api.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.baeuja.api.auth.dto.AuthDto;
import xyz.baeuja.api.auth.dto.SignInRequest;
import xyz.baeuja.api.auth.dto.SignUpRequest;
import xyz.baeuja.api.auth.service.AuthService;
import xyz.baeuja.api.global.response.ResultResponse;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.user.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * 로그인 (Guest 는 요청할 필요 없음)
     *
     * @param request UserSignInRequest must include email
     * @return AuthDto
     */
    @PostMapping("/sign-in")
    public ResponseEntity<ResultResponse<AuthDto>> signIn(@RequestBody @Valid SignInRequest request) {
        log.info("로그인 시도: email = {}", request.getEmail());

        JwtUserInfo userInfo = userService.loadUserForSignIn(request.getEmail());

        AuthDto response = new AuthDto(
                authService.issueAccessToken(userInfo),
                authService.issueRefreshToken(userInfo)
        );

        return ResponseEntity
                .ok(ResultResponse.success(response));
    }

    /**
     * 회원 가입
     *
     * @param request UserSignInRequest must include (nickname, language, timezone, loginType)
     * @return AuthDto
     */
    @PostMapping("/sign-up")
    public ResponseEntity<ResultResponse<AuthDto>> signUp(@RequestBody @Valid SignUpRequest request) {
        JwtUserInfo jwtUserInfo = userService.signUp(request);

        AuthDto response = new AuthDto(
                authService.issueAccessToken(jwtUserInfo),
                authService.issueRefreshToken(jwtUserInfo)
        );

        log.info("회원 가입 성공 : userId = {}", jwtUserInfo.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResultResponse.success(response));
    }

    /**
     * 닉네임 유효성 검사
     *
     * @param nickname String
     * @return ResultResponse.success()
     */
    @GetMapping("/check-nickname")
    public ResponseEntity<ResultResponse<Void>> checkNickname(@RequestParam String nickname) {
        userService.validateNickname(nickname);

        return ResponseEntity.ok(ResultResponse.success("This is available nickname."));
    }

    /**
     * refresh token 으로 access token & refresh token 재발급
     *
     * @param request AuthDto
     * @return AuthDto
     */
    @PostMapping("/token")
    public ResponseEntity<ResultResponse<AuthDto>> getRenewToken(@RequestBody @Valid AuthDto request) {
        AuthDto authDto = authService.renewAccessTokenAndRefreshToken(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResultResponse.success(authDto));
    }
}
