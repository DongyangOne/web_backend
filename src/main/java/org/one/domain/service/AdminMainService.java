package org.one.domain.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.MainIntroUpdateRequestDto;
import org.one.domain.dto.request.MainLogoUpdateRequestDto;
import org.one.domain.dto.request.MainRecruitmentUpdateRequestDto;
import org.one.domain.dto.response.MainIntroResponseDto;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.dto.response.MainRecruitmentResponseDto;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.repository.MainPageConfigRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
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

	public MainIntroResponseDto updateIntro(MainIntroUpdateRequestDto request) {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.update(
				config.getLogoUrl(),
				request.getDescription(),
				config.getRecruitmentStart(),
				config.getRecruitmentEnd()
		);
		return MainIntroResponseDto.from(request.getDescription());
	}

	public MainRecruitmentResponseDto updateRecruitment(MainRecruitmentUpdateRequestDto request) {
		if (request.getRecruitmentStart().isAfter(request.getRecruitmentEnd())) {
			throw new BusinessException(ErrorCode.INVALID_INPUT);
		}
		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.update(
				config.getLogoUrl(),
				config.getDescription(),
				request.getRecruitmentStart(),
				request.getRecruitmentEnd()
		);
		return MainRecruitmentResponseDto.from(
				computeIsRecruiting(config),
				request.getRecruitmentStart(),
				request.getRecruitmentEnd()
		);
	}

	private boolean computeIsRecruiting(MainPageConfig config) {
		LocalDate start = config.getRecruitmentStart();
		LocalDate end = config.getRecruitmentEnd();
		if (start == null || end == null) {
			return false;
		}
		LocalDate today = LocalDate.now();
		return !today.isBefore(start) && !today.isAfter(end);
	}
}
