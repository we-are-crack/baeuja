package xyz.baeuja.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequest {

    @NotBlank(message = "email is required.")
    private String email;
}
