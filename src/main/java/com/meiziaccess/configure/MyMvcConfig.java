package com.meiziaccess.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.spring4.view.ThymeleafView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-u1 on 2016/4/12.
 */
@Configuration
public class MyMvcConfig {

//    @Bean
//    public TemplateResolver templateResolver(){
//        TemplateResolver templateResolver = new ServletContextTemplateResolver();
//        templateResolver.setPrefix("classpath:/pages/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML5");
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine(){
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        return templateEngine;
//    }
//
//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver(){
//        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
//        thymeleafViewResolver.setTemplateEngine(templateEngine());
//        return thymeleafViewResolver;
//    }

//    @Autowired
//    SpringTemplateEngine templateEngine;
//
//    @Autowired
//    ThymeleafViewResolver thymeleafViewResolver;
//
//    @Bean
//    @PostConstruct
//    public ThymeleafViewResolver addTemplateResolver(){
//        TemplateResolver resolver = new ServletContextTemplateResolver();
//        resolver.setPrefix("classpath:/pages/");
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode("HTML5");
//        resolver.setCharacterEncoding("UTF-8");
//        templateEngine.addTemplateResolver(resolver);
//        thymeleafViewResolver.setTemplateEngine(templateEngine);
//
//        return thymeleafViewResolver;
//    }
//
//    @Bean
//    ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
//        ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
//        List<ViewResolver> list = new ArrayList<>();
//        list.add(addTemplateResolver());
//        contentNegotiatingViewResolver.setViewResolvers(list);
//        return contentNegotiatingViewResolver;
//    }

//
////    @Autowired
////    private SpringTemplateEngine templateEngine;
////
////    @PostConstruct
////    public TemplateResolver templateResolver(){
////        TemplateResolver templateResolver = new ServletContextTemplateResolver ();
////        templateResolver.setPrefix("/pages");
////        templateResolver.setSuffix(".html");
////        templateResolver.setTemplateMode("HTML5");
////        templateEngine.addTemplateResolver(templateResolver);
////        return templateResolver;
////    }
//
//    @Bean
//    public TemplateResolver templateResolver(){
//        TemplateResolver resolver = new ServletContextTemplateResolver();
////        resolver.setResourceResolver(new SpringResourceResourceResolver());
//        resolver.setPrefix("classpath:pages/");
//        resolver.setSuffix(".html");
//        resolver.setTemplateMode("HTML5");
//        resolver.setCharacterEncoding("UTF-8");
//        resolver.setCacheable(false);
////        resolver.setOrder(2);
//        return resolver;
//    }

//    @Bean
//    public TemplateResolver getClassLoaderTemplateResolver(){
//        TemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setTemplateMode("HTML5");
//        return resolver;
//    }

//    @Bean
//    public TemplateResolver defaultTemplateResolver() {
//        TemplateResolver resolver = new TemplateResolver();
//        resolver.setResourceResolver(this.thymeleafResourceResolver());
//        resolver.setPrefix(this.properties.getPrefix());
//        resolver.setSuffix(this.properties.getSuffix());
//        resolver.setTemplateMode(this.properties.getMode());
//        if(this.properties.getEncoding() != null) {
//            resolver.setCharacterEncoding(this.properties.getEncoding().name());
//        }
//
//        resolver.setCacheable(this.properties.isCache());
//        Integer order = this.properties.getTemplateResolverOrder();
//        if(order != null) {
//            resolver.setOrder(order);
//        }
//
//        return resolver;
//    }
}
