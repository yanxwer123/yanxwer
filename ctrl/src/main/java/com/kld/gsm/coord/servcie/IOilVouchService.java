package com.kld.gsm.coord.servcie;


import com.kld.gsm.ATG.common.base.BaseService;
import com.kld.gsm.ATG.domain.OilVouch;

import java.util.List;

public interface IOilVouchService extends BaseService<OilVouch,Long> {
    OilVouch getOilVouch(int MacNo, int GunNo, int TTC, String TakeDate);
        List<OilVouch>  selectByshift(String oilvoch);
}
