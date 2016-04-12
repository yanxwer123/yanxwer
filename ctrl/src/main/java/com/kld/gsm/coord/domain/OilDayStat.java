package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-11-13 10:53
 * @Description: 成品油日结存报表  ( 记录成品油的日结信息  )
 */
public class OilDayStat extends AbsValueBean{
    private Date accountdate;//结帐日
    private String Oilno;//油品编码
    private String Oilname;//油品名称
    private String Price;//单价
    private double Daybegliter;//上日库存量
    private double Dayendliter;//本日库存量
    private String getbillno;//提货单号
    private double Dayinoiliter;//本日进油升量
    private double dayincount;//本日进油数量
    private double Daysaleliter;//本日付油总量
    private double Daysalelitersum;//本日总销售量
    private double Daysalecountsum;//本日总销售金额
    private double Dayindeliter;//本日核销损溢
    private String Transflag;//传输标志

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public String getOilno() {
        return Oilno;
    }

    public void setOilno(String oilno) {
        Oilno = oilno;
    }

    public String getOilname() {
        return Oilname;
    }

    public void setOilname(String oilname) {
        Oilname = oilname;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public double getDaybegliter() {
        return Daybegliter;
    }

    public void setDaybegliter(double daybegliter) {
        Daybegliter = daybegliter;
    }

    public double getDayendliter() {
        return Dayendliter;
    }

    public void setDayendliter(double dayendliter) {
        Dayendliter = dayendliter;
    }

    public String getGetbillno() {
        return getbillno;
    }

    public void setGetbillno(String getbillno) {
        this.getbillno = getbillno;
    }

    public double getDayinoiliter() {
        return Dayinoiliter;
    }

    public void setDayinoiliter(double dayinoiliter) {
        Dayinoiliter = dayinoiliter;
    }

    public double getDayincount() {
        return dayincount;
    }

    public void setDayincount(double dayincount) {
        this.dayincount = dayincount;
    }

    public double getDaysaleliter() {
        return Daysaleliter;
    }

    public void setDaysaleliter(double daysaleliter) {
        Daysaleliter = daysaleliter;
    }

    public double getDaysalelitersum() {
        return Daysalelitersum;
    }

    public void setDaysalelitersum(double daysalelitersum) {
        Daysalelitersum = daysalelitersum;
    }

    public double getDaysalecountsum() {
        return Daysalecountsum;
    }

    public void setDaysalecountsum(double daysalecountsum) {
        Daysalecountsum = daysalecountsum;
    }

    public double getDayindeliter() {
        return Dayindeliter;
    }

    public void setDayindeliter(double dayindeliter) {
        Dayindeliter = dayindeliter;
    }

    public String getTransflag() {
        return Transflag;
    }

    public void setTransflag(String transflag) {
        Transflag = transflag;
    }

    @Override
    public String toString() {
        return "OilDayStat{" +
                "accountdate=" + accountdate +
                ", Oilno='" + Oilno + '\'' +
                ", Oilname='" + Oilname + '\'' +
                ", Price='" + Price + '\'' +
                ", Daybegliter=" + Daybegliter +
                ", Dayendliter=" + Dayendliter +
                ", getbillno=" + getbillno +
                ", Dayinoiliter=" + Dayinoiliter +
                ", dayincount=" + dayincount +
                ", Daysaleliter=" + Daysaleliter +
                ", Daysalelitersum=" + Daysalelitersum +
                ", Daysalecountsum=" + Daysalecountsum +
                ", Dayindeliter=" + Dayindeliter +
                ", Transflag='" + Transflag + '\'' +
                '}';
    }
}
