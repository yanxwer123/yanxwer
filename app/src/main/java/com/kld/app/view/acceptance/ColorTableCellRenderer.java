package com.kld.app.view.acceptance;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/*
Created BY niyang
Created Date 2015/12/4
*/
public class ColorTableCellRenderer  extends DefaultTableCellRenderer {
    private HashMap<String, Color> nameToColor;
    private int partmentColumn;

    /**
     * 构造函数传入的列序
     * @param partmentColumn
     */
    public ColorTableCellRenderer(int partmentColumn,HashMap<String,Color> nameToColors) {
        this.partmentColumn=partmentColumn;
        this.nameToColor=nameToColors;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Object partment=table.getValueAt(row, partmentColumn);
        Color color = nameToColor.get(partment);
        if(color!=null) {
            setBackground(color);
        }

        if (isSelected) {
            Color cc=new Color(185,207,229);
            super.setBackground(cc);
        }
        return this;
    }
}
