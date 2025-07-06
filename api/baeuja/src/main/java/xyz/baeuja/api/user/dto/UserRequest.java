package xyz.baeuja.api.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.User;

@Getter
public class UserRequest {

    private String email;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotNull(message = "locale 은 필수입니다.")
    private String locale;

    @NotNull(message = "timezone 은 필수입니다.")
    private String timezone;

    @NotNull(message = "loginType 은 필수입니다.")
    private LoginType loginType;

    public User toEntity() {
        return new User(nickname, locale, timezone, loginType);
    }
}
