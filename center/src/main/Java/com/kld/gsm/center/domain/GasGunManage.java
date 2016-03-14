package com.kld.gsm.center.domain;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/1/14 18:21
 * @Description: 油枪管理
 */
public class GasGunManage {
    int oilcan;
    String oiltype;
    int oilgunno;

    public int getOilcan() {
        return oilcan;
    }

    public void setOilcan(int oilcan) {
        this.oilcan = oilcan;
    }

    public String getOiltype() {
        return oiltype;
    }

    public void setOiltype(String oiltype) {
        this.oiltype = oiltype;
    }

    public int getOilgunno() {
        return oilgunno;
    }

    public void setOilgunno(int oilgunno) {
        this.oilgunno = oilgunno;
    }

    @Override
    public String toString() {
        return "GasGunManage{" +
                "oilcan='" + oilcan + '\'' +
                ", oiltype='" + oiltype + '\'' +
                ", oilgunno='" + oilgunno + '\'' +
                '}';
    }
}
