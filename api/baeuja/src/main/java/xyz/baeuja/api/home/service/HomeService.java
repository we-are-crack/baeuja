package xyz.baeuja.api.home.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.baeuja.api.content.cache.WordIdCache;
import xyz.baeuja.api.content.domain.Word;
import xyz.baeuja.api.content.repository.jpa.WordJpaRepository;
import xyz.baeuja.api.content.repository.query.ContentQueryRepository;
import xyz.baeuja.api.home.dto.HomeContentsResponse;
import xyz.baeuja.api.home.dto.HomeRecommendWordsResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final ContentQueryRepository contentRepository;
    private final WordJpaRepository wordRepository;
    private final WordIdCache wordIdCache;

    /**
     * 최근 추가된 content 조회
     *
     * @return HomeContentResponse list
     */
    public List<HomeContentsResponse> getLatestContents() {
        return contentRepository.findHomeContents(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "updatedAt")));
    }

    /**
     * 사용자에게 학습 추천할 랜덤 단어 5개.
     *
     * @param excludeIds 이미 추천받은 단어. 제외할 단어 id list.
     * @return HomeRecommendWordResponse list
     */
    public List<HomeRecommendWordsResponse> getRecommendWords(List<Long> excludeIds) {
        final List<Long> safeExcludeIds = (excludeIds == null) ? List.of() : excludeIds;
        int wordsSize = 5;

        List<Long> wordIds = wordIdCache.getIds();

        List<Long> filteredIds = new ArrayList<>(
                wordIds.stream()
                        .filter(id -> !safeExcludeIds.contains(id))
                        .toList()
        );

        Collections.shuffle(filteredIds);

        List<Long> includeIds = filteredIds.stream()
                .limit(wordsSize)
                .toList();

        List<Word> findWords = wordRepository.findAllInIds(includeIds)
                .orElse(new ArrayList<>());

        return findWords.stream()
                .map(w -> HomeRecommendWordsResponse.from(w))
                .toList();
    }
}
