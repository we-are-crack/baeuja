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
import xyz.baeuja.api.global.exception.InvalidQueryParameterException;
import xyz.baeuja.api.global.util.jwt.JwtProvider;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.helper.RestDocsHelper;
import xyz.baeuja.api.helper.TestDataHelper;
import xyz.baeuja.api.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static xyz.baeuja.api.docs.RestDocsSnippets.*;
import static xyz.baeuja.api.helper.RestDocsHelper.buildSingleResultResponseFields;

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

    @Test
    @DisplayName("학습 콘텐츠 리스트 조회 성공")
    void list_success() {
        RequestSpecification spec = docsHelper.createSpecWithDocs(RestDocsHelper.createQueryResponseWithAuthSnippet(
                "learning-list-success",
                authorizationHeader(),
                learningContentsRequestParam("default size = 5"),
                buildSingleResultResponseFields(learningContentsResponse())
        ));

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/learning/contents");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.pop")).isNotEmpty();
        assertThat(response.jsonPath().getList("data.pop")).hasSize(5);
        assertThat(response.jsonPath().getList("data.movie")).isNotEmpty();
        assertThat(response.jsonPath().getList("data.drama")).isNotEmpty();
    }

    @Test
    @DisplayName("학습 콘텐츠 리스트 조회 성공 - size = 10")
    void list_success_size_10() {
        RequestSpecification spec = docsHelper.createSpecWithDocs(RestDocsHelper.createQueryResponseWithAuthSnippet(
                "learning-list-success-size-10",
                authorizationHeader(),
                learningContentsRequestParam("size = 10(최소 1개 이상)"),
                buildSingleResultResponseFields(learningContentsResponse())
        ));

        Response response = RestAssured
                .given(spec)
                .queryParam("size", 10)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/learning/contents");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("data.pop")).isNotEmpty();
        assertThat(response.jsonPath().getList("data.pop")).hasSize(10);
        assertThat(response.jsonPath().getList("data.movie")).isNotEmpty();
        assertThat(response.jsonPath().getList("data.drama")).isNotEmpty();
    }

    @Test
    @DisplayName("학습 콘텐츠 리스트 조회 실패 - size = 0")
    void list_success_size_0() {
        RequestSpecification spec = docsHelper.createSpecWithDocs(RestDocsHelper.createQueryResponseWithAuthSnippet(
                "learning-list-success-size-0",
                authorizationHeader(),
                learningContentsRequestParam("size = 0"),
                defaultResponse()
        ));

        Response response = RestAssured
                .given(spec)
                .queryParam("size", 0)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/learning/contents");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(InvalidQueryParameterException.CODE);
    }
}