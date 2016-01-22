package com.kld.gsm.MacLog;


import com.kld.gsm.MacLog.util.EhCacheHelper;
import com.kld.gsm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 日志的主要处理程序，处理每一行新增加的数据  test b
 * Created by miaozy on 15/10/25.
 */
public class MacLogConsumer implements Runnable {


    private final LinkedBlockingQueue<String> workItems;

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(MacLogConsumer.class);

    public MacLogConsumer(LinkedBlockingQueue<String> workItems) {
        this.workItems = workItems;
    }

    public MacLogConsumer() {
        this.workItems = null;
    }

    //油枪空闲占位符
    private final int GUNFREE = 2;
    /// <summary>
    /// 卡插入占位符
    /// </summary>
    private final int CARDINSERT = 19;
    /// <summary>
    /// 加油占位符
    /// </summary>
    private final int GUNBUSY = 11;


    /**
     * 缓存的每个端口的数据
     */
    private final ConcurrentHashMap<Integer, String> cachedPortDataMap_32 = new ConcurrentHashMap<Integer, String>();
    private final ConcurrentHashMap<Integer, String> cachedPortDataMap_31 = new ConcurrentHashMap<Integer, String>();

    public void run() {
        while (true) {

            try {
                String newdata = workItems.take();

                // //System.out.println("proce new line:" + newdata);
                ConsumerLine(newdata);
            } catch (Exception e) {
                logger.error("maclog consumer exception.." + e);
                e.printStackTrace();
            }


        }
    }


    public void ConsumerLine(String newdata) {
        try {
            //具体处理程序
            //过滤掉非Recv内容
            String[] linedata = newdata.split("\\,");
            if (linedata.length < 6) {
                return;
            }

            if (linedata.length < 6) {
                return;
            }

            if (!linedata[2].equals("Recv:")) {
                ////System.out.println("No Recv:  data is" + linedata[2]);
                return;
            }
            //取出prot
            int port = Integer.parseInt(linedata[3].split("\\:")[1]);
            int len = Integer.parseInt(linedata[4].split("\\:")[1]);
            String data = linedata[5].substring(6);

            //判断如果data的长度与len不符，则不处理
            String[] dataArray = data.split(" ");

            if (dataArray.length != len) {
                //System.out.println("length is not equal continue" + dataArray.length + "|" + len);
                return;
            }

            //包含两个FA的,则替换两个FA为一个FA
            data = data.replaceAll("FA FA", "FA");
            dataArray = data.split(" ");
            len = dataArray.length;

            //如果此port正好在交易缓存数据中存在
            String cachedData = cachedPortDataMap_32.get(port);

            if (cachedData != null && !dataArray[0].equals("FA")) {
                String newData = cachedData + " " + data;
                String[] allCachedDataArray = newData.split(" ");
                if (allCachedDataArray.length < 104) {
                    cachedPortDataMap_32.put(port, newData);
                    return;
                }else
                {
                    processFuelData(port, allCachedDataArray);
                    return;
                }
            }
            else if (cachedData != null) {
                String[] allCachedDataArray = cachedData.split(" ");
                processFuelData(port, allCachedDataArray);
                return;
            }
            String cachedData_31 = cachedPortDataMap_31.get(port);
            if (cachedData_31 != null && !dataArray[0].equals("FA")) {
                String newData = cachedData_31 + " " + data;
                String[] allCachedDataArray = newData.split(" ");
                int datalength = Integer.parseInt(allCachedDataArray[4]+allCachedDataArray[5]);
                if (datalength > allCachedDataArray.length - 8) {
                    cachedPortDataMap_31.put(port, newData);
                } else {

                    TransferState(newData);

                    //并且把数据清空
                    cachedPortDataMap_31.remove(port);
                }
                return;
            }

            //对于所有小于6的数据,也是同样的缓存
            if (dataArray.length < 7) {
                cachedPortDataMap_31.put(port, data);
                return;
            }
            String commandStr = dataArray[6];
            if (commandStr.equals("30")) {
                List<MacLogInfo> list = TransferGunFree(dataArray);
//                    for (MacLogInfo item : list) {
//                        //处理数据
//                        //放到缓存中
//                        //System.out.println("更新缓存，编号:" + item.GunNum);
//                        EhCacheHelper.updteCache(item);
//                    }
            } else if (commandStr.equals("31")) {// 状态数据
                int datalength = Integer.parseInt(dataArray[4] + dataArray[5]);

                if (datalength == len - 8) {
                    TransferState(data);
                } else {
                    cachedPortDataMap_31.put(port, data);
                }
            } else if (commandStr.equals("32")) {// 交易数据
                //如果是32交易数据,则必须等总长度到达104后才进行处理
                cachedPortDataMap_32.put(port, data);
            } else {//其他的命令软集线器暂时不处理
            }


        } catch (Exception e) {
            logger.error("read exception.." + e);
            e.printStackTrace();
        }
    }

