package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.domain.oss_sysmanage_DataUploadList;
import com.kld.gsm.center.domain.oss_sysmanage_DataUploadListKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@MysqlRepository
public interface oss_sysmanage_DataUploadListMapper {
    int deleteByPrimaryKey(oss_sysmanage_DataUploadListKey key);

    int insert(oss_sysmanage_DataUploadList record);

    int insertSelective(oss_sysmanage_DataUploadList record);

    oss_sysmanage_DataUploadList selectByPrimaryKey(oss_sysmanage_DataUploadListKey key);

    int updateByPrimaryKeySelective(oss_sysmanage_DataUploadList record);

    int updateByPrimaryKey(oss_sysmanage_DataUploadList record);

    List<oss_sysmanage_DataUploadList> selectbystatus(String status);
}