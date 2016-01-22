package com.kld.app.view.sysmanage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.kld.app.userctrl.btnCtrl;
import com.kld.app.util.DateChooser;

/**
 * Created by 1 on 2015/10/20.
 */
public class TimeInventoryPanel extends AbsJPanel{
    private JComboBox ygbhcomboBox;

    public JComboBox getYgbhcomboBox() {
        return ygbhcomboBox;
    }

    public void setYgbhcomboBox(JComboBox ygbhcomboBox) {
        this.ygbhcomboBox = ygbhcomboBox;
    }

    static String item = "";
    public TimeInventoryPanel() {
        final String[] names = {
                "油罐编号",
                "油品",
                "库存时间",
                "标准体积（L）",
                "油水总高（mm）",
                "净油体积（L）",
                "水高（mm）",
                "水量",
                "平均温度（℃）",
                "空体积（L）"
        };
        // table的数据
        final Object[][] data = {
                {
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                }
        };
        //创建table的模板
        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() { return names.length; }
            public int getRowCount() { return data.length;}
            public Object getValueAt(int row, int col) {return data[row][col];}
            public String getColumnName(int column) {return names[column];}
            public Class getColumnClass(int c) {return getValueAt(0, c).getClass();}
            public boolean isCellEditable(int row, int col) {return col != 1;}
            public void setValueAt(Object aValue, int row, int column) { data[row][column] = aValue; }
        };
        JTable maintable = new JTable(dataModel);

        JLabel rqlab = new JLabel("日期：");
        JTextField rqtext = new JTextField(20);
        DateChooser mp = new DateChooser();
        JLabel ygbhlab = new JLabel("油罐编号：");
        JButton cxbutton = new JButton("查询");
        ygbhcomboBox = new JComboBox();
        ygbhcomboBox.addItem("1");
        ygbhcomboBox.addItem("2");
        ygbhcomboBox.addItem("3");
        ygbhcomboBox.addItem("4");
        ygbhcomboBox.addItem("5");
        cxbutton.addActionListener(new btnCtrl(maintable, item,this));

        JPanel cxtjpanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        cxtjpanel.setLayout(flowLayout);
        cxtjpanel.add(rqlab);
        cxtjpanel.add(mp);
        cxtjpanel.add(ygbhlab);
        cxtjpanel.add(ygbhcomboBox);
        cxtjpanel.add(cxbutton);

        JLabel jLabel = new JLabel("时点库存列表");
        //把主窗口的table放到滚动条中
        JScrollPane jScrollPane = new JScrollPane(maintable);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
        //是用来控制添加进的组件的显示位置
        s.fill = GridBagConstraints.BOTH;
        //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。
        s.gridwidth=0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 1;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        //把滚动条放到主窗口
        this.add(cxtjpanel);
        this.add(jLabel);
        this.add(jScrollPane);
        layout.setConstraints(cxtjpanel, s);
        layout.setConstraints(jLabel,s);
        s.gridwidth=0;
        s.weightx = 1;
        s.weighty=1;
        layout.setConstraints(jScrollPane,s);
        this.setBackground(Color.yellow);
    }
}
