package com.kld.gsm.syntocenter.service.impl;

import com.kld.gsm.ATG.domain.AlarmDailyLost;
import com.kld.gsm.ATG.domain.MonitorInventory;
import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATG.domain.SysManageCubage;
import com.kld.gsm.syntocenter.server.ApplicationMain;
import com.kld.gsm.syntocenter.service.synMonitor;
import com.kld.gsm.syntocenter.util.action;
import com.kld.gsm.syntocenter.util.httpClient;
import com.kld.gsm.syntocenter.util.param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
Created BY xhz
Created Date 2015/11/19
*/
@SuppressWarnings("all")
@Service
public class synMonitorImpl implements synMonitor {
    //时点库存
    @Autowired
    private com.kld.gsm.ATG.dao.MonitorTimeInventoryDao monitorTimeInventoryDao;
    @Autowired
    private com.kld.gsm.ATG.dao.SysManageCubageDao sysManageCubageDao;
    public int synTimeInventory() {
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.TI.AddTimeInventory");
      /*  List<SysManageCubage> sysManageCubages=sysManageCubageDao.selectCubageInused();

        Map<String,String> map= new HashMap<String,String>();
        for (SysManageCubage item:sysManageCubages){
            map.put(item.getOilcan().toString(),item.getVersion());
        }*/

        //获取站级数据
        List<MonitorTimeInventory> monitorTimeInventories= monitorTimeInventoryDao.selectByTrans("0");
        if (monitorTimeInventories.isEmpty())return 1;
        Map<String,String> hm=new param().getparam();
        //System.out.println("nodono"+hm.get("NodeNo"));
        for (MonitorTimeInventory item:monitorTimeInventories){
            if (!ApplicationMain.canversion.isEmpty()&&ApplicationMain.canversion.get(item.getOilcan().toString())!=null)
                item.setVersion(ApplicationMain.canversion.get(item.getOilcan().toString()));
        }
        //
        httpClient client=new httpClient();
        try {
            client.request(path,monitorTimeInventories,hm);
            for (MonitorTimeInventory item:monitorTimeInventories){
                item.setTranstatus("1");
                monitorTimeInventoryDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;

    }

    @Autowired
    private com.kld.gsm.ATG.dao.MonitorInventoryDao monitorInventoryDao;
    @Override
    public int synInventory() {
      /*  action ac=new action();
        String path=ac.getUri("resource.services.TI.AddInventory");
        List<MonitorInventory> monitorInventories=monitorInventoryDao.selectByTrans("0");
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,monitorInventories,hm);
            for (MonitorInventory item:monitorInventories){
                item.setTranstatus("1");
                monitorInventoryDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;*/
        //获取action地址
        action ac=new action();
        String path=ac.getUri("resource.services.TI.InsertInventory");
        /*List<SysManageCubage> sysManageCubages=sysManageCubageDao.selectCubageInused();

        Map<String,String> map= new HashMap<String,String>();
        for (SysManageCubage item:sysManageCubages){
            map.put(item.getOilcan().toString(),item.getVersion());
        }
*/
        //获取站级数据
        List<MonitorInventory> monitorInventories= monitorInventoryDao.selectByTrans("0");
        if (monitorInventories.isEmpty())return 1;
        for (MonitorInventory item:monitorInventories){
            if (!ApplicationMain.canversion.isEmpty()&&ApplicationMain.canversion.get(item.getOilcanno().toString())!=null)
                item.setVersion(ApplicationMain.canversion.get(item.getOilcanno().toString()));
        }
        Map<String,String> hm=new param().getparam();
        //发送站级数据
        httpClient client=new httpClient();
        try {
            client.request(path,monitorInventories,hm);
            for (MonitorInventory item:monitorInventories){
                item.setTranstatus("1");
                monitorInventoryDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
