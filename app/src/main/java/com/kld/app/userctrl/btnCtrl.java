package com.kld.app.userctrl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTable;

public class btnCtrl  implements ActionListener {
    JTable maintable;
    String boxitem;
    JPanel j;
    public btnCtrl(JTable maintable,String boxitem,JPanel j) {
        this.maintable = maintable;
        this.boxitem = boxitem;
        this.j = j;
    }

    public void actionPerformed(ActionEvent e) {
//        ITimeInventoryMapperService TimeInventoryMapperService =
//                (TimeInventoryMapperServiceImpl)Context.ctx.getBean("timeInventoryMapperService");
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String tankno = TimeInventoryMapperService.
//                getTimeInventoryList(sdf.format(date), Integer.parseInt((String)((TimeInventoryPanel) j).getYgbhcomboBox().getSelectedItem()));
//        ////System.out.println("查询结束");
//        final String[] names = {
//                "油罐编号",
//                "油品",
//                "库存时间",
//                "标准体积（L）",
//                "油水总高（mm）",
//                "净油体积（L）",
//                "水高（mm）",
//                "水量",
//                "平均温度（℃）",
//                "空体积（L）"
//        };
//        // table的数据
//        final Object[][] data = new Object[1][10];
//        data[0][0] = tankno;
//        data[0][1] = tankno;
//        data[0][2] = tankno;
//        data[0][3] = tankno;
//        data[0][4] = tankno;
//        data[0][5] = tankno;
//        data[0][6] = tankno;
//        data[0][7] = tankno;
//        data[0][8] = tankno;
//        data[0][9] = tankno;
//        //创建table的模板
//        TableModel dataModel = new AbstractTableModel() {
//            public int getColumnCount() { return names.length; }
//            public int getRowCount() { return data.length;}
//            public Object getValueAt(int row, int col) {return data[row][col];}
//            public String getColumnName(int column) {return names[column];}
//            //public Class getColumnClass(int c) {return getValueAt(0, c).getClass();}
////            public boolean isCellEditable(int row, int col) {return col != 1;}
//            public void setValueAt(Object aValue, int row, int column) { data[row][column] = aValue; }
//        };
//        maintable.setModel(dataModel);

        /*this.data = new Object[list.size()][10];//一维是查询结果长度，二维是查询列的个数
        for (int i = 0; i < list.size(); i++) {
            data[i][1] = list.get(i).getTankno();
            data[i][2] = list.get(i).getOilcode();
            data[i][3] = list.get(i).getStoretime();
            data[i][4] = list.get(i).getVolumeStandard();
            data[i][5] = list.get(i).getHeightTotal();
            data[i][6] = list.get(i).getVolumeOil();
            data[i][7] = list.get(i).getHeightWater();
            data[i][8] = list.get(i).getWatervolume();
            data[i][9] = list.get(i).getTemperature();
            data[i][10] = list.get(i).getVolumeEmpty();
        }*/
    }

}