package xyz.baeuja.api.user.controller;

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
import xyz.baeuja.api.auth.security.exception.ExpiredTokenException;
import xyz.baeuja.api.auth.security.exception.InvalidJwtException;
import xyz.baeuja.api.global.util.jwt.JwtProvider;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.helper.RestDocsHelper;
import xyz.baeuja.api.helper.TestDataHelper;
import xyz.baeuja.api.user.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.baeuja.api.docs.RestDocsSnippets.*;
import static xyz.baeuja.api.helper.RestDocsHelper.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class UserApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestDataHelper helper;

    @Autowired
    JwtProvider jwtProvider;

    RestAssuredRestDocumentationConfigurer docConfig;
    RestDocsHelper restDocsHelper;

    String email = "test@test.com";
    String nickname = "닉네임";
    String language = "ko";
    String timezone = "Asia/Seoul";

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        helper.clearAll();
        docConfig = RestAssuredRestDocumentation.documentationConfiguration(provider);
        restDocsHelper = new RestDocsHelper(port, docConfig);
    }

    @Test
    @DisplayName("사용자 정보 조회 성공")
    void getMyInfo_success() {
        User user = helper.saveGoogleUser(email, nickname, language, timezone);

        String accessToken = jwtProvider.createAccessToken(new JwtUserInfo(user.getId(), user.getTimezone(), user.getRole()));

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(RestDocsHelper.createResponseSnippet(
                "users-get-my-info-success",
                authorizationHeader(),
                buildSingleResultResponseFields(getMyInfoResponse()))
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/users/me");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("data.email")).isEqualTo(email);
        assertThat(response.jsonPath().getString("data.nickname")).isEqualTo(nickname);
        assertThat(response.jsonPath().getString("data.language")).isEqualTo(language);
        assertThat(response.jsonPath().getString("data.timezone")).isEqualTo(timezone);
    }

    @Test
    @DisplayName("사용자 정보 조회 실패 - 인증 헤더 누락")
    void getMyInfo_fail_missing_auth_header() {
        helper.saveGoogleUser(email, nickname, language, timezone);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createResponseSnippet(
                "users-get-my-info-fail-missing",
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .when()
                .get("/api/users/me");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(InvalidJwtException.CODE);
    }

    @Test
    @DisplayName("사용자 정보 조회 실페 - 유효하지 않은 토큰")
    void getMyInfo_fail_invalid_token() {
        helper.saveGoogleUser(email, nickname, language, timezone);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(RestDocsHelper.createResponseSnippet(
                "users-get-my-info-fail-invalid",
                authorizationHeader(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer asdasdad")
                .when()
                .get("/api/users/me");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(InvalidJwtException.CODE);

    }

    @Test
    @DisplayName("사용자 정보 조회 실페 - 만료된 토큰")
    void getMyInfo_fail_expired_token() {
        User user = helper.saveGoogleUser(email, nickname, language, timezone);

        String accessToken = jwtProvider.createAccessToken(
                new JwtUserInfo(user.getId(), user.getTimezone(), user.getRole()),
                -1L);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(RestDocsHelper.createResponseSnippet(
                "users-get-my-info-fail-expired",
                authorizationHeader(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/users/me");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(ExpiredTokenException.CODE);
    }
}