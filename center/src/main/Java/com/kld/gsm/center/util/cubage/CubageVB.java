package com.kld.gsm.center.util.cubage;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2016/1/19 18:55
 * @Description:
 */
public class CubageVB {
    private int tankNo;  //罐号
    private int oilHeight; //高度
    private double oilVolume; //体积

    public int getTankNo() {
        return tankNo;
    }

    public void setTankNo(int tankNo) {
        this.tankNo = tankNo;
    }

    public int getOilHeight() {
        return oilHeight;
    }

    public void setOilHeight(int oilHeight) {
        this.oilHeight = oilHeight;
    }

    public double getOilVolume() {
        return oilVolume;
    }

    public void setOilVolume(double oilVolume) {
        this.oilVolume = oilVolume;
    }

    @Override
    public String toString() {
        return "Cubage{" +
                "tankNo=" + tankNo +
                ", oilHeight=" + oilHeight +
                ", oilVolume=" + oilVolume +
                '}';
    }
}
