package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.TeamHotoInfor;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-05 16:57
 * @Description:
 */
@SybaseRepository
public interface TeamHotoInforDao {
    /**
     * 根据日结时间查询
     *
     * @param date
     * @return
     */
    List<TeamHotoInfor> findByDayTime(Date date);
    List<TeamHotoInfor> findByDayTime1(String sql);

}
