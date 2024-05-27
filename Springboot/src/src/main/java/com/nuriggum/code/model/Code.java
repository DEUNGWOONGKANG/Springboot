package com.nuriggum.code.model;

import lombok.Data;

@Data
public class Code {
    // 그룹ID
    private String groupId;
    // 코드값
    private String code;
    // 코드명
    private String codeName;
    // 사용여부
    private boolean useYn;
    // 정렬순서
    private int sortNumber;
    // 등록일시
    private String registDate;
    // 등록사용자ID
    private String registUserId;
    // 변경일시
    private String changeDate;
    // 변경사용자ID
    private String changeUserId;
    // 사용자 정의 1
    private String attribute1;
    // 사용자 정의 2
    private String attribute2;
    // 사용자 정의 3
    private String attribute3;
    // 사용자 정의 4
    private String attribute4;
    // 사용자 정의 5
    private String attribute5;
    // 사용자 정의 6
    private String attribute6;
    // 사용자 정의 7
    private String attribute7;
    // 사용자 정의 8
    private String attribute8;
    // 사용자 정의 9
    private String attribute9;
}
