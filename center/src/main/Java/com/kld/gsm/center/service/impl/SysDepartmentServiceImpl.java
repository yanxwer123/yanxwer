package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_sysmanage_departmentMapper;
import com.kld.gsm.center.domain.oss_sysmanage_department;
import com.kld.gsm.center.service.SysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/1/14 14:00
 * @Description:
 */
@Service
public class SysDepartmentServiceImpl implements SysDepartmentService {
    @Autowired
    private oss_sysmanage_departmentMapper oss_sysmanage_departmentMapper;
    @Override
    public oss_sysmanage_department findByOUCode(String oucode) {
        return oss_sysmanage_departmentMapper.findByOUCode(oucode);
    }
}
