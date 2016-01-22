package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_alarm_measureLeak;
import com.kld.gsm.center.domain.oss_daily_StationShiftInfo;
import com.kld.gsm.center.service.AlarmMeasureLeakService;
import com.kld.gsm.center.util.ExportUtil;
import org.apache.poi.xssf.usermodel.*;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_alarm_measureLeakMapper;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 测漏表
 */
@Service("AlarmMeasureLeakService")
public class AlarmMeasureLeakServiceImpl implements AlarmMeasureLeakService {
    @Resource
    private oss_alarm_measureLeakMapper ossAlarmMeasureLeakMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddMeasureLeak(List<oss_alarm_measureLeak> oss_alarm_measureLeaks) {
        for (oss_alarm_measureLeak item:oss_alarm_measureLeaks)
        {
            ossAlarmMeasureLeakMapper.insert(item);
        }
        return 1;
    }

    @Override
    public List<HashMap<String, Object>> selectmeasurebywhere(String start,String end,String oucode) {
        HashMap map=new HashMap();
        map.put("start",start);
        map.put("end",end);
        if (oucode!=null && oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossAlarmMeasureLeakMapper.selectmeasurebywhere(map);
    }

    @Override
    public List<HashMap<String, Object>> querypage(Integer intPage, Integer intPageSize, String oucode, String start, String end) {
        HashMap map=new HashMap();
        map.put("start",start);
        map.put("end",end);
        if (oucode!=null && oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        map.put("firstRow",intPage);
        map.put("intPageSize",intPageSize);
        return ossAlarmMeasureLeakMapper.querypage(map);
    }

    @Override
    public void ExportMeasureLeak(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream)
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
                    cell.setCellValue(item.get("OilCanNo").toString());

                    cell = bodyRow.createCell(2);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("Status").toString());

                    cell = bodyRow.createCell(3);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("RevealRate").toString());

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cell = bodyRow.createCell(4);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("StartDate").toString());

                    cell = bodyRow.createCell(5);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("StartOilHeight").toString());

                    cell = bodyRow.createCell(6);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("StartWaterHeight").toString());
                    cell = bodyRow.createCell(7);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("StartOilTemp1").toString());
                    cell = bodyRow.createCell(8);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("StartOilTemp2").toString());
                    cell = bodyRow.createCell(9);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("StartOilTemp3").toString());
                    cell = bodyRow.createCell(10);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("StartOilTemp4").toString());
                    cell = bodyRow.createCell(11);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("StartOilTemp5").toString());
                    cell = bodyRow.createCell(12);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("EndDate").toString());
                    cell = bodyRow.createCell(13);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("EndOilHeight").toString());
                    cell = bodyRow.createCell(14);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("EndWaterHeight").toString());
                    cell = bodyRow.createCell(15);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("EndOilTemp1").toString());
                    cell = bodyRow.createCell(16);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("EndOilTemp2").toString());
                    cell = bodyRow.createCell(17);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("EndOilTemp3").toString());
                    cell = bodyRow.createCell(18);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("EndOilTemp4").toString());
                    cell = bodyRow.createCell(19);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("EndOilTemp5").toString());
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
