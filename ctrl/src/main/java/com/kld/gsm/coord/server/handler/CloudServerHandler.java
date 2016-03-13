package com.kld.gsm.coord.server.handler;

import com.kld.gsm.Socket.uitls.ByteUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:28
 * @Description: Netty主处理程序
 */
@ChannelHandler.Sharable
public class CloudServerHandler extends ChannelInboundHandlerAdapter {

    private ProtocolProcessor protocolProcessor;
    private static final Logger log = LoggerFactory.getLogger(CloudServerHandler.class);

    public CloudServerHandler(ProtocolProcessor protocolProcessor) {
        this.protocolProcessor = protocolProcessor;
    }

    //有连接时回调的函数
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      //log.info("connect context hashcode :" + ctx.hashCode());
        log.info("【CloudServerHandler】A client connected to me!");
        System.out.println("【CloudServerHandler】A client connected to me!\nCTX:"+ctx);
    }

    //有上传数据时回调的函数
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ////System.out.println("收到客户端发送过来的消息"+msg.toString());
        protocolProcessor.handlerProtocol(ctx, msg);
    }
    //断开
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("【CloudServerHandler】Client disconnection!");
        //关闭链路
        ctx.close();
        //删除未处理的字节包
        ByteUtils.getInstance().packageMapper.remove(ctx);
        //删除会话
        protocolProcessor.lostConnection(ctx);
    }
    //用户事件触发 会话时间
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("强制退出");
        //关闭链路
        ctx.close();
        //删除未处理的字节包
        ByteUtils.getInstance().packageMapper.remove(ctx);
        //删除会话
        protocolProcessor.lostConnection(ctx);


    }
}