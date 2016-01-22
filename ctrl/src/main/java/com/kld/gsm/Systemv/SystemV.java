package com.kld.gsm.Systemv;

import lajp.MsgQ;
import org.slf4j.Logger;

/**
 * Created by luyan on 15/10/27.
 */

public class SystemV {
    private static Logger logger= org.slf4j.LoggerFactory.getLogger(SystemV.class);

    static
    {
        //load lib
        logger.warn("java.library.path:"+System.getProperty("java.library.path"));
        System.loadLibrary("lajpmsgq");
    }
    static int mqkeyid_vouch=107;
    public  static byte[] readMsg(int mqtype)
    {
        logger.warn("start read systemV in type " + mqtype + " \n begin>>>>");
        int systemvkey = IPCKey.getIntj(mqkeyid_vouch);
        //System.out.println("get sysv key:"+systemvkey);

        //发送消息队列
        int msqid = MsgQ.msgget(systemvkey);
        //System.out.println("msqid   :"+msqid);
        byte[] msg = new byte[100];
        try {
           int r= MsgQ.msgrcv(msqid, msg, msg.length, mqtype);
            logger.info("msgrcv return value:"+r);
        }catch (Exception e){
            ////System.out.println("1234567890"+e);
            logger.info ("SystemV error"+e);
        }
          logger.info("============mqtype:" + mqtype + "=============msqid:" + msqid + "===begin==");
        StringBuilder sb=new StringBuilder();
        for(int i =0;i<msg.length;i++){
            sb.append("" + msg[i]);
        }
        logger.info(sb.toString());
        logger.info("============mqtype:" + mqtype + "=============msqid:" + msqid + "====end=");


        return msg;
    }
}
