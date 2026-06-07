package org.one.main.service;

import lombok.RequiredArgsConstructor;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.main.domain.MainPageConfig;
import org.one.main.dto.response.ActivityCardResponseDto;
import org.one.main.dto.response.VisitorMainResponseDto;
import org.one.main.repository.ActivityCardRepository;
import org.one.main.repository.MainPageConfigRepository;
import org.one.project.dto.response.MainProjectCardResponseDto;
import org.one.project.repository.ProjectEventRepository;
import org.one.recruitment.domain.Recruitment;
import org.one.recruitment.dto.response.MainRecruitmentResponseDto;
import org.one.recruitment.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorMainService {
    private final MainPageConfigRepository mainPageConfigRepository;
    private final ActivityCardRepository activityCardRepository;
    private final ProjectEventRepository projectEventRepository;
    private final RecruitmentRepository recruitmentRepository;

    /**
     * 메인 페이지 불러오기
     * @return VisitorMainResponseDto
     */
    public VisitorMainResponseDto getMainPage(){
        MainPageConfig mainPageConfig = mainPageConfigRepository.findById(1)
                .orElseThrow(()-> new BusinessException(ErrorCode.MAINPAGE_CONFIG_NOT_FOUND));

        List<ActivityCardResponseDto> activityCards = activityCardRepository.findAllByOrderByCardOrderAsc().stream()
                .map(ActivityCardResponseDto::from)
                .toList();

        List<MainProjectCardResponseDto> projectCards = projectEventRepository.findAllByOrderByYearAsc().stream()
                .map(MainProjectCardResponseDto::from)
                .toList();

        return VisitorMainResponseDto.of(mainPageConfig, activityCards, projectCards);
    }

    /**
     * 모집 공고 불러오기
     * @return MainRecruitmentResponseDto 모집공고응답
     */
    public MainRecruitmentResponseDto getRecruitment(){
        Recruitment recruitment = recruitmentRepository.findById(1)
                .orElseThrow(()->new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        //서버 시간 기준 모집 중인지 확인
        LocalDate today = LocalDate.now();
        boolean isRecruiting = !today.isBefore(recruitment.getRecruitmentStart())
                && !today.isAfter(recruitment.getRecruitmentEnd());
        return MainRecruitmentResponseDto.of(recruitment, isRecruiting);
    }


}
