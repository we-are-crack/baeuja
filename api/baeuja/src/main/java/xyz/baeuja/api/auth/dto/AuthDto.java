package xyz.baeuja.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDto {

    @NotBlank(message = "accessToken is required.")
    private final String accessToken;

    @NotBlank(message = "refreshToken is required.")
    private final String refreshToken;
}
