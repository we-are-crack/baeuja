package xyz.baeuja.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.User;

@Getter
@AllArgsConstructor
public class UserRequest {

    private String email;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotNull
    private String locale;

    @NotNull
    private String timezone;

    @NotNull
    private LoginType loginType;

    public User toEntity() {
        return new User(nickname, locale, timezone, loginType);
    }
}
