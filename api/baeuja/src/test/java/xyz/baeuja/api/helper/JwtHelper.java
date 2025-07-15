package xyz.baeuja.api.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.baeuja.api.global.util.jwt.JwtProvider;

@Component
public class JwtHelper {

    @Autowired
    JwtProvider jwtProvider;


}
