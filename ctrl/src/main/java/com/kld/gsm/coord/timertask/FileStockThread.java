package com.kld.gsm.coord.timertask;

import com.kld.gsm.ATG.dao.MonitorTimeInventoryDao;
import com.kld.gsm.ATG.dao.SysManageCanInfoDao;
import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATGDevice.ATGManager;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.ATGDevice.atg_timestock_data_in_t;
import com.kld.gsm.coord.Context;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Qcsea
 * @version 1.0
 * @CreationTime: 2015/12/25 10:42
 * @Description: 5秒文件库存
 */
public class FileStockThread  extends Thread {
    Logger logger = Logger.getLogger(FileStockThread.class);
    static List canList = new ArrayList();
    FileWriter fileWriter = null;
    String filepreStr="";

    @Autowired
    SysManageCanInfoDao sysManageCanInfodao;
    @Override
    public void run() {
        while (true) {
            try {
                int iSleep=TimeTaskPar.get("sdkclxsj");

                if(TimeTaskPar.get("sdkclxsj")<1)
                {
                    sleep(Integer.MAX_VALUE);
                }
                else {
                    sleep(iSleep * 1000);
                }

                Integer iRet=writeFileTime();
                if(iRet.equals(1))
                {
                    logger.error("FileStockThread Write File Timeout");
                }
            } catch (InterruptedException e) {
                logger.error("sleep:" + e);
                e.printStackTrace();
            }
        }
    }

    //region oldcode
    /*public Integer writeFileTime(){
        Integer iRet=new Integer("1");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<Integer> future =
                new FutureTask<Integer>(new Callable<Integer>() {//使用Callable接口作为构造参数
                    public Integer call() {
                        try
                        {
                            writeFile();
                        }
                        catch(Exception e) {
                        }
                        return 0;
                    }});
        executor.execute(future);
        //在这里可以做别的任何事情
        try {
            iRet= future.get(25000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5秒。同样可以用future.get()，不设置执行超时时间取得结果
        } catch (InterruptedException e) {
            future.cancel(true);
        } catch (ExecutionException e) {
            future.cancel(true);
        } catch (TimeoutException e) {
            future.cancel(true);
        } finally {
            executor.shutdown();
        }
        return 1;
    }*/
//endregion

    public Integer writeFileTime(){
        int bdStatus = 0;
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<Integer> call = new Callable<Integer>() {
            public Integer call() throws Exception {
              try {
                  logger.info("====================write file start:"+new Date().toString()+"=================");
                  writeFile();
                  return 0;
              }catch (Exception e){
                  return 1;
              }
            }
        };
        try {
            Future<Integer> future = exec.submit(call);
            // set  timeout to 10 seconds
            int iSleep=TimeTaskPar.get("sdkclxsj");
            if(TimeTaskPar.get("sdkclxsj")<1)
            {
                iSleep=Integer.MAX_VALUE;
            }

            Integer obj = future.get(1000 * (iSleep-1), TimeUnit.MILLISECONDS);
            bdStatus = obj;
            logger.info("writeFileTime value from call is :" + obj);
        } catch (TimeoutException ex) {
            logger.error("===============writeFileTime task time out===============");
            bdStatus = 1;
        } catch (Exception e) {
            System.out.println("failed to handle.");
            bdStatus = 1;
        }
        // close thread pool
        exec.shutdown();

        return bdStatus;
    }
    public void writeFile()
    {
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        if ("".equals(filepreStr)){
            filepreStr=sd.format(date);
        }
        if (filepreStr.equals(sd.format(date))){
            //当天
            try{
                if (fileWriter==null){
                     File file = new File(File.separator + "smc20" + File.separator+"gsm"+File.separator+"logs"+  File.separator + "gsmtimestock"+  File.separator  + filepreStr + ".log");
                     if(!file.getParentFile().exists()){
                         logger.info("如果日志路径不存在，则创建");
                         file.getParentFile().mkdirs();
                     }
                     file.setWritable(true, false);
                     fileWriter = new FileWriter(file, true);
                }
            }catch (Exception e){
                logger.error("当天文件库存异常:"+e.getMessage());
            }
        }else{
            //第二天
            filepreStr=sd.format(date);
            try {
                //转天关闭文件
                if (fileWriter != null) {
                    fileWriter.close();
                }
                //开启新的一天
                File file = new File(File.separator + "smc20" + File.separator+"gsm"+File.separator+"logs"+  File.separator + "gsmtimestock"+  File.separator  + filepreStr + ".log");
                if(!file.getParentFile().exists()){
                    logger.info("如果日志路径不存在，则创建");
                    file.getParentFile().mkdirs();
                }
                file.setWritable(true, false);
                fileWriter = new FileWriter(file, true);
            }catch (Exception e){
                logger.error("转天时文件库存异常:"+e.getMessage());
            }
        }

        try {
            logger.info("get in 文件库存");
            //初始化罐号
            if (canList.size() == 0) {
                logger.info("进入文件库存初始化罐号");
                initTimeStockParameters();
            }
             logger.info("writeFile start to use ATGManager.getStock...canList.size():"+canList.size());
            //获取罐存值
            Object obj = ATGManager.getStock(new ArrayList<Integer>());//取得返回值
            String error = ATGManager.checkReturnData(obj);//判断返回值类型
            if (!this.StringIsNull(error)) {//如果返回值类型不是空则打印错误消息
                logger.error(error);
            }
            List<atg_stock_data_out_t> stockList = (ArrayList) obj;//转为需要的格式
            //2、开始写文件
            logger.info("start to save file...");
            for (atg_stock_data_out_t stock : stockList) {//循环取得实时库存数据
                logger.info(stock.toString2());
                fileWriter.write(stock.toString2() + "\n");
                fileWriter.flush();
            }
            //logger.info("file close FileStockThead...");
            //fileWriter.close();
            logger.info("end FileStock...,after five seconds again");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件库存异常:"+e.getMessage());
        } finally {
            try {
                //关闭链接
                //this.closeURLConn(out, conn);
              /*  if (fileWriter != null) {
                    fileWriter.close();
                }*/
            } catch (Exception e1) {
                e1.printStackTrace();
                logger.error("文件库存异常:"+e1.getMessage());

            }
        }
    }

    public boolean StringIsNull(String s) {
        if (s != null && "".equals(s)) {
            return true;
        }
        return false;
    }
    /**
     * 初始化实时库存传入参数
     * @return
     */
    public List<atg_timestock_data_in_t> initTimeStockParameters() {
        //获取所有罐号
        sysManageCanInfodao = Context.getInstance().getBean(SysManageCanInfoDao.class);
        List<SysManageCanInfo> oilCanInforList = sysManageCanInfodao.selectAll();
        List<atg_timestock_data_in_t> timeStockList = new ArrayList<atg_timestock_data_in_t>();
        logger.info("oilCanInforList.size:" + oilCanInforList.size());
        for (SysManageCanInfo o : oilCanInforList) {
            logger.info("初始化文件库存传入参数o.getOilcan():" + o.getOilcan());
            canList.add(o.getOilcan());
        }
        return canList;
    }
}
