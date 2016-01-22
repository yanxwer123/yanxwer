package com.kld.gsm.Socket.protocol;


import com.kld.gsm.Socket.Constants;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:23
 * @Description:
 */
public class GasMsg {
    private String sCode = Constants.DEFAULT_SCODE;//起始符
    private String pidLenth = Constants.DEFAULT_PID_LENGTH;//业务编码长度
    private String pid;//业务编码内容
    private String dataLenth;//业务报文标识字节长度
     private String message;//业务报文

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getPidLenth() {
        return pidLenth;
    }

    public void setPidLenth(String pidLenth) {
        this.pidLenth = pidLenth;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDataLenth() {
        return dataLenth;
    }

    public void setDataLenth(String dataLenth) {
        this.dataLenth = dataLenth;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GasMessage{" +
                "sCode='" + sCode + '\'' +
                ", pidLenth='" + pidLenth + '\'' +
                ", pid='" + pid + '\'' +
                ", dataLenth='" + dataLenth + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
