package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.SysmanageRealgiveoil;

import java.util.List;

//*/
/**
// * Created by IntelliJ IDEA.
// *
// * @author <a href="yanxwer@163.com">yanxiaowei</a>
// * @version 1.0
// * @CreationTime: 2016/2/23 15:49
// * @Description:
// */

public interface Sys_SjfylService {
    int Addsjfyl(List<SysmanageRealgiveoil> realgiveoils);
  //根据出库单查询
    SysmanageRealgiveoil selectBybillno(String billno);

}
