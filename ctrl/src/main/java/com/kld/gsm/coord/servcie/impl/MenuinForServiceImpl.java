package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.common.base.BaseDao;
import com.kld.gsm.ATG.common.base.BaseServiceImpl;
import com.kld.gsm.coord.dao.MenuinForDao;
import com.kld.gsm.coord.domain.MenuinFor;
import com.kld.gsm.coord.servcie.MenuinForService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/29 17:28
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class MenuinForServiceImpl extends BaseServiceImpl<MenuinFor, Long> implements MenuinForService {
    @Autowired
    private MenuinForDao menuinForDao;

    @Override
    protected BaseDao<MenuinFor, Long> getDao() {
        return this.menuinForDao;
    }

    @Override
    public List<MenuinFor> getByMenuName(String menuName) {
        List<MenuinFor> list = menuinForDao.getByMenuName(menuName);
        return list;
    }

}
