package com.kld.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kld.gsm.ATG.dao.SysManageIquidInstrumentDao;
import com.kld.gsm.ATG.domain.SysManageIquidInstrument;

import com.kld.app.service.IIquidInstrumentService;

/**
 * Created by 1 on 2015/10/26.
 */
@Service("iquidInstrumentService")
public class IquidInstrumentServiceImpl implements IIquidInstrumentService {
    @Resource
    private SysManageIquidInstrumentDao sysManageIquidInstrumentDao;
    
    @Override
    public int insertSelective(SysManageIquidInstrument record) {
        return sysManageIquidInstrumentDao.insertSelective(record);
    }

    /**
     * 取得上一次校正时间
     * @return
     */
    @Override
    public String getLastAdjustTime() {
        return "编译报错.暂时修改一下";
//        return sysManageIquidInstrumentDao.getLastAdjustTime();
    }
}
