package com.kld.app.view.acceptance;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * Created by IntelliJ IDEA.
 *
 * @author mjxu
 * @version 1.0
 * @CreationTime: 2016/6/24 16:20
 * @Description:
 */
public class NumberLenghtLimitedDmt extends PlainDocument {

    private int limit;
    public NumberLenghtLimitedDmt(int  limit) {
        super();
        this.limit = limit;
    }
    public void insertString
            (int offset, String  str, AttributeSet attr)
            throws BadLocationException {
        if (str == null){
            return;
        }
        if ((getLength() + str.length()) <= limit) {

            char[] upper = str.toCharArray();
            int length=0;
            for (int i = 0; i < upper.length; i++) {
                if (upper[i]>='0'&&upper[i]<='9' || upper[i]=='.'){
                    upper[length++] = upper[i];
                }
            }
            super.insertString(offset, new String(upper,0,length), attr);
        }
    }
}