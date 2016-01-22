package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.hn.HNGunInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/26.
 */
public interface HNGunInfoService {
    List<HNGunInfo> GetGunInfo(String nodeno);
}
