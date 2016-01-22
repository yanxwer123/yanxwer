/**
 * 58.com Inc.
 * Copyright (c) 2005-2015 All Rights Reserved.
 */
package com.kld.gsm.center.web.security;

import com.kld.gsm.center.common.EncoderHandler;
import com.kld.gsm.center.domain.Sys_user;
import com.kld.gsm.center.service.UserService;
import com.kld.gsm.center.web.webcontroller.WebBaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 
 * @author yangjian
 * @version $Id: MyAuthenticationSuccessHandler.java,v 1.1 2015/12/15 12:22:11 chenzihong Exp $
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    static final Log LOG = LogFactory.getLog(MyAuthenticationSuccessHandler.class.getName());
    
    @Resource
    private UserService userService;
    /** 
     * @see AuthenticationSuccessHandler#onAuthenticationSuccess(HttpServletRequest, HttpServletResponse, Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException,
            ServletException {
            String ru = (String)request.getSession().getAttribute("ru");
            request.getSession().removeAttribute("ru"); 
            if(StringUtils.isEmpty(ru)){
                ru = "/web/index";
            }
            
            String userName = WebBaseController.getUserDetails().getUsername();
            Sys_user user = userService.selectUserMoreInfo(userName);
            request.getSession(true).setAttribute("username", userName);
            request.getSession(true).setAttribute("rname", user.getRealname());
            request.getSession().setAttribute("oucode", user.getOucode());
            request.getSession(true).setAttribute("ouname", user.getOuname());
            request.getSession(true).setAttribute("oulevel", user.getOulevel());
            String authorized = EncoderHandler.encodeByMD5(userName + "@jifen@ssologin");
            Cookie c = new Cookie("CookieAuthorize", "userid="+userName+"&authorized="+authorized);
            c.setMaxAge( 60 * 60);
            response.addCookie(c);
            response.sendRedirect(ru);
    }  
}
