-- applicant_member, member 테이블에서 학과, 생년월일, 성별 컬럼 제거
ALTER TABLE `applicant_member`
    DROP COLUMN `department`,
    DROP COLUMN `birthday`,
    DROP COLUMN `gender`;

ALTER TABLE `member`
    DROP COLUMN `department`,
    DROP COLUMN `birthday`,
    DROP COLUMN `gender`;
