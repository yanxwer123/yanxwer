package com.kld.gsm.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015/10/16 11:33
 * @Description:
 */
public class NumberUtils {
    /**
     * 数字转成4位字符串  高位不足补0
     * 不能format 高于4位的数字
     *
     * @param i
     * @return
     */
    public static String numberTo4Leng(int i) {
        if (i > 9999) {
            throw new RuntimeException("format error " + i + "");
        }
        return String.format("%04d", i);
    }

    public static BigDecimal numberTo2(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return bigDecimal;
        } else {
            DecimalFormat df = new DecimalFormat("#.00");
            String bigDecimalStr = df.format(bigDecimal);
            BigDecimal bigDecimal1 = BigDecimal.valueOf(Double.valueOf(bigDecimalStr));
            return bigDecimal1;
        }
    }
}
