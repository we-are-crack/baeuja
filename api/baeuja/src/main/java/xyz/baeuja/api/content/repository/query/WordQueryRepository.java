package xyz.baeuja.api.content.repository.query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.content.domain.Importance;
import xyz.baeuja.api.home.dto.HomeRecommendWordsResponse;
import xyz.baeuja.api.home.dto.SentenceSummaryDto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class WordQueryRepository {

    private final EntityManager em;




    /**
     * 홈 화면에서 뿌려줄 랜덤한 단어 리스트.
     * excludeIds 에 포함된 단어들을 제외하고, limit 개수 만큼 랜덤하게 단어를 조회
     *
     * @param excludeIds 제외할 단어 id 리스트
     * @param limit      조회할 단어 개수
     * @return HomeRecommendWordResponse list
     */
    @SuppressWarnings("unchecked")
    public List<HomeRecommendWordsResponse> findRandomWords(List<Long> excludeIds, int limit) {
        String queryString = "SELECT " +
                             "w.word_id AS word_id, w.korean AS word_korean, w.importance AS word_importance, " +
                             "u.unit_id AS unit_id, u.thumbnail_url AS unit_thumbnail_url, " +
                             "s.sentence_id AS sentence_id, s.korean AS korean_sentence, s.english AS english_sentence, " +
                             "sw.korean_word_in_sentence AS korean_word_in_sentence, " +
                             "sw.english_word_in_sentence AS english_word_in_sentence " +
                             "FROM word w " +
                             "JOIN sentence_word sw ON sw.word_id = w.word_id " +
                             "JOIN sentence s ON s.sentence_id = sw.sentence_id " +
                             "JOIN unit u ON u.unit_id = s.unit_id " +
                             (excludeIds != null && !excludeIds.isEmpty() ? " WHERE w.word_id NOT IN (:excludeIds)" : "") +
                             "ORDER BY RANDOM() " +
                             "LIMIT :limit";

        Query nativeQuery = em.createNativeQuery(queryString);

        if (excludeIds != null && !excludeIds.isEmpty()) {
            nativeQuery.setParameter("excludeIds", excludeIds);
        }

        List<Object[]> rows = nativeQuery
                .setParameter("limit", limit)
                .getResultList();

        return new ArrayList<>(mapToDto(rows).values());
    }

    /**
     * 조회한 쿼리 row list 를 HomeRecommendWordResponse DTO 객체로 매핑 후 Map 으로 반환
     *
     * @param rows 쿼리 조회 결과
     * @return Map<Long, HomeRecommendWordResponse>
     */
    private Map<Long, HomeRecommendWordsResponse> mapToDto(List<Object[]> rows) {
        Map<Long, HomeRecommendWordsResponse> resultMap = new LinkedHashMap<>();

        rows.forEach(row -> {
            Long wordId = ((Number) row[0]).longValue();
            Importance importance = Importance.valueOf((String) row[2]);
            String koreanWord = (String) row[1];

            SentenceSummaryDto sentence = extractSentenceFromRow(row);

            resultMap.computeIfAbsent(
                    wordId,
                    id -> new HomeRecommendWordsResponse(
                            wordId, koreanWord, importance, new ArrayList<>()
                    )
            ).getSentences().add(sentence);
        });

        return resultMap;
    }

    private SentenceSummaryDto extractSentenceFromRow(Object[] row) {
        Long unitId = ((Number) row[3]).longValue();
        String unitThumbnailUrl = (String) row[4];
        Long sentenceId = ((Number) row[5]).longValue();
        String koreanSentence = (String) row[6];
        String englishSentence = (String) row[7];
        String koreanWordInSentence = (String) row[8];
        String englishWordInSentence = (String) row[9];

        return new SentenceSummaryDto(
                unitId, unitThumbnailUrl,
                sentenceId, koreanSentence, englishSentence,
                koreanWordInSentence, englishWordInSentence
        );
    }
}
