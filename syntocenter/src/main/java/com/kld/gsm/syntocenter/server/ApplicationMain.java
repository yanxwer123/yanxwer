package com.kld.gsm.syntocenter.server;

import com.kld.gsm.syntocenter.socket.ob.Watcher;

 import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.syntocenter.service.synPurchase;
import com.kld.gsm.syntocenter.socket.client.TcpClient;
import com.kld.gsm.syntocenter.socket.ob.ConcreteWatched;
import com.kld.gsm.syntocenter.socket.ob.Watched;
import com.kld.gsm.syntocenter.socket.ob.Watcher;
 import com.kld.gsm.syntocenter.springContext.springFactory;

import com.kld.gsm.syntocenter.timetask.TimeTask;
import com.kld.gsm.syntocenter.util.ApplicationRunSingle;
import com.kld.gsm.syntocenter.util.action;
import com.kld.gsm.util.DateUtil;
import com.sun.corba.se.impl.orbutil.HexOutputStream;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/*
Created BY ny
Created Date 2015/11/18
*/
public class ApplicationMain  implements Watcher {
    //nodecode
    public static String NodeNo;
    //中心服务器地址
    public static String Host;

    public static Date UpLoadDate;

    public static boolean IsOpenrt=true;

    public static Map<String,Integer> oilcanmap;
    public static Map<String,String> canversion;
    public static String ctrladdr;
    public static String ctrlport;
    public static String gunaddr;
    public static String canaddr;
    public static Channel CC =null ;
    public static  Integer count=0;
    public static Watched watch = ConcreteWatched.getInstance();
    public static String sign = "";
    public static Watcher watcher  =new ApplicationMain();
    public static   action action = new action();
    private static final Logger LOG = Logger.getLogger("syntocenter");
    public static void main(String[] args) throws Exception{
        ApplicationRunSingle.makeSingle("syntocenter");
        action ac = new action();
        ctrladdr=ac.getCtrladdr();
        ctrlport=ac.getctrlport();
        gunaddr=ac.getUri("resource.services.TI.addQSSZT");
        canaddr=ac.getUri("resource.services.TI.addGSSZT");
        Host= ac.getHost();

        try {
            IsOpenrt = ac.getRTopen();
        } catch (Exception e) {
            LOG.error("get isopenrt 0" + e.getMessage());
        }

        //region 随机生成上传时间
        String dstring= DateUtil.getDate();
        //System.out.println(dstring);
        dstring=dstring+" 00:15:00";
        Date date= new Date();
        date.setHours(0);
        date.setMinutes(15);
        date.setSeconds(0);

        int max=82800000;
        int min=1;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        //System.out.println(s);
        UpLoadDate=new Date(date.getTime()+s);
       // UpLoadDate=new Date(date.getTime()+min);
        LOG.info("upload times:" + DateUtil.getDate(UpLoadDate, "yyyy-MM-dd HH:mm:ss"));

        ////System.out.println(new Date(date.getTime()+max));

        //endregion

        //region 自动生成signid
        // 创建 GUID 对象
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        sign = uuid.toString();
        //endregion

        //region link ctrl
            CC = reLink();
            System.out.println("Main-------------");
            System.out.println("cc:" + CC);
            System.out.println("watch:" + watch);


            //System.out.println("send sck");
            watch.addWetcher("A", watcher);
            GasMsg idel = ResultUtils.getInstance().sendSUCCESS(sign, new ArrayList(), Constants.PID_Code.A15_10000.toString());
            CC.writeAndFlush(idel);
        //endregion

        synPurchase ss=springFactory.getInstance().getBean(synPurchase.class);
        //启动定时上传
        LOG.info("启动定时上传开始");
        TimeTask timeTask = new TimeTask();
        timeTask.start();
        LOG.info("启动定时上传结束");
    }
    public  static Channel reLink() {
        // action action = new action();

         int i = 0;
        boolean flag = true;
        while (flag) {
            try {
                 CC = TcpClient.getInstance().getChannel(ctrladdr, Integer.valueOf(action.getctrlport()));
                 if (CC != null) {
                    System.out.println("reLink ok  ");
                    flag = false;
                    return CC;
                }
                 System.out.println("Wait Three seconds reLink......");

                Thread.sleep(3000);
                System.out.println("ReLink["+i+"]....");
                /*if(i==3){
                    JOptionPane.showMessageDialog(null, "与主调度断开链接,尝试重连。", "错误提示", JOptionPane.ERROR_MESSAGE);
                    i = 0;
                    // System.exit(0);
                }*/
             } catch (InterruptedException e) {
                System.out.println("重连出错.......");
                // System.exit(0);
            }
            i++;

        }
        return null;
    }
     //endregion

      @Override
    public void update(GasMsg gasMsg) {
        //System.out.println("get message:"+gasMsg.getMessage());
    }
}
