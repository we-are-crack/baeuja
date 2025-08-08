package xyz.baeuja.api.learning.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.baeuja.api.content.domain.Classification;
import xyz.baeuja.api.content.domain.Content;
import xyz.baeuja.api.content.repository.ContentRepository;
import xyz.baeuja.api.global.exception.InvalidQueryParameterException;
import xyz.baeuja.api.learning.dto.LearningContentDto;
import xyz.baeuja.api.learning.dto.LearningContentsResponse;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LearningService {

    private final ContentRepository contentRepository;

    /**
     * 학습 컨텐츠 리스트를 분류 별로 조회
     *
     * @param pageSize 각 분류별 콘텐츠 개수
     * @return LearningContentsResponse
     */
    public LearningContentsResponse findContents(int pageSize) {
        Map<Classification, List<LearningContentDto>> map = new EnumMap<>(Classification.class);

        try {
            for (Classification classification : Classification.values()) {
                Slice<Content> contentSlice = contentRepository.findAllByClassification(
                        classification,
                        PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt")));

                map.put(classification, getLearningContentDtoList(contentSlice));
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryParameterException(e.getMessage());
        }

        return new LearningContentsResponse(map.get(Classification.POP), map.get(Classification.MOVIE), map.get(Classification.DRAMA));
    }

    private List<LearningContentDto> getLearningContentDtoList(Slice<Content> contentSlice) {
        return contentSlice.getContent()
                .stream()
                .map(content -> LearningContentDto.of(content, 0))
                .toList();
    }
}
