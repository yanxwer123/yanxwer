package com.kld.gsm.coord.server;


import com.kld.gsm.Socket.parser.MessageDecoder;
import com.kld.gsm.Socket.parser.MessageEncoder;
import com.kld.gsm.coord.server.handler.CloudServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:15
 * @Description:
 */
public class NettyServer {
    private static Logger log = LoggerFactory.getLogger(NettyServer.class);
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void start(String host, int port, final CloudServerHandler handler) {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        //一个服务引导对象
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                try {
                    //打印 netty 协议日志
                    //pipeline.addFirst(new LoggingHandler(LogLevel.INFO));
                    //设置超时   读 10 秒   写 5秒    双向 0秒 <上行或下行>
                     pipeline.addLast("idlehandler", new IdleStateHandler( 8,8, 0));
                    //设置 协议逻辑处理器
                    pipeline.addLast("decoder", new MessageDecoder());
                    pipeline.addLast("encoder", new MessageEncoder());
                    pipeline.addLast("handler", handler);
                    //第一步,在服务启动类中设置数据处理管道.
                } catch (Throwable th) {
                    log.error("【NettyServer】 Exception: ", th);
                }
            }
        });
        b.option(ChannelOption.SO_BACKLOG, 128);
        b.option(ChannelOption.SO_REUSEADDR, true);
        b.option(ChannelOption.TCP_NODELAY, true);
        b.childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture f = b.bind(host, port);
            f.sync();
            log.info("Start NettServer Success host:{} port:{}", host, port);
        } catch (InterruptedException ex) {
            log.error("Server Netty Server Error", ex);
        }
    }

    public void stop() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        log.warn("stop netty Server .......");
    }
}
