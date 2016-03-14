package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_daily_pumpDigitShift;
import com.kld.gsm.center.domain.oss_daily_tankshift;
import com.kld.gsm.center.service.DTankShiftService;
import com.kld.gsm.center.util.FormatUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_tankshiftMapper;
import javax.annotation.Resource;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 油罐交接表
 */
@Service("DTankShiftService")
public class DTankShiftServiceImpl implements DTankShiftService {
    @Resource
    private oss_daily_tankshiftMapper ossDailyTankshiftMapper;

   @Transactional(rollbackFor = Exception.class)
    public int AddTankShift(List<oss_daily_tankshift> oss_daily_tankshifts) {
       for (oss_daily_tankshift item:oss_daily_tankshifts)
       {
           ossDailyTankshiftMapper.merge(item);
       }
        return 1;
    }


    @Override
    public ResultMsg selectByShift(String shift,String oucode) {
        ResultMsg result=new ResultMsg();
        HashMap hashMap = new HashMap();
        hashMap.put("shift", shift);
        hashMap.put("oucode", oucode);

        List<HashMap<String,Object>> exchangeList=ossDailyTankshiftMapper.selectByShift(hashMap);
        List<oss_daily_tankshift> list=new ArrayList<oss_daily_tankshift>();
        oss_daily_tankshift model;
        for (HashMap<String,Object> item:exchangeList) {
            Double dKC=Double.parseDouble(item.get("ToOilL").toString())+Double.parseDouble(item.get("InOilL").toString()) - Double.parseDouble(item.get("SaleL").toString())-Double.parseDouble(item.get("HoOilL").toString());
             item.put("kc", FormatUtil.DoubleFormat(dKC,FormatUtil.DOUBLEKEEPDIGIT));
        /*    ob[i][9] = ;
            ob[i][10] = item.getTooill()+item.getInoill()-item.getSalel()-item.getHooill();*/
            Double dSh=0.0;
            if(Double.parseDouble(item.get("SaleL").toString())>0.000001)
            {
                dSh=(Double.parseDouble(item.get("ToOilL").toString())+Double.parseDouble(item.get("InOilL").toString())-Double.parseDouble(item.get("SaleL").toString())-Double.parseDouble(item.get("HoOilL").toString()))/Double.parseDouble(item.get("SaleL").toString())*100;
            }
             item.put("sh", FormatUtil.DoubleFormat(dSh,FormatUtil.DOUBLEKEEPDIGIT));
        }
        result.setResult(true);
        result.setData(exchangeList);
        result.setRows(exchangeList);
        result.setTotal(exchangeList.size());
        return result;
    }
}
