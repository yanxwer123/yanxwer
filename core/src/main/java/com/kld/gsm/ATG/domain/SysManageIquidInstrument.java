package com.kld.gsm.ATG.domain;

import java.util.Date;

public class SysManageIquidInstrument {
    private Integer id          ;

    private String factory      ;//厂商

    private String mactype      ;//设备类型

    private String worktype     ;//采集类型

    private String commtype     ;//通讯模式

    private String ipaddress        ;//ip

    private String ipport       ;//端口

    private String serialport       ;//串口地址

    private String baudrate1        ;//波特率

    private String baudrate2        ;

    private String stopsite     ;//停止

    private String checksite        ;//校验

    private String datasite     ;//数据

    private Date lastadjusttime;//上次校正时间

    private String transtatus;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public String getMactype() {
        return mactype;
    }

    public void setMactype(String mactype) {
        this.mactype = mactype == null ? null : mactype.trim();
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype == null ? null : worktype.trim();
    }

    public String getCommtype() {
        return commtype;
    }

    public void setCommtype(String commtype) {
        this.commtype = commtype == null ? null : commtype.trim();
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress == null ? null : ipaddress.trim();
    }

    public String getIpport() {
        return ipport;
    }

    public void setIpport(String ipport) {
        this.ipport = ipport == null ? null : ipport.trim();
    }

    public String getSerialport() {
        return serialport;
    }

    public void setSerialport(String serialport) {
        this.serialport = serialport == null ? null : serialport.trim();
    }

    public String getBaudrate1() {
        return baudrate1;
    }

    public void setBaudrate1(String baudrate1) {
        this.baudrate1 = baudrate1 == null ? null : baudrate1.trim();
    }

    public String getBaudrate2() {
        return baudrate2;
    }

    public void setBaudrate2(String baudrate2) {
        this.baudrate2 = baudrate2 == null ? null : baudrate2.trim();
    }

    public String getStopsite() {
        return stopsite;
    }

    public void setStopsite(String stopsite) {
        this.stopsite = stopsite == null ? null : stopsite.trim();
    }

    public String getChecksite() {
        return checksite;
    }

    public void setChecksite(String checksite) {
        this.checksite = checksite == null ? null : checksite.trim();
    }

    public String getDatasite() {
        return datasite;
    }

    public void setDatasite(String datasite) {
        this.datasite = datasite == null ? null : datasite.trim();
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
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "SysManageIquidInstrument{" +
                "id=" + id +
                ", factory='" + factory + '\'' +
                ", mactype='" + mactype + '\'' +
                ", worktype='" + worktype + '\'' +
                ", commtype='" + commtype + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", ipport='" + ipport + '\'' +
                ", serialport='" + serialport + '\'' +
                ", baudrate1='" + baudrate1 + '\'' +
                ", baudrate2='" + baudrate2 + '\'' +
                ", stopsite='" + stopsite + '\'' +
                ", checksite='" + checksite + '\'' +
                ", datasite='" + datasite + '\'' +
                ", lastadjusttime=" + lastadjusttime +
                ", transtatus='" + transtatus + '\'' +
                ", createtime=" + createtime +
                '}';
    }
}