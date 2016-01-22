package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageDataUploadList;

import java.util.Date;
import java.util.List;

@MySqlRepository
public interface SysManageDataUploadListDao {
    int deleteByPrimaryKey(String id);

    int insert(SysManageDataUploadList record);

    int insertSelective(SysManageDataUploadList record);

    SysManageDataUploadList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysManageDataUploadList record);

    int updateByPrimaryKey(SysManageDataUploadList record);

    List<SysManageDataUploadList> selectByTrans(String trans);

    SysManageDataUploadList selectByDate(Date date );

    List<SysManageDataUploadList> selectByfilename(String filename);
}