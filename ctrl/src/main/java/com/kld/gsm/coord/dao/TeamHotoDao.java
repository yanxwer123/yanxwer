package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.TeamHoto;
import com.kld.gsm.coord.mybatis.SybaseRepository;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-07 18:19
 * @Description:
 */
@SybaseRepository
public interface TeamHotoDao {

    /**
     * 根据班次号查询  同步到Mysql
     *
     * @param vouchNo 班次号
     * @return
     */
    TeamHoto findByTeamVouchNo(String vouchNo);
}
