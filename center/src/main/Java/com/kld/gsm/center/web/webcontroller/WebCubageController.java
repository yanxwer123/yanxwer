package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_sys_OrgUnit;
import com.kld.gsm.center.domain.oss_sysmanage_cubage;
import com.kld.gsm.center.service.ISysmanageCubageService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/*
Created BY niyang
Created Date 2015/11/24
description： 容积表
*/
@Controller
@ApiIgnore
@RequestMapping("/web/cubage")
public class WebCubageController  extends WebBaseController {

    @Resource
    private ISysmanageCubageService sysmanageCubageService;

    /*容积表管理*/
    @RequestMapping("/mgr")
    public ModelAndView cuaggemgr() {
        return new ModelAndView("/cubage/mgr");
    }

    /*容积表分析*/
    @RequestMapping("/analysis")
    public ModelAndView cuaggeanalysis() {
        return new ModelAndView("/cubage/analysis");
    }
    @RequestMapping("/upload")
    public ModelAndView upload() {
        return new ModelAndView("/upload");
    }

    /**
     * 版本查询
     *
     * @return
     */
    @RequestMapping("/cubageVersions")
    public ModelAndView cubageVersions() {
        return new ModelAndView("/cubage/cubageVersions");
    }

    /**
     * 查询容积表
     *
     * @param page
     * @param rows
     * @param sfgx
     * @param oucode
     * @return
     */
    @RequestMapping(value = "/getCubages", method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg getCubages(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, @RequestParam(value = "sfgx", required = false) String sfgx, @RequestParam(value = "oucode", required = false) String oucode) {
        //设置当前页
        int intPage = page == null || page <= 0 ? 1 : page;
        //设置每页显示的数量
        int intPageSize = rows == null || rows <= 0 ? 10 : rows;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("intPage", intPage);
        map.put("intPageSize", intPageSize);
        map.put("sfgx", sfgx);
        map.put("oucode", oucode + "%");
        int count = sysmanageCubageService.getCubageCounts(map);
        List<oss_sysmanage_cubage> list = sysmanageCubageService.getCubages(map);
        if (list != null) {
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            result.setTotal(count);
            return result;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/getCubageVersions", method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg getCubageVersions(@RequestParam(value = "oilcanno", required = false) int oilcanno, @RequestParam(value = "oucode", required = false) String oucode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("oucode", oucode);
        map.put("oilcanno", oilcanno);
        List<oss_sysmanage_cubage> list = sysmanageCubageService.getCubageVersions(map);
        if (list != null) {
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            return result;
        }
        return null;
    }

    //region 导出容积表

    /**
     * 导出容积表
     *
     * @param response
     * @param oilcanno
     * @param version
     * @param oucode
     * @param FileName
     */
    @RequestMapping(value = "/excelCubageInfos", method = RequestMethod.GET)
    @ResponseBody
    public void excelCubageInfos(HttpServletResponse response, int oilcanno, int version, String oucode, String FileName) {
        response.setContentType("application/binary;charset=ISO8859_1");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = {"油站名称", "油罐号", "版本号", "高度", "升数"};
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("oucode", oucode);
            map.put("oilcanno", oilcanno);
            map.put("version", version);
            List<Map<String, Object>> list = sysmanageCubageService.getCubageInfos(map);
            sysmanageCubageService.ExportCubageInfos(list, titles, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    @RequestMapping(value = "/useCubageSave", method = RequestMethod.GET)
    @ResponseBody
    public void useCubageSave(int oilcanno, int version, String oucode) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("oilcanno",oilcanno);
        map.put("version",version);
        map.put("oucode",oucode);
        sysmanageCubageService.useCubageSave(map);
    }
}