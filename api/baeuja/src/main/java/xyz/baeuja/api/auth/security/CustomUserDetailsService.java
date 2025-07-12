package xyz.baeuja.api.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.user.domain.User;
import xyz.baeuja.api.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User findUser = userRepository.findOne(Long.parseLong(userId));

        if (findUser == null) {
            throw new UsernameNotFoundException("토큰 사용자를 찾을 수 없습니다.");
        }

        return CustomUserDetails.from(JwtUserInfo.from(findUser));
    }
}
