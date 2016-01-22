package com.kld.gsm.coord.domain;

import java.util.Date;


/**
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015.11.8
 * @Description:油罐信息表
 */
public class OilCanInfor {

    private int oilcanno;
    private String oilcantype;
    private String outfactoryno;
    private String factoryname;
    private Date outfacorytime;
    private Date installdate;
    private int useyears;
    private String oilno;
    private double cubage;
    private String oilcanstatus;
    private String transflag;
    private double lederliter;
    private double canfactliter;

    public int getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(int oilcanno) {
        this.oilcanno = oilcanno;
    }

    public String getOilcantype() {
        return oilcantype;
    }

    public void setOilcantype(String oilcantype) {
        this.oilcantype = oilcantype;
    }

    public String getOutfactoryno() {
        return outfactoryno;
    }

    public void setOutfactoryno(String outfactoryno) {
        this.outfactoryno = outfactoryno;
    }

    public String getFactoryname() {
        return factoryname;
    }

    public void setFactoryname(String factoryname) {
        this.factoryname = factoryname;
    }

    public Date getOutfacorytime() {
        return outfacorytime;
    }

    public void setOutfacorytime(Date outfacorytime) {
        this.outfacorytime = outfacorytime;
    }

    public Date getInstalldate() {
        return installdate;
    }

    public void setInstalldate(Date installdate) {
        this.installdate = installdate;
    }

    public int getUseyears() {
        return useyears;
    }

    public void setUseyears(int useyears) {
        this.useyears = useyears;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public double getCubage() {
        return cubage;
    }

    public void setCubage(double cubage) {
        this.cubage = cubage;
    }

    public String getOilcanstatus() {
        return oilcanstatus;
    }

    public void setOilcanstatus(String oilcanstatus) {
        this.oilcanstatus = oilcanstatus;
    }

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
    }

    public double getLederliter() {
        return lederliter;
    }

    public void setLederliter(double lederliter) {
        this.lederliter = lederliter;
    }

    public double getCanfactliter() {
        return canfactliter;
    }

    public void setCanfactliter(double canfactliter) {
        this.canfactliter = canfactliter;
    }
}
