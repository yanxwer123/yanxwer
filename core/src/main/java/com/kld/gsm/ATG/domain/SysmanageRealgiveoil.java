package com.kld.gsm.ATG.domain;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2016/2/19 16:15
 * @Description:
 */
public class SysmanageRealgiveoil {
    private String ckdid;

    private String sjfyl;//原发体积Vt

    private Double wd;//温度

    private Double md;//密度

    public String getCkdid() {
        return ckdid;
    }

    public void setCkdid(String ckdid) {
        this.ckdid = ckdid;
    }

    public String getSjfyl() {
        return sjfyl;
    }

    public void setSjfyl(String sjfyl) {
        this.sjfyl = sjfyl;
    }

    public Double getWd() {
        return wd;
    }

    public void setWd(Double wd) {
        this.wd = wd;
    }

    public Double getMd() {
        return md;
    }

    public void setMd(Double md) {
        this.md = md;
    }

    @Override
    public String toString() {
        return "SysmanageRealgiveoil{" +
                "ckdid='" + ckdid + '\'' +
                ", sjfyl='" + sjfyl + '\'' +
                ", wd=" + wd +
                ", md=" + md +
                '}';
    }
}
