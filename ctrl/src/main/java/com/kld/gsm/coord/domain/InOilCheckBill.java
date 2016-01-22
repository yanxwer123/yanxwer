package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/11/10 18:12
 * @Description:进油核对单
 */
public class InOilCheckBill {
    private String vouchno;      //进油核对单号
    private String inoilcompno;    //进油客户编码
    private String oilno;          //油品编码
    private String teamvouchno;    //班次号
    private String goodsbillno;    //提货单号
    private Double origamount;     //原发数量
    private Double origvidedens;  //原发视密度
    private Double origtemper;	    //原发试验温度
    private Double origcnttemper;  //原发计量温度
    private Double origstandens;   //原发标准密度
    private Double origvolucorr;   //原发体积修正数
    private Double origstdliter;   //原发标准升数
    private Double origairhigh;	  //原发空高
    private Double origoiwahigh;	  //原发油水总高
    private Double origwatehigh;	  //原发水高
    private Double origlitramout;  //原发升数
    private String pickupplace;	  //提货地点
    private Double checamount;	    //验收数量
    private Double checvidedens;	  //验收视密度
    private Double chectemper;	    //验收试验温度
    private Double checcnttemper;	//验收计量温度
    private Double checstandens;	  //验收标准密度
    private Double checvolucorr;	  //验收体积修正数
    private Double checoiwahigh;	  //验收油水总高
    private Double checairhigh;	  //验收空高
    private Double checwatehigh;	  //验收水高
    private Double checlitramount; //验收升数
    private Double checstdliter;   //验收标准升数
    private Double deinweight;	    //损溢重量
    private Double deinvolu;	      //损溢体积
    private String deinanalyse;    //损溢分析
    private String shipdept;	      //承运单位
    private String chiptrucno;	    //承运车号
    private String checothers;	    //其它检验情况
    private String vittaperson;	  //卸油员
    private String driver;	        //驾驶员
    private Date accountdate;      //结帐日期
    private Date arrivetime;       //到站时间
    private String acceptflag;     //接收状态
    private String refusersn;      //拒收原因
    private int operatorcode;      //接收人
    private int chkoprno;         //复核人
    private String billstatus;     //单据状态
    private String transflag;      //传输标志
    private int oilcanno;          //卸入罐号
    private String dayflag;        //日结标志

    public String getVouchno() {
        return vouchno;
    }

    public void setVouchno(String vouchno) {
        this.vouchno = vouchno;
    }

    public String getInoilcompno() {
        return inoilcompno;
    }

