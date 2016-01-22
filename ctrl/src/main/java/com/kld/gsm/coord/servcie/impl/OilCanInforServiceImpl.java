package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.coord.dao.OilCanInforDao;
import com.kld.gsm.coord.domain.OilCanInfor;
import com.kld.gsm.coord.servcie.OilCanInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/5 16:52
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class OilCanInforServiceImpl implements OilCanInforService {
    @Autowired
    private OilCanInforDao oilCanInforDao;


    @Override
    public List<OilCanInfor> findByOilNo(String oilno) {
        List<OilCanInfor> list = oilCanInforDao.findByOilNo(oilno);
        return list;
    }

    @Override
    public List<OilCanInfor> selectOilCanInfor() {
        List<OilCanInfor> list =oilCanInforDao.selectOilCanInfor();
        return list;
    }
}
