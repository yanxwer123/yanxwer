package com.kld.gsm.ATG.domain;

public class MonitorInventory extends MonitorInventoryKey {
    private double oilcubage;

    private double standcubage;

    private double emptycubage;

    private double totalheight;

    private double waterheight;

    private double temp;

    private double temp1;

    private double temp2;

    private double temp3;

    private double temp4;

    private double temp5;

    private double waterbulk;

    private double apparentdensity;

    private double standdensity;

    private String transtatus;

    private String oilno;

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getOilcubage() {
        return oilcubage;
    }

    public void setOilcubage(double oilcubage) {
        this.oilcubage = oilcubage;
    }

   public String getTranstatus(){return transtatus;}
    public  void  setGetTranstatus(String transtatus){this.transtatus=transtatus;}
    public double getStandcubage() {
        return standcubage;
    }

    public void setStandcubage(double standcubage) {
        this.standcubage = standcubage;
    }

    public double getEmptycubage() {
        return emptycubage;
    }

    public void setEmptycubage(double emptycubage) {
        this.emptycubage = emptycubage;
    }

    public double getTotalheight() {
        return totalheight;
    }

    public void setTotalheight(double totalheight) {
        this.totalheight = totalheight;
    }

    public double getWaterheight() {
        return waterheight;
    }

    public void setWaterheight(double waterheight) {
        this.waterheight = waterheight;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTemp1() {
        return temp1;
    }

    public void setTemp1(double temp1) {
        this.temp1 = temp1;
    }

    public double getTemp2() {
        return temp2;
    }

    public void setTemp2(double temp2) {
        this.temp2 = temp2;
    }

    public double getTemp3() {
        return temp3;
    }

    public void setTemp3(double temp3) {
        this.temp3 = temp3;
    }

    public double getTemp4() {
        return temp4;
    }

    public void setTemp4(double temp4) {
        this.temp4 = temp4;
    }

    public double getTemp5() {
        return temp5;
    }

    public void setTemp5(double temp5) {
        this.temp5 = temp5;
    }

    public double getWaterbulk() {
        return waterbulk;
    }

    public void setWaterbulk(double waterbulk) {
        this.waterbulk = waterbulk;
    }

    public double getApparentdensity() {
        return apparentdensity;
    }

    public void setApparentdensity(double apparentdensity) {
        this.apparentdensity = apparentdensity;
    }

    public double getStanddensity() {
        return standdensity;
    }

    public void setStanddensity(double standdensity) {
        this.standdensity = standdensity;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus == null ? null : transtatus.trim();
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    @Override
    public String toString() {
        return "MonitorInventory{" +
                "oilcubage=" + oilcubage +
                ", standcubage=" + standcubage +
                ", emptycubage=" + emptycubage +
                ", totalheight=" + totalheight +
                ", waterheight=" + waterheight +
                ", temp=" + temp +
                ", temp1=" + temp1 +
                ", temp2=" + temp2 +
                ", temp3=" + temp3 +
                ", temp4=" + temp4 +
                ", temp5=" + temp5 +
                ", waterbulk=" + waterbulk +
                ", apparentdensity=" + apparentdensity +
                ", standdensity=" + standdensity +
                ", transtatus='" + transtatus + '\'' +
                ", oilno='" + oilno + '\'' +
                '}';
    }
}