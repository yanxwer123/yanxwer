package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.domain.*;
import com.kld.gsm.center.service.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/1/15 15:17
 * @Description:
 */
@Controller
@RequestMapping("/web/sysmanage")
public class WebSysmanageController extends WebBaseController{
    @Resource
    private SysDepartmentService sysDepartmentService;
    @Resource
    private TankInfoService tankInfoService;
    @Resource
    private SysOrgUnitService sysOrgUnitService;
    @Resource
    private UserService userService;
    @Resource
    private Sys_funcService sys_funcService;
    @Resource
    private Sys_roleService sys_roleService;
    @Resource
    private Sys_rolefuncrelService sys_rolefuncrelservice;
    @Resource
    private Sys_userroleService sys_userroleService;
    @Resource
    private SysDictService sysDictService;

    @RequestMapping("/loadpage")
    public ModelAndView loadpage(){
        return new ModelAndView("sysmanage/sysmanage");}

    @RequestMapping("/catalog")
    public ModelAndView catalog(){
        return new ModelAndView("sysmanage/catalog");}

    @RequestMapping("/sysmanage")
    public String sysmanage(ModelMap modelMap,@RequestParam(value = "id", required = false) String id){
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
        return "sysmanage/sysmanage";
    }

    @RequestMapping("/dict")
    public ModelAndView dict(){return new ModelAndView("sysmanage/dict");}


