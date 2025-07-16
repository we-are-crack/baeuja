package xyz.baeuja.api.auth.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import xyz.baeuja.api.auth.exception.*;
import xyz.baeuja.api.global.util.jwt.JwtProvider;
import xyz.baeuja.api.global.util.jwt.JwtUserInfo;
import xyz.baeuja.api.helper.RestDocsHelper;
import xyz.baeuja.api.helper.TestDataHelper;
import xyz.baeuja.api.user.domain.LoginType;
import xyz.baeuja.api.user.domain.Role;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.baeuja.api.docs.RestDocsSnippets.*;
import static xyz.baeuja.api.helper.RequestBodyBuilder.buildRequestBody;
import static xyz.baeuja.api.helper.RestDocsHelper.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class AuthApiControllerTest {

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
    @DisplayName("로그인 성공")
    void signIn_success() {
        helper.saveGoogleUser(email, nickname, language, timezone);

        Map<String, String> requestBodyParams = new HashMap<>();
        requestBodyParams.put("email", email);
        String requestBody = buildRequestBody(requestBodyParams);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createRequestResponseSnippet(
                        "auth-sign-in-success",
                        signInRequest(),
                        buildResultResponseField(authData()))
        );

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/sign-in");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("data.accessToken")).isNotNull();
        assertThat(response.jsonPath().getString("data.refreshToken")).isNotNull();
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    void signIn_fail() {
        Map<String, String> requestBodyParams = new HashMap<>();
        requestBodyParams.put("email", email);
        String requestBody = buildRequestBody(requestBodyParams);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createRequestResponseSnippet(
                "auth-sign-in-fail",
                signInRequest(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/sign-in");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(UserNotFoundException.CODE);
    }

    @Test
    @DisplayName("GUEST 회원 가입 성공")
    void signUp_guest_success() {
        Map<String, String> requestBodyParams = new HashMap<>();
        requestBodyParams.put("language", language);
        requestBodyParams.put("timezone", timezone);
        requestBodyParams.put("loginType", LoginType.GUEST.name());
        String requestBody = buildRequestBody(requestBodyParams);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createRequestResponseSnippet(
                "auth-sign-up-guest-success",
                signUpGuestRequest(),
                buildResultResponseField(authData()))
        );

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/sign-up");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getString("data.accessToken")).isNotNull();
        assertThat(response.jsonPath().getString("data.refreshToken")).isNotNull();
    }

    @Test
    @DisplayName("GOOGLE 회원 가입 성공")
    void signUp_google_success() {
        Map<String, String> requestBodyParams = new HashMap<>();
        requestBodyParams.put("email", email);
        requestBodyParams.put("nickname", nickname);
        requestBodyParams.put("language", language);
        requestBodyParams.put("timezone", timezone);
        requestBodyParams.put("loginType", LoginType.GOOGLE.name());
        String requestBody = buildRequestBody(requestBodyParams);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createRequestResponseSnippet(
                "auth-sign-up-google-success",
                signUpGoogleRequest(),
                buildResultResponseField(authData()))
        );

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/sign-up");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getString("data.accessToken")).isNotNull();
        assertThat(response.jsonPath().getString("data.refreshToken")).isNotNull();
    }

    @Test
    @DisplayName("GOOGLE 회원 가입 실패 - 이메일 누락")
    void signUp_google_fail_missing() {
        Map<String, String> requestBodyParams = new HashMap<>();
        requestBodyParams.put("nickname", nickname);
        requestBodyParams.put("language", language);
        requestBodyParams.put("timezone", timezone);
        requestBodyParams.put("loginType", LoginType.GOOGLE.name());
        String requestBody = buildRequestBody(requestBodyParams);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createRequestResponseSnippet(
                "auth-sign-up-google-fail-missing",
                signUpGoogleRequestWithoutEmail(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/sign-up");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(InvalidSignUpRequestException.CODE);
    }

    @Test
    @DisplayName("GOOGLE 회원 가입 실패 - 중복 이메일")
    void signUp_google_fail_duplicate() {
        helper.saveGoogleUser(email, nickname, language, timezone);

        Map<String, String> requestBodyParams = new HashMap<>();
        requestBodyParams.put("email", email);
        requestBodyParams.put("nickname", nickname);
        requestBodyParams.put("language", language);
        requestBodyParams.put("timezone", timezone);
        requestBodyParams.put("loginType", LoginType.GOOGLE.name());
        String requestBody = buildRequestBody(requestBodyParams);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createRequestResponseSnippet(
                "auth-sign-up-google-fail-duplicate",
                signUpGoogleRequest(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/sign-up");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(DuplicateEmailException.CODE);
    }

    @Test
    @DisplayName("닉네임 유효성 검사 성공")
    void checkNickname_success() {
        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createQueryResponseSnippet(
                "auth-check-nickname-success",
                checkNicknameRequest(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .queryParam("nickname", nickname)
                .when()
                .get("/api/auth/check-nickname");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo("SUCCESS");
    }

    @Test
    @DisplayName("닉네임 유효성 검사 실패 - 중복")
    void checkNickname_fail_duplicate() {
        helper.saveGuestUser(nickname, language, timezone);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createQueryResponseSnippet(
                "auth-check-nickname-fail-duplicate",
                checkNicknameRequest(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .queryParam("nickname", nickname)
                .when()
                .get("/api/auth/check-nickname");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(DuplicateNicknameException.CODE);
    }

    @Test
    @DisplayName("닉네임 유효성 검사 실패 - 글자수 제한")
    void checkNickname_fail_length() {
        helper.saveGuestUser(nickname, language, timezone);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createQueryResponseSnippet(
                "auth-check-nickname-fail-length",
                checkNicknameRequest(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .queryParam("nickname", "q")
                .when()
                .get("/api/auth/check-nickname");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(InvalidNicknameException.CODE);
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void getRenewToken_success() {
        String expiredAccessToken = jwtProvider.createAccessToken(new JwtUserInfo(1L, timezone, Role.MEMBER), -1L);
        String refreshToken = jwtProvider.createRefreshToken(new JwtUserInfo(1L, timezone, Role.MEMBER));

        Map<String, String> requestBodyParams = new HashMap<>();
        requestBodyParams.put("accessToken", expiredAccessToken);
        requestBodyParams.put("refreshToken", refreshToken);
        String requestBody = buildRequestBody(requestBodyParams);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createRequestResponseSnippet(
                "auth-renew-token-success",
                authData(),
                buildResultResponseField(authData()))
        );

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/token");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("data.accessToken")).isNotNull();
        assertThat(response.jsonPath().getString("data.refreshToken")).isNotNull();
    }

    @Test
    @DisplayName("토큰 재발급 실패")
    void getRenewToken_fail() {
        String expiredAccessToken = jwtProvider.createAccessToken(new JwtUserInfo(1L, timezone, Role.MEMBER), -1L);
        String refreshToken = jwtProvider.createRefreshToken(new JwtUserInfo(2L, timezone, Role.MEMBER));

        Map<String, String> requestBodyParams = new HashMap<>();
        requestBodyParams.put("accessToken", expiredAccessToken);
        requestBodyParams.put("refreshToken", refreshToken);
        String requestBody = buildRequestBody(requestBodyParams);

        RequestSpecification spec = restDocsHelper.createSpecWithDocs(createRequestResponseSnippet(
                "auth-renew-token-fail",
                authData(),
                defaultResponse())
        );

        Response response = RestAssured
                .given(spec)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/token");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo("INVALID_TOKEN");
    }
}