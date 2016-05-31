
package com.kld.gsm.coord.server.handler;

import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.ATGDevice.*;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.MacLog.util.EhCacheHelper;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.SysConfig;
import com.kld.gsm.coord.Constants;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.dao.NodeInforDao;
import com.kld.gsm.coord.dao.OilCanInforDao;
import com.kld.gsm.coord.domain.NodeInfor;
import com.kld.gsm.coord.domain.Sysinfor;
import com.kld.gsm.coord.servcie.*;
import com.kld.gsm.coord.utils.ReflectUtils;
import com.kld.gsm.util.JsonMapper;
import io.netty.channel.ChannelHandlerContext;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;


/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:31
 * @Description:
 */

public class ProtocolProcessor {


    private ProtocolProcessor() {
    }

    private static ProtocolProcessor instance = null;

    public static ProtocolProcessor getInstance() {
        if (instance == null) {
            instance = new ProtocolProcessor();
        }
        return instance;
    }

    //region  resource
    @Resource
    private AcceptSevices acceptanceOdRegisterInfoService;
    @Resource
    private LoginMsgService loginMsgService;
    @Autowired
    private IDailyTradeAccountService dailyTradeAccountService;
    @Autowired
    private TankGunBasedDataService tankGunBasedDataService;
    @Autowired
    private OilPurchaseAcceptanceService oilPurchaseAcceptanceService;
    @Resource
    private OilCanInforService oilCanInforService;
    @Autowired
    private OilCanInforDao oilCanInforDao;
    @Autowired
    private SysManageCubageDao sysManageCubageDao;
    @Autowired
    SysManageCubageInfoDao sysManageCubageInfoDao;
    @Autowired
    SysManageIquidCubageDao sysManageIquidCubageDao;
    @Autowired
    SysManageIquidCubageInfoDao sysManageIquidCubageInfoDao;
    @Autowired
    SysmanageService sysmanageService;
    @Autowired
    private NodeInforDao nodeInforDao;
    @Autowired
    SysManageAlarmParameterDao sysManageAlarmParameterDao;

    //endregion


    //region 保存所有链接 的map  key  是  com.kld.gsm.syntocenter.socket 链路    value 是自己封装的session 对象
    public Map<ChannelHandlerContext, ConnectionSession> appMapper = new HashMap<ChannelHandlerContext, ConnectionSession>();
    public Map<ChannelHandlerContext, ConnectionSession> Mapper = new HashMap<ChannelHandlerContext, ConnectionSession>();
    private static final Logger log = LoggerFactory.getLogger(ProtocolProcessor.class);
    public static ResultMsg subMessage = null;
//endregion


    /**
     * 具体业务逻辑处理入口
     *
     * @param ctx
     * @param message
     */
    public void handlerProtocol(ChannelHandlerContext ctx, Object message) {
        // //System.out.println("ctrl收到消息对象:" + message.toString());
        GasMsg gasMessage = (GasMsg) message;
        ConnectionSession session = null;
        String keepmsg = gasMessage.getMessage();
        //TODO 将JSON消息体转成对象 进行业务代码的调试
        subMessage = new JsonMapper().fromJson(keepmsg, ResultMsg.class);

        //保存新会话
        if (gasMessage.getPid().startsWith("A")) {
            log.info(new Date() + "["+ctx+"]请求:" + gasMessage.getPid());
            //if (gasMessage.getPid().startsWith(Constants.PID_Code.A15_10000.toString())) {

            //判断这个id是否有会话存在
            ////System.out.println("if save session?");

            session = searchDitCtxByGasStationNo(subMessage.getId(), appMapper);

            if (session != null) {
                ////System.out.println("have a session.....key" + session.getId());
                if (session.getCtx() != ctx) {
                    //System.out.println("socket Different，no response");
                    //System.out.println("old id:" + session.getId());
                    //System.out.println("new id:" + subMessage.getId());
                    //System.out.println("now socket:" + ctx);
                    //System.out.println("session socket:" + session.getCtx());
                    //     if (session.getId().equals(subMessage.getId())) {
                    //System.out.println("delete session ctx:" + session.getCtx());
                    session.getCtx().close();
                    appMapper.remove(session.getCtx());

                    //System.out.println("save ctx:" + ctx);
                    session = new ConnectionSession(subMessage.getId(), ctx);
                    appMapper.put(ctx, session);
                    // return;
                    //   }
                }/* else {
                    GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), new ArrayList(), Constants.PID_Code.A15_10000.toString());
                    System.out.println("返回心跳");
                    ctx.writeAndFlush(gasMsg);
                    return;
                }*/
            }
            //    }
            else {

                //System.out.println("session is null,save ctx");
                if (subMessage.getId() != null) {
                    session = new ConnectionSession(subMessage.getId(), ctx);
                    appMapper.put(ctx, session);
                    //System.out.println("save socket key==" + subMessage.getId());
//                    GasMsg gasMsg =ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), new ArrayList(),gasMessage.getPid());
//                    ctx.writeAndFlush(gasMsg);
                } else {
                    //System.out.println("no ID no Action");
                    return;
                }
            }
        } else {  //如果开头不是A 直接返回
            return;
        }
        //心跳返回
        if (gasMessage.getPid().startsWith(Constants.PID_Code.A15_10000.toString())) {
            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), new ArrayList(), gasMessage.getPid());
            ctx.writeAndFlush(gasMsg);
        }

        //region TODO 操作员登陆状态及权限信息
        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10001.toString())) {
            log.info("开始读权限");
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMessage.getMessage(), ResultMsg.class);
            System.out.println("---resultMsg--" + resultMsg.toString());
            LoginMsgService loginMsgService = Context.getInstance().getBean(LoginMsgService.class);
            GasMsg gasMsg = loginMsgService.setMsg(resultMsg.getId());
            log.info(gasMsg.toString());


            ctx.writeAndFlush(gasMsg);
        }
        //endregion


        //region TODO  A15_10002 	加油枪状态及泵码信息
        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10002.toString())) {
            Map macLogMap = EhCacheHelper.getAllMacLog();
            log.info("macLogMap.size():" + macLogMap.size());
            if (macLogMap.size() < 1) {

                //--------------------------------------
//                MacLogInfo macLogInfo = new MacLogInfo();
//                macLogInfo.setGunNum(Byte.valueOf("2"));
//                macLogInfo.setGasName("名称");
//                macLogInfo.setCardNum("加油卡数");
//                macLogInfo.setPumpReadout(1.1);
//                macLogInfo.setOiler("oiler");
//                macLogInfo.setCardType(CardTypeEnum.IC卡);
//                macLogInfo.setTotalCount(1);
//                macLogInfo.amount= BigDecimal.valueOf(400);
//                macLogInfo.qty= BigDecimal.valueOf(532);
//                macLogInfo.setPrice(BigDecimal.valueOf(100));
//                macLogInfo.setGunStatus(GunStatusEnum.卡插入);
//                macLogInfo.setFuelQuatity(BigDecimal.valueOf(998));
//                List list = new ArrayList();
//                list.add(macLogInfo);
//                MacLogInfo macLogInfo2 = new MacLogInfo();
//                macLogInfo2.setGunNum(Byte.valueOf("1"));
//                macLogInfo2.setGasName("名称");
//                macLogInfo2.setCardNum("加油卡数");
//                macLogInfo2.setPumpReadout(1.1);
//                macLogInfo2.setOiler("oiler");
//                macLogInfo2.setCardType(CardTypeEnum.IC卡);
//                macLogInfo2.setTotalCount(1);
//                macLogInfo2.amount= BigDecimal.valueOf(300);
//                macLogInfo2.qty= BigDecimal.valueOf(132);
//                macLogInfo2.setPrice(BigDecimal.valueOf(130));
//                macLogInfo2.setGunStatus(GunStatusEnum.提枪);
//                macLogInfo2.setFuelQuatity(BigDecimal.valueOf(948));
//                 list.add(macLogInfo2);
//
//                GasMsg gasMsgs = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), list, Constants.PID_Code.A15_10002.toString());

                //--------------------------------------
                ////System.out.println("no message");

                GasMsg gasMsg = ResultUtils.getInstance().sendFAIL(subMessage.getId(), new ArrayList(), Constants.PID_Code.A15_10002.toString());
                // //System.out.println("---4----------------------------");
                // //System.out.println("Return:" + gasMsg);
                ////System.out.println("return : " + gasMsg);
                ctx.writeAndFlush(gasMsg);
                return;
            }
            Iterator<Map.Entry> it = macLogMap.entrySet().iterator();
            List list = new ArrayList();
            log.info("macLogMap.entrySet().size():" + macLogMap.entrySet().size());
            while (it.hasNext()) {
                // //System.out.println("read MacLog ok");
                MacLogInfo macLogInfo = new MacLogInfo();
                try {
                    Map.Entry entry = it.next();
                    // //System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                    Element element = (Element) entry.getValue();
                    macLogInfo = (MacLogInfo) element.getObjectValue();
                    log.info("list add cache："+macLogInfo.toString());
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

                // //System.out.println("Gunstatus:" + macLogInfo.GunStatus);
                ////System.out.println("cardNum:" + macLogInfo.CardNum);
                ////System.out.println("CardType:" + macLogInfo.CardType);
                list.add(macLogInfo);

            }
            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), list, Constants.PID_Code.A15_10002.toString());
            ////System.out.println("<" + gasMsg.toString() + ">");
            if (list!=null) {
                list.clear();
            }
            ctx.writeAndFlush(gasMsg);//构造通知对象
        }
