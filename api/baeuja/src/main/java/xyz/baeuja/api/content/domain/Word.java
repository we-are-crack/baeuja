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
public class Word extends BaseEntity {

    @Id
    @Column(name = "word_id")
    private Long id;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    private List<SentenceWord> sentenceWords = new ArrayList<>();

    @Column(nullable = false)
    private String korean;

    @Column(nullable = false)
    private String english;

    @Column(nullable = false)
    private String roman;

    @Column(length = 500)
    private String perfectVoiceUrl;

    @Column(nullable = false)
    private String importance;
}
