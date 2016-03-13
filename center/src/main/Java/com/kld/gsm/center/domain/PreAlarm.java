package com.kld.gsm.center.domain;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2016/1/15 15:41
 * @Description:
 */
public class PreAlarm extends oss_sysmanage_AlarmParameter {
    String oiltype;

    public String getOiltype() {
        return oiltype;
    }

    public void setOiltype(String oiltype) {
        this.oiltype = oiltype;
    }

    @Override
    public String toString() {
        return "PreAlarm{" +
                "oiltype='" + oiltype + '\'' +
                '}';
    }
}
