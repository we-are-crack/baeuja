package xyz.baeuja.api.learning.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.baeuja.api.content.domain.Content;
import xyz.baeuja.api.global.entity.BaseEntity;
import xyz.baeuja.api.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserContentActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_content_activity_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    @Min(0)
    @Column(nullable = false)
    private int learningCount = 0;

    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private int progressRate = 0;
}
