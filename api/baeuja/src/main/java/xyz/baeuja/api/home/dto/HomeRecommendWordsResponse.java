package xyz.baeuja.api.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.baeuja.api.content.domain.Importance;
import xyz.baeuja.api.content.domain.Word;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class HomeRecommendWordsResponse {

    private Long wordId;
    private String koreanWord;
    private Importance importance;
    private List<SentenceSummaryDto> sentences = new ArrayList<>();

    public HomeRecommendWordsResponse(Long wordId, String koreanWord, Importance importance) {
        this.wordId = wordId;
        this.koreanWord = koreanWord;
        this.importance = importance;
    }

    public static HomeRecommendWordsResponse from(Word word) {
        HomeRecommendWordsResponse dto = new HomeRecommendWordsResponse(word.getId(), word.getKorean(), word.getImportance());

        word.getSentenceWords()
                .forEach(sentenceWord ->
                        dto.getSentences().add(SentenceSummaryDto.from(sentenceWord))
                );

        return dto;
    }
}
