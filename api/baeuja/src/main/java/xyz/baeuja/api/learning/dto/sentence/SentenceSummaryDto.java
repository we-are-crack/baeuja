package xyz.baeuja.api.content.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.baeuja.api.content.domain.Sentence;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SentenceDto {

    private final Long id;
    private final String korean;
    private final String english;
    private final boolean isConversation;
    private final boolean isFamousLine;
    private final boolean isBookmark;

    public SentenceDto of(Sentence sentence, boolean isBookmark) {
        return new SentenceDto(
                sentence.getId(),
                sentence.getKorean(),
                sentence.getEnglish(),
                sentence.isConversation(),
                sentence.isFamousLine(),
                isBookmark);
    }
}
