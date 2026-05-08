package org.one.global.apiPayload.handler;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.one.global.apiPayload.ApiResponse;
import org.one.global.apiPayload.code.ErrorCode;
import org.one.global.apiPayload.exception.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		return ResponseEntity.status(errorCode.getStatus())
				.body(ApiResponse.error(exception.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<List<String>>> handleValidationException(MethodArgumentNotValidException exception) {
		var errors = exception.getBindingResult().getFieldErrors().stream()
				.map(FieldError::getDefaultMessage)
				.toList();
		return ResponseEntity.badRequest()
				.body(ApiResponse.failure(errors, "입력값을 확인해주세요."));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(Exception exception) {
		return ResponseEntity.internalServerError()
				.body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
	}
}
