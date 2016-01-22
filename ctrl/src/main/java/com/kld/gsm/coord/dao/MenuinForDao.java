package com.kld.gsm.coord.dao;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.coord.domain.MenuinFor;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/29 17:24
 * @Description:
 */
@SybaseRepository
public interface MenuinForDao  extends BaseDao<MenuinFor,Long> {
    //通过菜单名称查所有
    List<MenuinFor> getByMenuName(String menuName);

}
