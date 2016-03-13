package com.kld.gsm.center.web.webcontroller.basicdata;

import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.service.*;
import com.kld.gsm.center.util.JsonMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-09 21:22
 * @Description:
 */
@Controller
@RequestMapping("/web/basicdata")
public class GasStationController {
    @Resource
    private TankInfoService tankInfoService;
    @Resource
    private SysOrgUnitService sysOrgUnitService;
    @Resource
    private SysDepartmentService sysDepartmentService;
    @Resource
    private OilGunInfoService oilGunInfoService;
    @Resource
    private AlarmParmeterService alarmParmeterService;

    /**
     * second level
     * Three level
     *
     * @return
     */
    @RequestMapping("/loadpage")
    public String basicdata() {
        return "basicdata/GasStationInfor";
    }

    @ResponseBody
    @RequestMapping("/findallstation")
    public List findbsasic() {
        //总集合
        List list = new ArrayList();
        //一级
        List<oss_sys_OrgUnit> fristLevel = sysOrgUnitService.selectByPOUCode("100");
        for (oss_sys_OrgUnit ossSysOrgUnit : fristLevel) {
            Tree tree = frist(ossSysOrgUnit.getOucode(), ossSysOrgUnit.getOuname(), "open", "wordnik_api.png");
            list.add(tree);
        }
        return list;
    }


    @RequestMapping("/basicdatamanage")
    public String SelectAcceptList(ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
        System.out.println("basicdata:" + id);
        //基础信息
        if (!id.equals("undefined")) {
            oss_sysmanage_department oss_sysmanage_department = sysDepartmentService.findByOUCode(id);
            if (oss_sysmanage_department != null) {
                modelMap.put("department", oss_sysmanage_department);
            }
        }

        List tankInfo = new ArrayList();
        for (oss_sysmanage_TankInfo oss_sysmanage_tankInfo : tankInfoService.findAll()) {
            if (id.equals(oss_sysmanage_tankInfo.getOucode())) {
                System.out.println("tankinfo : " + oss_sysmanage_tankInfo.getOilcan());
                tankInfo.add(oss_sysmanage_tankInfo.getOilcan());
            }
        }
        modelMap.put("tankInfo", tankInfo);
        return "basicdata/GasStationInfor";
    }

    @ResponseBody
    @RequestMapping("/gasgun")
    public ResultMsg findgasGun(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "rows", required = false) Integer rows,
                                ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
        ResultMsg resultMsg = new ResultMsg();
        System.out.println("gasgun id:" + id);
        if (id.equals("null")) {
            return resultMsg;
        }
        //设置当前页
        int intPage = page == null || page <= 0 ? 1 : page;
        //设置每页显示的数量
        int intPageSize = rows == null || rows <= 0 ? 10 : rows;
        Map map = new HashMap();
        map.put("intPage", intPage);
        map.put("intPageSize", intPageSize);
        map.put("id", id);


        //油枪信息

        List<GasGunManage> gasGunManages = oilGunInfoService.findByOUCodePage(map);
       /* for (GasGunManage gasGunManage : gasGunManages) {
            System.out.println("油枪信息： " + gasGunManage.toString());
        }*/

        resultMsg.setRows(gasGunManages);
        resultMsg.setData(gasGunManages);
        resultMsg.setTotal(oilGunInfoService.findByOUCode(id).size());
        return resultMsg;
    }

    @ResponseBody
    @RequestMapping("/peralarm")
    public ResultMsg perAlarm(@RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "rows", required = false) Integer rows,
                              ModelMap modelMap, @RequestParam(value = "id", required = false) String id) {
        ResultMsg resultMsg = new ResultMsg();
        System.out.println("peralame id:" + id);
        if (id.equals("null")) {
            return resultMsg;
        }
        //设置当前页
        int intPage = page == null || page <= 0 ? 1 : page;
        //设置每页显示的数量
        int intPageSize = rows == null || rows <= 0 ? 10 : rows;
        Map map = new HashMap();
        map.put("intPage", intPage);
        map.put("intPageSize", intPageSize);
        map.put("id", id);


        //油枪信息

        List<PreAlarm> preAlarms = alarmParmeterService.findByOUCodePage(map);
        for (PreAlarm preAlarm : preAlarms) {
            System.out.println("预报警信息： " + preAlarm.getOilcan());
        }
        resultMsg.setRows(preAlarms);
        resultMsg.setData(preAlarms);
        resultMsg.setTotal(alarmParmeterService.findByOUCode(id).size());
        return resultMsg;
    }

    //TODO ----------------------------------------
    @RequestMapping(value = "/peralarm/add")
    @ResponseBody
    public ResultMsg updateNewRequest(oss_sysmanage_AlarmParameter oss_sysmanage_alarmParameter, ResultMsg resultMsg) throws Exception {
        //判断油站编码和网点编码不等于空，
        if (null != oss_sysmanage_alarmParameter.getOucode() && !"".equals(oss_sysmanage_alarmParameter.getOucode())
                && null != oss_sysmanage_alarmParameter.getNodeno() && !"".equals(oss_sysmanage_alarmParameter.getNodeno())) {
            oss_sysmanage_alarmParameter.setOptime(new Date());
            int i ;
            try{
             i =   alarmParmeterService.insert(oss_sysmanage_alarmParameter);

            }catch (Exception e){
                System.err.println("预报警添加异常 \n"+e);
                resultMsg.setResult(false);
                resultMsg.setMsg("网点编号唯一");
                return resultMsg;
            }

            if (i == 1) {
                resultMsg.setResult(true);
                resultMsg.setMsg("添加成功");
            } else {
                resultMsg.setResult(false);
                resultMsg.setMsg("添加失败");
            }
        }


        return resultMsg;
    }
    //TODO ----------------------------------------


    /**
     * 一级
     *
     * @param id      OUCode
     * @param name
     * @param state
     * @param iconcls
     * @return
     */
    public Tree frist(String id, String name, String state, String iconcls) {
        Tree tree = new Tree();
        tree.setId(1);
        tree.setText(name);
        tree.setState(state);
        tree.setIconCls(iconcls);
        //region  根据OUCode  查询其三级菜单
        List<Children> three = new ArrayList<Children>();
        List<oss_sys_OrgUnit> secondLevel = sysOrgUnitService.selectByPOUCode(id);
        for (oss_sys_OrgUnit ossSysOrgUnit : secondLevel) {
            List<oss_sys_OrgUnit> threeOUnit = sysOrgUnitService.selectByPOUCode(ossSysOrgUnit.getOucode());
            for (oss_sys_OrgUnit threeOrgUnit : threeOUnit) {
                three.add(second(3, threeOrgUnit.getOuname(), threeOrgUnit.getOucode()));
            }
        }
        tree.setChildren(three);

        //endregion

        return tree;
    }

    /**
     * 二级
     *
     * @param id
     * @param name
     * @param attributes
     * @return
     */
    public static Children second(int id, String name, String attributes) {
        Children children = new Children();
        children.setId(id);
        children.setText(name);

        children.setAttributes(attributes);
        return children;
    }

}

class Tree {
    int id;//
    String text;//Name
    String state;// 状态  open|closed
    String iconCls;//图标
    List children;//下级

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                ", iconCls='" + iconCls + '\'' +
                ", children=" + children +
                '}';
    }
}


class Children {
    int id;
    String text;
    String attributes;

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Children{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", attributes='" + attributes + '\'' +
                '}';
    }
}