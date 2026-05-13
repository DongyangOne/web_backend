package org.one.global.exception;

import org.one.global.enums.ErrorCode;

/**
 * 비즈니스 규칙 위반을 ErrorCode 기준으로 표현하는 예외입니다.
 */
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;

	/**
	 * ErrorCode에 정의된 메시지만 예외 메시지로 사용합니다.
	 *
	 * @param errorCode 공통 에러 코드
	 */
	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	/**
	 * 응답 상태와 코드 생성을 위한 ErrorCode를 반환합니다.
	 *
	 * @return 공통 에러 코드
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
