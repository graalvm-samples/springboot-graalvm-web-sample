package com.fushun.framework.sample.security.admin;


import com.fushun.framework.sample.security.admin.filter.JWTAuthorizationFilter;
import com.fushun.framework.sample.security.admin.model.AdminLoginInfo;
import com.fushun.framework.sample.security.admin.service.AdminUserDetailsService;
import com.fushun.framework.security.config.BasicTokenConfig;
import com.fushun.framework.security.config.IgnoredUrlsConfig;
import com.fushun.framework.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration(proxyBeanMethods = false)
@EnableWebSecurity(debug=false)
public class AdminSecurityConfig {

    //用户获取服务接口
    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    //
    @Autowired
    private BasicTokenConfig basicTokenConfig;

    //不需要登陆的地址
    @Autowired
    private IgnoredUrlsConfig ignoredUrlsConfig;

    //登陆成功处理
    @Autowired
    private CustomAuthenticationSuccessHandler<AdminLoginInfo> successHandler;

    //登陆失败处理
    @Autowired
    private CustomAuthenticationFailHandler failHandler;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    /**退出成功处理类*/
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(adminUserDetailsService);
//    }


    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().requestMatchers(ignoredUrlsConfig.getNoLoginVerification().toArray(new String[]{}));
            }
        };
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //忽略地址 有2种方式
//        //1. 通过 WebSecurity 直接过滤掉地址，就是该地址直接不走过 HttpSecurity 的过滤链
//        //2. 设置该地址匿名访问 通过 HttpSecurity
//        //在我们的流程里面 如果采用第二种 那么永远无法实现地址过滤，因为我们的 JWTAuthorizationFilter 决定了如果你没有token 就永远无法访问一个接口地址
//        web.ignoring().antMatchers(ignoredUrlsConfig.getNoLoginVerification().toArray(new String[]{}));
//    }

//    AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(adminUserDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//        ProviderManager pm = new ProviderManager(daoAuthenticationProvider);
//        return pm;
//    }

    //    @Bean
    AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        ProviderManager pm = new ProviderManager(authenticationProvider);
        return pm;
    }

    //    @Bean
    AuthenticationProvider authenticationProvider(AdminUserDetailsService adminUserDetailsService) {
        // 创建一个用户认证提供者
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 设置用户相关信息，可以从数据库中读取、或者缓存、或者配置文件
        authProvider.setUserDetailsService(adminUserDetailsService);
        // 设置加密机制，用于对用户进行身份验证
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }

//    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
//    }

    @Bean
    @Order(2)
    SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**")
                        .hasAnyRole("ADMIN").anyRequest().permitAll())
                .formLogin(form -> form.loginProcessingUrl("/admin/login")
                        .usernameParameter("username").passwordParameter("password")
                        .failureHandler(failHandler)
                        .successHandler(successHandler))
                .logout(lo->lo.logoutUrl("/admin/logout").logoutSuccessHandler(logoutSuccessHandler))
                .csrf(csrf -> csrf.disable())
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(w->w.accessDeniedHandler(customAccessDeniedHandler))
                .exceptionHandling(w->w.authenticationEntryPoint(customAuthenticationEntryPoint))
                .authenticationProvider(authenticationProvider(adminUserDetailsService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(authenticationProvider(adminUserDetailsService))));
        return http.build();


//        http//多HttpSecurity配置时必须设置这个，除最后一个外，因为不设置的话默认匹配所有，就不会执行到下面的HttpSecurity了
//                // 表单登录方式
//                .antMatcher("/admin/**")
//                .formLogin()
////                .loginPage("/basic/needLogin") //不需要了，访问需要权限的链接，返回json方式，采用设置方式：exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
//                .loginProcessingUrl("/admin/login")
//                .usernameParameter("username").passwordParameter("password")
//                .permitAll() // 登录请求url
//                .successHandler(successHandler)// 成功处理类
//                .failureHandler(failHandler) // 失败
//
//                .and()
//                .headers().frameOptions().disable() // 允许网页iframe //关闭X-Frame-Options
//
//                //登出设置
//                .and()
//                .logout()
//                .logoutUrl("/admin/logout")
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .permitAll()
//
////                //身份证校验
////                .and()
////                .authorizeRequests()  // 任何请求
////                .anyRequest()                // 需要身份认证
////                .authenticated()
//
//                .and()
//                .authorizeRequests()//启用基于 HttpServletRequest 的访问限制，开始配置哪些URL需要被保护、哪些不需要被保护
//                .antMatchers(ignoredUrlsConfig.getUrls().toArray(new String[]{})).permitAll()//未登陆用户允许的请求
//                //前端登陆，不能返回后端接口，后端访问，不能访问前端接口
//                .anyRequest().hasAnyRole("ADMIN")//其他路径下的请求全部需要登陆，获得ADMIN角色
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
//                // 前后端分离采用JWT 不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//
//                //Spring Security 实战干货：自定义异常处理 https://juejin.im/post/5dc3528c5188255f6b6756c3
//                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
//                .and()
//                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
//
//                //图形验证码过滤器
//                .and()
//                // 添加自定义权限过滤器
//                // 图形验证码过滤器
////                .addFilterBefore(imageValidateFilter, UsernamePasswordAuthenticationFilter.class)
//                // 添加JWT过滤器 除已配置的其它请求都需经过此过滤器
//                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }

}