    private void processFuelData(int port, String[] allCachedDataArray) {
        List<RefueledInfo> refuelList = TransferFuelData(allCachedDataArray);
        for (RefueledInfo refueledInfo : refuelList) {
            MacLogInfo macLogInfo = new MacLogInfo();
            switch (refueledInfo.TType) {
                case 6:
                    macLogInfo.GunStatus = GunStatusEnum.下班;
                    break;
                default:
                    macLogInfo.GunStatus = GunStatusEnum.挂枪;
                    break;
            }
            macLogInfo.GunNum = refueledInfo.GunNo;
            macLogInfo.amount= refueledInfo.Amount;
            macLogInfo.CardNum = refueledInfo.CardNo;
            macLogInfo.Price = refueledInfo.Price;
            macLogInfo.qty= refueledInfo.Qty;
            macLogInfo.setPumpReadout(refueledInfo.VolTotal);
            System.out.println("32 update cache data:" + macLogInfo.toString());
            EhCacheHelper.updteCache(macLogInfo);

        }
        //并且把数据清空
        cachedPortDataMap_32.remove(port);
    }

    /*public static void main(String[] args) throws Exception {

        MacLogConsumer macLogConsumer = new MacLogConsumer();
        macLogConsumer.ProcessLine();
    }*/

    // 文件内容的总行数。
    static int getTotalLines(File file) throws IOException {
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
        // it will return the number of characters actually skipped
        lineNumberReader.skip(Long.MAX_VALUE);
        int lineNumber = lineNumberReader.getLineNumber();
        lineNumber++;
        lineNumberReader.close();
        return lineNumber;
    }


    /// <summary>
    /// 转换枪空闲
    /// </summary>
    /// <param name="datastr"></param>
    /// <returns></returns>
    public List<MacLogInfo> TransferGunFree(String[] rData) {
        if (rData.length <= 3) return null; //如果接收的数组长度小于等于3，不处理，（命令头+crc校验码就=3了）；

        //todo 暂时没有进行CRC检查

        List<MacLogInfo> retList = new ArrayList<MacLogInfo>();
        int dataCount = Integer.parseInt(rData[5]) - 1;
        //从第8位开始读
        int startIndex = 7;
        for (int i = 0; i < dataCount; i++) {
            String[] gunFreeData = new String[2];
            System.arraycopy(rData, startIndex, gunFreeData, 1, 1);
            MacLogInfo data1 = TransformGunFree(gunFreeData);
            retList.add(data1);
            startIndex += 1;
        }

        return retList;
    }

    /// <summary>
    /// 转换空闲
    /// </summary>
    /// <param name="rData"></param>
    /// <returns></returns>
    private MacLogInfo TransformGunFree(String[] rData) {
        //rdata[9] 枪号
        int gunno = Integer.parseInt(rData[1], 16);
        MacLogInfo macLogInfo = new MacLogInfo();
        macLogInfo.GunNum = (byte) gunno;
        macLogInfo.GunStatus = GunStatusEnum.空闲;
        return macLogInfo;
    }


    /// <summary>
    /// 转换加油状态
    /// </summary>
    /// <param name="str"></param>
    private void TransferState(String rData) {
       // logger.info(String.format("Rec 31 type data:%s", rData));
        List<MacLogInfo> list = SubTransferState(rData);
        if (list == null) {
            // logger.info("Rec 31 List :is null");
        } else {
            // logger.info("Rec 31 List :" + list.size());
        }
        if (list != null) {

            for (MacLogInfo data : list) {

                System.out.println("31 data is:" + data);
                EhCacheHelper.updteCache(data);
            }
        }
    }

