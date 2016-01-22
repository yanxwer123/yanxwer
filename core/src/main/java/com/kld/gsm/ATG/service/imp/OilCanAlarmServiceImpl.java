package com.kld.gsm.ATG.service.imp;

import com.kld.gsm.ATG.dao.AlarmInventoryDao;
import com.kld.gsm.ATG.domain.AlarmInventory;
import com.kld.gsm.ATG.service.OilCanAlarmService;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2015/11/25 15:19
 * @Description:
 */
@Service
public class OilCanAlarmServiceImpl implements OilCanAlarmService{
//    @Autowired
    private AlarmInventoryDao alarmInventoryDao;
    @Override
    public int insert(AlarmInventory parameter){
        return this.insert(parameter);
    }

    @Override
    public int updateByPrimaryKey(AlarmInventory record) {
        return 1;
    }

    @Override
    public AlarmInventory getByNo(int oilcan) {
        return this.getByNo(oilcan);
    }
}