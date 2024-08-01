package com.fushun.framework.sample.security.admin.filter;

import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.sample.security.admin.model.AdminLoginInfo;
import com.fushun.framework.security.service.TokenService;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.framework.util.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口登陆token校验
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);


    private TokenService tokenService=SpringContextUtil.getBean(TokenService.class);


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        AdminLoginInfo adminLoginInfo =  tokenService.getLoginUser();
        if (StringUtils.isEmpty(adminLoginInfo)) {
            Map<String, Object> headersMap = new HashMap<>();
            Collections.list(request.getHeaderNames())
                    .stream()
                    .forEach(name -> headersMap.put(name, request.getHeader(name)));
            // 记录下请求内容
            logger.error("未登陆 URL:[{}],hears:[{}],HTTP_METHOD:[{}],IP:[{}],PATH:[{}],ARGS[{}]",
                    request.getRequestURL().toString(),
                    JsonUtil.toJson(headersMap),
                    request.getMethod(),
                    request.getRemoteAddr(),
                    request.getServletPath(),
                    JsonUtil.classToJson(request.getParameterMap())
            );
            chain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = this.getAuthentication(adminLoginInfo,response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error("发生异常", e);
        }
        super.doFilterInternal(request, response, chain);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(AdminLoginInfo adminLoginInfo, HttpServletResponse response) {

        // 若未保存登录状态重新设置失效时间
        tokenService.refreshToken(adminLoginInfo);
        if (StringUtils.isNotEmpty(adminLoginInfo.getUsername())) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(adminLoginInfo, null, adminLoginInfo.getAuthorities());
            return authenticationToken;
        }
        return null;
    }
}
