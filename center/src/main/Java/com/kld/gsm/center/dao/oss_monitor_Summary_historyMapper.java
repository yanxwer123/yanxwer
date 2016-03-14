package com.kld.gsm.center.dao;
import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_monitor_Summary;
import org.apache.ibatis.annotations.Param;

import com.kld.gsm.center.domain.oss_monitor_Summary_history;
import com.kld.gsm.center.domain.oss_monitor_Summary_historyKey;
import java.util.List;


@MysqlRepository
public interface oss_monitor_Summary_historyMapper {
    int deleteByPrimaryKey(oss_monitor_Summary_historyKey key);

    int insert(oss_monitor_Summary_history record);

    int insertlist(@Param("list")List<oss_monitor_Summary> oss_monitor_Summary_historyList);

    int insertSelective(oss_monitor_Summary_history record);

    oss_monitor_Summary_history selectByPrimaryKey(oss_monitor_Summary_historyKey key);

    int updateByPrimaryKeySelective(oss_monitor_Summary_history record);

    int updateByPrimaryKey(oss_monitor_Summary_history record);
}