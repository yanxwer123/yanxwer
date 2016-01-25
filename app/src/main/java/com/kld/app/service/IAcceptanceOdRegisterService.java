package com.kld.app.service;

import com.kld.gsm.ATG.domain.AcceptanceNoBills;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.ATG.domain.SysManageOilType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IAcceptanceOdRegisterService {

    int deleteByPrimaryKey(String deliveryno);

    int insert(AcceptanceOdRegister record);

    int insertSelective(AcceptanceOdRegister record);

    AcceptanceOdRegister selectByPrimaryKey(String deliveryno);

    int updateByPrimaryKeySelective(AcceptanceOdRegister record);

    int updateByPrimaryKey(AcceptanceOdRegister record);

    /**
     * @description 根据油品编码获取 无货单验收数据
     * */
    List<AcceptanceOdRegister>  selectWHDByoilno(String oilno);


    List<HashMap<String,?>> selectjhysbynoanddate(String no,Date st,Date et);

    /**
     * @param billno 手工单号
     * @description 作废进货单号 ，删除登记表，明细表记录，出库单状态还原
     *
     * */
    int cancelAcceptOdreg(String billno);

    /**
     * @decription 获取正在使用的油品列表
     * */
    List<SysManageOilType> selectUseOilType();


    /*
    * @description 根据oilno获取oiltype记录
    * */
    SysManageOilType selectOilType(String oil);


    /**
     * 完成卸油获取 实际收油，损失率等
     * */
    Map getodreglossrate(Double yfss,Double yfssv20,String billno);


    AcceptanceNoBills selectNobill(String bill);

    int UpdateNobill(AcceptanceNoBills noBills);


}
