package com.nuriggum.nuriframe.common.message.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig{

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();  // locale 프로퍼티 파일 관련 설정 역할을 하는 친구
        messageSource.setBasename("messages/messages");  // 경로 설정
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
