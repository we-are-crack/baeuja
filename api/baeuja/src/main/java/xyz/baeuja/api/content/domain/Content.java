package xyz.baeuja.api.content.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.baeuja.api.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseEntity {

    @Id
    @Column(name = "content_id")
    private Long id;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private List<Unit> units = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Classification classification;

    @Column(nullable = false, unique = true, length = 100)
    private String title;

    @Column(length = 100)
    private String artist;

    @Column(length = 100)
    private String director;

    @Column(nullable = false, length = 9999)
    private String description;

    @Column(nullable = false, length = 500)
    private String thumbnailUrl;

    @Column(nullable = false)
    private String youtubeId;
}
