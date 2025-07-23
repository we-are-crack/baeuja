package xyz.baeuja.api.home.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.content.repository.query.ContentQueryRepository;
import xyz.baeuja.api.home.dto.HomeContentResponse;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final ContentQueryRepository contentRepository;

    /**
     * 최근 추가된 content 조회
     *
     * @return HomeContentResponse list
     */
    public List<HomeContentResponse> getLatestContents() {
        return contentRepository.findTop10ByOrderByCreatedAtDesc();
    }


}
