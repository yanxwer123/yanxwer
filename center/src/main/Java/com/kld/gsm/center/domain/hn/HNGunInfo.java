package com.kld.gsm.center.domain.hn;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by xhz on 2015/11/25.
 */
public class HNGunInfo {
    private String nodeno;
    private int oilcan;
    private Date startTime;
    private Date endTime;
    private Double unloadQty;
    private Double backTankQty;
    private Double saleQty;
    private Double startStock;
    private Double endStock;
    private Double lossQty;
    private Double lossRate;
    public  String getNodeno(){return nodeno;}
    public  void setNodeno(String nodeno){this.nodeno=nodeno;}
    public int getOilcan(){return oilcan;}
    public void setOilcan(int oilcan){this.oilcan=oilcan;}
    public  Date getStartTime(){return startTime;}
    public void setStartTime(Date startTime){this.startTime=startTime;}
    public Date getEndTime(){return  endTime;}
    public void setEndTime(Date endTime){this.endTime=endTime;}
    public double getunloadQty(){return unloadQty;}
    public void setUnloadQty(double unloadQty){this.unloadQty=unloadQty;}
    public  double getBackTankQty(){return backTankQty;}
    public  void setBackTankQty(double backTankQty){this.backTankQty=backTankQty;}
    public Double getSaleQty(){return saleQty;}
    public void  setSaleQty(double saleQty){this.saleQty=saleQty;}
    public double getStartStock(){return startStock;}
    public void setStartStock(double startStock){this.startStock=startStock;}
    public Double getEndStock(){return endStock;}
    public void setEndStock(double endStock){this.endStock=endStock;}
    public double getLossQty(){return lossQty;}
    public void setLossQty(double lossQty){this.lossQty=lossQty;}
    public  double getlossRate(){return lossQty;}
    public  void setLossRate(double lossRate){this.lossRate=lossRate;}
}
