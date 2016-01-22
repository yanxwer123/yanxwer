package com.kld.app.service.impl;

import com.kld.app.service.IAcceptanceOdRegisterInfoService;
import com.kld.gsm.ATG.dao.AcceptanceOdRegisterInfoDao;
import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;
import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfoKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SuppressWarnings("All")
@Service
public class AcceptanceOdRegisterInfoServiceImpl implements IAcceptanceOdRegisterInfoService {

    @Autowired
    private AcceptanceOdRegisterInfoDao dao;
    
    @Override
    public int deleteByPrimaryKey(AcceptanceOdRegisterInfoKey key) {
        return this.dao.deleteByPrimaryKey(key);
    }

    @Override
    public int insert(AcceptanceOdRegisterInfo record) {
        return this.dao.insert(record);
    }

    @Override
    public int insertSelective(AcceptanceOdRegisterInfo record) {
        return this.dao.insertSelective(record);
    }

    @Override
    public AcceptanceOdRegisterInfo selectByPrimaryKey(AcceptanceOdRegisterInfoKey key) {
        return this.dao.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(AcceptanceOdRegisterInfo record) {
        return this.dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AcceptanceOdRegisterInfo record) {
        return this.dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<AcceptanceOdRegisterInfo> selectByDeliveryNoDate(String deliveryNo, Date xyrqq, Date xyrqz) {
        Map params = new HashMap();
        params.put("manualNo", deliveryNo);
        params.put("begin", xyrqq);
        params.put("end", xyrqz);
        return this.dao.selectByDeliveryNoDate(params);
    }

    @Override
    public int merge(AcceptanceOdRegisterInfo record) {
        return dao.merge(record);
    }

    @Override
    public List<AcceptanceOdRegisterInfo> selectbydeliveryno(String deliveryno) {
        return  dao.selectBydeliveryno(deliveryno);
    }

    @Override
    public AcceptanceOdRegisterInfo selectendtime(String manualno) {
        return  dao.selectEnd(manualno);
    }

    @Override
    public AcceptanceOdRegisterInfo selectbegintime(String manualno) {
       return dao.selectBegin(manualno);
    }

    @Override
    public List<AcceptanceOdRegisterInfo> selecbycanno(Integer canno,Date st,Date et) {

        HashMap hm=new HashMap();
        hm.put("canno",canno);
        hm.put("sttime",st);
        hm.put("ettime",et);
        return dao.selectbycanno(hm);

    }
}
