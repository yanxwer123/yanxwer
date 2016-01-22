package com.kld.gsm.coord.servcie.impl;


import com.kld.gsm.ATG.domain.DailyOilTankRegister;
import com.kld.gsm.coord.dao.OilCanlEdgerDao;
import com.kld.gsm.coord.servcie.OilCanlEdgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/8 20:31
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class OilCanlEdgerImpl implements OilCanlEdgerService {
    @Autowired
    private OilCanlEdgerDao oilCanlEdgerDao;

    @Override
    public List<DailyOilTankRegister> findByDate(Date date) {
        List<DailyOilTankRegister> list = oilCanlEdgerDao.findByDate(date);
        return list;
    }
}
