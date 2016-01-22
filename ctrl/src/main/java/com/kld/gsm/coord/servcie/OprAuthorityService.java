package com.kld.gsm.coord.servcie;


import com.kld.gsm.ATG.common.base.BaseService;
import com.kld.gsm.coord.domain.OprAuthority;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/27 17:18
 * @Description:
 */
public interface OprAuthorityService extends BaseService<OprAuthority,Long> {

    List<OprAuthority> getByOprNo(int oprno);
}