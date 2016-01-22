package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.HNGunInfoMapper;
import com.kld.gsm.center.dao.oss_sysmanage_oilTypeMapper;
import com.kld.gsm.center.domain.oss_sysmanage_oilType;
import com.kld.gsm.center.service.OilTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fangzhun on 2015/12/4.
 */
@Service("OilTypeService")
public class OilTypeServiceImpl  implements OilTypeService {

    @Resource
    private oss_sysmanage_oilTypeMapper oss_sysmanage_oilTypeMapper;
    @Override
    public List<HashMap<String,String>> selectOilType() {
        return oss_sysmanage_oilTypeMapper.selectOilType();
    }
}
