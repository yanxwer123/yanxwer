package com.kld.gsm.MacLog.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/1/22 12:40
 * @Description:
 */
public class ApplicationRunSingle {

    /**
     * 在应用程序的main方法里调用此函数，保证程序只有一个实例在运行
     * @param singleId
     */
    public static void makeSingle(String singleId){
        RandomAccessFile raf = null;
        FileChannel channel = null;
        FileLock lock = null;
        Resource base=new ClassPathResource("/config/tempdir.properties");
        Properties tempdir = null;
        try {
            tempdir= PropertiesLoaderUtils.loadProperties(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File file = new File(tempdir.getProperty("resource.temp.tempdir")+singleId+".tmp");
            file.deleteOnExit();
            file.createNewFile();

            raf = new RandomAccessFile(file,"rw");
            channel  = raf.getChannel();
            lock = channel.tryLock();

            if(lock==null){
                //如果没有得到锁，则程序退出
                //不用手动释放锁和关闭流，当程序退出时会自动被关闭。
                throw new Error("An instance of ctrl is running...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试程序
     * @param args
     * @throws Exception
     */
    /*public static void main(String[] args) throws Exception {
        ApplicationRunSingle.makeSingle("ctrl"); // 保证程序只有一个实例在运行.

        // 测试: 模拟一个程序正在运行5秒
        System.out.println("Start");
        System.out.println("Waiting 10 seconds.");

        for (int i = 0; i < 10; ++i) {
            Thread.sleep(1000);
            System.out.println((i + 1) + "......");
        }

        System.out.println("End");
    }*/
}










