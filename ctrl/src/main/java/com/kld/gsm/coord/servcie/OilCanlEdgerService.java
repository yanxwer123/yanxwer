package com.kld.gsm.coord.servcie;

import com.kld.gsm.ATG.domain.DailyOilTankRegister;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/8 20:30
 * @Description: 加油站油品分罐保管登记帐(oilcanledger)
 */
public interface OilCanlEdgerService {
    List<DailyOilTankRegister> findByDate(Date date);

}