//endregion


        //region TODO  A15_10003 	交易明细及交易结束时液位仪信息
        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10003.toString())) {
            ////System.out.println("A15_10003.....begin");
            //2)收到app反馈 修改mysql交易数据状态
            List<Map> list = subMessage.getData();
            Map map = list.get(0);

            DailyTradeAccount input = new DailyTradeAccount();
            input.setIsRecieved((String) map.get("IsRecieved"));//接收结果
            input.setMacno((Integer) map.get("macno"));//油机编号 (终端机身号)
            input.setOilgun((String) map.get("oilgunno"));//油枪编号 (枪号)
            input.setTtc((Integer) map.get("ttc"));//交易序号
            Date timeDate = Timestamp.valueOf((String) map.get("takedate"));
            ////System.out.println("timeDate:" + timeDate);
            input.setTakedate(timeDate);//交易时间
            //修改mysql交易加油流水表oss_daily_TradeAccount的是否回执操作
            ////System.out.println("================================================================update");
            ////System.out.println("map:" + map.toString());

            ////System.out.println("================================================================update");

            IDailyTradeAccountService dailyTradeAccountService = Context.getInstance().getBean(IDailyTradeAccountService.class);
            ////System.out.println("dailyTradeAccountService:---" + dailyTradeAccountService);
            dailyTradeAccountService.update(input);
            ////System.out.println("input" + input.getIsRecieved());
            ////System.out.println("input" + input.getMacno());
            ////System.out.println("input" + input.getTtc());
            ////System.out.println("input" + input.getTakedate());

            ////System.out.println("================================A15_10003end================================");

        }
//endregion


        //region TODO  A15_10004	实时库存信息
        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10004.toString())) {
            //查寻所有油罐号
            SysManageCanInfoDao sysManageCanInfodao = Context.getInstance().getBean(SysManageCanInfoDao.class);
            List<SysManageCanInfo> oilCanInforList = sysManageCanInfodao.selectAll();
            List<atg_timestock_data_in_t> timeStockList = new ArrayList<atg_timestock_data_in_t>();
            log.info("receive A15_10004");
            log.info("get all can" + oilCanInforList);

            List tankNo = new ArrayList();

            log.info("can no：" + tankNo);
            ////System.out.println("oilcaninfo ------------------------------------oilcaninfo");
            for (SysManageCanInfo oilcan : oilCanInforList) {
                tankNo.add(oilcan.getOilcan());
            }
            ////System.out.println(tankNo.toString());
            ////System.out.println("oilcaninfo ------------------------------------oilcaninfo");


            List<atg_stock_data_out_t> list = ATGManager.getStock(tankNo);

            //log.info("A15_10004 get atg data【" + list.size() + "】---" + list.toString());

//            for (atg_stock_data_out_t a : list) {
//                atg_stock_data_out_t atg = new atg_stock_data_out_t();
//                atg.uOilCanNo = a.uOilCanNo;
//                atg.strDate = a.strDate;
//                atg.strTime = a.strTime;
//                atg.fOilCubage = a.fOilCubage;
//                atg.fOilStandCubage = a.fOilStandCubage;
//                atg.fEmptyCubage = a.fEmptyCubage;
//                atg.fTotalHeight = a.fTotalHeight;
//                atg.fWaterHeight = a.fWaterHeight;
//                atg.fOilTemp = a.fOilTemp;
//                atg.fOilTemp1 = a.fOilTemp1;
//                atg.fOilTemp2 = a.fOilTemp2;
//                atg.fOilTemp3 = a.fOilTemp3;
//                atg.fOilTemp4 = a.fOilTemp4;
//                atg.fOilTemp5 = a.fOilTemp5;
//                atg.fWaterBulk = a.fWaterBulk;
//                atg.fApparentDensity = a.fApparentDensity;
//                atg.fStandDensity = a.fStandDensity;
//                list.add(atg);
//                ////System.out.println(list);
//            }
            GasMsg gss = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), list, Constants.PID_Code.A15_10004.toString());
            if (list!=null) {
                list.clear();
            }
            log.info("return   " + gss);
            ctx.writeAndFlush(gss);
        }
//endregion


        //mysql实时库存保存操作
        MonitorTimeInventory monitorTimeInventory = new MonitorTimeInventory();
        //int ret_monitorTimeInventory = monitorTimeInventoryService.insert(monitorTimeInventory);

        //region TODO  A15_10005	油品进货验收数据
        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10005.toString())) {
            //取得出库单号
            log.info("进货验收主调度启动……………………………………");
            if (null == subMessage.getId() || "".equals(subMessage.getId())) {
                log.info("没有id值，主调度返回");
                return;
            }
            List date = subMessage.getData();
            for (int i = 0; i < date.size(); i++) {
                log.info("取到进货验收接受参数Map:i" + i);
                Map oilStock = (Map) date.get(i);

                //根据出库单号在mysql查询操作
                int oprno = 0;//预留字段，当前操作员
                SysinforService sysinforService = Context.getInstance().getBean(SysinforService.class);
                Sysinfor sysinfor = sysinforService.getAll();
                if (null != sysinfor || !"".equals(sysinfor)) {
                    oprno = sysinfor.getOprno();
                }
                String DeliveryNo = oilStock.get("1").toString();//出库单号
                log.info("取到进货验收接受参数Map" + oilStock.toString());

                OilPurchaseAcceptanceService oilPurchaseAcceptanceService = Context.getInstance().getBean(OilPurchaseAcceptanceService.class);
                //油罐进油明细表
                /*AcceptSevices  acceptanceOdRegisterInfoService = Context.getInstance().getBean(AcceptSevices.class);
                List<AcceptanceOdRegisterInfo> selectAcceptanceOdRegisterInfo = acceptanceOdRegisterInfoService.selectAcceptanceOdRegisterInfo(DeliveryNo);
                if (null == selectAcceptanceOdRegisterInfo) {
                    log.info("没有查询到油罐号");
                    return;
                }*/
                //OilCan没有意义
                int Oilcan = -1; //selectAcceptanceOdRegisterInfo.getOilcan();
                //log.info("打印油罐号" + Oilcan);
                if (oilStock.get("2").toString().equals("0")) {//状态
                    log.info("状态为0" + DeliveryNo);
                    oilPurchaseAcceptanceService.selectAndInsert(DeliveryNo, oprno, subMessage.getId());
                } else if (oilStock.get("2").toString().equals("1")) {
                    //更新状态位
                    oilPurchaseAcceptanceService.update(DeliveryNo, Oilcan, subMessage.getId());
                    log.info("状态为1" + DeliveryNo);
                } else if (oilStock.get("2").toString().equals("3")) {
                    //更新单号
                    String manualno = oilStock.get("3").toString();//新出库单号
                    oilPurchaseAcceptanceService.updateNo(DeliveryNo, manualno);
                    log.info("状态为3" + DeliveryNo);
                }
                List list=new ArrayList();
                list.add(DeliveryNo);
                GasMsg gss = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(),list, Constants.PID_Code.A15_10005.toString());
                log.info("return   " + gss);
                ctx.writeAndFlush(gss);
            }
        }
