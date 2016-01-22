package com.kld.gsm.ATG.domain;

import java.text.DecimalFormat;
import java.util.Date;

public class AlarmOilInContrast {
    private String deliveryno;

    private String shift;

    private String oilno;

    private Double planl;

    private Double realrecieve;

    private Double loss;

    private Double lossrate;

    private Double duringsales;

    private String transtatus;

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    private Date creattime;

    public String getDeliveryno() {
        return deliveryno;
    }

    public void setDeliveryno(String deliveryno) {
        this.deliveryno = deliveryno == null ? null : deliveryno.trim();
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift == null ? null : shift.trim();
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno == null ? null : oilno.trim();
    }

    public Double getPlanl() {
        return planl;
    }

    public void setPlanl(Double planl) {
        this.planl = planl;
    }

    public Double getRealrecieve() {
        return realrecieve;
    }

    public void setRealrecieve(Double realrecieve) {
        this.realrecieve = realrecieve;
    }

    public Double getLoss() {
        return loss;
    }

    public void setLoss(Double loss) {
        this.loss = loss;
    }

    public Double getLossrate() {
        return lossrate;
    }

    public void setLossrate(Double lossrate) {
        DecimalFormat df=new DecimalFormat("#####.0000");
        if (lossrate!=null){
            lossrate=Double.parseDouble(df.format(lossrate));
        }
        this.lossrate = lossrate;
    }

    public Double getDuringsales() {
        return duringsales;
    }

    public void setDuringsales(Double duringsales) {
        this.duringsales = duringsales;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    @Override
    public String toString() {
        return "AlarmOilInContrast{" +
                "deliveryno='" + deliveryno + '\'' +
                ", shift='" + shift + '\'' +
                ", oilno='" + oilno + '\'' +
                ", planl=" + planl +
                ", realrecieve=" + realrecieve +
                ", loss=" + loss +
                ", lossrate=" + lossrate +
                ", duringsales=" + duringsales +
                ", transtatus='" + transtatus + '\'' +
                ", creattime=" + creattime +
                '}';
    }
}