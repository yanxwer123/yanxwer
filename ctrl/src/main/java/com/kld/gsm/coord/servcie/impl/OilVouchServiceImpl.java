package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.common.base.BaseServiceImpl;
import com.kld.gsm.ATG.domain.OilVouch;
import com.kld.gsm.coord.dao.OilVouchDao;
import com.kld.gsm.coord.servcie.IOilVouchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service
public class OilVouchServiceImpl extends BaseServiceImpl<OilVouch,Long> implements IOilVouchService {
    @Autowired
    private OilVouchDao oilVouchDao;
    @Override
    protected BaseDao<OilVouch, Long> getDao() {
        return this.oilVouchDao;
    }

    @Override
    public OilVouch getOilVouch(int MacNo, int GunNo, int TTC, String TakeDate) {
        Map sql_map = new HashMap();
        sql_map.put("MacNo",MacNo);
        sql_map.put("GunNo",GunNo);
        sql_map.put("TTC",TTC);
        sql_map.put("TakeDate", TakeDate);
        OilVouch oilVouch = oilVouchDao.getOilVouch(sql_map);
        return oilVouch;
    }

    @Override
    public List<OilVouch> selectByshift(String oilvoch) {
        return oilVouchDao.selectByshift(oilvoch);
    }
}
