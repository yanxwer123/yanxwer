package com.kld.gsm.coord.dao;


import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.coord.domain.OprAuthority;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/27 17:14
 * @Description:
 */
@SybaseRepository
public interface OprAuthorityDao extends BaseDao<OprAuthority,Long> {
    //通过操作员编号查询
    List<OprAuthority> getByOprNo(int oprno);
}
