package xyz.baeuja.api.global.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.baeuja.api.user.domain.Role;
import xyz.baeuja.api.user.domain.User;

@Getter
@AllArgsConstructor
public class JwtUserInfo {

    private final Long id;
    private final String timezone;
    private final Role role;

    public static JwtUserInfo from(User user) {
        return new JwtUserInfo(user.getId(), user.getTimezone(), user.getRole());
    }
}
