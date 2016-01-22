package com.kld.gsm.syntocenter.service;


import com.kld.gsm.ATG.domain.MonitorOilgun;

import java.util.List;

/*
Created BY niyang
Created Date 2015/11/19
*/
public interface synPurchase {
    int GetDeilveByNodeNo();

    int insertlist(List<MonitorOilgun> guns);
}
