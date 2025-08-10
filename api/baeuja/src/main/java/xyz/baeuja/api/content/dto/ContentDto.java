package xyz.baeuja.api.content.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.baeuja.api.content.domain.Classification;
import xyz.baeuja.api.content.domain.Content;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentDto {

    private final Long id;
    private final Classification classification;
    private final String title;
    private final String thumbnailUrl;
    private final String youtubeId;
    private final String description;

    @Setter
    private String artist;

    @Setter
    private String director;

    public static ContentDto from(Content content) {
        ContentDto contentDto = new ContentDto(
                content.getId(),
                content.getClassification(),
                content.getTitle(),
                content.getThumbnailUrl(),
                content.getYoutubeId(),
                content.getDescription());

        if (content.getClassification() == Classification.POP) {
            contentDto.setArtist(content.getArtist());
        } else {
            contentDto.setDirector(content.getDirector());
        }

        return contentDto;
    }
}
