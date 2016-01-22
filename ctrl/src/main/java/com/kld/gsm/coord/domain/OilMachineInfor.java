package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015.11.8
 * @Description:油机信息表
 */
public class OilMachineInfor {

    private int oilmachineno;
    private String oilmachinemodel;
    private String outfactoryno;
    private String factoryname;
    private Date outfacorytime;
    private Date installdate;
    private int useyears;
    private int oilgunnum;
    private int oilunitnum;
    private String oilmachinestatus;
    private String transflag;
    private String machinetype;

    public int getOilmachineno() {
        return oilmachineno;
    }

    public void setOilmachineno(int oilmachineno) {
        this.oilmachineno = oilmachineno;
    }

    public String getOilmachinemodel() {
        return oilmachinemodel;
    }

    public void setOilmachinemodel(String oilmachinemodel) {
        this.oilmachinemodel = oilmachinemodel;
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

    public int getOilgunnum() {
        return oilgunnum;
    }

    public void setOilgunnum(int oilgunnum) {
        this.oilgunnum = oilgunnum;
    }

    public int getOilunitnum() {
        return oilunitnum;
    }

    public void setOilunitnum(int oilunitnum) {
        this.oilunitnum = oilunitnum;
    }

    public String getOilmachinestatus() {
        return oilmachinestatus;
    }

    public void setOilmachinestatus(String oilmachinestatus) {
        this.oilmachinestatus = oilmachinestatus;
    }

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
    }

    public String getMachinetype() {
        return machinetype;
    }

    public void setMachinetype(String machinetype) {
        this.machinetype = machinetype;
    }
}
