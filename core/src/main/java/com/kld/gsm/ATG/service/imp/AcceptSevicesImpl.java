package com.kld.gsm.ATG.service.imp;

import com.fasterxml.jackson.databind.JavaType;
import com.kld.gsm.ATG.dao.AcceptanceDeliveryBillDao;
import com.kld.gsm.ATG.dao.SysmanageRealgiveoilDao;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.ATG.utils.action;
import com.kld.gsm.ATG.utils.httpClient;
import com.kld.gsm.ATG.utils.param;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.util.JsonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/*
Created BY niyang
Created Date 2015/11/22/
*/
@Service
@SuppressWarnings("all")
public class AcceptSevicesImpl implements AcceptSevices {
    @Resource
    private com.kld.gsm.ATG.dao.AcceptanceDeliveryBillDao acceptanceDeliveryBillDao;
    @Resource
    private com.kld.gsm.ATG.dao.AcceptanceOdRegisterDao acceptanceOdRegisterDao;
    @Resource
    private com.kld.gsm.ATG.dao.AcceptanceOdRegisterInfoDao acceptanceOdRegisterInfoDao;
    @Resource
    private SysmanageRealgiveoilDao sysmanageRealgiveoilDao;

    @Resource
    private AcceptanceDeliveryBillDao deliveryBillDao;


