package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.coord.dao.TeamHotoDao;
import com.kld.gsm.coord.domain.TeamHoto;
import com.kld.gsm.coord.servcie.TeamHotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-07 18:23
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class TeamHotoServiceImpl implements TeamHotoService {
    @Autowired
    private TeamHotoDao teamHotoDao;

    @Override
    public TeamHoto findByTeamVouchNo(String vouchNo) {
        TeamHoto teamHoto = teamHotoDao.findByTeamVouchNo(vouchNo);

        return teamHoto;
    }

    @Override
    public TeamHoto findByTeamVouchNo1(String sql) {
        TeamHoto teamHoto = teamHotoDao.findByTeamVouchNo1(sql);

        return teamHoto;
    }
}
