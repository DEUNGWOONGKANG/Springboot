package com.nuriggum.nuriframe.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuriggum.nuriframe.common.message.model.MessageKey;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationFilter() {
        // url과 일치할 경우 해당 필터가 동작합니다.
        super(new AntPathRequestMatcher("/login"));
    }

    private boolean isPost(HttpServletRequest request) {

        if("POST".equals(request.getMethod())) {
            return true;
        }

        return false;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 해당 요청이 POST 인지 확인
        if(!isPost(request)) {
            throw new IllegalStateException(MessageKey.IS_NOT_SUPPORT);
        }

        ServletInputStream inputStream = request.getInputStream();

        // POST 이면 body 를 AccountDto(로그인 정보 DTO)에 매핑
        LoginRequestDto loginRequestDto = objectMapper.readValue(inputStream, LoginRequestDto.class);

        // ID, PASSWORD 가 있는지 확인
        if(!StringUtils.hasLength(loginRequestDto.getLoginId())
                || !StringUtils.hasLength(loginRequestDto.getPassword())) {
            throw new IllegalArgumentException(MessageKey.USER_NOT_EXIST);
        }

        // 처음에는 인증 되지 않은 토큰 생성
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDto.getLoginId(),
                loginRequestDto.getPassword()
        );

        // Manager 에게 인증 처리
        Authentication authenticate = this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        return authenticate;
    }

    @Data
    public static class LoginRequestDto {
        private String loginId;
        private String password;
        private String loginType;

    }

}
