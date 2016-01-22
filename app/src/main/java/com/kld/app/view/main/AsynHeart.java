package com.kld.app.view.main;

import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-25 21:05
 * @Description:
 */
public class AsynHeart {
    @Async
    public static void sendIdel(){
        GasMsg gasMsg;
         boolean flag = true;
         while (true) {
            try {
                gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10000.toString());
                System.out.println("send heart");
                Main.CC.writeAndFlush(gasMsg);
                Thread.sleep(4000);
                if(flag){
                    gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10001.toString());
                    System.out.println("[Asyn01]:"+gasMsg.getPid());
                    System.out.println("[Asyn01]:"+gasMsg.toString());
                    System.out.println("[AsynHeart]请求权限");
                    Main.CC.writeAndFlush(gasMsg);
                    flag=false;
                }
            } catch (InterruptedException e) {
                //System.out.println("---------------------");
                e.printStackTrace();
            }
        }
    }
}
