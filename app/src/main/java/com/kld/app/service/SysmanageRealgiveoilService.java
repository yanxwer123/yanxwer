package com.kld.app.service;

import com.kld.gsm.ATG.domain.SysmanageRealgiveoil;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/2/28  15:04
 * @Description:
 */
public interface SysmanageRealgiveoilService {
    SysmanageRealgiveoil findByCxdId(String cxdid);
}
