package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.domain.oss_alarm_Equipment;
import com.kld.gsm.center.service.AlarmEquipmentService;
import com.kld.gsm.center.util.ExportUtil;
import com.kld.gsm.center.util.FormatUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kld.gsm.center.dao.oss_alarm_EquipmentMapper;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xhz on 2015/11/18.
 * 设备故障表
 */
@Service("AlarmEquipmentService")
public class AlarmEquipmentServiceImpl implements AlarmEquipmentService {
    @Resource
    private oss_alarm_EquipmentMapper ossAlarmEquipmentMapper;
    @Transactional(rollbackFor=Exception.class)
    public int AddEquipment(List<oss_alarm_Equipment> oss_alarm_equipments) {
        for (oss_alarm_Equipment item:oss_alarm_equipments)
        {
            ossAlarmEquipmentMapper.insert(item);
        }
        return 1;
    }
    //pageNO为NULL
    @Override
    public List<HashMap<String, Object>> queryPrepayPageList(Integer pageNo, Integer pageSize, String FailureType, String StartAlarmTime, String EndAlarmTime,String oucode) {
        HashMap hashMap = new HashMap();
        //pageNO为NULL，取全部
        if (pageNo != null && pageSize != null) {
            pageNo = pageNo < 1 ? 1 : pageNo;//三元表达式，如果pageNo小于1，pageNo值就为1 否则就是传递进来的pageNo
            //pageSize = pageSize < 1 ? SystemConstant.PAGESIZE:pageSize; //三元表达式 如果pageSize小于1，就去取枚举里面的pageSize,否则就是传递进来的pageSize
            int firstRow = (pageNo - 1) * pageSize;

            hashMap.put("firstRow", firstRow);
            hashMap.put("pageSize", pageSize);
        }
        hashMap.put("failuretype", FailureType);
        hashMap.put("start", StartAlarmTime);
        hashMap.put("end", EndAlarmTime);
        if (oucode!=null && oucode!="") {
            hashMap.put("oucode", oucode + "%");
        }
        else
        {
            hashMap.put("oucode", oucode);
        }
        return ossAlarmEquipmentMapper.queryPrepayPageList(hashMap);
    }

    @Override
    public int queryPrepayCount(String FailureType, String StartAlarmTime, String EndAlarmTime,String oucode) {
        HashMap hashMap = new HashMap();
        hashMap.put("failuretype", FailureType);
        hashMap.put("start", StartAlarmTime);
        hashMap.put("end", EndAlarmTime);
        hashMap.put("oucode",oucode);
        return ossAlarmEquipmentMapper.queryPrepayCount(hashMap);
    }

    @Override
    public List<HashMap<String, Object>> selectiq(HashMap hashmap) {
        List<HashMap<String,Object>> list= ossAlarmEquipmentMapper.selectiq(hashmap);
        for (HashMap<String,Object> hashMap:list) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime= sdf.format(hashMap.get("StartAlarmTime"));
            hashMap.remove("StartAlarmTime");
            hashMap.put("StartAlarmTime",datetime);
            datetime= sdf.format(hashMap.get("EndAlarmTime"));
            hashMap.remove("EndAlarmTime");
            hashMap.put("EndAlarmTime",datetime);
        }
        return list;
    }

    @Override
    public List<HashMap<String, Object>> selectEqbywhere(String start,String end,String oucode) {
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
        return ossAlarmEquipmentMapper.selectEqbywhere(map);
    }

    /*{field:'OUName',title:'油站名称',width:100,align:'center'},
    {field:'OilCan',title:'油罐编号',width:100,align:'center'},
    {field:'StartAlarmTime',title:'开始报警时间',width:100,align:'center',formatter: formatDatebox},
    {field:'EndAlarmTime',title:'结束报警时间',width:100,align:'center',formatter: formatDatebox},
    {field:'Name',title:'故障类型',width:100,align:'center'},
    {field:'EquipCode',title:'设备代码',width:100,align:'center'},
    *//*  {field:'MalfunctionCode',title:'故障信息代码',width:100},*//*
    {field:'EquipBrand',title:'设备品牌',width:100,align:'center'},
    {field:'ProbeModel',title:'探棒型号',width:100,align:'center'},
    {field:'Remark',title:'备注',width:100,align:'center'}*/

    @Override
    public void ExcelIQ(List<HashMap<String, Object>> list, String[] titles, ServletOutputStream outputStream)
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
                cell.setCellValue(FormatUtil.ConvertToString(item.get("OilCan")));
                cell = bodyRow.createCell(2);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(item.get("StartAlarmTime")));
                cell = bodyRow.createCell(3);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(item.get("EndAlarmTime")));
                cell = bodyRow.createCell(4);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(item.get("Name")));
                cell = bodyRow.createCell(5);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(item.get("EquipCode")));
                cell = bodyRow.createCell(6);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(item.get("EquipBrand")));
                cell = bodyRow.createCell(7);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(item.get("ProbeModel")));
                cell = bodyRow.createCell(8);
                cell.setCellStyle(bodyStyle);
                cell.setCellValue(FormatUtil.ConvertToString(item.get("Remark")));

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
