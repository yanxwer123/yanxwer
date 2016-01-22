package com.kld.gsm.center.domain;

public class oss_sysmanage_Depot {
    private String ykId;

    private String ykName;

    public String getYkId() {
        return ykId;
    }

    public void setYkId(String ykId) {
        this.ykId = ykId == null ? null : ykId.trim();
    }

    public String getYkName() {
        return ykName;
    }

    public void setYkName(String ykName) {
        this.ykName = ykName == null ? null : ykName.trim();
    }
}