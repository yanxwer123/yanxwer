package com.kld.app.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

/*
Created BY niyang
Created Date 2015/12/29
*/
public class SuperStringDocument extends PlainDocument {

    private int length;
    private String[] requirestr;

    public SuperStringDocument(Integer strlength){
        this.length=strlength;
    }

    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {
        // 若字符串为空，直接返回。
        if (str == null) {
            return;
        }
        int len = getLength();
        if(len>=length){return;}
        super.insertString(offs, str, a);// 把字符添加到文本框
    }
}
