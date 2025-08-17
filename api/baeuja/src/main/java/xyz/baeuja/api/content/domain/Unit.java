package xyz.baeuja.api.content.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.baeuja.api.global.entity.BaseEntity;
import xyz.baeuja.api.learning.domain.UserUnitActivity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Unit extends BaseEntity {

    @Id
    @Column(name = "unit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    @OneToMany(mappedBy = "unit")
    private List<Sentence> sentences = new ArrayList<>();

    @OneToMany(mappedBy = "unit")
    private List<UserUnitActivity> userUnitActivities = new ArrayList<>();

    @Column(nullable = false, length = 500)
    private String thumbnailUrl;

    @Column(nullable = false)
    private String youtubeId;

    @Column(nullable = false)
    private int startTime;

    @Column(nullable = false)
    private int endTime;
}
