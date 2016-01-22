/**
 * 58.com Inc.
 * Copyright (c) 2005-2015 All Rights Reserved.
 */
package com.kld.gsm.center.dao;

import com.kld.gsm.center.common.MysqlRepository;
import com.kld.gsm.center.common.MysqlRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@MysqlRepository
public interface UserAuthorityPointDao {
    List<HashMap<String, Object>> getauthorityPointListByUserId(@Param("username") String username);

}
