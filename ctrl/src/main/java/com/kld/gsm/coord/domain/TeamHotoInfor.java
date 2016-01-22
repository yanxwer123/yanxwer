package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-05 16:54
 * @Description: 交接班信息表
 */
public class TeamHotoInfor {
    private int Gunno;        //枪号
    private String teamvouchno;    //班次号
    private String Ondutyflag;    //上班否
    private String ondutydate;    //上班时间
    private String Offdutyflag;    //下班否
    private String offdutydate;    //下班时间
    private String Teamhotoflag;    //	交接班否
    private Date Teamhototime;        //交接时间
    private String dayflag;    //日结否
    private Date daytime;    //日结时间

    public int getGunno() {
        return Gunno;
    }

    public void setGunno(int gunno) {
        Gunno = gunno;
    }

    public String getTeamvouchno() {
        return teamvouchno;
    }

    public void setTeamvouchno(String teamvouchno) {
        this.teamvouchno = teamvouchno;
    }

    public String getOndutyflag() {
        return Ondutyflag;
    }

    public void setOndutyflag(String ondutyflag) {
        Ondutyflag = ondutyflag;
    }

    public String getOndutydate() {
        return ondutydate;
    }

    public void setOndutydate(String ondutydate) {
        this.ondutydate = ondutydate;
    }

    public String getOffdutyflag() {
        return Offdutyflag;
    }

    public void setOffdutyflag(String offdutyflag) {
        Offdutyflag = offdutyflag;
    }

    public String getOffdutydate() {
        return offdutydate;
    }

    public void setOffdutydate(String offdutydate) {
        this.offdutydate = offdutydate;
    }

    public String getTeamhotoflag() {
        return Teamhotoflag;
    }

    public void setTeamhotoflag(String teamhotoflag) {
        Teamhotoflag = teamhotoflag;
    }

    public Date getTeamhototime() {
        return Teamhototime;
    }

    public void setTeamhototime(Date teamhototime) {
        Teamhototime = teamhototime;
    }

    public String getDayflag() {
        return dayflag;
    }

    public void setDayflag(String dayflag) {
        this.dayflag = dayflag;
    }

    public Date getDaytime() {
        return daytime;
    }

    public void setDaytime(Date daytime) {
        this.daytime = daytime;
    }

    @Override
    public String toString() {
        return "TeamHotoInfor{" +
                "Gunno=" + Gunno +
                ", teamvouchno='" + teamvouchno + '\'' +
                ", Ondutyflag='" + Ondutyflag + '\'' +
                ", ondutydate='" + ondutydate + '\'' +
                ", Offdutyflag='" + Offdutyflag + '\'' +
                ", offdutydate='" + offdutydate + '\'' +
                ", Teamhotoflag='" + Teamhotoflag + '\'' +
                ", Teamhototime=" + Teamhototime +
                ", dayflag='" + dayflag + '\'' +
                ", daytime=" + daytime +
                '}';
    }
}
