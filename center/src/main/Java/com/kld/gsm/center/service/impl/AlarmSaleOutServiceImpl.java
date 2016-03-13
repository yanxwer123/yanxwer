package com.kld.gsm.center.service.impl;

/**
 * Created by xhz on 2015/11/18.
 * 脱销预警
 */

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_alarm_SaleOut;
import com.kld.gsm.center.service.AlarmSaleOutService;
import com.kld.gsm.center.util.ExportUtil;
import com.kld.gsm.center.util.FormatUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import com.kld.gsm.center.dao.oss_alarm_SaleOutMapper;

@Service("AlarmSaleOutService")
public class AlarmSaleOutServiceImpl implements AlarmSaleOutService {

    @Resource
    private oss_alarm_SaleOutMapper ossAlarmSaleOutMapper;

    @Transactional(rollbackFor = Exception.class)
    public int AddSaleOut(List<oss_alarm_SaleOut> oss_alarm_saleOuts) {
        for (oss_alarm_SaleOut item : oss_alarm_saleOuts) {
            ossAlarmSaleOutMapper.insert(item);
        }
        return 1;
    }

    @Override
    public  List<HashMap<String,Object>> selectSaleOut(HashMap hashmap) {
        List<HashMap<String,Object>> list= ossAlarmSaleOutMapper.selectSaleOut(hashmap);
        for (HashMap<String,Object> hashMap:list) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime= sdf.format(hashMap.get("MesasureTime"));
            hashMap.remove("MesasureTime");
            hashMap.put("MesasureTime", datetime);
        }
        return list;
    }

    @Override
    public List<HashMap<String,Object>>  queryPrepayPageList(Integer pageNo, Integer pageSize,String oucode,String StartAlarmTime,String EndAlarmTime){
        HashMap hashMap = new HashMap();
        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;//三元表达式，如果pageNo小于1，pageNo值就为1 否则就是传递进来的pageNo
            //pageSize = pageSize < 1 ? SystemConstant.PAGESIZE:pageSize; //三元表达式 如果pageSize小于1，就去取枚举里面的pageSize,否则就是传递进来的pageSize
            int firstRow = (pageNo - 1) * pageSize;
      /*      if(prepay==null){
                prepay=new PrepayTicketTradeRecord();
            }
            prepay.setFirstRow(firstRow);
            prepay.setRowSize(pageSize);*/
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
        }
        if (oucode!=null && oucode!="") {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }
        hashMap.put("start", StartAlarmTime);
        hashMap.put("end", EndAlarmTime);
        return ossAlarmSaleOutMapper.queryPrepayPageList(hashMap);
    }

    @Override
    public int queryPrepayCount(String oucode,String StartAlarmTime,String EndAlarmTime) {
        HashMap hashMap = new HashMap();
        if (oucode!=null && oucode!="") {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }
        hashMap.put("start", StartAlarmTime);
        hashMap.put("end", EndAlarmTime);
        return ossAlarmSaleOutMapper.queryPrepayCount(hashMap);
    }

    @Override
    public List<HashMap<String,Object>> queryJYPrepayPageList(Integer pageNo, Integer pageSize,String oucode,String StartAlarmTime,String EndAlarmTime) {
        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;//三元表达式，如果pageNo小于1，pageNo值就为1 否则就是传递进来的pageNo
            //pageSize = pageSize < 1 ? SystemConstant.PAGESIZE:pageSize; //三元表达式 如果pageSize小于1，就去取枚举里面的pageSize,否则就是传递进来的pageSize
            int firstRow = (pageNo - 1) * pageSize;
      /*      if(prepay==null){
                prepay=new PrepayTicketTradeRecord();
            }
            prepay.setFirstRow(firstRow);
            prepay.setRowSize(pageSize);*/
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
            hashMap.put("start", StartAlarmTime);
            hashMap.put("end", EndAlarmTime);
            if(oucode!=null && oucode!="") {
                hashMap.put("oucode", oucode + "%");
            }
            else
            {
                hashMap.put("oucode", oucode);
            }
            return ossAlarmSaleOutMapper.queryJYPrepayPageList(hashMap);
        }
        return  null;
    }

    @Override
    public List<HashMap<String,Object>> queryJYCountPrepayPageList(String oucode,String StartAlarmTime,String EndAlarmTime) {
        HashMap hashMap = new HashMap();
        hashMap.put("start", StartAlarmTime);
        hashMap.put("end", EndAlarmTime);
        if(oucode!=null && oucode!="") {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }
        return ossAlarmSaleOutMapper.queryJYCountPrepayPageList(hashMap);
    }



    //脱销预警
    @Override
    public void ExcelSaleOut(List<HashMap<String,Object>> list, String[] titles, ServletOutputStream outputStream) {
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
                cell.setCellValue(FormatUtil.ConvertToString(goods.get("OUName")));

                cell = bodyRow.createCell(1);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(goods.get("OilName")));

                cell = bodyRow.createCell(2);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(goods.get("StartAlarmTime")));

                cell = bodyRow.createCell(3);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(goods.get("EndAlarmTime")));

                cell = bodyRow.createCell(4);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(goods.get("NowVolume")));

                cell = bodyRow.createCell(5);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(goods.get("CanSaleVolume")));

                cell = bodyRow.createCell(6);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(goods.get("HourAverageSales")));

                cell = bodyRow.createCell(7);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(goods.get("PredictHours")));

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
