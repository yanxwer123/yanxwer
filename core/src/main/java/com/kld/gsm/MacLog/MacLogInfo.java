package com.kld.gsm.MacLog;

import java.math.BigDecimal;

/**
 * Created by miaozy on 15/10/25.
 */
public class MacLogInfo {
    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
    public long ID;
    public byte GunNum;
    public BigDecimal amount;
    public BigDecimal qty;
    public BigDecimal Price;
    public GunStatusEnum GunStatus;
    public BigDecimal FuelQuatity;
    //油品
    public String GasName;
    //卡号
    public String CardNum;

    //泵码
    public Double PumpReadout;

    //加油工
    public String Oiler;
    public CardTypeEnum CardType;

    public int TotalCount;

    public byte getGunNum() {
        return GunNum;
    }

    public void setGunNum(byte gunNum) {
        GunNum = gunNum;
    }

    public BigDecimal getAmount() {
        if (amount == null) {
            return null;
        }
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getQty() {
        if (qty == null) {
            return null;
        }
        return qty.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        if (Price == null) {
            return null;
        }
        return Price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setPrice(BigDecimal price) {

        Price = price;
    }

    public GunStatusEnum getGunStatus() {
        return GunStatus;
    }

    public void setGunStatus(GunStatusEnum gunStatus) {
        GunStatus = gunStatus;
    }

    public BigDecimal getFuelQuatity() {
        if (FuelQuatity == null) {
            return null;
        }
        return FuelQuatity.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setFuelQuatity(BigDecimal fuelQuatity) {
        FuelQuatity = fuelQuatity;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getGasName() {
        return GasName;
    }

    public void setGasName(String gasName) {
        GasName = gasName;
    }

    public String getCardNum() {
        return CardNum;
    }

    public void setCardNum(String cardNum) {
        CardNum = cardNum;
    }

    public Double getPumpReadout() {
        return PumpReadout;
    }

    public void setPumpReadout(Double pumpReadout) {
        PumpReadout = pumpReadout;
    }

    public String getOiler() {
        return Oiler;
    }

    public void setOiler(String oiler) {
        Oiler = oiler;
    }

    public CardTypeEnum getCardType() {
        return CardType;
    }

    public void setCardType(CardTypeEnum cardType) {
        CardType = cardType;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }


    @Override
    public String toString() {
        return "MacLogInfo{" +
                "GunNum=" + GunNum +
                ", Amount=" + getAmount() +
                ", Qty=" + getQty() +
                ", Price=" + getPrice() +
                ", GunStatus=" + GunStatus +
                ", FuelQuatity=" + getFuelQuatity() +
                ", ID=" + ID +
                ", GasName='" + GasName + '\'' +
                ", CardNum='" + CardNum + '\'' +
                ", PumpReadout=" + PumpReadout +
                ", Oiler='" + Oiler + '\'' +
                ", CardType=" + CardType +
                ", TotalCount=" + TotalCount +
                '}';
    }

}
