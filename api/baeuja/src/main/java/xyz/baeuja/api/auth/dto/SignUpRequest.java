package xyz.baeuja.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.User;

@Getter
public class SignUpRequest {

    private String email;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "language 는 필수입니다.")
    private String language;

    @NotBlank(message = "timezone 은 필수입니다.")
    private String timezone;

    @NotNull(message = "loginType 은 필수입니다.")
    private LoginType loginType;

    public User toEntity() {
        return new User(nickname, language, timezone, loginType);
    }
}
