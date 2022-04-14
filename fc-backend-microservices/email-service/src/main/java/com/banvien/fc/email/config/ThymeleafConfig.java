package com.banvien.fc.email.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebMvc
public class ThymeleafConfig{

    @Autowired
    private TemplateEngine templateEngine;

    @PostConstruct
    public void extension() {
        templateEngine.addTemplateResolver(stringResolver());
    }

    private ITemplateResolver stringResolver() {
        StringTemplateResolver resolver = new StringTemplateResolver();
        return resolver;
    }

}
