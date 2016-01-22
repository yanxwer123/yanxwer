package com.kld.gsm.ATGDevice;

/**
 * Created by luyan on 15/10/21.
 */
public class atg_alarm_data_out_t {
    public int uOilCanNO;
    public String strDate;
    public String strTime;
    public String strAlarmType;
    public String strAlarmState;
    public String strRemark;
    public String strReport;

    public String toString2() {
        return "atg_alarm_data_out_t{" +"\r\n"+
                "uOilCanNO=" + uOilCanNO +"\r\n"+
                ", strDate='" + strDate + '\'' +"\r\n"+
                ", strTime='" + strTime + '\'' +"\r\n"+
                ", strAlarmType='" + strAlarmType + '\'' +"\r\n"+
                ", strAlarmState='" + strAlarmState + '\'' +"\r\n"+
                ", strRemark='" + strRemark + '\'' +"\r\n"+
                ", strReport='" + strReport + '\'' +"\r\n"+
                '}';
    }
}
