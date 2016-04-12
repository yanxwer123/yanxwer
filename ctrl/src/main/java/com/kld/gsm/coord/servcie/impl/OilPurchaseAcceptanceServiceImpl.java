package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.dao.AcceptanceDeliveryBillDao;
import com.kld.gsm.ATG.dao.AcceptanceNoBillsDao;
import com.kld.gsm.ATG.dao.AcceptanceOdRegisterDao;
import com.kld.gsm.ATG.dao.AcceptanceOdRegisterInfoDao;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.AcceptanceNoBills;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.coord.Constants;
import com.kld.gsm.coord.dao.AtgoilinDao;
import com.kld.gsm.coord.dao.BillInforDao;
import com.kld.gsm.coord.dao.InoilcheckbillDao;
import com.kld.gsm.coord.dao.OilcanindetailDao;
import com.kld.gsm.coord.domain.BillInfor;
import com.kld.gsm.coord.domain.InOilCheckBill;
import com.kld.gsm.coord.domain.OilCanIndeTail;
import com.kld.gsm.coord.servcie.OilPurchaseAcceptanceService;
import com.kld.gsm.coord.server.handler.ConnectionSession;
import com.kld.gsm.coord.server.handler.ProtocolProcessor;
import com.kld.gsm.util.JsonMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/11/12 10:52
 * @Description:
 */

@SuppressWarnings("all")
@Service
public class OilPurchaseAcceptanceServiceImpl implements OilPurchaseAcceptanceService {
    @Autowired
    AcceptanceOdRegisterInfoDao acceptanceOdRegisterInfoDao;
    @Autowired
    AcceptanceOdRegisterDao acceptanceOdRegisterDao;
    @Autowired
    AcceptanceDeliveryBillDao acceptanceDeliveryBillDao;
    @Autowired
    OilcanindetailDao oilcanindetailDao;
    @Autowired
    InoilcheckbillDao inoilcheckbillDao;
    @Autowired
    AtgoilinDao atgoilinDao;
    @Autowired
    BillInforDao billInforDao;
    @Autowired
    AcceptanceNoBillsDao acceptanceNoBillsDao;
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(OilPurchaseAcceptanceServiceImpl.class);
    @Override
    public int selectAndInsert(String DeliveryNo,int oprno,String id) {
            //油罐进油明细表(OILCANINDETAIL)数据 查询Mysql的卸油登记明细表
            //可能会卸入多个罐，卸油明细有多条。
            List<AcceptanceOdRegisterInfo> selectAcceptanceOdRegisterInfo =acceptanceOdRegisterInfoDao.selectById(DeliveryNo);
            if(null!=selectAcceptanceOdRegisterInfo&&selectAcceptanceOdRegisterInfo.size()>0){
                //System.out.println("插入卸油登记明细表开始");
                OilCanIndeTail oilCanIndeTail = new OilCanIndeTail();
                for(AcceptanceOdRegisterInfo item:selectAcceptanceOdRegisterInfo) {
                    selectAcceptanceOdRegisterInfo2oilCanIndeTail(id, item, oilCanIndeTail, oprno);
                }
                //System.out.println("插入卸油登记明细表成功结束");
            }else{
                //System.out.println("Mysql 卸油登记明细表中无此记录请核对或者忽略……DeliveryNo=" + DeliveryNo);
            }
            //进油核对表(INOILCHECKBILL)  组合--mysql 两站表：卸油登记表和出库单表
            //卸油登记表
            AcceptanceOdRegister selectAcceptanceOdRegister = acceptanceOdRegisterDao.selectByPrimaryKey(DeliveryNo);
            //出库单表
            AcceptanceDeliveryBills selectAcceptanceDeliveryBills = acceptanceDeliveryBillDao.selectByPrimaryKey(DeliveryNo);

            if(null!=selectAcceptanceOdRegister){
                if(null==selectAcceptanceDeliveryBills){
                    //System.out.println("xinjiancuixiang");
                    AcceptanceDeliveryBills acceptanceDeliveryBills=new AcceptanceDeliveryBills();
                    acceptanceDeliveryBills.setFromoildepot("");//Fromoildepot 出油油库
                    acceptanceDeliveryBills.setCarno("");//承运车号
                    selectAcceptanceDeliveryBills=acceptanceDeliveryBills;
                    //System.out.println("建立对象结束，赋值ok");

                }
                InOilCheckBill inOilCheckBill = new InOilCheckBill();
                //多个罐号如何处理是个问题？？？
                /*int oilcan=0;
                selectAcceptanceOdRegisterI2InOilCheckBill(id,oilcan, oprno, inOilCheckBill, selectAcceptanceDeliveryBills, selectAcceptanceOdRegister);*/
                //随便取一个给管控
                if(null!=selectAcceptanceOdRegisterInfo&&selectAcceptanceOdRegisterInfo.size()>0){
                    int oilcan=selectAcceptanceOdRegisterInfo.get(0).getOilcan();
                    selectAcceptanceOdRegisterI2InOilCheckBill(id,oilcan, oprno, inOilCheckBill, selectAcceptanceDeliveryBills, selectAcceptanceOdRegister);
                }else{
                    int oilcan=0;
                    selectAcceptanceOdRegisterI2InOilCheckBill(id,oilcan, oprno, inOilCheckBill, selectAcceptanceDeliveryBills, selectAcceptanceOdRegister);
                }
            }else{
                //System.out.println("Mysql卸油登记表或出库单表中无此记录请核对或者忽略……DeliveryNo=" + DeliveryNo);

            }
       return 0;
    }

