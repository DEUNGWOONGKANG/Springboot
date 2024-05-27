package com.nuriggum.nuriframe.common.util;

import com.nuriggum.nuriframe.common.config.RestDocsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Import(RestDocsConfig.class)
@ExtendWith({ RestDocumentationExtension.class})
public abstract class AbstractRestDocs {

    protected MockMvc mockMvc;

    @Autowired
    protected RestDocumentationResultHandler restDoc;

    //Spring Rest Docs 문서 생성을 위한 Test Method 공통 유틸
    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity())    // SECURITY LOGIN API 테스트를 위한 apply
                .alwaysDo(print())
                .alwaysDo(restDoc)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

}
