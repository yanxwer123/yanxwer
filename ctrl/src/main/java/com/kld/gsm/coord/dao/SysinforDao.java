package com.kld.gsm.coord.dao;


import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.coord.domain.Sysinfor;
import com.kld.gsm.coord.mybatis.SybaseRepository;
import org.apache.ibatis.annotations.Param;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/24 17:15
 * @Description查询系统信息表
 */
@SybaseRepository
public interface SysinforDao extends BaseDao<Sysinfor,Long> {
    //查询库中是否有数据
     Sysinfor getAll();
     Sysinfor getAll1(@Param("sql")String sql);

}

