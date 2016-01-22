package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.common.base.BaseServiceImpl;
import com.kld.gsm.coord.dao.VouchStockDao;
import com.kld.gsm.coord.domain.VouchStock;
import com.kld.gsm.coord.servcie.IVouchStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class VouchStockServiceImpl extends BaseServiceImpl<VouchStock,Long> implements IVouchStockService{

    @Autowired
    private VouchStockDao vouchStockDao;
    @Override
    protected BaseDao<VouchStock, Long> getDao() {
        return this.vouchStockDao;
    }

    @Override
    public int insert(VouchStock vouchStock) {
        return vouchStockDao.insert(vouchStock);
    }
}
