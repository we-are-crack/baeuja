package xyz.baeuja.api.learning.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.content.domain.Classification;
import xyz.baeuja.api.content.domain.Content;
import xyz.baeuja.api.content.dto.ContentDto;
import xyz.baeuja.api.content.repository.ContentRepository;
import xyz.baeuja.api.global.exception.InvalidQueryParameterException;
import xyz.baeuja.api.global.exception.NotFoundException;
import xyz.baeuja.api.global.response.page.PageInfo;
import xyz.baeuja.api.global.response.page.PagedResponse;
import xyz.baeuja.api.learning.dto.content.LearningContentDto;
import xyz.baeuja.api.learning.dto.content.LearningAllContentsResponse;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LearningService {

    private final ContentRepository contentRepository;

    /**
     * 학습 컨텐츠 리스트를 분류 별로 조회
     *
     * @param pageSize 각 분류별 콘텐츠 개수
     * @return LearningContentsResponse
     */
    public LearningAllContentsResponse findAllLearningContents(int pageSize) {
        Map<Classification, List<LearningContentDto>> map = new EnumMap<>(Classification.class);

        for (Classification classification : Classification.values()) {
            Slice<Content> contentSlice = getContentSlice(classification, 0, pageSize);
            map.put(classification, getLearningContentDtoList(contentSlice));
        }

        return new LearningAllContentsResponse(map.get(Classification.POP), map.get(Classification.MOVIE), map.get(Classification.DRAMA));
    }

    /**
     * classification에 해당하는 콘텐츠 리스트 조회
     *
     * @param classification 조회할 콘텐츠 분류
     * @param pageNumber     시작 페이지 번호
     * @param pageSize       페이지 크기
     * @return PagedResponse
     */
    public PagedResponse<LearningContentDto> findLearningContents(Classification classification, int pageNumber, int pageSize) {
        Slice<Content> contentSlice = getContentSlice(classification, pageNumber, pageSize);
        return new PagedResponse<>(getLearningContentDtoList(contentSlice), PageInfo.of(contentSlice));
    }

    /**
     * * 콘텐츠 상세 조회
     *
     * @param contentId 조회하고자 하는 content id
     * @return ContentDto
     * @throws NotFoundException content id에 해당하는 content가 존재하지 않음
     */
    public ContentDto findContent(Long contentId) throws NotFoundException {
        Content findContent = contentRepository.findById(contentId)
                .orElseThrow(() -> new NotFoundException("Learning content not found."));
        return ContentDto.from(findContent);
    }

    /**
     * Content 분류(classification)에 해당하는 content를 Slice 타입으로 반환하는 <br>
     * (contentRepository.findAllByClassification() 메서드를 호출해서 Content slice 반환.
     *
     * @param classification content 분류
     * @param pageNumber     시작 페이지 번호
     * @param pageSize       페이지 크기
     * @return Content slice
     * @throws InvalidQueryParameterException 유효하지 않은 pageNumber 또는 pageSize 값이 넘어올 때
     */
    private Slice<Content> getContentSlice(Classification classification, int pageNumber, int pageSize)
            throws InvalidQueryParameterException {
        try {
            return contentRepository.findAllByClassification(
                    classification,
                    PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"))
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryParameterException(e.getMessage());
        }
    }

    /**
     * Content slice -> LearningContentDto list 로 변환
     *
     * @param contentSlice Content slice
     * @return LearningContentDto list
     */
    private List<LearningContentDto> getLearningContentDtoList(Slice<Content> contentSlice) {
        return contentSlice.getContent()
                .stream()
                .map(content -> LearningContentDto.of(content, 0))
                .toList();
    }
}
