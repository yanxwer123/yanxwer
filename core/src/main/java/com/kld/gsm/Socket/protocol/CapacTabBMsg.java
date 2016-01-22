package com.kld.gsm.Socket.protocol;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/11/7 14:12
 * @Description: 容积表 -高 -升
 */
public class CapacTabBMsg {
   private double Height;
   private double Liter;

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public double getLiter() {
        return Liter;
    }

    public void setLiter(double liter) {
        Liter = liter;
    }

    @Override
    public String toString() {
        return "CapacityTableBodyMessage{" +
                "Height=" + Height +
                ", Liter=" + Liter +
                '}';
    }
}
