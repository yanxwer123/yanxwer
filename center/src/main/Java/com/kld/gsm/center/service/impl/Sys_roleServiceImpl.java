package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.Sys_roleMapper;
import com.kld.gsm.center.domain.Sys_role;
import com.kld.gsm.center.service.Sys_roleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/2/17 19:40
 * @Description:
 */
@Service("sys_roleservice")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class Sys_roleServiceImpl implements Sys_roleService {
    @Resource
    private Sys_roleMapper sys_roleMapper;
    @Override
    public List<Sys_role> getRoleList(Map<String, Object> map) {
        return null;
    }

    @Override
    public List<Sys_role> selectAll() {
        return sys_roleMapper.selectAll();
    }

    @Override
    public List<Sys_role> getSysRoleFunList(Map<String, Object> map) {
        return null;
    }

    @Override
    public int delete(String rolecode) {
        return sys_roleMapper.delete(rolecode);
    }

    @Override
    public int insertRow(HashMap map) {
        return sys_roleMapper.insertRow(map);
    }

    @Override
    public int updateRow(HashMap map) {
        return sys_roleMapper.updateRow(map);
    }

    @Override
    public int updateSysRoleByCode(Sys_role sys_role) {
        return 0;
    }

    @Override
    public int insert(Sys_role sys_role) {
        return 0;
    }

    @Override
    public List<HashMap<String, Object>> getSysRolePageList(Integer pageNo, Integer pageSize, String name) {
        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;
            int firstRow = (pageNo - 1) * pageSize;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
            hashMap.put("name", name);

            return sys_roleMapper.getSysRolePageList(hashMap);
        }

        return null;
    }

    @Override
    public List<HashMap<String, Object>> getSysRoleList(String name) {
        HashMap hashMap = new HashMap();
        hashMap.put("name", name);

        return sys_roleMapper.getSysRoleList(hashMap);
    }

    @Override
    public Sys_role selectSysRoleByRolecode(Sys_role sys_role) {
        return null;
    }
}
