package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.SysmanageRealgiveoilMapper;
import com.kld.gsm.center.domain.SysmanageRealgiveoil;
import com.kld.gsm.center.service.Sys_SjfylService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2016/2/23 16:54
 * @Description:
 */
@SuppressWarnings("all")
@Service
public class Sys_SjfylServiceImpl implements Sys_SjfylService{
    @Resource
    private SysmanageRealgiveoilMapper sysmanageRealgiveoilMapper;
    @Transactional(rollbackFor=Exception.class)
    public int Addsjfyl(List<SysmanageRealgiveoil> realgiveoils){
     for(SysmanageRealgiveoil realgiveoil:realgiveoils){
         sysmanageRealgiveoilMapper.insert(realgiveoil);
     }
        return 1;
    }

    @Override
    public SysmanageRealgiveoil selectBybillno(String ckdid) {
        return sysmanageRealgiveoilMapper.selectByPrimaryKey(ckdid);
    }


}
