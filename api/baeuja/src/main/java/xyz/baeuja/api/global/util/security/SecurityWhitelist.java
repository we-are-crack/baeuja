package xyz.baeuja.api.global.util.security;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class SecurityWhitelist {

    private static final List<String> WHITELIST_PREFIXES = List.of(
            "/api/auth",
            "/favicon.ico",
            "/error",
            "/docs"
    );

    public static boolean isWhitelisted(String path) {
        return WHITELIST_PREFIXES.stream().anyMatch(path::startsWith);
    }
}
