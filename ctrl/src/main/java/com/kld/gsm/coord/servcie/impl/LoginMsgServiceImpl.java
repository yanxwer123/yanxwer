package com.kld.gsm.coord.servcie.impl;

import com.kld.gsm.ATG.domain.CurUser;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.coord.Context;
import com.kld.gsm.coord.dao.MenuinForDao;
import com.kld.gsm.coord.dao.OprAuthorityDao;
import com.kld.gsm.coord.dao.RoleinforDao;
import com.kld.gsm.coord.dao.SysinforDao;
import com.kld.gsm.coord.domain.Sysinfor;
import com.kld.gsm.coord.servcie.LoginMsgService;
import com.kld.gsm.util.JsonMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2015/11/10 15:58
 * @Description:操作员登录权限及权限信息
 */

@Service
public class LoginMsgServiceImpl implements LoginMsgService {
    @Autowired
    private SysinforDao sysinforDao;
    @Autowired
    private RoleinforDao roleinforDao;
    @Autowired
    private OprAuthorityDao oprAuthorityDao;
    @Autowired
    private MenuinForDao menuinForDao;
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(LoginMsgServiceImpl.class);

    @Override
    public GasMsg setMsg(String id) {
        SysinforDao sysinforDao = Context.getInstance().getBean(SysinforDao.class);
        RoleinforDao roleinforDao = Context.getInstance().getBean(RoleinforDao.class);
        GasMsg apploginMessage;
        //System.out.println("begin select -------------------");
        try {
            String sql = "select * from sysinfor ";
            Sysinfor sysinfor = sysinforDao.getAll1(sql);
            //System.out.println("sysinfor>>>>" + sysinfor.toString());
            if (sysinfor != null) {//登陆
                try {
                    sql = "select menuname from roleinfor ,oprauthority " +
                            " where roleinfor.rolename=oprauthority.rolename AND oprauthority.oprno='"+sysinfor.getOprno()+"'" ;
                    List<String> roleinfors = roleinforDao.getAll1(sql);
                    List menuList = new ArrayList();
                    for (String roleinfor : roleinfors) {
                        if (!menuList.contains(roleinfor.trim())) {
                            menuList.add(roleinfor.trim());//权限菜单
                        }
                    }
                    //System.out.println("getmenuList> :\n" + menuList);
                    apploginMessage = ResultUtils.getInstance().sendLoginSUCCESS(id, String.valueOf(sysinfor.getOprno()), sysinfor.getOprname(), menuList);
                    //System.out.println("message:" + apploginMessage);

                } catch (Exception e) {
                    apploginMessage = ResultUtils.getInstance().sendLoginFAIL(id);
                    //System.out.println("获取菜单异常" + e.getMessage());
                    logger.info("获取菜单异常" + e.getMessage());

                }
            } else {//未登录
                apploginMessage = ResultUtils.getInstance().sendLoginFAIL(id);
                logger.info("没有角色");
                //System.out.println("没有角色");

            }
        } catch (Exception e) {
            apploginMessage = ResultUtils.getInstance().sendLoginFAIL(id);
            //System.out.println("获取角色异常" + e.getMessage());

            logger.info("获取角色异常" + e.getMessage());
        }
        return apploginMessage;
    }

    public static void main(String[] args) throws InterruptedException {
        LoginMsgServiceImpl loginMsgService = Context.getInstance().getBean(LoginMsgServiceImpl.class);
        GasMsg gasMsg = loginMsgService.setMsg("aaa");

        CurUser curUser = new JsonMapper().fromJson(gasMsg.getMessage(), CurUser.class);
        //System.out.println("curUser：" + curUser);

        for (int i = 0; i < curUser.getMenuList().size(); i++) {
            //System.out.println(curUser.getMenuList().get(i));
        }
    }

}