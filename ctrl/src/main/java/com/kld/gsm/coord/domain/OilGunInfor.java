package com.kld.gsm.coord.domain;


/**
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015.11.8
 * @Description:油枪信息表
 */
public class OilGunInfor {

    private int	oilgunno;
    private int	machineno;
    private int	oilcanno;
    private String	oilgunname;
    private int  ctrlunitnum;
    private int	linkadr;
    private String	gunstatus;
    private String	transflag;
    private double	initpump;

    public int getOilgunno() {
        return oilgunno;
    }

    public void setOilgunno(int oilgunno) {
        this.oilgunno = oilgunno;
    }

    public int getMachineno() {
        return machineno;
    }

    public void setMachineno(int machineno) {
        this.machineno = machineno;
    }

    public int getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(int oilcanno) {
        this.oilcanno = oilcanno;
    }

    public String getOilgunname() {
        return oilgunname;
    }

    public void setOilgunname(String oilgunname) {
        this.oilgunname = oilgunname;
    }

    public int getCtrlunitnum() {
        return ctrlunitnum;
    }

    public void setCtrlunitnum(int ctrlunitnum) {
        this.ctrlunitnum = ctrlunitnum;
    }

    public int getLinkadr() {
        return linkadr;
    }

    public void setLinkadr(int linkadr) {
        this.linkadr = linkadr;
    }

    public String getGunstatus() {
        return gunstatus;
    }

    public void setGunstatus(String gunstatus) {
        this.gunstatus = gunstatus;
    }

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
    }

    public double getInitpump() {
        return initpump;
    }

    public void setInitpump(double initpump) {
        this.initpump = initpump;
    }
}
