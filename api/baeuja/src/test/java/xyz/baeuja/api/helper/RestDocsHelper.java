package xyz.baeuja.api.helper;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentationConfigurer;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import java.util.Arrays;

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

    public static RestDocumentationFilter createRequestBodyAndResponseSnippet(
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

    public static RestDocumentationFilter createQueryParamAndResponseSnippet(
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

    public static RestDocumentationFilter createQueryParamAndResponseSnippet(
            String identifier,
            RequestHeadersSnippet header,
            ParameterDescriptor[] queryParams,
            FieldDescriptor[] responseFields
    ) {
        return buildSnippet(
                identifier,
                header,
                queryParameters(queryParams),
                responseFields(responseFields)
        );
    }

    public static RestDocumentationFilter createPathParamAndResponseSnippet(
            String identifier,
            RequestHeadersSnippet header,
            ParameterDescriptor[] pathParams,
            FieldDescriptor[] responseFields
    ) {
        return buildSnippet(
                identifier,
                header,
                pathParameters(pathParams),
                responseFields(responseFields)
        );
    }

    public static RestDocumentationFilter createPathAndQueryParamAndResponseSnippet(
            String identifier,
            RequestHeadersSnippet header,
            ParameterDescriptor[] pathParams,
            ParameterDescriptor[] queryParams,
            FieldDescriptor[] responseFields
    ) {
        return buildSnippet(
                identifier,
                header,
                pathParameters(pathParams),
                queryParameters(queryParams),
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

    public static RestDocumentationFilter createResponseSnippet(
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

    public static FieldDescriptor[] buildSingleResultResponseFields(FieldDescriptor... dataFields) {
        return buildResultResponseFields("data.", dataFields);
    }

    public static FieldDescriptor[] buildListResultResponseFields(FieldDescriptor... dataFields) {
        return buildResultResponseFields("data[].", dataFields);
    }

    public static FieldDescriptor[] mergeFields(FieldDescriptor[]... arrays) {
        return Arrays.stream(arrays)
                .flatMap(Arrays::stream)
                .toArray(FieldDescriptor[]::new);
    }

    public static ParameterDescriptor[] mergeParameters(ParameterDescriptor[]... arrays) {
        return Arrays.stream(arrays)
                .flatMap(Arrays::stream)
                .toArray(ParameterDescriptor[]::new);
    }

    private static RestDocumentationFilter buildSnippet(String identifier, Snippet... snippets) {
        return document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                snippets
        );
    }

    private static FieldDescriptor[] buildResultResponseFields(String prefix, FieldDescriptor... dataFields) {
        FieldDescriptor[] defaultFields = new FieldDescriptor[]{
                fieldWithPath("code").description("응답 코드"),
                fieldWithPath("message").description("응답 메시지"),
                fieldWithPath("data").description("응답 데이터").optional()
        };

        FieldDescriptor[] combined = new FieldDescriptor[defaultFields.length + dataFields.length];
        System.arraycopy(defaultFields, 0, combined, 0, defaultFields.length);

        for (int i = 0; i < dataFields.length; i++) {
            FieldDescriptor fd = dataFields[i];

            FieldDescriptor newField = fieldWithPath(prefix + fd.getPath())
                    .description(fd.getDescription())
                    .type(fd.getType());

            if (fd.isOptional()) {
                newField.optional();
            }

            combined[defaultFields.length + i] = newField;
        }

        return combined;
    }
}