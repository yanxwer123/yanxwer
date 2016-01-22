package com.kld.gsm.coord.servcie;


import com.kld.gsm.ATG.common.base.BaseService;
import com.kld.gsm.coord.domain.Sysinfor;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/24 17:17
 * @Description:
 */
public interface SysinforService extends BaseService<Sysinfor,Long> {
     Sysinfor getAll();
}
