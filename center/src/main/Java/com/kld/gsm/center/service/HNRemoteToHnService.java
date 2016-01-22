package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.hn.HNRemote;

import java.util.List;

/**
 * Created by Administrator on 2015/11/26.
 */
public interface HNRemoteToHnService {
    List<HNRemote> GetRemotetoHnInfo(String nodeno);
}
