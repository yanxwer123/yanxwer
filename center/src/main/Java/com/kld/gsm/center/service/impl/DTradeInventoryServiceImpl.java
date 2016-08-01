package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_daily_SelfOilMapper;
import com.kld.gsm.center.domain.oss_daily_SelfOil;
import com.kld.gsm.center.domain.oss_daily_TradeInventory;
import com.kld.gsm.center.service.DTradeInventoryService;
import com.kld.gsm.center.util.action;
import com.kld.gsm.center.util.getSelfOilUntil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_TradeInventoryMapper;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 交易库存表
 */
@Service("DTradeInventoryService")
public class DTradeInventoryServiceImpl implements DTradeInventoryService {

    @Resource
    private oss_daily_TradeInventoryMapper ossDailyTradeInventoryMapper;
    @Resource
    private oss_daily_SelfOilMapper ossDailySelfOilMapper;

    @Transactional(rollbackFor=Exception.class)
    public int AddTradeInventory(List<oss_daily_TradeInventory> oss_daily_tradeInventories) {

        //添加开关，配置文件控制,调用不同的接口
        action ac=new action();
        if(ac.getOpenSelfOilSet()=="0"){

            for (oss_daily_TradeInventory item:oss_daily_tradeInventories)
            {
                item.setCardno("");
                ossDailyTradeInventoryMapper.insert(item);
            }
        }else{
            //修改，添加和自用油表关联，增加卡号字段信息，除了自用油卡号以外，其他卡号均设置为null
            if(getSelfOilUntil.selfOils==null){
                getSelfOilUntil.selfOils= ossDailySelfOilMapper.selectId();
            }

            if(getSelfOilUntil.selfOils.size()!=0){
                List list=new ArrayList();
                for(oss_daily_SelfOil self:getSelfOilUntil.selfOils){
                    list.add(self.getCardNo());
                }
                for(oss_daily_TradeInventory item:oss_daily_tradeInventories)
                {
                    if(!list.contains(item.getCardno())){
                        item.setCardno(null);
                    }
                    ossDailyTradeInventoryMapper.insert(item);
                }

            }else{
                for (oss_daily_TradeInventory item:oss_daily_tradeInventories)
                {
                    item.setCardno("");
                    ossDailyTradeInventoryMapper.insert(item);
                }
            }
            return 2;
        }
        return 1;
    }

    @Override
    public int updateTradeInventory(List<oss_daily_TradeInventory> oss_daily_tradeInventories) {

        for (oss_daily_TradeInventory item:oss_daily_tradeInventories)
        {
            ossDailyTradeInventoryMapper.update(item);
        }
        return 1;
    }
}
