package xyz.baeuja.api.learning.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.baeuja.api.global.response.ResultResponse;
import xyz.baeuja.api.learning.dto.LearningContentsResponse;
import xyz.baeuja.api.learning.service.LearningService;

@RestController
@RequestMapping("/api/learning")
@RequiredArgsConstructor
public class LearningApiController {

    private final LearningService learningService;

    /**
     * 학습 콘텐츠 리스트
     *
     * @param size 각 분류별 콘텐츠 개수. default = 5.
     * @return data : LearningContentsResponse
     */
    @GetMapping("/contents")
    public ResponseEntity<ResultResponse<LearningContentsResponse>> list(
            @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        LearningContentsResponse learningContentsResponse = learningService.findContents(size);
        return ResponseEntity.ok(ResultResponse.success(learningContentsResponse));
    }
}
