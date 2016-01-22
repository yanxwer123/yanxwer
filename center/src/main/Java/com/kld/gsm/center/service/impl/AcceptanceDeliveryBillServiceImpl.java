package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_acceptance_deliveryBillMapper;
import com.kld.gsm.center.dao.oss_alarm_DailyLostMapper;
import com.kld.gsm.center.domain.oss_acceptance_deliveryBill;
import com.kld.gsm.center.domain.oss_alarm_DailyLost;
import com.kld.gsm.center.service.AcceptanceDeliveryBillService;
import com.kld.gsm.center.util.ExportUtil;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 5 on 2015/11/19.
 */
@Service
public class AcceptanceDeliveryBillServiceImpl implements AcceptanceDeliveryBillService {
    private static final Logger LOG = Logger.getLogger("taskpolling");
    @Resource
    private oss_acceptance_deliveryBillMapper ossAcceDeliveryBillMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddDeliveryBill(List<oss_acceptance_deliveryBill> accdeliveryBillList) {
        for (oss_acceptance_deliveryBill item:accdeliveryBillList)
        {
            try {
                ossAcceDeliveryBillMapper.insert(item);
            }catch (Exception e){
                LOG.error("插入主键已存在的数据。");
            }
        }
        return 1;
    }

    /**
     * 根据出库单号 查询
     *
     * @param billno
     */
    @Override
    public oss_acceptance_deliveryBill selectBybillno(String billno) {
       return ossAcceDeliveryBillMapper.selectByPrimaryKey(billno);
    }

    @Override
    public int updatedeliverybill(oss_acceptance_deliveryBill record) {
        ossAcceDeliveryBillMapper.updateByPrimaryKeySelective(record);
        return 1;
    }

    @Override
    public List<HashMap<String, Object>> selectDBill(String oiltype, String oucode) {
        HashMap map=new HashMap();
        map.put("oiltype", oiltype);

        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossAcceDeliveryBillMapper.selectDBill(map);
    }
    @Override
    public List<HashMap<String, Object>> selectJHL(String oiltype,String start,String end, String oucode) {
        HashMap map=new HashMap();
        map.put("oiltype", oiltype);
        map.put("start",start);
        map.put("end",end);
        if (oucode!=null&&oucode!="") {
            map.put("oucode", oucode+"%");
        }
        else
        {
            map.put("oucode", oucode);
        }
        return ossAcceDeliveryBillMapper.selectDBill(map);
    }

    @Override
    public List<HashMap<String, Object>> selectDeliveryBillPage(Integer pageNo, Integer pageSize, String oucode, String deliveryno,String psdId, String startTime, String endTime, String yslx, String startTime1, String endTime1, String yjssts) {

        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;
            int firstRow = (pageNo - 1) * pageSize;
            HashMap hashMap = new HashMap();
            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
            hashMap.put("psdId", psdId);
            hashMap.put("ManualNo", deliveryno);
            hashMap.put("startTime", startTime);
            hashMap.put("endTime", endTime);
            hashMap.put("yslx", yslx);
            hashMap.put("startTime1", startTime1);
            hashMap.put("endTime1", endTime1);
            hashMap.put("yjssts", yjssts);
            if(oucode!=null && oucode!="") {
                hashMap.put("oucode", oucode + "%");
            }
            else
            {
                hashMap.put("oucode", oucode);
            }
            return ossAcceDeliveryBillMapper.selectDeliveryBillPage(hashMap);
        }
        return null;
    }

    @Override
    public List<HashMap<String, Object>> selectAllDeliveryBillPage(String oucode, String deliveryno, String psdId, String startTime, String endTime, String yslx, String startTime1, String endTime1, String yjssts) {

        HashMap hashMap = new HashMap();
        hashMap.put("pageSize", psdId);
        hashMap.put("pageSize", deliveryno);
        hashMap.put("pageSize", startTime);
        hashMap.put("pageSize", endTime);
        hashMap.put("pageSize", yslx);
        hashMap.put("pageSize", startTime1);
        hashMap.put("pageSize", endTime1);
        hashMap.put("pageSize", yjssts);
        if(oucode!=null && oucode!="") {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }
        return ossAcceDeliveryBillMapper.selectAllDeliveryBillPage(hashMap);
    }

    @Override
    public void JHYS(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream) {

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
        // 构建表体数据、
        if (list != null && list.size() > 0) {
            for (int j = 0; j < list.size(); j++)
            {
                XSSFRow bodyRow = sheet.createRow(j + 1);
                //List<oss_daily_StationShiftInfo> goods = list.get(j);
                for (HashMap<String, Object> item:list) {
                    cell = bodyRow.createCell(0);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("ManualNo").toString());

                    cell = bodyRow.createCell(1);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("PSD_ID").toString());

                    cell = bodyRow.createCell(2);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("OUCode").toString());

                    cell = bodyRow.createCell(3);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("OilNo").toString());

                    cell = bodyRow.createCell(4);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("Planl").toString());

                    cell = bodyRow.createCell(5);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("DeliveryTime").toString());

                    cell = bodyRow.createCell(6);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("FromDepotName").toString());

                    cell = bodyRow.createCell(7);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("begintime").toString());

                    cell = bodyRow.createCell(8);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("IsComplete").toString());

                    cell = bodyRow.createCell(9);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("realgetlv20").toString());

                    cell = bodyRow.createCell(10);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("DischargeLossV20").toString());
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
