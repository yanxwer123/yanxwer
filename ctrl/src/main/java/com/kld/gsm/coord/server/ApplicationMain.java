package com.kld.gsm.coord.server;


import com.kld.gsm.ATG.dao.SysManageIquidInstrumentDao;
import com.kld.gsm.ATG.domain.SysManageIquidInstrument;
import com.kld.gsm.ATG.domain.oss_monitor_Pump;
import com.kld.gsm.ATG.service.MonitorService;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_init_in_t;
import com.kld.gsm.MacLog.GunStatusEnum;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.MacLog.MacLogProcessor;
import com.kld.gsm.MacLog.util.ApplicationRunSingle;
import com.kld.gsm.MacLog.util.EhCacheHelper;
import com.kld.gsm.Systemv.MQCanInfo;
import com.kld.gsm.Systemv.MQDay;
import com.kld.gsm.Systemv.MQShift;
import com.kld.gsm.Systemv.MQVouch;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.server.handler.CloudServerHandler;
import com.kld.gsm.coord.server.handler.ProtocolProcessor;
import com.kld.gsm.coord.timertask.TimeTask;
import org.slf4j.Logger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:27
 * @Description: master  com.kld.gsm.syntocenter.socket
 */
public class ApplicationMain {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) throws Exception {

        // 保证程序只有一个实例在运行.
        ApplicationRunSingle.makeSingle("ctrl");
        logger.warn("ctrl.main方法启动................");

        final NettyServer nettyServer = new NettyServer();

        ProtocolProcessor protocolProcessor = ProtocolProcessor.getInstance();
        CloudServerHandler handler = new CloudServerHandler(protocolProcessor);

        //   region com.kld.gsm.syntocenter.socket
        List<String> list = getLocalIPList();
        logger.info("ip.size:"+list.size());
        for (String ip : list) {
            nettyServer.start(ip, 8992, handler);
            logger.warn("开始监控 --------server " + ip+":8992" );
        }
        //endregion

        logger.warn("开始加载液位仪~~~~~~~~~~~~~~~~start");
       boolean atgresult= init();
        if(!atgresult)
        {
            logger.error("液位仪初始化失败.");
        }
        logger.warn("开始加载液位仪~~~~~~~~~~~~~~~~end");
        Calendar calendar = Calendar.getInstance();
        logger.warn("启动定时任务开始------------------------" + calendar.getTime());
        TimeTask.start();
        logger.warn("启动定时任务完成------------------------" + calendar.getTime());
        Context.getInstance();
        logger.warn("加载持久化数据");
        try {
            MonitorService monitorService=Context.getInstance().getBean(MonitorService.class);
            List<oss_monitor_Pump> monitor_pumps=monitorService.selectAll();
            if (monitor_pumps!=null&&monitor_pumps.size()>0){
                for (oss_monitor_Pump item:monitor_pumps){
                    //logger.info((byte) item.getGunno().intValue() + "," + item.getPump());
                    MacLogInfo info=new MacLogInfo();
                    info.setGunNum((byte) item.getGunno().intValue());
                    info.setPumpReadout(item.getPump());
                    info.setGunStatus(GunStatusEnum.卡插入);
                    EhCacheHelper.updteCache(info);
                }
            }
        }catch (Exception e){
            logger.error("加载持久化数据异常"+e.getMessage());
        }

        //region 启动消息队列监控线程
        logger.warn("开始启动消息队列监控....................");
        MQVouch sysv = new MQVouch();
        Thread consumerThread = new Thread(sysv);
        if (!consumerThread.isAlive()) {
            consumerThread.start();
        }
        MQShift shift = new MQShift();
        Thread shiftThread = new Thread(shift);
        if (!shiftThread.isAlive()) {
            shiftThread.start();
        }
        MQDay day = new MQDay();
        Thread dayThread = new Thread(day);
        if (!dayThread.isAlive()) {
            dayThread.start();
        }
        MQCanInfo caninfo = new MQCanInfo();
        Thread caninfoThread = new Thread(caninfo);
        if (!caninfoThread.isAlive()) {
            caninfoThread.start();
        }
        logger.warn("消息队列监控启动完成...................");
        //endregion
        //region maclog
        logger.warn("start maclog--------------------------");
        MacLogProcessor fileTailer = new MacLogProcessor();
        fileTailer.process();

        //endregion



        //region钩子，优雅的关闭
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                //液位仪清理
                try {
                    logger.info("结束线程");
                    TimeTask.stop();
                    logger.info("调用清理液位仪方法...");
                    ATGManager.clear();
                }catch (Exception e){
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                nettyServer.stop();
            }
        }));
        //endregion
    }

    //region初始化液位仪

    public static Boolean init() {
        try {
            SysManageIquidInstrumentDao sysManageIquidInstrumentDao = Context.getInstance().getBean(SysManageIquidInstrumentDao.class);
            ////System.out.println("sysManageIquidInstrumentDao:{" + sysManageIquidInstrumentDao + "}");
            SysManageIquidInstrument sysManageIquidInstrument = sysManageIquidInstrumentDao.selectLast();
            if (sysManageIquidInstrument == null) {
                logger.info("无法初始化液位仪........");
                return false;
            } else {
                logger.info("=================液位仪参数:=============\n[" + sysManageIquidInstrument.toString() + "]");
                atg_init_in_t init_in_t = new atg_init_in_t();
                init_in_t.strDeviceType = sysManageIquidInstrument.getMactype();//String.valueOf("WB_");//设备型号
                //网口 1 串口 0
                if("网口".equals(sysManageIquidInstrument.getCommtype())) {
                    init_in_t.uConnMode = 1;//1;// Integer.parseInt(sysManageIquidInstrument.getCommtype());//通讯模式
                }
                else
                {
                    init_in_t.uConnMode = 0;
                }
                init_in_t.strSerialAddress = sysManageIquidInstrument.getSerialport();//串口地址
                init_in_t.strSerialBaudRate = sysManageIquidInstrument.getBaudrate1();//"9600";//波特率
                init_in_t.strSerialStopBit = sysManageIquidInstrument.getStopsite();//"1";//停止位
                init_in_t.strSerialCheckBit = sysManageIquidInstrument.getChecksite().substring(0,1);//"n";//检验位
                init_in_t.strSerialDataBit = sysManageIquidInstrument.getDatasite();//"8";//数据位
//            //System.out.println("获取到的IP｛"+sysManageIquidInstrument.getIpport()+"｝");
//            //System.out.println("获取到的port｛"+ sysManageIquidInstrument.getIpport()+"｝");
                init_in_t.strIPAddress = sysManageIquidInstrument.getIpaddress();//"192.168.0.211";//ip
                init_in_t.strIPPort = sysManageIquidInstrument.getIpport();//"5656";//端口
                init_in_t.strLogPath = "/smc20/gsm/logs/ATG/";
//            init_in_t.strLogPath ="ywy.log";
                int i = ATGManager.init(init_in_t);
                if (i == 0) {

                    logger.error("ATG  Init SUCCESS .....");
                    return true;
                } else {

                    logger.error("ATG  Init FAIL .....");
                    return false;
                }
            }
        }catch (Exception ex)
        {
            logger.error("液位仪初始化失败."+ex.getMessage());
            return false;
        }
    }

    //endregion
    //region 获取本机IP
    public static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        if (!ipList.contains(ip)) {
                            ipList.add(ip);
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }
    //endregion

}

