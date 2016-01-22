package com.kld.gsm.center.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class oss_daily_TradeAccount extends oss_daily_TradeAccountKey {
    private String cardno;

    private String machineoilno;

    private String oilno;

    private Integer ctc;

    private String openo;

    private Double liter;

    private Double price;

    private Double amount;

    private Double balance;

    private Double pumpno;

    private String tac;

    private String gmac;

    private String psamTac;

    private String psamAsn;

    private String tempinalno;

    private Integer psamTtc;

    private String moneysou;

    private String paymode;

    private String payunit;

    private String tMac;

    private Date accountdate;

    private String tracode;

    private Date gettime;

    private Integer keyVersion;

    private Integer keyIndex;

    private String compmatchflag;

    private String compno;

    private String backmatchflag;

    private String paytypeflag;

    private String shift;

    private String transflag;

    private String isrecieved;

    private String oucode;

    private String backcanflag;

    public String getBackcanflag() {
        return backcanflag;
    }

    public void setBackcanflag(String backcanflag) {
        this.backcanflag = backcanflag;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public String getMachineoilno() {
        return machineoilno;
    }

    public void setMachineoilno(String machineoilno) {
        this.machineoilno = machineoilno == null ? null : machineoilno.trim();
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public Integer getCtc() {
        return ctc;
    }

    public void setCtc(Integer ctc) {
        this.ctc = ctc;
    }

    public String getOpeno() {
        return openo;
    }

    public void setOpeno(String openo) {
        this.openo = openo == null ? null : openo.trim();
    }

    public Double getLiter() {
        return liter;
    }

    public void setLiter(Double liter) {
        this.liter = liter;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getPumpno() {
        return pumpno;
    }

    public void setPumpno(Double pumpno) {
        this.pumpno = pumpno;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac == null ? null : tac.trim();
    }

    public String getGmac() {
        return gmac;
    }

    public void setGmac(String gmac) {
        this.gmac = gmac == null ? null : gmac.trim();
    }

    public String getPsamTac() {
        return psamTac;
    }

    public void setPsamTac(String psamTac) {
        this.psamTac = psamTac == null ? null : psamTac.trim();
    }

    public String getPsamAsn() {
        return psamAsn;
    }

    public void setPsamAsn(String psamAsn) {
        this.psamAsn = psamAsn == null ? null : psamAsn.trim();
    }

    public String getTempinalno() {
        return tempinalno;
    }

    public void setTempinalno(String tempinalno) {
        this.tempinalno = tempinalno == null ? null : tempinalno.trim();
    }

    public Integer getPsamTtc() {
        return psamTtc;
    }

    public void setPsamTtc(Integer psamTtc) {
        this.psamTtc = psamTtc;
    }

    public String getMoneysou() {
        return moneysou;
    }

    public void setMoneysou(String moneysou) {
        this.moneysou = moneysou == null ? null : moneysou.trim();
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode == null ? null : paymode.trim();
    }

    public String getPayunit() {
        return payunit;
    }

    public void setPayunit(String payunit) {
        this.payunit = payunit == null ? null : payunit.trim();
    }

    public String gettMac() {
        return tMac;
    }

    public void settMac(String tMac) {
        this.tMac = tMac == null ? null : tMac.trim();
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public String getTracode() {
        return tracode;
    }

    public void setTracode(String tracode) {
        this.tracode = tracode == null ? null : tracode.trim();
    }

    public Date getGettime() {
        return gettime;
    }

    public void setGettime(Date gettime) {
        this.gettime = gettime;
    }

    public Integer getKeyVersion() {
        return keyVersion;
    }

    public void setKeyVersion(Integer keyVersion) {
        this.keyVersion = keyVersion;
    }

    public Integer getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(Integer keyIndex) {
        this.keyIndex = keyIndex;
    }

    public String getCompmatchflag() {
        return compmatchflag;
    }

    public void setCompmatchflag(String compmatchflag) {
        this.compmatchflag = compmatchflag == null ? null : compmatchflag.trim();
    }

    public String getCompno() {
        return compno;
    }

    public void setCompno(String compno) {
        this.compno = compno == null ? null : compno.trim();
    }

    public String getBackmatchflag() {
        return backmatchflag;
    }

    public void setBackmatchflag(String backmatchflag) {
        this.backmatchflag = backmatchflag == null ? null : backmatchflag.trim();
    }

    public String getPaytypeflag() {
        return paytypeflag;
    }

    public void setPaytypeflag(String paytypeflag) {
        this.paytypeflag = paytypeflag == null ? null : paytypeflag.trim();
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift == null ? null : shift.trim();
    }

    public String getTransflag() {
        return transflag;
    }

    public void setTransflag(String transflag) {
        this.transflag = transflag == null ? null : transflag.trim();
    }

    public String getIsrecieved() {
        return isrecieved;
    }

    public void setIsrecieved(String isrecieved) {
        this.isrecieved = isrecieved == null ? null : isrecieved.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
}