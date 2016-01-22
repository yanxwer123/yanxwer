package com.kld.gsm.MacLog;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by miaozy on 15/10/27.
 */
public class RefueledInfo {
    public Date WorkDay;
    public int  Shift;
    public byte GunNo;
    public int TankNo;
    public String GasCode;
    public String GasCodeAS;
    public BigDecimal Qty;
    public BigDecimal Price;
    public BigDecimal  Amount;
    public Date  RefueledTime;
    public int FLAG;
    public String POSTTC;
    public String ASN;
    public double  Balance;
    public double  VolTotal;
    public short  TType;
    public String   C_UNIT;
    public short N_CARDTYPE;
    public String  C_VER;
    public String C_EMP;
    public String C_RFU;
    public String CardNo;
    public CardTypeEnum CardType;
    public String HubData;

    @Override
    public String toString() {
        return "RefueledInfo{" +
                "WorkDay=" + WorkDay +
                ", Shift=" + Shift +
                ", GunNo=" + GunNo +
                ", TankNo=" + TankNo +
                ", GasCode='" + GasCode + '\'' +
                ", GasCodeAS='" + GasCodeAS + '\'' +
                ", Qty=" + Qty +
                ", Price=" + Price +
                ", Amount=" + Amount +
                ", RefueledTime=" + RefueledTime +
                ", FLAG=" + FLAG +
                ", POSTTC='" + POSTTC + '\'' +
                ", ASN='" + ASN + '\'' +
                ", Balance=" + Balance +
                ", VolTotal=" + VolTotal +
                ", TType=" + TType +
                ", C_UNIT='" + C_UNIT + '\'' +
                ", N_CARDTYPE=" + N_CARDTYPE +
                ", C_VER='" + C_VER + '\'' +
                ", C_EMP='" + C_EMP + '\'' +
                ", C_RFU='" + C_RFU + '\'' +
                ", CardNo='" + CardNo + '\'' +
                ", CardType=" + CardType +
                ", HubData='" + HubData + '\'' +
                '}';
    }
}
