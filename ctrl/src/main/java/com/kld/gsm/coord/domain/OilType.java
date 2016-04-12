package com.kld.gsm.coord.domain;

import java.util.Date;


/**
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015.11.8
 * @Description:机走油品编码
 */
public class OilType extends AbsValueBean{
    private String oilno;
    private String oilname;
    private String machineoilno;
    private String oilattribute	;
    private String control;
    private String validflag;
    private Date validtime;
    private Date modifytime;
    private String modifynodeno	;
    private int modifyope;
    private String levelcode;
    private String lastoilflag;
    private int oillevel;
    private String validfalg;
    private String inuseflag;

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname;
    }

    public String getMachineoilno() {
        return machineoilno;
    }

    public void setMachineoilno(String machineoilno) {
        this.machineoilno = machineoilno;
    }

    public String getOilattribute() {
        return oilattribute;
    }

    public void setOilattribute(String oilattribute) {
        this.oilattribute = oilattribute;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getValidflag() {
        return validflag;
    }

    public void setValidflag(String validflag) {
        this.validflag = validflag;
    }

    public Date getValidtime() {
        return validtime;
    }

    public void setValidtime(Date validtime) {
        this.validtime = validtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getModifynodeno() {
        return modifynodeno;
    }

    public void setModifynodeno(String modifynodeno) {
        this.modifynodeno = modifynodeno;
    }

    public int getModifyope() {
        return modifyope;
    }

    public void setModifyope(int modifyope) {
        this.modifyope = modifyope;
    }

    public String getLevelcode() {
        return levelcode;
    }

    public void setLevelcode(String levelcode) {
        this.levelcode = levelcode;
    }

    public String getLastoilflag() {
        return lastoilflag;
    }

    public void setLastoilflag(String lastoilflag) {
        this.lastoilflag = lastoilflag;
    }

    public int getOillevel() {
        return oillevel;
    }

    public void setOillevel(int oillevel) {
        this.oillevel = oillevel;
    }

    public String getValidfalg() {
        return validfalg;
    }

    public void setValidfalg(String validfalg) {
        this.validfalg = validfalg;
    }

    public String getInuseflag() {
        return inuseflag;
    }

    public void setInuseflag(String inuseflag) {
        this.inuseflag = inuseflag;
    }
}
