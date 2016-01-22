package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_alarm_InventoryMapper;
import com.kld.gsm.center.domain.oss_alarm_Inventory;
import com.kld.gsm.center.service.AlarmInventoryService;
import com.kld.gsm.center.service.SysDictService;
import com.kld.gsm.center.util.ExportUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xhz on 2015/11/18.
 * 库存预警
 */
@Service
public class AlarmInventoryServiceImpl implements AlarmInventoryService {
    @Resource
    private oss_alarm_InventoryMapper ossAlarmInventoryMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddInventory(List<oss_alarm_Inventory> oss_alarm_inventories) {
        for (oss_alarm_Inventory item:oss_alarm_inventories)
        {
            //ossAlarmInventoryMapper.insert(item);
            ossAlarmInventoryMapper.merge(item);
        }
        return 1;
    }
    @Override
    public List<HashMap<String, Object>> selectInventoryInfo(Integer page, Integer rows, String begin, String end, String oucode,String alarmtype)
    {
        try {
            if (page != null && rows != null) {
                page = page < 1 ? 1 : page;
                int firstRow = (page - 1) * rows;
                HashMap hashMap = new HashMap();
                hashMap.put("firstRow", firstRow);
                hashMap.put("pageSize", rows);
                hashMap.put("begin", begin);
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                date = fmt.parse(end);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
                end = fmt.format(calendar.getTime());
                hashMap.put("end", end);
                if(oucode!=null && oucode!="") {
                    hashMap.put("oucode", oucode + "%");
                }
                else
                {
                    hashMap.put("oucode", oucode);
                }
                hashMap.put("alarmtype", alarmtype);
                List<HashMap<String, Object>> list = ossAlarmInventoryMapper.selectInventoryInfo(hashMap);
                return list;
            }
            return null;
        }
        catch (Exception ex)
        {
            return  null;
        }
    }

    @Resource
    private SysDictService sysDictService;

    @Override
    public List<HashMap<String, Object>> selectInventoryAllInfo(String begin, String end, String oucode, String alarmtype) {

           HashMap hashMap = new HashMap();
           hashMap.put("begin", begin);
           Date date = new Date();
           SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = fmt.parse(end);
        }
        catch (Exception ex)
        {

        }
           Calendar calendar = new GregorianCalendar();
           calendar.setTime(date);
           calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
           end = fmt.format(calendar.getTime());
           hashMap.put("end", end);
           if (oucode != null && oucode != "") {
               hashMap.put("oucode", oucode + "%");
           } else {
               hashMap.put("oucode", oucode);
           }
           hashMap.put("alarmtype", alarmtype);
           List<HashMap<String, Object>> list = ossAlarmInventoryMapper.selectInventoryAllInfo(hashMap);
           return list;

    }

    @Override
    public void ExportIntenvory(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream)
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
                    cell.setCellValue(item.get("OUName").toString());

                    cell = bodyRow.createCell(1);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("OilCan").toString());



                    cell = bodyRow.createCell(2);
                    cell.setCellStyle(bodyStyle);
                    //if (item.get("OilName")==null)

                    cell.setCellValue(item.get("OilName") == null ? "" : item.get("OilName").toString());

                    cell = bodyRow.createCell(3);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("NAME").toString());


                    cell = bodyRow.createCell(4);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("StartTime").toString());

                    cell = bodyRow.createCell(5);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("EndTIme").toString());


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
