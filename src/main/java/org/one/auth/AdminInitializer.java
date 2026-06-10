package org.one.auth;

import org.one.auth.domain.Admin;
import org.one.auth.repository.AdminRepository;
import org.one.global.config.auth.AdminInitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션 시작 시 어드민 계정이 없으면 환경변수로 자동 생성합니다.
 */
@Component
public class AdminInitializer implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final AdminInitProperties adminInitProperties;

	public AdminInitializer(AdminRepository adminRepository,
			PasswordEncoder passwordEncoder,
			AdminInitProperties adminInitProperties) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminInitProperties = adminInitProperties;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (adminRepository.count() > 0) {
			return;
		}
		String encodedPassword = passwordEncoder.encode(adminInitProperties.getPassword());
		adminRepository.save(new Admin(adminInitProperties.getUsername(), encodedPassword));
		log.info("Admin account initialized: {}", adminInitProperties.getUsername());
	}
}
