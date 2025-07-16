package xyz.baeuja.api.helper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentationConfigurer;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

public class RestDocsHelper {

    private final int port;
    private final RestAssuredRestDocumentationConfigurer configFilter;

    public RestDocsHelper(int port, RestAssuredRestDocumentationConfigurer configFilter) {
        this.port = port;
        this.configFilter = configFilter;
    }

    public RequestSpecification createSpecWithDocs(
            RestDocumentationFilter snippetFilter
    ) {
        return new RequestSpecBuilder()
                .setPort(port)
                .addHeader("Host", "api.baeuja.xyz")
                .addFilter(configFilter)
                .addFilter(snippetFilter)
                .build();
    }

    public static RestDocumentationFilter createRequestResponseSnippet(
            String identifier,
            FieldDescriptor[] requestFields,
            FieldDescriptor[] responseFields
    ) {
        return buildSnippet(
                identifier,
                requestFields(requestFields),
                responseFields(responseFields)
        );
    }

    public static RestDocumentationFilter createQueryResponseSnippet(
            String identifier,
            ParameterDescriptor[] queryParams,
            FieldDescriptor[] responseFields
    ) {
        return buildSnippet(
                identifier,
                queryParameters(queryParams),
                responseFields(responseFields)
        );
    }

    public static RestDocumentationFilter createAuthHeaderSnippet(
            String identifier,
            RequestHeadersSnippet header,
            FieldDescriptor[] responseFields
    ) {
        return buildSnippet(
                identifier,
                header,
                responseFields(responseFields)
        );
    }

    public static RestDocumentationFilter createResponseSnippet(
            String identifier,
            FieldDescriptor[] responseFields
    ) {
        return buildSnippet(
                identifier,
                responseFields(responseFields)
        );
    }

    public static FieldDescriptor[] buildResultResponseField(FieldDescriptor... dataFields) {
        FieldDescriptor[] defaultField = new FieldDescriptor[] {
                fieldWithPath("code").description("응답 코드"),
                fieldWithPath("message").description("응답 메시지"),
                fieldWithPath("data").description("응답 데이터")
        };

        if (dataFields == null || dataFields.length == 0) {
            return defaultField;
        }

        FieldDescriptor[] resultResponseField = new FieldDescriptor[defaultField.length + dataFields.length];
        System.arraycopy(defaultField, 0, resultResponseField, 0, defaultField.length);

        for (int i = 0; i < dataFields.length; i++) {
            FieldDescriptor fd = dataFields[i];

            resultResponseField[defaultField.length + i] = fieldWithPath("data." + fd.getPath())
                    .description(fd.getDescription())
                    .type(fd.getType());
        }
        return resultResponseField;
    }

    private static RestDocumentationFilter buildSnippet(String identifier, Snippet... snippets) {
        return document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                snippets
        );
    }
}