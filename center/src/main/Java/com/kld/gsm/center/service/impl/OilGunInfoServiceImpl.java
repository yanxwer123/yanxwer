package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_sysmanage_OilGunInfoMapper;
import com.kld.gsm.center.domain.GasGunManage;
import com.kld.gsm.center.domain.oss_sysmanage_OilGunInfo;
import com.kld.gsm.center.service.OilGunInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/1/14 17:55
 * @Description:
 */
@Service
public class OilGunInfoServiceImpl implements OilGunInfoService {
    @Autowired
    private oss_sysmanage_OilGunInfoMapper oss_sysmanage_oilGunInfoMapper;
    @Override
    public List<GasGunManage> findByOUCode(String oucode) {
        List<GasGunManage> gasGunManages = new ArrayList<GasGunManage>();
        List<oss_sysmanage_OilGunInfo> list = oss_sysmanage_oilGunInfoMapper.findByNodeNo(oucode);
        if(list.size()>0){
            GasGunManage gasGunManage ;
            for(oss_sysmanage_OilGunInfo oss_sysmanage_oilGunInfo :list) {
                gasGunManage = new GasGunManage();
                gasGunManage.setOilcan(oss_sysmanage_oilGunInfo.getOilcan());
                gasGunManage.setOilgunno(oss_sysmanage_oilGunInfo.getOilgun());
                String oilcan=String.valueOf(oss_sysmanage_oilGunInfo.getOilcan());
                String nodeno=oss_sysmanage_oilGunInfo.getNodeno();
                HashMap selectMap = new HashMap();
                selectMap.put("oilcan", oilcan);
                selectMap.put("nodeno", nodeno);
                String oiltype =  oss_sysmanage_oilGunInfoMapper.findoiltype(selectMap);
                gasGunManage.setOiltype(oiltype==null?"未匹配":oiltype);
                gasGunManages.add(gasGunManage);
            }
        }else {
            System.out.println("没有获取到");
        }

        return gasGunManages;
    }

    @Override
    public List<GasGunManage> findByOUCodePage(Map map) {

        int pageNo = Integer.parseInt(String.valueOf(map.get("intPage")));
        int pageSize = Integer.parseInt(String.valueOf(map.get("intPageSize")));

             pageNo = pageNo < 1 ? 1 : pageNo;
            int firstRow = (pageNo - 1) * pageSize;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
            hashMap.put("id",map.get("id"));
        List<GasGunManage> gasGunManages = new ArrayList<GasGunManage>();
        List<oss_sysmanage_OilGunInfo> list = oss_sysmanage_oilGunInfoMapper.findByNodeNoPage(hashMap);
        if (list.size() > 0) {
            GasGunManage gasGunManage;
            for (oss_sysmanage_OilGunInfo oss_sysmanage_oilGunInfo : list) {
                gasGunManage = new GasGunManage();
                gasGunManage.setOilcan(oss_sysmanage_oilGunInfo.getOilcan());
                gasGunManage.setOilgunno(oss_sysmanage_oilGunInfo.getOilgun());

                String oilcan=String.valueOf(oss_sysmanage_oilGunInfo.getOilcan());
                String nodeno=oss_sysmanage_oilGunInfo.getNodeno();
                HashMap selectMap = new HashMap();
                selectMap.put("oilcan", oilcan);
                selectMap.put("nodeno", nodeno);
                String oiltype = oss_sysmanage_oilGunInfoMapper.findoiltype(selectMap);
                gasGunManage.setOiltype(oiltype == null ? "未匹配" : oiltype);
                gasGunManages.add(gasGunManage);
            }
        } else {
            System.out.println("没有获取到");
        }

        return gasGunManages;
    }
}
