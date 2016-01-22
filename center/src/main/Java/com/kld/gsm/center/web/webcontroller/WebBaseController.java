package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.web.exception.FrontValidationException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.NestedServletException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by miaozy on 15/2/25.
 */
@Component
public class WebBaseController {

    protected final Logger logger	 = Logger.getLogger(WebBaseController.class);
    protected static final int PAGESIZE = 20;
    protected static final String ERROR_CODE = "errorCode";
    protected static final String ERROR_MESSAGE = "errorMessage";
    protected static final String DEFAULT_ERROR_ATTRIBUTE = "error/error";

    protected static final Integer DEFAULTP_AGESIZE = 10;
    protected static final String RESULT = "result";
    protected static final String VALIDATE_TYPE = "resultType";
    protected static final String SUCCESS = "success";
    protected static final String MESSAGE = "message";

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected int pageNum;
    protected int pageSize;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    //目前还没有存储起这个对象

    /**
     * 获取当前登录用户id,如果不存在则返回0
     * @return
     */
    public  int   getUserID() {
        return   0;
    }

    /***
     * 获取当亲爱的登录用户的用户名，如果不存在则返回空
     * @return
     */
    public String getUserName(){
        String uname="";
        UserDetails userDetails=getUserDetails();
        if(userDetails!=null){
            uname=getUserDetails().getUsername();
        }
        return uname;
    }

    /**
     * 获取当前登录用户的权限列表
     * @return
     */
    public ArrayList<String> GetUserFuncList()
    {
        UserDetails userDetails = getUserDetails();
        ArrayList<String> list = new ArrayList<String>();
        if(userDetails!=null){

            for(GrantedAuthority auth : userDetails.getAuthorities()){
                list.add(auth.getAuthority());
            }

        }
        return list;
    }

    /**
     * 获取用户所在的机构
     * @return
     */
    public String getUserOuCode(){
        String oucode="";
        Object object= request.getSession(true).getAttribute("oucode");
        if(object!=null){
            oucode=object.toString();
        }else{
            oucode="-1";
        }

        return oucode;
    }

    /**
     * 获取用户所在的机构级别
     * @return
     */
    public String getUserOuLevel(){
        String oulevel="";
        Object object= request.getSession(true).getAttribute("oulevel");
        if(object!=null){
            oulevel=object.toString();
        }else{
            oulevel="0";
        }

        return oulevel;
    }

    public static UserDetails getUserDetails(){
        SecurityContext sc = SecurityContextHolder.getContext();
        if(sc!=null&&sc.getAuthentication()!=null){
            Object userDetails = sc.getAuthentication().getPrincipal();
            if(userDetails!=null&&userDetails instanceof UserDetails){
                return (UserDetails)userDetails;
            }
        }
        return null;
    }

    /**
     * 统一的异常处理?
     *
     * @param ex
     * @param request
     * @return
     * @throws IOException
     * @throws NestedServletException
     */
    @ExceptionHandler(value = {Exception.class, FrontValidationException.class})
    public void actionExceptionHandler(Throwable ex, HttpServletRequest request, HttpServletResponse response)
            throws IOException, NestedServletException {


        JSONObject jsonObject = null;

        String requestType = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(requestType);


        if (ex instanceof FrontValidationException) {
            Errors errors = ((FrontValidationException) ex).getErrors();
            StringBuffer errorMessage = new StringBuffer();
            if (errors != null && errors.hasErrors()) {
                List<ObjectError> oeList = errors.getAllErrors();
                for (ObjectError oe : oeList) {
                    errorMessage.append(oe.getDefaultMessage()).append("</br>");
                }
                ((FrontValidationException) ex).setErrorMessage(errorMessage.toString());
            } else {
                errorMessage.append(ex.getMessage());
            }

            if (!isAjax) {
                redirectErrorPage(errorMessage.toString(), request, response);
                logger.error(ex);
                return;
            }

            // VALIDATE_TYPE
            jsonObject = this.setFailResult(new JSONObject(), errorMessage.toString(), "validate");

        }  else {
            if (!isAjax) {
                ex.printStackTrace();
                redirectErrorPage("网络异常，请稍后再试?", request, response);
                logger.error(ex);
                return;
            }
            jsonObject = this.setFailResult(new JSONObject(), "网络异常，请稍后再试�?");
            jsonObject.put("resultType", "DzException");
        }

        logger.error(ex);
        ex.printStackTrace();

        this.responseWrite(response, jsonObject);

    }

    private void redirectErrorPage(String message, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setAttribute("message", message);

        RequestDispatcher view = request.getRequestDispatcher("/error");
        try {
            view.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 操作成功返回的json数据
     *
     * @param jsonObject
     * @param message
     * @return
     */
    protected JSONObject setSuccessResult(JSONObject jsonObject, String message) {
        jsonObject.put(RESULT, SUCCESS);
        jsonObject.put(MESSAGE, message);
        return jsonObject;
    }

    protected JSONObject setSuccessResult(JSONObject jsonObject, String message, String paramMap) {
        jsonObject.put(RESULT, SUCCESS);
        jsonObject.put(MESSAGE, message);
        jsonObject.put("paramMap", paramMap);
        return jsonObject;
    }

    protected void responseWrite(HttpServletResponse response, JSONObject jsonObject) throws IOException {
        final int jsonToString = 3;

        response.setContentType("application/x-json; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(jsonObject.toString(jsonToString));
    }

    protected void responseWrite(HttpServletResponse response, String message) throws IOException {

        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(message);
    }

    protected void responseWrite(HttpServletResponse response, String message, int cacheTimesLong) throws IOException {
        Date date = new Date();
        response.setDateHeader("Last-Modified", date.getTime());
        response.setDateHeader("Expires", date.getTime() + cacheTimesLong);

        response.setHeader("Cache-Control", "public");
        response.setHeader("Pragma", "Pragma");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(message);
    }

    protected void responseWrite(HttpServletResponse response, JSONArray jsonObject) throws IOException {
        final int jsonToString = 3;
        response.getWriter().write(jsonObject.toString(jsonToString));
    }


    /**
     *
     * 操作失败返回的json数据
     *
     * @param jsonObject
     * @param message
     * @return
     */
    protected JSONObject setFailResult(JSONObject jsonObject, String message) {
        jsonObject.put(RESULT, "fail");
        jsonObject.put(MESSAGE, message);
        return jsonObject;
    }

    protected JSONObject setFailResult(JSONObject jsonObject, String message, String failType) {
        jsonObject.put(VALIDATE_TYPE, failType);
        jsonObject.put(RESULT, "fail");
        jsonObject.put(MESSAGE, message);
        return jsonObject;
    }

    public int getPageSize() {
        if("".equals(request.getParameter("rows"))||request.getParameter("rows")==null){
            return 10;//默认每页显示10�?
        }else{
            return Integer.parseInt(request.getParameter("rows"));
        }
	}

    public int getPageNum() {
//        int limit=getPageSize();
        int offset;
        if("".equals(request.getParameter("page"))||request.getParameter("page")==null){
            offset=0;
        }else{
            offset= Integer.parseInt(request.getParameter("page"));
        }
        return offset;
      /*  if(offset%limit==0){
			return offset/limit;
		}else{
			return offset/limit+1;
		}*/
	}

}
