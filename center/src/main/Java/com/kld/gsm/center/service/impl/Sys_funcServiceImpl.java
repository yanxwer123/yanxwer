package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.Sys_funcMapper;
import com.kld.gsm.center.dao.Sys_funcMapper;
import com.kld.gsm.center.domain.Sys_func;
import com.kld.gsm.center.service.Sys_funcService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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
    public int insertRow(HashMap map) {
        return sys_funcDao.insertRow(map);
    }

    @Override
    public int updateRow(HashMap map) {
        return sys_funcDao.updateRow(map);
    }

    @Override
    public int delRow(String funccode) {
        return sys_funcDao.deleteByPrimaryKey(funccode);
    }

    @Override
    public List<Sys_func> getFuncList(Map<String,Object> map){
            return sys_funcDao.getFuncList(map);
    }

    @Override
    public List<Sys_func> selectBycode(String parentcode) {
        return sys_funcDao.selectBycode(parentcode);
    }

    @Override
    public List<HashMap<String, Object>> getCatalogList(Integer pageNo, Integer pageSize) {
        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;
            int firstRow = (pageNo - 1) * pageSize;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
            return sys_funcDao.getCatalogList(hashMap);
        }
        return null;
    }

    @Override
    public List<HashMap<String, Object>> getCatalogAllList() {
        return sys_funcDao.getCatalogAllList();
    }

    @Override
    public List<Sys_func> selectAll() {
        return sys_funcDao.selectAll();
    }

}