//endregion


        //region TODO  A15_10006	液位仪设置
        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10006.toString())) {
            log.info("setting atg ~~~~~");
            ResultMsg liquiDleveMessage = new JsonMapper().fromJson(gasMessage.getMessage(), ResultMsg.class);
            List list = liquiDleveMessage.getData();
            for (int i = 0; i < list.size(); i++) {
                atg_init_in_t init_in_t = new atg_init_in_t();
                Map map = (Map) list.get(i);
                SysManageIquidInstrument sysManageIquidInstrument = (SysManageIquidInstrument) mapToObject(SysManageIquidInstrument.class, map);
                log.info("map trans to sysManageIquidInstrument:" + sysManageIquidInstrument);
                init_in_t.strDeviceType = sysManageIquidInstrument.getMactype();//设备型号
//                init_in_t.strDeviceType = "1";
                init_in_t.uConnMode = "网口".equals(sysManageIquidInstrument.getCommtype()) ? 1 : 0;//通讯模式（1串口，2网口）
                if (sysManageIquidInstrument.getSerialport() != null && !"".equals(sysManageIquidInstrument.getSerialport())) {
                    init_in_t.strSerialAddress = sysManageIquidInstrument.getSerialport();//串口地址
                }
                init_in_t.strSerialBaudRate = sysManageIquidInstrument.getBaudrate1();//波特率
                init_in_t.strSerialStopBit = sysManageIquidInstrument.getStopsite();//停止位
                if (sysManageIquidInstrument.getChecksite() != null && "".equals(sysManageIquidInstrument.getChecksite())) {
                    init_in_t.strSerialCheckBit = sysManageIquidInstrument.getChecksite().substring(0, 1);//检验位
                } else {
                    init_in_t.strSerialCheckBit = "n";
                }
                init_in_t.strSerialDataBit = sysManageIquidInstrument.getDatasite();//数据位
                init_in_t.strIPAddress = sysManageIquidInstrument.getIpaddress();//ip
                init_in_t.strIPPort = sysManageIquidInstrument.getIpport();//端口
                log.info("begin atg~~~sysManageIquidInstrument:" + sysManageIquidInstrument);
                log.info("init_in_t.strDeviceType" + init_in_t.strDeviceType);
                log.info("init_in_t.uConnMode" + init_in_t.uConnMode);
                log.info("init_in_t.strSerialAddress" + init_in_t.strSerialAddress);
                log.info("init_in_t.strSerialBaudRate" + init_in_t.strSerialBaudRate);
                log.info("init_in_t.strSerialStopBit" + init_in_t.strSerialStopBit);
                log.info("init_in_t.strSerialCheckBit" + init_in_t.strSerialCheckBit);
                log.info("init_in_t.strSerialDataBit" + init_in_t.strSerialDataBit);
                log.info("init_in_t.strIPAddress" + init_in_t.strIPAddress);
                log.info("init_in_t.strIPPort" + init_in_t.strIPPort);
                init_in_t.strLogPath = "/smc20/gsm/logs/ATG/";
                int ret = ATGManager.init(init_in_t);

                if (ret == 0) {
                    GasMsg appSetLiquidlevelMessage = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), new ArrayList(), Constants.PID_Code.A15_10006.toString());
                    ctx.writeAndFlush(appSetLiquidlevelMessage);
                } else if (ret == 1) {
                    GasMsg appSetLiquidlevelMessage = ResultUtils.getInstance().sendFAIL(subMessage.getId(), new ArrayList(), Constants.PID_Code.A15_10006.toString());
                    ctx.writeAndFlush(appSetLiquidlevelMessage);
                }
            }
        }

        //endregion


        //region TODO  A15_10007	探棒校正参数设置
        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10007.toString())) {
            log.info("探棒校正参数设置...");
            ResultMsg liquiDleveMessage = new JsonMapper().fromJson(gasMessage.getMessage(), ResultMsg.class);
            List list = liquiDleveMessage.getData();
            GasMsg appSetProbeMessage = new GasMsg();
            int ret = setprobetimeout(list);
            if (ret == 0) {
                appSetProbeMessage = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), new ArrayList(), Constants.PID_Code.A15_10007.toString());
            } else{
                appSetProbeMessage = ResultUtils.getInstance().sendFAIL(subMessage.getId(), new ArrayList(), Constants.PID_Code.A15_10007.toString());
            }
            ctx.writeAndFlush(appSetProbeMessage);
            return;
        }
