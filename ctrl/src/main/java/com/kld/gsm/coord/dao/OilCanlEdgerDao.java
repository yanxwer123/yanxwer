package com.kld.gsm.coord.dao;

import com.kld.gsm.ATG.domain.DailyOilTankRegister;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/8 20:29
 * @Description:
 */
@SybaseRepository
public interface OilCanlEdgerDao {
    List<DailyOilTankRegister> findByDate(Date date);

}
