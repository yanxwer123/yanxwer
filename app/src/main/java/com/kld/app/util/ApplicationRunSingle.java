package com.kld.app.util;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/1/22 12:40
 * @Description:
 */
public class ApplicationRunSingle {
    static Logger logger = Logger.getLogger(ApplicationRunSingle.class);

    /**
     * 在应用程序的main方法里调用此函数，保证程序只有一个实例在运行
     * @param singleId
     */
    public static void makeSingle(String singleId){
        RandomAccessFile raf = null;
        FileChannel channel = null;
        FileLock lock = null;
        try {
            File file = new File(singleId+".lck");
            file.createNewFile();

            raf = new RandomAccessFile(file,"rw");
            channel  = raf.getChannel();
            lock = channel.tryLock();

            if(lock==null){
                //如果没有得到锁，则程序退出
                //不用手动释放锁和关闭流，当程序退出时会自动被关闭。
                JOptionPane.showMessageDialog(null, "液位仪应用系统已启动，不能重复启动！", "系统提示", JOptionPane.WARNING_MESSAGE);
                logger.error("An instance of app is running...");
                throw new Error("An instance of app is running...");
            }
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 测试程序
     * @param args
     * @throws Exception
     */
//    public static void main(String[] args) throws Exception {
//        ApplicationRunSingle.makeSingle("ctrl"); // 保证程序只有一个实例在运行.
//
//        // 测试: 模拟一个程序正在运行5秒
//        System.out.println("Start");
//        System.out.println("Waiting 10 seconds.");
//
//        for (int i = 0; i < 20; ++i) {
//            Thread.sleep(2000);
//            System.out.println((i + 1) + "......");
//        }
//
//        System.out.println("End");
//    }
}










