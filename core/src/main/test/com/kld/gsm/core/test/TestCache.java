package com.kld.gsm.core.test;

import com.kld.gsm.ATGDevice.atg_capacity_data_t;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.FileNotFoundException;


/**
 * Created by miaozy on 15/10/24.
 */
public class TestCache {
     public static void main(String  []args) throws FileNotFoundException {

         CacheManager cacheManager =  new CacheManager("core/src/main/resources/ehcache.xml");

         Cache  myCache = cacheManager.getCache("test");

         atg_capacity_data_t  t1 = new atg_capacity_data_t();
         t1.fLiter = 1.2;
         t1.uCapacitySize = 2;
         t1.uHigh = 4;
         t1.uOilCanNO = 5;

         Element element = new Element(1L, t1);

         myCache.put(element);

         Element aa = myCache.get(1L);
         atg_capacity_data_t tt = (atg_capacity_data_t)aa.getObjectValue();

         System.out.println(tt.fLiter);


     }
}
