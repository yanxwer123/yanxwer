package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.hn.HNGunInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/25.
 */
@MysqlRepository
public interface HNGunInfoMapper {

    public List<HNGunInfo> getinfo(Map<String, Object> map);
}
