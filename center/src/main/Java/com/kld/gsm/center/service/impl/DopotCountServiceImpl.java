package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.FMResult;
import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_daily_opotCount;
import com.kld.gsm.center.domain.oss_daily_tankshift;
import com.kld.gsm.center.service.DopotCountService;
import com.kld.gsm.center.util.PayTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_opotCountMapper;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by xhz on 2015/11/18.
 * 付油量分类统计表（按付油类型）
 */
@Service("DopotCountService")
public class DopotCountServiceImpl implements DopotCountService {
    @Resource
    private oss_daily_opotCountMapper ossDailyOpotCountMapper;
    @Transactional(rollbackFor = Exception.class)
    public int AddDopotCount(List<oss_daily_opotCount> oss_daily_opotCounts) {
        for (oss_daily_opotCount item:oss_daily_opotCounts)
        {
            ossDailyOpotCountMapper.insert(item);
        }
        return 1;
    }
    @Override
    public ResultMsg selectByShift(String shift,String oucode) {
        ResultMsg result=new ResultMsg();

        HashMap hashMap = new HashMap();
        hashMap.put("shift", shift);
        hashMap.put("oucode", oucode);

        List<oss_daily_opotCount> exchangeList=ossDailyOpotCountMapper.selectFyxx(hashMap);
        List<FMResult> list=getDataInfo(exchangeList);
        result.setResult(true);
        result.setData(list);
        result.setRows(list);
        result.setTotal(list.size());
        return result;
    }



