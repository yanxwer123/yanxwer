package com.kld.app.util;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * Created by luyan on 15/12/12.
 */
public class IntegerDocument extends PlainDocument {

        private static final long serialVersionUID = 1L;

        public IntegerDocument()
        {
            super();
        }

        int _min=Integer.MIN_VALUE,_max=Integer.MAX_VALUE;
        public IntegerDocument(int min,int max)
        {
            _min=min;
            _max=max;
        }

        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            // 若字符串为空，直接返回。
            if (str == null) {
                return;
            }
            int len = getLength();
            String s = getText(0, len);// 文本框已有的字符
            //-开头的情况
            if(len==0&&str.equals("-"))
            {
                if(_min>0){
                    return;
                }
                super.insertString(offs,str,a);
                return;
            }
                try {

                    s = s.substring(0, offs) + str + s.substring(offs, len);// 在已有的字符后添加字符
                    int i = Integer.parseInt(s); // 只能为正整数
               if (i < _min || i > _max) { // 限制范围
                    return;
                }
                } catch (Exception e) {
                    Toolkit.getDefaultToolkit().beep();// 发出提示声音
                /*JOptionPane.showMessageDialog(,
                        "请输入0~255的正整数", null, JOptionPane.ERROR_MESSAGE);// 警告框*/
                    return;
                }

            super.insertString(offs, str, a);// 把字符添加到文本框
        }
    }

