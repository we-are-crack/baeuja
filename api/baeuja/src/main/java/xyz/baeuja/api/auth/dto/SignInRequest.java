package xyz.baeuja.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;
}
