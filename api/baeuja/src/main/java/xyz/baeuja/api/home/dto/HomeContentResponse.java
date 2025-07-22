package xyz.baeuja.api.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.baeuja.api.content.domain.Classification;

@Getter
@AllArgsConstructor
public class HomeContentResponse {

    private Classification classification;
    private String title;
    private String artist;
    private String director;
    private String thumbnailUrl;
    private long unitCount;
    private long wordCount;
}
