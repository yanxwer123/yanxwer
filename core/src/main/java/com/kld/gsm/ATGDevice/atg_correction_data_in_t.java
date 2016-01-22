package com.kld.gsm.ATGDevice;

/**
 * Created by luyan on 15/10/21.
 * 探棒校正输入参数项
 */
public class atg_correction_data_in_t {
    public String strDeviceModel;//设备型号
    public String strProbeNo;//探棒编号
    public String uOilTy;//油品类型
    public double fOilCorrection;//油位0点校正（毫米）
    public double fWaterCorrection;//水位0点校正（毫米）
    public double fProbeOffset;//探棒偏移（毫米）
    public double fTiltOffset;//倾斜偏移（毫米）
    public double fTemp1;//温度1实测值(℃)
    public double fProbeTemp1	;//温度1探棒测量值(℃)
    public double fTemp2;//温度2实测值(℃)
    public double fProbeTemp2	;//温度2探棒测量值(℃)
    public double fTemp3;//温度3实测值(℃)
    public double fProbeTemp3	;//温度3探棒测量值(℃)
    public double fTemp4;//温度4实测值(℃)
    public double fProbeTemp4	;//温度4探棒测量值(℃)
    public double fTemp5;//温度5实测值(℃)
    public double fProbeTemp5	;//温度5探棒测量值(℃)
    public double fInitDesnsity;//初始密度(kg/m3)
    public double fInitHighDiff;//初始高度差(mm)油位与密度位之间的高度差
    public double fCorrectionFactor;//密度的修正系数

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

    public String getuOilTy() {
        return uOilTy;
    }

    public void setuOilTy(String uOilTy) {
        this.uOilTy = uOilTy;
    }

    public double getfOilCorrection() {
        return fOilCorrection;
    }

    public void setfOilCorrection(double fOilCorrection) {
        this.fOilCorrection = fOilCorrection;
    }

    public double getfWaterCorrection() {
        return fWaterCorrection;
    }

    public void setfWaterCorrection(double fWaterCorrection) {
        this.fWaterCorrection = fWaterCorrection;
    }

    public double getfProbeOffset() {
        return fProbeOffset;
    }

    public void setfProbeOffset(double fProbeOffset) {
        this.fProbeOffset = fProbeOffset;
    }

    public double getfTiltOffset() {
        return fTiltOffset;
    }

    public void setfTiltOffset(double fTiltOffset) {
        this.fTiltOffset = fTiltOffset;
    }

    public double getfTemp1() {
        return fTemp1;
    }

    public void setfTemp1(double fTemp1) {
        this.fTemp1 = fTemp1;
    }

    public double getfProbeTemp1() {
        return fProbeTemp1;
    }

    public void setfProbeTemp1(double fProbeTemp1) {
        this.fProbeTemp1 = fProbeTemp1;
    }

    public double getfTemp2() {
        return fTemp2;
    }

    public void setfTemp2(double fTemp2) {
        this.fTemp2 = fTemp2;
    }

    public double getfProbeTemp2() {
        return fProbeTemp2;
    }

    public void setfProbeTemp2(double fProbeTemp2) {
        this.fProbeTemp2 = fProbeTemp2;
    }

    public double getfTemp3() {
        return fTemp3;
    }

    public void setfTemp3(double fTemp3) {
        this.fTemp3 = fTemp3;
    }

    public double getfProbeTemp3() {
        return fProbeTemp3;
    }

    public void setfProbeTemp3(double fProbeTemp3) {
        this.fProbeTemp3 = fProbeTemp3;
    }

    public double getfTemp4() {
        return fTemp4;
    }

    public void setfTemp4(double fTemp4) {
        this.fTemp4 = fTemp4;
    }

    public double getfProbeTemp4() {
        return fProbeTemp4;
    }

    public void setfProbeTemp4(double fProbeTemp4) {
        this.fProbeTemp4 = fProbeTemp4;
    }

    public double getfTemp5() {
        return fTemp5;
    }

    public void setfTemp5(double fTemp5) {
        this.fTemp5 = fTemp5;
    }

    public double getfProbeTemp5() {
        return fProbeTemp5;
    }

    public void setfProbeTemp5(double fProbeTemp5) {
        this.fProbeTemp5 = fProbeTemp5;
    }

    public double getfInitDesnsity() {
        return fInitDesnsity;
    }

    public void setfInitDesnsity(double fInitDesnsity) {
        this.fInitDesnsity = fInitDesnsity;
    }

    public double getfInitHighDiff() {
        return fInitHighDiff;
    }

    public void setfInitHighDiff(double fInitHighDiff) {
        this.fInitHighDiff = fInitHighDiff;
    }

    public double getfCorrectionFactor() {
        return fCorrectionFactor;
    }

    public void setfCorrectionFactor(double fCorrectionFactor) {
        this.fCorrectionFactor = fCorrectionFactor;
    }
}
