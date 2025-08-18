package xyz.baeuja.api.learning.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import xyz.baeuja.api.auth.security.CustomUserDetails;
import xyz.baeuja.api.content.domain.Classification;
import xyz.baeuja.api.content.dto.ContentDto;
import xyz.baeuja.api.global.response.ResultResponse;
import xyz.baeuja.api.global.response.page.PagedResponse;
import xyz.baeuja.api.learning.dto.content.LearningContentDto;
import xyz.baeuja.api.learning.dto.content.LearningAllContentsResponse;
import xyz.baeuja.api.learning.dto.unit.LearningUnitResponse;
import xyz.baeuja.api.learning.service.LearningService;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
@RequiredArgsConstructor
public class LearningApiController {

    private final LearningService learningService;

    /**
     * 전체 학습 콘텐츠 리스트
     *
     * @param size 각 분류별 콘텐츠 개수. default = 5.
     * @return data : LearningContentsResponse
     */
    @GetMapping("/contents/all")
    public ResponseEntity<ResultResponse<LearningAllContentsResponse>> contentsAll(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        LearningAllContentsResponse learningAllContentsResponse = learningService
                .findAllLearningContents(userDetails.getUserId(), size);
        return ResponseEntity.ok(ResultResponse.success(learningAllContentsResponse));
    }

    /**
     * 특정 분류에 해당하는 학습 콘텐츠 리스트
     *
     * @param classification 콘텐츠 분류
     * @param pageable       페이징 정보 (page, size)
     * @return PagedResponse<LearningContentDto>
     */
    @GetMapping("/contents")
    public ResponseEntity<ResultResponse<PagedResponse<LearningContentDto>>> contents(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam Classification classification,
            @PageableDefault(size = 5, page = 0) Pageable pageable
    ) {
        PagedResponse<LearningContentDto> pagedContents = learningService
                .findLearningContents(userDetails.getUserId(), classification, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(ResultResponse.success(pagedContents));
    }

    /**
     * 콘텐츠 상세 정보 조회
     *
     * @param contentId 조회할 content id
     * @return ContentDto
     */
    @GetMapping("/contents/{id}")
    public ResponseEntity<ResultResponse<ContentDto>> content(
            @PathVariable(name = "id") Long contentId
    ) {
        ContentDto contentDto = learningService.findContent(contentId);
        return ResponseEntity.ok(ResultResponse.success(contentDto));
    }

    /**
     * 학습 유닛 리스트 조회
     *
     * @param userDetails    인증된 사용자 정보. user id 조회.
     * @param contentId      content id
     * @param classification content classification
     * @return LearningUnitResponse list
     */
    @GetMapping("/contents/{id}/units")
    public ResponseEntity<ResultResponse<List<LearningUnitResponse>>> units(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable(name = "id") Long contentId,
            @RequestParam Classification classification
    ) {
        List<LearningUnitResponse> learningUnitResponses
                = learningService.findUnits(userDetails.getUserId(), contentId, classification);
        return ResponseEntity.ok(ResultResponse.success(learningUnitResponses));
    }
}
