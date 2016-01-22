package com.kld.gsm.center.web.exception;

import org.apache.log4j.Logger;

/**
 * @ClassName: BaseException
 * @Description:
 * @author  二手车-苗治勇
 * @date   2015-02-26
 *
 */
public class BaseWebException extends BaseException {

    private static final long	serialVersionUID	= 807477419235941651L;

    protected final Logger log					= Logger.getLogger(BaseWebException.class);

    private String errorMessage;

    public BaseWebException() {
        super();
    }

    public BaseWebException(String message) {
        super(message);
    }

    public BaseWebException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