    public List<MacLogInfo> SubTransferState(String datastr) {
        String[] rData = datastr.split(" ");
        if (rData.length <= 3) return null; //如果接收的数组长度小于等于3，不处理，（命令头+crc校验码就=3了）；

        //todo  CRC校验
        boolean bCRC = true;
        List<MacLogInfo> retList = null;
        if (bCRC) {
            if (rData.length <= 10) return null;
            retList = new ArrayList<MacLogInfo>();
            int dataCount = Integer.parseInt(rData[7], 16);
            //从第8位开始读
            int startIndex = 8;
            for (int i = 0; i < dataCount; i++) {
                String signStr = this.TransforSign(rData[startIndex]);

                //空闲状态就不转换了，否则可能会跟挂枪混在一起
                //2013-05-24 由于远程监管系统需要 这个状态也转换下
                if (signStr.equals("00")) {
                    String[] gunFreeData = new String[GUNFREE];
                    System.arraycopy(rData, startIndex, gunFreeData, 0, GUNFREE);
                    MacLogInfo data1 = TransformGunFree(gunFreeData);
                    retList.add(data1);
                    startIndex += GUNFREE;
                } else if (signStr.equals("01")) {
                    String[] cardInsertData = new String[CARDINSERT];
                    System.arraycopy(rData, startIndex, cardInsertData, 0, CARDINSERT);
                    MacLogInfo data = TransformCardInsert(cardInsertData);
                    retList.add(data);
                    startIndex += CARDINSERT;
                } else if (signStr.equals("02")) {
                    String[] gunbusyData = new String[GUNBUSY];
                    System.arraycopy(rData, startIndex, gunbusyData, 0, GUNBUSY);
                    MacLogInfo data2 = TransformGunBusy(gunbusyData);
                    retList.add(data2);
                    startIndex += GUNBUSY;
                } else {
                    DateUtil dateUtil = new DateUtil();
                    logger.error(String.format("failue parse mac gun data：{0}", dateUtil.getDateTime()) + datastr);
                    break;
                }
            }
        }
        return retList;
    }


    /// <summary>
    /// 只取低2位，其他位不考虑
    /// </summary>
    /// <param name="inputstr"></param>
    /// <returns></returns>
    private String TransforSign(String inputstr) {
        byte andNum = 3;
        byte input = Byte.parseByte(inputstr, 16);
        return padLeft(String.valueOf(input & andNum), 2);
    }

    String padLeft(String s, int length) {
        byte[] bs = new byte[length];
        byte[] ss = s.getBytes();
        Arrays.fill(bs, (byte) (48 & 0xff));
        System.arraycopy(ss, 0, bs, length - ss.length, ss.length);
        return new String(bs);
    }


    /// <summary>
    /// 转换卡插入状态
    /// St	状态字	1	BIN	　	Bit1=0，
    //Bit0=1：卡插入（IC卡加油机）
    //NZN	逻辑枪号	1	Hex	　
    //LEN	卡信息数据长度	1	Hex	　	固定为0x10
    //ASN	卡应用号	10	BCD	　	压缩BCD码
    //CardSt	卡状态	2	BCD	　	使用附录3的定义
    //BAL	余额	4	Hex	　
    /// </summary>
    /// <param name="rData"></param>
    /// <returns></returns>
    private MacLogInfo TransformCardInsert(String[] rData) {
        //rdata[9] 枪号
        int gunno = Integer.parseInt(rData[1], 16);
        //卡号
        String[] cardNoArray = new String[10];
        System.arraycopy(rData, 3, cardNoArray, 0, 10);
        String cardNo = StringUtils.join(cardNoArray, "");
        ////System.out.println("test:" + cardNo);

        String cType = cardNo.substring(5, 7);
        MacLogInfo gun = new MacLogInfo();
        gun.GunNum = (byte) gunno;
        gun.GunStatus = GunStatusEnum.卡插入;
        gun.CardNum = cardNo;


        if (cType.equals("41")) {
            gun.CardType = CardTypeEnum.员工卡;
        } else if (cType.equals("11")) {
            gun.CardType = CardTypeEnum.IC卡;
        } else if (cType.equals("51")) {
            gun.CardType = CardTypeEnum.验泵卡;
        } else {
            gun.CardType = CardTypeEnum.IC卡;
        }
        return gun;
    }