//endregion


        //region TODO   A15_10008  探棒与油罐的关系
        /*if (gasMessage.getPid().equals(Constants.PID_Code.A15_10008.toString())) {
            ResultMsg probeTankResultMsg = new JsonMapper().fromJson(gasMessage.getMessage(), ResultMsg.class);
            GasMsg appSetProbeTankMessage = new GasMsg();
            List<Map<String,Object>> probeTankList = probeTankResultMsg.getData();
            for (int i=0;i<probeTankList.size();i++) {
                Map<String,Object> map = probeTankList.get(i);
                SysManagePaTRelation sysManagePaTRelation = (SysManagePaTRelation)mapToObject(SysManagePaTRelation.class,map);
                atg_probecan_data_in_t data_in_t = new atg_probecan_data_in_t();
                data_in_t.strProbeNo = sysManagePaTRelation.getProbemodel();//探棒编号
                data_in_t.uOilCanNo = sysManagePaTRelation.getOilcan();//油罐编号
                data_in_t.strDeviceModel = sysManagePaTRelation.getDreviceModel();//设备型号
                data_in_t.uProbePort = sysManagePaTRelation.getuProbePort();//探棒端口
                data_in_t.strOilNo = sysManagePaTRelation.getOilno();//油品编码
                data_in_t.uOilType = sysManagePaTRelation.getStrOilType();//油品类型
                data_in_t.strOilName = sysManagePaTRelation.getStrOilName();//油品名称
                List list = new ArrayList();
                list.add(data_in_t);
                int ret = ATGManager.setProbe(list);
                if (ret == 0) {
                    appSetProbeTankMessage = ResultUtils.getInstance().sendSUCCESS("127.0.0.1", new ArrayList(), "A15_10008");
                } else if (i == ret) {
                    appSetProbeTankMessage = ResultUtils.getInstance().sendFAIL("127.0.0.1", new ArrayList(), "A15_10008");
                }
            }
            ctx.writeAndFlush(appSetProbeTankMessage);
        }*/
        //endregion

        //region TODO  A15_10009	容积表<上传\下发>

        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10009.toString())) {
            //1： 上传(从液位仪读出数据，存入Mysql库)oilcaninfor
            //2： 下发(从Mysql库中读取出来，提交给液位仪)11
            ResultMsg capacityResultMsg = new JsonMapper().fromJson(gasMessage.getMessage(), ResultMsg.class);//获取消息中的操作类型
            ////System.out.println("到了判断里面：" + capacityResultMsg);
            ////System.out.println("ID:" + capacityResultMsg.getId());
            //首先拿到data的时候要遍历里面所有的容积对象  循环的时候创建新的液位仪接收对象，因为液位仪接收的参数是对象而不是List
            List capacityList = capacityResultMsg.getData();
            for (int i = 0; i < capacityList.size(); i++) {
                ////System.out.println("第" + i + "个：" + capacityResultMsg.getData().get(i));
                Map map = (Map) capacityResultMsg.getData().get(i);
                int flag = Integer.valueOf(map.get("operation").toString());
                List<SysManageCubageInfo> sysManageCubageInfoList = new ArrayList<SysManageCubageInfo>();
                if (flag == 1) {//下发
                    // 液位仪接口参数 -  对象
                    atg_capacity_data_in_t capacity = new atg_capacity_data_in_t();
                    capacity.uOilCanNO = Integer.valueOf(map.get("oilNo").toString());
                    capacity.uCapacitySize = Integer.valueOf(map.get("operation").toString());
                    capacity.strVersion = map.get("strVersion").toString();
                    //查询容积明细表
                    SysManageCubageInfo info = new SysManageCubageInfo();
                    info.setVersion(capacity.strVersion);
                    info.setOilcan(capacity.uOilCanNO);
                    SysCubageService sysCubageService = (SysCubageService) (Context.getInstance().getBean("sysCubageService"));
                    sysManageCubageInfoList = sysCubageService.selectCubageInfo(info);

                    //todo 创建对象 →list
                    List<atg_capacitytable_data_in_t> capacitytableList = new ArrayList<atg_capacitytable_data_in_t>();
                    for (SysManageCubageInfo sysManageCubageInfo : sysManageCubageInfoList) {
                        atg_capacitytable_data_in_t capacitytable = new atg_capacitytable_data_in_t();
                        capacitytable.uHigh = (int) Double.parseDouble(sysManageCubageInfo.getHeight().toString());
                        sysManageCubageInfo.setHeight(sysManageCubageInfo.getHeight());
                        capacitytable.fLiter = Double.parseDouble(sysManageCubageInfo.getLiter().toString());
                        sysManageCubageInfo.setLiter(sysManageCubageInfo.getLiter());
                        capacitytableList.add(capacitytable);
                    }
                    capacity.pCapacityTableData = capacitytableList;

                    //写入缓存
                    EhCacheHelper.updateCubageInfo(sysManageCubageInfoList);
                    ResultMsg resultMsg = new ResultMsg();
                    resultMsg.setId(Constants.PID_Code.A15_10009.toString());
                    resultMsg.setTime(new Date().toLocaleString());
                    resultMsg.setData(new ArrayList());
                    //region get iq
                    log.info("get iq");
                    SysManageIquidInstrumentDao sysManageIquidInstrumentDao = Context.getInstance().getBean(SysManageIquidInstrumentDao.class);
                    SysManageIquidInstrument sysManageIquidInstrument = sysManageIquidInstrumentDao.selectLast();
                    String result;
                    log.info("探棒工作模式："+sysManageIquidInstrument.getWorktype());
                    if (sysManageIquidInstrument.getWorktype().trim().equals("探棒直联")){
                        log.info("探棒直连模式不下发");
                        result="0";
                    }else{
                        result = ATGManager.setCapacityTable(capacity);
                    }
                    //endregion

                    if (!"0".equals(result)) {//不返回0则是失败
                        resultMsg.setResult("1");
                    } else {
                        resultMsg.setResult("0");
                    }

                    String tankJson = new JsonMapper().toJson(resultMsg);
                    GasMsg gasMsg = new GasMsg();
                    gasMsg.setPid(Constants.PID_Code.A15_10009.toString());
                    gasMsg.setMessage(tankJson);
                    ////System.out.println("返回的对象" + gasMsg);
                    ctx.writeAndFlush(gasMsg);
                } else if (flag == 0) {//上传
                    //todo 调用省中心接口
                    //调用方法，更新从省中心拿来的数据
                    SysManageDic dic = Context.getInstance().getBean(SysManageDic.class);
                    nodeInforDao = Context.getInstance().getBean(NodeInforDao.class);
                    List<NodeInfor> NodeInforList = nodeInforDao.selectNodeInfor();
                    //调用省中心接口
                    log.info("开始调用省中心接口...");
                    String ip = SysConfig.getCenterIP();
                    boolean ret = sysmanageService.GetCubgeByNodeNo(ip, NodeInforList.get(0).getNodeno());
                    log.info("省中心接口ret:" + ret);
                    ResultMsg resultMsg = new ResultMsg();
                    resultMsg.setId(Constants.PID_Code.A15_10009.toString());
                    resultMsg.setTime(new Date().toLocaleString());
                    resultMsg.setData(new ArrayList());
                    if (ret) {
                        resultMsg.setResult("1");
                    } else {
                        resultMsg.setResult("0");
                    }
                    String tankJson = new JsonMapper().toJson(resultMsg);
                    GasMsg gasMsg = new GasMsg();
                    gasMsg.setPid(Constants.PID_Code.A15_10009.toString());
                    gasMsg.setMessage(tankJson);
                    ctx.writeAndFlush(gasMsg);
                    /*// 存放油罐号
                    OilCanInforDao oilCanInforDao = Context.getInstance().getBean(OilCanInforDao.class);

                    List oilnoList = new ArrayList();
                    List<OilCanInfor> oilCanInforList = oilCanInforDao.selectOilCanInfor();
                    if (oilCanInforList.size() > 0) {
                        //todo 遍历所有的油罐号存到oilnoList中
                        for (OilCanInfor smtif : oilCanInforList) {
                            oilnoList.add(smtif.getOILCANNO());
                        }
                    }
                    //拿到所有的油罐号去调用液位仪接口,拿到液位仪信息 存入Mysql
                    List<atg_capacity_data_in_t> resultCaTabList = ATGManager.getCapacityTable(oilnoList);
                    for (atg_capacity_data_in_t cdit : resultCaTabList) {
                        int uOilCanNO = cdit.uOilCanNO;
                        SysManageIquidCubage sysManageCubage = new SysManageIquidCubage();
                        sysManageCubage.setOilcan(uOilCanNO);
                        sysManageCubage.setVersion(cdit.strVersion);
                        //TODO 持久层操作，Insert操作     //获取消息中的操作类型
                        SysManageIquidCubageDao sysManageIquidCubageDao = Context.getInstance().getBean(SysManageIquidCubageDao.class);
                        sysManageIquidCubageDao.insert(sysManageCubage);
                        List<atg_capacitytable_data_in_t> acditList = cdit.pCapacityTableData;
                        for (atg_capacitytable_data_in_t acdit : acditList) {
                            double auh = acdit.uHigh;
                            double fl = acdit.fLiter;
                            SysManageIquidCubageInfo sysManageCubageInfo = new SysManageIquidCubageInfo();
                            sysManageCubageInfo.setOilcan(uOilCanNO);
                            sysManageCubageInfo.setVersion(cdit.strVersion);
                            sysManageCubageInfo.setHeight(auh);
                            sysManageCubageInfo.setLiter(fl);
                            SysManageIquidCubageInfoDao sysManageIquidCubageInfoDao=Context.getInstance().getBean(SysManageIquidCubageInfoDao.class);
                            sysManageIquidCubageInfoDao.insertSelective(sysManageCubageInfo);
                        }
                        ResultMsg resultMsg = new ResultMsg();
                        resultMsg.setId(Constants.PID_Code.A15_10009.toString());
                        resultMsg.setTime(new Date().toLocaleString());
                        resultMsg.setData(new ArrayList());
                        resultMsg.setResult("1");
                        String tankJson = new JsonMapper().toJson(resultMsg);
                        GasMsg gasMsg = new GasMsg();
                        gasMsg.setPid(Constants.PID_Code.A15_10009.toString());
                        gasMsg.setMessage(tankJson);
                        ctx.writeAndFlush(gasMsg);
                    }*/
                }
            }

        }
