package com.kld.gsm.center.web.security;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by miaozy on 15/4/27.
 */
public class SecurityServlet extends HttpServlet implements Filter {

    private static ApplicationContext ctx = null;


    public void init(FilterConfig filterConfig) throws ServletException {
        ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        int editUserId = 0;
        String userInfo = "";

        String url = httpServletRequest.getRequestURI();
        if(url.endsWith(".js") || url.endsWith(".css") || url.endsWith(".jsp"))
        {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //在这里根据当前的session或者cookie里的账号进行权限控制，如果权限控制失败，跳转至登录界面 httpServletResponse.sendRedirect("/Login");

        chain.doFilter(httpServletRequest, httpServletResponse);
    }
}
