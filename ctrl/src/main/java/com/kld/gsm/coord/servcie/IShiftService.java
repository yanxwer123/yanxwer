package com.kld.gsm.coord.servcie;


import com.kld.gsm.coord.domain.Payoilstat;

import java.util.List;

/**
 * Created by yinzhiguang on 2015/11/8.
 */
public interface IShiftService {
    void saveShiftInfo (String shiftno);

    List<Payoilstat> selectDifferent();
}
