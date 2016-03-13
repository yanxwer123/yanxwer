package com.kld.gsm.center.common;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dan on 2015/7/17.
 */
public class ImageFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String verifycode = request.getParameter("j_checkcode");
        if(StringUtils.isBlank(verifycode)){
            request.getRequestDispatcher("/web/login?msg=请输入验证码").forward(request, response);
        }else {
            String sessionyanz = (String) request.getSession(true).getAttribute("rand");
            if (verifycode.toLowerCase().equals(sessionyanz.toLowerCase())) {

                request.getRequestDispatcher("/web/registerCheck").forward(request, response);
                chain.doFilter(request, response);
            } else {
                request.getRequestDispatcher("/web/login?msg=验证码有误").forward(request, response);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