    public void setInoilcompno(String inoilcompno) {
        this.inoilcompno = inoilcompno;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public String getTeamvouchno() {
        return teamvouchno;
    }

    public void setTeamvouchno(String teamvouchno) {
        this.teamvouchno = teamvouchno;
    }

    public String getGoodsbillno() {
        return goodsbillno;
    }

    public void setGoodsbillno(String goodsbillno) {
        this.goodsbillno = goodsbillno;
    }

    public Double getOrigamount() {
        return origamount;
    }

    public void setOrigamount(Double origamount) {
        this.origamount = origamount;
    }

    public Double getOrigvidedens() {
        return origvidedens;
    }

    public void setOrigvidedens(Double origvidedens) {
        this.origvidedens = origvidedens;
    }

    public Double getOrigtemper() {
        return origtemper;
    }

    public void setOrigtemper(Double origtemper) {
        this.origtemper = origtemper;
    }

    public Double getOrigcnttemper() {
        return origcnttemper;
    }

    public void setOrigcnttemper(Double origcnttemper) {
        this.origcnttemper = origcnttemper;
    }

    public Double getOrigstandens() {
        return origstandens;
    }

    public void setOrigstandens(Double origstandens) {
        this.origstandens = origstandens;
    }

    public Double getOrigvolucorr() {
        return origvolucorr;
    }

    public void setOrigvolucorr(Double origvolucorr) {
        this.origvolucorr = origvolucorr;
    }

    public Double getOrigstdliter() {
        return origstdliter;
    }

    public void setOrigstdliter(Double origstdliter) {
        this.origstdliter = origstdliter;
    }

    public Double getOrigairhigh() {
        return origairhigh;
    }

    public void setOrigairhigh(Double origairhigh) {
        this.origairhigh = origairhigh;
    }

    public Double getOrigoiwahigh() {
        return origoiwahigh;
    }

    public void setOrigoiwahigh(Double origoiwahigh) {
        this.origoiwahigh = origoiwahigh;
    }

    public Double getOrigwatehigh() {
        return origwatehigh;
    }

    public void setOrigwatehigh(Double origwatehigh) {
        this.origwatehigh = origwatehigh;
    }

    public Double getOriglitramout() {
        return origlitramout;
    }

    public void setOriglitramout(Double origlitramout) {
        this.origlitramout = origlitramout;
    }

    public String getPickupplace() {
        return pickupplace;
    }

    public void setPickupplace(String pickupplace) {
        this.pickupplace = pickupplace;
    }

    public Double getChecamount() {
        return checamount;
    }

    public void setChecamount(Double checamount) {
        this.checamount = checamount;
    }

    public Double getChecvidedens() {
        return checvidedens;
    }

    public void setChecvidedens(Double checvidedens) {
        this.checvidedens = checvidedens;
    }

    public Double getChectemper() {
        return chectemper;
    }

    public void setChectemper(Double chectemper) {
        this.chectemper = chectemper;
    }

    public Double getCheccnttemper() {
        return checcnttemper;
    }

    public void setCheccnttemper(Double checcnttemper) {
        this.checcnttemper = checcnttemper;
    }

    public Double getChecstandens() {
        return checstandens;
    }

    public void setChecstandens(Double checstandens) {
        this.checstandens = checstandens;
    }

    public Double getChecvolucorr() {
        return checvolucorr;
    }

    public void setChecvolucorr(Double checvolucorr) {
        this.checvolucorr = checvolucorr;
    }

    public Double getChecoiwahigh() {
        return checoiwahigh;
    }

    public void setChecoiwahigh(Double checoiwahigh) {
        this.checoiwahigh = checoiwahigh;
    }

    public Double getChecairhigh() {
        return checairhigh;
    }

    public void setChecairhigh(Double checairhigh) {
        this.checairhigh = checairhigh;
    }

    public Double getChecwatehigh() {
        return checwatehigh;
    }

    public void setChecwatehigh(Double checwatehigh) {
        this.checwatehigh = checwatehigh;
    }

    public Double getCheclitramount() {
        return checlitramount;
    }

    public void setCheclitramount(Double checlitramount) {
        this.checlitramount = checlitramount;
    }

    public Double getChecstdliter() {
        return checstdliter;
    }

    public void setChecstdliter(Double checstdliter) {
        this.checstdliter = checstdliter;
    }

    public Double getDeinweight() {
        return deinweight;
    }

    public void setDeinweight(Double deinweight) {
        this.deinweight = deinweight;
    }

    public Double getDeinvolu() {
        return deinvolu;
    }

    public void setDeinvolu(Double deinvolu) {
        this.deinvolu = deinvolu;
    }

    public String getDeinanalyse() {
        return deinanalyse;
    }

    public void setDeinanalyse(String deinanalyse) {
        this.deinanalyse = deinanalyse;
    }

    public String getShipdept() {
        return shipdept;
    }

    public void setShipdept(String shipdept) {
        this.shipdept = shipdept;
    }

    public String getChiptrucno() {
        return chiptrucno;
    }

    public void setChiptrucno(String chiptrucno) {
        this.chiptrucno = chiptrucno;
    }

    public String getChecothers() {
        return checothers;
    }

    public void setChecothers(String checothers) {
        this.checothers = checothers;
    }

    public String getVittaperson() {
        return vittaperson;
    }

    public void setVittaperson(String vittaperson) {
        this.vittaperson = vittaperson;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public Date getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(Date arrivetime) {
        this.arrivetime = arrivetime;
    }

    public String getAcceptflag() {
        return acceptflag;
    }

    public void setAcceptflag(String acceptflag) {
        this.acceptflag = acceptflag;
    }

    public String getRefusersn() {
        return refusersn;
    }

    public void setRefusersn(String refusersn) {
        this.refusersn = refusersn;
    }

    public int getOperatorcode() {
        return operatorcode;
    }

    public void setOperatorcode(int operatorcode) {
        this.operatorcode = operatorcode;
    }

    public int getChkoprno() {
        return chkoprno;
    }

    public void setChkoprno(int chkoprno) {
        this.chkoprno = chkoprno;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag;
    }

    public int getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(int oilcanno) {
        this.oilcanno = oilcanno;
    }

    public String getDayflag() {
        return dayflag;
    }

    public void setDayflag(String dayflag) {
        this.dayflag = dayflag;
    }
}
