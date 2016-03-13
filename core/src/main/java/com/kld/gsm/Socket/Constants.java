package com.kld.gsm.Socket;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:11
 * @Description:
 */
public class Constants {

    public static final String DEFAULT_SCODE = "20158";//固定起始符号
    public static final String DEFAULT_PID_LENGTH = "9";//固定业务编码长度

    //业务代码 枚举
    public enum PID_Code {
        //App
        A15_11111,
        A15_10000,//	预留业务代码
        A15_10001,//	操作员登陆状态及权限信息
        A15_10002,//	加油枪状态及泵码信息
        A15_10003,//	交易明细及交易结束时液位仪信息
        A15_10004,//	实时库存信息
        A15_10005,//	油品进货验收数据
        A15_10006,//    液位仪设置
        A15_10007,//    探棒矫正参数设置
        A15_10008,//    探棒与油罐的关系
        A15_10009,//    容积表<上传\下发>
        A15_10010,//    预报警设置
        A15_10011,//    罐枪基础数据同步
        A15_10012,
        A15_10013,//
        A15_10014,
        A15_10015,
        A15_10016//高升转换

    }
}