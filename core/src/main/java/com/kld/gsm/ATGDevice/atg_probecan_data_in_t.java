package com.kld.gsm.ATGDevice;

/**
 * Created by luyan on 15/10/21.
 * 探棒油管配置
 */
public class atg_probecan_data_in_t {
    public String strDeviceModel;
    public String strProbeNo;
    public int uProbePort;
    public int uOilCanNo;
    public String uOilType;
    public String strOilNo;
    public String strOilName;

    public String getStrDeviceModel() {
        return strDeviceModel;
    }

    public void setStrDeviceModel(String strDeviceModel) {
        this.strDeviceModel = strDeviceModel;
    }

    public String getStrProbeNo() {
        return strProbeNo;
    }

    public void setStrProbeNo(String strProbeNo) {
        this.strProbeNo = strProbeNo;
    }

    public int getuProbePort() {
        return uProbePort;
    }

    public void setuProbePort(int uProbePort) {
        this.uProbePort = uProbePort;
    }

    public int getuOilCanNo() {
        return uOilCanNo;
    }

    public void setuOilCanNo(int uOilCanNo) {
        this.uOilCanNo = uOilCanNo;
    }

    public String getuOilType() {
        return uOilType;
    }

    public void setuOilType(String uOilType) {
        this.uOilType = uOilType;
    }

    public String getStrOilNo() {
        return strOilNo;
    }

    public void setStrOilNo(String strOilNo) {
        this.strOilNo = strOilNo;
    }

    public String getStrOilName() {
        return strOilName;
    }

    public void setStrOilName(String strOilName) {
        this.strOilName = strOilName;
    }
}
