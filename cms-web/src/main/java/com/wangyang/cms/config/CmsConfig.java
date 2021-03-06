package com.wangyang.cms.config;

import com.wangyang.cms.core.thymeleaf.CmsDialect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;


@Configuration
@Slf4j
public class CmsConfig implements ApplicationContextAware {

//    @Bean
//    public ViewResolver myViewResolver(){
//        MyCustomViewResolver myCustomViewResolver = new MyCustomViewResolver();
//        myCustomViewResolver.setOrder(1);
//        return myCustomViewResolver;
//    }
    @Value("${cms.workDir}")
    private String workDir;

    private static ApplicationContext applicationContext;
    @Override
    public  void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public static <T> T  getBean(Class<T> clazz) {
        return applicationContext != null?applicationContext.getBean(clazz):null;
    }
    public static Object  getBean(String name) {
        return applicationContext != null?applicationContext.getBean(name):null;
    }


    /**
     * 路径特殊字符允许
     * @return
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "^+|{}[]\\"));
        return factory;
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*"); // e.g. http://domain1.com
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/api/**", config);
//        return new CorsFilter(source);
//    }
//    @Bean
//    public WebMvcConfigurer corsConfigurer()
//    {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").
//                allowedOrigins("*"). //允许跨域的域名，可以用*表示允许任何域名使用
//                allowedMethods("*"). //允许任何方法（post、get等）
//                allowedHeaders("*"). //允许任何请求头
//                allowCredentials(true). //带上cookie信息
//                exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
//            }
//        };
//    }
    @Bean
    public ViewResolver thymeleafViewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCache(false);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }


    /**
     * https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#chaining-template-resolvers
     * @return
     */
    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addDialect(new CmsDialect());

        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setPrefix("file:"+workDir+"/");
        springResourceTemplateResolver.setSuffix(".html");
        //set cache
        springResourceTemplateResolver.setCacheable(false);
        springResourceTemplateResolver.setCharacterEncoding("UTF-8");
        springResourceTemplateResolver.setApplicationContext(this.applicationContext);
        springResourceTemplateResolver.setOrder(Integer.valueOf(1));
        springResourceTemplateResolver.getResolvablePatternSpec().addPattern("templates/*");
        springResourceTemplateResolver.getResolvablePatternSpec().addPattern("html/*");



        templateEngine.addTemplateResolver(springResourceTemplateResolver);

        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        springResourceTemplateResolver.setCharacterEncoding("UTF-8");
        stringTemplateResolver.setOrder(Integer.valueOf(2));
        templateEngine.addTemplateResolver(stringTemplateResolver);
        return templateEngine;
    }




}
