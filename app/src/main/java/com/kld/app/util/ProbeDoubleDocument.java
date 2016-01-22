package com.kld.app.util;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/12/23 19:05
 * @Description:
 */
public class ProbeDoubleDocument  extends PlainDocument {

    private static final long serialVersionUID = 1L;
    private int _maxLength = 5;

    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {
        // 若字符串为空，直接返回。
        if (str == null) {
            return;
        }
        int len = getLength();
        if(len>8){return;}
        String s = getText(0, len);// 文本框已有的字符
        //-开头的情况
        if(len==0&&str.equals("-"))
        {
            super.insertString(offs,str,a);
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
            //JOptionPane.showMessageDialog(null,str,str,JOptionPane.DEFAULT_OPTION);
           /* if (i < 0 || i > 255) { // 限制范围
                throw new Exception();
            }*/
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();// 发出提示声音
            /*JOptionPane.showMessageDialog(,
                    "请输入0~255的正整数", null, JOptionPane.ERROR_MESSAGE);// 警告框*/
            return;
        }
        if(s.indexOf(".")!=-1){
            int len2 = s.indexOf(".");
            String bf = s.substring(0, len2);
            int bflen = bf.length();
            //小数点前保留两位
            if(bflen > 5){
                return;
            }
            int slen=s.length();
           int aftmp=slen-len2-1;
            //小数点后保留两位
            if(aftmp > 4){
                return;
            }
        }
        /*else {
            char[] charVal = str.toCharArray();
            String strOldValue = getText(0, getLength());
            byte[] tmp = strOldValue.getBytes();
            if (tmp.length + charVal.length > _maxLength) {
                return;
            }
        }*/
        super.insertString(offs, str, a);// 把字符添加到文本框
    }
}
