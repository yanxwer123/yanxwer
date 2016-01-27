package com.kld.gsm.ATGDevice;

/**
 * Created by luyan on 15/10/21.
 * 实时库存输出数据
 */
public class atg_stock_data_out_t {
    public int    uOilCanNo; //罐号
    public String strDate; //日期
    public String strTime; //时间
    public double fOilCubage;//净油体积   标准体积/油水总高
    public double fOilStandCubage;//标准体积，单位：升
    public double fEmptyCubage;//空体积  ，单位：升
    public double fTotalHeight;//油水总高，单位：毫米
    public double fWaterHeight;//水高 ，单位：毫米
    public double fOilTemp;//平均温度，单位：摄氏度
    public double fOilTemp1;//5点温度
    public double fOilTemp2;//
    public double fOilTemp3;//fEmptyCubage
    public double fOilTemp4;//
    public double fOilTemp5;//
    public double fWaterBulk;//水体积，单位：升
    public double fApparentDensity;//视密度
    public double fStandDensity;//标准密度

    @Override
    public String toString() {
        return "atg_stock_data_out_t{" +
                "uOilCanNo=" + uOilCanNo +
                ", strDate='" + strDate + '\'' +
                ", strTime='" + strTime + '\'' +
                ", fOilCubage=" + fOilCubage +
                ", fOilStandCubage=" + fOilStandCubage +
                ", fEmptyCubage=" + fEmptyCubage +
                ", fTotalHeight=" + fTotalHeight +
                ", fWaterHeight=" + fWaterHeight +
                ", fOilTemp=" + fOilTemp +
                ", fOilTemp1=" + fOilTemp1 +
                ", fOilTemp2=" + fOilTemp2 +
                ", fOilTemp3=" + fOilTemp3 +
                ", fOilTemp4=" + fOilTemp4 +
                ", fOilTemp5=" + fOilTemp5 +
                ", fWaterBulk=" + fWaterBulk +
                ", fApparentDensity=" + fApparentDensity +
                ", fStandDensity=" + fStandDensity +
                '}';
    }
    public String toString2() {
        return uOilCanNo +
                "," + strDate +
                "," + strTime +
                "," + fOilCubage +
                "," + fOilStandCubage +
                "," + fEmptyCubage +
                "," + fTotalHeight +
                "," + fWaterHeight +
                "," + fOilTemp +
                "," + fOilTemp1 +
                "," + fOilTemp2 +
                "," + fOilTemp3 +
                "," + fOilTemp4 +
                "," + fOilTemp5 +
                "," + fWaterBulk +
                "," + fApparentDensity +
                "," + fStandDensity ;
    }
    public String toString3() {
        return "atg_stock_data_out_t{" + "\r\n"+
                "uOilCanNo=" + uOilCanNo + "\r\n"+
                ", strDate='" + strDate + '\'' + "\r\n"+
                ", strTime='" + strTime + '\'' + "\r\n"+
                ", fOilCubage=" + fOilCubage + "\r\n"+
                ", fOilStandCubage=" + fOilStandCubage + "\r\n"+
                ", fEmptyCubage=" + fEmptyCubage + "\r\n"+
                ", fTotalHeight=" + fTotalHeight + "\r\n"+
                ", fWaterHeight=" + fWaterHeight + "\r\n"+
                ", fOilTemp=" + fOilTemp + "\r\n"+
                ", fOilTemp1=" + fOilTemp1 + "\r\n"+
                ", fOilTemp2=" + fOilTemp2 + "\r\n"+
                ", fOilTemp3=" + fOilTemp3 + "\r\n"+
                ", fOilTemp4=" + fOilTemp4 + "\r\n"+
                ", fOilTemp5=" + fOilTemp5 + "\r\n"+
                ", fWaterBulk=" + fWaterBulk + "\r\n"+
                ", fApparentDensity=" + fApparentDensity + "\r\n"+
                ", fStandDensity=" + fStandDensity + "\r\n"+
                '}';
    }
}
