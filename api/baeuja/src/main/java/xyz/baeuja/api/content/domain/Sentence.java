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
public class Sentence extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sentence_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToMany(mappedBy = "sentence", cascade = CascadeType.ALL)
    private List<SentenceWord> sentenceWords = new ArrayList<>();

    @Column(nullable = false)
    private String korean;

    @Column(nullable = false)
    private String english;

    @Column(nullable = false)
    private String roman;

    private String perfectVoiceUrl;

    @Column(nullable = false)
    private int startTime;

    @Column(nullable = false)
    private int endTime;

    @Column(nullable = false)
    private boolean isConversation;

    @Column(nullable = false)
    private boolean isFamousLine;
}
