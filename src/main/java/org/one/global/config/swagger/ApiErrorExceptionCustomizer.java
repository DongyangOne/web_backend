package org.one.global.config.swagger;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.LinkedHashMap;
import java.util.Map;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.enums.ErrorCode;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * {@link ApiErrorExceptions} 어노테이션을 읽어 Swagger 문서에 에러 응답 컴포넌트 참조를 자동 등록합니다.
 */
@Component
public class ApiErrorExceptionCustomizer implements OperationCustomizer {

	@Override
	public Operation customize(Operation operation, HandlerMethod handlerMethod) {
		ApiErrorExceptions annotation = handlerMethod.getMethodAnnotation(ApiErrorExceptions.class);
		if (annotation == null) {
			return operation;
		}

		ApiResponses responses = operation.getResponses();
		if (responses == null) {
			responses = new ApiResponses();
			operation.setResponses(responses);
		}

		Map<Integer, String> statusToRef = new LinkedHashMap<>();
		for (ErrorCode errorCode : annotation.value()) {
			int status = errorCode.getStatus().value();
			String ref = switch (status) {
				case 400 -> "#/components/responses/BadRequest";
				case 401 -> "#/components/responses/Unauthorized";
				case 403 -> "#/components/responses/Forbidden";
				case 404 -> "#/components/responses/NotFound";
				case 500 -> "#/components/responses/InternalServerError";
				default -> null;
			};
			if (ref != null) {
				statusToRef.put(status, ref);
			}
		}

		for (Map.Entry<Integer, String> entry : statusToRef.entrySet()) {
			responses.addApiResponse(String.valueOf(entry.getKey()),
					new ApiResponse().$ref(entry.getValue()));
		}

		return operation;
	}
}
