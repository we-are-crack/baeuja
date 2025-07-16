package xyz.baeuja.api.docs;

import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class RestDocsSnippets {

    public static FieldDescriptor[] defaultResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("code").description("응답 코드"),
                fieldWithPath("message").description("응답 메시지"),
        };
    }

    public static FieldDescriptor[] signInRequest() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("사용자 이메일").optional(),
        };
    }

    public static FieldDescriptor[] signUpGuestRequest() {
        return new FieldDescriptor[]{
                fieldWithPath("language").description("사용자 언어"),
                fieldWithPath("timezone").description("사용자 타임존"),
                fieldWithPath("loginType").description("로그인 타입 (GUEST, GOOGLE)")
        };
    }

    public static FieldDescriptor[] signUpGoogleRequest() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("사용자 이메일"),
                fieldWithPath("nickname").description("사용자 닉네임"),
                fieldWithPath("language").description("사용자 언어"),
                fieldWithPath("timezone").description("사용자 타임존"),
                fieldWithPath("loginType").description("로그인 타입 (GUEST, GOOGLE)")
        };
    }

    public static FieldDescriptor[] signUpGoogleRequestWithoutEmail() {
        return new FieldDescriptor[]{
                fieldWithPath("nickname").description("사용자 닉네임"),
                fieldWithPath("language").description("사용자 언어"),
                fieldWithPath("timezone").description("사용자 타임존"),
                fieldWithPath("loginType").description("로그인 타입 (GUEST, GOOGLE)")
        };
    }

    public static FieldDescriptor[] getMyInfoResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("사용자 이메일"),
                fieldWithPath("nickname").description("사용자 닉네임"),
                fieldWithPath("language").description("사용자 언어"),
                fieldWithPath("timezone").description("사용자 타임존"),
                fieldWithPath("loginType").description("로그인 타입 (GUEST, GOOGLE)")
        };
    }

    public static FieldDescriptor[] authData() {
        return new FieldDescriptor[]{
                fieldWithPath("accessToken").description("Access Token"),
                fieldWithPath("refreshToken").description("Refresh Token")
        };
    }

    public static ParameterDescriptor[] checkNicknameRequest() {
        return new ParameterDescriptor[]{
                parameterWithName("nickname").description("검증할 닉네임").optional(),
        };
    }

    public static RequestHeadersSnippet authorizationHeader() {
        return requestHeaders(
                headerWithName("Authorization").description("Bearer 인증 토큰")
        );
    }
}