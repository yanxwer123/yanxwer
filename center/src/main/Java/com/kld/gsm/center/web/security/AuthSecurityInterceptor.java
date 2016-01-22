package com.kld.gsm.center.web.security;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthSecurityInterceptor extends HandlerInterceptorAdapter {

//    @Resource
//    AuthorDao authorDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handler2 = (HandlerMethod) handler;
        AuthSecurity authSecurity = handler2.getMethodAnnotation(AuthSecurity.class);

        if (authSecurity != null) {
            //需要进行权限验证的Url
            String url = authSecurity.value();
            boolean result = false;
            result = ResourceFilterStrategy(request, response, url);
            return result;

        } else
            return true;
    }

    /**
     * 判断是否有资源权限
     *
     * @param request
     * @param response
     * @param mapping
     * @return
     * @throws Exception
     */
    private boolean ResourceFilterStrategy(HttpServletRequest request, HttpServletResponse response, String mapping) throws Exception {

        int editUserId = 0;
        String pw = "";
        boolean isLogin = false;

        //mapping是action里指定的 authsecurity的url，通过权限库可以在此进行验证

        return true;
    }
}