package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.coord.dao.TeamHotoInforDao;
import com.kld.gsm.coord.domain.TeamHotoInfor;
import com.kld.gsm.coord.servcie.TeamHotoInforService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-05 17:02
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class TeamHotoInforImpl implements TeamHotoInforService {
    Logger LOGGER = Logger.getLogger(this.getClass());
    @Autowired
    private TeamHotoInforDao teamHotoInforDao;
    @Override
    public List<TeamHotoInfor> findByDayTime(Date date) {
//        List<TeamHotoInfor>  list =  teamHotoInforDao.findByDayTime(date);
        String sql = "select distinct teamvouchno from teamhotoinfor  where daytime='"+date+"'" +
                "    order by teamvouchno";
        LOGGER.info("TeamHotoInforImpl的sql:"+sql);
        List<TeamHotoInfor>  list =  teamHotoInforDao.findByDayTime1(sql);
        return list;
    }
}