    /// <summary>
    /// 转换加油状态
    /// </summary>
    /// St	状态字	1	Hex	　	Bit1=1，
    //Bit0=0：抬枪或加油中
    //NZN	逻辑枪号	1	Hex	　   	　
    //P_UNIT	结算单位/方式	1	Bin	　	使用记录中UNIT的定义
    //AMN	数额	3	Hex	　	单位 使用记录中UNIT的定义
    //VOL	升数	3	Hex	　	单位 0.01升
    //PRC	价格	2	Hex	　	默认系数为0.01
    /// <param name="rData"></param>
    /// <returns></returns>
    private MacLogInfo TransformGunBusy(String[] rData) {
        //rdata[9] 枪号
        int gunno = Integer.parseInt(rData[1], 16);
        //rdata[11~13] 金额
        String sMnt = rData[3] + rData[4] + rData[5];
        int iMnt = Integer.parseInt(sMnt, 16);
        double dMnt = iMnt * 0.01;
        //rdata[14~16] 加油量
        String sVol = rData[6] + rData[7] + rData[8];
        int iVol = Integer.parseInt(sVol, 16);
        double dVol = iVol * 0.01;
        //rdata[17~18]单价
        String sPrice = rData[9] + rData[10];
        int iPrice = Integer.parseInt(sPrice, 16);
        double dPrice = iPrice * 0.01;

        MacLogInfo gun = new MacLogInfo();
        gun.GunNum = (byte) gunno;
        gun.amount= BigDecimal.valueOf(dMnt);
        gun.GunStatus = GunStatusEnum.提枪;
        gun.qty =BigDecimal.valueOf(dVol);
        gun.Price = BigDecimal.valueOf(dPrice);
        return gun;

    }

    public List<RefueledInfo> TransferFuelData(String[] rData) {
        String[] retbody = new String[54];

        //命令字
        System.arraycopy(rData, 0, retbody, 0, 5);

        //写死有48字节的内容
        retbody[5] = "48";

        //前面部分内容
        System.arraycopy(rData, 6, retbody, 6, 30);

        //不知道这个信息的含义
        retbody[36] = "00";
        System.arraycopy(rData, 70, retbody, 37, 3);
        System.arraycopy(rData, 74, retbody, 40, 13);

        //同样不了解
        retbody[53] = "00";
        String retStr = StringUtils.join(retbody, " ");


        //数据唯一标示为：枪号+TTC号+加油时间的ss
        //40 枪号
        //7-10 ttc
        //18 加油时间ss
        //重新复制
        rData = retStr.split(" ");
        List<RefueledInfo> liRec = null;
        try {
            liRec = getRecDataFor1(rData);
            for (RefueledInfo info : liRec) {
                //如果卡号全都为0,则收到的数据有问题,后续需要丢弃此类数据
                if (info.CardNo.equals("00000000000000000000")) {
                    //System.out.println("收到的交易数据不正常,卡号为0:" + info);
                    continue;
                }
               // //System.out.println("32 data is：" + info);
            }

        } catch (Exception ex) {
            logger.error("failue to save vouch data");
            logger.error(ex.getStackTrace().toString());
        }
        return liRec;
    }

    /// <summary>
    /// 根据通讯协议转换上班，下班信息
    /// 􀂾 b7=1：卡错；
    //􀂾 b6=0/1：使用后台黑(白)名单/使用油机内黑(白)名单
    //􀂾 b4=1：扣款签名有效（无法判断用户卡TAC 标记是否清除）；
    //􀂾 b3-b0：0=正常加油；
    //1=逃卡；
    //2=错卡；
    //3=补扣；
    //4=补充
    //5=员工上班（每条枪一条记录；对于一个IC卡终端有多条油枪时，每枪一条记录）
    //6=员工下班（每条枪一条记录；一个IC卡终端有多条油枪时，每枪一条记录）
    //7=非卡机联动加油
    //8=对油价信息的回应
    //9=卡片交易出错记录（出错后在TAC即电子签名字段填写出错原因，出错原因代码见附录9）
    /// </summary>
    /// <param name="input"></param>
    /// <returns></returns>
    private int TransforTType(byte input) {
        //不取该字段的高5位，采用&的方式
        byte andNum = 7;
        int retVal = input & andNum;
        return retVal;
    }

