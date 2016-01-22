package com.kld.gsm.ATGDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luyan on 15/11/8.
 */
 class ATGDevice {
    static {
        //System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary("ATGManager");
//        System.loadLibrary("ATGLib");
    }

    /**
     * 加载配置信息
     * @param inputData
     * @return 返回值 0：成功，!0：失败
     */
    public native static int init(atg_init_in_t inputData);
    /**
     * 探棒校正参数设置
     * @param inputData
     * @return 返回值 0：成功，!0：失败
     */
    public native static  int setCorrection(List<atg_correction_data_in_t> inputData);
    /**
     * 探棒油罐配置
     * @param inputData
     * @return 返回值 0：成功，!0：失败
     */
    public native static  int setProbe(List<atg_probecan_data_in_t> inputData);
    /**
     * 实时库存采集
     * @param oilCanNo
     * @return
     */
    public native static  List<atg_stock_data_out_t> getStock(List<Integer> oilCanNo);
    /**
     * 整点库存采集
     * @param timeStockData
     * @return
     */
    public native static  List<atg_stock_data_out_t> getTimeStock(List<atg_timestock_data_in_t> timeStockData);
    /**
     * 进油信息采集
     * @param oilInData
     * @return
     */
    public native static  List<atg_oilin_data_out_t> getOilIn(List<atg_oilin_data_in_t> oilInData);
    /**
     * 油罐报警采集
     * @param alarmData
     * @return
     */
    public native static  List<atg_alarm_data_out_t> getAlarm(List<atg_alarm_data_in_t> alarmData);
    /**
     * 设备故障采集
     * @param failureData
     * @return
     */
    public native static  List<atg_failure_data_out_t> getFailure(List<atg_failure_data_in_t> failureData);
    /**
     * 液位仪对时
     * @param date 管控机时间，格式：YYYYMMDDHHMMSS
     * @return 成功：""，失败：错误描述。
     */
    public native static  String checkTime(String date);
    /**
     * 预报警设置
     * @param setAlarmData
     * @return 成功：""，失败：错误描述。
     */
    public native static  String alarmSetter(List<atg_setalarm_data_in_t> setAlarmData);
    /**
     * 油罐油品变类
     * @param chgOilInfoData
     * @return 成功：""，失败：错误描述。
     */
    public native static  String chgOilInfo(List<atg_chgoilinfo_data_in_t> chgOilInfoData);
    /**
     * 容积表(全罐表)上传
     * @param data
     * @return
     */
    public native static  List<atg_capacity_data_in_t> getCapacityTable(List<Integer> data);
    /**
     * 容积表(全罐表)下发
     * @param data
     * @return 成功：""，失败：错误描述。
     */
    public native static  String setCapacityTable(atg_capacity_data_in_t data);
    /**
     * 启动静态液位异常测试
     * @param data
     * @return
     */
    public native static  String startLiquid(List<atg_startliquid_data_in_t> data);
    /**
     * 停止静态液位异常测试
     * @param data
     * @return
     */
    public native static  List<atg_liquidreport_data_out_t> stopLiquid(List<atg_stopliquid_data_in_t> data);
    /**
     * 静态液位异常测试报告
     * @param data
     * @return
     */
    public native static  List<atg_liquidreport_data_out_t> liquidReport(List<atg_liquidreport_data_in_t> data);
    /**
     * 设备基础信息
     * @param data
     * @return
     */
    public native static  atg_device_out_t getDeviceInfo(List<Integer> data);
    /**
     * 高升转换
     * @param data
     * @return
     */
    public native static  List<atg_hightoliter_data_out_t> HightOLiter(List<atg_hightoliter_in_t> data);
    /**
     * 液位仪开关机记录
     * @param data
     * @return
     */
    public native static  List<atg_powerrecord_data_out_t> getPowerRecord(List<atg_powerrecord_in_t> data);
    /**
     * 释放所占用的资源，断开与相关设备的连接，并清理连接信息
     * @return 成功：0。
     */
    public native static  int clear();

    public native static int atg_param(String strOpeCode,Object inputdata);
    public native static int atg_init(atg_init_in_t input);
    public native static int testList(ArrayList<atg_init_in_t> list);

    public native static ArrayList<atg_init_in_t> getfromc(int count);

    //public native static


    public static void main(String[] args) throws Exception{
        ATGDevice amg = new ATGDevice();
        int ret=0;

        //3.6.1	初始化方法   done
        atg_init_in_t init = new atg_init_in_t();
        init.strDeviceType = "1";
        init.uConnMode = 1;//1:网口 0：串口
        init.strSerialAddress = "/dev/ttyS0";
        init.strSerialBaudRate = "9600";
        init.strSerialStopBit = "1";
        init.strSerialCheckBit = "n";
        init.strSerialDataBit = "8";
        init.strIPAddress = "192.168.0.211";
        init.strLogPath = "20151119.log";
        init.strIPPort = "5656";
//        init.strLogPath = "/smc20/gsm/log/";
//         init.strIPAddress = "192.168.0.111";
//        init.strIPPort = "9500";
        //System.out.println("java  start~~~~~~~~~~~~~~~~");
        ret = amg.init(init);
        //System.out.println("java return ret:"+ret);
        /*//3.6.2.1	探棒校正参数设置 done 联调成功 第二轮成功 a.uOilTy=5;//应该是String 类型  需要修改~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        List<atg_correction_data_in_t> inputData = new ArrayList<atg_correction_data_in_t>();
        atg_correction_data_in_t a = new atg_correction_data_in_t();
        a.strDeviceModel="SP300";
        a.strProbeNo="140153";
        a.uOilTy="5";//应该是String 类型  需要修改~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        a.fOilCorrection=2.2;
        a.fWaterCorrection=2.2;
        a.fProbeOffset=2.2;
        a.fTiltOffset	=2.2;
        a.fTemp1=2.2;
        a.fProbeTemp1	=2.2;
        a.fTemp2=2.2;
        a.fProbeTemp2	=2.2;
        a.fTemp3=2.2;
        a.fProbeTemp3	=2.2;
        a.fTemp4=2.2;
        a.fProbeTemp4	=2.2;
        a.fTemp5=2.2;
        a.fProbeTemp5	=2.2;
        a.fInitDesnsity=2.2;
        a.fInitHighDiff=4.0;
        a.fCorrectionFactor=2.2;
        inputData.add(a);
        int ret1 = amg.setCorrection(inputData);
        //System.out.println("java setCorrection return ret:"+ret1);*/
/*
        //3.6.2.2	探棒油罐配置 done 联调成功
        List<atg_probecan_data_in_t> inputData2 = new ArrayList<atg_probecan_data_in_t>();
        atg_probecan_data_in_t a3 = new atg_probecan_data_in_t();
        a3.strDeviceModel="SS160";
        a3.strProbeNo="140153";
        a3.uProbePort=192;
        a3.uOilCanNo=1;
        a3.uOilType=0;//~~~~~~~~~~~~~~~~~~~~增加strOilType String类型~~~~~~~~~~~~~~~~~
        a3.strOilNo="1901";
        //a.strOilName="oilname";~~~~~~~~~~~~~~~~~~~~增加strOilName String类型~~~~~~~~~~~~~~~~~
        inputData2.add(a3);
        int ret11 = amg.setProbe(inputData2);
        //System.out.println("java setProbe return ret:"+ret11);*/
        /*//3.6.3.1	实时库存采集 done 联调成功 第二轮成功 串口成功
        List<Integer> li = new ArrayList<Integer>();
        li.add(1);
        List<atg_stock_data_out_t> atg_stock_data_out_ts = amg.getStock(li);
        if(atg_stock_data_out_ts!=null) {
            //System.out.println("Java String out" + atg_stock_data_out_ts.size());
            //System.out.println(atg_stock_data_out_ts.get(0).uOilCanNo);
            //System.out.println(atg_stock_data_out_ts.get(0).strDate);
            //System.out.println(atg_stock_data_out_ts.get(0).strTime);
            //System.out.println(atg_stock_data_out_ts.get(0).fOilCubage);
        }*/
        /*//3.6.3.2	整点库存采集 done 联调成功 第二轮成功
        List<atg_timestock_data_in_t> li32 = new ArrayList<atg_timestock_data_in_t>();
        atg_timestock_data_in_t a14 = new atg_timestock_data_in_t();
        a14.uOilCanNo=1;
        a14.strDataTime="20150101123021";
        a14.uReqCount=1;
        li32.add(a14);
        //System.out.println("JAVA Start zhengdiankucun~~~~~~~~~~~~~~~~~");
        List<atg_stock_data_out_t> l = amg.getTimeStock(li32);
        //System.out.println("Java String out:"+l.size());
        //System.out.println("Java list out uOilCanNo:"+l.get(0).uOilCanNo);
        //System.out.println("Java list out strDate:"+l.get(0).strDate);
        //System.out.println("Java list out fOilCubage:"+l.get(0).fOilCubage);
        //System.out.println("Java list out fOilStandCubage:"+l.get(0).fOilStandCubage);
        //System.out.println("Java list out fTotalHeight:"+l.get(0).fTotalHeight);
*/
        /*//3.6.3.3	进油信息采集 done 联调成功 第二轮成功
        List<atg_oilin_data_in_t> datas = new ArrayList<atg_oilin_data_in_t>();
        atg_oilin_data_in_t data = new atg_oilin_data_in_t();
        data.strDataTime = "20160609121313";
        data.uOilCanNO = 1;
        data.uReqCount = 5;
        datas.add(data);
//        atg_oilin_data_in_t data2 = new atg_oilin_data_in_t();
//        data2.strDataTime = "20150202221313";
//        data2.uOilCanNO = 2;
//        data2.uReqCount = 1;
//        datas.add(data2);
        List<atg_oilin_data_out_t> retatg_oilin_data_out_t = amg.getOilIn(datas);
        //System.out.println("Java String out:"+retatg_oilin_data_out_t.size());
        for(int i=0;i<retatg_oilin_data_out_t.size();i++) {
            //System.out.println("Java list out uOilCanNO:"+i+"号" + retatg_oilin_data_out_t.get(i).uOilCanNO);
            //System.out.println("Java list out strStartDate:"+i+"号" + retatg_oilin_data_out_t.get(i).strStartDate);
            //System.out.println("Java list out fStartCubage:"+i+"号" + retatg_oilin_data_out_t.get(i).fStartCubage);
        }*/

        /*//3.6.3.4	油罐报警采集 done 联调成功 第二轮成功
        List<atg_alarm_data_in_t> alarmData = new ArrayList<atg_alarm_data_in_t>();
        atg_alarm_data_in_t a = new atg_alarm_data_in_t();
        a.uOilCanNO=1;
        a.strDataTime="20150101123231";
        a.uReqCount=2;
        alarmData.add(a);
        List<atg_alarm_data_out_t> li5 =amg.getAlarm(alarmData);
        //System.out.println("Java out"+li5.size());
        for(int i=0;i<li5.size();i++) {
            //System.out.println("Java list out uOilCanNO:" + li5.get(i).uOilCanNO);
            //System.out.println("Java list out strDate:" + li5.get(i).strDate);
            //System.out.println("Java list out strTime:" + li5.get(i).strTime);
            //System.out.println("Java list out strAlarmType:" + li5.get(i).strAlarmType);
        }
        //3.6.3.5	设备故障采集 done 第二轮成功
        List<atg_failure_data_in_t> failureData = new ArrayList<atg_failure_data_in_t>();
        atg_failure_data_in_t a11=  new atg_failure_data_in_t();
        a11.uOilCanNO=1;
        a11.strDataTime="20150101121212";
        a11.uReqCount=2;
        failureData.add(a11);
        List<atg_failure_data_out_t> li1 = amg.getFailure(failureData);
        //System.out.println("Java String out atg_failure_data_out_t:"+li1.size());
        for(int i=0;i<li1.size();i++) {
            //System.out.println(li1.get(i).uOilCanNO);
            //System.out.println(li1.get(i).strDate);
            //System.out.println(li1.get(i).strTime);
            //System.out.println(li1.get(i).strDeviceType);
            //System.out.println(li1.get(i).strFailureType);
            //System.out.println(li1.get(i).strEquipCode);
        }*/
        /*//3.6.3.6	液位仪对时 done 联调成功 第二轮成功
        //YYYYMMDDHHMMSS
        String pInputData ="20151114223112";
        String a2=amg.checkTime(pInputData);
        //System.out.println("checkTime~~~~:"+a2);*/

        //3.6.3.7	预报警设置 done 联调成功 第二轮成功
        List<atg_setalarm_data_in_t> setAlarmData = new ArrayList<atg_setalarm_data_in_t>();
        atg_setalarm_data_in_t a1 = new atg_setalarm_data_in_t();
        a1.uOilCanNO=1;
        a1.fLowWarning=160.88;
        a1.fLowAlarm=140.12;
        a1.fHighWarning=370.09;
        a1.fHighAlarm=380.54;
        a1.fWaterAlarm=130.2;
        //盗油报警,单位：升/小时，默认300L/H
        //漏油报警,单位：升/小时，默认60L/H
        //渗漏报警,单位：升/小时，默认0.8L/H
        //高温报警，单位：摄氏度。温度>=55
        //低温报警，单位：摄氏度。温度<=-10
        a1.fThiefAlarm=300;
        a1.fLeakAlarm=60;
        a1.fPercolatingAlarm=0.8;
        a1.fHighTempAlarm = 55;
        a1.fLowTempAlarm=-10;
        setAlarmData.add(a1);
        String liatg_setalarm_data_in_t = amg.alarmSetter(setAlarmData);
        //System.out.println("Java out liatg_setalarm_data_in_t:"+liatg_setalarm_data_in_t);

        /*//3.6.3.8	油罐油品变类 done 方法废弃
        List<atg_chgoilinfo_data_in_t> chgOilInfoData = new ArrayList<atg_chgoilinfo_data_in_t>();
        atg_chgoilinfo_data_in_t a = new atg_chgoilinfo_data_in_t();
        a.uOilCanNO=1;
        a.strOilNo="222";
        a.strOilName="油罐油品";
        chgOilInfoData.add(a);
        String li = amg.chgOilInfo(chgOilInfoData);
        //System.out.println("Java out oiltype :"+li);*/

        /*//3.6.3.9	容积表(全罐表)上传  done  联调成功 第二轮成功
        List<Integer> li = new ArrayList<Integer>();
        li.add(1);
        li.add(2);
        List<atg_capacity_data_in_t> ret1 = amg.getCapacityTable(li);
        //System.out.println("Java out ret1.size~~~~"+ret1.size());
        for(int i=0;i<ret1.size();i++) {
            //System.out.println("Java out ret.get("+i+").uOilCanNO:" + ret1.get(i).uOilCanNO);
            //System.out.println("Java out ret.get("+i+").uCapacitySize:" + ret1.get(i).uCapacitySize);
            List<atg_capacitytable_data_in_t> ret2 = ret1.get(i).pCapacityTableData;
            //System.out.println("ret2~~~~~~~~~~~~~~~"+ret2.size());
            for(int j=0;j<ret2.size();j++) {
                //System.out.println(i+"Java out ret2.get("+j+").fLiter:" + ret2.get(j).fLiter);
                //System.out.println(i+"Java out ret2.get("+j+").uHigh:" + ret2.get(j).uHigh);
            }
        }
        //3.6.3.10	容积表(全罐表)下发 done 联调成功 第二轮成功
        atg_capacity_data_in_t data = new atg_capacity_data_in_t();
        data.uCapacitySize=1;
        data.uOilCanNO=1;
        ArrayList<atg_capacitytable_data_in_t> list = new ArrayList<atg_capacitytable_data_in_t>();
        atg_capacitytable_data_in_t a = new atg_capacitytable_data_in_t();
        a.uHigh=1;
        a.fLiter=11.11;
        list.add(a);
        atg_capacitytable_data_in_t b = new atg_capacitytable_data_in_t();
        b.uHigh=22;
        b.fLiter=22.22;
        list.add(b);
        data.pCapacityTableData = list;
        String ret2 = amg.setCapacityTable(data);
        //System.out.println("ret1~~~~~~~~~~~~~~~~~~:"+ret2);

        //3.6.3.11	启动静态液位异常测试 done 联调成功 第二轮成功
        List<atg_startliquid_data_in_t> LiquidData =new ArrayList<atg_startliquid_data_in_t>();
        atg_startliquid_data_in_t a3 =new atg_startliquid_data_in_t();
        a3.uOilCanNo=1;
        a3.uTestType=0;
        a3.strDataTime="20151118093500";
        a3.uTestDuration=2;
        a3.fTestRate=0.76;
        LiquidData.add(a3);
        String startLiquid =amg.startLiquid(LiquidData);
        //System.out.println("java out startLiquid:"+startLiquid);*/
        /*//3.6.3.12	停止静态液位异常测试  done 联调成功 第二轮成功
        List<atg_stopliquid_data_in_t> liquidData = new ArrayList<atg_stopliquid_data_in_t>();
        atg_stopliquid_data_in_t a=  new atg_stopliquid_data_in_t();
        a.uOilCanNo=1;
        a.uTestType=0;
        liquidData.add(a);
        List<atg_liquidreport_data_out_t> li = amg.stopLiquid(liquidData);
        //System.out.println("Java String out"+li.size());
        for(int i=0;i<li.size();i++){
            //System.out.println("uOilCanNo "+li.get(i).uOilCanNo);
            //System.out.println("uRevealStatus"+li.get(i).uRevealStatus);
            //System.out.println("fRevealRate "+li.get(i).fRevealRate);
            //System.out.println("strStartDate "+li.get(i).strStartDate);
            //System.out.println("strStartTime "+li.get(i).strStartTime);
            //System.out.println("fStartOilHeight "+li.get(i).fStartOilHeight);
            //System.out.println("fStartWaterHeight"+li.get(i).fStartWaterHeight);
            //System.out.println("fStartOilTemp "+li.get(i).fStartOilTemp);
            //System.out.println("fStartOilTemp1 "+li.get(i).fStartOilTemp1);
            //System.out.println("fStartOilTemp2 "+li.get(i).fStartOilTemp2);
            //System.out.println("fStartOilTemp3 "+li.get(i).fStartOilTemp3);
            //System.out.println("fStartOilTemp4 "+li.get(i).fStartOilTemp4);
            //System.out.println("fStartOilTemp5 "+li.get(i).fStartOilTemp5);
            //System.out.println("fStartOilCubage"+li.get(i).fStartOilCubage);
            //System.out.println("fStartOilStandCubage"+li.get(i).fStartOilStandCubage);
            //System.out.println("fStartEmptyCubage "+li.get(i).fStartEmptyCubage);
            //System.out.println("fStartWaterBulk "+li.get(i).fStartWaterBulk);
            //System.out.println("strEndDate "+li.get(i).strEndDate);
            //System.out.println("strEndTime "+li.get(i).strEndTime);
            //System.out.println("fEndOilHeight"+li.get(i).fEndOilHeight);
            //System.out.println("fEndWaterHeight"+li.get(i).fEndWaterHeight);
            //System.out.println("fEndOilTemp "+li.get(i).fEndOilTemp);
            //System.out.println("fEndOilTemp1"+li.get(i).fEndOilTemp1);
            //System.out.println("fEndOilTemp2 "+li.get(i).fEndOilTemp2);
            //System.out.println("fEndOilTemp3 "+li.get(i).fEndOilTemp3);
            //System.out.println("fEndOilTemp4 "+li.get(i).fEndOilTemp4);
            //System.out.println("fEndOilTemp5 "+li.get(i).fEndOilTemp5);
            //System.out.println("fEndOilCubage "+li.get(i).fEndOilCubage);
            //System.out.println("fEndOilStandCubage"+li.get(i).fEndOilStandCubage);
            //System.out.println("fEndEmptyCubage"+li.get(i).fEndEmptyCubage);
            //System.out.println("fEndWaterBulk"+li.get(i).fEndWaterBulk);
        }*/

        /*//3.6.3.13	静态液位异常测试报告 done 第二轮成功
        List<atg_liquidreport_data_in_t> liquidData3 = new ArrayList<atg_liquidreport_data_in_t>();
        atg_liquidreport_data_in_t a2=  new atg_liquidreport_data_in_t();
        a2.uOilCanNo=1;
        a2.strDataTime="20150101080812";
        a2.uTestType=0;
        a2.uReqCount=1;
        liquidData3.add(a2);
        List<atg_liquidreport_data_out_t> li2 = amg.liquidReport(liquidData3);
        //System.out.println("Java String out" + li2.size());
        for(int i=0;i<li2.size();i++){
            //System.out.println(li2.get(i).uOilCanNo);
            //System.out.println(li2.get(i).uRevealStatus);
            //System.out.println(li2.get(i).fRevealRate);
            //System.out.println(li2.get(i).strStartDate);
            //System.out.println(li2.get(i).strStartTime);
            //System.out.println(li2.get(i).fStartOilHeight);
            //System.out.println(li2.get(i).fStartWaterHeight);
            ////System.out.println(li.get(i).fStartOilTemp);
            //System.out.println(li2.get(i).fStartOilTemp1);
            //System.out.println(li2.get(i).fStartOilTemp2);
            //System.out.println(li2.get(i).fStartOilTemp3);
            //System.out.println(li2.get(i).fStartOilTemp4);
            //System.out.println(li2.get(i).fStartOilTemp5);
            //System.out.println(li2.get(i).fStartOilCubage);
            //System.out.println(li2.get(i).fStartOilStandCubage);
            //System.out.println(li2.get(i).fStartEmptyCubage);
            //System.out.println(li2.get(i).fStartWaterBulk);
            //System.out.println(li2.get(i).strEndDate);
            //System.out.println(li2.get(i).strEndTime);
            //System.out.println(li2.get(i).fEndOilHeight);
            //System.out.println(li2.get(i).fEndWaterHeight);
            ////System.out.println(2li.get(i).fEndOilTemp);
            //System.out.println(li2.get(i).fEndOilTemp1);
            //System.out.println(li2.get(i).fEndOilTemp2);
            //System.out.println(li2.get(i).fEndOilTemp3);
            //System.out.println(li2.get(i).fEndOilTemp4);
            //System.out.println(li2.get(i).fEndOilTemp5);
            //System.out.println(li2.get(i).fEndOilCubage);
            //System.out.println(li2.get(i).fEndOilStandCubage);
            //System.out.println(li2.get(i).fEndEmptyCubage);
            //System.out.println(li2.get(i).fEndWaterBulk);
        }*/

        /*//3.6.3.14	设备基础信息 done    返回参数为OBJECT not LIST   联调成功 第二轮成功
        List<Integer> data = new ArrayList<Integer>();
        data.add(1);
        atg_device_out_t ret1 = amg.getDeviceInfo(data);
        //System.out.println("Java out ret.strDeviceModel:" + ret1.strDeviceModel);
        //System.out.println("Java out ret.strEquipCode:" + ret1.strEquipCode);
        //System.out.println("Java out ret.strSysVersion:" + ret1.strSysVersion);
        //System.out.println("Java out ret.strMakeDate:" + ret1.strMakeDate);
        //System.out.println("Java out ret.uRetCount:" + ret1.uRetCount);
        List<atg_device_data_out_t> ret2 = ret1.pDeviceData;
        for(int j=0;j<ret2.size();j++){
            //System.out.println("Java out ret.uOilCanNo:" + ret2.get(j).uOilCanNo);
            //System.out.println("Java out ret.strProbeNo:" + ret2.get(j).strProbeNo);
            //System.out.println("Java out ret.strProbeModel:" + ret2.get(j).strProbeModel);
        }*/
        /*//3.6.3.15	高升转换 done   atg_hightoliter_in_t的uCont要改为int型。否则C取不到值   联调成功 第二轮成功
        List<atg_capacity_data_in_t> capacityData = new ArrayList<atg_capacity_data_in_t>();
        List<atg_hightoliter_in_t> highToLiterData = new ArrayList<atg_hightoliter_in_t>();
        atg_hightoliter_in_t b =new atg_hightoliter_in_t();
        List<atg_capacitytable_data_in_t> d=new ArrayList<atg_capacitytable_data_in_t>();
        atg_capacity_data_in_t a12 = new atg_capacity_data_in_t();
        a12.uOilCanNO = 1;
        a12.uCapacitySize = 50;
        for(int i=0;i<50;i++) {
            atg_capacitytable_data_in_t c =new atg_capacitytable_data_in_t();
            c.fLiter = 2.1+i*10;
            c.uHigh = 3+i;
            d.add(c);
            //System.out.println("Java out c.fLiter:" + c.fLiter);
            //System.out.println("Java out c.uHigh:"+c.uHigh);
        }
        a12.pCapacityTableData=d;
        capacityData.add(a12);
        b.uCount=1;
        b.fTotalHeight=40;
        b.fWaterHeight=23.2;
        b.pCapacityData=capacityData;
        highToLiterData.add(b);
        List<atg_hightoliter_data_out_t> li = amg.HightOLiter(highToLiterData);
        //System.out.println("atg_hightoliter_data_out_t size:"+li.size());
        for(int i=0;i<li.size();i++) {
            //System.out.println(i+"atg_hightoliter_data_out_t uOilCanNo:"+li.get(i).uOilCanNo);
            //System.out.println(i+"atg_hightoliter_data_out_t fOilCubage:"+li.get(i).fOilCubage);
            //System.out.println(i+"atg_hightoliter_data_out_t fOilStandCubage:"+li.get(i).fOilStandCubage);
            //System.out.println(i+"atg_hightoliter_data_out_t fEmptyCubage:"+li.get(i).fEmptyCubage);
            //System.out.println(i+"atg_hightoliter_data_out_t fWaterBulk:"+li.get(i).fWaterBulk);
        }

        //3.6.3.16	液位仪开关机记录 done 联调成功 第二轮成功
        List<atg_powerrecord_in_t> data = new ArrayList<atg_powerrecord_in_t>();
        atg_powerrecord_in_t a4 = new atg_powerrecord_in_t();
        a4.uReqCount=5;
        a4.strDataTime="20150914080812";
        data.add(a4);
        List<atg_powerrecord_data_out_t> ret4 = amg.getPowerRecord(data);
        for(int i=0;i<ret4.size();i++) {
            //System.out.println("java out  strDate:" + ret4.get(i).strDate);
            //System.out.println("java out  strTime:" + ret4.get(i).strTime);
            //System.out.println("java out  strOperateType:" + ret4.get(i).strOperateType);
            //System.out.println("java out  uOilCanNO:" + ret4.get(i).uOilCanNO);
            //System.out.println("java out  fTotalHeight:" + ret4.get(i).fTotalHeight);
            //System.out.println("java out  fWaterHeight:" + ret4.get(i).fWaterHeight);
            //System.out.println("java out  fOilTemp:" + ret4.get(i).fOilTemp);
            //System.out.println("java out  fOilTemp1:" + ret4.get(i).fOilTemp1);
            //System.out.println("java out  fOilTemp2:" + ret4.get(i).fOilTemp2);
            //System.out.println("java out  fOilTemp3:" + ret4.get(i).fOilTemp3);
            //System.out.println("java out  fOilTemp4:" + ret4.get(i).fOilTemp4);
            //System.out.println("java out  fOilTemp5:" + ret4.get(i).fOilTemp5);
            //System.out.println("java out  fOilCubage:" + ret4.get(i).fOilCubage);
            //System.out.println("java out  fOilStandCubage:" + ret4.get(i).fOilStandCubage);
            //System.out.println("java out  fEmptyCubage:" + ret4.get(i).fEmptyCubage);
            //System.out.println("java out  fWaterBulk:" + ret4.get(i).fWaterBulk);
        }*/
        //清除
        amg.clear();
    }
}
