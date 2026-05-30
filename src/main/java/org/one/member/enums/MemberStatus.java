package org.one.member.enums;

/**
 * 정규 부원의 재적 상태를 표현합니다.
 */
public enum MemberStatus {
    /**
     * 재학 중인 상태입니다.
     */
    ACTIVE,

    /**
     * 군 복무로 휴학 중인 상태입니다.
     */
    MILITARY_LEAVE,

    /**
     * 졸업한 상태입니다.
     */
    GRADUATED,

    /**
     * 탈퇴한 상태입니다.
     */
    WITHDRAWN,

    /**
     * 일반 휴학 중인 상태입니다.
     */
    ON_LEAVE
}
