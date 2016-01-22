package com.kld.gsm.ATGDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/19 21:57
 * @Description:
 */
public class ATGDeviceWhileMain {

    public static void main(String[] args) {
        int ret = 0;
        int count=0;
        ATGDevice amg = new ATGDevice();
        //3.6.1	初始化方法   done
        atg_init_in_t init = new atg_init_in_t();
        init.strDeviceType = "1";
        init.uConnMode = 1;//1:网口 0：串口
        init.strSerialAddress = "/dev/ttyS0";
        init.strSerialBaudRate = "9600";
        init.strSerialStopBit = "1";
        init.strSerialCheckBit = "n";
        init.strSerialDataBit = "8";
//        init.strIPAddress = "192.168.0.211";
//        init.strLogPath = "20151119.log";
//        init.strIPPort = "5656";
        init.strLogPath = "/smc20/gsm/log/";
        init.strIPAddress = "192.168.0.111";
        init.strIPPort = "9500";
        //System.out.println("java  start~~~~~~~~~~~~~~~~");
        ret = amg.init(init);
        //System.out.println("java return ret:" + ret);
        while (true) {
            //3.6.2.1	探棒校正参数设置 done 联调成功 第二轮成功 a.uOilTy=5;//应该是String 类型  需要修改~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            List<atg_correction_data_in_t> inputData = new ArrayList<atg_correction_data_in_t>();
            atg_correction_data_in_t a = new atg_correction_data_in_t();
            a.strDeviceModel = "SP300";
            a.strProbeNo = "140153";
            a.uOilTy = "5";//应该是String 类型  需要修改~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            a.fOilCorrection = 2.2;
            a.fWaterCorrection = 2.2;
            a.fProbeOffset = 2.2;
            a.fTiltOffset = 2.2;
            a.fTemp1 = 2.2;
            a.fProbeTemp1 = 2.2;
            a.fTemp2 = 2.2;
            a.fProbeTemp2 = 2.2;
            a.fTemp3 = 2.2;
            a.fProbeTemp3 = 2.2;
            a.fTemp4 = 2.2;
            a.fProbeTemp4 = 2.2;
            a.fTemp5 = 2.2;
            a.fProbeTemp5 = 2.2;
            a.fInitDesnsity = 2.2;
            a.fInitHighDiff = 4.0;
            a.fCorrectionFactor = 2.2;
            inputData.add(a);
            int ret1 = amg.setCorrection(inputData);
            //System.out.println("java setCorrection return ret:" + ret1);

            //3.6.2.2	探棒油罐配置 done 联调成功
            List<atg_probecan_data_in_t> inputData2 = new ArrayList<atg_probecan_data_in_t>();
            atg_probecan_data_in_t a3 = new atg_probecan_data_in_t();
            a3.strDeviceModel = "SS160";
            a3.strProbeNo = "140153";
            a3.uProbePort = 192;
            a3.uOilCanNo = 1;
            a3.uOilType = "0";//~~~~~~~~~~~~~~~~~~~~增加strOilType String类型~~~~~~~~~~~~~~~~~
            a3.strOilNo = "1901";
            a3.strOilName="oilname";//~~~~~~~~~~~~~~~~~~~~增加strOilName String类型~~~~~~~~~~~~~~~~~
            inputData2.add(a3);
            int ret11 = amg.setProbe(inputData2);
            //System.out.println("java setProbe return ret:" + ret11);
            //3.6.3.1	实时库存采集 done 联调成功 第二轮成功 串口成功
            List<Integer> li = new ArrayList<Integer>();
            li.add(1);
            List<atg_stock_data_out_t> atg_stock_data_out_ts = amg.getStock(li);
            if (atg_stock_data_out_ts != null) {
                //System.out.println("Java String out" + atg_stock_data_out_ts.size());
                //System.out.println(atg_stock_data_out_ts.get(0).uOilCanNo);
                //System.out.println(atg_stock_data_out_ts.get(0).strDate);
                //System.out.println(atg_stock_data_out_ts.get(0).strTime);
                //System.out.println(atg_stock_data_out_ts.get(0).fOilCubage);
            }
            //3.6.3.2	整点库存采集 done 联调成功 第二轮成功
            List<atg_timestock_data_in_t> li32 = new ArrayList<atg_timestock_data_in_t>();
            atg_timestock_data_in_t a14 = new atg_timestock_data_in_t();
            a14.uOilCanNo = 1;
            a14.strDataTime = "20150101123021";
            a14.uReqCount = 1;
            li32.add(a14);
            //System.out.println("JAVA Start zhengdiankucun~~~~~~~~~~~~~~~~~");
            List<atg_stock_data_out_t> l = amg.getTimeStock(li32);
            //System.out.println("Java String out:" + l.size());
            //System.out.println("Java list out uOilCanNo:" + l.get(0).uOilCanNo);
            //System.out.println("Java list out strDate:" + l.get(0).strDate);
            //System.out.println("Java list out fOilCubage:" + l.get(0).fOilCubage);
            //System.out.println("Java list out fOilStandCubage:" + l.get(0).fOilStandCubage);
            //System.out.println("Java list out fTotalHeight:" + l.get(0).fTotalHeight);

            //3.6.3.3	进油信息采集 done 联调成功 第二轮成功
            List<atg_oilin_data_in_t> datas = new ArrayList<atg_oilin_data_in_t>();
            atg_oilin_data_in_t data = new atg_oilin_data_in_t();
            data.strDataTime = "20150609121313";
            data.uOilCanNO = 1;
            data.uReqCount = 5;
            datas.add(data);
//        atg_oilin_data_in_t data2 = new atg_oilin_data_in_t();
//        data2.strDataTime = "20150202221313";
//        data2.uOilCanNO = 2;
//        data2.uReqCount = 1;
//        datas.add(data2);
            List<atg_oilin_data_out_t> retatg_oilin_data_out_t = amg.getOilIn(datas);
            //System.out.println("Java String out:" + retatg_oilin_data_out_t.size());
            for (int i = 0; i < retatg_oilin_data_out_t.size(); i++) {
                //System.out.println("Java list out uOilCanNO:" + i + "号" + retatg_oilin_data_out_t.get(i).uOilCanNO);
                //System.out.println("Java list out strStartDate:" + i + "号" + retatg_oilin_data_out_t.get(i).strStartDate);
                //System.out.println("Java list out fStartCubage:" + i + "号" + retatg_oilin_data_out_t.get(i).fStartCubage);
            }

            //3.6.3.4	油罐报警采集 done 联调成功 第二轮成功
            List<atg_alarm_data_in_t> alarmData = new ArrayList<atg_alarm_data_in_t>();
            atg_alarm_data_in_t aaaaa = new atg_alarm_data_in_t();
            aaaaa.uOilCanNO = 1;
            aaaaa.strDataTime = "20150101123231";
            aaaaa.uReqCount = 2;
            alarmData.add(aaaaa);
            List<atg_alarm_data_out_t> li5 = amg.getAlarm(alarmData);
            //System.out.println("Java out" + li5.size());
            for (int i = 0; i < li5.size(); i++) {
                //System.out.println("Java list out uOilCanNO:" + li5.get(i).uOilCanNO);
                //System.out.println("Java list out strDate:" + li5.get(i).strDate);
                //System.out.println("Java list out strTime:" + li5.get(i).strTime);
                //System.out.println("Java list out strAlarmType:" + li5.get(i).strAlarmType);
            }
            //3.6.3.5	设备故障采集 done 第二轮成功
            List<atg_failure_data_in_t> failureData = new ArrayList<atg_failure_data_in_t>();
            atg_failure_data_in_t a11 = new atg_failure_data_in_t();
            a11.uOilCanNO = 1;
            a11.strDataTime = "20150101121212";
            a11.uReqCount = 2;
            failureData.add(a11);
            List<atg_failure_data_out_t> li1 = amg.getFailure(failureData);
            //System.out.println("Java String out atg_failure_data_out_t:" + li1.size());
            for (int i = 0; i < li1.size(); i++) {
                //System.out.println(li1.get(i).uOilCanNO);
                //System.out.println(li1.get(i).strDate);
                //System.out.println(li1.get(i).strTime);
                //System.out.println(li1.get(i).strDeviceType);
                //System.out.println(li1.get(i).strFailureType);
                //System.out.println(li1.get(i).strEquipCode);
            }
            //3.6.3.6	液位仪对时 done 联调成功 第二轮成功
            //YYYYMMDDHHMMSS
            String pInputData = "20151114223112";
            String a2a = amg.checkTime(pInputData);
            //System.out.println("checkTime~~~~:" + a2a);

            //3.6.3.7	预报警设置 done 联调成功 第二轮成功
            List<atg_setalarm_data_in_t> setAlarmData = new ArrayList<atg_setalarm_data_in_t>();
            atg_setalarm_data_in_t a1 = new atg_setalarm_data_in_t();
            a1.uOilCanNO = 1;
            a1.fLowWarning = 160.88;
            a1.fLowAlarm = 140.12;
            a1.fHighWarning = 470.09;
            a1.fHighAlarm = 480.54;
            a1.fWaterAlarm = 230.2;
            //盗油报警,单位：升/小时，默认300L/H
            //漏油报警,单位：升/小时，默认60L/H
            //渗漏报警,单位：升/小时，默认0.8L/H
            //高温报警，单位：摄氏度。温度>=55
            //低温报警，单位：摄氏度。温度<=-10
            a1.fThiefAlarm = 300;
            a1.fLeakAlarm = 60;
            a1.fPercolatingAlarm = 0.8;
            a1.fHighTempAlarm = 55;
            a1.fLowTempAlarm = -10;
            setAlarmData.add(a1);
            String liatg_setalarm_data_in_t = amg.alarmSetter(setAlarmData);
            //System.out.println("Java out liatg_setalarm_data_in_t:" + liatg_setalarm_data_in_t);


            //3.6.3.9	容积表(全罐表)上传  done  联调成功 第二轮成功
            List<Integer> lili = new ArrayList<Integer>();
            lili.add(1);
            lili.add(2);
            List<atg_capacity_data_in_t> ret1111 = amg.getCapacityTable(lili);
            //System.out.println("Java out ret1.size~~~~" + ret1111.size());
            for (int i = 0; i < ret1111.size(); i++) {
                //System.out.println("Java out ret.get(" + i + ").uOilCanNO:" + ret1111.get(i).uOilCanNO);
                //System.out.println("Java out ret.get(" + i + ").uCapacitySize:" + ret1111.get(i).uCapacitySize);
                List<atg_capacitytable_data_in_t> ret2 = ret1111.get(i).pCapacityTableData;
                //System.out.println("ret2~~~~~~~~~~~~~~~" + ret2.size());
                for (int j = 0; j < ret2.size(); j++) {
                    //System.out.println(i + "Java out ret2.get(" + j + ").fLiter:" + ret2.get(j).fLiter);
                    //System.out.println(i + "Java out ret2.get(" + j + ").uHigh:" + ret2.get(j).uHigh);
                }
            }
            //3.6.3.10	容积表(全罐表)下发 done 联调成功 第二轮成功
            atg_capacity_data_in_t data321 = new atg_capacity_data_in_t();
            data321.uCapacitySize = 1;
            data321.uOilCanNO = 1;
            ArrayList<atg_capacitytable_data_in_t> list = new ArrayList<atg_capacitytable_data_in_t>();
            atg_capacitytable_data_in_t aaa = new atg_capacitytable_data_in_t();
            aaa.uHigh = 1;
            aaa.fLiter = 11.11;
            list.add(aaa);
            atg_capacitytable_data_in_t b = new atg_capacitytable_data_in_t();
            b.uHigh = 22;
            b.fLiter = 22.22;
            list.add(b);
            data321.pCapacityTableData = list;
            String ret2 = amg.setCapacityTable(data321);
            //System.out.println("ret1~~~~~~~~~~~~~~~~~~:" + ret2);

            //3.6.3.11	启动静态液位异常测试 done 联调成功 第二轮成功
            List<atg_startliquid_data_in_t> LiquidData = new ArrayList<atg_startliquid_data_in_t>();
            atg_startliquid_data_in_t a3333 = new atg_startliquid_data_in_t();
            a3333.uOilCanNo = 1;
            a3333.uTestType = 0;
            a3333.strDataTime = "20151118093500";
            a3333.uTestDuration = 2;
            a3333.fTestRate = 0.76;
            LiquidData.add(a3333);
            String startLiquid = amg.startLiquid(LiquidData);
            //System.out.println("java out startLiquid:" + startLiquid);

            //System.out.println("java sleep 60s~~~~~~~~~");
            try {
                Thread.sleep(60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //3.6.3.12	停止静态液位异常测试  done 联调成功 第二轮成功
            List<atg_stopliquid_data_in_t> liquidData = new ArrayList<atg_stopliquid_data_in_t>();
            atg_stopliquid_data_in_t a90 = new atg_stopliquid_data_in_t();
            a90.uOilCanNo = 1;
            a90.uTestType = 0;
            liquidData.add(a90);
            List<atg_liquidreport_data_out_t> li09 = amg.stopLiquid(liquidData);
            //System.out.println("Java String out" + li09.size());
            for (int i = 0; i < li09.size(); i++) {
                //System.out.println("uOilCanNo " + li09.get(i).uOilCanNo);
                //System.out.println("uRevealStatus" + li09.get(i).uRevealStatus);
                //System.out.println("fRevealRate " + li09.get(i).fRevealRate);
                //System.out.println("strStartDate " + li09.get(i).strStartDate);
                //System.out.println("strStartTime " + li09.get(i).strStartTime);
                //System.out.println("fStartOilHeight " + li09.get(i).fStartOilHeight);
                //System.out.println("fStartWaterHeight" + li09.get(i).fStartWaterHeight);
                //System.out.println("fStartOilTemp " + li09.get(i).fStartOilTemp);
                //System.out.println("fStartOilTemp1 " + li09.get(i).fStartOilTemp1);
                //System.out.println("fStartOilTemp2 " + li09.get(i).fStartOilTemp2);
                //System.out.println("fStartOilTemp3 " + li09.get(i).fStartOilTemp3);
                //System.out.println("fStartOilTemp4 " + li09.get(i).fStartOilTemp4);
                //System.out.println("fStartOilTemp5 " + li09.get(i).fStartOilTemp5);
                //System.out.println("fStartOilCubage" + li09.get(i).fStartOilCubage);
                //System.out.println("fStartOilStandCubage" + li09.get(i).fStartOilStandCubage);
                //System.out.println("fStartEmptyCubage " + li09.get(i).fStartEmptyCubage);
                //System.out.println("fStartWaterBulk " + li09.get(i).fStartWaterBulk);
                //System.out.println("strEndDate " + li09.get(i).strEndDate);
                //System.out.println("strEndTime " + li09.get(i).strEndTime);
                //System.out.println("fEndOilHeight" + li09.get(i).fEndOilHeight);
                //System.out.println("fEndWaterHeight" + li09.get(i).fEndWaterHeight);
                //System.out.println("fEndOilTemp " + li09.get(i).fEndOilTemp);
                //System.out.println("fEndOilTemp1" + li09.get(i).fEndOilTemp1);
                //System.out.println("fEndOilTemp2 " + li09.get(i).fEndOilTemp2);
                //System.out.println("fEndOilTemp3 " + li09.get(i).fEndOilTemp3);
                //System.out.println("fEndOilTemp4 " + li09.get(i).fEndOilTemp4);
                //System.out.println("fEndOilTemp5 " + li09.get(i).fEndOilTemp5);
                //System.out.println("fEndOilCubage " + li09.get(i).fEndOilCubage);
                //System.out.println("fEndOilStandCubage" + li09.get(i).fEndOilStandCubage);
                //System.out.println("fEndEmptyCubage" + li09.get(i).fEndEmptyCubage);
                //System.out.println("fEndWaterBulk" + li09.get(i).fEndWaterBulk);
            }

            //3.6.3.13	静态液位异常测试报告 done 第二轮成功
            List<atg_liquidreport_data_in_t> liquidData3 = new ArrayList<atg_liquidreport_data_in_t>();
            atg_liquidreport_data_in_t a2 = new atg_liquidreport_data_in_t();
            a2.uOilCanNo = 1;
            a2.strDataTime = "20150101080812";
            a2.uTestType = 0;
            a2.uReqCount = 1;
            liquidData3.add(a2);
            List<atg_liquidreport_data_out_t> li2 = amg.liquidReport(liquidData3);
            //System.out.println("Java String out" + li2.size());
            for (int i = 0; i < li2.size(); i++) {
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
            }

            //3.6.3.14	设备基础信息 done    返回参数为OBJECT not LIST   联调成功 第二轮成功
            List<Integer> data09 = new ArrayList<Integer>();
            data09.add(1);
            atg_device_out_t ret111 = amg.getDeviceInfo(data09);
            //System.out.println("Java out ret.strDeviceModel:" + ret111.strDeviceModel);
            //System.out.println("Java out ret.strEquipCode:" + ret111.strEquipCode);
            //System.out.println("Java out ret.strSysVersion:" + ret111.strSysVersion);
            //System.out.println("Java out ret.strMakeDate:" + ret111.strMakeDate);
            //System.out.println("Java out ret.uRetCount:" + ret111.uRetCount);
            List<atg_device_data_out_t> ret2222 = ret111.pDeviceData;
            for (int j = 0; j < ret2222.size(); j++) {
                //System.out.println("Java out ret.uOilCanNo:" + ret2222.get(j).uOilCanNo);
                //System.out.println("Java out ret.strProbeNo:" + ret2222.get(j).strProbeNo);
                //System.out.println("Java out ret.strProbeModel:" + ret2222.get(j).strProbeModel);
            }
            //3.6.3.15	高升转换 done   atg_hightoliter_in_t的uCont要改为int型。否则C取不到值   联调成功 第二轮成功
            List<atg_capacity_data_in_t> capacityData = new ArrayList<atg_capacity_data_in_t>();
            List<atg_hightoliter_in_t> highToLiterData = new ArrayList<atg_hightoliter_in_t>();
            atg_hightoliter_in_t bbb = new atg_hightoliter_in_t();
            List<atg_capacitytable_data_in_t> d = new ArrayList<atg_capacitytable_data_in_t>();
            atg_capacity_data_in_t a12 = new atg_capacity_data_in_t();
            a12.uOilCanNO = 1;
            a12.uCapacitySize = 50;
            for (int i = 0; i < 50; i++) {
                atg_capacitytable_data_in_t c = new atg_capacitytable_data_in_t();
                c.fLiter = 2.1 + i * 10;
                c.uHigh = 3 + i;
                d.add(c);
                //System.out.println("Java out c.fLiter:" + c.fLiter);
                //System.out.println("Java out c.uHigh:" + c.uHigh);
            }
            a12.pCapacityTableData = d;
            capacityData.add(a12);
            bbb.uCount = 1;
            bbb.fTotalHeight = 40;
            bbb.fWaterHeight = 23.2;
            bbb.pCapacityData = capacityData;
            highToLiterData.add(bbb);
            List<atg_hightoliter_data_out_t> li90 = amg.HightOLiter(highToLiterData);
            //System.out.println("atg_hightoliter_data_out_t size:" + li90.size());
            for (int i = 0; i < li90.size(); i++) {
                //System.out.println(i + "atg_hightoliter_data_out_t uOilCanNo:" + li90.get(i).uOilCanNo);
                //System.out.println(i + "atg_hightoliter_data_out_t fOilCubage:" + li90.get(i).fOilCubage);
                //System.out.println(i + "atg_hightoliter_data_out_t fOilStandCubage:" + li90.get(i).fOilStandCubage);
                //System.out.println(i + "atg_hightoliter_data_out_t fEmptyCubage:" + li90.get(i).fEmptyCubage);
                //System.out.println(i + "atg_hightoliter_data_out_t fWaterBulk:" + li90.get(i).fWaterBulk);
            }

            //3.6.3.16	液位仪开关机记录 done 联调成功 第二轮成功
            List<atg_powerrecord_in_t> data90 = new ArrayList<atg_powerrecord_in_t>();
            atg_powerrecord_in_t a4 = new atg_powerrecord_in_t();
            a4.uReqCount = 5;
            a4.strDataTime = "20150914080812";
            data90.add(a4);
            List<atg_powerrecord_data_out_t> ret4 = amg.getPowerRecord(data90);
            for (int i = 0; i < ret4.size(); i++) {
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
            }
            try {
                //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~round:"+ (++count));
                Thread.sleep(60*5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //清除
        //amg.clear();

    }
}

