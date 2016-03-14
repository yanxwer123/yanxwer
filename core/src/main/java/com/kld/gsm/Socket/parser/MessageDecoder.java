package com.kld.gsm.Socket.parser;


import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.uitls.ByteUtils;
import com.kld.gsm.Socket.uitls.DecoderPackage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:38
 * @Description: 解码
 */
public class MessageDecoder extends ByteArrayDecoder {
    private static final Logger log = LoggerFactory.getLogger(MessageDecoder.class);


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        //使用工具类转换成String字符串


        String msgStr = ByteUtils.getInstance().byteBufferToString(ctx, msg);
        // //System.out.println("1111111111111111111111-----------"+msgStr);
        //  String result = AppendPackage.getInstance().getPackage(ctx, msgStr);
        //result = 0 拼接不成功
        if (msgStr.equals("0")) {
            return;//直接返回不做处理
        }

        GasMsg Message = DecoderPackage.getP(msgStr);
        //out.add --- 添加到这个List里面后，netty会主动将这个数据往下传递
        out.add(Message);
       // log.info(new Date().toLocaleString() + "Decoder[" + ctx + "]");

        //TODO 再写个方法遍历  当前链路中所有的对象，是不是还存在完整的包 然后 在添加到list中一并处理
        //TODO 循环的请求  只要有一个完整的包就跳出当前循环  然后再继续
        while (true) {
            // String str = AppendPackage.getInstance().ergodic(ctx);
            String str  = ByteUtils.getInstance().ergodic(ctx);
           // //System.out.println();
            if (str.equals("0")) {
                //   log.info("无完整包 【break loop】");
                break;//跳出整个循环

            } else {
                GasMsg gasMessage = DecoderPackage.getP(str);
                //  log.info("轮询 出完整的包 set Message：" + gasMessage);
//                log.info(new Date().toLocaleString() + "Decoder[" + ctx + "]");
                out.add(gasMessage);
            }

        }
    }


}
