package com.meiziaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Null;

@SpringBootApplication
public class MeiziaccessApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeiziaccessApplication.class, args);
	}

//	@Bean
//	public TemplateResolver templateResolver(){
//		TemplateResolver resolver = new ServletContextTemplateResolver();
////		resolver.setResourceResolver(new SpringResourceResourceResolver());
//		resolver.setPrefix("classpath:/pages/");
//		resolver.setSuffix(".html");
//		resolver.setTemplateMode("HTML5");
//		resolver.setCharacterEncoding("UTF-8");
////		resolver.setOrder(2);
//		return resolver;
//	}
}
