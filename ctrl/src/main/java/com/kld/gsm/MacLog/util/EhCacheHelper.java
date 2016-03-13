package com.kld.gsm.MacLog.util;

import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.MacLog.GunStatusEnum;
import com.kld.gsm.MacLog.MacLogInfo;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 使用ehcache存储加油抢信息
 * Created by miaozy on 15/10/26.
 */
public class EhCacheHelper {
    static CacheManager cacheManager = null;
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(EhCacheHelper.class);
    static {
       URL url = new EhCacheHelper().getClass().getResource("../../../../../ehcache.xml");
        //    URL url = new EhCacheHelper().getClass().getResource("ehcache.xml");

        //System.out.println(url);
       /* File f =new File("ehcache.xml");
        //System.out.println( f.getAbsolutePath());*/
        cacheManager = new CacheManager(url);

    }


    public static void removeCache(byte GunNo) {
        Cache myCache = cacheManager.getCache("maclog");
        myCache.remove(GunNo);
    }

    public static void updteCache(MacLogInfo info) {

        Cache myCache = cacheManager.getCache("maclog");
        Element myE = myCache.get(info.GunNum);
        if (myE != null) {
            logger.info("element not null:"+info.GunNum+"info:"+info.toString()+"\n");
            MacLogInfo macLogInfo = (MacLogInfo) myE.getObjectValue();
            if(macLogInfo!=null) {
                logger.info("macLogInfo not null:" + macLogInfo.GunNum + "macLogInfo:" + macLogInfo.toString() + "\n");
            }
            if(macLogInfo!=null&&macLogInfo.PumpReadout!=null&&(info.PumpReadout==null||info.PumpReadout<=0.0001))
            {
                if (macLogInfo.PumpReadout>0) {
                    logger.info("update pumpreadout come in!\n");
                    info.PumpReadout = macLogInfo.PumpReadout;
                }
            }
            if (info.GunStatus == GunStatusEnum.卡插入 && macLogInfo.GunStatus == GunStatusEnum.提枪){
                return;
            }
            if (info.GunStatus == GunStatusEnum.卡插入 && macLogInfo.GunStatus == GunStatusEnum.挂枪){
                GunStatusEnum temp=info.getGunStatus();
                info=macLogInfo;
                info.setGunStatus(temp);
            }
            if (info.GunStatus == GunStatusEnum.卡插入 && macLogInfo.GunStatus == GunStatusEnum.卡插入){
                info=macLogInfo;
            }

        }
        Element element = new Element(info.GunNum, info);
        logger.info("add new cache item GunNum:"+info.GunNum+"info:"+info.toString()+"\n");
        myCache.put(element);
        info=null;
    }
    //仅更新泵码数
    public static void updteCache(byte GunNum,Double PumpReadout) {

        Cache myCache = cacheManager.getCache("maclog");
        Element myE = myCache.get(GunNum);
        if (myE != null) {
            MacLogInfo macLogInfo = (MacLogInfo) myE.getObjectValue();
            macLogInfo.PumpReadout=PumpReadout;
            Element element = new Element(GunNum, macLogInfo);
            logger.info("update PumpReadout:"+macLogInfo.GunNum+"info:"+macLogInfo.toString()+"\n");
            myCache.put(element);
        }
    }
    /*public static void updteCache(MacLogInfo info) {
        Element element = new Element(info.GunNum, info);
        Cache myCache = cacheManager.getCache("maclog");
        Element myE = myCache.get(info.GunNum);
        if (myE != null) {
            MacLogInfo macLogInfo = (MacLogInfo) myE.getObjectValue();
            if (info.GunStatus == GunStatusEnum.卡插入 && macLogInfo.GunStatus == GunStatusEnum.提枪)
                return;
        }
        // logger.info("add new cache item GunNum:"+info.GunNum+"info:"+info.toString()+"\n");
        myCache.put(element);
    }*/
    /**
     * 获取所有油机日志状态.
     *
     * @return
     */
    public static Element getOilGunStatus(byte oilgun) {
        Cache myCache = cacheManager.getCache("maclog");
        return myCache.get(oilgun);
    }
    /**
     * 获取所有油机日志状态.
     *
     * @return
     */
    public static Map getAllMacLog() {
        Cache myCache = cacheManager.getCache("maclog");
        List<Byte> keys = myCache.getKeys();
        ////System.out.println("cache keys count"+keys.size());
        return myCache.getAll(keys);
    }


    /**
     * 更新所有油管缓存
     *allstok为键值
     * @param stocks
     */
    public static void updateCanStocks(List<atg_stock_data_out_t> stocks) {
        logger.info("update stocks:"+stocks.toString());
        Cache myCache = cacheManager.getCache("ATGCache");
        Element el = new Element("allstock", stocks);
        myCache.put(el);
    }

    /**
     * 获取某个油罐数据
     *
     * @param canno
     * @return
     */
    public static atg_stock_data_out_t getCanStock(int canno) {
        Cache myCache = cacheManager.getCache("ATGCache");
        return (atg_stock_data_out_t) myCache.get(canno).getObjectValue();
    }

    public static List<atg_stock_data_out_t> getCanStocks() {
        return null;
    }

    /**
     * 更新某个油罐库存
     *
     * @param stock
     */
    public static void updateCanStock(atg_stock_data_out_t stock) {
        Cache myCache = cacheManager.getCache("ATGCache");
        Element el = new Element(stock.uOilCanNo, stock);
        myCache.put(el);
    }

    /**
     * 获取所有油罐库存
     * 键值为 allstock 未找到返回null
     * @return
     */
    public static Element getAllCanStock() {
        Cache myCache = cacheManager.getCache("ATGCache");
        return myCache.get("allstock");
    }

    /**
     * 更新容积表信息
     * @param sysManageCubageInfoList
     */
    public static void updateCubageInfo(List<SysManageCubageInfo> sysManageCubageInfoList){
        Cache myCache = cacheManager.getCache("CubageInfo");
        Element element = new Element(sysManageCubageInfoList.get(0).getOilcan(), sysManageCubageInfoList);
        myCache.put(element);
    }

    /**
     * 取得容积表信息
     * @param oilcan
     * @return
     */
    public static List<SysManageCubageInfo> getCubageInfo(int oilcan){
        Cache myCache;
        try{
            myCache = cacheManager.getCache("CubageInfo");
        }catch (Exception e){
            logger.error("cacheManager.getCache('CubageInfo') is null" + e);
            e.printStackTrace();
            return null;
        }
        Element element = myCache.get(oilcan);
        if(element==null){
            logger.warn("getCubageInfo myCache.get(" + oilcan + ") is null!");
            return null;
        }
        return (List<SysManageCubageInfo>) element.getObjectValue();
    }

    /**
     * 获取所有罐号
     * 键值为 allcannos 未找到返回null
     * @return
     */
    public static Element getAllCanNos() {
        Cache myCache = cacheManager.getCache("ATGCache");
        return myCache.get("allcannos");
    }

    /**
     * 更新罐号
     * @param cannos
     */
    public static void updateCanNos(List<Integer> cannos){
        Cache myCache = cacheManager.getCache("ATGCache");
        Element element = new Element("allcannos", cannos);
        myCache.put(element);
    }
}
