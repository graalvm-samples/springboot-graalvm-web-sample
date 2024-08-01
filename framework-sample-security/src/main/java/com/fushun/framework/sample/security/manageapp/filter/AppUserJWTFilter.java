package com.fushun.framework.sample.security.manageapp.filter;

import com.alibaba.fastjson2.JSON;
import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.sample.security.manageapp.model.AppUserLoginInfo;
import com.fushun.framework.security.service.TokenService;
import com.fushun.framework.util.response.ApiResult;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.framework.util.util.ResponseUtil;
import com.fushun.framework.util.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//@Component
@Slf4j
public class AppUserJWTFilter extends BasicAuthenticationFilter {


    private TokenService tokenService= SpringContextUtil.getBean(TokenService.class);

    private Logger logger = LoggerFactory.getLogger(AppUserJWTFilter.class);

    public AppUserJWTFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        AppUserLoginInfo loginInfo =tokenService.getLoginUser();
        if (StringUtils.isEmpty(loginInfo)) {
            Map<String, Object> headersMap = new HashMap<>();
            Collections.list(request.getHeaderNames())
                    .stream()
                    .forEach(name -> headersMap.put(name, request.getHeader(name)));
            // 记录下请求内容
            logger.error("未登陆 URL:[{}],hears:[{}],http_method:[{}],ip:[{}],path:[{}],args[{}]",
                    request.getRequestURL().toString(),
                    JSON.toJSON(headersMap),
                    request.getMethod(),
                    request.getRemoteAddr(),
                    request.getServletPath(),
                    JsonUtil.classToJson(request.getParameterMap())
            );
            ResponseUtil.out(response, ApiResult.ofFail("LOGIN_TIMEOUT", "用户未登录"));
            return;
        }

        // 若未保存登录状态重新设置失效时间
//        if (StringUtils.isEmpty(loginInfo.getMobile()))  {
//            logger.error("用户手机号为空 token:{}",loginInfo.getToken());
//            ResponseUtil.out(response, ApiResult.ofFail("USER_MOBILE_NOT_EXIST", "用户手机号不存在"));
//            return;
//        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginInfo,"",loginInfo.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        chain.doFilter(request, response);
        super.doFilterInternal(request, response, chain);
    }
}
