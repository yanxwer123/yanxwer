package com.kld.app.util;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Administrator on 2015/11/28.
 */
public class RjsyStore extends JTable {
    public RjsyStore(Object[][] data) {
        super(0, 10);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setEnabled(false);
        this.getTableHeader().setUI(new UI());
        this.getColumnModel().getColumn(0).setPreferredWidth(150);

        this.getColumnModel().getColumn(1).setPreferredWidth(180);
        DefaultTableModel model = (DefaultTableModel) this.getModel();

        Vector rowData = new Vector();
        for(int i=0;i<data.length;i++){
            Vector rowData1 = new Vector();
            rowData1.add(data[i][0]);
            rowData1.add(data[i][1]);
            rowData1.add(data[i][2]);
            rowData1.add(data[i][3]);
            rowData1.add(data[i][4]);
            rowData1.add(data[i][5]);
            rowData1.add(data[i][6]);
            rowData1.add(data[i][7]);
            rowData1.add(data[i][8]);
            rowData1.add(data[i][9]);
            rowData.add(rowData1);
        }

        model.setRowCount(rowData.size());//设置数据条数

       for(int i =0;i<rowData.size();i++){
            for(int j=0;j<model.getColumnCount();j++){
                model.setValueAt(((Vector)rowData.get(i)).get(j), i, j);
            }
        }
    }

    // 表头绘制器*********************************************************************
    private class UI extends BasicTableHeaderUI {
        private JTableHeader header;

        public void paint(Graphics g, JComponent c) {
            header = (JTableHeader) c;
            getTableHeader().setPreferredSize(
                    new Dimension(RjsyStore.this.getWidth()+120, 45));// 设置表头大小。横坐标必须足够大，
            // 否则会出现绘制不完全以及闪烁现象
            // 分类
            JLabel label = getLabel("日期");
            rendererPane.paintComponent(g, label, header, 0, 0, getWidth(0),
                    45, true);
            label=getLabel("品种");
            rendererPane.paintComponent(g, label, header, getX(1),0, getWidth(1),
                    45, true);
            label = getLabel("本日罐存（帐存）");
            rendererPane.paintComponent(g, label, header, getX(2), 0,
                    getWidth(2), 45, true);

            label=getLabel("本日进货");
            rendererPane.paintComponent(g, label, header, getX(3), 0,
                    getWidth(3)+getWidth(3), 45, true);
            label = getLabel("出库单号");
            rendererPane.paintComponent(g, label, header, getX(3), 30,
                    getWidth(3), 15, true);
            label = getLabel("进货数量（L）");
            rendererPane.paintComponent(g, label, header, getX(4), 30,
                    getWidth(4), 15, true);

            label=getLabel("本日付出");
            rendererPane.paintComponent(g, label, header, getX(5), 0,
                    getWidth(5), 45, true);
            label = getLabel("本日库存");
            rendererPane.paintComponent(g, label, header, getX(6), 0,
                    getWidth(6), 45, true);
            label = getLabel("实测库存");
            rendererPane.paintComponent(g, label, header, getX(7), 0,
                    getWidth(7), 45, true);
            label = getLabel("损耗量");
            rendererPane.paintComponent(g, label, header, getX(8), 0,
                    getWidth(8), 45, true);
            label = getLabel("损耗率");
            rendererPane.paintComponent(g, label, header, getX(9), 0,
                    getWidth(9), 45, true);
        }

        // 得到指定列的起始坐标
        private int getX(int column) {
            int x = 0;
            for (int i = 0; i < column; i++)
                x += header.getColumnModel().getColumn(i).getWidth();
            return x;
        }

        // 得到指定列的宽度
        private int getWidth(int column) {
            return header.getColumnModel().getColumn(column).getWidth();
        }

        // 得到具有指定文本的标签
        private JLabel getLabel(String text) {
            JLabel label = new JLabel(text, JLabel.CENTER);
            label.setFont(new Font("Dialog", Font.PLAIN, 12));
            label.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            return label;
        }
    }

//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.setSize(800, 300);
//		frame.setDefaultCloseOperation(3);
//		frame.getContentPane().add(new JScrollPane(new StoreTable()));
//		frame.setVisible(true);
//	}
}
