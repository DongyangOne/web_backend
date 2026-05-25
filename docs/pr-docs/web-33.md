# [web-33] 관리자 메인 로고 수정 API 구현

## 작업 내용
관리자 페이지에서 메인 페이지 로고 이미지를 파일로 업로드하여 수정할 수 있는 API를 구현합니다.
이미지 파일을 직접 업로드하면 MinIO에 저장 후 URL이 반환됩니다.

## 변경 사항
- `PATCH /api/v1/admin/main/logo` 엔드포인트 구현 (multipart/form-data)
- `MainPageConfig` 엔티티 정리 (description, recruitmentStart/End 제거)
- `MainLogoResponseDto` 추가
- `MainPageConfigRepository` 추가 (`getConfig()` - Singleton 엔티티 ID 고정 1)
- `AdminMainController`, `AdminMainService` 추가
- 기존 로고가 있으면 MinIO에서 삭제 후 새 파일 업로드

## API

### 메인 로고 수정
`PATCH /api/v1/admin/main/logo`

> Content-Type: `multipart/form-data`

**Request Parts:**

| 파트 | 타입 | 필수 | 설명 |
|------|------|------|------|
| `logo` | 파일(이미지) | ✅ | 로고 이미지 파일 (이미지만 허용) |

**Response:** `200 OK`
```json
{
  "success": true,
  "data": {
    "logoUrl": "https://cdn.example.com/logo/abc123.png"
  }
}
```

**프론트엔드 요청 예시 (JavaScript):**
```javascript
const formData = new FormData();
formData.append('logo', logoFile);

axios.patch('/api/v1/admin/main/logo', formData);
```

## 테스트
- [x] PATCH /api/v1/admin/main/logo → 200 OK
- [x] 응답에 MinIO URL 포함 확인
- [x] 동영상 파일 전송 시 400 INVALID_INPUT
- [x] 기존 로고 있을 때 덮어쓰기 시 기존 파일 MinIO에서 삭제 확인
