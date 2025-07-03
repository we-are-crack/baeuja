package we_are_crack.baeuja.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import we_are_crack.baeuja.user.domain.LoginType;
import we_are_crack.baeuja.user.domain.User;

@Data
@AllArgsConstructor
public class UserRequest {

    private String email;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Length(min = 2, max = 20, message = "닉네임 길이가 2이상 20이하여야 합니다.")
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
