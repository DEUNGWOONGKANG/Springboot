package com.nuriggum.nuriframe.common.message.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageConfig {
    @Bean
    public LocaleResolver localResolver() {
        // messageSource의 기본 언어를 한국어로 설정
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.KOREA);
        return sessionLocaleResolver;
    }

    @Bean

    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");   // request로 넘어오는 language 파라미터를 받아서 locale로 설정
        return interceptor;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();  // locale 프로퍼티 파일 관련 설정 역할을 하는 친구
        messageSource.setBasename("messages/messages");  // 경로 설정
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