    @Override
    public int update(String DeliveryNo, int Oilcan,String id) {
        try {
            //更新油罐进油明细表(OILCANINDETAIL)
            int update_Oilanindetail = oilcanindetailDao.updateOilcanindetail(DeliveryNo);
            System.err.println("更新油罐进油明细表成功");
        }catch (Exception e){
            System.err.println("更新油罐进油明细表失败………………");
            error(id);
        }
        try {
            //更新进油核对表(INOILCHECKBILL)
            int update_Inoilcheckbill = inoilcheckbillDao.updateInoilcheckbill(DeliveryNo);
            System.err.println("更新油罐进油明细表成功");
        }catch (Exception e){
            System.err.println("更新进油核对表失败………………");
            error(id);
        }
        return 0;
    }

    @Override
    public int updateNo(String DeliveryNo, String manualno) {
        try {
            HashMap map = new HashMap();
            map.put("DeliveryNo", DeliveryNo);
            map.put("manualno", manualno);
            //更新油罐进油明细表(OILCANINDETAIL)
            int update_Oilanindetail = oilcanindetailDao.updateManualno(map);
        }catch (Exception e){
            System.err.println("更新油罐进油明细表出库单号失败………………");

        }
        try {
            HashMap map1 = new HashMap();
            map1.put("DeliveryNo", DeliveryNo);
            map1.put("manualno", manualno);
            //更新进油核对表(INOILCHECKBILL)
            int update_Inoilcheckbill = inoilcheckbillDao.updateManualno(map1);
        }catch (Exception e){
            System.err.println("更新进油核对表出库单号失败………………");
        }
        return 0;

    }

