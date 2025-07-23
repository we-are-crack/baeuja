package xyz.baeuja.api.docs;

import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class RestDocsSnippets {

    /**
     * 기본 응답 필드
     *
     * @return FieldDescriptor[] <br>
     * - fieldWithPath("code").description("응답 코드"),
     * - fieldWithPath("message").description("응답 메시지"),
     */
    public static FieldDescriptor[] defaultResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("code").description("응답 코드"),
                fieldWithPath("message").description("응답 메시지"),
        };
    }

    /**
     * 인증 헤더 스니펫
     *
     * @return RequestHeadersSnippet <br>
     * - headerWithName("Authorization").description("Bearer 인증 토큰")
     */
    public static RequestHeadersSnippet authorizationHeader() {
        return requestHeaders(
                headerWithName("Authorization").description("Bearer 인증 토큰")
        );
    }

    // ========================auth======================== //

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

    // ========================users======================== //

    /**
     * 사용자 정보 응답 필드 스니펫
     *
     * @return FieldDescriptor[] <br>
     * - fieldWithPath("email").description("사용자 이메일") <br>
     * - fieldWithPath("nickname").description("사용자 닉네임") <br>
     * - fieldWithPath("language").description("사용자 언어") <br>
     * - fieldWithPath("timezone").description("사용자 타임존") <br>
     * - fieldWithPath("loginType").description("로그인 타입 (GUEST, GOOGLE)")
     */
    public static FieldDescriptor[] getMyInfoResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("사용자 이메일"),
                fieldWithPath("nickname").description("사용자 닉네임"),
                fieldWithPath("language").description("사용자 언어"),
                fieldWithPath("timezone").description("사용자 타임존"),
                fieldWithPath("loginType").description("로그인 타입 (GUEST, GOOGLE)")
        };
    }

    // ========================home======================== //

    /**
     * 홈 최신 컨텐츠 응답 필드 스니펫
     *
     * @return FieldDescriptor[] <br>
     *- fieldWithPath("classification").description(" content 분류 ex)POP, DRAMA, MOVIE") <br>
     *- fieldWithPath("title").description("제목") <br>
     *- fieldWithPath("artist").description("가수") <br>
     *- fieldWithPath("director").description("감독") <br>
     *- fieldWithPath("thumbnailUrl").description("썸네일 주소") <br>
     *- fieldWithPath("unitCount").description("학습할 수 있는 유닛 수") <br>
     *- fieldWithPath("wordCount").description("학습할 수 있는 단어 수")
     */
    public static FieldDescriptor[] homeContentsResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("classification").description("content 분류 ex)POP, DRAMA, MOVIE"),
                fieldWithPath("title").description("제목"),
                fieldWithPath("artist").description("가수").optional(),
                fieldWithPath("director").description("감독").optional(),
                fieldWithPath("thumbnailUrl").description("썸네일 주소"),
                fieldWithPath("unitCount").description("학습할 수 있는 유닛 수"),
                fieldWithPath("wordCount").description("학습할 수 있는 단어 수")
        };
    }

    public static ParameterDescriptor[] excludeIdsRequest() {
        return new ParameterDescriptor[]{
                parameterWithName("excludeIds").description("이미 추천받은 단어 id 리스트").optional(),
        };
    }

    public static FieldDescriptor[] homeRecommendWordsResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("wordId").description("단어 id"),
                fieldWithPath("koreanWord").description("한국어 단어 원형"),
                fieldWithPath("importance").description("중요도 ex) A, B, C, D"),
                fieldWithPath("sentences").description("단어가 포함된 문장 리스트"),
                fieldWithPath("sentences[].unitId").description("문장이 포함된 유닛 id"),
                fieldWithPath("sentences[].unitThumbnailUrl").description("유닛 썸네일 주소"),
                fieldWithPath("sentences[].sentenceId").description("문장 id"),
                fieldWithPath("sentences[].koreanSentence").description("한국어 문장"),
                fieldWithPath("sentences[].englishSentence").description("영어 번역 문장"),
                fieldWithPath("sentences[].koreanWordInSentence").description("한국어 문장에 포함된 한국어 단어 모양"),
                fieldWithPath("sentences[].englishWordInSentence").description("영어 번역 문장에 포함된 영어 단어 모양")
        };
    }
}