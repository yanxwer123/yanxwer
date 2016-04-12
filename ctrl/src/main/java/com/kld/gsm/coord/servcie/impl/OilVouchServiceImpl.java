package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.common.base.BaseServiceImpl;
import com.kld.gsm.ATG.domain.OilVouch;
import com.kld.gsm.coord.dao.OilVouchDao;
import com.kld.gsm.coord.servcie.IOilVouchService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Service
public class OilVouchServiceImpl extends BaseServiceImpl<OilVouch,Long> implements IOilVouchService {
    Logger LOGGER = Logger.getLogger(this.getClass());
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
        String sql = "SELECT macno,ttc,cardno,machineoilno,oilno,takedate, " +
                " ctc,oilgunno,openo,liter,price,amount,balance,pumpno,tac,gmac,psam_tac, " +
                " psam_asn,terminalno,psam_ttc,moneysou,paymode,payunit,t_mac,accountdate, " +
                " tracode,gettime,key_version,key_index,compmatchflag,compno,backmatchflag, " +
                " paymatchflag,teamvouchno,transflag,teamhotoflag,billstatus " +
                " FROM oilvouch vouch " +
                " WHERE macno = '"+MacNo+"' AND oilgunno = '"+GunNo+"' AND ttc='"+TTC+"' " +
                " AND takedate = '"+TakeDate+"'";
        LOGGER.info("getOilVouch1的sql:"+sql);
        OilVouch oilVouch = oilVouchDao.getOilVouch1(sql);
        LOGGER.info("getOilVouch1的返回值:"+oilVouch);
        return oilVouch;
    }

    @Override
    public List<OilVouch> selectByshift(String oilvoch) {
        return oilVouchDao.selectByshift(oilvoch);
    }
}
