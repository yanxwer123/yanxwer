package com.kld.gsm.center.web.webcontroller;

import com.kld.gsm.center.domain.Sys_func;
import com.kld.gsm.center.domain.Sys_rolefuncrel;
import com.kld.gsm.center.domain.Sys_userrole;
import com.kld.gsm.center.service.Sys_funcService;
import com.kld.gsm.center.service.Sys_rolefuncrelService;
import com.kld.gsm.center.service.Sys_userroleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/2/26 10:22
 * @Description:
 */
@Controller
@RequestMapping("/web/common")
public class WebCommonController extends WebBaseController {

    @Resource
    private Sys_rolefuncrelService sys_rolefuncrelservice;
    @Resource
    private Sys_userroleService sys_userroleService;
    @Resource
    private Sys_funcService sys_funcService;

    @ResponseBody
    @RequestMapping("/selectAcessControl")
    public List selectAcessControl(){
        HttpSession session = request.getSession();
        String userid=(String)session.getAttribute("userID");
        System.out.println("打印session中的用户id"+userid);
        //通过userid查询出用户的角色，一个用户可以拥有多个角色
        List<Sys_userrole> sysUserRole = sys_userroleService.selectByUserids(userid);
        System.out.println("此处打印出权限为" + sysUserRole.size());
        List list1 = new ArrayList();
        //查询出所有的权限
        List<Sys_func> sysfuncrelAll =sys_funcService.selectAll();
        for(Sys_func func:sysfuncrelAll){
            list1.add(func.getFunccode());
        }
        if(sysUserRole.size()!=0){
            //根据角色查出所拥有的权限
            List<Sys_rolefuncrel> sysRolefuncrel = sys_rolefuncrelservice.selectRCBylist(sysUserRole);
            List list = new ArrayList();
            for(Sys_rolefuncrel funce :sysRolefuncrel){
                list.add(funce.getFunccode());
            }
            list1.removeAll(list);
            return list1;
        }

        return list1;
    }


}
