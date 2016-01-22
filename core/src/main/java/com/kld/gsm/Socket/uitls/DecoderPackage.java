package com.kld.gsm.Socket.uitls;


import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.util.JsonMapper;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:37
 * @Description:
 */
public class DecoderPackage {
    /**
     *
     * @param result  AppendPackage 类处理的整包String  进行处理转换对象
     * @return  GasMessage对象
     */
    public static GasMsg getP(String result){
        //开始对这个串做拆分
        String sCode = result.substring(0, 5);
        String pidLenth = result.substring(5,6);
        String pid = result.substring(6, 15);
        String dataLenth = result.substring(15, 19);
        String message = result.substring(19);
        ////System.out.println();
        try {

            ResultMsg keepMessageBody = new JsonMapper().fromJson(message, ResultMsg.class);
            ////System.out.println("没问题："+keepMessageBody);
        }catch (Exception e){
            ////System.out.println("转换异常");
        }

        //光标复位，从零位解析
        //msg.resetReaderIndex();
        //创建心跳类
        GasMsg  gasMsg = new GasMsg();

        gasMsg.setsCode(sCode);
        gasMsg.setPidLenth(pidLenth);
        gasMsg.setPid(pid);
        gasMsg.setDataLenth(dataLenth);
        gasMsg.setMessage(message);
        return gasMsg;
    }
}