    private void selectAcceptanceOdRegisterI2InOilCheckBill(String id,int oilCan, int oprno, InOilCheckBill inOilCheckBill, AcceptanceDeliveryBills selectAcceptanceDeliveryBills, AcceptanceOdRegister selectAcceptanceOdRegister){
        String typeno="03";
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        String nowdate=date.format(new Date());//截取时间的年月日，以yyyyMMdd输出
        String nowdate1=nowdate.substring(0,6);
        String vouchno=nowdate+"0001";
        List<BillInfor> billInforList=billInforDao.selectBycode(typeno);
        if(billInforList.size()!=0){
                for(BillInfor  billInfor:billInforList){
                    BillInfor uBillInfor=new BillInfor();
                    if(billInfor.getMaxvouchno().substring(0,8).equals(nowdate)){

                        if(Integer.parseInt(billInfor.getMaxvouchno().substring(6,12))>100000){
                            vouchno=nowdate1+String.valueOf((Integer.parseInt(billInfor.getMaxvouchno().substring(6,12))+1));
                        }else{
                            vouchno=nowdate1+"0"+String.valueOf((Integer.parseInt(billInfor.getMaxvouchno().substring(6,12))+1));
                        }
                        uBillInfor.setTypeno(typeno);
                        billInfor.setBillname("");
                        uBillInfor.setMaxvouchno(vouchno);
                    }else {
                        uBillInfor.setTypeno(typeno);
                        billInfor.setBillname("");
                        uBillInfor.setMaxvouchno(vouchno);
                    }
                    String sql = "update BillInfor set maxvouchno='"+uBillInfor.getMaxvouchno()
                            +"' where typeno='"+uBillInfor.getTypeno()+"'";
                    logger.info("billInforDao.updateBillInfor1的sql="+sql);
                    billInforDao.updateBillInfor1(sql);
                }
        }else {
            BillInfor billInfor=new BillInfor();
            billInfor.setTypeno("03");
            billInfor.setMaxvouchno(vouchno);
            billInfor.setBillname("");
            billInforDao.insertBillInfor1(billInfor.getInsertSql("billInfor"));
        }

        inOilCheckBill.setVouchno((null == vouchno || "".equals(vouchno)) ? "" : vouchno);
        inOilCheckBill.setOilno((null == selectAcceptanceOdRegister.getOilno() || "".equals(selectAcceptanceOdRegister.getOilno())) ? "" : selectAcceptanceOdRegister.getOilno());
        //inOilCheckBill.setTeamvouchno((null == selectAcceptanceOdRegister.getShift() || "".equals(selectAcceptanceOdRegister.getShift())) ? "" : selectAcceptanceOdRegister.getShift());
        inOilCheckBill.setGoodsbillno((null == selectAcceptanceOdRegister.getManualNo() || "".equals(selectAcceptanceOdRegister.getManualNo())) ? "" : selectAcceptanceOdRegister.getManualNo());
        inOilCheckBill.setOrigamount((null == selectAcceptanceOdRegister.getPlanl() || "".equals(selectAcceptanceOdRegister.getPlanl())) ? 0.0 : selectAcceptanceOdRegister.getPlanl());
        inOilCheckBill.setOrigstdliter((null == selectAcceptanceOdRegister.getPlanl() || "".equals(selectAcceptanceOdRegister.getPlanl())) ? 0.0 : selectAcceptanceOdRegister.getPlanl());
        inOilCheckBill.setOriglitramout((null == selectAcceptanceOdRegister.getPlanl() || "".equals(selectAcceptanceOdRegister.getPlanl())) ? 0.0 : selectAcceptanceOdRegister.getPlanl()); //原发升数
        inOilCheckBill.setPickupplace((null == selectAcceptanceDeliveryBills.getFromoildepot() || "".equals(selectAcceptanceDeliveryBills.getFromoildepot())) ? "" : selectAcceptanceDeliveryBills.getFromoildepot());
        inOilCheckBill.setChecamount((null == selectAcceptanceOdRegister.getRealreceivel() || "".equals(selectAcceptanceOdRegister.getRealreceivel())) ? 0.0 : selectAcceptanceOdRegister.getRealreceivel());
        inOilCheckBill.setCheccnttemper((null == selectAcceptanceOdRegister.getCantrucktemp() || "".equals(selectAcceptanceOdRegister.getCantrucktemp())) ? 0.0 : selectAcceptanceOdRegister.getCantrucktemp());
        inOilCheckBill.setChecoiwahigh((null == selectAcceptanceOdRegister.getHeighttotal() || "".equals(selectAcceptanceOdRegister.getHeighttotal())) ? 0.0 : selectAcceptanceOdRegister.getHeighttotal());
        inOilCheckBill.setChecairhigh((null == selectAcceptanceOdRegister.getHeightempey() || "".equals(selectAcceptanceOdRegister.getHeightempey())) ? 0.0 : selectAcceptanceOdRegister.getHeightempey());
        inOilCheckBill.setChecwatehigh((null == selectAcceptanceOdRegister.getHeightwater() || "".equals(selectAcceptanceOdRegister.getHeightwater())) ? 0.0 : selectAcceptanceOdRegister.getHeightwater());
        inOilCheckBill.setCheclitramount((null == selectAcceptanceOdRegister.getRealreceivel() || "".equals(selectAcceptanceOdRegister.getRealreceivel())) ? 0.0 : selectAcceptanceOdRegister.getRealreceivel());
        inOilCheckBill.setChecstdliter((null == selectAcceptanceOdRegister.getRealreceivel() || "".equals(selectAcceptanceOdRegister.getRealreceivel())) ? 0.0 : selectAcceptanceOdRegister.getRealreceivel());//验收标准升数  已有数据，！！！
        inOilCheckBill.setDeinvolu((null == selectAcceptanceOdRegister.getDischargeloss() || "".equals(selectAcceptanceOdRegister.getDischargeloss())) ? 0.0 : selectAcceptanceOdRegister.getDischargeloss());
        inOilCheckBill.setDeinanalyse((null == String.valueOf(selectAcceptanceOdRegister.getDischargerate()) || "".equals(String.valueOf(selectAcceptanceOdRegister.getDischargerate()))) ? "" : String.valueOf(selectAcceptanceOdRegister.getDischargerate()));
        inOilCheckBill.setShipdept("0");//承运单位
        inOilCheckBill.setChiptrucno((null == selectAcceptanceDeliveryBills.getCarno() || "".equals(selectAcceptanceDeliveryBills.getCarno())) ? "" : selectAcceptanceDeliveryBills.getCarno());
        inOilCheckBill.setVittaperson((null == String.valueOf(oprno) || "".equals(String.valueOf(oprno))) ? "" : String.valueOf(oprno));
        inOilCheckBill.setDriver("0");//驾驶员
        inOilCheckBill.setAcceptflag((null == String.valueOf(selectAcceptanceOdRegister.getIsdel()) || "".equals(String.valueOf(selectAcceptanceOdRegister.getIsdel()))) ? "" : String.valueOf(selectAcceptanceOdRegister.getIsdel()));
        inOilCheckBill.setOperatorcode(oprno);
        inOilCheckBill.setArrivetime(selectAcceptanceOdRegister.getBegintime());
        inOilCheckBill.setBillstatus("2");
        inOilCheckBill.setTransflag((null == selectAcceptanceOdRegister.getTranstatus() || "".equals(selectAcceptanceOdRegister.getTranstatus()))? "":selectAcceptanceOdRegister.getTranstatus());
        inOilCheckBill.setOilcanno(oilCan);
        //inOilCheckBill.setDayflag("0");

            //插入到进油核对表(INOILCHECKBILL)
        try {
            String sql = inOilCheckBill.getInsertSql("inoilcheckbill");
            int insert_Inoilcheckbill = inoilcheckbillDao.insertInoilcheckbill1(sql);
            //System.out.println("插入到进油核对表成功");
        }catch (Exception e){
            logger.error("插入到进油核对表失败："+e.getMessage());
            logger.error(inOilCheckBill.toString2());
            logger.error(selectAcceptanceOdRegister.toString());
        }


    }

