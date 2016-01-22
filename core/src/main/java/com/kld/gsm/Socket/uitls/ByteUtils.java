package com.kld.gsm.Socket.uitls;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/24 12:17
 * @Description: 拼包 解包
 */
public class ByteUtils {
    private static final Logger log = LoggerFactory.getLogger(ByteUtils.class);

    private ByteUtils() {

    }

    private static ByteUtils instance = null;

    public static ByteUtils getInstance() {
        if (instance == null) {
            instance = new ByteUtils();
        }
        return instance;
    }


    //key  链路    value  链路的半包对象
    private Map<ChannelHandlerContext, ByteSession> packageMapper = new HashMap<ChannelHandlerContext, ByteSession>();

    public String byteBufferToString(ChannelHandlerContext ctx, ByteBuf byteBuf) throws UnsupportedEncodingException {


        //查询map中是否存在该链路，
        // 存在 拿出Buffer Append
        // 不存在保存链路 处理数据，数据不完整保存，完整抛出，大于就摘取正确报文，保留多出来的报文
        //todo 处理不存在的情况]
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);


        String strs = new String(bytes,"UTF-8");
        ////System.out.println("当前链路所有报文[" + strs + "] length:" + strs.length());
        byteBuf.resetReaderIndex();


        if (packageMapper.get(ctx) == null) {
            ByteSession byteSession = new ByteSession();
            byteSession.setCtx(ctx);
            //读取可读字节

            if (bytes.length > 19) {

                //把byteBuf数据读取bytes 在处理bytes中的数据
                ////System.out.println("开始读字节");
                //读取前六位 如果是 201589 则是完整报文开始，如果不是 保留不处理
                byte[] SCode = new byte[6];
                for (int i = 0; i < 6; i++) {
                    SCode[i] = bytes[i];
                }
                ////System.out.println("固定报文头:" + new String(SCode));
                //完整报文
                //todo  判断 new String(m,"UTF-8") == 20189 证明是新包，否则拼接
                if (new String(SCode).equals("201589")) {
                    byte[] dataLength = new byte[4];
                    int size = 0;
                    //todo 读取报文长度 四位
                    for (int i = 15; i < 19; i++) {
                        dataLength[size++] = bytes[i];
                    }
                    //业务报文长度
                    int datalengt = Integer.valueOf(new String(dataLength));
                    //完整报文
                    int resultLength = datalengt + 19;
                    //总长度 - 报文头 =业务报文长度
                    int byteBufLength = bytes.length - 19;

                    ////System.out.println("完整报文长度:[" + resultLength + "]  业务报文长度:[" + datalengt + "] 除报文头长度:[" + byteBufLength + "]");
                    //完整报文
                    if (byteBufLength == datalengt) {
                        ////System.out.println("直接返回这个报文" + new String(bytes));
                        byte [] no =new byte[0] ;
                        byteSession.setByteBuffer(no);
                        packageMapper.put(ctx,byteSession);
                        return new String(bytes,"UTF-8");
                    }
                    //粘包
                    if (byteBufLength > datalengt) {
                        ////System.out.println("设定字节数组大小为：" + resultLength);

                        byte[] result = new byte[resultLength];
                        for (int i = 0; i < resultLength; i++) {
                            result[i] = bytes[i];
                        }
                        ////System.out.println("返回完整报文：" + new String(result));


                        ////System.out.println("多余的报文长度：" + (bytes.length - resultLength));
                        ////System.out.println("从这个长度开始读" + resultLength);

                        int l = bytes.length - resultLength;
                        ////System.out.println(l);
                        byte[] ds = new byte[l];
                        int ds2 = 0;
                        for (int i = resultLength; i < bytes.length; i++) {
                            ds[ds2++] = bytes[i];
                        }
                        ////System.out.println("多出来的报文" + new String(ds));

                        //todo 多余的报文
                        byteSession.setByteBuffer(ds);
                        packageMapper.put(ctx, byteSession);
                        return new String(result,"UTF-8");
                    }

                    //半包
                    if (byteBufLength < datalengt) {
                        String str = new String(bytes,"UTF-8");
                        ////System.out.println("半包[" + str + "] length:" + str.length());
                        byteSession.setByteBuffer(bytes);
                        packageMapper.put(ctx, byteSession);
                        return "0";
                    }
                }


            } else {
                byteSession.setByteBuffer(bytes);
                packageMapper.put(ctx, byteSession);
                return "0";
            }

        } else {
            ByteSession byteSession = packageMapper.get(ctx);
            byte[] byteBuffer = byteSession.getByteBuffer();
            ////System.out.println("会话中的包:" + new String(byteBuffer));
            ////System.out.println("开始读字节");

            byte[] res = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                res[i] = bytes[i];
            }
            ////System.out.println("新包需要拼接到之前的报:" + new String(res));
            int dataSize = byteBuffer.length + res.length;
            ////System.out.println("拼包 长度为" + dataSize);


