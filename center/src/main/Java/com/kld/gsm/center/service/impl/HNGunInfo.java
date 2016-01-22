package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.HNGunInfoMapper;
import com.kld.gsm.center.service.HNGunInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xhz on 2015/11/26.
 */
@Service
public class HNGunInfo implements HNGunInfoService {
    @Resource
    private HNGunInfoMapper gunInfoMapper;
    public List<com.kld.gsm.center.domain.hn.HNGunInfo> GetGunInfo(String nodeno) {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("nodeno", nodeno);
        //取得返回的结果集
        List<com.kld.gsm.center.domain.hn.HNGunInfo> results =gunInfoMapper.getinfo(map);
        if (results.size()>0)
        return results;
        else
            return null;
    }
}
