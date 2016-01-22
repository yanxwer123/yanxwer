package com.kld.gsm.syntocenter.socket.ob;


import com.kld.gsm.Socket.protocol.GasMsg;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/14 22:42
 * @Description:
 */
public interface Watcher {
    void update(GasMsg gasMsg);
}
