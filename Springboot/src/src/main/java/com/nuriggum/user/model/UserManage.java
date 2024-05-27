package com.nuriggum.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserManage extends User {
    //비밀번호 변경시 입력 패스워드1
    String newPassword1;
    //비밀번호 변경시 입력 패스워드2
    String newPassword2;
}
