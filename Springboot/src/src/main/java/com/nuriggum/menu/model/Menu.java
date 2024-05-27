package com.nuriggum.menu.model;

import lombok.Data;

@Data
public class Menu {
    // 메뉴 ID
    private String menuId;
    // 상위메뉴 ID
    private String upperMenuId;
    // 메뉴명
    private String menuName;
    // URL
    private String url;
    // 정렬순서
    private int sortNumber;
    // 메뉴설명
    private String description;
    // 회사 ID
    private Integer companyId;
    // 사용여부
    private boolean useYn;
    // 시스템 타입
    private String systemType;
    // 언어
    private String language;
    // 등록일시
    private String registDate;
    // 등록사용자ID
    private String registUserId;
    // 변경일시
    private String changeDate;
    // 변경사용자ID
    private String changeUserId;
}
