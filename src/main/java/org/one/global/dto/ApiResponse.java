package org.one.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.one.global.enums.ErrorCode;

/**
 * 모든 API 응답에서 공통으로 사용하는 응답 포맷입니다.
 *
 * @param success 요청 성공 여부
 * @param code 프론트엔드에서 분기 처리할 수 있는 응답 코드
 * @param message 사용자 또는 개발자가 확인할 응답 메시지
 * @param data 실제 응답 데이터
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
		boolean success,
		String code,
		String message,
		T data
) {

	/**
	 * 데이터만 포함한 성공 응답을 생성합니다.
	 *
	 * @param data 응답 데이터
	 * @return SUCCESS 코드가 포함된 성공 응답
	 */
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, "SUCCESS", null, data);
	}

	/**
	 * 데이터와 안내 메시지를 포함한 성공 응답을 생성합니다.
	 *
	 * @param data 응답 데이터
	 * @param message 성공 안내 메시지
	 * @return SUCCESS 코드가 포함된 성공 응답
	 */
	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(true, "SUCCESS", message, data);
	}

	/**
	 * ErrorCode에 정의된 코드와 메시지로 실패 응답을 생성합니다.
	 *
	 * @param errorCode 공통 에러 코드
	 * @return 실패 응답
	 */
	public static ApiResponse<Void> error(ErrorCode errorCode) {
		return error(errorCode.getCode(), errorCode.getMessage());
	}

	/**
	 * 코드와 메시지만 포함한 실패 응답을 생성합니다.
	 *
	 * @param code 프론트엔드 분기용 에러 코드
	 * @param message 에러 메시지
	 * @return 실패 응답
	 */
	public static ApiResponse<Void> error(String code, String message) {
		return new ApiResponse<>(false, code, message, null);
	}
}
