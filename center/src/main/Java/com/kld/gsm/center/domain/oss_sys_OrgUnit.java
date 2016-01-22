package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_sys_OrgUnit {
    private String nodeno;

    private String oucode;

    private Integer ouid;

    private String parentoucode;

    private String universalcode;

    private Integer oulevel;

    private String ouname;

    private String shortname;

    private String abname;

    private String outype;

    private Integer isunit;

    private String portalurl;

    private Integer orderno;

    private String oulabel;

    private Integer status;

    private String extend1;

    private String extend2;

    private String createdby;

    private Date createddate;

    private String modifiedby;

    private Date modifieddate;

    public String getNodeno() {
        return nodeno;
    }

    public void setNodeno(String nodeno) {
        this.nodeno = nodeno == null ? null : nodeno.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }

    public Integer getOuid() {
        return ouid;
    }

    public void setOuid(Integer ouid) {
        this.ouid = ouid;
    }

    public String getParentoucode() {
        return parentoucode;
    }

    public void setParentoucode(String parentoucode) {
        this.parentoucode = parentoucode == null ? null : parentoucode.trim();
    }

    public String getUniversalcode() {
        return universalcode;
    }

    public void setUniversalcode(String universalcode) {
        this.universalcode = universalcode == null ? null : universalcode.trim();
    }

    public Integer getOulevel() {
        return oulevel;
    }

    public void setOulevel(Integer oulevel) {
        this.oulevel = oulevel;
    }

    public String getOuname() {
        return ouname;
    }

    public void setOuname(String ouname) {
        this.ouname = ouname == null ? null : ouname.trim();
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname == null ? null : shortname.trim();
    }

    public String getAbname() {
        return abname;
    }

    public void setAbname(String abname) {
        this.abname = abname == null ? null : abname.trim();
    }

    public String getOutype() {
        return outype;
    }

    public void setOutype(String outype) {
        this.outype = outype == null ? null : outype.trim();
    }

    public Integer getIsunit() {
        return isunit;
    }

    public void setIsunit(Integer isunit) {
        this.isunit = isunit;
    }

    public String getPortalurl() {
        return portalurl;
    }

    public void setPortalurl(String portalurl) {
        this.portalurl = portalurl == null ? null : portalurl.trim();
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public String getOulabel() {
        return oulabel;
    }

    public void setOulabel(String oulabel) {
        this.oulabel = oulabel == null ? null : oulabel.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1 == null ? null : extend1.trim();
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2 == null ? null : extend2.trim();
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby == null ? null : modifiedby.trim();
    }

    public Date getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(Date modifieddate) {
        this.modifieddate = modifieddate;
    }
}