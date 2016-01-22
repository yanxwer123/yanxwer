package com.kld.gsm.ATG.domain;


import java.util.List;


/*
Created BY niyang
Created Date 2015/11/21
*/
public class DailyMain {

    public List<DailyStationShiftInfo> getStationShiftInfoLst() {
        return stationShiftInfoLst;
    }

    public void setStationShiftInfoLst(List<DailyStationShiftInfo> stationShiftInfoLst) {
        this.stationShiftInfoLst = stationShiftInfoLst;
    }

    public List<DailyOpoCount> getOpoCountLost() {
        return opoCountLost;
    }

    public void setOpoCountLost(List<DailyOpoCount> opoCountLost) {
        this.opoCountLost = opoCountLost;
    }

    public List<DailyTankShift> getTankShiftLost() {
        return tankShiftLost;
    }

    public void setTankShiftLost(List<DailyTankShift> tankShiftLost) {
        this.tankShiftLost = tankShiftLost;
    }

    public List<DailyPumpDigitShift> getPumpDigitShiftLost() {
        return pumpDigitShiftLost;
    }

    public void setPumpDigitShiftLost(List<DailyPumpDigitShift> pumpDigitShiftLost) {
        this.pumpDigitShiftLost = pumpDigitShiftLost;
    }

    private List<DailyStationShiftInfo> stationShiftInfoLst;
    private  List<DailyPumpDigitShift> pumpDigitShiftLost;
    private  List<DailyTankShift> tankShiftLost;
    private  List<DailyOpoCount> opoCountLost;

    public List<DailyOpotCount> getOpotCountLst() {
        return opotCountLst;
    }

    public void setOpotCountLst(List<DailyOpotCount> opotCountLst) {
        this.opotCountLst = opotCountLst;
    }

    private List<DailyOpotCount> opotCountLst;
}
