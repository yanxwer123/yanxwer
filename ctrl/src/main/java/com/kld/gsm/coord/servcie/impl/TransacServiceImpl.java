package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.DailyTradeAccountDao;
import com.kld.gsm.ATG.dao.DailyTradeInventoryDao;
import com.kld.gsm.ATG.dao.SysManageDictDao;
import com.kld.gsm.ATG.dao.SysManageOilGunInfoDao;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.MonitorService;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.MacLog.GunStatusEnum;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.MacLog.util.EhCacheHelper;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.coord.Constants;
import com.kld.gsm.coord.dao.OilVouchDao;
import com.kld.gsm.coord.dao.VouchStockDao;
import com.kld.gsm.coord.domain.VouchStock;
import com.kld.gsm.coord.servcie.ITransacService;
import com.kld.gsm.coord.server.handler.ConnectionSession;
import com.kld.gsm.coord.server.handler.ProtocolProcessor;
import com.kld.gsm.util.JsonMapper;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/10 15:40
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class TransacServiceImpl implements ITransacService {
    @Autowired
    private OilVouchDao oilVouchDao;
    @Autowired
    public DailyTradeAccountDao dailyTradeAccountDao;
    @Autowired
    private VouchStockDao vouchStockDao;
    @Autowired
    private DailyTradeInventoryDao dailyTradeInventoryDao;
    @Autowired
    private SysManageDictDao sysManageDictDao;
    @Autowired
    private SysManageOilGunInfoDao selectOilCanByMacNo;

    @Autowired
    private MonitorService monitorService;

    private static final Logger log = LoggerFactory.getLogger(TransacServiceImpl.class);
    @Override
    public void getInformation(int MacNo, int GunNo, int TTC, String TakeDate) {
        try {
            //1)查sybase交易数据 存mysql
            //1.1查询sybase交易数据
            log.info("==============================================");
            log.info("Begin TranscHandler  [" + MacNo + "]" + "[" + GunNo + "]" + "[" + TTC + "]" + "[" + TakeDate + "]");
            Map sql_map = new HashMap();
            sql_map.put("MacNo", MacNo);
            sql_map.put("GunNo", GunNo);
            sql_map.put("TTC",TTC);
            sql_map.put("TakeDate", TakeDate);
            log.info("oilVouchDao is null? " + oilVouchDao);
            OilVouch oilVouch = oilVouchDao.getOilVouch(sql_map);
            if(oilVouch==null)
            {
                log.error("获取交易数据失败.交易对象为空");
                return;
            }
            oilVouch.setMacno(MacNo);
            oilVouch.setOilgunno(GunNo+"");
            oilVouch.setTtc(TTC);
            oilVouch.setTakedate(TakeDate);
/*
             try {
                MacLogInfo macLogInfo = new MacLogInfo();
                macLogInfo.setGunNum((byte) Integer.parseInt(oilVouch.getOilgunno().trim()));
                macLogInfo.setAmount(BigDecimal.valueOf(oilVouch.getAmount()));
                macLogInfo.setQty(BigDecimal.valueOf(oilVouch.getLiter()));
                 macLogInfo.setPrice(BigDecimal.valueOf(oilVouch.getPrice()));
                macLogInfo.setGunStatus(GunStatusEnum.挂枪);

                 List list = new ArrayList();
                 list.add(macLogInfo);

                 Map map = ProtocolProcessor.getInstance().appMapper;
                 Iterator<Map.Entry<ChannelHandlerContext, ConnectionSession>> iterator = map.entrySet().iterator();
                 while (iterator.hasNext()) {
                     Map.Entry<ChannelHandlerContext, ConnectionSession> entry = iterator.next();
                     GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS("allRealtimedata", list, Constants.PID_Code.A15_10002.toString());
                     log.info("send all app maclog guaqiang GunStatusEnum.挂枪"+macLogInfo.toString());
                     entry.getValue().getCtx().writeAndFlush(gasMsg);
                 }
                log.info(macLogInfo.toString());
                //EhCacheHelper.updteCache(macLogInfo);
            }catch (Exception e){
                log.error("转换macinfo："+oilVouch.toString());
            }*/
            log.info("select * from oilvouch[result]:" + oilVouch.toString());
            //交易加油流水表oss_daily_TradeAccount
            DailyTradeAccount dailyTradeAccount = new DailyTradeAccount();
            //从sybase的交易表存到mysql的交易加油流水表
            oilVouch2dailyTradeAccount(oilVouch, dailyTradeAccount);
            //1.2保存mysql交易加油流水表oss_daily_TradeAccount
            log.info("pre insert[dailytradeaccount]:" + dailyTradeAccount.toString());
            log.info("dailyTradeAccount.getCardNo()"+dailyTradeAccount.getCardNo());
            log.info("dailyTradeAccount.getGetTime()"+dailyTradeAccount.getGetTime());
            log.info("dailyTradeAccount.getOilgun()"+dailyTradeAccount.getOilgun());
            log.info("dailyTradeAccount.getOilNo()"+dailyTradeAccount.getOilNo());
            if(dailyTradeAccountDao==null) {
                log.info("dailyTradeAccountDao is NULL~~~~~~~~");
            }else{
                log.info("dailyTradeAccountDao is NOT NULL~~~~~~~~");
            }
            int ret1 = dailyTradeAccountDao.insertSelective(dailyTradeAccount);
            log.info("----------------------------insert mysql ok");
            //查询是否需要读取液位仪
           SysManageDict sysManageDict = sysManageDictDao.selectByCode("sfdqywy");
           if("1".equals(sysManageDict.getValue())) {//如果返回值是1，则需要读取，否则不读取
                //1.3)查实时库存 存mysql-sybase
                List<Integer> oilCanNo = new ArrayList<Integer>();
                oilCanNo.add(selectOilCanByMacNo.selectOilCanByOilGun(GunNo));
                //根据罐号查询实时库存
                List<atg_stock_data_out_t> ret = ATGManager.getStock(oilCanNo);
               if (ret!=null) {
                   log.info("get ret:" + ret.size());
               }
                //mysql保存  交易库存表oss_daily_TradeInventory
                DailyTradeInventory dailyTradeInventory = new DailyTradeInventory();
                //给mysql交易库存表赋值
               if(ret!=null&&ret.size()>0) {
                   getDailyTradeInventory(oilVouch, ret, dailyTradeInventory);
               }else{
                   getDailyTradeInventory(oilVouch, ret, dailyTradeInventory,oilCanNo.get(0));
               }
                //保存mysql交易库存表
                dailyTradeInventoryDao.insert(dailyTradeInventory);

                //把读取液位仪的实时库存赋值到sybase的实时库存vouchStock
                VouchStock vouchStock = new VouchStock();
                //给sybase交易库存表赋值
               getVouchStock(oilVouch, ret, vouchStock);
               log.info("vouchStock:"+vouchStock);
                //sybase实时库存保存操作
                int ret_vouchStock = vouchStockDao.insert(vouchStock);
               log.info("ret_vouchStock :"+ret_vouchStock );
            }
            //2)通知app 交易数据
            GasMsg gasMessage = new GasMsg();
            gasMessage.setPid(Constants.PID_Code.A15_10003.toString());//交易明细及交易结束时液位仪信息
            String json = new JsonMapper().toJson(dailyTradeAccount);
            gasMessage.setMessage(json);
            log.info("send app :" + gasMessage.toString());
            Map map = ProtocolProcessor.getInstance().appMapper;
            if(map.size()>0){
                log.info("one com.kld.gsm.syntocenter.socket conn.....");
            }
            Iterator<Map.Entry<ChannelHandlerContext, ConnectionSession>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<ChannelHandlerContext, ConnectionSession> entry = iterator.next();
                entry.getValue().getCtx().writeAndFlush(gasMessage);
            }

        }catch (Exception e){
            log.error("dailyTradeAccountDao insert error"+e.getStackTrace(),e);
            e.printStackTrace();
        }
    }
    /**
     * 从sybase的交易表存到mysql的交易加油流水表
     * @param oilVouch
     * @param dailyTradeAccount
     * @return
     */
    private void oilVouch2dailyTradeAccount(OilVouch oilVouch,DailyTradeAccount dailyTradeAccount)throws Exception{
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dailyTradeAccount.setMacno(oilVouch.getMacno());
        dailyTradeAccount.setTtc(oilVouch.getTtc());
        dailyTradeAccount.setCardNo(oilVouch.getCardno());
        dailyTradeAccount.setMachineOilNo(oilVouch.getMachineoilno());
        dailyTradeAccount.setOilNo(oilVouch.getOilno());
        dailyTradeAccount.setTakedate(sd2.parse(oilVouch.getTakedate()));
        dailyTradeAccount.setCTC(oilVouch.getCtc());

        dailyTradeAccount.setOilgun(leftPad(oilVouch.getOilgunno()));
        dailyTradeAccount.setOpeNo(oilVouch.getOpeno());
        dailyTradeAccount.setLiter(oilVouch.getLiter());
        dailyTradeAccount.setPrice(oilVouch.getPrice());
        dailyTradeAccount.setAmount(oilVouch.getAmount());
        dailyTradeAccount.setBalance(oilVouch.getBalance());
        dailyTradeAccount.setPumpNo(oilVouch.getPumpno());
        dailyTradeAccount.setTAC(oilVouch.getTac());
        dailyTradeAccount.setGMAC(oilVouch.getGmac());
        dailyTradeAccount.setPSAM_TAC(oilVouch.getPsam_tac());
        dailyTradeAccount.setPSAM_ASN(oilVouch.getPsam_asn());
        dailyTradeAccount.setTempinalNo(oilVouch.getTerminalno());
        dailyTradeAccount.setPSAM_TTC(oilVouch.getPsam_ttc());
        dailyTradeAccount.setMoneySou(oilVouch.getMoneysou());
        dailyTradeAccount.setPayMode(oilVouch.getPaymode());
        dailyTradeAccount.setPayUnit(oilVouch.getPayunit());
        dailyTradeAccount.setT_MAC(oilVouch.getT_mac());
        if(oilVouch.getAccountdate()!=null){
            dailyTradeAccount.setAccountDate(sd.parse(oilVouch.getAccountdate()));
        }
        //加油卡号 1000514400001542554  如果第56位是51，那么是回罐的
        if("51".equals(oilVouch.getCardno().substring(4, 6))) {
            dailyTradeAccount.setBackcanflag("1");
        }
        dailyTradeAccount.setTraCode(oilVouch.getTracode());
        dailyTradeAccount.setGetTime(sd.parse(oilVouch.getGettime()));
        dailyTradeAccount.setKey_Version(oilVouch.getKey_version());
        dailyTradeAccount.setKey_Index(oilVouch.getKey_index());
        dailyTradeAccount.setCompMatchFlag(oilVouch.getCompmatchflag() + "");
        dailyTradeAccount.setCompNo((null == oilVouch.getCompno() || "".equals(oilVouch.getCompno()) ? "" : oilVouch.getCompno()));
        dailyTradeAccount.setBackMatchFlag(oilVouch.getBackmatchflag() + "");
        dailyTradeAccount.setPayTypeFlag(oilVouch.getPaymatchflag() + "");
        dailyTradeAccount.setShift((null == oilVouch.getTeamvouchno() || "".equals(oilVouch.getTeamvouchno())?"":oilVouch.getTeamvouchno()));
        dailyTradeAccount.setTransFlag("0");
        dailyTradeAccount.setIsRecieved("0");
        //泵码数
        //dailyTradeAccount.setPumpNo(oilVouch.getPumpno());
        byte bGunNo=Byte.parseByte(oilVouch.getOilgunno());
        //region泵码数持久化
        try {
            oss_monitor_Pump pump=new oss_monitor_Pump();
            pump.setGunno((int)bGunNo);
            pump.setPump(oilVouch.getPumpno());
            pump.setModifydate(new Date());
            monitorService.merge(pump);
            log.info((int)bGunNo+" 枪,持久化:"+oilVouch.getPumpno());
        }catch (Exception e){
            log.error((int)bGunNo+" 枪,持久化:"+oilVouch.getPumpno()+",失败。");
        }

        //endregion
        EhCacheHelper.updteCache(bGunNo, oilVouch.getPumpno());
    }

    private void getDailyTradeInventory(OilVouch oilVouch,List<atg_stock_data_out_t> ret,DailyTradeInventory dailyTradeInventory)throws Exception{
        log.info("oilVouch"+oilVouch);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sd3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyyMMddHHmmss");
        if(ret!=null&&ret.size()>0) {
            for (atg_stock_data_out_t out : ret) {
                DecimalFormat decimalFormat = new DecimalFormat("######0.00");
                dailyTradeInventory.setMacno(oilVouch.getMacno());//油机编号
                dailyTradeInventory.setTtc(oilVouch.getTtc());//交易序号
                dailyTradeInventory.setTakedate(sd3.parse(oilVouch.getTakedate()));//交易时间
                dailyTradeInventory.setOilgun(leftPad(oilVouch.getOilgunno()));//油枪编号
                dailyTradeInventory.setOilcan(out.uOilCanNo);//油罐编号
                dailyTradeInventory.setOilno(oilVouch.getOilno());//油品编码
                try {
                    //dailyTradeInventory.setOpetime(sd3.parse(oilVouch.getGettime()));//采集时间
                }catch (Exception e){
                    //dailyTradeInventory.setOpetime(sd2.parse(out.strDate + out.strTime));//采集时间
                }
                dailyTradeInventory.setOpetime(sd2.parse(out.strDate + out.strTime));//采集时间
                dailyTradeInventory.setStockdate(out.strDate);//日期
                dailyTradeInventory.setStocktime(out.strTime);//时间
                dailyTradeInventory.setOill(out.fOilCubage);//净油体积
                dailyTradeInventory.setStandardl(Double.valueOf(decimalFormat.format(out.fOilStandCubage)));//标准体积
                dailyTradeInventory.setEmptyl(out.fEmptyCubage);//空体积
                dailyTradeInventory.setHeighttotal(out.fTotalHeight);//油水总高
                dailyTradeInventory.setHeightwater(out.fWaterHeight);//水高
                dailyTradeInventory.setOiltemp(out.fOilTemp);//油温
                dailyTradeInventory.setWaterl(out.fWaterBulk);//水体积
                dailyTradeInventory.setDensity(out.fApparentDensity);//视密度
                dailyTradeInventory.setDensitystandard(Double.valueOf(decimalFormat.format(out.fStandDensity)));//标准密度
                //处理空班次
                if(oilVouch.getTeamvouchno()==null) {
                    dailyTradeInventory.setShift("");//班次号
                }
                else
                {
                    //+""会存为NULL
                    dailyTradeInventory.setShift(oilVouch.getTeamvouchno() + "");//班次号
                }
                dailyTradeInventory.setTranstatus("0");
                //加油卡号 1000514400001542554  如果第56位是51，那么是回罐的
                if("51".equals(oilVouch.getCardno().substring(4, 6))) {
                    dailyTradeInventory.setBackcanflag("1");
                }
            }
        }
    }

    private void getDailyTradeInventory(OilVouch oilVouch,List<atg_stock_data_out_t> ret,DailyTradeInventory dailyTradeInventory,int oilcanno)throws Exception{
        log.info("getDailyTradeInventory have no stockinfo,oilVouch"+oilVouch);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sd3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        SimpleDateFormat sdDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdMinute = new SimpleDateFormat("HHmmss");
        String d = sdDate.format(date);
        String m = sdMinute.format(date);
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        dailyTradeInventory.setMacno(oilVouch.getMacno());//油机编号
        dailyTradeInventory.setTtc(oilVouch.getTtc());//交易序号
        dailyTradeInventory.setTakedate(sd3.parse(oilVouch.getTakedate()));//交易时间
        dailyTradeInventory.setOilgun(leftPad(oilVouch.getOilgunno()));//油枪编号
        dailyTradeInventory.setOilcan(oilcanno);//油罐编号
        dailyTradeInventory.setOilno(oilVouch.getOilno());//油品编码
        dailyTradeInventory.setOpetime(date);//采集时间
        dailyTradeInventory.setStockdate(d);//日期
        dailyTradeInventory.setStocktime(m);//时间
        dailyTradeInventory.setOill(0.0);//净油体积
        dailyTradeInventory.setStandardl(0.0);//标准体积
        dailyTradeInventory.setEmptyl(0.0);//空体积
        dailyTradeInventory.setHeighttotal(0.0);//油水总高
        dailyTradeInventory.setHeightwater(0.0);//水高
        dailyTradeInventory.setOiltemp(0.0);//油温
        dailyTradeInventory.setWaterl(0.0);//水体积
        dailyTradeInventory.setDensity(0.0);//视密度
        dailyTradeInventory.setDensitystandard(0.0);//标准密度
        //处理空班次
        if(oilVouch.getTeamvouchno()==null) {
            dailyTradeInventory.setShift("");//班次号
        }
        else
        {
            //+""会存为NULL
            dailyTradeInventory.setShift(oilVouch.getTeamvouchno() + "");//班次号
        }
        dailyTradeInventory.setTranstatus("0");
        //加油卡号 1000514400001542554  如果第56位是51，那么是回罐的
        if("51".equals(oilVouch.getCardno().substring(4, 6))) {
            dailyTradeInventory.setBackcanflag("1");
        }
        log.info("getDailyTradeInventory have no stockinfo,dailyTradeInventory"+dailyTradeInventory);
    }

    private String leftPad(String gunno){
        String no=gunno;
        if (no.length()<3){
            for(int i=0;i<3-gunno.trim().length();i++){
                no="0"+no;
            }
        }
        return no;
    }

    private void getVouchStock(OilVouch oilVouch,List<atg_stock_data_out_t> ret,VouchStock vouchStock)throws Exception{
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sd3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyyMMddHHmmss");
        if(ret!=null&&ret.size()>0) {
            for (atg_stock_data_out_t out : ret) {
                vouchStock.setMacno(oilVouch.getMacno());//油机编号
                vouchStock.setTtc(oilVouch.getTtc());//交易序号
                vouchStock.setTakedate(sd3.parse(oilVouch.getTakedate()));//交易时间
                vouchStock.setOilgunno(leftPad(oilVouch.getOilgunno()));//油枪编号
                vouchStock.setOilcanno(out.uOilCanNo);//油罐编号
                vouchStock.setOilno(oilVouch.getOilno());//油品编码
                vouchStock.setOpetime(sd2.parse(out.strDate + out.strTime));//采集时间
                vouchStock.setStockdate(out.strDate);//日期
                vouchStock.setStocktime(out.strTime);//时间
                vouchStock.setOilcubage(out.fOilCubage);//净油体积
                vouchStock.setOilstandcubage(out.fOilStandCubage);//标准体积
                vouchStock.setEmptycubage(out.fEmptyCubage);//空体积
                vouchStock.setTotalheight(out.fTotalHeight);//油水总高
                vouchStock.setWaterheight(out.fWaterHeight);//水高
                vouchStock.setOiltemp(out.fOilTemp);//油温
                vouchStock.setWaterbulk(out.fWaterBulk);//水体积
                vouchStock.setApparentdensity(out.fApparentDensity);//视密度
                vouchStock.setStanddensity(out.fStandDensity);//标准密度
                vouchStock.setTeamvouchno((null == oilVouch.getTeamvouchno() || "".equals(oilVouch.getTeamvouchno())) ? "" : oilVouch.getTeamvouchno());//班次号
                vouchStock.setTranflag("");
                vouchStock.setRemark("");
            }
        }
    }
}
