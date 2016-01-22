package com.kld.app.socket.client;

import com.kld.app.socket.client.handler.ProtocolProcessor;
import com.kld.app.socket.client.handler.TcpClientHandler;
import com.kld.gsm.Socket.parser.MessageDecoder;
import com.kld.gsm.Socket.parser.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/14 17:21
 * @Description:
 */
public class TcpClient {


    private TcpClient() {

    }

    private static TcpClient instance = null;

    public static TcpClient getInstance() {
        if (instance == null) {
            instance = new TcpClient();
        }
        return instance;
    }

    public static Bootstrap bootstrap = getBootstrap();
    public static ProtocolProcessor protocolProcessor = ProtocolProcessor.getInstance();

    /**
     * 初始化Bootstrap
     *
     * @return
     */
    public static final Bootstrap getBootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                //  pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                //  pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                //设置超时   读 10 秒   写 5秒    双向 0秒 <上行或下行>
                // pipeline.addLast("idlehandler", new IdleStateHandler(1,0,0));

                pipeline.addLast("decoder", new MessageDecoder());
                pipeline.addLast("encoder", new MessageEncoder());
                pipeline.addLast("handler", new TcpClientHandler(protocolProcessor));
            }
        });
        b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    public static final Channel getChannel(String host, int port) {
        Channel channel = null;
        try {
            channel = bootstrap.connect(host, port).sync().channel();
            System.out.println("Link Netty Server SUCCESS host: "+host+"  port: "+port);

        } catch (Exception e) {
            System.err.println("Link Netty Server FAll host: "+host+"  port: "+port);
           // e.printStackTrace();
            return null;
        }
        return channel;
    }
}
