package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_alarm_OilInContrast;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface oss_alarm_OilInContrastMapper {
    int deleteByPrimaryKey(String deliveryno);

    int insert(oss_alarm_OilInContrast record);

    int insertSelective(oss_alarm_OilInContrast record);

    oss_alarm_OilInContrast selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(oss_alarm_OilInContrast record);

    int updateByPrimaryKey(oss_alarm_OilInContrast record);
    List<HashMap<String,Object>> selectoilincontrastbywhere(HashMap hashmap);
    List<HashMap<String,Object>> querypage(HashMap hashmap);
}