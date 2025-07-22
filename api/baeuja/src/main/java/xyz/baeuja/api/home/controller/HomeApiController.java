package xyz.baeuja.api.home.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.baeuja.api.global.response.ResultResponse;
import xyz.baeuja.api.home.dto.HomeContentResponse;
import xyz.baeuja.api.home.service.HomeService;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeApiController {

    private final HomeService service;

    /**
     * 최근 추가된 content 10개 응답
     *
     * @return HomeContentResponse list
     */
    @GetMapping("/contents")
    public ResponseEntity<ResultResponse<List<HomeContentResponse>>> getContents() {
        List<HomeContentResponse> latestContents = service.getLatestContents();
        return ResponseEntity.ok(ResultResponse.success(latestContents));
    }


}
