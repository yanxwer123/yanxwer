package com.kld.app.view.main;

import com.kld.gsm.Socket.Constants;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/14 22:45
 * @Description:
 */
public class App implements Watcher {
    public static void main(String[] args) throws Exception {
        Main.watch.addWetcher("A", new App());
        GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10002.toString());
        Main.CC.writeAndFlush(gasMsg);

        Watched girl = ConcreteWatched.getInstance();

        Watcher watcher = new App();
        girl.addWetcher("A", watcher);

        // Channel cc = Main.reLink();//TcpClient.getInstance().getChannel("192.168.0.103", 8992);
        if (Main.CC == null) {
            System.out.println("Link Netty Server FAll");
        } else {
            gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10002.toString());
            //   System.out.println(gasMsg);
            Main.CC.writeAndFlush(gasMsg);
        }

    }

    @Override
    public void update(GasMsg gasMessage) {
        try {

//            System.out.println("能返回么？" + gasMessage.getMessage());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
