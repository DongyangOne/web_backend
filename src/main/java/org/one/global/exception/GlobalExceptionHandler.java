package org.one.global.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 컨트롤러에서 발생한 예외를 공통 ApiResponse 형식으로 변환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 서비스 계층에서 발생한 비즈니스 예외를 ErrorCode 기반 응답으로 변환합니다.
	 *
	 * @param exception 비즈니스 예외
	 * @return 실패 응답
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		return ResponseEntity.status(errorCode.getStatus())
				.body(ApiResponse.error(errorCode.getCode(), exception.getMessage()));
	}

	/**
	 * 요청 DTO 검증 실패를 필드 메시지 목록과 함께 BAD_REQUEST 응답으로 변환합니다.
	 *
	 * @param exception Bean Validation 예외
	 * @return 검증 실패 응답
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationException(
			MethodArgumentNotValidException exception) {
		List<String> errors = exception.getBindingResult().getFieldErrors().stream()
				.map(FieldError::getDefaultMessage)
				.toList();
		String validationMessage = errors.isEmpty()
				? ErrorCode.INVALID_INPUT.getMessage()
				: String.join(", ", errors);
		return ResponseEntity.badRequest()
				.body(ApiResponse.error(ErrorCode.INVALID_INPUT.getCode(), validationMessage));
	}

	/**
	 * JSON 파싱 실패, 필수 파라미터 누락, 단일 값 검증 실패를 BAD_REQUEST 응답으로 변환합니다.
	 *
	 * @param exception 잘못된 요청 예외
	 * @return 잘못된 요청 실패 응답
	 */
	@ExceptionHandler({
			HttpMessageNotReadableException.class,
			MissingServletRequestParameterException.class,
			ConstraintViolationException.class
	})
	public ResponseEntity<ApiResponse<Void>> handleInvalidRequestException(Exception exception) {
		return ResponseEntity.badRequest()
				.body(ApiResponse.error(ErrorCode.INVALID_INPUT));
	}

	/**
	 * 예상하지 못한 예외를 내부 서버 오류 응답으로 변환합니다.
	 *
	 * @param exception 처리되지 않은 예외
	 * @return 내부 서버 오류 응답
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(Exception exception) {
		log.error("Unexpected exception occurred", exception);
		return ResponseEntity.internalServerError()
				.body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
	}
}
