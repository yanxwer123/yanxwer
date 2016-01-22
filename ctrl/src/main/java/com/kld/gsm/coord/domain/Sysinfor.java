package com.kld.gsm.coord.domain;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/24 17:05
 * @Description: 系统信息
 */
public class Sysinfor implements Serializable{
    private String sysdate ;
    private int oprno;
    private String oprname;
    private String teamvouchno;
    private String runappname;

    public String getSysdate() {
        return sysdate;
    }

    public void setSysdate(String sysdate) {
        this.sysdate = sysdate;
    }

    public int getOprno() {
        return oprno;
    }

    public void setOprno(int oprno) {
        this.oprno = oprno;
    }

    public String getOprname() {
        return oprname;
    }

    public void setOprname(String oprname) {
        this.oprname = oprname;
    }

    public String getTeamvouchno() {
        return teamvouchno;
    }

    public void setTeamvouchno(String teamvouchno) {
        this.teamvouchno = teamvouchno;
    }

    public String getRunappname() {
        return runappname;
    }

    public void setRunappname(String runappname) {
        this.runappname = runappname;
    }

    @Override
    public String toString() {
        return "Sysinfor{" +
                "sysdate='" + sysdate + '\'' +
                ", oprno=" + oprno +
                ", oprname='" + oprname + '\'' +
                ", teamvouchno='" + teamvouchno + '\'' +
                ", runappname='" + runappname + '\'' +
                '}';
    }
}