    private List<FMResult> getDataInfo(List<oss_daily_opotCount> oilNolist) {
        HashMap<String,List<oss_daily_opotCount>> map=new HashMap<String, List<oss_daily_opotCount>>();
        for (int i=0;i<oilNolist.size();i++)
        {
            oss_daily_opotCount opotCount=(oss_daily_opotCount)oilNolist.get(i);
            List<oss_daily_opotCount> maplist=map.get(opotCount.getOilno());
            List<oss_daily_opotCount> listst=new ArrayList<oss_daily_opotCount>();
            if (maplist!=null)
            {
                maplist.add(opotCount);
            }
            else
            {
                listst.add(opotCount);
                map.put(opotCount.getOilno(),listst);
            }
        }
        Set<String> set=map.keySet();
        System.out.println("map的对象个数-------》"+set.size());
        FMResult model =null;
        List<FMResult> list = new ArrayList<FMResult>();
        for (String key:set)
        {
            List opotlist=map.get(key);
            double total=0;
            if (opotlist.size()==1)
            {
                model=new FMResult();
                oss_daily_opotCount ossDailyOpotCount=(oss_daily_opotCount)opotlist.get(0);
                if (ossDailyOpotCount.getOilname()!=null)
                {
                    model.setOilname(ossDailyOpotCount.getOilname().toString());
                }
                if (ossDailyOpotCount.getType().equals(PayTypeEnum.现金.value()))//现金
                {
                    model.setMoney(ossDailyOpotCount.getAmount());

                }
                else
                {
                    model.setMoney(0.00);
                }

                if (ossDailyOpotCount.getType().equals(PayTypeEnum.邮票.value()))//邮票
                {
                    model.setYp(ossDailyOpotCount.getAmount());

                }
                else
                {
                    model.setYp(0.00);
                }
                if (ossDailyOpotCount.getType().equals(PayTypeEnum.记账.value()))//记账
                {
                    model.setJz(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setJz(0.00);
                }
                if (ossDailyOpotCount.getType().equals(PayTypeEnum.银行卡.value()))//银行卡
                {
                    model.setYhk(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setYhk(0.00);
                }
                if (ossDailyOpotCount.getType().equals(PayTypeEnum.其他1.value()))//其他1
                {
                    model.setOther1(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setOther1(0.00);
                }
                if (ossDailyOpotCount.getType().equals(PayTypeEnum.其他2.value()))//其他2
                {
                    model.setOther2(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setOther2(0.00);
                }
                if (ossDailyOpotCount.getType().equals(PayTypeEnum.IC卡积分.value()))//IC卡积分
                {
                    model.setIcjf(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setIcjf(0.00);
                }

                if (ossDailyOpotCount.getType().equals(PayTypeEnum.IC卡.value()))//IC卡
                {
                    model.setIc(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setIc(0.00);
                }

                if (ossDailyOpotCount.getType().equals(PayTypeEnum.已售未提.value()))//已售未提
                {
                    model.setYswt(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setYswt(0.00);
                }

                if (ossDailyOpotCount.getType().equals(PayTypeEnum.已提未售.value()))//已提未售
                {
                    model.setYtws(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setYtws(0.00);
                }

                if (ossDailyOpotCount.getType().equals(PayTypeEnum.代存代付.value()))//已提未售
                {
                    model.setDcdf(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setDcdf(0.00);
                }

                if (ossDailyOpotCount.getType().equals(PayTypeEnum.邮票代管.value()))//已提未售
                {
                    model.setYpdg(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setYpdg(0.00);
                }

                if (ossDailyOpotCount.getType().equals(PayTypeEnum.自用油.value()))//自用油
                {
                    model.setZyy(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setZyy(0.00);
                }

                if (ossDailyOpotCount.getType().equals(PayTypeEnum.非机走.value()))	//非机走
                {
                    model.setFjz(ossDailyOpotCount.getAmount());
                }
                else
                {
                    model.setFjz(0.00);
                }
                total=ossDailyOpotCount.getAmount();
                model.setTotal(total);
                list.add(model);
            }
            else
            {
                String oilName=""; Double xj=0.0;Double yp=0.0;
                Double jz=0.0; Double yhk=0.0;Double qt1=0.0;
                Double qt2=0.0;Double ickjf=0.0;Double ic=0.0;
                Double yswt=0.0;Double ytws=0.0; Double dcdf=0.0;
                Double ypdg=0.0;Double zyy=0.0; Double fjz=0.0;
                for (Object opo:opotlist)
                {
                    oss_daily_opotCount ossDailyOpotCount1=(oss_daily_opotCount)opo;
                    model=new FMResult();
                    if (ossDailyOpotCount1.getOilname()!=null)
                    {
                        oilName=ossDailyOpotCount1.getOilname().toString();
                    }
                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.现金.value()))//现金
                    {
                        xj+=ossDailyOpotCount1.getAmount();
                        model.setMoney(xj);

                    }
                    else
                    {
                        model.setMoney(xj);
                    }

                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.邮票.value()))//邮票
                    {
                        yp+=ossDailyOpotCount1.getAmount();
                        model.setYp(yp);

                    }
                    else
                    {
                        model.setYp(yp);
                    }
                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.记账.value()))//记账
                    {
                        jz+=ossDailyOpotCount1.getAmount();
                        model.setJz(jz);
                    }
                    else
                    {
                        model.setJz(jz);
                    }
                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.银行卡.value()))//银行卡
                    {
                        yhk+=ossDailyOpotCount1.getAmount();
                        model.setYhk(yhk);
                    }
                    else
                    {
                        model.setYhk(yhk);
                    }
                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.其他1.value()))//其他1
                    {
                        qt1+=ossDailyOpotCount1.getAmount();
                        model.setOther1(qt1);
                    }
                    else
                    {
                        model.setOther1(qt1);
                    }
                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.其他2.value()))//其他2
                    {
                        qt2+=ossDailyOpotCount1.getAmount();
                        model.setOther2(qt2);
                    }
                    else
                    {
                        model.setOther2(qt2);
                    }
                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.IC卡积分.value()))//IC卡积分
                    {
                        ickjf+=ossDailyOpotCount1.getAmount();
                        model.setIcjf(ickjf);
                    }
                    else
                    {
                        model.setIcjf(ickjf);
                    }

                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.IC卡.value()))//IC卡
                    {
                        ic+=ossDailyOpotCount1.getAmount();
                        model.setIc(ic);
                    }
                    else
                    {
                        model.setIc(ic);
                    }

                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.已售未提.value()))//已售未提
                    {
                        yswt+=ossDailyOpotCount1.getAmount();
                        model.setYswt(yswt);
                    }
                    else
                    {
                        model.setYswt(yswt);
                    }

                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.已提未售.value()))//已提未售
                    {
                        ytws+=ossDailyOpotCount1.getAmount();
                        model.setYtws(ytws);
                    }
                    else
                    {
                        model.setYtws(ytws);
                    }

                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.代存代付.value()))//已提未售
                    {
                        dcdf+=ossDailyOpotCount1.getAmount();
                        model.setDcdf(dcdf);
                    }
                    else
                    {
                        model.setDcdf(dcdf);
                    }

                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.邮票代管.value()))//已提未售
                    {
                        ypdg+=ossDailyOpotCount1.getAmount();
                        model.setYpdg(ypdg);
                    }
                    else
                    {
                        model.setYpdg(ypdg);
                    }

                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.自用油.value()))//自用油
                    {
                        zyy+=ossDailyOpotCount1.getAmount();
                        model.setZyy(zyy);
                    }
                    else
                    {
                        model.setZyy(zyy);
                    }

                    if (ossDailyOpotCount1.getType().equals(PayTypeEnum.非机走.value()))	//非机走
                    {
                        fjz+=ossDailyOpotCount1.getAmount();
                        model.setFjz(fjz);
                    }
                    else
                    {
                        model.setFjz(fjz);
                    }
                    total+=ossDailyOpotCount1.getAmount();

                }
                model.setOilname(oilName);
                model.setTotal(total);
                list.add(model);
            }
        }

        return list;

    }



}
