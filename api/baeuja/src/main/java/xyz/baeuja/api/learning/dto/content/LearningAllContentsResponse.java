package xyz.baeuja.api.learning.dto.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LearningAllContentsResponse {

    private List<LearningContentDto> pop;
    private List<LearningContentDto> movie;
    private List<LearningContentDto> drama;

}
