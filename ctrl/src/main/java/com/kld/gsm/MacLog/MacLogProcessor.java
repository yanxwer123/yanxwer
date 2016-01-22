package com.kld.gsm.MacLog;

import com.kld.gsm.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.Tailer;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 油机日志监控中心
 * <p/>
 * 启动一个线程进行文件内容监控，如果文件有新的内容，则追加到LinkedBlockingQueue
 * 启动另外一个线程进行文件内容消费
 * <p/>
 * Created by miaozy on 15/10/24.
 */
public class MacLogProcessor {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(MacLogProcessor.class);

    private static final int SLEEP = 500;//目前监控时间间隔为500毫秒，即0.5秒
    LinkedBlockingQueue<String> workItems = new LinkedBlockingQueue<String>();

    public void process() throws IOException, InterruptedException {

        String datestr = DateUtil.getDate(new Date(), "yyyyMMdd");
        String filename = "/smc20/muxtemp/" + datestr + ".log";
        logger.info("read:"+filename);
       // String filepath = Thread.currentThread().getContextClassLoader().getResource(filename).getPath();
        //监控文件内容变化
        File file = new File(filename);
        logger.info("maclog file absolute path:" + file.getAbsolutePath());
        if (!file.exists()) {
            //System.out.println("not find maclog file");
        }
        Tailer tailer = new Tailer(file, new MacLogTailerListenerAdapter(workItems), SLEEP, true);
        Thread thread = new Thread(tailer);
        logger.info("start file tailer");
        thread.start();

        //消费文件内容变化
        MacLogConsumer macLogConsumer = new MacLogConsumer(workItems);
        Thread consumerThread = new Thread(macLogConsumer);
        logger.info("start maclog consumer");
        consumerThread.start();

        //启动一个monitor线程,监控刚才的两个线程
        MacLogMonitor monitor = new MacLogMonitor(thread, consumerThread);
        logger.info("start maclog monitor thread");
        monitor.start();
    }

    ///test method  mast 111111
    public static void main(String[] args) throws IOException, InterruptedException {
        MacLogProcessor fileTailer = new MacLogProcessor();

    /*    String filepath = Thread.currentThread().getContextClassLoader().getResource("tmp.log").getPath();
        fileTailer.process();
        File file = new File(filepath);
        if (!file.exists())
            file.createNewFile();
            */

        String sourcefilePath = "G:\\20160110.log";
        File datafile = new File(sourcefilePath);

        FileReader in = new FileReader(datafile);
        LineNumberReader reader = new LineNumberReader(in);
        String line = null;
        MacLogConsumer macLogConsumer=new MacLogConsumer();
        while ((line = reader.readLine()) != null) {
            //
             Thread.sleep(1);
            macLogConsumer.ConsumerLine(line);
            //  //System.out.println(String.format("读取行数据:%s", line));
            //FileUtils.write(file, line + IOUtils.LINE_SEPARATOR, true);
        }
    }
}
