package com.kld.app.service.impl;

import com.kld.app.service.DailyOpoCountService;
import com.kld.gsm.ATG.dao.DailyOpoCountDao;
import com.kld.gsm.ATG.domain.DailyOpoCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/3/1  22:02
 * @Description:
 */
@Service
@SuppressWarnings("all")
public class DailyOpoCountImpl implements DailyOpoCountService {
    @Autowired
    private  DailyOpoCountDao dailyOpoCountDao;
    @Override
    public String findByShift(String shift) {

        return dailyOpoCountDao.findByShift(shift);
    }
}
