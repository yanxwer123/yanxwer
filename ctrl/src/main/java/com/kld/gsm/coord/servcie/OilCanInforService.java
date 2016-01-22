package com.kld.gsm.coord.servcie;

import com.kld.gsm.coord.domain.OilCanInfor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/5 16:52
 * @Description:
 */
public interface OilCanInforService {
    List<OilCanInfor> findByOilNo(String oilno);
    List<OilCanInfor> selectOilCanInfor();
}