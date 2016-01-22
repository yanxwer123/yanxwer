package com.kld.gsm.center.service.impl;

import com.kld.gsm.center.dao.oss_sysmanage_cubageMapper;
import com.kld.gsm.center.domain.oss_sysmanage_cubage;
import com.kld.gsm.center.service.ISysmanageCubageService;
import com.kld.gsm.center.util.ExportUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/1/4 12:29
 * @Description:
 */
@Service
public class SysmanageCubageServiceImpl implements ISysmanageCubageService {

    @Resource
    private oss_sysmanage_cubageMapper cubageMapper;
    @Override
    public List<oss_sysmanage_cubage> getCubages(Map<String, Object> map) {
        return cubageMapper.getCubages(map);
    }

    @Override
    public int getCubageCounts(Map<String, Object> map) {
        return cubageMapper.getCubageCounts(map);
    }

    @Override
    public List<oss_sysmanage_cubage> getCubageVersions(Map<String,Object> map) {
        return cubageMapper.getCubageVersions(map);
    }

    @Override
    public List<Map<String, Object>> getCubageInfos(Map<String, Object> map) {
        return cubageMapper.getCubageInfos(map);
    }

    /**
     * 生成excel文件
     * @param list
     * @param titles
     * @param outputStream
     */
    @Override
    public void ExportCubageInfos(List<Map<String, Object>> list, String[] titles, OutputStream outputStream) {
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
        }
        // 构建表体数据
        if (list != null && list.size() > 0) {
            for (int j = 0; j < list.size(); j++) {
                XSSFRow bodyRow = sheet.createRow(j + 1);
                for (Map<String, Object> item:list) {
                    cell = bodyRow.createCell(0);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("oucode").toString());

                    cell = bodyRow.createCell(1);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("oilcan").toString());

                    cell = bodyRow.createCell(2);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("version").toString());

                    cell = bodyRow.createCell(3);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("liter").toString());

                    cell = bodyRow.createCell(4);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(item.get("height").toString());
                }
            }
        }
        try{
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            try {
                outputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int useCubageSave(Map<String, Object> map) {
        return cubageMapper.useCubageSave(map);
    }
}
