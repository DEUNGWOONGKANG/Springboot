package com.nuriggum.nuriframe.security.config;

import com.nuriggum.nuriframe.security.model.UserDetail;
import com.nuriggum.nuriframe.security.model.WithMockUser;
import com.nuriggum.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class CustomWithSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {
    @Autowired
    private UserMapper userMapper;

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        //테스트시 SECURITY 로그인을 처리 하기 위해 추가
        UserDetail userDetail = userMapper.selectLoginUser(annotation.loginId());

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities()));

        return context;
    }
}
