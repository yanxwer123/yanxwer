package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.SysManageDictDao;
import com.kld.gsm.center.domain.SysManageDict;
import com.kld.gsm.center.service.SysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fangzhun on 2015/12/4.
 */
@Service("SysDictService")
public class SysDictServiceImpl implements SysDictService {

    @Resource
    SysManageDictDao sysManageDictDao;
    @Override
    public int selectPSYJFZ() {
        return sysManageDictDao.selectByName("配送建议阀值");
    }

    @Override
    public List<HashMap<String, String>> selectYJLX() {
        return sysManageDictDao.selectYJLX();
    }

    @Override
    public SysManageDict selectByCode(String code) {
        return sysManageDictDao.selectByCode(code);
    }

    @Override
    public List<HashMap<String, String>> selectByParentId(String parentid){return sysManageDictDao.selectByParentId(parentid);}

    @Override
    public List<SysManageDict> selectbyVersion(Integer version) {
        return sysManageDictDao.selectbyVersion(version);
    }

    @Override
    public SysManageDict selectByPrimaryKey(int id) {
        return sysManageDictDao.selectByPrimaryKey(id);
    }

    @Override
    public List<SysManageDict> selectAll() {
        return sysManageDictDao.selectAll();
    }

    @Override
    public int insertSelective(SysManageDict sysManageDict) {
        return sysManageDictDao.insertSelective(sysManageDict);
    }

    @Override
    public int updateByPrimaryKeySelective(SysManageDict sysManageDict) {
        return sysManageDictDao.updateByPrimaryKeySelective(sysManageDict);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysManageDictDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<HashMap<String, Object>> getDictList(Integer pageNo, Integer pageSize,Integer dictID,Integer parentID,String name) {
        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;
            int firstRow = (pageNo - 1) * pageSize;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
            hashMap.put("dictID", dictID);
            hashMap.put("parentID", parentID);
            hashMap.put("name", name);
            return sysManageDictDao.getDictList(hashMap);
        }
        return null;
    }

    @Override
    public List<HashMap<String, Object>> getDictAllList(Integer dictID,Integer parentID,String name) {
        HashMap hashMap = new HashMap();
        hashMap.put("dictID", dictID);
        hashMap.put("parentID", parentID);
        hashMap.put("name", name);
        return sysManageDictDao.getDictAllList(hashMap);
    }

    @Override
    public int insertRow(HashMap map) {
        return sysManageDictDao.insertRow(map);
    }

    @Override
    public int updateRow(HashMap map) {
        return sysManageDictDao.updateRow(map);
    }

}
