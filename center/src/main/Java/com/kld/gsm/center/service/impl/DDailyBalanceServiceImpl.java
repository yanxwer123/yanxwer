package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_daily_DailyBalance;
import com.kld.gsm.center.service.DDailyBalanceService;
import com.kld.gsm.center.util.ExportUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_daily_DailyBalanceMapper;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xhz on 2015/11/18.
 * 日平衡表
 */
@Service("DDailyBalanceService")
public class DDailyBalanceServiceImpl implements DDailyBalanceService {
    @Resource
    private oss_daily_DailyBalanceMapper ossDailyDailyBalanceMapper;
    @Transactional(rollbackFor = Exception.class)
    public int AddDailyBalance(List<oss_daily_DailyBalance> oss_daily_dailyBalances) {
        for (oss_daily_DailyBalance item:oss_daily_dailyBalances)
        {
            ossDailyDailyBalanceMapper.insert(item);
        }
        return 1;
    }
    @Override
    public List<HashMap<String, Object>> selectbalancebywhere(String start,String end,String oucode) {
        HashMap map=new HashMap();
        map.put("start",start);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        try {
            date = fmt.parse(end);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            end = fmt.format(calendar.getTime());

        }
        catch (Exception ex)
        {

        }
        map.put("end",end);
        if(oucode!=null && oucode!="") {
            map.put("oucode", oucode + "%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossDailyDailyBalanceMapper.selectbalancebywhere(map);
    }

    @Override
    public List<oss_daily_DailyBalance> selectbalance(String start,String end,String oucode) {
        HashMap map=new HashMap();
        map.put("start",start);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        try {
            date = fmt.parse(end);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            end = fmt.format(calendar.getTime());

        }
        catch (Exception ex)
        {

        }
        map.put("end",end);
        if(oucode!=null && oucode!="") {
            map.put("oucode", oucode + "%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossDailyDailyBalanceMapper.selectbalance(map);
    }

    @Override
    public List<HashMap<String, Object>> querypage(Integer page, Integer rows, String start, String end,String oucode){
        HashMap map=new HashMap();
        if (page != null && rows != null) {
            page = page < 1 ? 1 : page;
            int firstRow = (page - 1) * rows;
            map.put("firstRow", firstRow);
            map.put("pageSize", rows);
        }

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        try {
            date = fmt.parse(end);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            end = fmt.format(calendar.getTime());

        }
        catch (Exception ex)
        {

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
        return ossDailyDailyBalanceMapper.querypage(map);
    }

    @Override
    public void ExportBalance(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream)
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
                HashMap<String, Object> item=list.get(j);
                    cell = bodyRow.createCell(0);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("OUName").toString());

                cell = bodyRow.createCell(1);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(item.get("AccountDate").toString());


                cell = bodyRow.createCell(2);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("DarlyankStock").toString());

                    cell = bodyRow.createCell(3);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("DeliveryNo").toString());

                    cell = bodyRow.createCell(4);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("ReceiveL").toString());

                    cell = bodyRow.createCell(5);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("TodayOut").toString());

                    cell = bodyRow.createCell(6);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("TodayStock").toString());

                    cell = bodyRow.createCell(7);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("RealStock").toString());
                    cell = bodyRow.createCell(8);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("Loss").toString());
                    cell = bodyRow.createCell(9);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("LossSent").toString());
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