    public List<RefueledInfo> getRecDataFor1(String[] rData) {
        List<RefueledInfo> liRecData = new ArrayList<RefueledInfo>();
        try {
            //crc校验成功，进行数据脱壳
            //1.TTC号 rdata[7~10]
            //string ttc = rData[7] + rData[8] + rData[9] + rData[10];
            String ttc = "00" + rData[8] + rData[9] + rData[10];
            ttc = String.valueOf(Integer.parseInt(ttc, 16));
            //2.交易类型  rdata[11]
            // Convert the number expressed in base-16 to an integer.
            int t_type = this.TransforTType(Byte.parseByte(rData[11], 16));
            //3.交易日期及时间 rdata[12~18]
            Date cTime;
            //集线器过来的加油时间不靠谱
            cTime = DateUtil.convertStringToDate(rData[12] + rData[13] + "-" + rData[14] + "-" + rData[15] + " " + rData[16] + ":" + rData[17] + ":" + rData[18]);
            //4.卡号rdata[19~28]
            String ASN = rData[19] + rData[20] + rData[21] + rData[22] + rData[23] + rData[24] + rData[25] + rData[26] + rData[27] + rData[28];
            //5.卡余额rdata[29~32]
            String sBAL = rData[29] + rData[30] + rData[31] + rData[32];

            long BAL = Long.parseLong(sBAL, 16);
            double bBAL = BAL * 0.01;
            //6.加油金额 rdata[33~35]
            String sAMN = rData[33] + rData[34] + rData[35];
            Integer iAMN = Integer.parseInt(sAMN, 16);
            double bAMN = iAMN * 0.01;
            //7.N_DEBITSRC rdata[36] 待验证
            int iDebitSRC = Integer.parseInt(rData[36], 16);
            //8.UNIT	结算单位/方式 rdata[37]  bit0- 0电子钱包  bit0-1积分钱包
            String unit = formatUnit(rData[37]);
            //9.C-TYPE	卡类 rdata[38]
            String cardType = rData[38];
            //10.VER	版本 rdata[39]
            String ver = rData[39];
            //11.NZN	枪号 rdata[40]
            int nzn = Integer.parseInt(rData[40], 16);
            //12.G-CODE	油品代码 rdata[41~42],目前没有使用此数据，是通过系统中的油枪配置的油品来获得
            //13.VOL	升数 rdata[43~45]
            String sVOL = rData[43] + rData[44] + rData[45];
            int iVOL = Integer.parseInt(sVOL, 16);
            double dVOL = iVOL * 0.01;
            //14.PRC	成交价格 rdata[46~47]
            String sPRC = rData[46] + rData[47];
            int iPRC = Integer.parseInt(sPRC, 16);
            double dPRC = iPRC * 0.01;
            //15.EMP	员工号 rdata[48]
            String emp = rData[48];
            //16.V-TOT	升累计 rdata[49~52]dataArray[6]
            String sVTOT = rData[49] + rData[50] + rData[51] + rData[52];
            long lVTOT = Integer.parseInt(sVTOT, 16);
            double dVTOT = lVTOT * 0.01;
            //根据枪号去获取绑定的油品代码，管控代码和erp代码


            //生成待结账交易记录对象
            RefueledInfo rec = new RefueledInfo();


            rec.WorkDay = new Date();
            rec.Shift = 1;
            rec.GunNo = (byte) nzn;


            rec.TankNo = 1;


            rec.GasCode = "60185261";
            rec.GasCodeAS = "";


            rec.Qty = BigDecimal.valueOf(dVOL);
            rec.Price = BigDecimal.valueOf(dPRC);
            rec.Amount = BigDecimal.valueOf(bAMN);
            rec.RefueledTime = cTime;
            rec.FLAG = 0;
            rec.POSTTC = ttc;
            // oilDto.Model.POSP = short.Parse(comm.PortName.Substring(3));
            rec.ASN = ASN;
            rec.Balance = bBAL;
            rec.VolTotal = dVTOT;//泵码数
            rec.TType = (short) t_type;
            rec.C_UNIT = unit;
            rec.N_CARDTYPE = Short.parseShort(cardType);
            rec.C_VER = ver;
            rec.C_EMP = emp;
            rec.C_RFU = "0";
            rec.CardNo = ASN;
            //判断当前的加油记录是哪个交易，员工卡/IC卡/EM卡
            String cType = ASN.substring(5, 7);

            if (cType.equals("41")) {
                rec.CardType = CardTypeEnum.员工卡;
            }
            if (cType.equals("11")) {
                rec.CardType = CardTypeEnum.IC卡;
            }
            if (cType.equals("51")) {
                rec.CardType = CardTypeEnum.验泵卡;
            } else {
                rec.CardType = CardTypeEnum.IC卡;
            }
            //将原始数据信息还原存放
            rec.HubData = StringUtils.join(rData, " ");
            liRecData.add(rec);
        } catch (Exception ex) {
            logger.error("failue to trans vouch data：" + ex.getMessage());
            logger.error(ex.getStackTrace().toString());
            logger.error(StringUtils.join(rData, " "));
        }
        return liRecData;
    }

    /// <summary>
    /// 只取格式化为二进制后的最后一位数（如果不等于1就等于0）
    /// </summary>
    /// <param name="strData"></param>
    /// <returns></returns>
    private String formatUnit(String strData) {
        String strResult = "0";
        if (StringUtils.isEmpty(strData)) {
            return strResult;
        }
        String strTemp = String.valueOf(Byte.parseByte(String.valueOf(strData.charAt(strData.length() - 1)), 2));
        strResult = String.valueOf(strTemp.charAt(strTemp.length() - 1));

        return strResult;
    }


}
