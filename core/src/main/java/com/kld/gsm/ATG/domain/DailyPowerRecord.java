package com.kld.gsm.ATG.domain;

public class DailyPowerRecord extends DailyPowerRecordKey {

    private Double totalheight;

    private Double waterheight;

    private Double oiltemp;

    private Double oiltemp1;

    private Double oiltemp2;

    private Double oiltemp3;

    private Double oiltemp4;

    private Double oiltemp5;

    private Double oilcubage;

    private Double oilstandcubage;

    private Double emptycubage;

    private Double waterbulk;



    public Double getTotalheight() {
        return totalheight;
    }

    public void setTotalheight(Double totalheight) {
        this.totalheight = totalheight;
    }

    public Double getWaterheight() {
        return waterheight;
    }

    public void setWaterheight(Double waterheight) {
        this.waterheight = waterheight;
    }

    public Double getOiltemp() {
        return oiltemp;
    }

    public void setOiltemp(Double oiltemp) {
        this.oiltemp = oiltemp;
    }

    public Double getOiltemp1() {
        return oiltemp1;
    }

    public void setOiltemp1(Double oiltemp1) {
        this.oiltemp1 = oiltemp1;
    }

    public Double getOiltemp2() {
        return oiltemp2;
    }

    public void setOiltemp2(Double oiltemp2) {
        this.oiltemp2 = oiltemp2;
    }

    public Double getOiltemp3() {
        return oiltemp3;
    }

    public void setOiltemp3(Double oiltemp3) {
        this.oiltemp3 = oiltemp3;
    }

    public Double getOiltemp4() {
        return oiltemp4;
    }

    public void setOiltemp4(Double oiltemp4) {
        this.oiltemp4 = oiltemp4;
    }

    public Double getOiltemp5() {
        return oiltemp5;
    }

    public void setOiltemp5(Double oiltemp5) {
        this.oiltemp5 = oiltemp5;
    }

    public Double getOilcubage() {
        return oilcubage;
    }

    public void setOilcubage(Double oilcubage) {
        this.oilcubage = oilcubage;
    }

    public Double getOilstandcubage() {
        return oilstandcubage;
    }

    public void setOilstandcubage(Double oilstandcubage) {
        this.oilstandcubage = oilstandcubage;
    }

    public Double getEmptycubage() {
        return emptycubage;
    }

    public void setEmptycubage(Double emptycubage) {
        this.emptycubage = emptycubage;
    }

    public Double getWaterbulk() {
        return waterbulk;
    }

    public void setWaterbulk(Double waterbulk) {
        this.waterbulk = waterbulk;
    }

    @Override
    public String toString() {
        return "DailyPowerRecord{" +
                "totalheight=" + totalheight +
                ", waterheight=" + waterheight +
                ", oiltemp=" + oiltemp +
                ", oiltemp1=" + oiltemp1 +
                ", oiltemp2=" + oiltemp2 +
                ", oiltemp3=" + oiltemp3 +
                ", oiltemp4=" + oiltemp4 +
                ", oiltemp5=" + oiltemp5 +
                ", oilcubage=" + oilcubage +
                ", oilstandcubage=" + oilstandcubage +
                ", emptycubage=" + emptycubage +
                ", waterbulk=" + waterbulk +
                '}';
    }
}