package com.fushun.framework.sample.security.manageapp;

import com.fushun.framework.sample.security.admin.filter.JWTAuthorizationFilter;
import com.fushun.framework.sample.security.manageapp.filter.AppUserJWTFilter;
import com.fushun.framework.sample.security.manageapp.model.AppUserLoginInfo;
import com.fushun.framework.sample.security.manageapp.service.AppUserDetailService;
import com.fushun.framework.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//@Order(3)
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity(debug=false)
//@EnableMethodSecurity()
public class UserAppSecurityAdapter {


    @Autowired
    CustomAuthenticationFailHandler appUserAuthenticationFailHandler;
    @Autowired
    CustomAuthenticationSuccessHandler<AppUserLoginInfo> appUserAuthenticationSuccessHandler;
    @Autowired
    LogoutSuccessHandlerImpl appUserLogoutSuccessHandler;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private AppUserDetailService appUserDetailService;


    public static class PasswordVerification implements PasswordEncoder {

        @Override
        public String encode(CharSequence charSequence) {
            return null;
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            //网超系统的密码校验 统一直接通过
            return true;
        }
    }


//    @Bean
    AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        ProviderManager pm = new ProviderManager(authenticationProvider);
        return pm;
    }

//    @Bean
    AuthenticationProvider authenticationProvider(AppUserDetailService appUserDetailService) {
        // 创建一个用户认证提供者
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 设置用户相关信息，可以从数据库中读取、或者缓存、或者配置文件
        authProvider.setUserDetailsService(appUserDetailService);
        // 设置加密机制，用于对用户进行身份验证
        authProvider.setPasswordEncoder(new PasswordVerification());
        return authProvider;
    }


    @Bean
    @Order(1)
    SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {

//        AppUserJWTFilter appUserJWTFilter=new AppUserJWTFilter();
        http.securityMatcher("/app/**")
                .authorizeHttpRequests(auth -> auth.requestMatchers("/app/**")
                        .hasAnyRole("APP").anyRequest().permitAll()
                )
                .formLogin(form ->
                        form.loginProcessingUrl("/app/login")
                        .usernameParameter("username").passwordParameter("password")
                        .failureHandler(appUserAuthenticationFailHandler)
                        .successHandler(appUserAuthenticationSuccessHandler)
                )
                .logout(lo->lo.logoutUrl("/app/logout")
                        .logoutSuccessHandler(appUserLogoutSuccessHandler)
                )
                .csrf(csrf -> csrf.disable())
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(w->w.accessDeniedHandler(customAccessDeniedHandler))
                .exceptionHandling(w->w.authenticationEntryPoint(customAuthenticationEntryPoint))
                .authenticationProvider(authenticationProvider(appUserDetailService))
                .addFilter(new AppUserJWTFilter(authenticationManager(authenticationProvider(appUserDetailService))))
        ;
        return http.build();

//        http
//                .antMatcher("/app/**")
//                // 表单登录方式
//                .formLogin()
//                .loginProcessingUrl("/app/login")
//                .usernameParameter("username").passwordParameter("password")
//                .permitAll() // 登录请求url
//                .successHandler(appUserAuthenticationSuccessHandler)// 成功处理类
//                .failureHandler(appUserAuthenticationFailHandler) // 失败
//
//                .and()
//                .headers().frameOptions().disable() // 允许网页iframe //关闭X-Frame-Options
//
//                //登出设置
//                .and()
//                .logout()
//                .logoutUrl("/app/logout")
//                .logoutSuccessHandler(appUserLogoutSuccessHandler)
//                .permitAll()
//
//
//                .and()
//                .authorizeRequests()//启用基于 HttpServletRequest 的访问限制，开始配置哪些URL需要被保护、哪些不需要被保护
//                .anyRequest().hasAnyRole("USER")//其他/api路径下的请求全部需要登陆，获得USER角色
//
//                //跨域
//                .and()
//                .cors()// 允许跨域
//
//                //跨站请求防护
//                .and()
//                .csrf().disable()// 关闭跨站请求防护
//
//
//
//                // 前后端分离采用JWT 不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
//                .and()
//                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
//                .and()
//                .addFilter(new AppUserJWTFilter(authenticationManager()));

    }



}
