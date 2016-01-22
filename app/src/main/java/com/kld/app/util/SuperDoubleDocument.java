package com.kld.app.util;


import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

/*
Created BY niyang
Created Date 2015/12/29
*/
public class SuperDoubleDocument extends PlainDocument {

    private static final long serialVersionUID = 1L;
    private int intlength = 5;
    private int pointlength=2;
    public boolean isfs=false;
    private Double _min=Double.MIN_VALUE;
    private Double _max=Double.MAX_VALUE;

    public Double get_min() {
        return _min;
    }

    public void set_min(Double _min) {
        this._min = _min;
    }

    public Double get_max() {
        return _max;
    }

    public void set_max(Double _max) {
        this._max = _max;
    }

    public SuperDoubleDocument(Integer intlength,Integer pointlength){
        this.intlength=intlength;
        this.pointlength=pointlength;
    }

    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {
        // 若字符串为空，直接返回。
        if (str == null) {
            return;
        }
        int len = getLength();
        String s = getText(0, len);// 文本框已有的字符


       /* if(len>(intlength+pointlength+1)){return;}
        */
        //-开头的情况
        if(len==0&&str.equals("-"))
        {
            if (_min>0){
                return;
            }
            if (isfs) {
                super.insertString(offs, str, a);
            }
            return;
        }
        if (str.equals("d")||str.equals("f")){
            return;
        }
        if(len==0&&str.equals("."))
        {
            super.insertString(offs,"0"+str,a);
            return;
        }
        try {
            s = s.substring(0, offs) + str + s.substring(offs, len);// 在已有的字符后添加字符
            Double i = Double.parseDouble(s); // 只能为正整数
            if (i < _min || i > _max) { // 限制范围
                return;
            }

        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();// 发出提示声音
            return;
        }
        if(s.indexOf(".")!=-1){
            int len2 = s.indexOf(".");
            String bf = s.substring(0, len2);
            int bflen = bf.length();
            if (s.substring(0,1).equals("-")){
                if (bflen > intlength+1) {
                    return;
                }
            }else {
                //小数点前保留两位
                if (bflen > intlength) {
                    return;
                }
            }
            int slen=s.length();
            int aftmp=slen-len2-1;
            //小数点后保留两位
            if(aftmp > pointlength){
                return;
            }
        }
        if (s.indexOf(".")==-1){
            if (s.substring(0,1).equals("-")){
                if (s.length() > intlength+1) {
                    return;
                }
            }else {
                if (s.length() > intlength) {
                    return;
                }
            }
        }
        super.insertString(offs, str, a);// 把字符添加到文本框
    }


}
