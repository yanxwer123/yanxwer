package com.kld.gsm.ATG.dao;

import com.kld.gsm.ATG.common.Repository.MySqlRepository;
import com.kld.gsm.ATG.domain.SysManageIquidInstrument;

import javax.print.DocFlavor;
import java.util.List;

@MySqlRepository
public interface SysManageIquidInstrumentDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysManageIquidInstrument record);

    int insertSelective(SysManageIquidInstrument record);

    SysManageIquidInstrument selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysManageIquidInstrument record);

    int updateByPrimaryKey(SysManageIquidInstrument record);

    List<SysManageIquidInstrument> selectByTrans(String trans);

    //查询设备型号
    SysManageIquidInstrument selectSBXH();

    SysManageIquidInstrument  selectLast();

}