package xyz.baeuja.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.baeuja.api.user.domain.LoginType;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private LoginType loginType;
}
