package com.kld.gsm.syntocenter.service;

/*
Created BY niyang
Created Date 2015/11/18
*/
public interface synAlarm {
  int synDailyLost();
  int synShiftLost();
  int synInventory();
  int synGaTContrast();
  int synMeasureLeak();
  int synSaleOut();
  int synEquipment();
  int synOilInContrast();
}
