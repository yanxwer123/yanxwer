package com.kld.gsm.coord.server.handler;

import io.netty.channel.ChannelHandlerContext;


/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:28
 * @Description: 会话 ：保存Socket链路对象
 */
public class ConnectionSession {

    private static ConnectionSession instance = null;

    public static ConnectionSession getInstance() {
        if (instance == null) {
            instance = new ConnectionSession();
        }
        return instance;
    }

    private String id;
    private ChannelHandlerContext ctx;

    public ConnectionSession() {
    }

    public ConnectionSession(String id, ChannelHandlerContext ctx) {
        this.id = id;
        this.ctx = ctx;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
}
