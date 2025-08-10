package xyz.baeuja.api.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.baeuja.api.content.domain.SentenceWord;

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

    public static SentenceSummaryDto from(SentenceWord sentenceWord) {
        return new SentenceSummaryDto(
                sentenceWord.getSentence().getUnit().getId(),
                sentenceWord.getSentence().getUnit().getThumbnailUrl(),
                sentenceWord.getSentence().getId(),
                sentenceWord.getSentence().getKorean(),
                sentenceWord.getSentence().getEnglish(),
                sentenceWord.getKoreanWordInSentence(),
                sentenceWord.getEnglishWordInSentence()
        );
    }
}
