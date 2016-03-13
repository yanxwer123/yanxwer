package com.kld.gsm.center.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.kld.gsm.center.common.RedisUtil;
import com.kld.gsm.center.dao.oss_monitor_InventoryMapper;
import com.kld.gsm.center.dao.oss_monitor_SummaryMapper;
import com.kld.gsm.center.dao.oss_monitor_Summary_historyMapper;
import com.kld.gsm.center.dao.oss_sysmanage_oilTypeMapper;
import com.kld.gsm.center.domain.GasGunManage;
import com.kld.gsm.center.domain.MacLogInfo;
import com.kld.gsm.center.domain.atg_stock_data_out_t;
import com.kld.gsm.center.domain.oss_monitor_Summary;
import com.kld.gsm.center.service.MonitorSummaryService;
import com.kld.gsm.center.service.OilGunInfoService;
import com.kld.gsm.center.service.SysOrgUnitService;
import com.kld.gsm.center.util.ExportUtil;
import com.kld.gsm.center.util.JsonMapper;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liuming on 2016/2/16.
 */
@Service("MonitorSummaryService")
public class MonitorSummaryServiceImpl1 implements MonitorSummaryService {

    @Resource
    private oss_monitor_SummaryMapper ossMonitorSummaryMapper;

    @Resource
    private oss_sysmanage_oilTypeMapper oss_sysmanage_oilTypeMapper;

    @Resource
    private oss_monitor_InventoryMapper myoss_monitor_InventoryMapper;

    @Resource
    private oss_monitor_Summary_historyMapper oss_monitor_Summary_historyMapper;


    @Override
    public List<HashMap<String, Object>> selectSummary(HashMap<String, Object> map) {
        //myOss_monitor_SummaryMapper.selectByPrimaryKey();
        // List<HashMap<String, Object>> list = ossAlarmInventoryMapper.selectInventoryInfo(hashMap);
       List<HashMap<String, Object>> list = ossMonitorSummaryMapper.selectSummary(map);
        return list;
    }


    @Override
    public List<HashMap<String, Object>> selectSummarybyParent(HashMap<String, Object> map) {

        List<HashMap<String, Object>> list = ossMonitorSummaryMapper.selectSummarybyParent(map);
        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectSummaryCountbyParent(HashMap<String, Object> map) {

        List<HashMap<String, Object>> list = ossMonitorSummaryMapper.selectSummaryCountbyParent(map);
        return list;
    }

    @Override
    public List<HashMap<String, Object>> getInventoryList(HashMap<String, Object> map) {

        List<HashMap<String, Object>> list = myoss_monitor_InventoryMapper.getInventoryList(map);
        return list;
    }

    @Override
    public List<HashMap<String, Object>> getInventoryAllList(HashMap<String, Object> map) {

        List<HashMap<String, Object>> list = myoss_monitor_InventoryMapper.getInventoryAllList(map);
        return list;
    }


    @Override
    public void downloadzdkc(List<HashMap<String,Object>> list, String[] titles, ServletOutputStream outputStream)
    {
        // 创建一个workbook 对应一个excel应用文件
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = workBook.createSheet("Sheet1");
        ExportUtil exportUtil = new ExportUtil(workBook, sheet);
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
        // 构建表头
        XSSFRow headRow = sheet.createRow(0);
        XSSFCell cell = null;
        for (int i = 0; i < titles.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titles[i]);
            //System.out.println(titles[i]);
        }
        // 构建表体数据
        if (list != null && list.size() > 0) {
            for (int j = 0; j < list.size(); j++)
            {
                XSSFRow bodyRow = sheet.createRow(j + 1);
                //List<oss_daily_StationShiftInfo> goods = list.get(j);
                HashMap<String,Object> item=list.get(j);
                cell = bodyRow.createCell(0);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("OilCanNo").toString());

                cell = bodyRow.createCell(1);
                cell.setCellStyle(bodyStyle);
                if(item.get("OilName")==null)
                {
                    cell.setCellValue("");
                }
                else
                {
                    cell.setCellValue(item.get("OilName").toString());
                }

                cell = bodyRow.createCell(2);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("alltime").toString());

                cell = bodyRow.createCell(3);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("StandCubage").toString());

