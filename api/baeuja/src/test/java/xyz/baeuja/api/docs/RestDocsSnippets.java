package xyz.baeuja.api.docs;

import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import xyz.baeuja.api.content.domain.Classification;

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

    public static FieldDescriptor[] signInRequestBody() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("사용자 이메일").optional(),
        };
    }

    public static FieldDescriptor[] signUpGuestRequestBody() {
        return new FieldDescriptor[]{
                fieldWithPath("language").description("사용자 언어"),
                fieldWithPath("timezone").description("사용자 타임존"),
                fieldWithPath("loginType").description("로그인 타입 (GUEST, GOOGLE)")
        };
    }

    public static FieldDescriptor[] signUpGoogleRequestBody() {
        return new FieldDescriptor[]{
                fieldWithPath("email").description("사용자 이메일"),
                fieldWithPath("nickname").description("사용자 닉네임"),
                fieldWithPath("language").description("사용자 언어"),
                fieldWithPath("timezone").description("사용자 타임존"),
                fieldWithPath("loginType").description("로그인 타입 (GUEST, GOOGLE)")
        };
    }

    public static FieldDescriptor[] signUpGoogleRequestBodyWithoutEmail() {
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

    public static ParameterDescriptor[] checkNicknameRequestParam() {
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

    public static ParameterDescriptor[] excludeIdsRequestParam() {
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

    // ========================learning======================== //

    public static ParameterDescriptor[] learningAllContentListQueryParam(String description) {
        return new ParameterDescriptor[]{
                parameterWithName("size").description("콘텐츠 분류 별 개수. \n" + description).optional(),
        };
    }

    public static FieldDescriptor[] learningAllContentListResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("pop[]").description("POP 콘텐츠 리스트"),
                fieldWithPath("movie[]").description("MOVIE 콘텐츠 리스트"),
                fieldWithPath("drama[]").description("DRAMA 콘텐츠 리스트"),
                fieldWithPath("pop[].id").description("content id"),
                fieldWithPath("pop[].classification").description("content 분류 ex)POP, DRAMA, MOVIE"),
                fieldWithPath("pop[].title").description("제목"),
                fieldWithPath("pop[].artist").description("가수"),
                fieldWithPath("pop[].thumbnailUrl").description("썸네일 주소"),
                fieldWithPath("pop[].progressRate").description("학습률(0 ~ 100)"),
                fieldWithPath("movie[].id").description("content id"),
                fieldWithPath("movie[].classification").description("content 분류 ex)POP, DRAMA, MOVIE"),
                fieldWithPath("movie[].title").description("제목"),
                fieldWithPath("movie[].director").description("영화 감독"),
                fieldWithPath("movie[].thumbnailUrl").description("썸네일 주소"),
                fieldWithPath("movie[].progressRate").description("학습률(0 ~ 100)"),
                fieldWithPath("drama[].id").description("content id"),
                fieldWithPath("drama[].classification").description("content 분류 ex)POP, DRAMA, MOVIE"),
                fieldWithPath("drama[].title").description("제목"),
                fieldWithPath("drama[].director").description("드라마 감독"),
                fieldWithPath("drama[].thumbnailUrl").description("썸네일 주소"),
                fieldWithPath("drama[].progressRate").description("학습률(0 ~ 100)"),

        };
    }

    public static ParameterDescriptor[] learningContentListPathParam(String description) {
        return new ParameterDescriptor[]{
                parameterWithName("classification").description("콘텐츠 분류 (POP or MOVIE or DRAMA). \n" + description)
        };
    }

    public static FieldDescriptor[] learningContentListResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("content[]").description("페이징 된 콘텐츠 리스트")
        };
    }

    public static FieldDescriptor[] learningContentListResponse(Classification classification) {
        FieldDescriptor optionalfieldDescriptor;

        if (classification == Classification.POP) {
            optionalfieldDescriptor = fieldWithPath("content[].artist").description("가수");
        } else {
            optionalfieldDescriptor = fieldWithPath("content[].director").description("감독");
        }

        return new FieldDescriptor[]{
                fieldWithPath("content[]").description("페이징 된 콘텐츠 리스트"),
                fieldWithPath("content[].id").description("content id"),
                fieldWithPath("content[].classification").description("content 분류 ex)POP, DRAMA, MOVIE"),
                fieldWithPath("content[].title").description("제목"),
                optionalfieldDescriptor,
                fieldWithPath("content[].thumbnailUrl").description("썸네일 주소"),
                fieldWithPath("content[].progressRate").description("학습률(0 ~ 100)"),
        };
    }

    // ========================paging======================== //
    public static ParameterDescriptor[] pagingQueryParam() {
        return new ParameterDescriptor[]{
                parameterWithName("page").description("페이지 시작 번호"),
                parameterWithName("size").description("페이지 크기")
        };
    }

    public static FieldDescriptor[] pageInfoResponse() {
        return new FieldDescriptor[]{
                fieldWithPath("pageInfo").description("페이지 정보"),
                fieldWithPath("pageInfo.pageNumber").description("페이지 번호"),
                fieldWithPath("pageInfo.pageSize").description("페이지 크기"),
                fieldWithPath("pageInfo.hasNext").description("다음 페이지 존재 여부"),
                fieldWithPath("pageInfo.hasPrevious").description("이전 페이지 존재 여부"),
        };
    }
}
