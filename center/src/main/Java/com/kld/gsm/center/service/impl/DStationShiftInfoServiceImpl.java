package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_daily_StationShiftInfo;
import com.kld.gsm.center.domain.oss_daily_remoteinventory;
import com.kld.gsm.center.service.DStationShiftInfoService;
import com.kld.gsm.center.util.ExportUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_StationShiftInfoMapper;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xhz on 2015/11/18.
 * 油站班报表
 */
@Service("DStationShiftInfoService")
public class DStationShiftInfoServiceImpl implements DStationShiftInfoService {

    @Resource
    private oss_daily_StationShiftInfoMapper ossDailyStationShiftInfoMapper;
    @Transactional(rollbackFor = Exception.class)
    public int AddStationShiftInfo(List<oss_daily_StationShiftInfo> oss_daily_stationShiftInfos) {
       for (oss_daily_StationShiftInfo item:oss_daily_stationShiftInfos)
       {
           ossDailyStationShiftInfoMapper.insert(item);
       }
        return 1;
    }

    @Override
    public List<HashMap<String, Object>> getShiftList(HashMap<String,Object> map) {

        List<HashMap<String,Object>> exchangeList=ossDailyStationShiftInfoMapper.selectShiftInfo(map);

        return exchangeList;
    }

    @Override
    public List<HashMap<String, Object>> selectPageShiftInfo(Integer page, Integer rows, String begin, String end, String oucode) {
        if (page != null && rows != null) {
            page = page < 1 ? 1 : page;
            int firstRow = (page - 1) * rows;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", rows);
            hashMap.put("begin",begin);
            hashMap.put("end", end);
            if (oucode!=null && oucode !="") {
                hashMap.put("oucode", oucode + "%");
            }
            else
            {
                hashMap.put("oucode", oucode);
            }
            List<HashMap<String,Object>> list = ossDailyStationShiftInfoMapper.selectPageShiftInfo(hashMap);

            return list;
        }
        return null;
    }

    @Override
    public void YZBB(List<HashMap<String,Object>> list, String[] titles, ServletOutputStream outputStream)
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
               for (HashMap<String,Object> item:list) {
                   cell = bodyRow.createCell(0);
                   cell.setCellStyle(bodyStyle);
                   cell.setCellValue(item.get("ouname").toString());

                   cell = bodyRow.createCell(1);
                   cell.setCellStyle(bodyStyle);
                   cell.setCellValue(item.get("Shift").toString());

                   cell = bodyRow.createCell(2);
                   cell.setCellStyle(bodyStyle);
                   cell.setCellValue(item.get("Successor").toString());

                   cell = bodyRow.createCell(3);
                   cell.setCellStyle(bodyStyle);
                   cell.setCellValue(item.get("SucceedTIme").toString());

                   cell = bodyRow.createCell(4);
                   cell.setCellStyle(bodyStyle);
                   cell.setCellValue(item.get("ShiftOperator").toString());

                   cell = bodyRow.createCell(5);
                   cell.setCellStyle(bodyStyle);
                   cell.setCellValue(item.get("ShiftTime").toString());

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
