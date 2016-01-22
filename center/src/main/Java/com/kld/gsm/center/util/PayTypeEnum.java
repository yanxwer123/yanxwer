package com.kld.gsm.center.util;

/**
 * Created by xhz on 2015/12/4.
 */
public enum  PayTypeEnum {
    现金 ("00"),
    邮票 ("01"),
    记账("02"),
    银行卡("03"),
    其他1("04"),
    其他2("05"),
    IC卡("06"),
    IC卡积分("16"),
    已售未提("22"),
    已提未售("32"),
    代存代付("42"),
    邮票代管("52"),
    自用油("62"),
    非机走("07");


    private String _value;

    private PayTypeEnum(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
