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
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	/**
	 * 에러 코드에 HTTP 상태, 문자열 코드, 기본 메시지를 함께 묶습니다.
	 *
	 * @param status 응답 HTTP 상태
	 * @param code 클라이언트 분기용 문자열 코드
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
