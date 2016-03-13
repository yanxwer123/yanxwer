package com.kld.app.service;

import com.kld.gsm.ATG.domain.DailyOpoCount;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/3/1  22:01
 * @Description:
 */
public interface DailyOpoCountService {
    String findByShift(String shift);
}
