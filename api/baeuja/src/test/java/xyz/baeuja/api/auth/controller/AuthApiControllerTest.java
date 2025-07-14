package xyz.baeuja.api.auth.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import xyz.baeuja.api.helper.TestDataHelper;
import xyz.baeuja.api.user.domain.LoginType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestDataHelper helper;

    String email = "test@test.com";
    String nickname = "닉네임";
    String language = "ko";
    String timezone = "Asia/Seoul";

    @BeforeEach
    void setUp() {
        helper.clearAll();
        RestAssured.port = port;
    }

    @Test
    @DisplayName("로그인 성공")
    void signIn_success() {
        helper.saveGoogleUser(email, nickname, language, timezone);

        String requestBody = getRequestBodyJson(email);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/sign-in");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("data.accessToken")).isNotNull();
    }

    @Test
    @DisplayName("GUEST 회원 가입 성공")
    void signUp_guest_success() {
        String requestBody = getRequestBodyJson(nickname, language, timezone, LoginType.GUEST);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/sign-up");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getString("data.accessToken")).isNotNull();
    }

    @Test
    @DisplayName("GOOGLE 회원 가입 성공")
    void signUp_google_success() {
        String requestBody = getRequestBodyJson(email, nickname, language, timezone, LoginType.GUEST);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/sign-up");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getString("data.accessToken")).isNotNull();
    }

    @Test
    @DisplayName("닉네임 유효성 검사 성공")
    void checkNickname_success() {
        Response response = RestAssured
                .given()
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

        Response response = RestAssured
                .given()
                .queryParam("nickname", nickname)
                .when()
                .get("/api/auth/check-nickname");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    private String getRequestBodyJson(String email) {
        return String.format("""
            {
              "email": "%s"
            }
            """, email);
    }

    private String getRequestBodyJson(String nickname, String language, String timezone, LoginType loginType) {
        return String.format("""
            {
              "nickname": "%s",
              "language": "%s",
              "timezone": "%s",
              "loginType": "%s"
            }
            """, nickname, language, timezone, loginType.name());
    }

    private String getRequestBodyJson(String email, String nickname, String language, String timezone, LoginType loginType) {
        return String.format("""
            {
              "email": "%s",
              "nickname": "%s",
              "language": "%s",
              "timezone": "%s",
              "loginType": "%s"
            }
            """, email, nickname, language, timezone, loginType.name());
    }
}