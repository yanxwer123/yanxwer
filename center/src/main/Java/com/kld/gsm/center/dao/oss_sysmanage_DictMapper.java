package com.kld.gsm.center.dao;

import com.kld.gsm.center.domain.oss_sysmanage_Dict;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface oss_sysmanage_DictMapper {
    int deleteByPrimaryKey(Integer dictid);

    int insert(oss_sysmanage_Dict record);

    int insertSelective(oss_sysmanage_Dict record);

    oss_sysmanage_Dict selectByPrimaryKey(Integer dictid);

    int updateByPrimaryKeySelective(oss_sysmanage_Dict record);

    int updateByPrimaryKey(oss_sysmanage_Dict record);

    List<oss_sysmanage_Dict>  getdicts(int version);
}