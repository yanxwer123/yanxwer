package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_alarm_ShiftLost;
import com.kld.gsm.center.service.AlarmShiftLostService;
import com.kld.gsm.center.util.ExportUtil;
import com.kld.gsm.center.util.FormatUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import com.kld.gsm.center.dao.oss_alarm_ShiftLostMapper;
/**
 * Created by xhz on 2015/11/17.
 * 交接班损溢预警
 */
@Service("AlarmShiftLostService")
public class AlarmShiftLostServiceImpl implements AlarmShiftLostService {
    @Resource
    private oss_alarm_ShiftLostMapper ossAlarmShiftLostMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddAlarmShiftLost(List<oss_alarm_ShiftLost> alarmShiftLost ) {
        for(oss_alarm_ShiftLost item:alarmShiftLost){
            ossAlarmShiftLostMapper.insert(item);
        }
        return 1;
    }
    @Override
    public List<HashMap<String, Object>> selectshiftlostbywhere(String start,String end,String oucode) {
        HashMap map=new HashMap();
        map.put("start",start);
        map.put("end",end);
        if(oucode!=null && oucode!="") {
            map.put("oucode", oucode + "%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossAlarmShiftLostMapper.selectshiftlostbywhere(map);
    }

    @Override
    public List<HashMap<String, Object>> querypage(Integer page, Integer rows, String oucode, String start, String end) {
        HashMap map=new HashMap();
        if (page != null && rows != null) {
            page = page < 1 ? 1 : page;
            int firstRow = (page - 1) * rows;
            map.put("firstRow", firstRow);
            map.put("pageSize", rows);
        }
        map.put("start",start);
        map.put("end",end);
        if(oucode!=null && oucode!="") {
            map.put("oucode", oucode + "%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossAlarmShiftLostMapper.querypage(map);
    }

    @Override
    public List<HashMap<String, Object>> selectAlarmCount(String start, String oucode) {
        HashMap map=new HashMap();
        map.put("start",start);
        if(oucode!=null && oucode!="") {
            map.put("oucode", oucode + "%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossAlarmShiftLostMapper.selectAlarmCount(map);
    }

    @Override
    public void ExportShiftLost(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream)
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
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("OUName")));

                    cell = bodyRow.createCell(1);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("Shift")));

                    cell = bodyRow.createCell(2);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("ShiftTime")));

                    cell = bodyRow.createCell(3);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("OilCanNo")));

                    cell = bodyRow.createCell(4);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("OilName")));

                    cell = bodyRow.createCell(5);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("StartOilHeight")));

                    cell = bodyRow.createCell(6);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("StartOilL")));

                    cell = bodyRow.createCell(7);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("EndOilHeight")));

                    cell = bodyRow.createCell(8);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("EndOilL")));


                    cell = bodyRow.createCell(9);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("OilDischarge")));

                    cell = bodyRow.createCell(10);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("Sale")));

                    cell = bodyRow.createCell(11);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("Loss")));

                    cell = bodyRow.createCell(12);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(FormatUtil.ConvertToString(item.get("ProfitLossRatio")));
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
