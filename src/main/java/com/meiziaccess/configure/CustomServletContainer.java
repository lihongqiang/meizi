package com.meiziaccess.configure;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by user-u1 on 2016/4/13.
 */

//@Component
//public class CustomServletContainer implements EmbeddedServletContainerCustomizer {
//
//    //方法一修改servlet
//    @Override
//    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
//        configurableEmbeddedServletContainer.setPort(8888);
//        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
//        configurableEmbeddedServletContainer.setSessionTimeout(600);
//    }
//
//    //方法二修改tomcatServlet
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer(){
//        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//        factory.setPort(8888);
//        factory.setSessionTimeout(10, TimeUnit.MINUTES);
//        return factory;
//    }
//}
