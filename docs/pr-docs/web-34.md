# [web-34] 관리자 모집 공고 조회 API 구현

## 작업 내용
관리자 페이지에서 모집 공고 정보를 조회할 수 있는 API를 구현합니다.
기획 변경에 따라 `title` 필드를 제거하고 `field`(지원분야)를 추가합니다.

## 변경 사항
- `V6__update_recruitment.sql`: `title` 컬럼 제거, `field` TEXT 컬럼 추가
- `Recruitment` 엔티티: `title` → `field` 교체
- `GET /api/v1/admin/recruitment` 엔드포인트 구현
- `AdminRecruitmentController`, `AdminRecruitmentService` 추가
- `RecruitmentResponseDto` 추가
- `RecruitmentRepository` 추가 (`findRecruitment()` - Singleton 엔티티 ID 고정 1)
- 현재 날짜 기준 `recruiting` 자동 계산

## API

### 모집 공고 조회
`GET /api/v1/admin/recruitment`

**Response:** `200 OK`
```json
{
  "success": true,
  "data": {
    "target": "대학교 1~3학년",
    "field": "프론트엔드, 백엔드",
    "recruitmentStart": "2026-03-01",
    "recruitmentEnd": "2026-03-15",
    "interviewStart": "2026-03-20",
    "interviewEnd": "2026-03-22",
    "notificationDate": "2026-03-28",
    "recruiting": false
  }
}
```

## 테스트
- [x] GET /api/v1/admin/recruitment → 200 OK
- [x] 모집 기간 내 요청 시 recruiting: true
- [x] 모집 기간 외 요청 시 recruiting: false