    private void selectAcceptanceOdRegisterInfo2oilCanIndeTail(String id,AcceptanceOdRegisterInfo selectAcceptanceOdRegisterInfo, OilCanIndeTail oilCanIndeTail,int oprno){
        String typeno="02";
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        String nowdate=date.format(new Date());//截取时间的年月日，以yyyyMMdd输出
        String nowdate1=nowdate.substring(0, 6);
        String vouchno=nowdate+"0001";
        List<BillInfor> billInforList=billInforDao.selectBycode(typeno);
        if(billInforList.size()!=0){
            for(BillInfor  billInfor:billInforList){
                BillInfor uBillInfor=new BillInfor();
                if(billInfor.getMaxvouchno().substring(0,8).equals(nowdate)){
                    //System.out.println("打印出数据为"+billInfor.getMaxvouchno().toString());
                    if(Integer.parseInt(billInfor.getMaxvouchno().substring(6,12))>100000){
                        vouchno=nowdate1+String.valueOf((Integer.parseInt(billInfor.getMaxvouchno().substring(6,12))+1));
                    }else{
                        vouchno=nowdate1+"0"+String.valueOf((Integer.parseInt(billInfor.getMaxvouchno().substring(6,12))+1));
                    }

                    //System.out.println("计算结束,shujuwei"+vouchno);
                    uBillInfor.setTypeno(typeno);
                    billInfor.setBillname("");
                    uBillInfor.setMaxvouchno(vouchno);
                }else {
                    uBillInfor.setTypeno(typeno);
                    billInfor.setBillname("");
                    uBillInfor.setMaxvouchno(vouchno);
                }
                //System.out.println("zhixinng update");
                String sql = "update BillInfor set maxvouchno='"+uBillInfor.getMaxvouchno()
                        +"' where typeno='"+uBillInfor.getTypeno()+"'";
                logger.info("billInforDao.updateBillInfor1的sql="+sql);
                billInforDao.updateBillInfor1(sql);
                //System.out.println("zhixinng update over");

            }
        }else {
            BillInfor billInfor=new BillInfor();
            billInfor.setTypeno(typeno);
            billInfor.setMaxvouchno(vouchno);
            billInfor.setBillname("");
            billInforDao.insertBillInfor1(billInfor.getInsertSql("billInfor"));
        }
        //System.out.println("jinlaile kaishi，开始赋值了 ");
        oilCanIndeTail.setVouchno((null == vouchno || "".equals(vouchno)) ? "" : vouchno);
        oilCanIndeTail.setOilno((null == selectAcceptanceOdRegisterInfo.getOilno() || "".equals(selectAcceptanceOdRegisterInfo.getOilno())) ? "" : selectAcceptanceOdRegisterInfo.getOilno());
        oilCanIndeTail.setOilcanno((null == selectAcceptanceOdRegisterInfo.getOilcan() || "".equals(selectAcceptanceOdRegisterInfo.getOilcan())) ? 0 : selectAcceptanceOdRegisterInfo.getOilcan());
        //oilCanIndeTail.setTeamvouchno((null == selectAcceptanceOdRegisterInfo.getShift() || "".equals(selectAcceptanceOdRegisterInfo.getShift())) ? "" : selectAcceptanceOdRegisterInfo.getShift());
        oilCanIndeTail.setIngoodsdate(selectAcceptanceOdRegisterInfo.getBegintime());
        oilCanIndeTail.setIngoodsperson((null == String.valueOf(oprno) || "".equals(String.valueOf(oprno)) ? "" : String.valueOf(oprno)));
        oilCanIndeTail.setGoodsbillno((null == selectAcceptanceOdRegisterInfo.getManualNo() || "".equals(selectAcceptanceOdRegisterInfo.getManualNo())) ? "" : selectAcceptanceOdRegisterInfo.getManualNo());
        oilCanIndeTail.setInbefofootage((null == selectAcceptanceOdRegisterInfo.getBeginoilheight() || "".equals(selectAcceptanceOdRegisterInfo.getBeginoilheight())) ? 0 : selectAcceptanceOdRegisterInfo.getBeginoilheight());
        oilCanIndeTail.setInbefoliter((null == selectAcceptanceOdRegisterInfo.getBeginoill() || "".equals(selectAcceptanceOdRegisterInfo.getBeginoill())) ? 0 : selectAcceptanceOdRegisterInfo.getBeginoill());
        oilCanIndeTail.setInaftefootage((null == selectAcceptanceOdRegisterInfo.getEndoilheight() || "".equals(selectAcceptanceOdRegisterInfo.getEndoilheight())) ? 0 : selectAcceptanceOdRegisterInfo.getEndoilheight());
        oilCanIndeTail.setInafteliter((null == selectAcceptanceOdRegisterInfo.getEndoill() || "".equals(selectAcceptanceOdRegisterInfo.getEndoill())) ? 0 : selectAcceptanceOdRegisterInfo.getEndoill());
        if (selectAcceptanceOdRegisterInfo.getDuringsales()!=null){
            oilCanIndeTail.setInafteliter(oilCanIndeTail.getInafteliter()+selectAcceptanceOdRegisterInfo.getDuringsales());
        }
        oilCanIndeTail.setAccountdate(selectAcceptanceOdRegisterInfo.getCreatetime());
        oilCanIndeTail.setBillstatus(2);
        oilCanIndeTail.setTransflag((null == selectAcceptanceOdRegisterInfo.getTranstatus() || "".equals(selectAcceptanceOdRegisterInfo.getTranstatus())) ? "" : selectAcceptanceOdRegisterInfo.getTranstatus());
        //oilCanIndeTail.setDayflag("0");
        //System.out.println("jinlaile kaishi，赋值完了 ");
        try{
            //插入到油罐进油明细表(OILCANINDETAIL)
            String sql = oilCanIndeTail.getInsertSql("oilcanindetail");
            int insert_Oilanindetail = oilcanindetailDao.insertOilcanindetail1(sql);
            //System.out.println("插入到油罐进油明细表成功");
        }catch (Exception e){
            System.err.println("插入到油罐进油明细表失败………………");
            error(id);
        }
    }
    private void error(String id) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setId(id);
        resultMsg.setResult("1");
        List list = new ArrayList();
        Map map = new LinkedHashMap();
        map.put("error", "Mysql t Exception");
        list.add(map);
        resultMsg.setData(list);
        GasMsg gasMsg = new GasMsg();
        gasMsg.setPid(Constants.PID_Code.A15_10005.toString());
        gasMsg.setMessage(new JsonMapper().toJson(resultMsg));

        ConnectionSession connectionSession =  ProtocolProcessor.getInstance().searchDitCtxByGasStationNo(id,ProtocolProcessor.getInstance().appMapper);
        if(!connectionSession.equals("")&&connectionSession!=null){
            connectionSession.getCtx().writeAndFlush(gasMsg);
        }
        System.err.println("无应用程序，且操作失败。。。。。。id:["+id+"]  com.kld.gsm.syntocenter.socket["+connectionSession.getCtx()+"]");

    }


}

