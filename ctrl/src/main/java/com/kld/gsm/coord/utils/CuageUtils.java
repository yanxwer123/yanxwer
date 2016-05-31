package com.kld.gsm.coord.utils;

import com.kld.gsm.ATGDevice.atg_capacity_data_in_t;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class CuageUtils {
    /**
     * 获取指定液位高度的液位容积
     * @param height double
     * @return double
     * */
    double get_volume(atg_capacity_data_in_t  pInputData, double height) {
        int i = 0;
        double volume, minV, maxV;
        volume = 0;
        for (i = 0; i < pInputData.getpCapacityTableData().size(); i++) {
            if (height == pInputData.getpCapacityTableData().get(i).uHigh) {
                volume = pInputData.getpCapacityTableData().get(i).fLiter;
                break;
            } else if (i + 1 < pInputData.uCapacitySize
                    && height >pInputData.getpCapacityTableData().get(i).uHigh
                    && height < pInputData.getpCapacityTableData().get(i+1).uHigh) {
                minV = pInputData.getpCapacityTableData().get(i).fLiter;
                maxV = pInputData.getpCapacityTableData().get(i+1).fLiter;

                if (pInputData.getpCapacityTableData().get(i+1).uHigh
                        -pInputData.getpCapacityTableData().get(i).uHigh == 0) {
                    return -1;
                }
                volume = minV
                        + (maxV - minV)
                        * (height -pInputData.getpCapacityTableData().get(i).uHigh)
                        / (pInputData.getpCapacityTableData().get(i+1).uHigh
                        - pInputData.getpCapacityTableData().get(i).uHigh);
                break;
            }
        }
        return volume;

    }
}
