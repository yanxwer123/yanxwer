package com.kld.gsm.center.web.exception;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;

import java.util.Map;


/**
 * @ClassName: FrontValidationException
 * @Description:
 * @author  二手车-苗治勇
 * @date   2015-02-26
 *
 */
public class FrontValidationException  extends  BaseWebException{

    private static final long	serialVersionUID	= -125073506113127808L;
    protected final Logger log					= Logger.getLogger(FrontValidationException.class);

    /**
     * 整合spring mvc校验功能 统一处理
     * */
    private Errors errors;

    private String resultCode;

    private Map<String, String> paramMap;

    public String getResultCode() {
        return resultCode;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public static final String FRONT_MESSAGE_EXCEPTION	= "前端数据校验异常。";

    public Errors getErrors() {
        return errors;
    }

    public FrontValidationException() {
        super();
    }

    public FrontValidationException(String errorMessage) {
        super(errorMessage);
        this.setErrorMessage(errorMessage);
    }

    public FrontValidationException(Exception e) {
        super(e.getMessage(), e);
    }

    public FrontValidationException(int errorCode, Errors errors) {
        super(FRONT_MESSAGE_EXCEPTION);
        this.setCode(errorCode);
        this.errors = errors;
    }

    public FrontValidationException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.setCode(errorCode);
        this.setErrorMessage(errorMessage);
    }

    public FrontValidationException(String errorMessage, String resultCode, Map<String, String> paramMap) {
        super(errorMessage);
        this.setErrorMessage(errorMessage);
        this.resultCode = resultCode;
        this.paramMap = paramMap;
    }


}
