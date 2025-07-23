package xyz.baeuja.api.home.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.baeuja.api.content.domain.Classification;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class HomeContentsResponse {

    private Classification classification;
    private String title;
    private String artist;
    private String director;
    private String thumbnailUrl;
    private long unitCount;
    private long wordCount;
}
