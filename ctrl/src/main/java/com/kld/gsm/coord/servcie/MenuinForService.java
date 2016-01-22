package com.kld.gsm.coord.servcie;

import com.kld.gsm.ATG.common.base.BaseService;
import com.kld.gsm.coord.domain.MenuinFor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/29 17:27
 * @Description:
 */
public interface MenuinForService extends BaseService<MenuinFor, Long> {

    List<MenuinFor> getByMenuName(String menuName);

}