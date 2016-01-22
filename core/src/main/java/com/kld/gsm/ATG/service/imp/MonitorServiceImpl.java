package com.kld.gsm.ATG.service.imp;

import com.kld.gsm.ATG.dao.oss_monitor_PumpMapper;
import com.kld.gsm.ATG.domain.oss_monitor_Pump;
import com.kld.gsm.ATG.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/12/31.
 */
/*
Created BY niyang
Created Date 2015/12/31
*/
@SuppressWarnings("all")
@Service
public class MonitorServiceImpl implements MonitorService {
    @Autowired
    private oss_monitor_PumpMapper pumpdao;
    @Override
    public int merge(oss_monitor_Pump record) {
        pumpdao.merge(record);
        return 0;
    }

    @Override
    public List<oss_monitor_Pump> selectAll() {
        return pumpdao.selectAll();
    }
}
