package com.kld.gsm.MacLog;

import com.kld.gsm.util.DateUtil;
import org.apache.commons.io.input.Tailer;
import org.apache.log4j.MDC;
import org.slf4j.Logger;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 监控 监控油机日志的两个线程
 * Created by miaozy on 15/11/15.
 */
public class MacLogMonitor  extends  Thread {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(MacLogMonitor.class);

    private  Thread  tailerThread;
    private  Thread  consumerThread;

    private Date monitorDate;

    /**
     * 允许重新启动的次数
     */
    private  final int  restartLimit = 5;
    private  int  restartTailerCount = 0;
    private  int  restartConsumerCount = 0;

    public  MacLogMonitor(Thread  tailerThread,Thread  consumerThread){
        this.tailerThread  = tailerThread;
        this.consumerThread = consumerThread;
        monitorDate =  new Date();
    }

    public void run() {
        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid);
        while (true) {
            monitor();
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 监控主要逻辑,如果线程已经死掉,则重启
     *
     */
    private void monitor() {
        if(tailerThread.getState().equals(State.TERMINATED) && restartTailerCount <= restartLimit){
            logger.error("监控文件Tailer线程已经终止,现在开始重启线程!");
             tailerThread.start();
             restartTailerCount ++;
        }
        if(consumerThread.getState().equals(State.TERMINATED) && restartConsumerCount <= restartLimit){
            logger.error("文件内容消费线程已经终止,现在开始重启线程!");
            consumerThread.start();
            restartConsumerCount ++;
        }

        //如果如果已经不是第一天了,则马上监控一个新的文件
        if(!isSameDay(monitorDate,new Date())){

            //// TODO: 15/11/15   则处代码与Processor处重复,需要处理
            //监控一个新的
            String datestr= DateUtil.getDate(new Date(),"yyyyMMdd");
            String filepath = "/smc20/muxtemp/"+datestr+".log";
            LinkedBlockingQueue<String> workItems = new LinkedBlockingQueue<String>();

            //监控文件内容变化
            File file = new File(filepath);
            //System.out.println("maclog file absolute path:"+file.getAbsolutePath());
            if(!file.exists())
            {
                //System.out.println("not find maclog file");
            }
            Tailer tailer = new Tailer(file, new MacLogTailerListenerAdapter(workItems), 500, true);
            tailerThread.interrupt();
            tailerThread = new Thread(tailer);
            //System.out.println("start file tailer for another day:" + datestr);
            tailerThread.start();


            //消费文件内容变化
            MacLogConsumer  macLogConsumer  = new MacLogConsumer(workItems);
            consumerThread.interrupt();
            consumerThread = new Thread(macLogConsumer);
            //System.out.println("start maclog consumer for another day:" + datestr);
            consumerThread.start();

            monitorDate = new Date();
        }

     }

    public boolean isSameDay(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }
    }
}
