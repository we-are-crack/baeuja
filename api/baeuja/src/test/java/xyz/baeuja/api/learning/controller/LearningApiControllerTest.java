package xyz.baeuja.api.learning.controller;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.content.domain.Classification;
import xyz.baeuja.api.global.exception.ErrorCode;
import xyz.baeuja.api.global.exception.InvalidQueryParameterException;
import xyz.baeuja.api.global.util.jwt.JwtProvider;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.helper.RestDocsHelper;
import xyz.baeuja.api.helper.TestDataHelper;
import xyz.baeuja.api.learning.dto.sentence.RepresentativeSentenceDto;
import xyz.baeuja.api.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.baeuja.api.docs.RestDocsSnippets.*;
import static xyz.baeuja.api.helper.RestDocsHelper.*;
import static xyz.baeuja.api.helper.RestDocsHelper.buildSingleResultResponseFields;
import static xyz.baeuja.api.helper.RestDocsHelper.mergeFields;

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
class LearningApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestDataHelper dataHelper;

    @Autowired
    JwtProvider jwtProvider;

    User user;
    String accessToken;

    RestDocsHelper docsHelper;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        dataHelper.clearAll();
        user = dataHelper.saveGuestUser();
        accessToken = jwtProvider.createAccessToken(new JwtUserInfo(user.getId(), user.getTimezone(), user.getRole()));
        docsHelper = new RestDocsHelper(port,
                RestAssuredRestDocumentation.documentationConfiguration(provider));
    }

    //===============================contentsAll api test===============================//

    @Test
    @DisplayName("학습 콘텐츠 리스트 조회 성공")
    void contentsAll_success() {
        RequestSpecification spec = docsHelper.createSpecWithDocs(createQueryParamAndResponseSnippet(
                "learning-contents-all-success",
                authorizationHeader(),
                learningContentsAllQueryParam("default size = 5"),
                buildSingleResultResponseFields(learningContentsAllResponse())
        ));

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/learning/contents/all");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.pop")).isNotEmpty();
        assertThat(response.jsonPath().getList("data.pop")).hasSize(5);
        assertThat(response.jsonPath().getList("data.movie")).isNotEmpty();
        assertThat(response.jsonPath().getList("data.drama")).isNotEmpty();
    }

    @Test
    @DisplayName("학습 콘텐츠 리스트 조회 성공 - size = 10")
    void contentsAll_success_size_10() {
        RequestSpecification spec = docsHelper.createSpecWithDocs(createQueryParamAndResponseSnippet(
                "learning-contents-all-success-size-10",
                authorizationHeader(),
                learningContentsAllQueryParam("size = 10(최소 1개 이상)"),
                buildSingleResultResponseFields(learningContentsAllResponse())
        ));

        Response response = RestAssured
                .given(spec)
                .queryParam("size", 10)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/learning/contents/all");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.pop")).isNotEmpty();
        assertThat(response.jsonPath().getList("data.pop")).hasSize(10);
        assertThat(response.jsonPath().getList("data.movie")).isNotEmpty();
        assertThat(response.jsonPath().getList("data.drama")).isNotEmpty();
    }

    @Test
    @DisplayName("학습 콘텐츠 리스트 조회 실패 - size = 0")
    void contentsAll_success_size_0() {
        RequestSpecification spec = docsHelper.createSpecWithDocs(createQueryParamAndResponseSnippet(
                "learning-contents-all-success-size-0",
                authorizationHeader(),
                learningContentsAllQueryParam("size = 0"),
                defaultResponse()
        ));

        Response response = RestAssured
                .given(spec)
                .queryParam("size", 0)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/learning/contents/all");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(InvalidQueryParameterException.CODE);
    }

    //===============================contents api test===============================//

    @Test
    @DisplayName("특정 분류 학습 콘텐츠 리스트 조회 성공")
    void contents_success() {
        Classification classification = Classification.POP;

        RequestSpecification spec = docsHelper.createSpecWithDocs(createQueryParamAndResponseSnippet(
                "learning-contents-success",
                authorizationHeader(),
                learningContentsQueryParam("classification = pop"),
                buildSingleResultResponseFields(mergeFields(learningContentsResponse(classification), pageInfoResponse())))
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("classification", classification)
                .when()
                .get("/api/learning/contents");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.content")).isNotEmpty().hasSize(5);
        assertThat(response.jsonPath().getInt("data.pageInfo.pageNumber")).isZero();
        assertThat(response.jsonPath().getInt("data.pageInfo.pageSize")).isEqualTo(5);
    }

    @Test
    @DisplayName("특정 분류 학습 콘텐츠 리스트 조회 성공 - 페이지 번호 지정")
    void contents_success_page_1() {
        Classification classification = Classification.POP;
        int page = 1;

        RequestSpecification spec = docsHelper.createSpecWithDocs(createQueryParamAndResponseSnippet(
                "learning-contents-page-1",
                authorizationHeader(),
                mergeParameters(learningContentsQueryParam("classification = pop"), pagingQueryParam()),
                buildSingleResultResponseFields(mergeFields(learningContentsResponse(classification), pageInfoResponse())))
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("classification", classification)
                .queryParam("page", page)
                .queryParam("size", 5)
                .when()
                .get("/api/learning/contents");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.content")).isNotEmpty().hasSize(5);
        assertThat(response.jsonPath().getInt("data.pageInfo.pageNumber")).isEqualTo(page);
        assertThat(response.jsonPath().getInt("data.pageInfo.pageSize")).isEqualTo(5);
    }

    @Test
    @DisplayName("특정 분류 학습 콘텐츠 리스트 조회 성공 - 초괴 페이지 지정 시 빈 리스트 반환")
    void contents_success_page_10000() {
        Classification classification = Classification.POP;
        int page = 10000;

        RequestSpecification spec = docsHelper.createSpecWithDocs(createQueryParamAndResponseSnippet(
                "learning-contents-page-10000",
                authorizationHeader(),
                mergeParameters(learningContentsQueryParam("classification = pop"), pagingQueryParam()),
                buildSingleResultResponseFields(mergeFields(learningContentsResponse(), pageInfoResponse())))
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("classification", classification)
                .queryParam("page", page)
                .queryParam("size", 5)
                .when()
                .get("/api/learning/contents");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.content")).isEmpty();
    }

    @Test
    @DisplayName("특정 분류 학습 콘텐츠 리스트 조회 실패 - 잘못된 classification")
    void contents_fail_invalid_path_param() {
        String classification = "invalid";

        RequestSpecification spec = docsHelper.createSpecWithDocs(createQueryParamAndResponseSnippet(
                "learning-contents-fail-invalid-path-param",
                authorizationHeader(),
                learningContentsQueryParam("classification = invalid"),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("classification", classification)
                .when()
                .get("/api/learning/contents");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(ErrorCode.INVALID_PATH_PARAMETER.name());
    }

    //===============================content api test===============================//

    @Test
    @DisplayName("콘텐츠 상세 조회 성공")
    void content_success() {
        Long contentId = 1L;

        RequestSpecification spec = docsHelper.createSpecWithDocs(createPathParamAndResponseSnippet(
                "learning-content-success",
                authorizationHeader(),
                learningContentPathParam(),
                buildSingleResultResponseFields(learningContentResponse(Classification.POP))
        ));

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/learning/contents/{id}", contentId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong("data.id")).isEqualTo(contentId);
    }

    @Test
    @DisplayName("콘텐츠 상세 조회 실패 - NotFound")
    void content_fail() {
        Long contentId = 0L;

        RequestSpecification spec = docsHelper.createSpecWithDocs(createPathParamAndResponseSnippet(
                "learning-content-fail",
                authorizationHeader(),
                learningContentPathParam(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/learning/contents/{id}", contentId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    //===============================units api test===============================//

    @Test
    @DisplayName("학습 유닛 리스트 조회 성공 - POP")
    void units_pop_success() {
        Long contentId = 1L;
        Classification classification = Classification.POP;

        RequestSpecification spec = docsHelper.createSpecWithDocs(createPathAndQueryParamAndResponseSnippet(
                "learning-units-pop-success",
                authorizationHeader(),
                learningContentPathParam(),
                learningContentsQueryParam("classification = POP"),
                buildListResultResponseFields(learningUnitsResponse(classification))
        ));

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("classification", classification)
                .when()
                .get("/api/learning/contents/{id}/units", contentId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data")).isNotEmpty();
        assertThat(response.jsonPath().getString("data[0].lastLearned")).isEmpty();
        assertThat(response.jsonPath().getObject("data[0].sentence", RepresentativeSentenceDto.class)).isNull();
    }

    @Test
    @DisplayName("학습 유닛 리스트 조회 성공 - DRAMA")
    void units_drama_success() {
        Long contentId = 11L;
        Classification classification = Classification.DRAMA;

        RequestSpecification spec = docsHelper.createSpecWithDocs(createPathAndQueryParamAndResponseSnippet(
                "learning-units-drama-success",
                authorizationHeader(),
                learningContentPathParam(),
                learningContentsQueryParam("classification = DRAMA"),
                buildListResultResponseFields(learningUnitsResponse(classification))
        ));

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("classification", classification)
                .when()
                .get("/api/learning/contents/{id}/units", contentId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data")).isNotEmpty();
        assertThat(response.jsonPath().getString("data[0].lastLearned")).isEmpty();
        assertThat(response.jsonPath().getObject("data[0].sentence", RepresentativeSentenceDto.class)).isNotNull();
        assertThat(response.jsonPath().getBoolean("data[0].sentence.isBookmark")).isFalse();
    }

    @Test
    @DisplayName("학습 유닛 리스트 조회 실패 - NotFound")
    void units_fail_not_found() {
        Long contentId = 0L;
        Classification classification = Classification.DRAMA;

        RequestSpecification spec = docsHelper.createSpecWithDocs(createPathAndQueryParamAndResponseSnippet(
                "learning-units-fail-not-found",
                authorizationHeader(),
                learningContentPathParam(),
                learningContentsQueryParam("classification = DRAMA"),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("classification", classification)
                .when()
                .get("/api/learning/contents/{id}/units", contentId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("학습 유닛 리스트 조회 실패 - 잘못된 classification")
    void units_fail_invalid_classification() {
        Long contentId = 1L;
        String classification = "invalid";

        RequestSpecification spec = docsHelper.createSpecWithDocs(createPathAndQueryParamAndResponseSnippet(
                "learning-units-fail-invalid-classification",
                authorizationHeader(),
                learningContentPathParam(),
                learningContentsQueryParam("classification = invalid"),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("classification", classification)
                .when()
                .get("/api/learning/contents/{id}/units", contentId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(ErrorCode.INVALID_PATH_PARAMETER.name());
    }
}