package com.kld.gsm.Socket.uitls;

import com.kld.gsm.ATG.domain.CurUser;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.util.JsonMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-11-13 15:12
 * @Description: 给主调度提供返回方法 成功与失败
 */
public class ResultUtils {
    private ResultUtils() {
    }

    private static ResultUtils instance = null;

    public static ResultUtils getInstance() {
        if (instance == null) {
            instance = new ResultUtils();
        }
        return instance;
    }

    /**
     *
     * @param id 客户端ID
     * @param list 返回客户端的消息
     * @param pid 业务代码
     * @return  GasMsg
     */
    public GasMsg sendSUCCESS(String id, List list, String pid) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setId(id);
        resultMsg.setResult("0");
        resultMsg.setTime(new Date().toLocaleString());
        resultMsg.setData(list);
        String subJson = new JsonMapper().toJson(resultMsg);

        GasMsg gasMsg = new GasMsg();
        gasMsg.setPid(pid);
        gasMsg.setMessage(subJson);
        return gasMsg;
    }
    /**
     *
     * @param id 客户端ID
     * @param list 返回客户端的消息
     * @param pid 业务代码
     * @return  GasMsg
     */
    public GasMsg sendFAIL(String id, List list, String pid) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setId(id);
        resultMsg.setResult("1");
        resultMsg.setTime(new Date().toLocaleString());
        resultMsg.setData(list);
        String subJson = new JsonMapper().toJson(resultMsg);

        GasMsg gasMsg = new GasMsg();
        gasMsg.setPid(pid);
        gasMsg.setMessage(subJson);
        return gasMsg;
    }


    /**
     *  返回成功
     * @param id  用户id
     * @param oprno  操作员号
     * @param oprname 操作员名
     * @param menu  操作菜单
     * @return  结果对象
     */
    public GasMsg sendLoginSUCCESS(String id,String oprno,String oprname,List menu) {
        CurUser curUser = new CurUser();
        curUser.setId(id);
        curUser.setResult("0");
        curUser.setTime(new Date().toLocaleString());
        curUser.setOprno(oprno);
        curUser.setOprname(oprname);
        curUser.setMenuList(menu);
        String subJson = new JsonMapper().toJson(curUser);
        GasMsg gasMsg = new GasMsg();
        gasMsg.setPid(Constants.PID_Code.A15_10001.toString());
        gasMsg.setMessage(subJson);
        return gasMsg;
    }


    /**
     *  返回成功
     * @param id  用户id

     * @return  结果对象
     */
    public GasMsg sendLoginFAIL(String id) {
        CurUser curUser = new CurUser();
        curUser.setId(id);
        curUser.setResult("0");
        curUser.setTime(new Date().toLocaleString());
        curUser.setOprno("");
        curUser.setOprname("");
        curUser.setMenuList(new ArrayList());
        String subJson = new JsonMapper().toJson(curUser);
        GasMsg gasMsg = new GasMsg();
        gasMsg.setPid(Constants.PID_Code.A15_10001.toString());
        gasMsg.setMessage(subJson);
        return gasMsg;
    }
}
