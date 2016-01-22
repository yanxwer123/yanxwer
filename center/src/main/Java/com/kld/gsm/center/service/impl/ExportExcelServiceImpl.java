package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.service.AlarmSaleOutService;
import com.kld.gsm.center.service.ExportExcelService;
import com.kld.gsm.center.util.ExportUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fangzhun on 2015/12/5.
 */
@Service("ExportExcelService")
public class ExportExcelServiceImpl implements ExportExcelService {

    @Resource
    private AlarmSaleOutService alarmSaleOutService;
    //脱销预警
    @Override
    public void TXYJexportExcel(List<HashMap<String,Object>> list, String[] titles, ServletOutputStream outputStream) {
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
                     HashMap<String,Object> goods = list.get(j);

                     cell = bodyRow.createCell(0);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("STATION_NAME").toString());

                     cell = bodyRow.createCell(1);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("OilName").toString());

                     cell = bodyRow.createCell(2);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("MesasureTime").toString());

                     cell = bodyRow.createCell(3);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("NowVolume").toString());

                     cell = bodyRow.createCell(4);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("CanSaleVolume").toString());

                     cell = bodyRow.createCell(5);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("DayAverageSales").toString());

                     cell = bodyRow.createCell(6);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("HourAverageSales").toString());

                     cell = bodyRow.createCell(7);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("PredictHours").toString());

                     cell = bodyRow.createCell(8);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("YJSSTS").toString());

                     cell = bodyRow.createCell(9);
                     cell.setCellStyle(bodyStyle);
                     cell.setCellValue(goods.get("PSYJ").toString());

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
    //设备故障报警
    @Override
    public void SBGZYJexportExcel(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream) {
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
                HashMap<String,Object> goods = list.get(j);

                cell = bodyRow.createCell(0);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(goods.get("OUName").toString());

                cell = bodyRow.createCell(1);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(goods.get("OilCan").toString());

                cell = bodyRow.createCell(2);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(goods.get("StartAlarmTime").toString());

                cell = bodyRow.createCell(3);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(goods.get("EndAlarmTime").toString());

                cell = bodyRow.createCell(4);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(goods.get("Name").toString());

                cell = bodyRow.createCell(5);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(goods.get("EquipCode").toString());



                cell = bodyRow.createCell(6);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(goods.get("EquipBrand").toString());

                cell = bodyRow.createCell(7);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(goods.get("ProbeModel").toString());

                cell = bodyRow.createCell(8);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(goods.get("Remark").toString());

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
