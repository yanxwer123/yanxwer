package com.kld.gsm.center.domain;

import java.util.Date;

public class oss_sysmanage_Dict {
    private Integer dictid;

    private Integer parentid;

    private Integer sort;

    private String name;

    private String code;

    private Integer isdel;

    private String value;

    private Date createtime;

    private String creator;

    private Integer isappcache;

    private Integer bsnsid;

    private Integer version;

    public Integer getDictid() {
        return dictid;
    }

    public void setDictid(Integer dictid) {
        this.dictid = dictid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getIsappcache() {
        return isappcache;
    }

    public void setIsappcache(Integer isappcache) {
        this.isappcache = isappcache;
    }

    public Integer getBsnsid() {
        return bsnsid;
    }

    public void setBsnsid(Integer bsnsid) {
        this.bsnsid = bsnsid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}