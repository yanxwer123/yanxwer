package com.kld.gsm.center.domain;

public class oss_monitor_Inventory extends oss_monitor_InventoryKey {

    private String oilno;

    private Double oilcubage;

    private Double standcubage;

    private Double emptycubage;

    private Double totalheight;

    private Double waterheight;

    private Double temp;

    private Double temp1;

    private Double temp2;

    private Double temp3;

    private Double temp4;

    private Double temp5;

    private Double waterbulk;

    private Double apparentdensity;

    private Double standdensity;

    private String version;

    private String oucode;

    private String transtatus;

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public Double getOilcubage() {
        return oilcubage;
    }

    public void setOilcubage(Double oilcubage) {
        this.oilcubage = oilcubage;
    }

    public Double getStandcubage() {
        return standcubage;
    }

    public void setStandcubage(Double standcubage) {
        this.standcubage = standcubage;
    }

    public Double getEmptycubage() {
        return emptycubage;
    }

    public void setEmptycubage(Double emptycubage) {
        this.emptycubage = emptycubage;
    }

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

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp1() {
        return temp1;
    }

    public void setTemp1(Double temp1) {
        this.temp1 = temp1;
    }

    public Double getTemp2() {
        return temp2;
    }

    public void setTemp2(Double temp2) {
        this.temp2 = temp2;
    }

    public Double getTemp3() {
        return temp3;
    }

    public void setTemp3(Double temp3) {
        this.temp3 = temp3;
    }

    public Double getTemp4() {
        return temp4;
    }

    public void setTemp4(Double temp4) {
        this.temp4 = temp4;
    }

    public Double getTemp5() {
        return temp5;
    }

    public void setTemp5(Double temp5) {
        this.temp5 = temp5;
    }

    public Double getWaterbulk() {
        return waterbulk;
    }

    public void setWaterbulk(Double waterbulk) {
        this.waterbulk = waterbulk;
    }

    public Double getApparentdensity() {
        return apparentdensity;
    }

    public void setApparentdensity(Double apparentdensity) {
        this.apparentdensity = apparentdensity;
    }

    public Double getStanddensity() {
        return standdensity;
    }

    public void setStanddensity(Double standdensity) {
        this.standdensity = standdensity;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode == null ? null : oucode.trim();
    }
    public String getTranstatus() {
        return transtatus;
    }
    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus ;
    }

}