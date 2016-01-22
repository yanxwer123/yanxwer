package com.kld.gsm.ATG.domain;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="yanxwer@163.com">yanxiaowei</a>
 * @version 1.0
 * @CreationTime: 2015/12/12 22:21
 * @Description:
 */
public class CurUser {
    private String id;//用户标识
    private String result;//返回结果
    private String time;//时间
    private String oprno;//操作员号
    private String oprname;//操作员名称
    private List  menuList;//菜单名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOprno() {
        return oprno;
    }

    public void setOprno(String oprno) {
        this.oprno = oprno;
    }

    public String getOprname() {
        return oprname;
    }

    public void setOprname(String oprname) {
        this.oprname = oprname;
    }

    public List getMenuList() {
        return menuList;
    }

    public void setMenuList(List menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "CurUser{" +
                "id='" + id + '\'' +
                ", result='" + result + '\'' +
                ", time='" + time + '\'' +
                ", oprno='" + oprno + '\'' +
                ", oprname='" + oprname + '\'' +
                ", menuList=" + menuList +
                '}';
    }
}
