package com.kld.gsm.center.web.webcontroller;



import com.kld.gsm.center.common.VerifyCodeUtils;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
Created BY niyang
Created Date 2015/11/23
*/
@Controller
@ApiIgnore
@RequestMapping({"/web"})
public class WebHomeControllerWeb extends WebBaseController {

    static final Log LOG = LogFactory.getLog(WebHomeControllerWeb.class);

    @RequestMapping("/login")
    public ModelAndView Login(HttpServletRequest request,HttpServletResponse response)
    {
        ModelMap result = new ModelMap();
        String msg = request.getParameter("msg");
        result.put("msg", msg);
        return new ModelAndView("login");
    }


    @RequestMapping("loginerror")
    public  ModelAndView LoginError()
    {
        ModelMap result = new ModelMap();
        result.put("msg", "登录失败！用户名或密码有误");
        return new ModelAndView("/login",result);
    }

    @ResponseBody
    @RequestMapping("message")
    public  String message(HttpServletRequest request,HttpServletResponse response)
    {
        return request.getParameter("msg");
    }

    @RequestMapping({"","/","/index"})
    public  ModelAndView Index()
    {

       // ArrayList<String> list =   GetUserFuncList();

        //return new ModelAndView("/index","list",list);
         return new ModelAndView("/index");
    }


    @RequestMapping("generateVerifyCode")
    public void generateVerifyCode(){
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        session.setAttribute("rand", verifyCode.toLowerCase());
        //生成图片
        int w = 200, h = 80;
        try {
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        }catch (IOException ex){
            logger.error("验证码生成图片异常！"+ex);
        }
    }

}
