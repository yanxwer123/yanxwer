package com.kld.gsm.coord.domain;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-07 18:16
 * @Description: 交接班表
 */
public class TeamHoto {
   private String teamvouchno;//班次号
   private String takedate;//日期
   private String accounter;//会计
   private String hoteamer;//交班班长
   private String toteamer;//接班班长
   private String receiver;//收银员
   private String hotoflag;//交接班标志
   private String transflag;//传输标志

    public String getTeamvouchno() {
        return teamvouchno;
    }

    public void setTeamvouchno(String teamvouchno) {
        this.teamvouchno = teamvouchno;
    }

    public String getTakedate() {
        return takedate;
    }

    public void setTakedate(String takedate) {
        this.takedate = takedate;
    }

    public String getAccounter() {
        return accounter;
    }

    public void setAccounter(String accounter) {
        this.accounter = accounter;
    }

    public String getHoteamer() {
        return hoteamer;
    }

    public void setHoteamer(String hoteamer) {
        this.hoteamer = hoteamer;
    }

    public String getToteamer() {
        return toteamer;
    }

    public void setToteamer(String toteamer) {
        this.toteamer = toteamer;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getHotoflag() {
        return hotoflag;
    }

    public void setHotoflag(String hotoflag) {
        this.hotoflag = hotoflag;
    }

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
    }

    @Override
    public String toString() {
        return "TeamHoto{" +
                "teamvouchno='" + teamvouchno + '\'' +
                ", takedate='" + takedate + '\'' +
                ", accounter='" + accounter + '\'' +
                ", hoteamer='" + hoteamer + '\'' +
                ", toteamer='" + toteamer + '\'' +
                ", receiver='" + receiver + '\'' +
                ", hotoflag='" + hotoflag + '\'' +
                ", transflag='" + transflag + '\'' +
                '}';
    }
}
