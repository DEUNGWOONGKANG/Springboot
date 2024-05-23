package com.nuriggum.nuriframe.common.util;

import com.nuriggum.nuriframe.security.model.UserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.SecureRandom;

public class NuriSecurityUtil {

    public static String getLoginUserId(){
        //SECURITY 로그인 사용자 데이터 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //TEST
        if(authentication.getPrincipal().toString().equals("anonymousUser")){
            return "TEST";
        }
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();

        return userDetail.getUserId();
    }

    public static String getRandomNumber(){
        SecureRandom random = new SecureRandom();
        int randomNumber = random.nextInt(900000) + 100000;
        return String.valueOf(randomNumber);
    }
}
