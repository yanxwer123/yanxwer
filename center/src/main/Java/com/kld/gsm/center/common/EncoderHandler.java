package com.kld.gsm.center.common;
import java.security.MessageDigest;

/**
 *
 */
public class EncoderHandler {

    private static final String ALGORITHM = "MD5";

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * encode string
     *
     * @param algorithm
     * @param str
     * @return String
     */
    public static String encode(String algorithm, String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * encode By MD5
     *
     * @param str
     * @return String
     */
    public static String encodeByMD5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes
     *            the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 转化字符串为十六进制编码
     * @param inputString
     * @return
     */
    public static String toHexString(String inputString) {
        String s = "00000000",r="";
        if (inputString != "") {
            s = inputString;
        }
        for (int i = 0; i < s.length(); i++) {
            byte[] ba = s.substring(i, i + 1).getBytes();
            String tmpHex = Integer.toHexString(ba[0] & 0xFF);
            r+=tmpHex.toUpperCase();
            if (ba.length == 2) {
                tmpHex = Integer.toHexString(ba[1] & 0xff);
                r+=tmpHex.toUpperCase();
            }
        }
        //System.out.println("=="+inputString);
        //System.out.println("==="+r);
        return r;
    }

    //把a转成指定进制
    public static String tenToAny(int a,int n){
        String str = "";
        //1:用a去除以n，得到商和余数
        int sun = a/n;
        int yuShu = a%n;
        str = ""+numToChat(yuShu);
        while(sun > 0 ){
            //2：继续用商去除以n，得到商和余数
            yuShu = sun % n;
            sun = sun / n;
            //3：如果商为0，那么就终止
            //4：把所有的余数倒序排列
            str = numToChat(yuShu) + str;
        }
//        //System.out.println(n+"进制==="+str);
        return str;
    }
    //写一个方法实现：把一个十进制的数转换成为16进制的数
    public static String tenToSixteen(int a){
        String str = "";
        //1:用a去除以16，得到商和余数
        int sun = a/16;
        int yuShu = a%16;
        str = ""+numToChat(yuShu);
        while(sun > 0 ){
            //2：继续用商去除以16，得到商和余数
            yuShu = sun % 16;
            sun = sun / 16;
            //3：如果商为0，那么就终止
            //4：把所有的余数倒序排列
            str = numToChat(yuShu) + str;
        }
//        //System.out.println("16进制==="+str);
        return str;
    }

    public static String numToChat(int a){
        switch(a){
            case 10 :
                return "A";
            case 11 :
                return "B";
            case 12 :
                return "C";
            case 13 :
                return "D";
            case 14 :
                return "E";
            case 15 :
                return "F";
        }
        return ""+a;
    }

    public static void main(String[] args) {
        //System.out.println(toHexString("BJ0A1"));
    }
}