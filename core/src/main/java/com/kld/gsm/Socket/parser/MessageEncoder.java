package com.kld.gsm.Socket.parser;

import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.uitls.NumberUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:38
 * @Description: 编码
 */
public class MessageEncoder extends MessageToByteEncoder<GasMsg> {

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

    }
}
