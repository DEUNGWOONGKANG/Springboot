package com.nuriggum.nuriframe.security.model;

import com.nuriggum.nuriframe.security.config.CustomWithSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomWithSecurityContextFactory.class)
public @interface WithMockUser {
    String loginId() default "nuri";
}