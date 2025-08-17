package xyz.baeuja.api.learning.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.baeuja.api.content.domain.Unit;
import xyz.baeuja.api.global.entity.BaseEntity;
import xyz.baeuja.api.user.domain.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUnitActivity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_unit_activity_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Min(0)
    @Column(nullable = false)
    private int learningCount = 0;

    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private int progressRate = 0;
}
