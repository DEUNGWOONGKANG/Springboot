package com.nuriggum.nuriframe.security.config;

import com.nuriggum.nuriframe.common.message.model.MessageKey;
import com.nuriggum.nuriframe.common.message.util.MessageUtil;
import com.nuriggum.nuriframe.security.model.UserDetail;
import com.nuriggum.user.mapper.UserMapper;
import com.nuriggum.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MessageUtil messageUtil;

    @Value("${login.fail.limit.count}")
    private int failLimitCount;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();

         UserDetail user = (UserDetail) userDetailsService.loadUserByUsername(loginId);
        //사용자가 존재하지 않을때
        if(user == null) throw new UsernameNotFoundException(messageUtil.getMessage(MessageKey.USER_NOT_EXIST, null));

        //사용자 계정이 잠금처리되었을때
        if(user.isLockYn()) throw new BadCredentialsException(messageUtil.getMessage(MessageKey.USER_LOCK, null));

        //비밀번호 일치여부 검증
        if(!passwordEncoder.matches(password, user.getPassword())) {
            int failCount = user.getLoginFailCount() + 1;

            User newUser = new User();
            newUser.setUserId(user.getUserId());
            newUser.setLoginFailCount(failCount);

            // 실패 횟수가 제한 카운트보다 작은경우 카운트 증가
            userMapper.updateLoginFailCount(newUser);

            if(failCount == failLimitCount){
                // 실패 횟수가 제한 카운트와 동일한 경우 잠금 처리
                newUser.setLockYn(true);
                userMapper.updateUserLock(newUser);
            }
            throw new BadCredentialsException(messageUtil.getMessage(MessageKey.INVALID_PASSWORD, null));
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }
}
