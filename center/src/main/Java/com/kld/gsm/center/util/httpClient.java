package com.kld.gsm.center.util;

import com.kld.gsm.center.domain.Result;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/*import com.kld.gsm.util.JsonMapper;*/


/*
Created BY niyang
Created Date 2015/11/18
*/
public class httpClient {
    private String result;

    public String GetResult(){return result;}

    public String request(String path,Object json,Map<String,String> maps) throws  Exception {

        action ac=new action();
        /*转发开关关闭，不进行转发*/
        if (ac.getOpenSet()=="0"){
            Result result=new Result();
            result.setResult(false);
            return  new JsonMapper().toJson(result);
        }

        path=path+"?";

        for (Map.Entry<String,String> item:maps.entrySet()){
            path +=(item.getKey()+"="+item.getValue()+"&");
        }
        String encoding="UTF-8";
        //String encoding="GBK";
        String params =new JsonMapper().toJson(json);
        params= URLEncoder.encode(params,"UTF-8");
        params="params="+params;
        //System.out.println(path);
        byte[] data = params.getBytes(encoding);
        URL url =new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
       /* conn.setRequestProperty("Content-Type", "application/json; charset="+ encoding);
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));*/
        conn.setConnectTimeout(5*1000);
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