    @RequestMapping( value = "/getDictList",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg getDictList(@RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "rows",required = false) Integer rows,
                                 @RequestParam(value = "dictID",required = false) Integer dictID,
                                 @RequestParam(value = "parentID",required = false) Integer parentID,
                                 @RequestParam(value = "name",required = false) String name) {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String,Object>> list=sysDictService.getDictList(intPage, intPageSize, dictID, parentID, name);
        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            result.setTotal(sysDictService.getDictAllList(dictID, parentID, name).size());
            return result;
        }
        else {
            return null;
        }
    }

    @RequestMapping( value = "/getCatalogList",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg GetShiftList(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "rows",required = false) Integer rows
                                  ) {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String,Object>> list=sys_funcService.getCatalogList(intPage, intPageSize);
        if(list!=null){
            ResultMsg result = new ResultMsg();
            result.setData(list);
            result.setRows(list);
            result.setTotal(sys_funcService.getCatalogAllList().size());
            return result;
        }
        else {
            return null;
        }
    }

   @ResponseBody
   @RequestMapping("/findallUserRole")
   public List findUserRole() {
       Tree tree = new Tree();
       // /总集合
       List list = new ArrayList();
       //一级
       List<Children> UserRole = new ArrayList<Children>();
       List<Sys_role> fristLevel = sys_roleService.selectAll();
       for (Sys_role sysRole : fristLevel) {
           UserRole.add(second(Integer.parseInt(sysRole.getRolecode()), sysRole.getName(),sysRole.getOucode()));
       }
        tree.setChildren(UserRole);
       list.add(tree);
       return list;
   }



    @ResponseBody
    @RequestMapping("/findallRoleCatalog")
    public List findallRoleCatalog() {
        //总集合
        List list = new ArrayList();
        //一级
        List<Sys_func> fristLevel = sys_funcService.selectBycode("1");
        for (Sys_func sysfunc : fristLevel) {
            Tree tree = RoleCatalogfrist(sysfunc.getFunccode(), sysfunc.getName(), "open", "wordnik_api.png");
            list.add(tree);
        }
        return list;
    }

    public Tree RoleCatalogfrist(String id, String name, String state, String iconcls) {
        Tree tree = new Tree();
        tree.setId(Integer.parseInt(id));
        tree.setText(name);
        tree.setState(state);
        tree.setIconCls(iconcls);
        //region    查询其二级菜单
        List<Children> RoleCatalogtwoe = new ArrayList<Children>();
        List<Sys_func> RoleCatalogsecondLevel = sys_funcService.selectBycode(id);
        for (Sys_func sysfunc : RoleCatalogsecondLevel) {
            RoleCatalogtwoe.add(second(Integer.parseInt(sysfunc.getFunccode()), sysfunc.getName(), String.valueOf(sysfunc.getOrderno())));
        }
        tree.setChildren(RoleCatalogtwoe);
        return tree;
    }

    @ResponseBody
    @RequestMapping("/selectRC")
    public  List selectRC(String rolecode){
        List<Sys_rolefuncrel> sysRolefuncrel = sys_rolefuncrelservice.selectByrolecode(rolecode);
        List list=new ArrayList();
      for(Sys_rolefuncrel sysRC :sysRolefuncrel){
          list.add(sysRC.getFunccode());
    }
        return list;
    }
    @ResponseBody
    @RequestMapping("/selectRCBycode")
    public  List selectRCBycode(String delfunccode){
        List<Sys_rolefuncrel> sysRolefuncrel = sys_rolefuncrelservice.selectRCBycode(delfunccode);
        List list=new ArrayList();
        for(Sys_rolefuncrel sysRC :sysRolefuncrel){
            list.add(sysRC.getRolecode());
        }
        return list;
    }
    @ResponseBody
    @RequestMapping("/saveRC")
    public  int saveRC(String mycars,String roleid){
        List<Sys_rolefuncrel> list = new ArrayList<Sys_rolefuncrel>();
        StringTokenizer st=new StringTokenizer(mycars,",");
        while(st.hasMoreTokens()){
            Sys_rolefuncrel rolefuncrel=new Sys_rolefuncrel();
            String funccode=st.nextToken();
            rolefuncrel.setFunccode(funccode);
            rolefuncrel.setRolecode(roleid);
            list.add(rolefuncrel);
        }
        int deleteRc = sys_rolefuncrelservice.deleteRoleFuncByRolecode(roleid);
        int insertRc=sys_rolefuncrelservice.insertList(list);
        return insertRc;
    }
    @ResponseBody
        @RequestMapping("/selectUR")
        public  List selectUR(String userids){
        List<Sys_userrole> sysUserRole = sys_userroleService.selectByUserids(userids);
        List list=new ArrayList();
        for(Sys_userrole sysUR :sysUserRole){
            list.add(sysUR.getRolecode());
        }
        return list;
    }
    @ResponseBody
    @RequestMapping("/selectURByrole")
    public  List selectURByrole(String delrolecode){
        List<Sys_userrole> sysUserRole = sys_userroleService.selectByrolecode(delrolecode);
        List list=new ArrayList();
        for(Sys_userrole sysUR :sysUserRole){
            list.add(sysUR.getUserid());
        }
        System.out.println("获取当前权限所有的用户"+list);
        return list;
    }
    @ResponseBody
    @RequestMapping("/saveUR")
    public  int saveUR(String myUsercar,String userids){
        List<Sys_userrole> list = new ArrayList<Sys_userrole>();
        StringTokenizer st=new StringTokenizer(myUsercar,",");
        while(st.hasMoreTokens()){
            Sys_userrole userRole=new Sys_userrole();
            String funccode=st.nextToken();
            userRole.setRolecode(funccode);
            userRole.setUserid(userids);
            userRole.setCreateDate(new Date());
            list.add(userRole);
        }
        int deleteRc = sys_userroleService.deleteUserRoleByUserids(userids);
        int insertRc=sys_userroleService.insertList(list);
        return insertRc;
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
    /**
     * 一级
     *
     * @param id
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
        return tree;
    }

     @RequestMapping("/userList")
    @ResponseBody
    public ResultMsg userList(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "rows", required = false) Integer rows,
                                @RequestParam(value = "id", required = false) String id,
                                @RequestParam(value = "username", required = false) String username,
                                @RequestParam(value = "realname", required = false) String realname) {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;
        List<HashMap<String,Object>>  sysUsersList=userService.getSysUserPageList(intPage, intPageSize, id, username, realname);
         ResultMsg resultJson=new ResultMsg();
        resultJson.setRows(sysUsersList);
        resultJson.setData(sysUsersList);
        resultJson.setTotal(userService.getUserList(id, username, realname).size());
        return resultJson;
    }
    @RequestMapping("/roleList")
    @ResponseBody
    public ResultMsg roleList(@RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "rows", required = false) Integer rows,
                              @RequestParam(value = "id", required = false) String id,
                              @RequestParam(value = "name", required = false) String name) {
        //设置当前页
        int intPage=page==null||page<=0?1:page;
        //设置每页显示的数量
        int intPageSize=rows==null||rows<=0?10:rows;

        List<HashMap<String,Object>>  sysRolesList=sys_roleService.getSysRolePageList(intPage, intPageSize, name);
        ResultMsg resultJson=new ResultMsg();
        resultJson.setRows(sysRolesList);
        resultJson.setData(sysRolesList);
        resultJson.setTotal(sys_roleService.getSysRoleList(name).size());
        return resultJson;
    }

    @RequestMapping("/add")
    @ResponseBody
    public int roleList(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "realname", required = false) String realname,
                        @RequestParam(value = "oucode", required = false) String oucode,
                        @RequestParam(value = "email", required = false) String email,
                        @RequestParam(value = "mobile", required = false) String mobile,
                        @RequestParam(value = "creator", required = false) String creator,
                        @RequestParam(value = "state", required = false) String state,
                        @RequestParam(value = "createtime", required = false) String createtime) {
        HashMap hashMap = new HashMap();
        hashMap.put("username", username);
        hashMap.put("realname", realname);
        hashMap.put("oucode", oucode);
        hashMap.put("email", email);
        hashMap.put("state", state);
        hashMap.put("createtime", createtime);
        hashMap.put("mobile", mobile);
        hashMap.put("creator", creator);
        hashMap.put("pwd", md5("111111"));
        int insert=userService.insertRow(hashMap);
        return insert;
    }

    @RequestMapping("/addDict")
    @ResponseBody
    public int addDict(@RequestParam(value = "dictID", required = false) Integer dictID,
                       @RequestParam(value = "parentID", required = false) Integer parentID,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "value", required = false) String value,
                       @RequestParam(value = "code", required = false) String code,
                       @RequestParam(value = "IsDel", required = false) String IsDel){
        HashMap hashMap = new HashMap();
        hashMap.put("dictID", dictID);
        hashMap.put("parentID", parentID);
        hashMap.put("name", name);
        hashMap.put("value", value);
        hashMap.put("code", code);

        int insert=sysDictService.insertRow(hashMap);
        return insert;
    }

    @RequestMapping("/addRole")
    @ResponseBody
    public int addRole(@RequestParam(value = "rolecode", required = false) String rolecode,
                        @RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "creator", required = false) String creator,
                        @RequestParam(value = "createtime", required = false) String createtime,
                        @RequestParam(value = "state", required = false) String state) {
        HashMap hashMap = new HashMap();
        hashMap.put("rolecode", rolecode);
        hashMap.put("name", name);
        hashMap.put("creator", creator);
        hashMap.put("state", state);
        hashMap.put("createtime", createtime);

        int insert=sys_roleService.insertRow(hashMap);
        return insert;
    }

    @RequestMapping("/addCatalog")
     @ResponseBody
     public int addCatalog(@RequestParam(value = "funccode", required = false) String funccode,
                           @RequestParam(value = "parentcode", required = false) String parentcode,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "targeturl", required = false) String targeturl) {
        HashMap CataMap = new HashMap();
        CataMap.put("funccode", funccode);
        CataMap.put("parentcode", parentcode);
        CataMap.put("name", name);
        CataMap.put("targeturl", targeturl);

        int insert=sys_funcService.insertRow(CataMap);
        return insert;
    }
    @RequestMapping("/editDict")
    @ResponseBody
    public int editDict(@RequestParam(value = "dictID", required = false) Integer dictID,
                       @RequestParam(value = "parentID", required = false) Integer parentID,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "value", required = false) String value,
                       @RequestParam(value = "code", required = false) String code){
        HashMap hashMap = new HashMap();
        hashMap.put("dictID", dictID);
        hashMap.put("parentID", parentID);
        hashMap.put("name", name);
        hashMap.put("value", value);
        hashMap.put("code", code);

        int update=sysDictService.updateRow(hashMap);
        return update;
    }

    @RequestMapping("/editRole")
    @ResponseBody
    public int editRole(@RequestParam(value = "rolecode", required = false) String rolecode,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "creator", required = false) String creator,
                       @RequestParam(value = "state", required = false) String state,
                       @RequestParam(value = "createtime", required = false) String createtime) {
        HashMap mapping = new HashMap();
        mapping.put("rolecode", rolecode);
        mapping.put("name", name);
        mapping.put("creator", creator);
        mapping.put("state", state);
        mapping.put("createtime", createtime);

        int update = sys_roleService.updateRow(mapping);
        return update;
    }


    @RequestMapping("/edituser")
    @ResponseBody
    public int edituserList(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "realname", required = false) String realname,
                        @RequestParam(value = "oucode", required = false) String oucode,
                        @RequestParam(value = "mobile", required = false) String mobile,
                        @RequestParam(value = "creator", required = false) String creator,
                        @RequestParam(value = "email", required = false) String email,
                        @RequestParam(value = "state", required = false) String state,
                        @RequestParam(value = "createtime", required = false) String createtime) {
        HashMap map = new HashMap();
        map.put("username", username);
        map.put("realname", realname);
        map.put("oucode", oucode);
        map.put("email", email);
        map.put("state", state);
        map.put("createtime", createtime);
        map.put("mobile", mobile);
        map.put("creator", creator);
        int update=userService.updateRow(map);
        return update;
    }

    @RequestMapping("/editCatalog")
    @ResponseBody
    public int editCatalog(@RequestParam(value = "funccode", required = false) String funccode,
                           @RequestParam(value = "parentcode", required = false) String parentcode,
                           @RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "targeturl", required = false) String targeturl) {
        HashMap CataUpMap = new HashMap();
        CataUpMap.put("funccode", funccode);
        CataUpMap.put("parentcode", parentcode);
        CataUpMap.put("name", name);
        CataUpMap.put("targeturl", targeturl);
        int update=sys_funcService.updateRow(CataUpMap);
        return update;
    }

    @RequestMapping("/deluser")
    @ResponseBody
    public int deluser(@RequestParam(value = "username", required = false) String username) {
        int delte=userService.delRow(username);
        return delte;
    }

    @RequestMapping("/delrole")
    @ResponseBody
    public int delrole(@RequestParam(value = "rolecode", required = false) String rolecode) {
        int delte=sys_roleService.delete(rolecode);
        return delte;
    }
    @RequestMapping("/delCatalog")
    @ResponseBody
    public int delCatalog(@RequestParam(value = "funccode", required = false) String funccode) {
        int delte=sys_funcService.delRow(funccode);
        return delte;
    }
   /* @RequestMapping("/delDict")
    @ResponseBody
    public int delDict(@RequestParam(value = "delDictID", required = false) Integer delDictID) {
        int delte=sysDictService.deleteByPrimaryKey(delDictID);
        return delte;
    }*/

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

    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
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
    String attributes;
    String text;

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