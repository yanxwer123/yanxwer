package com.kld.gsm.coord.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/11/10 19:49
 * @Description:油罐进油数据表
 */
public class AtgOilIn {
    private int oilcanno;            //油罐编号
    private Date opetime;           //入库时间
    private String oilno;            //油品编码
    private String StartDate;        //开始日期
    private String StartTime;        //开始时间
    private String EndDate;          //结束日期
    private String EndTime;          //结束时间
    private Double StartCubage;      //初始净油体积
    private Double StartStandCubage; //初始标准体积
    private Double StartOilHeight;   //初始油高
    private Double StartWaterHeight; //初始水高
    private Double StartOilTemp;     //初始温度
    private Double EndCubage;        //结束净油体积
    private Double EndStandCubage;   //结束标准体积
    private Double EndOilHeight;     //结束油高
    private Double EndWaterHeight;   //结束水高
    private Double EndOilTemp;       //结束温度
    private Double EmptyCubage;      //结束空容
    private Double ApparentDensity;  //视密度
    private Double StandDensity;     //标准密度
    private Double PayOilLiter;      //期间加油
    private String TeamVouchNo;      //班次号
    private String GoodsBillNo;      //提货单号
    private String billstatus;       //状态
    private String tranflag;         //通讯状态
    private String remark;           //备注

    public int getOilcanno() {
        return oilcanno;
    }

    public void setOilcanno(int oilcanno) {
        this.oilcanno = oilcanno;
    }

    public Date getOpetime() {
        return opetime;
    }

    public void setOpetime(Date opetime) {
        this.opetime = opetime;
    }

    public String getOilno() {
        return oilno;
    }

    public void setOilno(String oilno) {
        this.oilno = oilno;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Double getStartCubage() {
        return StartCubage;
    }

    public void setStartCubage(Double startCubage) {
        StartCubage = startCubage;
    }

    public Double getStartStandCubage() {
        return StartStandCubage;
    }

    public void setStartStandCubage(Double startStandCubage) {
        StartStandCubage = startStandCubage;
    }

    public Double getStartOilHeight() {
        return StartOilHeight;
    }

    public void setStartOilHeight(Double startOilHeight) {
        StartOilHeight = startOilHeight;
    }

    public Double getStartWaterHeight() {
        return StartWaterHeight;
    }

    public void setStartWaterHeight(Double startWaterHeight) {
        StartWaterHeight = startWaterHeight;
    }

    public Double getStartOilTemp() {
        return StartOilTemp;
    }

    public void setStartOilTemp(Double startOilTemp) {
        StartOilTemp = startOilTemp;
    }

    public Double getEndCubage() {
        return EndCubage;
    }

    public void setEndCubage(Double endCubage) {
        EndCubage = endCubage;
    }

    public Double getEndStandCubage() {
        return EndStandCubage;
    }

    public void setEndStandCubage(Double endStandCubage) {
        EndStandCubage = endStandCubage;
    }

    public Double getEndOilHeight() {
        return EndOilHeight;
    }

    public void setEndOilHeight(Double endOilHeight) {
        EndOilHeight = endOilHeight;
    }

    public Double getEndWaterHeight() {
        return EndWaterHeight;
    }

    public void setEndWaterHeight(Double endWaterHeight) {
        EndWaterHeight = endWaterHeight;
    }

    public Double getEndOilTemp() {
        return EndOilTemp;
    }

    public void setEndOilTemp(Double endOilTemp) {
        EndOilTemp = endOilTemp;
    }

    public Double getEmptyCubage() {
        return EmptyCubage;
    }

    public void setEmptyCubage(Double emptyCubage) {
        EmptyCubage = emptyCubage;
    }

    public Double getApparentDensity() {
        return ApparentDensity;
    }

    public void setApparentDensity(Double apparentDensity) {
        ApparentDensity = apparentDensity;
    }

    public Double getStandDensity() {
        return StandDensity;
    }

    public void setStandDensity(Double standDensity) {
        StandDensity = standDensity;
    }

    public Double getPayOilLiter() {
        return PayOilLiter;
    }

    public void setPayOilLiter(Double payOilLiter) {
        PayOilLiter = payOilLiter;
    }

    public String getTeamVouchNo() {
        return TeamVouchNo;
    }

    public void setTeamVouchNo(String teamVouchNo) {
        TeamVouchNo = teamVouchNo;
    }

    public String getGoodsBillNo() {
        return GoodsBillNo;
    }

    public void setGoodsBillNo(String goodsBillNo) {
        GoodsBillNo = goodsBillNo;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }

    public String getTranflag() {
        return tranflag;
    }

    public void setTranflag(String tranflag) {
        this.tranflag = tranflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
