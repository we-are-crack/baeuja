package xyz.baeuja.api.learning.dto.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import xyz.baeuja.api.content.domain.Classification;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class LearningContentDto {

    private final Long id;
    private final Classification classification;
    private final String title;
    private final String thumbnailUrl;
    private final String artist;
    private final String director;

    private final Integer progressRate;

    public LearningContentDto(Long id, Classification classification, String title, String thumbnailUrl, String artist, String director, Integer progressRate) {
        this.id = id;
        this.classification = classification;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.artist = artist;
        this.director = director;
        this.progressRate = progressRate != null ? progressRate : 0;
    }
}
