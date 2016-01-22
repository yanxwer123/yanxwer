package com.kld.gsm.center.domain;

public class oss_acceptance_deliveryPlanKey {
    private String psdId;

    private String nodeno;

    public String getPsdId() {
        return psdId;
    }

    public void setPsdId(String psdId) {
        this.psdId = psdId == null ? null : psdId.trim();
    }

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }
}