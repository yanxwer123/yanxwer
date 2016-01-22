package com.kld.gsm.coord.domain;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/27 16:54
 * @Description: 操作员权限分配
 */
public class OprAuthority  implements Serializable {
    private String Rolename;//角色名称
    private int Oprno;//操作员号
    private String Flag;//标志
    private String authority;//说明

    public String getRolename() {
        return Rolename;
    }

    public void setRolename(String rolename) {
        Rolename = rolename;
    }

    public int getOprno() {
        return Oprno;
    }

    public void setOprno(int oprno) {
        Oprno = oprno;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "OprAuthority{" +
                "Rolename='" + Rolename + '\'' +
                ", Oprno=" + Oprno +
                ", Flag='" + Flag + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
