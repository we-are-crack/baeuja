package xyz.baeuja.api.user.controller;

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
import xyz.baeuja.api.user.exception.DuplicateEmailException;
import xyz.baeuja.api.user.exception.DuplicateNicknameException;
import xyz.baeuja.api.user.exception.InvalidNicknameException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestDataHelper helper;

    @BeforeEach
    void setUp() {
        helper.clearAll();
        RestAssured.port = port;
    }

    String nickname = "닉네임";
    String language = "ko";
    String timezone = "Asia/Seoul";

    @Test
    @DisplayName("게스트 회원가입 성공")
    void saveUser_guest() {
        LoginType loginType = LoginType.GUEST;
        String requestBody = getRequestBodyJson(nickname, language, timezone, loginType);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getString("data.email")).isNull();
        assertThat(response.jsonPath().getString("data.nickname")).isEqualTo(nickname);
        assertThat(response.jsonPath().getString("data.loginType")).isEqualTo(loginType.name());

    }

    @Test
    @DisplayName("구글(이메일) 회원가입 성공")
    void saveUser_google() {
        String email = "test@test.com";
        LoginType loginType = LoginType.GOOGLE;
        String requestBody = getRequestBodyJson(email, nickname, language, timezone, loginType);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getString("data.email")).isEqualTo(email);
        assertThat(response.jsonPath().getString("data.loginType")).isEqualTo(loginType.name());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복 이메일")
    void saveUser_fail() {
        String email = "test@test.com";
        helper.saveGoogleUser(email, nickname, language, timezone);

        String requestBody = getRequestBodyJson(email, nickname, language, timezone, LoginType.GOOGLE);

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(DuplicateEmailException.CODE);
    }

//    @Test
//    @DisplayName("Guest에서 구글로 계정 전환")

    @Test
    @DisplayName("닉네임 유효성 검사 성공")
    void checkNickname_success() {
        Response response = RestAssured
                .given()
                .queryParam("nickname", nickname)
                .when()
                .get("/api/users/check-nickname");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo("SUCCESS");
    }

    @Test
    @DisplayName("닉네임 유효성 검사 실패 - 중복 닉네임")
    void checkNickname_duplicate_nickname() {
        helper.saveGuestUser(nickname, language, timezone);

        Response response = RestAssured
                .given()
                .queryParam("nickname", nickname)
                .when()
                .get("/api/users/check-nickname");


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(DuplicateNicknameException.CODE);
    }

    @Test
    @DisplayName("닉네임 유효성 검사 실패 - 특수문자 닉네임")
    void checkNickname_invalid_nickname() {
        String invalidNickname = "!@#asdasd";

        Response response = RestAssured
                .given()
                .queryParam("nickname", invalidNickname)
                .when()
                .get("/api/users/check-nickname");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("code")).isEqualTo(InvalidNicknameException.CODE);
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