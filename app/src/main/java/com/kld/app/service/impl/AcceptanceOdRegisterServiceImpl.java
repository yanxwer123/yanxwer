package com.kld.app.service.impl;

import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.SysManageDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.gsm.ATG.dao.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SuppressWarnings("ALL")
@Service("acceptanceOdRegister")
public class AcceptanceOdRegisterServiceImpl implements IAcceptanceOdRegisterService {
    @Autowired
    private AcceptanceOdRegisterDao dao;
    @Autowired
    private AcceptanceOdRegisterInfoDao infoDao;
    @Autowired
    private AcceptanceDeliveryBillDao billDao;
    @Autowired
    private AcceptanceNoBillsDao noBillsDao;
    @Autowired
    private SysManageOilTypeDao oilTypeDao;
    @Autowired
    private SysManageDictDao sysManageDictDao;
    @Autowired
    private AlarmOilInContrastDao alarmOilInContrastDao;



    @Override
    public int deleteByPrimaryKey(String deliveryno) {
        return this.dao.deleteByPrimaryKey(deliveryno);
    }

    @Override
    public int insert(AcceptanceOdRegister record) {
        //////System.out.println( record.toString());
        if (null == dao.selectByPrimaryKey(record.getDeliveryno())){
            return this.dao.insert(record);
        }else{
            return 0;
        }
    }

    @Override
    public int insertSelective(AcceptanceOdRegister record) {
        return this.dao.insertSelective(record);
    }

    @Override
    public AcceptanceOdRegister selectByPrimaryKey(String deliveryno) {
        return this.dao.selectByPrimaryKey(deliveryno);
    }

    @Override
    public int updateByPrimaryKeySelective(AcceptanceOdRegister record) {
        return this.dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AcceptanceOdRegister record) {
        return this.dao.updateByPrimaryKey(record);
    }

    /**
     * @param oilno
     * @description 根据油品编码获取 无货单验收数据
     */
    @Override
    public List<AcceptanceOdRegister> selectWHDByoilno(String oilno) {

        return dao.selectWHDByoil(oilno);

    }

    @Override
    public List<HashMap<String, ?>> selectjhysbynoanddate(String no, Date st, Date et,String oilcan,String symbol,String dischargeRate) {
        Map params = new HashMap();
        params.put("deliveryNo", no);
        params.put("begin", st);
        params.put("end", et);
        params.put("oilcan", oilcan);
        params.put("symbol", symbol);
        params.put("dischargeRate", dischargeRate);
        return  dao.selectjhysbynoanddate(params);
    }

    /**
     * @param billno 手工单号
     * @description 作废进货单号 ，删除登记表，明细表记录，出库单状态还原,报警删除
     */
    @Transactional(rollbackFor = Exception.class)
    public int cancelAcceptOdreg(String manualno){

        AcceptanceOdRegister ac=dao.selectByPrimaryKey(manualno);

        //del mingxi
        infoDao.deleteByDeliveryNo(manualno);
        //del main
        dao.deleteByPrimaryKey(manualno);

        alarmOilInContrastDao.deleteByPrimaryKey(manualno);
        alarmOilInContrastDao.deleteByPrimaryKey(ac.getDeliveryno());
        //更新出库单状态 使用出库单号
        AcceptanceDeliveryBills bill= billDao.selectByPrimaryKey(ac.getDeliveryno());
        if (bill!=null){
            //update 出库单状态
            bill.setIscomplete("0");
            bill.setRelevancedelveryno(null);
            billDao.updateByPrimaryKey(bill);
        }
        //删除无货单 使用手工单号
        noBillsDao.deleteByPrimaryKey(ac.getManualNo());
        return 1;
    }

    /**
     * @decription 获取正在使用的油品列表
     */
    @Override
    public List<SysManageOilType> selectUseOilType() {
        return oilTypeDao.selectUse();
    }

    @Override
    public SysManageOilType selectOilType(String oilno) {
        return oilTypeDao.selectByPrimaryKey(oilno);
    }

    /**
     * 完成卸油获取 实际收油，损失率等
     *
     * @param yfss
     * @param billno
     */
    @Override
    public Map getodreglossrate(Double yfss, Double yfssv20,String billno) {

        Double syl=Double.parseDouble(sysManageDictDao.selectByCode("sylycsz").getValue());
        Map<String,Object> hm=new HashMap<String, Object>();
        hm.put("yfss",yfss);
        hm.put("yfssv20",yfssv20);
        hm.put("billno",billno);
        hm.put("syl",syl/100);
        return  dao.getOdregRate(hm);
    }


    @Override
    public AcceptanceNoBills selectNobill(String bill) {
       return noBillsDao.selectByPrimaryKey(bill);

    }

    @Override
    public int UpdateNobill(AcceptanceNoBills noBills) {
        return noBillsDao.updateByPrimaryKey(noBills);
    }
}
