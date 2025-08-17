package xyz.baeuja.api.learning.dto.sentence;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SentenceSummaryDto {

    private final Long id;
    private final String korean;
    private final String english;
    private final boolean isConversation;
    private final boolean isFamousLine;
    private final Boolean isBookmark;

    public SentenceSummaryDto(Long id, String korean, String english, boolean isConversation, boolean isFamousLine, Boolean isBookmark) {
        this.id = id;
        this.korean = korean;
        this.english = english;
        this.isConversation = isConversation;
        this.isFamousLine = isFamousLine;
        this.isBookmark = isBookmark != null && isBookmark; // null → false
    }
}
