package com.kld.gsm.center.web.exception;


/***
 * Finance base DzException
 *
 * @author  miaozhiyong@autohome.com.cn
 * @since 2015-02-26
 * @version 1.0
 *
 */
public class BaseException extends Exception {

    /**
     *
     */
    private static final long	serialVersionUID				= 2723104623621180899L;

    /** 未知异常 */
    public static final int		UNKNOWN_EXCEPTION				= 0;

    /** 参数异常 */
    public static final int		ILLEGAL_PARAM_EXCEPTION			= 1000;

    /** 参数长度异常 */
    public static final int		ILLEGAL_LENGTH_EXCEPTION		= 1001;

    /** 参数值异常 */
    public static final int		ILLEGAL_VALUE_EXCEPTION			= 1002;

    /** 权限异常 */
    public static final int		FORBIT_EXCEPTION				= 2000;

    /** 操作异常 */
    public static final int		OPERATION_EXECEPTION			= 3000;

    /** 数据校验异常 */
    public static final int		FRONT_VALIDATION_EXECEPTION		= 4000;

    /** 上传文件数据校验异常 */
    public static final int		UPLOAD_VALIDATION_EXECEPTION	= 5000;

    public static final int		WEBSERVICE_EXCEPTION			= 6000;

    /** 异常码 */
    private int					code							= UNKNOWN_EXCEPTION;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(int code) {
        super();
        this.code = code;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

