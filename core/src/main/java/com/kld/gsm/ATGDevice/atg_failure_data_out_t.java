package com.kld.gsm.ATGDevice;

/**
 * Created by luyan on 15/10/21.
 */
public class atg_failure_data_out_t {
    public int uOilCanNO;
    public String strDate;
    public String strTime;
    public String strDeviceType;
    public String strFailureType;
    public String strEquipCode;
    public String strFailCode;
    public String strEquipBrand;
    public String strProbeModel;
    public String strRemark;
    public String strAlarmState;//故障状态

    public String toString2() {
        return "atg_failure_data_out_t{" +"\r\n"+
                "uOilCanNO=" + uOilCanNO +"\r\n"+
                ", strDate='" + strDate + '\'' +"\r\n"+
                ", strTime='" + strTime + '\'' +"\r\n"+
                ", strDeviceType='" + strDeviceType + '\'' +"\r\n"+
                ", strFailureType='" + strFailureType + '\'' +"\r\n"+
                ", strEquipCode='" + strEquipCode + '\'' +"\r\n"+
                ", strFailCode='" + strFailCode + '\'' +"\r\n"+
                ", strEquipBrand='" + strEquipBrand + '\'' +"\r\n"+
                ", strProbeModel='" + strProbeModel + '\'' +"\r\n"+
                ", strRemark='" + strRemark + '\'' +"\r\n"+
                ", strAlarmState='" + strAlarmState + '\'' +"\r\n"+
                '}';
    }
}
