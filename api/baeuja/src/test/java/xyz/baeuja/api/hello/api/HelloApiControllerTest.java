package xyz.baeuja.api.hello.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
class HelloApiControllerTest {

    @LocalServerPort
    private int port;

    private Filter configFilter; // 공통 config 필터

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider) {
        // 공통 config 필터 (snippets 경로 등 설정)
        this.configFilter = RestAssuredRestDocumentation.documentationConfiguration(provider);
    }

    /**
     * 재사용 가능한 RequestSpecification 생성
     */
    private RequestSpecification createSpecWithDocs(RestDocumentationFilter snippetFilter) {
        return new RequestSpecBuilder()
                .setPort(port)
                .addFilter(configFilter)
                .addFilter(snippetFilter) // 문서화용 필터만 테스트마다 다르게 주입
                .build();
    }

    /**
     * 재사용 가능한 문서 필터 생성
     */
    private RestDocumentationFilter createHelloDocFilter(String identifier) {
        return document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                        parameterWithName("name").description("이름")
                ),
                responseFields(
                        fieldWithPath("data.name").description("요청한 이름"),
                        fieldWithPath("data.greeting").description("인사말")
                )
        );
    }

    private RestDocumentationFilter createHelloV2DocFilter(String identifier) {
        return document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("name").description("이름")
                ),
                responseFields(
                        fieldWithPath("data.name").description("요청한 이름"),
                        fieldWithPath("data.greeting").description("인사말")
                )
        );
    }

    @Test
    @DisplayName("GET /hello API - name 파라미터를 받아서 응답을 반환한다.")
    void 문서화테스트() {
        RequestSpecification spec = createSpecWithDocs(createHelloDocFilter("hello-get"));

        Response response = RestAssured
                .given(spec)
                    .header("Host", "api.baeuja.xyz")
                    .queryParam("name", "crack")
                .when()
                    .get("/hello");

        assertThat(response.getStatusCode()).isEqualTo(200);
        String name = response.jsonPath().getString("data.name");
        String greeting = response.jsonPath().getString("data.greeting");

        assertThat(name).isEqualTo("crack");
        assertThat(greeting).isEqualTo("Hello, crack");
    }

    @Test
    @DisplayName("GET /hello API - request body 에 name 필드를 받아서 응답한다.")
    void 문서화테스트_V2() {
        RequestSpecification spec = createSpecWithDocs(createHelloV2DocFilter("hello-post"));

        String requestBody = """
                {
                    "name" : "crack"
                }
                """;

        Response response = RestAssured
                .given(spec)
                    .header("Host", "api.baeuja.xyz")
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                .when()
                    .post("/hello");

        assertThat(response.getStatusCode()).isEqualTo(200);
        String name = response.jsonPath().getString("data.name");
        String greeting = response.jsonPath().getString("data.greeting");

        assertThat(name).isEqualTo("crack");
        assertThat(greeting).isEqualTo("Hello, crack");
    }
}