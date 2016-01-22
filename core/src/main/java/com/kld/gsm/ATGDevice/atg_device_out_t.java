package com.kld.gsm.ATGDevice;

import java.util.List;

/**
 * Created by luyan on 15/10/21.
 */
public class atg_device_out_t {
    public String strDeviceModel;
    public String strEquipCode;
    public String strSysVersion;
    public String strMakeDate;
    public int uRetCount;
    public List<atg_device_data_out_t> pDeviceData;
}
