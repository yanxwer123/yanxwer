package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.Sys_funcMapper;
import com.kld.gsm.center.dao.Sys_funcMapper;
import com.kld.gsm.center.domain.Sys_func;
import com.kld.gsm.center.service.Sys_funcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by jw on 2015/7/30.
 */
@Service
public class Sys_funcServiceImpl implements Sys_funcService {
    @Resource
    private Sys_funcMapper sys_funcDao;

    @Override
    public List<Sys_func> getFuncList(Map<String,Object> map){
            return sys_funcDao.getFuncList(map);
    }

}
