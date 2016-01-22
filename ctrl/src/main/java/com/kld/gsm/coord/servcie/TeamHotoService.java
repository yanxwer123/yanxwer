package com.kld.gsm.coord.servcie;

import com.kld.gsm.coord.domain.TeamHoto;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-07 18:22
 * @Description:
 */
public interface TeamHotoService {

    /**
     * 根据班次号查询  同步到Mysql
     *
     * @param vouchNo 班次号
     * @return
     */
    TeamHoto findByTeamVouchNo(String vouchNo);
}
