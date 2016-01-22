package com.kld.gsm.ATGDevice;

import java.util.List;

/**
 * Created by yinzhiguang on 2015/10/28.
 */
public class atg_capacity_data_in_t {
    public int uOilCanNO;
    public String strVersion;
    public int uCapacitySize;
    public List<atg_capacitytable_data_in_t> pCapacityTableData;


    public int getuOilCanNO() {
        return uOilCanNO;
    }

    public void setuOilCanNO(int uOilCanNO) {
        this.uOilCanNO = uOilCanNO;
    }

    public int getuCapacitySize() {
        return uCapacitySize;
    }

    public void setuCapacitySize(int uCapacitySize) {
        this.uCapacitySize = uCapacitySize;
    }

    public List<atg_capacitytable_data_in_t> getpCapacityTableData() {
        return pCapacityTableData;
    }

    public void setpCapacityTableData(List<atg_capacitytable_data_in_t> pCapacityTableData) {
        this.pCapacityTableData = pCapacityTableData;
    }

    public String getStrVersion() {
        return strVersion;
    }

    public void setStrVersion(String strVersion) {
        this.strVersion = strVersion;
    }
}
