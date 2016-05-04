package com.kld.app.socket.client.handler;

import com.kld.app.socket.client.TcpClient;
import com.kld.app.util.Constant;
import com.kld.app.view.main.Main;
import com.kld.gsm.Socket.uitls.ByteUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger log = LoggerFactory.getLogger(TcpClientHandler.class);

    private ProtocolProcessor protocolProcessor;
    static int i = 0;
    static boolean flag = true;

    public TcpClientHandler(ProtocolProcessor protocolProcessor) {
        this.protocolProcessor = protocolProcessor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        protocolProcessor.handlerProtocol(ctx, msg);
    }

    //断开
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("disconnect");
        ctx.close();
        ByteUtils.getInstance().packageMapper.remove(ctx);

        i = 0;
        flag = true;
        while (flag) {
            System.out.println("----");
            try {

                Channel channel = TcpClient.getInstance().getChannel(Constant.IP, Constant.PORT);

                if (channel != null) {
                    log.info("[Netty].reLink ok  ");
                    System.out.println("[Netty].reLink ok  ");
                    Main.CC = channel;
                    flag = false;
                    break;
                }
                log.info("[Netty]Wait five seconds reLink......");
                System.out.println("[Netty]Wait five seconds reLink......");

                Thread.sleep(5000);
                log.info("[Netty]ReLink[" + i + "]....");
                System.out.println("[Netty]ReLink[" + i + "]....");
                //如果未成功连接，则20秒左右弹出一次提示
                if (i == 8) {
                    JOptionPane.showMessageDialog(null, "与后台服务未能成功建立连接,正在尝试重连...", "错误提示", JOptionPane.WARNING_MESSAGE);
                    //i = 0;
                    // System.exit(0);
                }
                //尝试60秒后，未能成功连接，系统退出
                if (i == 16) {
                    JOptionPane.showMessageDialog(null, "与后台服务未能成功建立连接,请尝试重启...", "错误提示", JOptionPane.ERROR_MESSAGE);
                    //i = 0;
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                log.info("重连出错.......");
                // System.exit(0);
            }
            i++;
        }
    }


    //用户事件触发 会话时间
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("强制断开");
        log.info("强制断开");
        //关闭链路
        ctx.close();
        ByteUtils.getInstance().packageMapper.remove(ctx);
    }
}
