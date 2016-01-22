/**
 * 58.com Inc.
 * Copyright (c) 2005-2015 All Rights Reserved.
 */
package com.kld.gsm.center.web.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @author yangjian
 * @version $Id: MyLoginUrlAuthenticationEntryPoint.java,v 1.1 2015/12/15 12:22:11 chenzihong Exp $
 */
public class MyLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    static final Log LOG = LogFactory.getLog(MyLoginUrlAuthenticationEntryPoint.class.getName());
    
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException,
            ServletException {
        String returnUrl = buildHttpReturnUrlForRequest(request);
        request.getSession().setAttribute("ru", returnUrl);
        super.commence(request, response, authException);
    }

    protected String buildHttpReturnUrlForRequest(HttpServletRequest request) throws IOException,
            ServletException {

        RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
        urlBuilder.setScheme("http");
        urlBuilder.setServerName(request.getServerName());
        urlBuilder.setPort(request.getServerPort());
        urlBuilder.setContextPath(request.getContextPath());
        urlBuilder.setServletPath(request.getServletPath());
        urlBuilder.setPathInfo(request.getPathInfo());
        urlBuilder.setQuery(request.getQueryString());

        return urlBuilder.getUrl();
    }
}
