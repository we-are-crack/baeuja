package xyz.baeuja.api.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.baeuja.api.content.domain.Importance;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class HomeRecommendWordsResponse {

    private Long wordId;
    private String koreanWord;
    private Importance importance;

    private List<SentenceSummaryDto> sentences = new ArrayList<>();
}
