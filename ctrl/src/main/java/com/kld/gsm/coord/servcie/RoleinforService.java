package com.kld.gsm.coord.servcie;

import com.kld.gsm.ATG.common.base.BaseService;
import com.kld.gsm.coord.domain.Roleinfor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/29 15:06
 * @Description:
 */
public interface RoleinforService extends BaseService<Roleinfor,Long> {
    List<Roleinfor> getByRoleName(String roleName);
}
