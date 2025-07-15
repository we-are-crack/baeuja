package xyz.baeuja.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.User;

@Getter
@AllArgsConstructor
public class UserDto {

    private String email;
    private String nickname;
    private String language;
    private String timezone;
    private LoginType loginType;

    public static UserDto from(User user) {
        return new UserDto(user.getEmail(), user.getNickname(), user.getLanguage(), user.getTimezone(), user.getLoginType());
    }
}
