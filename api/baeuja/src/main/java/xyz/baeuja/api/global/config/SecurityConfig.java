package xyz.baeuja.api.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.baeuja.api.auth.security.CustomUserDetailsService;
import xyz.baeuja.api.auth.security.JwtAuthFilter;
import xyz.baeuja.api.auth.security.handler.CustomAccessDeniedHandler;
import xyz.baeuja.api.auth.security.handler.CustomAuthenticationEntryPoint;
import xyz.baeuja.api.global.util.jwt.JwtProvider;
import xyz.baeuja.api.user.domain.Role;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITELIST = {
            "/api/auth/**",
            "/favicon.ico",
            "/error"
    };

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 활성화
                .cors(Customizer.withDefaults())
                // CSRF, 폼 로그인, HTTP Basic 인증 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션 미사용 (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JwtAuthFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(new JwtAuthFilter(jwtProvider, userDetailsService, authenticationEntryPoint), UsernamePasswordAuthenticationFilter.class);

        // 커스텀 예외 핸들러 등록
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler));

        // http request 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(WHITELIST).permitAll()
                .requestMatchers("/docs/**").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
        );

        return http.build();
    }
}
