package com.kld.gsm.center.service;

import com.kld.gsm.center.domain.oss_sysmanage_cubage;
import com.kld.gsm.center.domain.oss_sysmanage_cubageInfo;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/1/4 12:27
 * @Description:
 */
public interface ISysmanageCubageService {
    List<oss_sysmanage_cubage> getCubages(Map<String,Object> map);
    int getCubageCounts(Map<String,Object> map);
    List<oss_sysmanage_cubage> getCubageVersions(Map<String,Object> map);
    List<Map<String,Object>> getCubageInfos(Map<String,Object> map);
    void ExportCubageInfos(List<Map<String, Object>> list,String[] titles,OutputStream outputStream);
    int useCubageSave(Map<String,Object> map);
    List<oss_sysmanage_cubage> getUntranCubages(Map<String, Object> map);
    List<oss_sysmanage_cubageInfo> getUntranCubageInfos(oss_sysmanage_cubage cubage);
}
