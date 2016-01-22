package com.kld.gsm.center.service;

import javax.servlet.ServletOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fangzhun on 2015/12/5.
 */
public interface ExportExcelService {
    public void TXYJexportExcel(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);
    public void SBGZYJexportExcel(List<HashMap<String,Object>> list,String [] titles,ServletOutputStream outputStream);
}
