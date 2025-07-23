package xyz.baeuja.api.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.baeuja.api.global.response.ResultResponse;
import xyz.baeuja.api.home.dto.HomeContentsResponse;
import xyz.baeuja.api.home.dto.HomeRecommendWordsResponse;
import xyz.baeuja.api.home.service.HomeService;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeApiController {

    private final HomeService service;
    private final HomeService homeService;

    /**
     * 최근 추가된 content 10개 응답
     *
     * @return data : HomeContentResponse list
     */
    @GetMapping("/contents")
    public ResponseEntity<ResultResponse<List<HomeContentsResponse>>> getContents() {
        List<HomeContentsResponse> latestContents = service.getLatestContents();
        return ResponseEntity.ok(ResultResponse.success(latestContents));
    }

    /**
     * 학습 단어 랜덤 추천
     *
     * @param excludeIds 쿼리 파라미터로 전달할 이미 추천받은 단어 id list
     * @return data : HomeRecommendWordResponse list
     */
    @GetMapping("/words")
    public ResponseEntity<ResultResponse<List<HomeRecommendWordsResponse>>> getWords(
            @RequestParam(name = "excludeIds", required = false) List<Long> excludeIds) {
        List<HomeRecommendWordsResponse> recommendWords = homeService.getRecommendWords(excludeIds);
        return ResponseEntity.ok(ResultResponse.success(recommendWords));
    }
}
