package com.kld.gsm.syntocenter.util;


import com.kld.gsm.util.JsonMapper;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


/*
Created BY niyang
Created Date 2015/11/18
*/
public class httpClient {
    private String result;

    public String GetResult(){return result;}

    public String request(String path,Object json,Map<String,String> maps) throws  Exception {
        path=path+"?";
        for (Map.Entry<String,String> item:maps.entrySet()){
            path +=(item.getKey()+"="+item.getValue()+"&");
        }
        String encoding="UTF-8";
        String params =new JsonMapper().toJson(json);
        //System.out.println(path);
        //System.out.println(params);
        byte[] data = params.getBytes(encoding);
        URL url =new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset="+ encoding);
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        conn.setConnectTimeout(30*1000);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        //System.out.println(conn.getResponseCode()); //响应代码 200表示成功
        if(conn.getResponseCode()==200){
            InputStream inStream = conn.getInputStream();
            result=new String(readStream(inStream));
        }
       return result;
    }
    /**
     * 读取流
     *
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    public byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
}
