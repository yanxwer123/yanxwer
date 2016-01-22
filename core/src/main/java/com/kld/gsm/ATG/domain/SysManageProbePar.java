package com.kld.gsm.ATG.domain;

import java.util.Date;
public class SysManageProbePar {
    private String devicemodel;//设备型号

    public String getDevicemodel() {
        return devicemodel;
    }

    public void setDevicemodel(String devicemodel) {
        this.devicemodel = devicemodel;
    }

    public String getProbemodel() {
        return probemodel;
    }

    public void setProbemodel(String probemodel) {
        this.probemodel = probemodel;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public Double getOilzero() {
        return oilzero;
    }

    public void setOilzero(Double oilzero) {
        this.oilzero = oilzero;
    }

    public Double getWaterzero() {
        return waterzero;
    }

    public void setWaterzero(Double waterzero) {
        this.waterzero = waterzero;
    }

    public Double getProbeskew() {
        return probeskew;
    }

    public void setProbeskew(Double probeskew) {
        this.probeskew = probeskew;
    }

    public Double getInclineskew() {
        return inclineskew;
    }

    public void setInclineskew(Double inclineskew) {
        this.inclineskew = inclineskew;
    }

    public Double getRealtemp1() {
        return realtemp1;
    }

    public void setRealtemp1(Double realtemp1) {
        this.realtemp1 = realtemp1;
    }

    public Double getProrealtemp1() {
        return prorealtemp1;
    }

    public void setProrealtemp1(Double prorealtemp1) {
        this.prorealtemp1 = prorealtemp1;
    }

    public Double getRealtemp2() {
        return realtemp2;
    }

    public void setRealtemp2(Double realtemp2) {
        this.realtemp2 = realtemp2;
    }

    public Double getProrealtemp2() {
        return prorealtemp2;
    }

    public void setProrealtemp2(Double prorealtemp2) {
        this.prorealtemp2 = prorealtemp2;
    }

    public Double getRealtemp3() {
        return realtemp3;
    }

    public void setRealtemp3(Double realtemp3) {
        this.realtemp3 = realtemp3;
    }

    public Double getProrealtemp3() {
        return prorealtemp3;
    }

    public void setProrealtemp3(Double prorealtemp3) {
        this.prorealtemp3 = prorealtemp3;
    }

    public Double getRealtemp4() {
        return realtemp4;
    }

    public void setRealtemp4(Double realtemp4) {
        this.realtemp4 = realtemp4;
    }

    public Double getProrealtemp4() {
        return prorealtemp4;
    }

    public void setProrealtemp4(Double prorealtemp4) {
        this.prorealtemp4 = prorealtemp4;
    }

    public Double getRealtemp5() {
        return realtemp5;
    }

    public void setRealtemp5(Double realtemp5) {
        this.realtemp5 = realtemp5;
    }

    public Double getProrealtemp5() {
        return prorealtemp5;
    }

    public void setProrealtemp5(Double prorealtemp5) {
        this.prorealtemp5 = prorealtemp5;
    }

    public Double getInitdesnsity() {
        return initdesnsity;
    }

    public void setInitdesnsity(Double initdesnsity) {
        this.initdesnsity = initdesnsity;
    }

    public Double getInithighdiff() {
        return inithighdiff;
    }

    public void setInithighdiff(Double inithighdiff) {
        this.inithighdiff = inithighdiff;
    }

    public Double getCorrectionfactor() {
        return correctionfactor;
    }

    public void setCorrectionfactor(Double correctionfactor) {
        this.correctionfactor = correctionfactor;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastadjusttime() {
        return lastadjusttime;
    }

    public void setLastadjusttime(Date lastadjusttime) {
        this.lastadjusttime = lastadjusttime;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus;
    }

    private String probemodel;//探棒编号

    private String oilno;//油品类型

    private Double oilzero;//油位0点矫正（毫米）

    private Double waterzero;//水位0点矫正（毫米）

    private Double  probeskew;//探棒偏移v（毫米）

    private Double inclineskew;//倾斜偏移（毫米）

    private Double realtemp1;//温度1实测值(℃)

    private Double prorealtemp1;//温度1探棒测量值(℃)

    private Double realtemp2;//温度2实测值(℃)

    private Double prorealtemp2;//温度2探棒测量值(℃)

    private Double realtemp3;//温度3实测值(℃)

    private Double prorealtemp3;//温度3探棒测量值(℃)

    private Double realtemp4;//温度4实测值(℃)

    private Double prorealtemp4;//温度4探棒测量值(℃)

    private Double realtemp5;//温度5实测值(℃)

    private Double prorealtemp5;//温度5探棒测量值(℃)

    private Double initdesnsity;//初始密度(kg/m3)

    private Double inithighdiff;//初始高度差(mm)油位与密度位之间的高度差

    private Double correctionfactor;//密度的修正系数

    private Date createtime;//操作时间

    private Date lastadjusttime;//上次校正时间

    private String transtatus;//传输状态


    private Integer probeport; //端口

    public Integer getProbeport() {
        return probeport;
    }

    public void setProbeport(Integer probeport) {
        this.probeport = probeport;
    }

    public Integer getOilcan() {
        return oilcan;
    }

    public void setOilcan(Integer oilcan) {
        this.oilcan = oilcan;
    }

    private Integer oilcan; //油罐

    @Override
    public String toString() {
        return "SysManageProbePar{" +
                "devicemodel='" + devicemodel + '\'' +
                ", probemodel='" + probemodel + '\'' +
                ", oilno='" + oilno + '\'' +
                ", oilzero=" + oilzero +
                ", waterzero=" + waterzero +
                ", probeskew=" + probeskew +
                ", inclineskew=" + inclineskew +
                ", realtemp1=" + realtemp1 +
                ", prorealtemp1=" + prorealtemp1 +
                ", realtemp2=" + realtemp2 +
                ", prorealtemp2=" + prorealtemp2 +
                ", realtemp3=" + realtemp3 +
                ", prorealtemp3=" + prorealtemp3 +
                ", realtemp4=" + realtemp4 +
                ", prorealtemp4=" + prorealtemp4 +
                ", realtemp5=" + realtemp5 +
                ", prorealtemp5=" + prorealtemp5 +
                ", initdesnsity=" + initdesnsity +
                ", inithighdiff=" + inithighdiff +
                ", correctionfactor=" + correctionfactor +
                ", createtime=" + createtime +
                ", lastadjusttime=" + lastadjusttime +
                ", transtatus='" + transtatus + '\'' +
                ", probeport=" + probeport +
                ", oilcan=" + oilcan +
                '}';
    }
}