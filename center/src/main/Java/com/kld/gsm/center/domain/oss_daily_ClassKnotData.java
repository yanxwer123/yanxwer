package com.kld.gsm.center.domain;

import java.util.List;

/*
   Created by fangzhun on 2015/11/20.
   	油站班报表名oss_daily_stationshiftinfo
   	付油量分类统计表名oss_daily_opotcount
   	泵码交接表名oss_daily_pumpdigitshift
   	油罐交接表oss_daily_tankshift
   	付油数量统计交接表oss_daily_opocount
   */
public class oss_daily_ClassKnotData {
    private List<oss_daily_StationShiftInfo> stationShiftInfoLst;
    private List<oss_daily_opotCount> opotCountLst;
    private List<oss_daily_pumpDigitShift> pumpDigitShiftLost;
    private  List<oss_daily_tankshift> tankShiftLost;
    private  List<oss_daily_opoCount> opoCountLost;

    public List<oss_daily_StationShiftInfo> getStationShiftInfoLst() {
        return stationShiftInfoLst;
    }

    public void setStationShiftInfoLst(List<oss_daily_StationShiftInfo> stationShiftInfoLst) {
        this.stationShiftInfoLst = stationShiftInfoLst;
    }

    public List<oss_daily_opotCount> getOpotCountLst() {
        return opotCountLst;
    }

    public void setOpotCountLst(List<oss_daily_opotCount> opotCountLst) {
        this.opotCountLst = opotCountLst;
    }

    public List<oss_daily_pumpDigitShift> getPumpDigitShiftLost() {
        return pumpDigitShiftLost;
    }

    public void setPumpDigitShiftLost(List<oss_daily_pumpDigitShift> pumpDigitShiftLost) {
        this.pumpDigitShiftLost = pumpDigitShiftLost;
    }

    public List<oss_daily_tankshift> getTankShiftLost() {
        return tankShiftLost;
    }

    public void setTankShiftLost(List<oss_daily_tankshift> tankShiftLost) {
        this.tankShiftLost = tankShiftLost;
    }

    public List<oss_daily_opoCount> getOpoCountLost() {
        return opoCountLost;
    }

    public void setOpoCountLost(List<oss_daily_opoCount> opoCountLost) {
        this.opoCountLost = opoCountLost;
    }
}
