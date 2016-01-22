package com.kld.gsm.util;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-23 18:00
 * @Description:
 */
public class SearchStringUtils {
    /**
     *
     * @param str 搜索标识
     * @param list 集合
     * @return 0存在 1不存在
     */
    public static int serach(String str,List<String> list){
        for(String unknown :list){
            if(unknown.equals(str)){
                return  0;
            }
        }
        return  1;
    }
}
