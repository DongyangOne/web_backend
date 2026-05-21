package org.one.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.one.global.enums.ErrorCode;

/**
 * Swagger 문서에 표시할 에러 응답 코드 목록을 선언합니다.
 * {@link ApiErrorExceptionCustomizer}가 이 어노테이션을 읽어 OpenAPI 응답을 자동 등록합니다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorExceptions {
	ErrorCode[] value();
}
