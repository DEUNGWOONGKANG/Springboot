package com.nuriggum.nuriframe.common.springdocs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpringRestDocsViewController {

    @GetMapping("/docs")
    public String index() {
        return "/docs/index.html";
    }
}
