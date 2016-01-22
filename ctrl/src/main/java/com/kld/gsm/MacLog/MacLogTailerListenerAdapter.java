package com.kld.gsm.MacLog;

import org.apache.commons.io.input.TailerListenerAdapter;

import java.util.concurrent.LinkedBlockingQueue;


/**
 * 文件新增内容处理
 * Created by miaozy on 15/10/25.
 */
public class MacLogTailerListenerAdapter extends TailerListenerAdapter {

    LinkedBlockingQueue<String> workItems;

    public MacLogTailerListenerAdapter(LinkedBlockingQueue<String> workItems) {
        this.workItems = workItems;
    }

    @Override
    public void handle(String line) { //增加的文件的内容
        //  //System.out.println("文件新增行数据:"+line);
        super.handle(line);
        try {
            ////System.out.println("maclog line data:"+line);
            workItems.put(line);
            // //System.out.println("read maclog "+workItems.take());
        } catch (InterruptedException e) {
            //System.out.println("fail to  read maclog line ");
            e.printStackTrace();
        }
    }
}
