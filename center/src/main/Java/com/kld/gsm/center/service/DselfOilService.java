package com.kld.gsm.center.service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/7/22 14:09
 * @Description:
 */
public interface DselfOilService {

    int insert(HashMap map);

    List<HashMap<String,Object>> getSelfOilList(Integer intPage,Integer intPageSize,String cardNo);

    List<HashMap<String,Object>> getSelfOilAllList(String cardNo);

    int delete(String cardNo);
}
