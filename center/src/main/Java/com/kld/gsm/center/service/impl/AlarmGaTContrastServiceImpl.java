package com.kld.gsm.center.service.impl;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kld.gsm.center.dao.SysManageDictDao;
import com.kld.gsm.center.dao.oss_sys_OrgUnitMapper;
import com.kld.gsm.center.domain.GaTContrast;
import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_alarm_GaTContrast;
import com.kld.gsm.center.service.AlarmGaTContrastService;
import com.kld.gsm.center.service.SysDictService;
import com.kld.gsm.center.util.ExportUtil;
import net.sf.json.JSONObject;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_alarm_GaTContrastMapper;
/**
 * Created by xhz on 2015/11/18.
 * 枪出罐出对比
 */
@Service("AlarmGaTContrastService")
public class AlarmGaTContrastServiceImpl implements AlarmGaTContrastService {
    @Resource
    private oss_alarm_GaTContrastMapper ossAlarmGaTContrastMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddGaTContrastService(List<oss_alarm_GaTContrast> oss_alarm_gaTContrastList) {
        for (oss_alarm_GaTContrast item:oss_alarm_gaTContrastList)
        {
            ossAlarmGaTContrastMapper.insert(item);
        }
        return 1;
    }
  @Resource
  private oss_sys_OrgUnitMapper ossSysOrgUnitMapper;
    @Resource
    private SysManageDictDao sysManageDictDao;

    @Override
    public List<HashMap<String, Object>> selectGatInfo(Integer page, Integer rows, String begin, String end, String oucode,String result) {

        if (page != null && rows != null) {
            page = page < 1 ? 1 : page;
            int firstRow = (page - 1) * rows;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", rows);
            hashMap.put("begin", begin);
            hashMap.put("end", end);
            if (oucode!=null && oucode!="") {
                hashMap.put("oucode", oucode + "%");
            }
            else
            {
                hashMap.put("oucode", oucode);
            }
            List<HashMap<String, Object>>  list=ossAlarmGaTContrastMapper.selectGatInfo(hashMap);
            List<HashMap<String, Object>> resultList=new ArrayList<HashMap<String, Object>>();
            for (HashMap<String, Object> item:list)
            {

                Integer fz= sysManageDictDao.selectByName("动态液位异常");
                double difference=Double.parseDouble(item.get("Difference").toString());

                if (result.equals("0"))//不录入按照结果查询条件的情况下
                {

                    if (difference < fz) {
                        item.put("result","正常");
                        item.put("ouname", ossSysOrgUnitMapper.selectByOUCode(item.get("OUCode").toString()).getOuname());

                    } else {
                        item.put("result", "异常");
                        item.put("ouname", ossSysOrgUnitMapper.selectByOUCode(item.get("OUCode").toString()).getOuname());

                    }
                    resultList.add(item);
                }
                else
                {
                    if (result.equals("1"))//正常
                    {
                        if (difference  < fz)
                        {

                            item.put("result", "正常");
                            item.put("ouname", ossSysOrgUnitMapper.selectByOUCode(item.get("OUCode").toString()).getOuname());
                            resultList.add(item);
                        }
                    }
                    if (result.equals("2"))//异常
                    {
                        if (difference > fz) {

                            item.put("result", "异常");
                            item.put("ouname", ossSysOrgUnitMapper.selectByOUCode(item.get("OUCode").toString()).getOuname());
                            resultList.add(item);
                        }
                    }
                }

            }

            return  resultList;

        }
        return null;
    }

    @Resource
    private SysDictService sysDictService;

    @Override
    public List<HashMap<String, Object>> selectGatAllInfo(String begin, String end, String oucode, String result) {
        HashMap hashMap = new HashMap();
        hashMap.put("begin", begin);
        hashMap.put("end", end);
        if (oucode!=null && oucode!="") {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }
        List<HashMap<String, Object>>  list=ossAlarmGaTContrastMapper.selectGatAllInfo(hashMap);
        List<HashMap<String, Object>> resultList=new ArrayList<HashMap<String, Object>>();
        String str=result;
        for (HashMap<String, Object> item:list)
        {
            Integer fz= sysManageDictDao.selectByName("动态液位异常");
            double difference=Double.parseDouble(item.get("Difference").toString());
            if (result.equals("0"))//不录入按照结果查询条件的情况下
            {
                if (difference  < fz) {
                    item.put("result","正常");
                    item.put("ouname",ossSysOrgUnitMapper.selectByOUCode(item.get("OUCode").toString()).getOuname());
                } else {
                    item.put("result", "异常");
                    item.put("ouname",ossSysOrgUnitMapper.selectByOUCode(item.get("OUCode").toString()).getOuname());
                }
                resultList.add(item);
            }
            else
            {
                if (result.equals("1"))//正常
                {
                    if (difference  < fz)
                    {

                        item.put("result", "正常");
                        item.put("ouname", ossSysOrgUnitMapper.selectByOUCode(item.get("OUCode").toString()).getOuname());
                        resultList.add(item);
                    }
                }
                if (result.equals("2"))//异常
                {
                    if (difference  > fz) {

                        item.put("result", "异常");
                        item.put("ouname", ossSysOrgUnitMapper.selectByOUCode(item.get("OUCode").toString()).getOuname());
                        resultList.add(item);
                    }
                }
            }

        }
       return resultList;
    }

    @Override
    public void ExportGatContrast(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream)
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
                for (HashMap<String, Object> item:list) {
                    cell = bodyRow.createCell(0);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("ouname").toString());

                    cell = bodyRow.createCell(1);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("OilCan").toString());

                    cell = bodyRow.createCell(2);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("FristMeasureTime").toString());

                    cell = bodyRow.createCell(3);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("FristMeasureStore").toString());


                    cell = bodyRow.createCell(4);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("SecodeMeasureTime").toString());

                    cell = bodyRow.createCell(5);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("SecodeMeasureStore").toString());

                    cell = bodyRow.createCell(6);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("IntervalSales").toString());
                    cell = bodyRow.createCell(7);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("IntervalTime").toString());
                    cell = bodyRow.createCell(8);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("Difference").toString());
                    cell = bodyRow.createCell(9);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("result").toString());
                }
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


}
