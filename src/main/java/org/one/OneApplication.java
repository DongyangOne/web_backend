package org.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ONE 백엔드 애플리케이션의 시작점입니다.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class OneApplication {

	/**
	 * Spring Boot 애플리케이션을 실행합니다.
	 *
	 * @param args 실행 인자
	 */
	public static void main(String[] args) {
		SpringApplication.run(OneApplication.class, args);
	}
}
