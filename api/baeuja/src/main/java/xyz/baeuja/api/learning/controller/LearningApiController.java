package xyz.baeuja.api.learning.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.baeuja.api.content.domain.Classification;
import xyz.baeuja.api.content.dto.ContentDto;
import xyz.baeuja.api.global.response.ResultResponse;
import xyz.baeuja.api.global.response.page.PagedResponse;
import xyz.baeuja.api.learning.dto.LearningContentDto;
import xyz.baeuja.api.learning.dto.LearningAllContentsResponse;
import xyz.baeuja.api.learning.service.LearningService;

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
    @GetMapping("/contents")
    public ResponseEntity<ResultResponse<LearningAllContentsResponse>> contents(
            @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        LearningAllContentsResponse learningAllContentsResponse = learningService.findAllLearningContents(size);
        return ResponseEntity.ok(ResultResponse.success(learningAllContentsResponse));
    }

    /**
     * 특정 분류에 해당하는 학습 콘텐츠 리스트
     *
     * @param classification 콘텐츠 분류
     * @param pageable 페이징 정보 (page, size)
     * @return PagedResponse<LearningContentDto>
     */
    @GetMapping("/contents/{classification}")
    public ResponseEntity<ResultResponse<PagedResponse<LearningContentDto>>> contentsByClassification(
            @PathVariable Classification classification,
            @PageableDefault(size = 5, page = 0) Pageable pageable
    ) {
        PagedResponse<LearningContentDto> pagedContents = learningService.findLearningContents(
                classification, pageable.getPageNumber(), pageable.getPageSize()
        );
        return ResponseEntity.ok(ResultResponse.success(pagedContents));
    }

//    @GetMapping("/contents/{id}")
//    public ResponseEntity<ResultResponse<ContentDto>> contentDetails
}
