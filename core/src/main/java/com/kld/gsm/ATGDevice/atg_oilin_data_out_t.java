package com.kld.gsm.ATGDevice;

/**
 * Created by luyan on 15/10/21.
 * 进油信息输出
 */
public class atg_oilin_data_out_t {
    public int uOilCanNO;
    public String strStartDate;
    public String strEndDate;
    public String strStartTime;
    public String strEndTime;
    public double fStartCubage;
    public double fStartStandCubage;
    public double fStartOilHeight;
    public double fStartWaterHeight;
    public double fStartOilTemp;
    public double fStartOilTemp1;
    public double fStartOilTemp2;
    public double fStartOilTemp3;
    public double fStartOilTemp4;
    public double fStartOilTemp5;
    public double fEndCubage;
    public double fEndStandCubage;
    public double fEndOilHeight;
    public double fEndWaterHeight;
    public double fEndOilTemp;
    public double fEndOilTemp1;
    public double fEndOilTemp2;
    public double fEndOilTemp3;
    public double fEndOilTemp4;
    public double fEndOilTemp5;
    public double fEmptyCubage;
    public double fApparentDensity;
    public double fStandDensity;

    public String toString2() {
        return "atg_oilin_data_out_t{" + "\r\n"+
                "uOilCanNO=" + uOilCanNO + "\r\n"+
                ", strStartDate='" + strStartDate + '\'' + "\r\n"+
                ", strEndDate='" + strEndDate + '\'' + "\r\n"+
                ", strStartTime='" + strStartTime + '\'' + "\r\n"+
                ", strEndTime='" + strEndTime + '\'' + "\r\n"+
                ", fStartCubage=" + fStartCubage + "\r\n"+
                ", fStartStandCubage=" + fStartStandCubage + "\r\n"+
                ", fStartOilHeight=" + fStartOilHeight + "\r\n"+
                ", fStartWaterHeight=" + fStartWaterHeight + "\r\n"+
                ", fStartOilTemp=" + fStartOilTemp + "\r\n"+
                ", fStartOilTemp1=" + fStartOilTemp1 + "\r\n"+
                ", fStartOilTemp2=" + fStartOilTemp2 + "\r\n"+
                ", fStartOilTemp3=" + fStartOilTemp3 + "\r\n"+
                ", fStartOilTemp4=" + fStartOilTemp4 + "\r\n"+
                ", fStartOilTemp5=" + fStartOilTemp5 + "\r\n"+
                ", fEndCubage=" + fEndCubage + "\r\n"+
                ", fEndStandCubage=" + fEndStandCubage + "\r\n"+
                ", fEndOilHeight=" + fEndOilHeight + "\r\n"+
                ", fEndWaterHeight=" + fEndWaterHeight + "\r\n"+
                ", fEndOilTemp=" + fEndOilTemp + "\r\n"+
                ", fEndOilTemp1=" + fEndOilTemp1 + "\r\n"+
                ", fEndOilTemp2=" + fEndOilTemp2 + "\r\n"+
                ", fEndOilTemp3=" + fEndOilTemp3 + "\r\n"+
                ", fEndOilTemp4=" + fEndOilTemp4 + "\r\n"+
                ", fEndOilTemp5=" + fEndOilTemp5 + "\r\n"+
                ", fEmptyCubage=" + fEmptyCubage + "\r\n"+
                ", fApparentDensity=" + fApparentDensity + "\r\n"+
                ", fStandDensity=" + fStandDensity + "\r\n"+
                '}';
    }
}
