package org.one.global.enums;

import org.springframework.http.HttpStatus;

/**
 * API 실패 응답에서 공통으로 사용하는 HTTP 상태, 코드, 메시지를 정의합니다.
 */
public enum ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "입력값이 올바르지 않습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "접근 권한이 없습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", "요청한 리소스를 찾을 수 없습니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "DUPLICATE_RESOURCE", "이미 존재하는 데이터입니다."),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "INVALID_DATE_RANGE", "날짜 범위가 올바르지 않습니다."),
    PHOTO_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "PHOTO_LIMIT_EXCEEDED", "사진은 최대 3장까지 업로드할 수 있습니다."),
    INVALID_OBJECT_KEY(HttpStatus.BAD_REQUEST, "INVALID_OBJECT_KEY", "유효하지 않은 파일 경로입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),
    DUPLICATE_STUDENT_ID(HttpStatus.CONFLICT, "DUPLICATE_STUDENT_ID", "이미 존재하는 학번입니다."),
    DUPLICATE_PHONE_NUMBER(HttpStatus.CONFLICT, "DUPLICATE_PHONE_NUMBER", "이미 존재하는 전화번호입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND", "존재하지 않는 부원입니다."),
    MAINPAGE_CONFIG_NOT_FOUND(HttpStatus.NOT_FOUND, "MAINPAGE_CONFIG_NOT_FOUND", "메인 페이지 설정 데이터가 db에 없습니다."),
    PRIVACY_POLICY_NOT_AGREED(HttpStatus.UNPROCESSABLE_CONTENT, "PRIVACY_POLICY_NOT_AGREED", "개인정보 수집 및 이용에 동의해야 회원가입이 가능합니다."),
    DUPLICATE_SELECT_MEMBER(HttpStatus.BAD_REQUEST, "DUPLICATE_SELECT_MEMBER", "중복되는 부원 선택이 있습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    /**
     * 에러 코드에 HTTP 상태, 문자열 코드, 기본 메시지를 함께 묶습니다.
     *
     * @param status  응답 HTTP 상태
     * @param code    클라이언트 분기용 문자열 코드
     * @param message 기본 에러 메시지
     */
    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    /**
     * 에러에 대응되는 HTTP 상태를 반환합니다.
     *
     * @return HTTP 상태
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * 프론트엔드에서 분기 처리할 문자열 코드를 반환합니다.
     *
     * @return 에러 코드 문자열
     */
    public String getCode() {
        return code;
    }

    /**
     * 에러 코드에 고정된 기본 메시지를 반환합니다.
     *
     * @return 에러 메시지
     */
    public String getMessage() {
        return message;
    }
}
