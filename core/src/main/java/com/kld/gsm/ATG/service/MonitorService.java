package com.kld.gsm.ATG.service;


import com.kld.gsm.ATG.dao.oss_monitor_PumpMapper;
import com.kld.gsm.ATG.domain.oss_monitor_Pump;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/*
Created BY niyang
Created Date 2015/12/31
*/
public interface MonitorService {

int merge(oss_monitor_Pump record);

List<oss_monitor_Pump> selectAll();
}
