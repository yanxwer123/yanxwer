package com.kld.app.view.acceptance;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
  
public class CkdcxTableModelFrame {  
    public static void main(String[] args) {  
        JFrame frame=new JTableModelFrame();  
        BorderLayout borderLayout = (BorderLayout) frame.getContentPane().getLayout();
        frame.getContentPane().setEnabled(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setVisible(true);  
    }  
      
}  
class JTableModelFrame extends JFrame  
{  
    public JTableModelFrame()  
    {  
        this.setTitle("出库单查询");  
        this.setSize(400,300);  
        TableModel model=new MyTableModel(new String[]{"序号","出库单号","出库时间","发油油库","目的油站","油品","发货温度","原发数量（L）","原发数量（吨）","交运时间","车牌号码","出库铅封号"},
        		new Object[5][3]);  
        JTable table=new JTable(model);  
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  
        table.setRowSelectionAllowed(true);  
        table.setColumnSelectionAllowed(true);  
//        TableColumn column=table.getColumnModel().getColumn(0);  
//        JComboBox combo=new JComboBox();  
//        combo.addItem("1");  
//        combo.addItem("2");  
//        combo.addItem("3");  
//        column.setCellEditor(new DefaultCellEditor(combo));  
        this.add(new JScrollPane(table));  
    }  
}  
