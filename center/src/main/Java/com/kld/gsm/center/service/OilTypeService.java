package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_sysmanage_oilType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fangzhun on 2015/12/4.
 */
public interface OilTypeService {
    List<HashMap<String,String>> selectOilType();
}
