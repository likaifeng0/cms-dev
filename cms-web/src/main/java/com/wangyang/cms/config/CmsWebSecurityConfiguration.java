package com.wangyang.cms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangyang.authorize.config.CustomFilterInvocationSecurityMetadataSource;
import com.wangyang.authorize.config.CustomUrlDecisionManager;
import com.wangyang.authorize.config.service.UserDetailServiceImpl;
import com.wangyang.authorize.jwt.JWTConfigurer;
import com.wangyang.authorize.jwt.TokenProvider;
import com.wangyang.authorize.pojo.dto.UserDto;
import com.wangyang.authorize.pojo.entity.User;
import com.wangyang.cms.pojo.support.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.io.PrintWriter;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)

public class CmsWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;

    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @Autowired
    private  JwtAuthenticationEntryPoint authenticationErrorHandler;
    @Autowired
    private  JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private  TokenProvider tokenProvider;
//    private  CorsFilter corsFilter;
//    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public CmsWebSecurityConfiguration(
            TokenProvider tokenProvider
//            CorsFilter corsFilter
//            JwtAuthenticationEntryPoint authenticationErrorHandler,
//            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
//        this.corsFilter = corsFilter;
//        this.authenticationErrorHandler = authenticationErrorHandler;
//        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    /**
     * 问题:这里创建UserDetailsService, controller不能获取AuthenticationManager
     * @param web
     * @throws Exception
     */
//    @Bean
//    public UserDetailsService myUserDetailsService(){
//        return new UserDetailServiceImpl();
//    }
//
//
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(myUserDetailsService());
//        auth.inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN");
//    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/latex/**","/user/latex/**","/components/**","/api/comment","/user/login","/user/authenticate","/admin/**","/templates/**","/download/**","/preview/**","/article/**","/articleList/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // create no session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                //至少写一个拦截，否则会报错
                .antMatchers("/api/authenticate").permitAll()
//                .anyRequest()
//                .authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>(){
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        return o;
                    }
                })
                .and()
                .apply(securityConfigurerAdapter());

                http.cors();//跨域访问
//            http
////                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
////                .antMatchers("/api/**")
////                .hasRole("ADMIN")
////                .antMatchers("/user/authenticate").permitAll()
//
////                .anyRequest().authenticated()
//
//
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>(){
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        o.setAccessDecisionManager(customUrlDecisionManager);
//                        o.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
//                        return o;
//                    }
//                })
//                .and()
//                .apply(securityConfigurerAdapter())
//                .and()
//
//                .formLogin()
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginProcessingUrl("/login")
//                .successHandler((req, resp, authentication) -> {
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    UserDto user = (UserDto)authentication.getPrincipal();
//                    user.setPassword(null);
//                    out.write(new ObjectMapper().writeValueAsString(BaseResponse.ok("Login success!!",user)));
//                    out.flush();
//                    out.close();
//                })
//                .failureHandler((req, resp, exception)->{
//                    resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    BaseResponse baseResponse = new BaseResponse();
//                    baseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//                    if (exception instanceof LockedException) {
//                        baseResponse.setMessage("账户被锁定，请联系管理员!");
//                    } else if (exception instanceof CredentialsExpiredException) {
//                        baseResponse.setMessage("密码过期，请联系管理员!");
//                    } else if (exception instanceof AccountExpiredException) {
//                        baseResponse.setMessage("账户过期，请联系管理员!");
//                    } else if (exception instanceof DisabledException) {
//                        baseResponse.setMessage("账户被禁用，请联系管理员!");
//                    } else if (exception instanceof BadCredentialsException) {
//                        baseResponse.setMessage("用户名或者密码输入错误，请重新输入!");
//                    }
//                    out.write(new ObjectMapper().writeValueAsString(baseResponse));
//                    out.flush();
//                    out.close();
//                })
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessHandler((request,resp,authentication) ->{
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    out.write(new ObjectMapper().writeValueAsString(BaseResponse.ok("logout success!!")));
//                    out.flush();
//                    out.close();
//                })
//                .permitAll()
//                .and().exceptionHandling()
//                .authenticationEntryPoint((req, resp, authException) -> {
//                    resp.setContentType("application/json;charset=utf-8");
//                    resp.setStatus(401);
//                    PrintWriter out = resp.getWriter();
//                    out.write(new ObjectMapper().writeValueAsString(BaseResponse.error("not authentication!!")));
//                    out.flush();
//                    out.close();
//                });
//
//        http.cors();
//        http.csrf().disable();
//
//
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

}
