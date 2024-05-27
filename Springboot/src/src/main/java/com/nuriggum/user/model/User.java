package com.nuriggum.user.model;

import lombok.Data;

@Data
public class User {
    // 사용자 ID - UUID
    private String userId;
    // 로그인 ID
    private String loginId;
    // 비밀번호
    private String password;
    // 사용자명
    private String userName;
    // 사번
    private String employeeNumber;
    // 휴대폰번호
    private String mobileNumber;
    // 이메일주소
    private String emailAddress;
    // 회사ID
    private Integer companyId;
    // 부서명
    private String deptName;
    // 직위명
    private String positionName;
    // 권한ID
    private String authorityId;
    // 로그인실패 횟수
    private int loginFailCount;
    // 계정 잠김 여부
    private boolean lockYn;
    // 잠금해제자 ID
    private String unlockUserId;
    // 잠금해제사유
    private String unlockReason;
    // 사용여부
    private boolean useYn;
    // 마지막 로그인일시
    private String lastLoginDate;
    // 마지막 로그인 IP
    private String lastLoginIp;
    // 등록일시
    private String registDate;
    // 등록사용자ID
    private String registUserId;
    // 변경일시
    private String changeDate;
    // 변경사용자ID
    private String changeUserId;
}
