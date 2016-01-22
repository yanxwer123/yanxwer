package com.kld.app.view.daily;

import com.kld.app.service.DailyDailyBaseEquipmentService;
import com.kld.app.service.MonitorTimeInventoryService;
import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.acceptance.MyTable;
import com.kld.app.view.main.Constant;
import com.kld.gsm.ATG.domain.DailyDailyBaseEquipment;
import com.kld.gsm.ATG.domain.SysManageCanInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.List;

/**
 * 交易记录
 *
 * @author jiangpei
 *         功能： 设备基础信息查询
 */
public class SbjcxxcxPage {

    private final String[] tableHeads = {
            "油罐编号",
            "产品型号",
            "设备代码",
            "探棒序列号",
            "系统版本号",
            "制造日期"
    };
    // table的数据
    private Object[][] data = new Object[0][tableHeads.length];
    private JPanel sbjcxxcxPagePanel;
    JComboBox ygbhJComboBox;
    private JTable probePartable;
    private JScrollPane scrollPane = new JScrollPane();
    MonitorTimeInventoryService monitorTimeInventoryService = Context.getInstance().getBean(MonitorTimeInventoryService.class);

    //List<DailyDailyBaseEquipment> list = new ArrayList<DailyDailyBaseEquipment>();

    public void setPanel(JPanel centerPanel) {
        centerPanel.setLayout(null);
        scrollPane.setBounds(0, 50, 800, 340);
        centerPanel.add(scrollPane);
        centerPanel.setVisible(true);

        sbjcxxcxPagePanel = new JPanel();
        sbjcxxcxPagePanel.setLayout(null);
        sbjcxxcxPagePanel.setBounds(0, 0, 800, 340);
        sbjcxxcxPagePanel.setVisible(true);
//        sbjcxxcxPagePanel.setBackground(new Color(Integer.decode(com.kld.app.util.Constant.CENTER_BG_COCLER)));
        centerPanel.add(sbjcxxcxPagePanel);

        //油罐编号
        JLabel hold1Label1 = new JLabel("油罐编号：");
//        hold1Label1.setForeground(Color.WHITE);
        hold1Label1.setFont(com.kld.app.util.Constant.BOTTOM_FONT);
        hold1Label1.setBounds(10, 10, 80, 30);
//        hold1Label1.setForeground(new Color(Integer.decode(com.kld.app.util.Constant.HOMEPAGE_COCLER)));
        sbjcxxcxPagePanel.add(hold1Label1);

        ygbhJComboBox = new JComboBox();
        SysManageCanInfoService tankInfoService=(SysManageCanInfoService) (Context.getInstance().getBean("SysManageTankInfoService"));
        List<SysManageCanInfo> TankInfoLst=tankInfoService.selectAll();
        ygbhJComboBox.addItem("全部");
        for (SysManageCanInfo item:TankInfoLst){
            ygbhJComboBox.addItem(item.getOilcan());
        }
        ygbhJComboBox.setEditable(false);
        ygbhJComboBox.setBounds(80, 10, 170, 30);
        sbjcxxcxPagePanel.add(ygbhJComboBox);
        sbjcxxcxPagePanel.updateUI();

        //查询
        JButton cxbutton = new JButton("查询");
        cxbutton.setSize(Constant.widthBut, Constant.heightBut);
        cxbutton.setLocation(300, 10);
        sbjcxxcxPagePanel.add(cxbutton);

//        JTable probePartable = new JTable(data,tableHeads) ;
//        for (int i = 0; i < tableHeads.length; i++) {
//        	probePartable.getColumnModel().getColumn(i).setPreferredWidth(133);
//        }
//        probePartable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        cxbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                DailyDailyBaseEquipmentService dailyDailyBaseEquipmentService = Context.getInstance().getBean(DailyDailyBaseEquipmentService.class);
                //DailyDeviceInfo dailyDeviceInfo = dailyDailyBaseEquipmentService.selectByPrimaryKey(Integer.valueOf(1));
                List<DailyDailyBaseEquipment> deviceInfoList;
                if("全部".equals(ygbhJComboBox.getSelectedItem().toString())){
                     //System.out.println("要查询所有");
                    deviceInfoList   = dailyDailyBaseEquipmentService.findAll();
                }else{
                    //System.out.println("查询----"+ ygbhJComboBox.getSelectedItem().toString());
                    deviceInfoList   = dailyDailyBaseEquipmentService.findByOilCanNo(ygbhJComboBox.getSelectedItem().toString());
                }


                data = new Object[deviceInfoList.size()][tableHeads.length];

                for (int i = 0; i < deviceInfoList.size(); i++) {
                    data[i][0] = deviceInfoList.get(i).getOilcanno();
                    data[i][1] =  deviceInfoList.get(i).getDevicemodel();
                    data[i][2] =  deviceInfoList.get(i).getEquipcode();
                    data[i][3] =  deviceInfoList.get(i).getProbeno();
                    data[i][4] =  deviceInfoList.get(i).getVersion();
                    data[i][5] =   deviceInfoList.get(i).getMakedate();
                }
                //list = new ArrayList<DailyDailyBaseEquipment>();

                  /*  for (int i = 0; i < deviceInfoList.size(); i++) {
                        DailyDailyBaseEquipment dailyDailyBaseEquipment = new DailyDailyBaseEquipment();
                        dailyDailyBaseEquipment.setOilcanno(dailyDeviceInfo.getOilcanno());
                        dailyDailyBaseEquipment.setDevicemodel(dailyDeviceInfo.getDevicemodel());
                        dailyDailyBaseEquipment.setVersion(dailyDeviceInfo.getVersion());
                        Date date = new Date(dailyDeviceInfo.getMakedate() + "");
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dailyDailyBaseEquipment.setMakedate(df.format(date));
                        ////System.out.println(dailyDeviceInfo.getMakedate());
                        dailyDailyBaseEquipment.setEquipcode(dailyDeviceInfo.getEquipcode());
                        //dailyDailyBaseEquipment.setProbeno(deviceInfoList.get(i).getProbeno());
                        list.add(dailyDailyBaseEquipment);
                    }*/
                    probePartable.removeAll();
                    probePartable.repaint();
                    probePartable = getTable(tableHeads, data);
                    probePartable.updateUI();
                    probePartable.setBounds(0, 0, 800, 300);
                    scrollPane.setViewportView(probePartable);
                   // scrollPane.updateUI();
                } catch (Exception ex) {
                    ////System.out.println("-----查询设备基本信息失败------");
                }
            }
        });

        probePartable = getTable(tableHeads, data);
        scrollPane.setViewportView(probePartable);
        centerPanel.setBackground(Color.WHITE);
        cxbutton.doClick(0);
    }

    private JTable getTable(String[] titles, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data,titles);
        JTable table = new MyTable(model);
        //选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        TableColumn firstColumn = columns.nextElement();
        firstColumn.setWidth(60);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        //table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setRowHeight(20);
        table.setDefaultRenderer(Object.class, render);
        while (columns.hasMoreElements()) {
            TableColumn column = columns.nextElement();
            column.setPreferredWidth(150);
        }
        table.setRowSelectionAllowed(true);
        table.setBounds(30, 30, 740, 260);
        return table;
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(800, 600);
        frame.setLocation(300, 200);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent a) {
                System.exit(1);
            }
        });
    }
}
