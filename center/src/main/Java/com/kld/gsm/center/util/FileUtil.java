package com.kld.gsm.center.util;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/1/9 13:22
 * @Description:
 */
public class FileUtil {
    /**
     * 解析excel文件到List<String>
     * @param url 文件的全路径
     * @return sheet集合, 文件不存在或没有sheet返回null
     * 返回三个List，从外到内 sheetList，rowList，cellList
     */
    public static List<List<List<String>>> excle2List(String url) {
        Logger logger = Logger.getLogger(FileUtil.class);
        String errorMsg = "";
        try {
            url = URLDecoder.decode(url, "utf-8"); //防止服务器路径中包含空格等问题
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String suffix = url.substring(url.lastIndexOf("."));  // 文件后辍.
        List<List<List<String>>> sheetList = null;
        File file = new File(url);
        if (file.exists()) {
            try {
                Workbook workBook = null;
                InputStream is = new FileInputStream(new File(url));
                try {
                    if (".xls".equals(suffix)) { //97-03
                        workBook = new HSSFWorkbook(is);
                    } else if (".xlsx".equals(suffix)) { //2007
                        workBook = new XSSFWorkbook(is);
                    } else {
                        System.out.println("不支持的文件类型！");
                        return null;
                    }
                } catch (Exception e) {
                    System.out.println("解析xls文件出错！");
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                int sheets = null != workBook ? workBook.getNumberOfSheets() : 0;
                if (sheets > 0) {
                    sheetList = new ArrayList<List<List<String>>>();
                    for (int i = 0; i < sheets; i++) {
                        Sheet sheet = workBook.getSheetAt(i); //读取第一个sheet
                        int rows = sheet.getPhysicalNumberOfRows(); // 获得行数
                        List<List<String>> rowList = new ArrayList<List<String>>();
                        if (rows > 1) { //第一行默认为标题
                            for (int j = 1; j < rows; j++) {
                                List<String> cellList = new ArrayList<String>();
                                Row row = sheet.getRow(j);
                                int cells = row.getLastCellNum();// 获得列数
                                if (cells > 0) {
                                    for (int k = 0; k < cells; k++) {
                                        Cell cell = row.getCell(k);
                                        cell.setCellType(Cell.CELL_TYPE_STRING); //全部置成String类型的单元格
                                        cellList.add(cell.getStringCellValue());
                                    }
                                } else {
                                    errorMsg = "第" + (j + 1) + "行数据没有列数为空!";
                                }
                                rowList.add(cellList);
                            }
                        } else {
                            errorMsg = "第" + (i + 1) + "个sheet中数据行数<=1";
                        }
                        sheetList.add(rowList);
                    }
                } else {
                    errorMsg = "没有sheet!";
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            errorMsg = "文件不存在！";
        }
        if (errorMsg.length() > 0) {
            logger.warn("错误消息：" + errorMsg);
        }
        return sheetList;
    }
    //region测试方法
    /*public static void main(String[] args) {
        String fileName = "d:/excel/test.xlsx";
        List<List<List<String>>> sheetList = FileUtil.excle2List(fileName);
        if (null != sheetList) {
            for (int i = 0; i < sheetList.size(); i++) {
                List<List<String>> rowList = sheetList.get(i);
                for (int j = 0; j < rowList.size(); j++) {
                    List<String> cellList = rowList.get(j);
                    System.out.println("油品："+cellList.get(0)+"油罐号："+cellList.get(1)
                    +"版本号："+cellList.get(2)+"高度："+cellList.get(3)+"升数："+cellList.get(4));
                }
            }
        }
    }*/
    //endregion
}
