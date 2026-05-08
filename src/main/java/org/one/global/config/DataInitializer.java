package org.one.global.config;

import org.one.global.config.props.AdminInitProperties;
import org.one.domain.admin.entity.Admin;
import org.one.domain.admin.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final AdminInitProperties adminInitProperties;

	public DataInitializer(AdminRepository adminRepository, PasswordEncoder passwordEncoder,
			AdminInitProperties adminInitProperties) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminInitProperties = adminInitProperties;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (adminRepository.count() > 0) return;

		adminRepository.save(new Admin(adminInitProperties.getUsername(), passwordEncoder.encode(adminInitProperties.getPassword())));

		log.warn("======================================================");
		log.warn("  초기 관리자 계정이 생성되었습니다.");
		log.warn("  아이디: {}", adminInitProperties.getUsername());
		log.warn("  비밀번호: {}", adminInitProperties.getPassword());
		log.warn("  로그인 후 반드시 비밀번호를 변경하세요.");
		log.warn("======================================================");
	}
}
