package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_acceptance_deliveryPlan extends oss_acceptance_deliveryPlanKey {
    private String oilId;

    private String depotCode;

    private Double xql;

    private String xqunit;

    private String cpNo;

    private Date psDate;

    private String lsgc;

    private String kcdd;

    private String transtatus;

    private String oucode;

    public String getOilId() {
        return oilId;
    }

    public void setOilId(String oilId) {
        this.oilId = oilId == null ? null : oilId.trim();
    }

    public String getDepotCode() {
        return depotCode;
    }

    public void setDepotCode(String depotCode) {
        this.depotCode = depotCode == null ? null : depotCode.trim();
    }

    public Double getXql() {
        return xql;
    }

    public void setXql(Double xql) {
        this.xql = xql;
    }

    public String getXqunit() {
        return xqunit;
    }

    public void setXqunit(String xqunit) {
        this.xqunit = xqunit == null ? null : xqunit.trim();
    }

    public String getCpNo() {
        return cpNo;
    }

    public void setCpNo(String cpNo) {
        this.cpNo = cpNo == null ? null : cpNo.trim();
    }

    public Date getPsDate() {
        return psDate;
    }

    public void setPsDate(Date psDate) {
        this.psDate = psDate;
    }

    public String getLsgc() {
        return lsgc;
    }

    public void setLsgc(String lsgc) {
        this.lsgc = lsgc == null ? null : lsgc.trim();
    }

    public String getKcdd() {
        return kcdd;
    }

    public void setKcdd(String kcdd) {
        this.kcdd = kcdd == null ? null : kcdd.trim();
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}