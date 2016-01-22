package com.kld.gsm.coord.domain;

/**
 * Created by yxw on 2015/11/4.
 */
public class RoleinforBody extends Roleinfor {
    private int  oprno;
    private String  oprname;
    private MenuinFor menuinfor;

    public int getOprno() {
        return oprno;
    }

    public void setOprno(int oprno) {
        this.oprno = oprno;
    }

    public String getOprname() {
        return oprname;
    }

    public void setOprname(String oprname) {
        this.oprname = oprname;
    }

    public MenuinFor getMenuinfor() {
        return menuinfor;
    }

    public void setMenuinfor(MenuinFor menuinfor) {
        this.menuinfor = menuinfor;
    }

    @Override
    public String toString() {
        return "RoleinforBody{" +
                "oprno='" + oprno + '\'' +
                ", oprname='" + oprname + '\'' +
                ", menuinfor=" + menuinfor +
                '}';
    }
}
