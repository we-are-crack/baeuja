package xyz.baeuja.api.global.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.baeuja.api.user.domain.Role;

@Getter
@AllArgsConstructor
public class JwtUserInfo {

    private Long id;
    private String timezone;
    private Role role;
}
