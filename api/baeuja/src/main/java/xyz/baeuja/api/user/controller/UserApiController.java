package xyz.baeuja.api.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import xyz.baeuja.api.auth.security.CustomUserDetails;
import xyz.baeuja.api.global.response.ResultResponse;
import xyz.baeuja.api.user.dto.UserDto;
import xyz.baeuja.api.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ResultResponse<UserDto>> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();

        UserDto userInfo = userService.getUserInfo(userId);

        return ResponseEntity
                .ok(ResultResponse.success(userInfo));
    }
}
