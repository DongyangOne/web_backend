package org.one.global.config;

import org.one.domain.admin.entity.Admin;
import org.one.domain.admin.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${admin.init.username}")
	private String initUsername;

	@Value("${admin.init.password}")
	private String initPassword;

	public DataInitializer(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (adminRepository.count() > 0) return;

		adminRepository.save(new Admin(initUsername, passwordEncoder.encode(initPassword)));

		log.warn("======================================================");
		log.warn("  초기 관리자 계정이 생성되었습니다.");
		log.warn("  아이디: {}", initUsername);
		log.warn("  비밀번호: {}", initPassword);
		log.warn("  로그인 후 반드시 비밀번호를 변경하세요.");
		log.warn("======================================================");
	}
}
