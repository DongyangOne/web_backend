package org.one.domain.service;

import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.MainLogoUpdateRequestDto;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.repository.MainPageConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminMainService {

	private final MainPageConfigRepository mainPageConfigRepository;

	public MainLogoResponseDto updateLogo(MainLogoUpdateRequestDto request) {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.update(
				request.getLogoUrl(),
				config.getDescription(),
				config.getRecruitmentStart(),
				config.getRecruitmentEnd()
		);
		return MainLogoResponseDto.from(request.getLogoUrl());
	}
}
