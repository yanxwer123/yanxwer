package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_alarm_OilInContrast;
import com.kld.gsm.center.service.AlarmOilInContrastService;
import com.kld.gsm.center.util.ExportUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_alarm_OilInContrastMapper;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 进油损耗对比表
 */
@Service("AlarmOilInContrastService")
public class AlarmOilInContrastServiceImpl implements AlarmOilInContrastService {
    @Resource
    private oss_alarm_OilInContrastMapper ossAlarmOilInContrastMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddOilInContrast(List<oss_alarm_OilInContrast> oss_alarm_oilInContrasts) {
        for (oss_alarm_OilInContrast item:oss_alarm_oilInContrasts)
        {
            ossAlarmOilInContrastMapper.insert(item);
        }
        return 1;
    }

    @Override
    public List<HashMap<String, Object>> selectoilincontrastbywhere(String start,String end,String oucode) {
        HashMap map=new HashMap();
        map.put("start",start);
        map.put("end",end);
        if (oucode!=null && oucode!="") {
            map.put("oucode", oucode + "%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossAlarmOilInContrastMapper.selectoilincontrastbywhere(map);
    }

    @Override
    public List<HashMap<String, Object>> querypage(Integer intPage, Integer intPageSize, String oucode, String start, String end) {
        HashMap map=new HashMap();
        map.put("start",start);
        map.put("end",end);
        if (oucode!=null && oucode!="") {
            map.put("oucode", oucode + "%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        map.put("firstRow",intPage);
        map.put("intPageSize",intPageSize);
        return ossAlarmOilInContrastMapper.querypage(map);
    }

    @Override
    public void ExportOilInContrast(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream)
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
                    cell.setCellValue(item.get("DeliveryNo").toString());

                    cell = bodyRow.createCell(2);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("PlanL").toString());

                    cell = bodyRow.createCell(3);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("RealRecieve").toString());

                    cell = bodyRow.createCell(4);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("Loss").toString());

                    cell = bodyRow.createCell(5);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("LossRate").toString());
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
