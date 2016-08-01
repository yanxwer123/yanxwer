package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_daily_SelfOil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/7/20 10:09
 * @Description:
 */
@MysqlRepository
public interface oss_daily_SelfOilMapper {

    int insertAll(oss_daily_SelfOil item);

    List<oss_daily_SelfOil>  selectId();

    int insert(HashMap map);

    List<HashMap<String,Object>>  getSelfOilList(HashMap map);

    List<HashMap<String,Object>> getSelfOilAllList(HashMap map);

    int del(String cardNo);

}
