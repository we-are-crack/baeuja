package we_are_crack.baeuja.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import we_are_crack.baeuja.user.domain.LoginType;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private LoginType loginType;
}
