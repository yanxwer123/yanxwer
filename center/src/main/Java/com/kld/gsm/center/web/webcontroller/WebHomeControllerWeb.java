package com.kld.gsm.center.web.webcontroller;



import com.kld.gsm.center.common.VerifyCodeUtils;
import com.kld.gsm.center.domain.Sys_user;
import com.kld.gsm.center.service.UserService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
Created BY niyang
Created Date 2015/11/23
*/
@Controller
@ApiIgnore
@RequestMapping({"/web"})
public class WebHomeControllerWeb extends WebBaseController {

    @Resource
    private UserService userService;

    static final Log LOG = LogFactory.getLog(WebHomeControllerWeb.class);

    @RequestMapping("/login")
    public ModelAndView Login(HttpServletRequest request,HttpServletResponse response)
    {
        ModelMap result = new ModelMap();
        String msg = request.getParameter("msg");
        result.put("msg", msg);
        return new ModelAndView("login");
    }
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response)
    {
        return new ModelAndView("/login");
    }

    @RequestMapping("/registerCheck")
    @ResponseBody
    public ModelAndView registerCheck(@RequestParam(value = "j_username", required = false) String j_username,
                                      @RequestParam(value = "j_password", required = false) String j_password){
           ModelMap result = new ModelMap();
            if(null==j_username || null==j_password || "".equals(j_username) || "".equals(j_password)){
                result.put("msg", "请输入用户名或密码");
                System.out.println("请输入用户名或密码");
            }else {
                String realname=j_username;
                String pwd=md5(j_password);
                //根据用户名查询用户username(即用户的id)
                String userID=userService.selectUserIdByRealname(realname);
                System.out.println("用户名"+userID);
                //查询用户和密码
                Sys_user userPwd=userService.querySysUserByUserName(userID);
                if(userPwd==null){
                    System.out.println("登录失败userPwd为空");
                    result.put("msg", "登录失败！用户名或密码有误");
                }else{
                    if(!pwd.equals(userPwd.getPwd())){
                        System.out.println("登录失败"+userPwd.getPwd());
                        result.put("msg", "登录失败！用户名或密码有误");
                    }else {
                        //存入session
                        System.out.println("进去" + userPwd.getPwd());

                        return new ModelAndView("/index");
                    }
                }

            }
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
        HttpSession session = request.getSession();
        session.setAttribute("realname", "admin");
        session.setAttribute("userID", "1");
        //判断session是否有值
        String name=(String)session.getAttribute("name");
       /* if(name==null || name==""){
            name="管理员";
        }*/
       // ArrayList<String>  list =   GetUserFuncList();

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



    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

}
