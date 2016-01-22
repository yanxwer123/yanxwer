package com.kld.app.socket.client.handler;

import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.gsm.Socket.protocol.GasMsg;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/15 14:41
 * @Description: 协议逻辑处理
 */
public class ProtocolProcessor {

    private ProtocolProcessor() {

    }

    private static ProtocolProcessor instance = null;

    public static ProtocolProcessor getInstance() {
        if (instance == null) {
            instance = new ProtocolProcessor();
        }
        return instance;
    }


    //保存所有链接 的map  key  是  com.kld.gsm.syntocenter.socket 链路    value 是自己封装的session 对象
    public Map<ChannelHandlerContext, ConnectionSession> appMapper = new HashMap<ChannelHandlerContext, ConnectionSession>();
    public Map<ChannelHandlerContext, ConnectionSession> centerMapper = new HashMap<ChannelHandlerContext, ConnectionSession>();



    public void handlerProtocol(ChannelHandlerContext ctx, Object message) {
        GasMsg gasMsg = (GasMsg) message;

        if(gasMsg.getPid().startsWith("A")){
            ConcreteWatched.getInstance().notifyWechers(gasMsg, "A");
        }
        if(gasMsg.getPid().startsWith("C")){
            ConcreteWatched.getInstance().notifyWechers(gasMsg, "A");
        }

    }

    /**
     * @param gasStationNo 索引
     * @param mapper       查找的mapper对象
     * @return
     */
    private ConnectionSession searchDitCtxByGasStationNo(String gasStationNo, Map mapper) {
        Iterator<Map.Entry<ChannelHandlerContext, ConnectionSession>> iterator = mapper.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ChannelHandlerContext, ConnectionSession> entry = iterator.next();
            if (entry.getValue().getId().equals(gasStationNo)) {
                return entry.getValue();
            }
        }
        return null;
    }
}


class ConnectionSession {
    private String id;
    private ChannelHandlerContext ctx;

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
