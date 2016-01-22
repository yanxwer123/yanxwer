package com.kld.gsm.ATG.service.imp;

import com.fasterxml.jackson.databind.JavaType;
import com.kld.gsm.ATG.dao.SysManageDictDao;
import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.ATG.domain.SysManageDict;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.ATG.utils.action;
import com.kld.gsm.ATG.utils.httpClient;
import com.kld.gsm.ATG.utils.param;
import com.kld.gsm.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;


/*
Created BY niyang
Created Date 2015/11/21
*/
@SuppressWarnings("all")
@Service
public class SysManageDicImpl implements SysManageDic {



    @Autowired
    private SysManageDictDao sysManageDictDao;

    public SysManageDict GetByKey(Integer dicno)
    {
        return sysManageDictDao.selectByPrimaryKey(dicno);
    }

    public SysManageDict GetByCode(String code)
    {
        return sysManageDictDao.selectByCode(code);
    }
    public List<SysManageDict> SelectAll()
    {
        return sysManageDictDao.selectAll();
    }

    @Override
    public int getVersion() {
        Map map=sysManageDictDao.getversion();
        if (map!=null&&map.get("version")!=null){
            return Integer.parseInt(map.get("version").toString());
        }
        return 0;
    }

    /**
     * @param host
     * @decription 同步字典
     */
    @Override
    public int synDicFromCenter(String host) {
        int i=0;
        action ac=new action();
        String path=ac.getUri(host, "resource.services.sys.dict");
        Integer version=getVersion();
        Map<String,String> hm=new param().getparam();
        hm.put("version",version.toString());
        //发送请求数据
        httpClient client=new httpClient();
        try {
            String js= client.request(path,null,hm);
            JsonMapper jm=new JsonMapper();
            JavaType jt=jm.createCollectionType(List.class, SysManageDict.class);
            List<SysManageDict> sysManageDicts=new JsonMapper().fromJson(js,jt);
            for (SysManageDict item:sysManageDicts){
                item.setName(URLDecoder.decode(item.getName(),"UTF-8"));
                sysManageDictDao.merge(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param parentcode
     * @return 字典列表
     * @decription 根据父级编码，查询字典
     */
    @Override
    public List<SysManageDict> getByParentCode(String parentcode) {
        return sysManageDictDao.selectByParentCode(parentcode);
    }
}
