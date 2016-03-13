package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_alarm_DailyLost;
import com.kld.gsm.center.domain.oss_daily_StationShiftInfo;
import com.kld.gsm.center.domain.oss_daily_remoteinventory;
import com.kld.gsm.center.service.AlarmDailyLostService;
import com.kld.gsm.center.dao.oss_alarm_DailyLostMapper;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.kld.gsm.center.util.ExportUtil;
import com.kld.gsm.center.util.FormatUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xhz on 2015/11/18.
 * 日结损溢预警表
 */
@Service("AlarmDailyLostService")
public class AlarmDailyLostServiceImpl implements AlarmDailyLostService {
   @Resource
   private oss_alarm_DailyLostMapper ossAlarmDailyLostMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddDailyLost(List<oss_alarm_DailyLost> alarmDailyLostList) {
        for (oss_alarm_DailyLost item:alarmDailyLostList)
        {
            ossAlarmDailyLostMapper.insert(item);
        }
        return 1;
    }

    @Override
    public List<HashMap<String,Object>> selectDailyLost(HashMap<String, Object> map) {

        List<HashMap<String,Object>> list=ossAlarmDailyLostMapper.selectDailyLost(map);

        return list;
    }

    @Override
    public List<HashMap<String,Object>> selectPageDailyLost(Integer page, Integer rows, String begin, String end, String oucode) {
        if (page != null && rows != null) {
            page = page < 1 ? 1 : page;
            int firstRow = (page - 1) * rows;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", rows);
            hashMap.put("begin",begin);
            hashMap.put("end", end);
            if(oucode!=null && oucode!="") {
                hashMap.put("oucode", oucode + "%");
            }
            else
            {
                hashMap.put("oucode", oucode);
            }
            List<HashMap<String,Object>> list = ossAlarmDailyLostMapper.selectPageDailyLost(hashMap);

            return list;
        }
        return null;
    }
    @Override
    public void ExportDailyLost(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream)
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
                HashMap<String, Object> item=list.get(j);
                    cell = bodyRow.createCell(0);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("OUName").toString()));

                    cell = bodyRow.createCell(1);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("OilName")));

                    cell = bodyRow.createCell(2);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("AccountDate")));

                    cell = bodyRow.createCell(3);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("DarlyankStock")));

                    cell = bodyRow.createCell(4);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("DeliveryNo")));

                    cell = bodyRow.createCell(5);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("ReceiveL")));

                    cell = bodyRow.createCell(6);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("TodayOut")));

                    cell = bodyRow.createCell(7);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("TodayEndStock")));

                    cell = bodyRow.createCell(8);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("RealStock")));
                    cell = bodyRow.createCell(9);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("Cost")));
                    cell = bodyRow.createCell(10);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("CostSent")));
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
