package xyz.baeuja.api.home.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentationConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.auth.security.exception.InvalidJwtException;
import xyz.baeuja.api.content.cache.WordIdCache;
import xyz.baeuja.api.docs.RestDocsSnippets;
import xyz.baeuja.api.global.exception.ErrorCode;
import xyz.baeuja.api.global.util.jwt.JwtProvider;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.helper.RestDocsHelper;
import xyz.baeuja.api.helper.TestDataHelper;
import xyz.baeuja.api.user.domain.User;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.baeuja.api.docs.RestDocsSnippets.authorizationHeader;
import static xyz.baeuja.api.docs.RestDocsSnippets.defaultResponse;
import static xyz.baeuja.api.helper.RestDocsHelper.*;

@ActiveProfiles("test")
@Sql(scripts = {
        "/sql/truncate_all.sql",
        "/sql/content.sql",
        "/sql/unit.sql",
        "/sql/sentence.sql",
        "/sql/word.sql",
        "/sql/sentence_word.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class HomeApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestDataHelper dataHelper;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    WordIdCache wordIdCache;

    RestAssuredRestDocumentationConfigurer docConfig;
    RestDocsHelper docsHelper;
    User user;

    String nickname = "닉네임";
    String language = "ko";
    String timezone = "Asia/Seoul";

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        dataHelper.clearAll();
        docConfig = RestAssuredRestDocumentation.documentationConfiguration(provider);
        docsHelper = new RestDocsHelper(port, docConfig);
        user = dataHelper.saveGuestUser(nickname, language, timezone);
        wordIdCache.init();
    }

    @Test
    @DisplayName("최신 콘텐츠 조회 성공")
    void getContents_success() {
        String accessToken = jwtProvider.createAccessToken(new JwtUserInfo(user.getId(), user.getTimezone(), user.getRole()));

        RequestSpecification spec = docsHelper.createSpecWithDocs(createAuthHeaderSnippet(
                "home-get-contents-success",
                RestDocsSnippets.authorizationHeader(),
                buildListResultResponseFields(RestDocsSnippets.homeContentsResponse())
        ));

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/home/contents");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.classification")).hasSize(10);
        assertThat(response.jsonPath().getString("data[].classification")).isNotBlank();
        assertThat(response.jsonPath().getString("data[].title")).isNotBlank();
        assertThat(response.jsonPath().getString("data[].thumbnail")).isNotBlank();
        assertThat(response.jsonPath().getInt("data[0].unitCount")).isNotZero();
        assertThat(response.jsonPath().getInt("data[0].wordCount")).isNotZero();
    }

    @Test
    @DisplayName("최신 콘텐츠 조회 실패 - 인증 헤더 누락")
    void getContents_fail_missing_auth_header() {
        RequestSpecification spec = docsHelper.createSpecWithDocs(createResponseSnippet(
                "home-get-contents-fail_missing",
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .when()
                .get("/api/home/contents");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(InvalidJwtException.CODE);
    }

    @Test
    @DisplayName("최신 콘텐츠 조회 실패 - 유효하지 않은 토큰")
    void getContents_fail_invalid_token() {
        RequestSpecification spec = docsHelper.createSpecWithDocs(createAuthHeaderSnippet(
                "home-get-contents-fail_invalid",
                authorizationHeader(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer asdasdad")
                .when()
                .get("/api/home/contents");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(InvalidJwtException.CODE);
    }

    @Test
    @DisplayName("추천 학습 단어 조회 성공 - 쿼피 파리미터 생략")
    void getWords_success() {
        String accessToken = jwtProvider.createAccessToken(new JwtUserInfo(user.getId(), user.getTimezone(), user.getRole()));

        RequestSpecification spec = docsHelper.createSpecWithDocs(createAuthHeaderSnippet(
                "home-get-words-success",
                RestDocsSnippets.authorizationHeader(),
                buildListResultResponseFields(RestDocsSnippets.homeRecommendWordsResponse())
        ));

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/home/words");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.wordId")).hasSize(5);
        assertThat(response.jsonPath().getInt("data[0].wordId")).isNotZero();
        assertThat(response.jsonPath().getString("data[0].koreanWord")).isNotBlank();
        assertThat(response.jsonPath().getInt("data[0].sentences[0].unitId")).isNotZero();
    }

    @Test
    @DisplayName("추천 학습 단어 조회 성공 - 쿼피 파리미터 추가")
    void getWords_success_exclude() {
        String accessToken = jwtProvider.createAccessToken(new JwtUserInfo(user.getId(), user.getTimezone(), user.getRole()));

        List<Long> excludeIds = LongStream.rangeClosed(0, 10).boxed().toList();

        RequestSpecification spec = docsHelper.createSpecWithDocs(createQueryResponseWithAuthSnippet(
                "home-get-words-success-exclude",
                RestDocsSnippets.authorizationHeader(),
                RestDocsSnippets.excludeIdsRequestParam(),
                buildListResultResponseFields(RestDocsSnippets.homeRecommendWordsResponse())
        ));

        Response response = RestAssured
                .given(spec)
                .queryParam("excludeIds", excludeIds)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/home/words");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.wordId")).hasSize(5);
        assertThat(response.jsonPath().getInt("data[0].wordId")).isNotZero();
        assertThat(response.jsonPath().getString("data[0].koreanWord")).isNotBlank();
        assertThat(response.jsonPath().getInt("data[0].sentences[0].unitId")).isNotZero();
    }

    @Test
    @DisplayName("추천 학습 단어 조회 실패 - 쿼리 파라미터 타입 불일치")
    void getWords_fail_invalid_query_param() {
        String accessToken = jwtProvider.createAccessToken(new JwtUserInfo(user.getId(), user.getTimezone(), user.getRole()));

        String excludeIds = "1&2&3&4&5";

        RequestSpecification spec = docsHelper.createSpecWithDocs(createQueryResponseWithAuthSnippet(
                "home-get-words-fail-invalid-query-param",
                RestDocsSnippets.authorizationHeader(),
                RestDocsSnippets.excludeIdsRequestParam(),
                defaultResponse()
        ));

        Response response = RestAssured
                .given(spec)
                .queryParam("excludeIds", excludeIds)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/home/words");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(ErrorCode.INVALID_QUERY_PARAMETER_TYPE.name());
    }
}