//endregion
        //region TODO  A15_10010	预报警设置
        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10010.toString())) {
            //解析Message(JSON)为 实体，穿给液位仪
            ////System.out.println("SysManageAlarmParameter：：" + subMessage.getData().toString());
            List list = subMessage.getData();
            if (list.size() < 1) {
                ResultMsg resultMsg1 = new ResultMsg();
                resultMsg1.setId(subMessage.getId());
                resultMsg1.setTime(new Date().toLocaleString());
                resultMsg1.setData(new ArrayList());
                resultMsg1.setResult("0");
                GasMsg gasMsg = new GasMsg();
                gasMsg.setPid(Constants.PID_Code.A15_10010.toString());
                String subJson = new JsonMapper().toJson(resultMsg1);
                gasMsg.setMessage(subJson);
                ctx.writeAndFlush(gasMsg);
                return;
            }
            ////System.out.println("--------------:" + list.get(0).toString());
            List<atg_setalarm_data_in_t> atgSetalarmDataInTList = new ArrayList<atg_setalarm_data_in_t>();
            for (int i = 0; i < list.size(); i++) {
                Map<Object, Object> map = (Map) list.get(i);
                ////System.out.println(map.get("oilcan"));
                SysManageAlarmParameter sysManageAlarmParameter = ReflectUtils.getBean(map, SysManageAlarmParameter.class);
                ////System.out.println("设置预报警:" + sysManageAlarmParameter.getOilcan());
                //上面把APP传过来的JSON转成了实体，获取实体中对应的值赋到液位仪预报警对象中去
                atg_setalarm_data_in_t atg = new atg_setalarm_data_in_t();
                atg.uOilCanNO = sysManageAlarmParameter.getOilcan();//油罐编号
                atg.fLowWarning = sysManageAlarmParameter.getLowprealarm();////低液位预警
                atg.fLowAlarm = sysManageAlarmParameter.getLowalarm();//低液位报警
                atg.fHighWarning = sysManageAlarmParameter.getHighprealarm();//高液位预警
                atg.fHighAlarm = sysManageAlarmParameter.getHighalarm();//高液位报警
                atg.fWaterAlarm = sysManageAlarmParameter.getWateralarm();//水杂报警
                atg.fWaterWarning = sysManageAlarmParameter.getWaterhightalarm();//高高水位报警
                atg.fHighTempAlarm = sysManageAlarmParameter.getHightempalarm();//高温报警
                atg.fLowTempAlarm = sysManageAlarmParameter.getLowtempalarm();//低温报警
                atg.fThiefAlarm = sysManageAlarmParameter.getStealoilalarm();//盗油报警
                atg.fLeakAlarm = sysManageAlarmParameter.getLeakageoilalarm();//漏油报警
                atg.fPercolatingAlarm = sysManageAlarmParameter.getLeakoilalarm();//渗漏报警
                //lastoptime;//上次设置时间
                //optime;//操作时间
                //transtatus;//传输状态
                atgSetalarmDataInTList.add(atg);
                ////System.out.println("得到的对象" + sysManageAlarmParameter.getOilcan());
            }
            String result = ATGManager.alarmSetter(atgSetalarmDataInTList);
            //TODO 根据液位仪返回结果创建返回对象
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setId(subMessage.getId());
            resultMsg.setTime(new Date().toLocaleString());
            resultMsg.setData(new ArrayList());
            if ("0".equals(result)) {
                resultMsg.setResult("0");
            } else {
                resultMsg.setResult("1");
            }
            String subJson = new JsonMapper().toJson(resultMsg);
            GasMsg gasMsg = new GasMsg();
            gasMsg.setPid(Constants.PID_Code.A15_10010.toString());
            gasMsg.setMessage(subJson);
            ////System.out.println("<" + gasMsg.toString() + ">");
            ctx.writeAndFlush(gasMsg);

        }

        //endregion

        //region TODO A15_10011 罐枪基础数据同步
        if (gasMessage.getPid().equals(Constants.PID_Code.A15_10011.toString())) {
            log.info("begin info sync");
            TankGunBasedDataService tankGunBasedDataService = Context.getInstance().getBean(TankGunBasedDataService.class);
            //SysManageAlarmParameterDao sysManageAlarmParameterDao = Context.getInstance().getBean(SysManageAlarmParameterDao.class);
            int tankGunBase = tankGunBasedDataService.saveTankGunBasedData(subMessage.getId());
            log.info("end info sync");
            List arrayList = new ArrayList();
            if (tankGunBase == 1) {
                arrayList.add("1");
                log.info("sync can gun info error ");
                GasMsg gss = ResultUtils.getInstance().sendFAIL(subMessage.getId(), arrayList, Constants.PID_Code.A15_10011.toString());
                ctx.writeAndFlush(gss);
            } else {
                arrayList.add("0");
                log.info("sync can gun info success");
                GasMsg gss = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), arrayList, Constants.PID_Code.A15_10011.toString());
                ctx.writeAndFlush(gss);
            }

        }
        //endregion

        //TODO 启动静态液位异常测试
        if (gasMessage.getPid().equals("A15_10012")) {
           /* List<Map> list = subMessage.getData();
            log.info("启动静态液位异常测试...list.size():"+list.size());
            Map map = list.get(0);
            List<atg_startliquid_data_in_t> startliquidDataInTList = new ArrayList<atg_startliquid_data_in_t>();
            atg_startliquid_data_in_t startliquidDataInT = new atg_startliquid_data_in_t();
            startliquidDataInT.uTestType = 0;
            startliquidDataInT.uOilCanNo = (Integer) map.get("oilno");//油罐号
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
           startliquidDataInT.strDataTime = sd.format(date);
           // startliquidDataInT.strDataTime = "20160930125750";
            startliquidDataInT.uTestDuration = 2;
            startliquidDataInT.fTestRate = 0.36;
            startliquidDataInTList.add(startliquidDataInT);
            log.info("startliquidDataInT.uOilCanNo:" + startliquidDataInT.uOilCanNo);
            ATGManager.startLiquid(startliquidDataInTList);
            List list1=new ArrayList();
            list1.add(startliquidDataInT.uOilCanNo);
            list1.add(sd.format(date));
            //Element element = EhCacheHelper.getAllCanStock();
            List<atg_stock_data_out_t> atgStockDataOutTList = (ArrayList<atg_stock_data_out_t>)ATGManager.getStock(new ArrayList());//(ArrayList<atg_stock_data_out_t>)element.getObjectValue();
            atg_stock_data_out_t atgStockDataOutT = new atg_stock_data_out_t();
            for(atg_stock_data_out_t a :atgStockDataOutTList){
                if(a.uOilCanNo ==startliquidDataInT.uOilCanNo){
                    atgStockDataOutT = a;
                    break;
                }
            }
            list1.add(atgStockDataOutT.fOilStandCubage);
            list1.add(atgStockDataOutT.fWaterBulk);*/
            List<atg_stock_data_out_t> list1 = startTestThread();
            if (list1 != null) {
                GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(gasMessage.getPid(), list1, Constants.PID_Code.A15_10012.toString());
                ctx.writeAndFlush(gasMsg);
            } else {
                GasMsg gasMsg = ResultUtils.getInstance().sendFAIL(gasMessage.getPid(), new ArrayList(), Constants.PID_Code.A15_10012.toString());
                ctx.writeAndFlush(gasMsg);
            }

        }
        //TODO 停止静态液位异常测试 A15_10013
        if (gasMessage.getPid().equals("A15_10013")) {

            //System.out.println("进入停止静态液位异常测试主调度…………………………");
           /* List<Map> list = subMessage.getData();

            Map map = list.get(0);
            List<atg_stopliquid_data_in_t> stopliquidDataInTList = new ArrayList<atg_stopliquid_data_in_t>();
            atg_stopliquid_data_in_t stopliquidDataInT = new atg_stopliquid_data_in_t();
            stopliquidDataInT.uTestType = 0;
            stopliquidDataInT.uOilCanNo = (Integer) map.get("oilno");//油罐号
            stopliquidDataInTList.add(stopliquidDataInT);
            ////System.out.println("打印list…………………………" + stopliquidDataInTList.size());
            List<atg_liquidreport_data_out_t> liquidreportDataOutTList = ATGManager.stopLiquid(stopliquidDataInTList);
            //Element element = EhCacheHelper.getAllCanStock();
            List<atg_stock_data_out_t> atgStockDataOutTList =(ArrayList<atg_stock_data_out_t>)ATGManager.getStock(new ArrayList());// (ArrayList<atg_stock_data_out_t>)element.getObjectValue();
            atg_stock_data_out_t atgStockDataOutT = new atg_stock_data_out_t();
            for(atg_stock_data_out_t a :atgStockDataOutTList){
                if(a.uOilCanNo ==stopliquidDataInT.uOilCanNo){
                    atgStockDataOutT = a;
                    break;
                }
            }
            liquidreportDataOutTList.get(0).fEndOilCubage=atgStockDataOutT.fOilStandCubage;//结束油体积
            liquidreportDataOutTList.get(0).fEndWaterBulk=atgStockDataOutT.fWaterBulk;//结束水体积
            log.info("结束油体积liquidreportDataOutTList.get(0).fEndOilCubage:"+liquidreportDataOutTList.get(0).fEndOilCubage);

            log.info("结束水体积liquidreportDataOutTList.get(0).fEndWaterBulk:"+liquidreportDataOutTList.get(0).fEndWaterBulk);*/
            //System.out.println("打印liquidreportDataOutTList…………………………"+liquidreportDataOutTList.size());

            List<atg_liquidreport_data_out_t> liquidreportDataOutTList = endTestThread();

            if (liquidreportDataOutTList != null) {
                log.info("end test get result:"+liquidreportDataOutTList.size());
                GasMsg gss = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), liquidreportDataOutTList, Constants.PID_Code.A15_10013.toString());
                ctx.writeAndFlush(gss);
            } else {
                GasMsg gasMsg = ResultUtils.getInstance().sendFAIL(subMessage.getId(), new ArrayList(), Constants.PID_Code.A15_10013.toString());
                ctx.writeAndFlush(gasMsg);
            }

        }//TODO 静态液位异常测试报告 A15_10014
        if (gasMessage.getPid().equals("A15_10014")) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
            List<Map> list = subMessage.getData();
            Map map = list.get(0);
            List<atg_liquidreport_data_in_t> liquidreportDataInTList = new ArrayList<atg_liquidreport_data_in_t>();
            atg_liquidreport_data_in_t liquidreportDataInT = new atg_liquidreport_data_in_t();
            liquidreportDataInT.uTestType = 0;
            liquidreportDataInT.uOilCanNo = (Integer) map.get("oilno");//油罐号
            liquidreportDataInT.uReqCount = 1;
            liquidreportDataInT.strDataTime = sd.format(new Date());
            liquidreportDataInTList.add(liquidreportDataInT);
            List<atg_liquidreport_data_out_t> liquidreportDataOutTList = ATGManager.liquidReport(liquidreportDataInTList);
            GasMsg gss = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), liquidreportDataOutTList, Constants.PID_Code.A15_10014.toString());
            ctx.writeAndFlush(gss);
        }
        //TODO 设备基础信息 A15_10015
        if (gasMessage.getPid().equals("A15_10015")) {
            List arrayList = new ArrayList();
            GasMsg gss = new GasMsg();
            IDeviceInfoService deviceInfoService = Context.getInstance().getBean(IDeviceInfoService.class);
            try {
                if(deviceInfoService.DeviceInfoSave()) {
                    log.info("deviceInfoService.DeviceInfoSave success");
                    gss = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), arrayList, Constants.PID_Code.A15_10015.toString());
                }else {
                    log.info("deviceInfoService.DeviceInfoSave fail");
                    gss = ResultUtils.getInstance().sendFAIL(subMessage.getId(), arrayList, Constants.PID_Code.A15_10015.toString());
                }
            } catch (Exception e) {
                log.info("deviceInfoService.DeviceInfoSave error");
                gss = ResultUtils.getInstance().sendFAIL(subMessage.getId(), arrayList, Constants.PID_Code.A15_10015.toString());
                log.error("DeviceInfoPolling error" + e);
                e.printStackTrace();
            } finally {
                ctx.writeAndFlush(gss);
            }
        }
        //TODO 高升转换 A15_10016
        if (gasMessage.getPid().equals("A15_10016")) {
            log.info("进入A15_10016");
            GasMsg gss = new GasMsg();
            List<Map> list = subMessage.getData();
            List<atg_hightoliter_data_out_t> ret = new ArrayList<atg_hightoliter_data_out_t>();
            try {
                if(list.size()<1){
                    log.warn("传入A15_10016的参数为空！直接返回。");
                    gss = ResultUtils.getInstance().sendFAIL(subMessage.getId(), ret, Constants.PID_Code.A15_10016.toString());
                    ctx.writeAndFlush(gss);
                    return ;
                }
                Map map = list.get(0);
                //从缓存中取得容积表
                List<atg_capacitytable_data_in_t> capacitytableDataInTList = new ArrayList<atg_capacitytable_data_in_t>();
                List<SysManageCubageInfo> sysManageCubageInfoList = EhCacheHelper.getCubageInfo(Integer.parseInt(map.get("oilcan").toString()));
                for (SysManageCubageInfo sysManageCubageInfo : sysManageCubageInfoList) {
                    atg_capacitytable_data_in_t capacitytableDataInT = new atg_capacitytable_data_in_t();
                    capacitytableDataInT.uHigh = (int) (double) sysManageCubageInfo.getHeight();
                    capacitytableDataInT.fLiter = sysManageCubageInfo.getLiter();
                    capacitytableDataInTList.add(capacitytableDataInT);
                }
                atg_capacity_data_in_t capacityDataInT = new atg_capacity_data_in_t();
                capacityDataInT.uCapacitySize = capacitytableDataInTList.size();//容积表明细长度
                capacityDataInT.pCapacityTableData = capacitytableDataInTList;//容积表明细
                capacityDataInT.uOilCanNO = Integer.parseInt(map.get("oilcan").toString());
                List<atg_capacity_data_in_t> capacityDataInTList = new ArrayList<atg_capacity_data_in_t>();
                capacityDataInTList.add(capacityDataInT);//容积表明细放到容积表

                atg_hightoliter_in_t hightoliterInT = new atg_hightoliter_in_t();
                //手工录入水高
                hightoliterInT.fWaterHeight = Double.parseDouble(map.get("WaterHeight").toString());
                //手工录入油水总高
                hightoliterInT.fTotalHeight = Double.parseDouble(map.get("TotalHeight").toString());
                //手工录入平均温度
                hightoliterInT.fOilTemp =  Double.parseDouble(map.get("OilTemp").toString());
                hightoliterInT.fOilTemp1 =  Double.parseDouble(map.get("OilTemp").toString());
                hightoliterInT.fOilTemp2 =  Double.parseDouble(map.get("OilTemp").toString());
                hightoliterInT.fOilTemp3 =  Double.parseDouble(map.get("OilTemp").toString());
                hightoliterInT.fOilTemp4 =  Double.parseDouble(map.get("OilTemp").toString());
                hightoliterInT.fOilTemp5 =  Double.parseDouble(map.get("OilTemp").toString());
                hightoliterInT.uCount = capacityDataInTList.size();
                hightoliterInT.pCapacityData = capacityDataInTList;
                List<atg_hightoliter_in_t> hightoliterInTList = new ArrayList<atg_hightoliter_in_t>();
                hightoliterInTList.add(hightoliterInT);
                //开始高升转换
                log.info("A15_10016开始高升转换");
                ret = ATGManager.HightOLiter(hightoliterInTList);
                log.info("A15_10016结束高升转换");
                //返回给app
                gss = ResultUtils.getInstance().sendSUCCESS(subMessage.getId(), ret, Constants.PID_Code.A15_10016.toString());
                log.info("A15_10016高升转换return   " + gss);

            } catch (Exception e) {
                log.error("A15_10016 error");
                gss = ResultUtils.getInstance().sendFAIL(subMessage.getId(), ret, Constants.PID_Code.A15_10016.toString());
                log.error("A15_10016 error" + e);
                e.printStackTrace();
            } finally {
                ctx.writeAndFlush(gss);
            }
        }
    }

    private Integer setProbe(List list) {
        SysManageOilTypeDao sysManageOilTypeDao = Context.getInstance().getBean(SysManageOilTypeDao.class);
        SysManageCanInfoDao sysManageCanInfoDao = Context.getInstance().getBean(SysManageCanInfoDao.class);
        List<atg_correction_data_in_t> atgCorrectionDataInTList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map map = (Map) list.get(i);
            SysManageProbePar sysManageProbePar = (SysManageProbePar) mapToObject(SysManageProbePar.class, map);
            //根据油罐编号查询油品信息
//                SysManageOilType sysManageOilType = sysManageOilTypeDao.selectByPrimaryKey(sysManageProbePar.getOilno());
//                log.info("sysManageOilType:"+sysManageOilType);
            SysManageCanInfo sysManageCanInfo = sysManageCanInfoDao.selectByPrimaryKey(sysManageProbePar.getOilcan());
            log.info("sysManageProbePar.getOilcan():" + sysManageProbePar.getOilcan() + ",sysManageCanInfo:" + sysManageCanInfo);
            String oilName = sysManageOilTypeDao.selectOilNo(sysManageCanInfo.getOilno());
            log.info("sysManageCanInfo.getOilno():" + sysManageCanInfo.getOilno() + ",oilName:" + oilName);
            atg_correction_data_in_t data_in_t = new atg_correction_data_in_t();
            data_in_t.strDeviceModel = sysManageProbePar.getDevicemodel();
            data_in_t.strProbeNo = sysManageProbePar.getProbemodel();//探棒编号
            data_in_t.uOilTy = sysManageProbePar.getOilno(); //sysManageOilType.getOiltype();//油品类型
            data_in_t.fOilCorrection = sysManageProbePar.getOilzero();//    油位0点矫正（毫米）
            data_in_t.fWaterCorrection = sysManageProbePar.getWaterzero();//水位0点矫正（毫米）
            data_in_t.fProbeOffset = sysManageProbePar.getProbeskew();//探棒偏移（毫米）
            data_in_t.fTiltOffset = sysManageProbePar.getInclineskew();//倾斜偏移（毫米）
            data_in_t.fTemp1 = sysManageProbePar.getRealtemp1();//温度1实测值(℃)
            data_in_t.fProbeTemp1 = sysManageProbePar.getProrealtemp1();//温度1探棒测量值(℃)
            data_in_t.fTemp2 = sysManageProbePar.getRealtemp2();//温度2实测值(℃)
            data_in_t.fProbeTemp2 = sysManageProbePar.getProrealtemp2();//温度2探棒测量值(℃)
            data_in_t.fTemp3 = sysManageProbePar.getRealtemp3();///温度3实测值(℃)
            data_in_t.fProbeTemp3 = sysManageProbePar.getProrealtemp3();//温度4实测值(℃)
            data_in_t.fTemp4 = sysManageProbePar.getRealtemp4();//温度4实测值(℃)
            data_in_t.fProbeTemp4 = sysManageProbePar.getProrealtemp4();//温度4探棒测量值(℃)
            data_in_t.fTemp5 = sysManageProbePar.getRealtemp5();//温度5实测值(℃)
            data_in_t.fProbeTemp5 = sysManageProbePar.getProrealtemp5();//温度5探棒测量值(℃)
            data_in_t.fInitDesnsity = sysManageProbePar.getInitdesnsity();//初始密度(kg/m3)
            data_in_t.fInitHighDiff = sysManageProbePar.getInithighdiff();//初始高度差(mm)
            data_in_t.fCorrectionFactor = sysManageProbePar.getCorrectionfactor();//密度的修正系数
            atgCorrectionDataInTList.add(data_in_t);
            log.info("sysManageProbePar:" + sysManageProbePar);

            SysManagePaTRelation sysManagePaTRelation = (SysManagePaTRelation) mapToObject(SysManagePaTRelation.class, map);
            log.info("sysManagePaTRelation:" + sysManagePaTRelation);
            atg_probecan_data_in_t probecanDataInT = new atg_probecan_data_in_t();
            probecanDataInT.strProbeNo = sysManageProbePar.getProbemodel();//探棒编号
            probecanDataInT.uOilCanNo = sysManageProbePar.getOilcan();//油罐编号
            probecanDataInT.strDeviceModel = sysManageProbePar.getDevicemodel();//设备型号
            if (sysManagePaTRelation.getuProbePort() != null && !"".equals(sysManagePaTRelation.getuProbePort())) {
                probecanDataInT.uProbePort = sysManagePaTRelation.getuProbePort();//探棒端口
            } else {
                probecanDataInT.uProbePort = 0;
            }
            log.error("probeport===="+map.get("probeport"));
            probecanDataInT.uProbePort = (Integer)map.get("probeport");
            log.error("probecanDataInT.uProbePort===="+probecanDataInT.uProbePort);
            probecanDataInT.strOilNo = sysManageCanInfo.getOilno();//油品编码
            probecanDataInT.uOilType = sysManageProbePar.getOilno();//油品类型
//                probecanDataInT.uOilType = "0";//油品类型  测试使用
            probecanDataInT.strOilName = oilName;//油品名称
            log.info("probecanDataInT.strProbeNo:" + probecanDataInT.strProbeNo);
            log.info("probecanDataInT.uOilCanNo:" + probecanDataInT.uOilCanNo);
            log.info("probecanDataInT.uProbePort:" + probecanDataInT.uProbePort);
            log.info("probecanDataInT.strOilNo:" + probecanDataInT.strOilNo);
            log.info("probecanDataInT.uOilType:" + probecanDataInT.uOilType);
            log.info("probecanDataInT.strOilName:" + probecanDataInT.strOilName);
            List probecanlist = new ArrayList();
            probecanlist.add(probecanDataInT);
            log.info("probecanlist.size():" + probecanlist.size());
            int ret = ATGManager.setProbe(probecanlist);
            log.info("ATGManager.setProbe-->ret:" + ret);
            if(ret!=0){
                return ret;
            }
           // log.info("ATGManager.setProbe-->ret:" + ret);
        }
        log.info("atgCorrectionDataInTList.size():" + atgCorrectionDataInTList.size());
        int ret = ATGManager.setCorrection(atgCorrectionDataInTList);
        log.info("atgCorrectionDataInTList.ret:" + ret);
        return ret;
    }


    public int setprobetimeout(final List list){
        int result=0;
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<Integer> call = new Callable<Integer>() {
            public Integer call() throws Exception {
                try {
                    log.info("setprobe" + new Date().toString());
                    return setProbe(list);
                }catch (Exception e) {
                    log.error("setprobe exception" + new Date().toString());
                    return 1;
                }
            }
        };
        try {
            Future<Integer> future = exec.submit(call);
            // set  timeout to 10 seconds
            result = future.get(1000 * 10, TimeUnit.MILLISECONDS);
            log.info("set probe value from call is :" + result);
        } catch (TimeoutException ex) {
            result=1;
            log.error("===============set probe task time out===============\n"+new Date().toString());
        } catch (Exception e) {
            result=1;
            log.error("===============set probe Exception==============\n"+e.getMessage());
        }
        // close thread pool
        exec.shutdown();
        return result;
    }


    //TODO 链路断开了删除该 会话
    public void lostConnection(ChannelHandlerContext ctx) {

        if (appMapper.get(ctx) != null) {
            log.info("KILL APP CTX" + appMapper.get(ctx).getId());
            appMapper.remove(ctx);
        }
        if (Mapper.get(ctx) != null) {
            log.info("KILL DIT CTX" + Mapper.get(ctx).getId());
            Mapper.remove(ctx);
        }
    }

    /**
     * @param gasStationNo 索引
     * @param mapper       查找的mapper对象
     * @return
     */
    public ConnectionSession searchDitCtxByGasStationNo(String gasStationNo, Map mapper) {
        Iterator<Map.Entry<ChannelHandlerContext, ConnectionSession>> iterator = mapper.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ChannelHandlerContext, ConnectionSession> entry = iterator.next();
            if (entry.getValue().getId().equals(gasStationNo)) {
                log.info("---------------查到Map" + entry.getKey() + ":" + entry.getValue());
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 把Map<String,Object>处理成实体类
     *
     * @param clazz 想要的实体类
     * @param map   包含信息的Map对象
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object mapToObject(Class clazz, Map<String, Object> map) {
        if (null == map) {
            return null;
        }
        Field[] fields = clazz.getDeclaredFields(); //取到类下所有的属性，也就是变量名
        Field field;
        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            String fieldName = field.getName();
            //把属性的第一个字母处理成大写
            String stringLetter = fieldName.substring(0, 1).toUpperCase();
            //取得set方法名，比如setBbzt
            String setterName = "set" + stringLetter + fieldName.substring(1);
            //真正取得set方法。
            Method setMethod = null;
            Class fieldClass = field.getType();
            try {
                if (isHaveSuchMethod(clazz, setterName)) {
                    if (fieldClass == String.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, String.valueOf(map.get(fieldName)));//为其赋值
                    } else if (fieldClass == Integer.class || fieldClass == int.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Integer.parseInt(String.valueOf(map.get(fieldName))));//为其赋值
                    } else if (fieldClass == Boolean.class || fieldClass == boolean.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Boolean.getBoolean(String.valueOf(map.get(fieldName))));//为其赋值
                    } else if (fieldClass == Short.class || fieldClass == short.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Short.parseShort(String.valueOf(map.get(fieldName))));//为其赋值
                    } else if (fieldClass == Long.class || fieldClass == long.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Long.parseLong(String.valueOf(map.get(fieldName))));//为其赋值
                    } else if (fieldClass == Double.class || fieldClass == double.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Double.parseDouble(String.valueOf(map.get(fieldName))));//为其赋值
                    } else if (fieldClass == Float.class || fieldClass == float.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Float.parseFloat(String.valueOf(map.get(fieldName))));//为其赋值
                    } else if (fieldClass == BigInteger.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, BigInteger.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));//为其赋值
                    } else if (fieldClass == BigDecimal.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, BigDecimal.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));//为其赋值
                    } else if (fieldClass == Date.class) {
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        if (map.get(fieldName).getClass() == java.sql.Date.class) {
                            setMethod.invoke(o, new Date(((java.sql.Date) map.get(fieldName)).getTime()));//为其赋值
                        } else if (map.get(fieldName).getClass() == java.sql.Time.class) {
                            setMethod.invoke(o, new Date(((java.sql.Time) map.get(fieldName)).getTime()));//为其赋值
                        } else if (map.get(fieldName).getClass() == java.sql.Timestamp.class) {
                            setMethod.invoke(o, new Date(((java.sql.Timestamp) map.get(fieldName)).getTime()));//为其赋值
                        }
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return o;
    }

    /**
     * 判断某个类里是否有某个方法
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static boolean isHaveSuchMethod(Class<?> clazz, String methodName) {
        Method[] methodArray = clazz.getMethods();
        boolean result = false;
        if (null != methodArray) {
            for (int i = 0; i < methodArray.length; i++) {
                if (methodArray[i].getName().equals(methodName)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 启动静态液位异常测试
     */
    public List<atg_stock_data_out_t> startTest() {
        List list1 = null;
        try {
            List<Map> list = subMessage.getData();
            log.info("启动静态液位异常测试...list.size():" + list.size());
            Map map = list.get(0);
            List<atg_startliquid_data_in_t> startliquidDataInTList = new ArrayList<atg_startliquid_data_in_t>();
            atg_startliquid_data_in_t startliquidDataInT = new atg_startliquid_data_in_t();
            startliquidDataInT.uTestType = 0;
            startliquidDataInT.uOilCanNo = (Integer) map.get("oilno");//油罐号
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            startliquidDataInT.strDataTime = sd.format(date);
            // startliquidDataInT.strDataTime = "20160930125750";
            startliquidDataInT.uTestDuration = 2;
            startliquidDataInT.fTestRate = 0.36;
            startliquidDataInTList.add(startliquidDataInT);
            log.info("startliquidDataInT.uOilCanNo:" + startliquidDataInT.uOilCanNo);
            ATGManager.startLiquid(startliquidDataInTList);
            list1 = new ArrayList();
            list1.add(startliquidDataInT.uOilCanNo);
            list1.add(sd.format(date));
            //Element element = EhCacheHelper.getAllCanStock();
            List<atg_stock_data_out_t> atgStockDataOutTList = (ArrayList<atg_stock_data_out_t>) ATGManager.getStock(new ArrayList());//(ArrayList<atg_stock_data_out_t>)element.getObjectValue();
            atg_stock_data_out_t atgStockDataOutT = new atg_stock_data_out_t();
            for (atg_stock_data_out_t a : atgStockDataOutTList) {
                if (a.uOilCanNo == startliquidDataInT.uOilCanNo) {
                    atgStockDataOutT = a;
                    break;
                }
            }
            list1.add(atgStockDataOutT.fOilStandCubage);
            list1.add(atgStockDataOutT.fWaterBulk);
        } catch (Exception e) {

        }
        return list1;
    }

    public List<atg_stock_data_out_t> startTestThread() {
        List<atg_stock_data_out_t> lRet = null;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<List<atg_stock_data_out_t>> future =
                new FutureTask<List<atg_stock_data_out_t>>(new Callable<List<atg_stock_data_out_t>>() {//使用Callable接口作为构造参数
                    public List<atg_stock_data_out_t> call() {
                        try {
                            return startTest();
                        } catch (Exception e) {
                        }
                        return null;
                    }
                });
        executor.execute(future);
        //在这里可以做别的任何事情
        try {
            lRet = future.get(25000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果
        } catch (InterruptedException e) {
            future.cancel(true);
        } catch (ExecutionException e) {
            future.cancel(true);
        } catch (TimeoutException e) {
            future.cancel(true);
        } finally {
            executor.shutdown();
        }
        return lRet;
    }

    public List<atg_liquidreport_data_out_t> endTest() {
        List<atg_liquidreport_data_out_t> liquidreportDataOutTList = null;
        try {
            //System.out.println("进入停止静态液位异常测试主调度…………………………");
            List<Map> list = subMessage.getData();
            Map map = list.get(0);
            List<atg_stopliquid_data_in_t> stopliquidDataInTList = new ArrayList<atg_stopliquid_data_in_t>();
            atg_stopliquid_data_in_t stopliquidDataInT = new atg_stopliquid_data_in_t();
            stopliquidDataInT.uTestType = 0;
            stopliquidDataInT.uOilCanNo = (Integer) map.get("oilno");//油罐号
            stopliquidDataInTList.add(stopliquidDataInT);
            //System.out.println("打印list…………………………" + stopliquidDataInTList.size());
            liquidreportDataOutTList = ATGManager.stopLiquid(stopliquidDataInTList);
            //Element element = EhCacheHelper.getAllCanStock();
            List<atg_stock_data_out_t> atgStockDataOutTList = (ArrayList<atg_stock_data_out_t>) ATGManager.getStock(new ArrayList());// (ArrayList<atg_stock_data_out_t>)element.getObjectValue();
            atg_stock_data_out_t atgStockDataOutT = new atg_stock_data_out_t();
            for (atg_stock_data_out_t a : atgStockDataOutTList) {
                if (a.uOilCanNo == stopliquidDataInT.uOilCanNo) {
                    atgStockDataOutT = a;
                    break;
                }
            }
            liquidreportDataOutTList.get(0).fEndOilCubage = atgStockDataOutT.fOilStandCubage;//结束油体积
            liquidreportDataOutTList.get(0).fEndWaterBulk = atgStockDataOutT.fWaterBulk;//结束水体积
            log.info("结束油体积liquidreportDataOutTList.get(0).fEndOilCubage:" + liquidreportDataOutTList.get(0).fEndOilCubage);
            log.info("结束水体积liquidreportDataOutTList.get(0).fEndWaterBulk:" + liquidreportDataOutTList.get(0).fEndWaterBulk);
        } catch (Exception e) {

        }
        return liquidreportDataOutTList;
    }

    public List<atg_liquidreport_data_out_t> endTestThread() {
        List<atg_liquidreport_data_out_t> lRet = null;
        /*ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<List<atg_liquidreport_data_out_t>> future =
                new FutureTask<List<atg_liquidreport_data_out_t>>(new Callable<List<atg_liquidreport_data_out_t>>() {//使用Callable接口作为构造参数
                    public List<atg_liquidreport_data_out_t> call() {
                        try {
                            return endTest();
                        } catch (Exception e) {
                        }
                        return null;
                    }
                });
        executor.execute(future);*/

        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<List<atg_liquidreport_data_out_t>> call = new Callable<List<atg_liquidreport_data_out_t>>() {
            public List<atg_liquidreport_data_out_t> call() throws Exception {
                try {
                    log.info("=================== end test:" + new Date().toString() + "=================");
                        return endTest();
                }catch (Exception e){
                    log.info("================end test Callable Failed===========");
                    return null;
                }
            }
        };
        //在这里可以做别的任何事情
        try {
            Future<List<atg_liquidreport_data_out_t>> future = exec.submit(call);
            lRet = future.get(25000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果
        }catch (TimeoutException e) {
            log.error("=============== end test time out=============== " + new Date().toString());
        } catch (Exception e) {
            log.error(" end test failed to handle.");
        }
        exec.shutdown();
        log.info("end test finished!"+new Date().toString());
        return lRet;
    }
}
