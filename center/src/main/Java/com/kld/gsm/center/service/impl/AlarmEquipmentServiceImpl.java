package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_alarm_Equipment;
import com.kld.gsm.center.service.AlarmEquipmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_alarm_EquipmentMapper;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 设备故障表
 */
@Service("AlarmEquipmentService")
public class AlarmEquipmentServiceImpl implements AlarmEquipmentService {
    @Resource
    private oss_alarm_EquipmentMapper ossAlarmEquipmentMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddEquipment(List<oss_alarm_Equipment> oss_alarm_equipments) {
        for (oss_alarm_Equipment item:oss_alarm_equipments)
        {
            ossAlarmEquipmentMapper.insert(item);
        }
        return 1;
    }

    @Override
    public List<HashMap<String, Object>> queryPrepayPageList(Integer pageNo, Integer pageSize, String FailureType, String StartAlarmTime, String EndAlarmTime,String oucode) {
        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;//三元表达式，如果pageNo小于1，pageNo值就为1 否则就是传递进来的pageNo
            //pageSize = pageSize < 1 ? SystemConstant.PAGESIZE:pageSize; //三元表达式 如果pageSize小于1，就去取枚举里面的pageSize,否则就是传递进来的pageSize
            int firstRow = (pageNo - 1) * pageSize;
      /*      if(prepay==null){
                prepay=new PrepayTicketTradeRecord();
            }
            prepay.setFirstRow(firstRow);
            prepay.setRowSize(pageSize);*/
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
            hashMap.put("failuretype", FailureType);
            hashMap.put("start", StartAlarmTime);
            hashMap.put("end", EndAlarmTime);
            if (oucode!=null && oucode!="") {
                hashMap.put("oucode", oucode + "%");
            }
            else
            {
                hashMap.put("oucode", oucode);
            }
            return ossAlarmEquipmentMapper.queryPrepayPageList(hashMap);
        }
        return  null;
    }

    @Override
    public int queryPrepayCount(String FailureType, String StartAlarmTime, String EndAlarmTime,String oucode) {
        HashMap hashMap = new HashMap();
        hashMap.put("failuretype", FailureType);
        hashMap.put("start", StartAlarmTime);
        hashMap.put("end", EndAlarmTime);
        hashMap.put("oucode",oucode);
        return ossAlarmEquipmentMapper.queryPrepayCount(hashMap);
    }

    @Override
    public List<HashMap<String, Object>> selectiq(HashMap hashmap) {
        List<HashMap<String,Object>> list= ossAlarmEquipmentMapper.selectiq(hashmap);
        for (HashMap<String,Object> hashMap:list) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime= sdf.format(hashMap.get("StartAlarmTime"));
            hashMap.remove("StartAlarmTime");
            hashMap.put("StartAlarmTime",datetime);
            datetime= sdf.format(hashMap.get("EndAlarmTime"));
            hashMap.remove("EndAlarmTime");
            hashMap.put("EndAlarmTime",datetime);
        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectEqbywhere(String start,String end,String oucode) {
        HashMap map=new HashMap();
        map.put("start",start);
        map.put("end",end);
        if(oucode!=null && oucode!="") {
            map.put("oucode", oucode + "%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossAlarmEquipmentMapper.selectEqbywhere(map);
    }
}
