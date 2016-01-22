package com.kld.gsm.coord.timertask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/12/20 11:03
 * @Description: 计划任务时间
 */
public class TimeTaskPar {
    public static Map<String, Integer> map = new HashMap<String, Integer>();
    public static Integer get(String key){
        return map.get(key);
    }
    public static void put(String key,Integer val){
        map.put(key,val);
    }
}
