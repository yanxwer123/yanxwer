package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_sysmanage_oilType {
    private String nodeno;

    private String oilno;

    private String oilname;

    private String macoilno;

    private String oiltype;

    private String control;

    private String validflag;

    private Date validtime;

    private Date modifytime;

    private String modifynodeno;

    private Integer modifyope;

    private String levelcode;

    private String lastoilflag;

    private Integer oillevel;

    private String inuseflag;

    private String transtatus;

    private String oucode;

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname == null ? null : oilname.trim();
    }

    public String getMacoilno() {
        return macoilno;
    }

    public void setMacoilno(String macoilno) {
        this.macoilno = macoilno == null ? null : macoilno.trim();
    }

    public String getOiltype() {
        return oiltype;
    }

    public void setOiltype(String oiltype) {
        this.oiltype = oiltype == null ? null : oiltype.trim();
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control == null ? null : control.trim();
    }

    public String getValidflag() {
        return validflag;
    }

    public void setValidflag(String validflag) {
        this.validflag = validflag == null ? null : validflag.trim();
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
        this.modifynodeno = modifynodeno == null ? null : modifynodeno.trim();
    }

    public Integer getModifyope() {
        return modifyope;
    }

    public void setModifyope(Integer modifyope) {
        this.modifyope = modifyope;
    }

    public String getLevelcode() {
        return levelcode;
    }

    public void setLevelcode(String levelcode) {
        this.levelcode = levelcode == null ? null : levelcode.trim();
    }

    public String getLastoilflag() {
        return lastoilflag;
    }

    public void setLastoilflag(String lastoilflag) {
        this.lastoilflag = lastoilflag == null ? null : lastoilflag.trim();
    }

    public Integer getOillevel() {
        return oillevel;
    }

    public void setOillevel(Integer oillevel) {
        this.oillevel = oillevel;
    }

    public String getInuseflag() {
        return inuseflag;
    }

    public void setInuseflag(String inuseflag) {
        this.inuseflag = inuseflag == null ? null : inuseflag.trim();
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