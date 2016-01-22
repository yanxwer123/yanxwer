package com.kld.gsm.ATG.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DailyTradeAccount extends DailyTradeAccountKey {
    private String cardno;
    private String machineoilno;
    private String oilno;
    private int ctc;
    private String openo;
    private double liter;
    private double price;
    private double amount;
    private double balance;
    private double pumpno;
    private String tac;
    private String gmac;
    private String psamTac;
    private String psamAsn;
    private String tempinalno;
    private int psamTtc;
    private String moneysou;
    private String paymode;
    private String payunit;
    private String tMac;
    private Date accountdate;
    private String tracode;
    private Date gettime;
    private int keyVersion;
    private int keyIndex;
    private String compmatchflag;
    private String compno;
    private String backmatchflag;
    private String paytypeflag;
    private String shift;
    private String transflag;
    private String isrecieved;
    private String backcanflag;

    @JsonProperty("cardno")
    public String getCardNo() {
        return cardno;
    }

    public void setCardNo(String cardNo) {
        cardno = cardNo;
    }

    @JsonProperty("machineoilno")
    public String getMachineOilNo() {
        return machineoilno;
    }

    public void setMachineOilNo(String machineOilNo) {
        machineoilno = machineOilNo;
    }

    @JsonProperty("oilno")
    public String getOilNo() {
        return oilno;
    }

    public void setOilNo(String oilNo) {
        oilno = oilNo;
    }

    @JsonProperty("ctc")
    public int getCTC() {
        return ctc;
    }

    public void setCTC(int CTC) {
        this.ctc = CTC;
    }

    @JsonProperty("openo")
    public String getOpeNo() {
        return openo;
    }

    public void setOpeNo(String opeNo) {
        openo = opeNo;
    }

    @JsonProperty("liter")
    public double getLiter() {
        return liter;
    }

    public void setLiter(double liter) {
        this.liter = liter;
    }

    @JsonProperty("price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @JsonProperty("amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @JsonProperty("balance")
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @JsonProperty("pumpno")
    public double getPumpNo() {
        return pumpno;
    }

    public void setPumpNo(double pumpNo) {
        this.pumpno = pumpNo;
    }

    @JsonProperty("tac")
    public String getTAC() {
        return tac;
    }

    public void setTAC(String TAC) {
        this.tac = TAC;
    }

    @JsonProperty("gmac")
    public String getGMAC() {
        return gmac;
    }

    public void setGMAC(String GMAC) {
        this.gmac = GMAC;
    }

    @JsonProperty("psamTac")
    public String getPSAM_TAC() {
        return psamTac;
    }

    public void setPSAM_TAC(String PSAM_TAC) {
        this.psamTac = PSAM_TAC;
    }

    @JsonProperty("psamAsn")
    public String getPSAM_ASN() {
        return psamAsn;
    }

    public void setPSAM_ASN(String PSAM_ASN) {
        this.psamAsn = PSAM_ASN;
    }

    @JsonProperty("tempinalno")
    public String getTempinalNo() {
        return tempinalno;
    }

    public void setTempinalNo(String tempinalNo) {
        tempinalno = tempinalNo;
    }

    @JsonProperty("psamTtc")
    public int getPSAM_TTC() {
        return psamTtc;
    }

    public void setPSAM_TTC(int PSAM_TTC) {
        this.psamTtc = PSAM_TTC;
    }

    @JsonProperty("moneysou")
    public String getMoneySou() {
        return moneysou;
    }

    public void setMoneySou(String moneySou) {
        moneysou = moneySou;
    }

    @JsonProperty("paymode")
    public String getPayMode() {
        return paymode;
    }

    public void setPayMode(String payMode) {
        paymode = payMode;
    }




    @JsonProperty("payunit")
    public String getPayUnit() {
        return payunit;
    }

    public void setPayUnit(String payUnit) {
        payunit = payUnit;
    }

    @JsonProperty("tMac")
    public String getT_MAC() {
        return tMac;
    }

    public void setT_MAC(String t_MAC) {
        tMac = t_MAC;
    }

    @JsonProperty("accountdate")
    public Date getAccountDate() {
        return accountdate;
    }

    public void setAccountDate(Date accountDate) {
        accountdate = accountDate;
    }

    @JsonProperty("tracode")
    public String getTraCode() {
        return tracode;
    }

    public void setTraCode(String traCode) {
        tracode = traCode;
    }

    @JsonProperty("gettime")
    public Date getGetTime() {
        return gettime;
    }

    public void setGetTime(Date getTime) {
        gettime = getTime;
    }

    @JsonProperty("keyVersion")
    public int getKey_Version() {
        return keyVersion;
    }

    public void setKey_Version(int key_Version) {
        keyVersion = key_Version;
    }

    @JsonProperty("keyIndex")
    public int getKey_Index() {
        return keyIndex;
    }

    public void setKey_Index(int key_Index) {
        keyIndex = key_Index;
    }

    @JsonProperty("compmatchflag")
    public String getCompMatchFlag() {
        return compmatchflag;
    }

    public void setCompMatchFlag(String compMatchFlag) {
        compmatchflag = compMatchFlag;
    }

    @JsonProperty("compno")
    public String getCompNo() {
        return compno;
    }

    public void setCompNo(String compNo) {
        compno = compNo;
    }

    @JsonProperty("backmatchflag")
    public String getBackMatchFlag() {
        return backmatchflag;
    }

    public void setBackMatchFlag(String backMatchFlag) {
        backmatchflag = backMatchFlag;
    }

    @JsonProperty("paytypeflag")
    public String getPayTypeFlag() {
        return paytypeflag;
    }

    public void setPayTypeFlag(String payTypeFlag) {
        paytypeflag = payTypeFlag;
    }
    @JsonProperty("shift")
    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @JsonProperty("transflag")
    public String getTransFlag() {
        return transflag;
    }

    public void setTransFlag(String transFlag) {
        transflag = transFlag;
    }

    @JsonProperty("isrecieved")
    public String getIsRecieved() {
        return isrecieved;
    }

    public void setIsRecieved(String isRecieved) {
        isrecieved = isRecieved;
    }

    @JsonProperty("backcanflag")
    public String getBackcanflag() {
        return backcanflag;
    }

    public void setBackcanflag(String backcanflag) {
        this.backcanflag = backcanflag;
    }

    @Override
    public String toString() {
        return "DailyTradeAccount{" +
                "cardno='" + cardno + '\'' +
                ", machineoilno='" + machineoilno + '\'' +
                ", oilno='" + oilno + '\'' +
                ", ctc=" + ctc +
                ", openo='" + openo + '\'' +
                ", liter=" + liter +
                ", price=" + price +
                ", amount=" + amount +
                ", balance=" + balance +
                ", pumpno=" + pumpno +
                ", tac='" + tac + '\'' +
                ", gmac='" + gmac + '\'' +
                ", psamTac='" + psamTac + '\'' +
                ", psamAsn='" + psamAsn + '\'' +
                ", tempinalno='" + tempinalno + '\'' +
                ", psamTtc=" + psamTtc +
                ", moneysou='" + moneysou + '\'' +
                ", paymode='" + paymode + '\'' +
                ", payunit='" + payunit + '\'' +
                ", tMac='" + tMac + '\'' +
                ", accountdate=" + accountdate +
                ", tracode='" + tracode + '\'' +
                ", gettime=" + gettime +
                ", keyVersion=" + keyVersion +
                ", keyIndex=" + keyIndex +
                ", compmatchflag='" + compmatchflag + '\'' +
                ", compno='" + compno + '\'' +
                ", backmatchflag='" + backmatchflag + '\'' +
                ", paytypeflag='" + paytypeflag + '\'' +
                ", shift='" + shift + '\'' +
                ", transflag='" + transflag + '\'' +
                ", isrecieved='" + isrecieved + '\'' +
                ", backcanflag='" + backcanflag + '\'' +
                '}';
    }
}