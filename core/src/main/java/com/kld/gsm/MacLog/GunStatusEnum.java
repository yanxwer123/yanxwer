package com.kld.gsm.MacLog;

/**
 * Created by miaozy on 15/10/25.
 */
public enum GunStatusEnum {
    空闲(10),
    卡插入(0),
    挂枪(1),
    提枪(2),
    下班(3),
    脱机(4);

    private int _value;

    private GunStatusEnum(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }
}
