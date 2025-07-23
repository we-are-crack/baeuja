package xyz.baeuja.api.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SentenceSummaryDto {

    private Long unitId;
    private String unitThumbnailUrl;

    private Long sentenceId;
    private String koreanSentence;
    private String englishSentence;

    private String koreanWordInSentence;
    private String englishWordInSentence;
}
