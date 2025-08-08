package xyz.baeuja.api.global.exception;

public enum ErrorCode {
    // 서버 에러
    INTERNAL_ERROR,

    // 인증, 인가 에러
    TOKEN_EXPIRED,
    INVALID_TOKEN,

    // 사용자 요청 에러
    INVALID_REQUEST_BODY,
    INVALID_SIGN_UP_REQUEST_BODY,
    INVALID_QUERY_PARAMETER,
    INVALID_QUERY_PARAMETER_TYPE,
    MISSING_QUERY_PARAMETER,
    INVALID_NICKNAME,


    // 리소스 에러
    USER_NOT_FOUND,
    DUPLICATE_EMAIL,
    DUPLICATE_NICKNAME

}
