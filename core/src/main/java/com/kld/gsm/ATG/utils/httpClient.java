package com.kld.gsm.ATG.utils;


import com.kld.gsm.util.JsonMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
      /*  ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String params=mapper.writeValueAsString(json);*/

        String params = new JsonMapper().toJson(json);
        //System.out.println(path);
        byte[] data = params.getBytes(encoding);
        URL url =new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset="+ encoding);
        /*conn.setRequestProperty("Content-Type", "application/json; charset=GBK");*/
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        conn.setConnectTimeout(5*1000);
        conn.setReadTimeout(5*1000);
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        //System.out.println(conn.getResponseCode()); //响应代码 200表示成功
        if(conn.getResponseCode()==200){
            InputStream inStream = conn.getInputStream();
            /*StringBuilder sb=getResponseTextV2(inStream);
            result=sb.toString();*/
            result=new String(readStream(inStream));
            /*result=new String(readStream(inStream));*/
           /* BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "gbk"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            result = buffer.toString();*/
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

    private  StringBuilder getResponseTextV2(InputStream netIps) throws Exception {
        StringBuilder builder=new StringBuilder();
        byte[] buffer = new byte[1024];
        int len;
        while((len = netIps.read(buffer)) != -1) {
            builder.append(new String(buffer, 0, len, "utf-8"));
        }
        return builder;
    }
}
