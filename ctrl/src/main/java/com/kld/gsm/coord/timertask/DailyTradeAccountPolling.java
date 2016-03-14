package com.kld.gsm.coord.timertask;


import com.kld.gsm.ATG.domain.DailyTradeAccount;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.coord.Constants;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.servcie.IDailyTradeAccountService;
import com.kld.gsm.coord.server.handler.ConnectionSession;
import com.kld.gsm.coord.server.handler.ProtocolProcessor;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DailyTradeAccountPolling extends Thread{
    Logger logger = Logger.getLogger(DailyTradeAccountPolling.class);

    @Override
    public void run() {
        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid);
        while(true) {
            try {
                sleep(TimeTaskPar.get("tzjysjsjjg")*1000);
            } catch (InterruptedException e) {
                logger.error("sleep:"+e);
                e.printStackTrace();
            }
            try {

                //  ////System.out.println("Polling...............................................");
                logger.error(" get in DailyTradeAccountPolling 交易流水");
                IDailyTradeAccountService dailyTradeAccountService = Context.getInstance().getBean(IDailyTradeAccountService.class);


                //查询 未通知的交易数据
                List<DailyTradeAccount> list = dailyTradeAccountService.findNotRecieved();
                ////System.out.println("list.size():"+list.size());
                for (DailyTradeAccount dailyTradeAccount : list) {
                    List listApp = new ArrayList();
                    listApp.add(dailyTradeAccount);
                    GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS("all", listApp, Constants.PID_Code.A15_10003.toString());
                    Map map = ProtocolProcessor.getInstance().appMapper;
                    if (map.size() > 0) {
                        ////System.out.println("map:"+map.toString());
                    }
                    Iterator<Map.Entry<ChannelHandlerContext, ConnectionSession>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<ChannelHandlerContext, ConnectionSession> entry = iterator.next();
                        entry.getValue().getCtx().writeAndFlush(gasMsg);
                    }
                }
            } catch (Exception ex) {
                logger.error("DailyTradeAccountPolling :" + ex);
            }

        }
    }
}
