package com.kld.gsm.coord.dao;

import com.kld.gsm.coord.domain.BillInfor;
import com.kld.gsm.coord.mybatis.SybaseRepository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2015/12/8 19:15
 * @Description:单据信息表查询
 */
@SybaseRepository
public interface BillInforDao {
    List<BillInfor> selectBycode(String typeno);
    int updateBillInfor(BillInfor billInfor);
    int updateBillInfor1(String sql);
    int insertBillInfor(BillInfor billInfor);
    int insertBillInfor1(String sql);
}
