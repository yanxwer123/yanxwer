package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_daily_SelfOilMapper;
import com.kld.gsm.center.service.DselfOilService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/7/22 14:12
 * @Description:
 */
@Service
public class DselfOilServiceImpl implements DselfOilService {

    @Resource
    private  oss_daily_SelfOilMapper oss_daily_selfOilMapper;
    @Override
    public int insert(HashMap map) {

        return oss_daily_selfOilMapper.insert(map);
    }

    @Override
    public List<HashMap<String, Object>> getSelfOilList(Integer pageNo, Integer pageSize, String cardNo) {
        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;
            int firstRow = (pageNo - 1) * pageSize;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
            hashMap.put("cardNo",cardNo);

            return oss_daily_selfOilMapper.getSelfOilList(hashMap);
        }

        return null;
    }

    @Override
    public List<HashMap<String, Object>> getSelfOilAllList(String cardNo) {
        HashMap hashMap = new HashMap();
        hashMap.put("cardNo", cardNo);
        return oss_daily_selfOilMapper.getSelfOilAllList(hashMap);
    }

    @Override
    public int delete(String cardNo) {
        return oss_daily_selfOilMapper.del(cardNo);
    }
}
