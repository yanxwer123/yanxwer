package com.kld.gsm.syntocenter.socket.client.handler;

import com.kld.gsm.syntocenter.server.ApplicationMain;
import com.kld.gsm.syntocenter.socket.client.TcpClient;
import com.kld.gsm.syntocenter.util.action;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/14 17:22
 * @Description:
 */
@ChannelHandler.Sharable
public class TcpClientHandler extends SimpleChannelInboundHandler {
    private ProtocolProcessor protocolProcessor;
    private static final Logger LOG = Logger.getLogger("TcpClientHandler");
    public TcpClientHandler(ProtocolProcessor protocolProcessor) {
        this.protocolProcessor = protocolProcessor;
    }
    static int i = 0;
    static boolean flag = true;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        protocolProcessor.handlerProtocol(ctx, msg);
    }

    //断开
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("disconnect");
        action action = new action();
        int i = 0;
        boolean flag = true;
        while (flag) {
            try {
                Channel channel = TcpClient.getInstance().getChannel(action.getCtrladdr(), Integer.valueOf(action.getctrlport()));

                if (channel != null) {
                    System.out.println("reLink ok  ");
                    ApplicationMain.CC = channel;
                    flag = false;
                    break;
                }
                LOG.info("Wait five seconds reLink......");

                Thread.sleep(5000);
                LOG.info("ReLink[" + i + "]....");
                if (i == 3) {
                    System.out.println("Stop");
                    //JOptionPane.showMessageDialog(null, "与主调度断开链接,尝试重连。", "错误提示", JOptionPane.ERROR_MESSAGE);
                    i = 0;
                    // System.exit(0);
                }
            } catch (InterruptedException e) {
                LOG.error("重连出错.......");
                // System.exit(0);
            }
            i++;
        }
    }


    //用户事件触发 会话时间
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        LOG.info("强制断开");
        //关闭链路
        ctx.close();

    }
}
