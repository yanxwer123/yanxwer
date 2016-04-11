package com.kld.gsm.coord.dao;


import com.kld.gsm.coord.domain.OilCanIndeTail;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/11/12 10:42
 * @Description:进油核对表数据插入与更新
 */
@SybaseRepository
public interface OilcanindetailDao {

    int insertOilcanindetail(OilCanIndeTail oilCanIndeTail);
    int insertOilcanindetail1(String sql);
    int updateOilcanindetail(String DeliveryNo);
    List<OilCanIndeTail> selectByOilvoch(String oilvoch);
    int  updateManualno(HashMap map);
}
