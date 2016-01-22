package com.kld.gsm.Socket.protocol;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/31 16:33
 * @Description: 容积表
 */
public class CapacTabMsg {
    private int operation;//操作类型  0上传/1下发
    private int oilNo;//油罐号
    private String strVersion;//版本号
    private List<CapacTabBMsg> capacTabBMsgs;

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public int getOilNo() {
        return oilNo;
    }

    public void setOilNo(int oilNo) {
        this.oilNo = oilNo;
    }

    public List<CapacTabBMsg> getCapacTabBMsgs() {
        return capacTabBMsgs;
    }

    public void setCapacTabBMsgs(List<CapacTabBMsg> capacTabBMsgs) {
        this.capacTabBMsgs = capacTabBMsgs;
    }

    public String getStrVersion() {
        return strVersion;
    }

    public void setStrVersion(String strVersion) {
        this.strVersion = strVersion;
    }

    @Override
    public String toString() {
        return "CapacTabMsg{" +
                "operation=" + operation +
                ", oilNo=" + oilNo +
                ", strVersion='" + strVersion + '\'' +
                ", capacTabBMsgs=" + capacTabBMsgs +
                '}';
    }
}
