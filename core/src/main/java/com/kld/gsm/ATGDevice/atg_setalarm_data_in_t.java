package com.kld.gsm.ATGDevice;

/**
 * Created by luyan on 15/10/21.
 */
public class atg_setalarm_data_in_t {
    public int uOilCanNO;
    public double fLowWarning;//低液位预警
    public double fLowAlarm;//低液位报警
    public double fHighWarning;//高液位预警
    public double fHighAlarm;//高液位报警,
    public double fWaterWarning;//高水位预警
    public double fWaterAlarm;//高水位报警
    public double fThiefAlarm;//
    public double fLeakAlarm;//
    public double fPercolatingAlarm;//
    public double fHighTempAlarm;//高温报警
    public double fLowTempAlarm;//低温报警

    public String toString2() {
        return "atg_setalarm_data_in_t{" +"\r\n"+
                "uOilCanNO=" + uOilCanNO +"\r\n"+
                ", fLowWarning=" + fLowWarning +"\r\n"+
                ", fLowAlarm=" + fLowAlarm +"\r\n"+
                ", fHighWarning=" + fHighWarning +"\r\n"+
                ", fHighAlarm=" + fHighAlarm +"\r\n"+
                ", fWaterWarning=" + fWaterWarning +"\r\n"+
                ", fWaterAlarm=" + fWaterAlarm +"\r\n"+
                ", fThiefAlarm=" + fThiefAlarm +"\r\n"+
                ", fLeakAlarm=" + fLeakAlarm +"\r\n"+
                ", fPercolatingAlarm=" + fPercolatingAlarm +"\r\n"+
                ", fHighTempAlarm=" + fHighTempAlarm +"\r\n"+
                ", fLowTempAlarm=" + fLowTempAlarm +"\r\n"+
                '}';
    }
}