            byte[] resultByte = new byte[byteBuffer.length + bytes.length];
            for (int i = 0; i < resultByte.length; i++) {
                if (i < byteBuffer.length) {
                    resultByte[i] = byteBuffer[i];
                } else {
                    resultByte[i] = bytes[i - byteBuffer.length];
                }
            }

            ////System.out.println("两个包拼接后 " + new String(resultByte));

            if (resultByte.length > 19) {
                //把byteBuf数据读取bytes 在处理bytes中的数据
                ////System.out.println("开始读报文固定数字");
                //读取前六位 如果是 201589 则是完整报文开始，如果不是 保留不处理
                byte[] SCode = new byte[6];
                for (int i = 0; i < 6; i++) {
                    SCode[i] = resultByte[i];
                }
                ////System.out.println("拼包固定报文头:" + new String(SCode));
                //完整报文
                //todo  判断 new String(m,"UTF-8") == 20189 证明是新包，否则拼接
                if (new String(SCode).equals("201589")) {
                    byte[] dataLength = new byte[4];
                    int size = 0;
                    //todo 读取报文长度 四位
                    for (int i = 15; i < 19; i++) {
                        dataLength[size++] = resultByte[i];
                    }
                    //业务报文长度
                    int datalengt = Integer.valueOf(new String(dataLength));
                    //完整报文
                    int resultLength = datalengt + 19;
                    //（会话中半包长度+ 当前参数的长度） - 报文头 =业务报文长度
                    int byteBufLength = dataSize - 19;

                    ////System.out.println("完整报文长度:[" + resultLength + "]  业务报文长度:[" + datalengt + "] 除报文头长度:[" + byteBufLength + "]");
                    //完整报文
                    if (byteBufLength == datalengt) {
                        ////System.out.println("直接返回这个报文" + new String(resultByte));
                        byte [] no =new byte[0] ;
                        byteSession.setByteBuffer(no);
                        packageMapper.put(ctx, byteSession);
                        return new String(resultByte,"UTF-8");
                    }
                    //粘包
                    if (byteBufLength > datalengt) {
                        ////System.out.println("设定字节数组大小为：" + resultLength);

                        byte[] result = new byte[resultLength];
                        for (int i = 0; i < resultLength; i++) {
                            result[i] = resultByte[i];
                        }
                        ////System.out.println("返回完整报文：" + new String(result));


                        ////System.out.println("多余的报文长度：" + (resultByte.length - resultLength));
                        ////System.out.println("从这个长度开始读" + resultLength);

                        int l = resultByte.length - resultLength;
                        ////System.out.println(l);
                        byte[] ds = new byte[l];
                        int ds2 = 0;
                        for (int i = resultLength; i < resultByte.length; i++) {
                            ds[ds2++] = resultByte[i];
                        }
                        ////System.out.println("多出来的报文" + new String(ds));
                        packageMapper.remove(ctx);

                        ByteSession newByteSession = new ByteSession();
                        //todo 多余的报文
                        newByteSession.setCtx(ctx);
                        newByteSession.setByteBuffer(ds);
                        packageMapper.put(ctx, newByteSession);
                        return new String(result,"UTF-8");
                    }

                    //半包
                    if (byteBufLength < datalengt) {
                        String str = new String(resultByte);
                        ////System.out.println("半包[" + str + "] length:" + str.length());
                        byteSession.setByteBuffer(resultByte);
                        packageMapper.put(ctx, byteSession);
                        return "0";
                    }
                }

            } else {
                byteSession.setByteBuffer(resultByte);
                packageMapper.put(ctx, byteSession);
                return "0";
            }
        }
        return "0";
    }


    /**
     * @param ctx
     * @return 完整报文
     * @throws UnsupportedEncodingException
     * @Description 准备做轮询当前的会话包中是否有完整包
     */
    public String ergodic(ChannelHandlerContext ctx) throws UnsupportedEncodingException {

        ByteSession byteSession = packageMapper.get(ctx);

        byte[] byteBuffer = byteSession.getByteBuffer();

        ////System.out.println("[轮询]会话中的包:" + new String(byteBuffer));

        int length = byteBuffer.length;//获取链路中数据的长度

        ////System.out.println("[轮询]链路中数据长度" + length);

        if (length > 19) {// 链路 中StringBuffer的长度大于19 ，获取四位消息长度
            //把byteBuf数据读取bytes 在处理bytes中的数据
            ////System.out.println("[轮询]开始读字节");
            //读取前六位 如果是 201589 则是完整报文开始，如果不是 保留不处理
            byte[] SCode = new byte[6];
            for (int i = 0; i < 6; i++) {
                SCode[i] = byteBuffer[i];
            }
            ////System.out.println("[轮询]固定报文头:" + new String(SCode));
            //完整报文
            //todo  判断 new String(m,"UTF-8") == 20189 证明是新包，否则拼接
            if (new String(SCode).equals("201589")) {
                byte[] dataLength = new byte[4];
                int size = 0;
                //todo 读取报文长度 四位
                for (int i = 15; i < 19; i++) {
                    dataLength[size++] = byteBuffer[i];
                }
                //业务报文长度
                int datalengt = Integer.valueOf(new String(dataLength));
                //完整报文
                int resultLength = datalengt + 19;
                //总长度 - 报文头 =业务报文长度
                int byteBufLength = length - 19;

                ////System.out.println("[轮询]完整报文长度:[" + resultLength + "]  业务报文长度:[" + datalengt + "] 除报文头长度:[" + byteBufLength + "]");
                //完整报文
                if (byteBufLength == datalengt) {
                    ////System.out.println("[轮询]直接返回这个报文" + new String(byteBuffer));
                    byte [] no =new byte[0] ;
                    byteSession.setByteBuffer(no);
                    packageMapper.put(ctx,byteSession);
                    return new String(byteBuffer,"UTF-8");
                }
                //粘包
                if (byteBufLength > datalengt) {
                    ////System.out.println("[轮询]设定字节数组大小为：" + resultLength);

                    byte[] result = new byte[resultLength];
                    for (int i = 0; i < resultLength; i++) {
                        result[i] = byteBuffer[i];
                    }
                    ////System.out.println("[轮询]返回完整报文：" + new String(result));


                    ////System.out.println("[轮询]多余的报文长度：" + (length - resultLength));
                    ////System.out.println("[轮询]从这个长度开始读" + resultLength);

                    int l = length - resultLength;
                    ////System.out.println(l);
                    byte[] ds = new byte[l];
                    int ds2 = 0;
                    for (int i = resultLength; i < length; i++) {
                        ds[ds2++] = byteBuffer[i];
                    }
                    ////System.out.println("[轮询]多出来的报文" + new String(ds));
                    // 删除会话
                    packageMapper.remove(ctx);
                    //会话中重新定制新的半包
                    ByteSession newByteSession = new ByteSession();
                    newByteSession.setCtx(ctx);
                    newByteSession.setByteBuffer(ds);
                    packageMapper.put(ctx, newByteSession);

                    return new String(result,"UTF-8");
                }

                //半包
                if (byteBufLength < datalengt) {

                    return "0";
                }

            }
            return "0";
        } else {
            return "0";
        }

    }
}

class ByteSession {
    private ChannelHandlerContext ctx;//链路
    private byte[] byteBuffer;//半包存放

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public byte[] getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(byte[] byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    @Override
    public String toString() {
        return "ByteSession{" +
                "ctx=" + ctx +
                ", byteBuffer=" + Arrays.toString(byteBuffer) +
                '}';
    }
}
