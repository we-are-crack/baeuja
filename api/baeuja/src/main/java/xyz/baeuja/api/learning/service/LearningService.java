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
import xyz.baeuja.api.content.repository.query.ContentQueryRepository;
import xyz.baeuja.api.content.repository.query.SentenceQueryRepository;
import xyz.baeuja.api.content.repository.query.UnitQueryRepository;
import xyz.baeuja.api.global.exception.InvalidQueryParameterException;
import xyz.baeuja.api.global.exception.NotFoundException;
import xyz.baeuja.api.global.response.page.PageInfo;
import xyz.baeuja.api.global.response.page.PagedResponse;
import xyz.baeuja.api.learning.dto.content.LearningContentDto;
import xyz.baeuja.api.learning.dto.content.LearningAllContentsResponse;
import xyz.baeuja.api.learning.dto.unit.LearningUnitResponse;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LearningService {

    private final ContentRepository contentRepository;
    private final ContentQueryRepository contentQueryRepository;
    private final UnitQueryRepository unitQueryRepository;
    private final SentenceQueryRepository sentenceQueryRepository;

    /**
     * 학습 컨텐츠 리스트를 분류 별로 조회
     *
     * @param pageSize 각 분류별 콘텐츠 개수
     * @return LearningContentsResponse
     */
    public LearningAllContentsResponse findAllLearningContents(Long userId, int pageSize) {
        Map<Classification, List<LearningContentDto>> map = new EnumMap<>(Classification.class);

        for (Classification classification : Classification.values()) {
            Slice<LearningContentDto> learningContentDtoSlice = getLearningContentDtoSlice(userId, classification, 0, pageSize);
            map.put(classification, learningContentDtoSlice.getContent());
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
    public PagedResponse<LearningContentDto> findLearningContents(Long userId, Classification classification, int pageNumber, int pageSize) {
        Slice<LearningContentDto> learningContentDtoSlice = getLearningContentDtoSlice(userId, classification, pageNumber, pageSize);
        return new PagedResponse<>(learningContentDtoSlice.getContent(), PageInfo.of(learningContentDtoSlice));
    }

    /**
     * 콘텐츠 상세 조회
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
     * 학습 유닛 리스트 조회
     *
     * @param userId         user id
     * @param contentId      content id
     * @param classification content classification
     * @return LearningUnitResponse list
     */
    public List<LearningUnitResponse> findUnits(Long userId, Long contentId, Classification classification)
            throws NotFoundException {
        List<LearningUnitResponse> learningUnits = unitQueryRepository.findLearningUnit(contentId, userId);

        if (learningUnits.isEmpty()) {
            throw new NotFoundException("Learning content not found.");
        }

        if (classification != Classification.POP) {
            learningUnits.forEach(
                    unit -> unit.setSentence(
                            sentenceQueryRepository.findSentenceWithBookmark(unit.getId(), userId)
                    )
            );
        }

        return learningUnits;
    }

    /**
     * Content 분류(classification)에 해당하는 LearningContentDto를 Slice에 담아서 반환
     *
     * @param userId         user id
     * @param classification content 분류
     * @param pageNumber     시작 페이지 번호
     * @param pageSize       페이지 크기
     * @return LearningContentDto slice
     * @throws InvalidQueryParameterException 유효하지 않은 pageNumber 또는 pageSize 값이 넘어올 때
     */
    private Slice<LearningContentDto> getLearningContentDtoSlice(Long userId, Classification classification, int pageNumber, int pageSize)
            throws InvalidQueryParameterException {
        try {
            return contentQueryRepository.findLearningContents(
                    userId,
                    classification,
                    PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt")
                    ));
        } catch (IllegalArgumentException e) {
            throw new InvalidQueryParameterException(e.getMessage());
        }
    }
}
