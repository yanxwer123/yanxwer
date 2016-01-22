package com.kld.gsm.coord.domain;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/27 16:45
 * @Description: 角色权限定义表
 */
public class Roleinfor {
    private String rolename;//角色名称
    private String menuname;
    private String frmname;
    private String menuflag;
    private String frmflag;
    private String addflag;
    private String modifyflag;
    private String printflag;
    private String queryflag;
    private String deleteflag;
    private String checkflag;
    private String save1;
    private String save2;
    private String save3;
    private String save4;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getFrmname() {
        return frmname;
    }

    public void setFrmname(String frmname) {
        this.frmname = frmname;
    }

    public String getMenuflag() {
        return menuflag;
    }

    public void setMenuflag(String menuflag) {
        this.menuflag = menuflag;
    }

    public String getFrmflag() {
        return frmflag;
    }

    public void setFrmflag(String frmflag) {
        this.frmflag = frmflag;
    }

    public String getAddflag() {
        return addflag;
    }

    public void setAddflag(String addflag) {
        this.addflag = addflag;
    }

    public String getModifyflag() {
        return modifyflag;
    }

    public void setModifyflag(String modifyflag) {
        this.modifyflag = modifyflag;
    }

    public String getPrintflag() {
        return printflag;
    }

    public void setPrintflag(String printflag) {
        this.printflag = printflag;
    }

    public String getQueryflag() {
        return queryflag;
    }

    public void setQueryflag(String queryflag) {
        this.queryflag = queryflag;
    }

    public String getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }

    public String getCheckflag() {
        return checkflag;
    }

    public void setCheckflag(String checkflag) {
        this.checkflag = checkflag;
    }

    public String getSave1() {
        return save1;
    }

    public void setSave1(String save1) {
        this.save1 = save1;
    }

    public String getSave2() {
        return save2;
    }

    public void setSave2(String save2) {
        this.save2 = save2;
    }

    public String getSave3() {
        return save3;
    }

    public void setSave3(String save3) {
        this.save3 = save3;
    }

    public String getSave4() {
        return save4;
    }

    public void setSave4(String save4) {
        this.save4 = save4;
    }

    @Override
    public String toString() {
        return "Roleinfor{" +
                "rolename='" + rolename + '\'' +
                ", menuname='" + menuname + '\'' +
                ", frmname='" + frmname + '\'' +
                ", menuflag='" + menuflag + '\'' +
                ", frmflag='" + frmflag + '\'' +
                ", addflag='" + addflag + '\'' +
                ", modifyflag='" + modifyflag + '\'' +
                ", printflag='" + printflag + '\'' +
                ", queryflag='" + queryflag + '\'' +
                ", deleteflag='" + deleteflag + '\'' +
                ", checkflag='" + checkflag + '\'' +
                ", save1='" + save1 + '\'' +
                ", save2='" + save2 + '\'' +
                ", save3='" + save3 + '\'' +
                ", save4='" + save4 + '\'' +
                '}';
    }
}
