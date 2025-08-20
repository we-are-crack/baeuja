package xyz.baeuja.api.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.baeuja.api.global.entity.BaseEntity;
import xyz.baeuja.api.learning.domain.UserContentActivity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserContentActivity> userContentActivities = new ArrayList<>();

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String timezone;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private LoginType loginType;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User(String nickname, String language, String timezone, LoginType loginType) {
        this.nickname = nickname;
        this.language = language;
        this.timezone = timezone;
        this.loginType = loginType;
        this.role = Role.GUEST;
    }

    /**
     * 구글 계정으로 전환
     */
    public void convertToGoogleAccount(String email) {
        this.loginType = LoginType.GOOGLE;
        this.role = Role.MEMBER;
        this.email = email;
    }
}
