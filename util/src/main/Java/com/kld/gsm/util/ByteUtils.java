package com.kld.gsm.util;


import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
/**
 * Created by luyan on 15/10/31.
 */
public class ByteUtils {
    private static org.apache.log4j.Logger logger=  org.apache.log4j.Logger.getLogger(ByteUtils.class);
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset + 1] & 0xFF) << 8)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }

    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    public static String getBytesString(byte[] msg, int offset,int length) {
        try {
            String strmsg="";
            if(msg.length>offset+length){
                byte[] date=new byte[length];

                for (int i = offset,j=0; j < length; j++,i++) {
                    date[j]=msg[i];

                }
                strmsg +=   new String(date, "ASCII");
            }
            return strmsg;
        }  catch (UnsupportedEncodingException e) {
            String exmsg = ExceptionUtils.getMessage(e);
            logger.error("----Vouch!failed can't change byte[] to str.-----" + exmsg);
        }
        return "";
    }
}
