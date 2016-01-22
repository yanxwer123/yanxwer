package com.kld.app.view.main;

import com.kld.app.socket.ob.Watcher;
import com.kld.gsm.Socket.protocol.GasMsg;
/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/14 22:45
 * @Description:
 */
public class ObNotify implements Watcher {


    @Override
    public void update(GasMsg gasMessage) {
        //System.out.println("消息通知:"+gasMessage);
    }
}
