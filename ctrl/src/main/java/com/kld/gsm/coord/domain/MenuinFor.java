package com.kld.gsm.coord.domain;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/29 17:21
 * @Description:
 */
public class MenuinFor {

    private String Menuname; //菜单名称
    private String Appname;//
    private String Frmname;//窗体名称
    private String Menudesc;//中文名称
    private String Menulevel;//菜单级别
    private String Parentname;//上级菜单名称
    private String Parentdesc;//上级菜单中文名称
    private String Menucontent;//菜单功能说明

    public String getMenuname() {
        return Menuname;
    }

    public void setMenuname(String menuname) {
        Menuname = menuname;
    }

    public String getAppname() {
        return Appname;
    }

    public void setAppname(String appname) {
        Appname = appname;
    }

    public String getFrmname() {
        return Frmname;
    }

    public void setFrmname(String frmname) {
        Frmname = frmname;
    }

    public String getMenudesc() {
        return Menudesc;
    }

    public void setMenudesc(String menudesc) {
        Menudesc = menudesc;
    }

    public String getMenulevel() {
        return Menulevel;
    }

    public void setMenulevel(String menulevel) {
        Menulevel = menulevel;
    }

    public String getParentname() {
        return Parentname;
    }

    public void setParentname(String parentname) {
        Parentname = parentname;
    }

    public String getParentdesc() {
        return Parentdesc;
    }

    public void setParentdesc(String parentdesc) {
        Parentdesc = parentdesc;
    }

    public String getMenucontent() {
        return Menucontent;
    }

    public void setMenucontent(String menucontent) {
        Menucontent = menucontent;
    }

    @Override
    public String toString() {
        return "{" +
                "Menuname='" + Menuname + '\'' +
                ", Appname='" + Appname + '\'' +
                ", Frmname='" + Frmname + '\'' +
                ", Menudesc='" + Menudesc + '\'' +
                ", Menulevel='" + Menulevel + '\'' +
                ", Parentname='" + Parentname + '\'' +
                ", Parentdesc='" + Parentdesc + '\'' +
                ", Menucontent='" + Menucontent + '\'' +
                '}';
    }
}
