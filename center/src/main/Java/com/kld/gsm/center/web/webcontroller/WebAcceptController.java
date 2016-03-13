package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.domain.ResultMsg;
import com.kld.gsm.center.domain.oss_acceptance_deliveryBill;
import com.kld.gsm.center.domain.oss_acceptance_odRegister;
import com.kld.gsm.center.domain.oss_sys_OrgUnit;
import com.kld.gsm.center.service.*;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/1/4 14:47
 * @Description:进货验收
 */
@Controller
@ApiIgnore
@RequestMapping("/web/acceptance")
public class WebAcceptController extends WebBaseController{
   /* @RequestMapping("/acceptanced")
    public ModelAndView acceptanced(){return new ModelAndView("business/acceptanced");}*/

    @RequestMapping("/accept")
    public ModelAndView accept(){
        return new ModelAndView("business/accept");
    }

    @Resource
    private AcceptanceDeliveryBillService acceptanceDeliveryBillService;
    @RequestMapping( value = "/selectAcceptList",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg SelectAcceptList(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "rows",required = false) Integer rows,
                                      @RequestParam(value = "oucode",required = false) String oucode,
                                      @RequestParam(value = "deliveryno",required = false) String deliveryno,
                                      @RequestParam(value = "psdId",required = false) String psdId,
                                      @RequestParam(value = "startTime",required = false) String startTime,
                                      @RequestParam(value = "endTime",required = false) String endTime,
                                      @RequestParam(value = "yslx",required = false) String yslx,
                                      @RequestParam(value = "startTime1",required = false) String startTime1,
                                      @RequestParam(value = "endTime1",required = false) String endTime1,
                                      @RequestParam(value = "yjssts",required = false) String yjssts
                                      ){
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String,Object>> list=acceptanceDeliveryBillService.selectDeliveryBillPage(intPage, intPageSize,oucode,deliveryno,psdId,startTime,endTime,yslx,startTime1,endTime1,yjssts);
           for (HashMap<String,Object> o:list) {
            HashMap map=new HashMap();
            map.put("oilno", o.get("OilNo").toString());
            String oilname=oilTypeService.selectByoilNo(map);
            if (oilname != null) {
                o.put("OilNo",oilname);
            } else {
                o.put("OilNo", "");
            }
        }
        ResultMsg resultJson=new ResultMsg();
        resultJson.setData(list);
        resultJson.setRows(list);
        resultJson.setTotal(acceptanceDeliveryBillService.selectAllDeliveryBillPage(oucode, deliveryno, psdId, startTime, endTime, yslx, startTime1, endTime1, yjssts).size());
        return  resultJson;
    }

    @Resource
    private SysOrgUnitService sysOrgUnitService;
    //进货验收
    @RequestMapping("/excelAccept")
    @ResponseBody
    public void excelAccept (HttpServletResponse response, String oucode,String deliveryno,String psdId,String startTime,String endTime,String yslx,String startTime1,String endTime1, String yjssts,String FileName){
        response.setContentType("application/binary;charset=ISO8859_1");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName =  java.net.URLDecoder.decode(FileName, "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
            String[] titles = { "出库单号", "配送单号", "油站","油品名称","原发数量（L）","出库时间","出库油库","验收日期","验收状态","实收升数（L）","损溢" };
            List<HashMap<String, Object>> list=acceptanceDeliveryBillService.selectAllDeliveryBillPage(oucode, deliveryno, psdId, startTime, endTime, yslx, startTime1, endTime1, yjssts);
            for (HashMap<String,Object> hashMap:list) {
                oss_sys_OrgUnit model = sysOrgUnitService.selectByOUCode(hashMap.get("OUCode").toString());
                if (model != null) {
                    hashMap.put("ouname", model.getOuname());
                } else {
                    hashMap.put("ouname", "");
                }
            }
            acceptanceDeliveryBillService.JHYS(list, titles, outputStream);
            }catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * div数据
     */
    @Resource
    private AcceptanceService acceptanceService;

    @RequestMapping( value = "/selectList",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg selectList(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "rows",required = false) Integer rows,
                                @RequestParam(value = "deliveryno",required = false) String deliveryno){
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String,Object>> list=acceptanceService.selectAcceptanceServicePage(intPage,intPageSize,deliveryno);
        ResultMsg resultJson=new ResultMsg();
        resultJson.setData(list);
        resultJson.setRows(list);
        resultJson.setTotal(acceptanceService.selectAllAcceptanceServicePage(deliveryno).size());
        return  resultJson;
    }

    /**
     * table数据
     */
    @Resource
    private OilTypeService oilTypeService;
    @RequestMapping("/acceptanced")
    public String selectDeliveryBill(ModelMap modelMap,String ManualNo) {
        oss_acceptance_deliveryBill deliveryBill =acceptanceDeliveryBillService.selectBybillno(ManualNo);
        oss_acceptance_odRegister  odRegister=acceptanceService.selectById(ManualNo);

        //替换油品编号为油品名称
        HashMap map=new HashMap();
        if(null!=deliveryBill && !"".equals(deliveryBill)){
            map.put("oilno", deliveryBill.getOilno());
            String oilname=oilTypeService.selectByoilNo(map);
            deliveryBill.setOilno(oilname);
        }
        modelMap.addAttribute("deliveryBill", deliveryBill);
        modelMap.addAttribute("odRegister", odRegister);
        return "business/acceptanced";
    }
}
