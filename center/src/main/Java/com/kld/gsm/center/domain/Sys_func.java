package com.kld.gsm.center.domain;

import java.util.Date;

public class Sys_func {
    private String funccode;

    private String parentcode;

    private String name;

    private String description;

    private String picurl;

    private String targeturl;

    private Integer isopenwindow;

    private Integer orderno;

    private String creator;

    private Date createtime;

    private String modifier;

    private Date moditime;

    public String getFunccode() {
        return funccode;
    }

    public void setFunccode(String funccode) {
        this.funccode = funccode == null ? null : funccode.trim();
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode == null ? null : parentcode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl == null ? null : picurl.trim();
    }

    public String getTargeturl() {
        return targeturl;
    }

    public void setTargeturl(String targeturl) {
        this.targeturl = targeturl == null ? null : targeturl.trim();
    }

    public Integer getIsopenwindow() {
        return isopenwindow;
    }

    public void setIsopenwindow(Integer isopenwindow) {
        this.isopenwindow = isopenwindow;
    }

    public Integer getOrderno() {
        return orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getModitime() {
        return moditime;
    }

    public void setModitime(Date moditime) {
        this.moditime = moditime;
    }
}