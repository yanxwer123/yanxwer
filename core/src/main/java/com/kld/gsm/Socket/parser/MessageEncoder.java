package com.kld.gsm.Socket.parser;

import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.uitls.NumberUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:38
 * @Description: 编码
 */
public class MessageEncoder extends MessageToByteEncoder<GasMsg> {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(MessageEncoder.class);

    public void encode(ChannelHandlerContext chc, GasMsg message, ByteBuf out) throws UnsupportedEncodingException {
        out.writeBytes(message.getsCode().getBytes("UTF-8"));
        out.writeBytes(message.getPidLenth().getBytes("UTF-8"));
        out.writeBytes(message.getPid().getBytes("UTF-8"));

        final String messageBody = message.getMessage();
        int dataLength = 0;
        if (messageBody != null && !"".equals(messageBody)) {
            dataLength = messageBody.getBytes("UTF-8").length;
        }
        String dataLengthStr = NumberUtils.numberTo4Leng(dataLength);
        out.writeBytes(dataLengthStr.getBytes("UTF-8"));
        out.writeBytes(messageBody.getBytes("UTF-8"));
       // logger.info(new Date().toLocaleString() + "Encoder[" + chc + "]" );
    }
}
