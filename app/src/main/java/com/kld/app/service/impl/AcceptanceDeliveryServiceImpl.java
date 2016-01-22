package com.kld.app.service.impl;

import java.util.List;

import com.kld.gsm.ATG.dao.*;
import com.kld.gsm.ATG.domain.AcceptanceNoBills;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kld.app.service.IAcceptanceDeliveryService;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;

import javax.annotation.Resource;
@SuppressWarnings("all")
@Service
public class AcceptanceDeliveryServiceImpl implements IAcceptanceDeliveryService{
 /*   @Autowired
 private AcceptanceDeliveryDao dao;*/

    @Resource
    private AcceptanceDeliveryBillDao deliveryBillDao;

    @Resource
    private AcceptanceOdRegisterDao  acceptanceOdRegisterDao;

    @Resource
    private AcceptanceOdRegisterInfoDao acceptanceOdRegisterInfoDao;

    @Resource
    private AcceptanceNoBillsDao noBillsDao;

    @Override
    public int deleteByPrimaryKey(String deliveryno) {
        return this.deliveryBillDao.deleteByPrimaryKey(deliveryno);
    }

    @Override
    public int insert(AcceptanceDeliveryBills record) {
        return this.deliveryBillDao.insert(record);
    }

    @Override
    public int insertSelective(AcceptanceDeliveryBills record) {
        return this.deliveryBillDao.insertSelective(record);
    }

    @Override
    public AcceptanceDeliveryBills selectByPrimaryKey(String deliveryno) {
        return this.deliveryBillDao.selectByPrimaryKey(deliveryno);
    }

    @Override
    public int updateByPrimaryKeySelective(AcceptanceDeliveryBills record) {
        return this.deliveryBillDao.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKey(AcceptanceDeliveryBills record) {
        return this.deliveryBillDao.updateByPrimaryKey(record);
    }

    @Override
    public List<AcceptanceDeliveryBills> selectAll() {
        return this.deliveryBillDao.selectAll();
    }

    /**
     * @param iscomplete 是否完成状态
     * @description 根据完成状态获取本地出库单
     */
    @Override
    public List<AcceptanceDeliveryBills> getbillsfromLocalByIsComplete(String iscomplete) {
        return deliveryBillDao.selectByIsComplete(iscomplete);
    }

    /**
     * @param billno 出库单号
     * @description:废弃卸油登记单and明细
     */
    @Override
    public int deleteOdandOdinfos(String billno) {
        //del mingxi
        acceptanceOdRegisterInfoDao.selectBydeliveryno(billno);

        //del main
        acceptanceOdRegisterDao.deleteByPrimaryKey(billno);
        return 0;
    }

    /**
     * @param no
     * @description 根据主键获取无货单验收表
     */

    @Override
    public AcceptanceNoBills getNobillBykey(String no) {
           return noBillsDao.selectByPrimaryKey(no);
    }

    /**
     * @param OilNo
     * @description 根据油品编码获取无货单的验收登记表
     */
    @Override
    public List<AcceptanceOdRegister> getNobillOds(String OilNo) {
      return   acceptanceOdRegisterDao.selectWHDByoil(OilNo);

    }

    /**
     * 保存无货单记录
     *
     * @param noBill
     */
    @Override
    public int insertNobills(AcceptanceNoBills noBill) {
        noBillsDao.merge(noBill);
        return 1;
    }
}