                cell = bodyRow.createCell(4);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("TotalHeight").toString());

                cell = bodyRow.createCell(5);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("OilCubage").toString());

                cell = bodyRow.createCell(6);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("WaterHeight").toString());

                cell = bodyRow.createCell(7);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("WaterBulk").toString());

                cell = bodyRow.createCell(8);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("Temp").toString());

                cell = bodyRow.createCell(9);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("EmptyCubage").toString());

            }
        }
        try
        {
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                outputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    @Override
    public int AddSummaryData() {

        //查询数据库数据
        List<oss_monitor_Summary>  SummaryList=new ArrayList<oss_monitor_Summary>();

        HashMap hashMap = new HashMap();
        List<HashMap<String, Object>> list = ossMonitorSummaryMapper.selectSummaryServer(hashMap);

        if (list.size()>0)
        {
           //得到油品编码和类型
            HashMap<String, Object> oilTypemap= new HashMap();

            List<HashMap<String, String>> OilTypelist =oss_sysmanage_oilTypeMapper.selectOilType();
            for(HashMap<String, String> hitem:OilTypelist)
            {
                oilTypemap.put(hitem.get("OilNo"),hitem.get("OilType"));
            }

            //更新时间
            Date dtCreateDate = new Date();

            //构造汇总数据
            for (HashMap itemhashMap :list) {
                oss_monitor_Summary ojb=new oss_monitor_Summary();
                ojb.setNodeno(itemhashMap.get("UniversalCode").toString());
                ojb.setOucode(itemhashMap.get("oucode").toString());
                ojb.setCreatedate(dtCreateDate);

                //汽油销售
                double QYSalesvalue=0;
                if(itemhashMap.get("QYSales")!=null)
                {
                    QYSalesvalue=Double.parseDouble(itemhashMap.get("QYSales").toString());

                }
                ojb.setQysales(QYSalesvalue);
                //柴油销售
                double CYSalesvalue=0;
                if(itemhashMap.get("CYSales")!=null)
                {
                    CYSalesvalue=Double.parseDouble(itemhashMap.get("CYSales").toString());

                }
                ojb.setCysales(CYSalesvalue);
                //汽油进货
                double QYOilinValue=0;
                if(itemhashMap.get("QYOilin")!=null)
                {
                    QYOilinValue=Double.parseDouble(itemhashMap.get("QYOilin").toString());

                }
                ojb.setQyoilin(QYOilinValue);
                //柴油进货
                double CYOilinValue=0;
                if(itemhashMap.get("CYOilin")!=null)
                {
                    CYOilinValue=Double.parseDouble(itemhashMap.get("CYOilin").toString());

                }
                ojb.setCyoilin(CYOilinValue);

                //提枪次数
                int TJCountValue=0;
                if(itemhashMap.get("TJCount")!=null)
                {
                    TJCountValue=Integer.parseInt(itemhashMap.get("TJCount").toString());
                }
                ojb.setTjcount(TJCountValue);

                //液位仪使用率
                double YwyRateValue=0;
                if(itemhashMap.get("Ywycount")!=null)
                {
                    YwyRateValue=Double.parseDouble(itemhashMap.get("Ywycount").toString());
                    if (YwyRateValue>1)
                    {
                        YwyRateValue=1;
                    }
                }
                ojb.setYwyrate(YwyRateValue);

                //从redis 里面获取库存信息
                HashMap<String, Object> myStore= GetRedisStore(ojb.getNodeno(),oilTypemap);

                //汽油库存
                ojb.setQystore(Double.parseDouble(myStore.get("QYStore").toString()));

                //柴油库存
                ojb.setCystore(Double.parseDouble(myStore.get("CYStore").toString()));

//                //汽油库存
//                ojb.setQystore(0.1);
//                //柴油库存
//                ojb.setCystore(0.1);
                SummaryList.add(ojb);

            }

              try{

                //保存到汇总表，删除全部添加
                  ossMonitorSummaryMapper.deleteAll();
                  ossMonitorSummaryMapper.insertlist(SummaryList);

                //保存到历史表
                   oss_monitor_Summary_historyMapper.insertlist(SummaryList);

                 }
              catch (Exception ex)
              {
                   // System.out.println(ex.getMessage());
              }

        }

        return SummaryList.size();
    }


//    private  double round(double v)
//    {
//        //return round(v,scale,BigDecimal.ROUND_HALF_EVEN);
//
//       // double f = 3.1516;
//        BigDecimal b = new BigDecimal(f);
//        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        return f1;
//    }


private  HashMap<String, Object>  GetRedisStore(String strOUcode,HashMap<String, Object> oilTypemap)
{
    HashMap hashMap = new HashMap();

    double QYStore=0;
    double CYStore=0;


    RedisUtil pRedisUtil=new RedisUtil();
    String tankjson=pRedisUtil.getValue(strOUcode+"can");
    JsonMapper jm=new JsonMapper();
    JavaType jt=jm.createCollectionType(List.class, MacLogInfo.class);
    jt=jm.createCollectionType(List.class,atg_stock_data_out_t.class);
    if (tankjson!=null&&!tankjson.isEmpty()&&!"".equals(tankjson)) {
        List<atg_stock_data_out_t> tanks = jm.fromJson(tankjson, jt);
        // tanksAndGuns.setTanks(tanks);
        for (atg_stock_data_out_t itemstock :tanks) {

            if (oilTypemap.get(itemstock.getOilno())=="01")  //汽油
            {
                QYStore+=itemstock.getStandardl();

            }
            else  //柴油
            {
                CYStore+=itemstock.getStandardl();
            }


        }

    }

    hashMap.put("QYStore", QYStore);
    hashMap.put("CYStore", CYStore);


    return hashMap;

}



    @Resource
    private OilGunInfoService oilGunInfoService;
    @Resource
    private SysOrgUnitService sysOrgUnitService;
    @Override
    public HashMap GetGqss(String OUCode) {

        HashMap hashMap = new HashMap();

        try{


            //得到UniversalCode
             String nodeno= sysOrgUnitService.selectByOUCode(OUCode).getNodeno();
            if(nodeno==null)
            {
                return hashMap;
            }

            //得到  油品类型名字 列表
            HashMap oilTypemap= new HashMap();
            List<HashMap<String, String>> OilTypelist =oss_sysmanage_oilTypeMapper.selectOilType();
            for(HashMap<String, String> hitem:OilTypelist)
            {
                oilTypemap.put(hitem.get("OilNo"),hitem.get("OilName"));
            }


            //从数据库获取油枪信息
            HashMap hashMapDbgunlist = new HashMap();
            List<GasGunManage> myGas=oilGunInfoService.findByOUCode(OUCode);
            for(GasGunManage gitem:myGas)
            {
                hashMapDbgunlist.put(gitem.getOilgunno(),gitem.getOilcan());

            }

            //从redis获取油罐信息
            RedisUtil pRedisUtil=new RedisUtil();
            String tankjson=pRedisUtil.getValue(nodeno+"can");
            JsonMapper jm=new JsonMapper();
            JavaType jt=jm.createCollectionType(List.class,atg_stock_data_out_t.class);
            if (tankjson!=null&&!tankjson.isEmpty()&&!"".equals(tankjson)) {
                List<atg_stock_data_out_t> tanks = jm.fromJson(tankjson, jt);
                for(atg_stock_data_out_t atgitem:tanks)
                {
                    TankGanInfor myTankGanInfor=new TankGanInfor();

                    //替换  oilno  为 name  OilTypelist   无法替换需要新增 字段
                    if(oilTypemap.containsKey(atgitem.getOilno()))
                    {
                        atgitem.setVersion(oilTypemap.get(atgitem.getOilno()).toString());
                    }
                    myTankGanInfor.setObjatg_stock_data_out_t(atgitem);
                    hashMap.put(atgitem.getOilcan().toString(),myTankGanInfor);
                }
            }

            //从redis获取油枪信息
            String gunjson= pRedisUtil.getValue(nodeno + "gun");
            JsonMapper jmgun=new JsonMapper();
            JavaType jtgun=jmgun.createCollectionType(List.class, MacLogInfo.class);
            if (gunjson!=null&&!gunjson.isEmpty()&&!"".equals(gunjson)){
                List<MacLogInfo> guns=jmgun.fromJson(gunjson, jtgun);

                for(MacLogInfo gitem:guns)
                {
                    if(hashMapDbgunlist.containsKey(gitem.getOilGun()))
                    {
                        Object OilCan= hashMapDbgunlist.get(gitem.getOilGun());
                        if(hashMap.containsKey(OilCan.toString()))
                        {
                            Object objTankGanInfor= hashMap.get(OilCan.toString());
                            TankGanInfor myobjTankGanInfor=(TankGanInfor)objTankGanInfor;
                            myobjTankGanInfor.getMacLogInfolist().add(gitem);


                        }

                    }
                }

            }

        }
        catch (Exception ex)
        {
             System.out.println(ex.getMessage());
        }

        //数据整合处理
        return  hashMap;
    }


    public  class TankGanInfor
    {

        private  atg_stock_data_out_t  objatg_stock_data_out_t;

        private   List<MacLogInfo>  MacLogInfolist;

        public List<MacLogInfo> getMacLogInfolist() {
            return MacLogInfolist;
        }

        public void setMacLogInfolist(List<MacLogInfo> macLogInfolist) {
            MacLogInfolist = macLogInfolist;
        }

        public atg_stock_data_out_t getObjatg_stock_data_out_t() {
            return objatg_stock_data_out_t;
        }

        public void setObjatg_stock_data_out_t(atg_stock_data_out_t objatg_stock_data_out_t) {
            this.objatg_stock_data_out_t = objatg_stock_data_out_t;
        }


    }







}
