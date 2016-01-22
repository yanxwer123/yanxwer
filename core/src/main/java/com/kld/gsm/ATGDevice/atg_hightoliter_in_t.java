package com.kld.gsm.ATGDevice;

import java.util.List;

/**
 * Created by luyan on 15/10/21.
 */
public class atg_hightoliter_in_t {
    public int uCount;
    public double fTotalHeight;
    public double fWaterHeight;
    public double fOilTemp;
    public double fOilTemp1;
    public double fOilTemp2;
    public double fOilTemp3;
    public double fOilTemp4;
    public double fOilTemp5;
    public List<atg_capacity_data_in_t> pCapacityData;

    public int getuCount() {
        return uCount;
    }

    public void setuCount(int uCount) {
        this.uCount = uCount;
    }

    public double getfTotalHeight() {
        return fTotalHeight;
    }

    public void setfTotalHeight(double fTotalHeight) {
        this.fTotalHeight = fTotalHeight;
    }

    public double getfWaterHeight() {
        return fWaterHeight;
    }

    public void setfWaterHeight(double fWaterHeight) {
        this.fWaterHeight = fWaterHeight;
    }

    public double getfOilTemp() {
        return fOilTemp;
    }

    public void setfOilTemp(double fOilTemp) {
        this.fOilTemp = fOilTemp;
    }

    public double getfOilTemp1() {
        return fOilTemp1;
    }

    public void setfOilTemp1(double fOilTemp1) {
        this.fOilTemp1 = fOilTemp1;
    }

    public double getfOilTemp2() {
        return fOilTemp2;
    }

    public void setfOilTemp2(double fOilTemp2) {
        this.fOilTemp2 = fOilTemp2;
    }

    public double getfOilTemp3() {
        return fOilTemp3;
    }

    public void setfOilTemp3(double fOilTemp3) {
        this.fOilTemp3 = fOilTemp3;
    }

    public double getfOilTemp4() {
        return fOilTemp4;
    }

    public void setfOilTemp4(double fOilTemp4) {
        this.fOilTemp4 = fOilTemp4;
    }

    public double getfOilTemp5() {
        return fOilTemp5;
    }

    public void setfOilTemp5(double fOilTemp5) {
        this.fOilTemp5 = fOilTemp5;
    }

    public List<atg_capacity_data_in_t> getpCapacityData() {
        return pCapacityData;
    }

    public void setpCapacityData(List<atg_capacity_data_in_t> pCapacityData) {
        this.pCapacityData = pCapacityData;
    }
}
