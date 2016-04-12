package com.kld.gsm.coord.dao;


import com.kld.gsm.coord.domain.OilCanIndeTail;
import com.kld.gsm.coord.mybatis.SybaseRepository;
import org.apache.ibatis.annotations.Param;

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
    int insertOilcanindetail1(@Param("sql")String sql);
    int updateOilcanindetail(String DeliveryNo);
    int updateOilcanindetail1(@Param("sql")String sql);
    List<OilCanIndeTail> selectByOilvoch(String oilvoch);
    int  updateManualno(HashMap map);
    int  updateManualno1(@Param("sql")String sql);
}
