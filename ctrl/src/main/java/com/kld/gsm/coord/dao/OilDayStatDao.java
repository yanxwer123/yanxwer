package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.OilDayStat;
import com.kld.gsm.coord.mybatis.SybaseRepository;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/3 12:20
 * @Description:   成品油日结存报表oildaystat  -- 日结信息
 */
@SybaseRepository
public interface OilDayStatDao  {
    List<OilDayStat> findByDate(Date date);
    List<OilDayStat> findByDate1(@Param("sql")String sql);
}
