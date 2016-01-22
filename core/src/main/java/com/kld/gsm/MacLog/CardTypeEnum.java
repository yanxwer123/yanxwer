package com.kld.gsm.MacLog;

/**
 * Created by miaozy on 15/10/25.
 */
public enum CardTypeEnum {
    /// <summary> 员工卡 </summary>
    员工卡(1),
    /// <summary> 加油卡 </summary>
    IC卡(2),
    /// <summary> 验泵卡 </summary>
    验泵卡(3);


    private int _value;

    private CardTypeEnum(int value)
    {
        _value = value;
    }

    public int value()
    {
        return _value;
    }
}
