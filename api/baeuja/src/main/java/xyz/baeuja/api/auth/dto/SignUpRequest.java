package xyz.baeuja.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.User;

@Getter
public class SignUpRequest {

    private String email;

    private String nickname;

    @NotBlank(message = "language is required")
    private String language;

    @NotBlank(message = "timezone is required")
    private String timezone;

    @NotNull(message = "loginType is required")
    private LoginType loginType;

    public User toEntity() {
        return new User(nickname, language, timezone, loginType);
    }

    public User toEntity(String nickname) {
        return new User(nickname, language, timezone, loginType);
    }
}
