package xyz.baeuja.api.learning.dto.unit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import xyz.baeuja.api.global.util.date.DateUtils;
import xyz.baeuja.api.learning.dto.sentence.RepresentativeSentenceDto;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class LearningUnitResponse {

    private final Long id;
    private final String thumbnail;

    private final Long sentencesCount;
    private final Long wordsCount;

    private final Integer progressRate;
    private final String lastLearned;

    @Setter
    private RepresentativeSentenceDto sentence;

    public LearningUnitResponse(Long id, String thumbnail, Long sentencesCount, Long wordsCount, Integer progressRate, LocalDateTime lastLearned) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.sentencesCount = sentencesCount;
        this.wordsCount = wordsCount;
        this.progressRate = progressRate != null ? progressRate : 0;
        this.lastLearned = lastLearned != null ? DateUtils.toDateTimeString(lastLearned) : "";
    }
}
