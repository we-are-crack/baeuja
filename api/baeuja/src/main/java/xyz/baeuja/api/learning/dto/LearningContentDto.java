package xyz.baeuja.api.learning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.baeuja.api.content.domain.Classification;
import xyz.baeuja.api.content.domain.Content;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LearningContentDto {

    private final Long id;
    private final Classification classification;
    private final String title;
    private final String thumbnailUrl;

    private final int progressRate;

    private String artist;
    private String director;

    private void setArtist(String artist) {
        this.artist = artist;
    }

    private void setDirector(String director) {
        this.director = director;
    }

    public static LearningContentDto of(Content content, int progressRate) {
        LearningContentDto learningContentDto = new LearningContentDto(content.getId(),
                content.getClassification(),
                content.getTitle(),
                content.getThumbnailUrl(),
                progressRate);

        if (content.getClassification() == Classification.POP) {
            learningContentDto.setArtist(content.getArtist());
        } else {
            learningContentDto.setDirector(content.getDirector());
        }

        return learningContentDto;
    }
}