    public SysmanageRealgiveoil getsjfyl(String host, String ckdId) {
        action ac = new action();
        String path = ac.getUri(host, "resource.services.accept.getsjfyl");
        Map<String, String> map = new param().getparam();
        map.put("ckdId", ckdId);
        httpClient client = new httpClient();
        try {
            String jsonResult = client.request(path, null, map);
            JsonMapper jsonMapper = new JsonMapper();
            SysmanageRealgiveoil sysmanageRealgiveoil = jsonMapper.fromJson(jsonResult, SysmanageRealgiveoil.class);
            if (sysmanageRealgiveoil == null||
                    sysmanageRealgiveoil.getSjfyl().isEmpty()||
                    sysmanageRealgiveoil.getWd()==null) {
                return null;
            } else {
                sysmanageRealgiveoilDao.insert(sysmanageRealgiveoil);
                return sysmanageRealgiveoil;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public AcceptanceDeliveryBills getDebillByNo(String host, String delno) {
        //获取action地址
        action ac = new action();
        String path = ac.getUri(host, "resource.services.accept.getbillsBybillno");

        Map<String, String> hm = new param().getparam();
        hm.put("billno", delno);
        //发送请求数据
        httpClient client = new httpClient();
        try {
            String js = client.request(path, null, hm);
            JsonMapper jm = new JsonMapper();
            AcceptanceDeliveryBills acceptanceDeliveryBillses = jm.fromJson(js, AcceptanceDeliveryBills.class);
            if (acceptanceDeliveryBillses == null) return null;
            return acceptanceDeliveryBillses;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    * 传输进货验收状态
    * */
    public int transodRegStatus(String host, OdregStatus odregStatus) {

        //resource.services.sys.transstatus
        //获取action地址
        action ac = new action();
        String path = ac.getUri(host, "resource.services.accept.transtatus");

        Map<String, String> hm = new param().getparam();

        //发送请求数据
        httpClient client = new httpClient();
        try {
            List<OdregStatus> statuses=new ArrayList<OdregStatus>();
            statuses.add(odregStatus);
            client.request(path, statuses, hm);
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * @param host   中心地址 nodeno 站级系统编码
     * @param nodeno
     * @description 从中心获取出货单
     */
    @Override
    public int getbillsfromcenter(String host, String nodeno) {
        //获取action地址
        int i = 0;
        action ac = new action();
        String path = ac.getUri(host, "resource.services.accept.getbillsfromcenter");

        Map<String, String> hm = new param().getparam();
        hm.put("NodeNo", nodeno);
        //发送请求数据
        httpClient client = new httpClient();
        try {
            String js = client.request(path, null, hm);
            JsonMapper jm = new JsonMapper();
            JavaType jt = jm.createCollectionType(List.class, AcceptanceDeliveryBills.class);
            List<AcceptanceDeliveryBills> acceptanceDeliveryBillses = jm.fromJson(js, jt);
            if (acceptanceDeliveryBillses.isEmpty()) return 0;
            else {
                String list = "";
                for (AcceptanceDeliveryBills item : acceptanceDeliveryBillses) {
                    list = list + (item.getDeliveryno().toString() + ",");
                    /*AcceptanceDeliveryBills localbill=acceptanceDeliveryBillDao.selectByPrimaryKey(item.getDeliveryno());
                    if (localbill!=null&&localbill.getIscomplete().equals("1"))continue;*/
                    item.setFromdepotname(URLDecoder.decode(item.getFromdepotname(), "UTF-8"));
                    String str = item.getTostationname();
                    item.setTostationname(URLDecoder.decode(str, "utf-8"));
                    item.setCarno(URLDecoder.decode(item.getCarno(), "utf-8"));
                    if (acceptanceDeliveryBillDao.selectByPrimaryKey(item.getDeliveryno()) == null) {
                        i++;
                        item.setIscomplete("0");
                        acceptanceDeliveryBillDao.merge(item);
                    }
                }


                path = ac.getUri(host, "resource.services.accept.noticecenter");
                hm.remove("NodeNo");
                hm.put("billnos", list);
                js = client.request(path, null, hm);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return i;
    }

    /**
     * 根据出库单号，获取本地出库单
     *
     * @param deliveryno
     */
    @Override
    public AcceptanceDeliveryBills getbillfromlocal(String deliveryno) {
        return acceptanceDeliveryBillDao.selectByPrimaryKey(deliveryno);
    }


    /*
    * 发送卸油数据到center
    * */
    @Transactional(rollbackFor = Exception.class)
    public int sendOdreg(String host, String NodeNo) {

        List<AcceptOdRegMain> acceptOdRegMains = new ArrayList<AcceptOdRegMain>();
        // get main
        List<AcceptanceOdRegister> acceptanceOdRegisters = acceptanceOdRegisterDao.selectByTrans("0");

        for (AcceptanceOdRegister item : acceptanceOdRegisters) {
            AcceptOdRegMain main = new AcceptOdRegMain();
            main.setAcceptanceOdRegisterInfos(acceptanceOdRegisterInfoDao.selectBydeliveryno(item.getManualNo()));
            main.setAcceptanceOdRegister(item);
            acceptOdRegMains.add(main);
        }

        //获取action地址
        action ac = new action();
        String path = ac.getUri(host, "resource.services.accept.addOdReg");

        Map<String, String> hm = new param().getparam();
        hm.put("NodeNo", NodeNo);
        //发送请求数据
        httpClient client = new httpClient();
        try {
            //System.out.println(new JsonMapper().toJson(acceptOdRegMains));
            String js = client.request(path, acceptOdRegMains, hm);
            ResultMsg r = new JsonMapper().fromJson(js, ResultMsg.class);

            for (AcceptanceOdRegister item : acceptanceOdRegisters) {
                item.setTranstatus("1");
                acceptanceOdRegisterDao.updateByPrimaryKey(item);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return 0;
    }

    public List<AcceptanceOdRegisterInfo> selectAcceptanceOdRegisterInfo(String DeliveryNo) {
        return acceptanceOdRegisterInfoDao.selectById(DeliveryNo);
    }

    @Override
    public int noticeCenterDelbillno(String host, String billno, String nodeno) {
        //resource.services.sys.transstatus
        //获取action地址
        action ac = new action();
        String path = ac.getUri(host, "resource.services.accept.delnotice");

        Map<String, String> hm = new param().getparam();
        hm.put("billno", billno);
        hm.put("nodeno", nodeno);

        //发送请求数据
        httpClient client = new httpClient();
        try {
            client.request(path, null, hm);
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<AcceptanceDeliveryBills> getUncompletebills() {
        return deliveryBillDao.selectByIsComplete("0");
    }

    @Override
    public SysmanageRealgiveoil getbydeliveryno(String deliveryno) {
        return sysmanageRealgiveoilDao.selectByPrimaryKey(deliveryno);
    }

    @Override
    public List<AcceptanceOdRegisterInfo> selectUncompleteInfo() {
        return acceptanceOdRegisterInfoDao.selectAllUncompleteinfo();
    }
}
