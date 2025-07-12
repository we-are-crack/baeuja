package xyz.baeuja.api.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xyz.baeuja.api.auth.security.exception.ExpiredAccessTokenException;
import xyz.baeuja.api.auth.security.exception.InvalidJwtException;
import xyz.baeuja.api.global.response.ResultResponse;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.info("🚫CustomAuthenticationEntryPoint handled: {} ", authException.getMessage());

        ResultResponse<Void> resultResponse;

        if (authException instanceof ExpiredAccessTokenException) {
            resultResponse = ResultResponse.failure(ExpiredAccessTokenException.CODE, authException.getMessage());
        } else if (authException instanceof InvalidJwtException) {
            resultResponse = ResultResponse.failure(InvalidJwtException.CODE, authException.getMessage());
        } else {
            resultResponse = ResultResponse.failure("UNAUTHORIZED", "인증되지 않은 요청입니다.");
        }

        String responseBody = objectMapper.writeValueAsString(resultResponse);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.getWriter().write(responseBody);
    }
}
