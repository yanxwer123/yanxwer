package com.kld.app.view.daily;

import com.kld.app.service.AlarmDailyLostService;
import com.kld.app.service.DailyTradeAccountService;
import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.service.SysManageDictService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.app.util.DoubleDocument;
import com.kld.app.util.IntegerDocument;
import com.kld.app.view.acceptance.ComboboxItem;
import com.kld.app.view.acceptance.MyTable;
import com.kld.gsm.ATG.domain.DailyTradeAccount;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;
import com.kld.gsm.ATG.domain.SysManageOilType;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * 交易记录
 *
 * @author YANGRL
 */
public class JyjlPage {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    //交易记录
    private static final String[] JYJL_TITLES = new String[]{"油机号","枪号", "TTC", "IC卡号", "金额(元)", "支付方式","交易类型","数量(升)", "单价(元/升)", "交易时间","油品" , "累计升数(升)"};
    private Object[][] billArray = {};
    private JTable table;
    private List<DailyTradeAccount> list;
    private JScrollPane scrollPane=new JScrollPane();
    private DailyTradeAccountService dailyTradeAccountService =
            (DailyTradeAccountService) (Context.getInstance().getBean("dailyTradeAccountService"));
    private IAcceptanceOdRegisterService acceptanceOdRegister =
            (IAcceptanceOdRegisterService) (Context.getInstance().getBean("acceptanceOdRegister"));
    //数据字典查数据
    SysManageDictService dictService = (SysManageDictService) (Context.getInstance().getBean("dictService"));

    //中部需要滚动条
    public void setPanel(final JPanel centerPanel) {
        centerPanel.setLayout(null);
        centerPanel.updateUI();

        JLabel jysjLabel = new JLabel("时间：");
        jysjLabel.setFont(Constant.BOTTOM_FONT);
        jysjLabel.setBounds(44, 10, 36, 12);
//        jysjLabel.setForeground(Color.WHITE);
        centerPanel.add(jysjLabel);

        //日期选择器
        final JXDatePicker beginDate = new JXDatePicker();
        beginDate.getEditor().setEditable(false);
        beginDate.setPreferredSize(new Dimension(200, 100));
        beginDate.setDate(new Date());
        beginDate.setFormats(dateFormat1);
        centerPanel.add(beginDate);
        beginDate.setBounds(90, 5, 160, 20);

        JLabel zhilab = new JLabel("至");
//        zhilab.setForeground(Color.WHITE);
        centerPanel.add(zhilab);
        zhilab.setBounds(265, 5, 20, 30);

        final JXDatePicker endDate = new JXDatePicker();
        endDate.setPreferredSize(new Dimension(200, 100));
        endDate.getEditor().setEditable(false);
        endDate.setDate(new Date());
        endDate.setFormats(dateFormat1);
        centerPanel.add(endDate);
        endDate.setBounds(300, 5, 160, 20);

        JLabel bcLabel = new JLabel("班次号：");
        bcLabel.setFont(Constant.BOTTOM_FONT);
        bcLabel.setBounds(515, 10, 60, 12);
//        bcLabel.setForeground(Color.WHITE);
        centerPanel.add(bcLabel);

        final JComboBox bcComboBox = new JComboBox();
        bcComboBox.addItem("全部");
        final HashMap shiftmap = new HashMap();//初始化
        shiftmap.put("begindate", beginDate.getDate());//加油时间
        Calendar calendar1 = new GregorianCalendar();
        calendar1.setTime(endDate.getDate());
        calendar1.add(calendar1.DATE, 1);//
        shiftmap.put("enddate", calendar1.getTime());//加油时间 最大
        ArrayList<String>  dailyTankShifts = dailyTradeAccountService.findLikeShift(shiftmap);
        for (String shift :dailyTankShifts) {
            if("".equals(shift)){
                bcComboBox.addItem("未班结");
            }else{
                bcComboBox.addItem(shift);
            }
        }
        beginDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null!=endDate.getDate() && !"".equals(endDate.getDate())){
                    bcComboBox.removeAllItems();
                    bcComboBox.addItem("全部");
                    final HashMap shiftmap = new HashMap();//初始化
                    shiftmap.put("begindate", beginDate.getDate());//加油时间
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(endDate.getDate());
                    calendar.add(calendar.DATE, 1);
                    shiftmap.put("enddate", calendar.getTime());//加油时间 最大
                    ArrayList<String>  dailyTankShifts = dailyTradeAccountService.findLikeShift(shiftmap);
                    for (String shift :dailyTankShifts) {
                        if("".equals(shift)){
                            bcComboBox.addItem("未班结");
                        }else{
                            bcComboBox.addItem(shift);
                        }
                    }
                }
            }

        });
        endDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null!=beginDate.getDate() && !"".equals(beginDate.getDate())){
                    bcComboBox.removeAllItems();
                    bcComboBox.addItem("全部");
                    final HashMap shiftmap = new HashMap();//初始化
                    shiftmap.put("begindate", beginDate.getDate());//加油时间
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(endDate.getDate());
                    calendar.add(calendar.DATE, 1);//
                    shiftmap.put("enddate", calendar.getTime());//加油时间 最大
                    ArrayList<String>  dailyTankShifts = dailyTradeAccountService.findLikeShift(shiftmap);
                    for (String shift :dailyTankShifts) {
                        if("".equals(shift)){
                            bcComboBox.addItem("未班结");
                        }else{
                            bcComboBox.addItem(shift);

                        }
                    }
                }

            }

        });
        centerPanel.add(bcComboBox);
        bcComboBox.setBounds(565, 5, 109, 20);


        JLabel jsfsLabel = new JLabel("是否回罐：");
        jsfsLabel.setFont(Constant.BOTTOM_FONT);
        jsfsLabel.setBounds(20, 40, 60, 12);
//        jsfsLabel.setForeground(Color.WHITE);
        centerPanel.add(jsfsLabel);

        final JComboBox jsfsLabelcomboBox = new JComboBox();
        jsfsLabelcomboBox.addItem("全部");
        jsfsLabelcomboBox.addItem("是");
        jsfsLabelcomboBox.addItem("否");
        centerPanel.add(jsfsLabelcomboBox);
        jsfsLabelcomboBox.setBounds(90, 35, 160, 20);

        JLabel jeLabel = new JLabel("金额：");
        jeLabel.setFont(Constant.BOTTOM_FONT);
        jeLabel.setBounds(270, 40, 36, 12);
//        jeLabel.setForeground(Color.WHITE);
        centerPanel.add(jeLabel);

        final JTextField je1 = new JTextField();
        je1.setFont(Constant.BOTTOM_FONT);
        je1.setBounds(310, 35, 80, 20);
        je1.setDocument(new DoubleDocument());
        je1.setForeground(Color.BLACK);
        centerPanel.add(je1);


        JLabel Label1 = new JLabel("至");
        Label1.setFont(Constant.BOTTOM_FONT);
        Label1.setBounds(395, 35, 35, 27);
//        Label1.setForeground(Color.WHITE);
        centerPanel.add(Label1);

        final JTextField je2 = new JTextField();
        je2.setFont(Constant.BOTTOM_FONT);
        je2.setBounds(410, 35, 80, 20);
        je2.setDocument(new DoubleDocument());
        je2.setForeground(Color.BLACK);
        centerPanel.add(je2);

        JLabel jeLabel1 = new JLabel("元");
        jeLabel1.setFont(Constant.BOTTOM_FONT);
        jeLabel1.setBounds(490, 41, 30, 12);
//        jeLabel1.setForeground(Color.WHITE);
        centerPanel.add(jeLabel1);

        JLabel jylxLabel = new JLabel("交易类型：");
        jylxLabel.setFont(Constant.BOTTOM_FONT);
        jylxLabel.setBounds(20, 70, 60, 12);
//        jylxLabel.setForeground(Color.WHITE);
        centerPanel.add(jylxLabel);

        final JComboBox jycxLabelcomboBox = new JComboBox();
        final SysManageDictService dictService =(SysManageDictService) (Context.getInstance().getBean(SysManageDictService.class));
        List<String> Namelst=dictService.selectSBByDictID(131); //131表示交易类型
        jycxLabelcomboBox.addItem("全部");//默认第一行为全部
        for (String item:Namelst){
            jycxLabelcomboBox.addItem(item);
        }
        centerPanel.add(jycxLabelcomboBox);
        jycxLabelcomboBox.setBounds(90, 65, 160, 20);


        JLabel qhLabel = new JLabel("枪   号: ");
        qhLabel.setFont(Constant.BOTTOM_FONT);
        qhLabel.setBounds(515, 40, 60, 12);
//        qhLabel.setForeground(Color.WHITE);
        centerPanel.add(qhLabel);
        //枪号
        final JComboBox qhcomboBox = new JComboBox();
        List<SysManageOilGunInfo> list1 = dailyTradeAccountService.selectAllOilGun();
        qhcomboBox.addItem("全部");
        for (SysManageOilGunInfo sysManageOilGunInfo : list1) {
            qhcomboBox.addItem(sysManageOilGunInfo.getOilgun());
        }
        centerPanel.add(qhcomboBox);
        qhcomboBox.setBounds(565, 35, 109, 20);

        JLabel ypLabel = new JLabel("油品：");
        ypLabel.setFont(Constant.BOTTOM_FONT);
        ypLabel.setBounds(270, 70, 36, 12);
//        ypLabel.setForeground(Color.WHITE);
        centerPanel.add(ypLabel);

        List<SysManageOilType> types = acceptanceOdRegister.selectUseOilType();
        //绑定油品
        final JComboBox ypcomboBox = new JComboBox();
        ypcomboBox.addItem("全部");
        if (!types.isEmpty()) {
            for (SysManageOilType oilType : types) {
                String s2 = oilType.getOilname();
                ComboboxItem item = new ComboboxItem(oilType.getOilno(), s2);
                ypcomboBox.addItem(item);
            }
            ;
        }
        centerPanel.add(ypcomboBox);
        ypcomboBox.setBounds(310, 65, 180, 20);

        JLabel TTCLabel = new JLabel("TTC号：");
        TTCLabel.setFont(Constant.BOTTOM_FONT);
        TTCLabel.setBounds(515, 70, 60, 12);
//        TTCLabel.setForeground(Color.WHITE);
        centerPanel.add(TTCLabel);

        final JTextField TTC = new JTextField();
        TTC.setFont(Constant.BOTTOM_FONT);
        TTC.setBounds(565, 65, 109, 20);
        TTC.setDocument(new IntegerDocument());
        TTC.setForeground(Color.BLACK);
        centerPanel.add(TTC);

        JButton cxbutton = new JButton("查询");
        centerPanel.add(cxbutton);
//    cxbutton.setBorderPainted(false);
//    cxbutton.setContentAreaFilled(false);
//    cxbutton.setPressedIcon(Common.createImageIcon(this.getClass(),"search-.png"));
//    cxbutton.setRolloverIcon(Common.createImageIcon(this.getClass(),"search-.png"));
//    cxbutton.setIcon(Common.createImageIcon(this.getClass(),"search.png"));
        cxbutton.setBounds(685, 67, 55, 18);

        final HashMap initmap = new HashMap();//初始化
        initmap.put("shift", null);//查询条件班次号
        initmap.put("oilno", null);//查询条件油品
        initmap.put("backmatchflag", null);//查询是否回罐
        initmap.put("minje", null);//最小金额
        initmap.put("maxje", null);//最大金额
        initmap.put("begindate", beginDate.getDate());//加油时间 最小
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(endDate.getDate());
        calendar.add(calendar.DATE, 1);//
        initmap.put("enddate", calendar.getTime());//加油时间 最大
        initmap.put("oilgun", null);//枪号
        initmap.put("tracode", null);//交易类型
        initmap.put("ttc", null);//ttc
        selectDailyTradeAccount(initmap);
        scrollPane = new JScrollPane();
        scrollPane.setBounds(2, 100, 795, 289);
        centerPanel.add(scrollPane);
        ////System.out.println("初始化的时候的map" + initmap.toString());
        cxbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //时间校验
                if (null == beginDate.getDate() || "".equals(beginDate.getDate())) {
                    JOptionPane.showMessageDialog(null, "请输入开始时间！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else if (null == endDate.getDate() || "".equals(endDate.getDate())) {
                    JOptionPane.showMessageDialog(null, "请输入结束时间！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (endDate.getDate().compareTo(beginDate.getDate()) < 0) {
                    JOptionPane.showMessageDialog(null, "开始时间不能晚于结束时间,请重新选择", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (!"".equals(je1.getText().toString()) && !"".equals(je2.getText().toString())) {
                    if (Double.valueOf(je1.getText().toString()) > Double.valueOf(je2.getText().toString())) {
                        JOptionPane.showMessageDialog(null, "输入金额范围不对，请重新输入", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                final HashMap map = new HashMap();

                if ("全部".equals(bcComboBox.getSelectedItem())) {
                    map.put("shift", null);//查询所有
                } else if ("未班结".equals(bcComboBox.getSelectedItem())) {
                    map.put("shift", "");//查询班次号为""的值
                } else {
                    map.put("shift", bcComboBox.getSelectedItem());//查询条件班次号
                }

                if ("全部".equals(ypcomboBox.getSelectedItem().toString())) {
                    map.put("oilno", null);//查询条件油品
                } else {
                    map.put("oilno", ((ComboboxItem) ypcomboBox.getSelectedItem()).getKey());//查询条件油品
                }

                if ("是".equals(jsfsLabelcomboBox.getSelectedItem())) {
                    map.put("backmatchflag", 1);//查询是否回罐
                } else if ("否".equals(jsfsLabelcomboBox.getSelectedItem())) {
                    map.put("backmatchflag", 0);//查询是否回罐
                } else {
                    map.put("backmatchflag", null);//查询是否回罐
                }
                map.put("minje", je1.getText());//最小金额
                map.put("maxje", je2.getText());//最大金额
                map.put("begindate", beginDate.getDate());//加油时间 最小
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(endDate.getDate());
                calendar.add(calendar.DATE, 1);//
                map.put("enddate", calendar.getTime());//加油时间 最大
                if ("全部".equals(qhcomboBox.getSelectedItem().toString())) {
                    map.put("oilgun", null);//查询条件枪号
                } else {
                    map.put("oilgun", qhcomboBox.getSelectedItem());//查询条件枪号
                }
                if ("全部".equals(jycxLabelcomboBox.getSelectedItem().toString())) {
                    map.put("tracode", null);//查询交易类型
                } else {
                    String Value = dictService.selectByName(jycxLabelcomboBox.getSelectedItem().toString());
                    map.put("tracode", Value);
                }
                map.put("ttc", TTC.getText());//ttc

                selectDailyTradeAccount(map);
                ////System.out.println("加载数据之后的map" + map.toString());

                scrollPane.remove(table);
                getTable(JYJL_TITLES, billArray);
               /* table = getTable(JYJL_TITLES, billArray);
                scrollPane.setViewportView(table);
                table.updateUI();*/
            }
        });
        getTable(JYJL_TITLES, billArray);
        /*table = getTable(JYJL_TITLES, billArray);*/

    }

    private void selectDailyTradeAccount(HashMap map) {
        AlarmDailyLostService alarmDailyLostService =
                (AlarmDailyLostService) (Context.getInstance().getBean("alarmDailyLostService"));
        ////System.out.println("jinrumap" + map.toString());
        list = dailyTradeAccountService.query(map);
        ////System.out.println("打印出List"+list );
//          list = dailyTradeAccountService.findNotRecieved();
        billArray = new Object[list.size()][12];
        for (int i = 0; i < list.size(); i++) {
            //"油机号","枪号","TTC","油品","金额（元）","数量（升）",
            //"单价（元/升）","交易时间","IC卡号","累计升数"
            DailyTradeAccount info = list.get(i);
            billArray[i][0] = info.getMacno();
            billArray[i][1] = info.getOilgun();
            billArray[i][2] = info.getTtc();
            billArray[i][3] = info.getCardNo();
            billArray[i][4] = info.getAmount();
            if(null!=info.getPayMode() && ! "".equals(info.getPayMode())){
                Integer paymode = Integer.parseInt(info.getPayMode().toString());
                billArray[i][5] = dictService.selectBySort(paymode);
            }else {
                billArray[i][5] = null;
            }
            ////System.out.println("打印出的值为"+info.getTraCode());
            if(null!=info.getTraCode() && ! "".equals(info.getTraCode())){
                Integer tracode1 = Integer.parseInt(info.getTraCode().toString());
                billArray[i][6] = dictService.selectBySort1(tracode1);
            }else {
                billArray[i][6] = null;
            }
            billArray[i][7] = new DecimalFormat("0").format(info.getLiter());
            billArray[i][8] = info.getPrice();
            billArray[i][9] = dateFormat.format(info.getTakedate());
            billArray[i][10] = alarmDailyLostService.selectOilNo(info.getOilNo());
            billArray[i][11] = new DecimalFormat("0").format(info.getPumpNo());
        }
    }

    private void getTable(String[] titles, Object[][] data) {
       /* MyTableModel model = new MyTableModel(titles, data);
        JTable table = new MyTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        TableColumn firstColumn = columns.nextElement();
        firstColumn.setPreferredWidth(150);

        // 设置table表头居中
        DefaultTableCellHeaderRenderer thr = new DefaultTableCellHeaderRenderer();
        thr.setHorizontalAlignment(JLabel.CENTER);
        table.getTableHeader().setDefaultRenderer(thr);
        // 设置table内容居中
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, tcr);

        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setRowHeight(20);
        while (columns.hasMoreElements()) {
            TableColumn column = columns.nextElement();
            column.setPreferredWidth(120);
//         column.setCellRenderer(render);
        }
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        table.setRowSelectionAllowed(true);  
//        table.setColumnSelectionAllowed(true);
        //table.setBounds(10, 90, 780, 230);
        return table;*/
        DefaultTableModel model = new DefaultTableModel(data,titles);
        table=new MyTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //居中
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        //table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setRowHeight(20);
        table.setDefaultRenderer(Object.class, render);

        table.setCellSelectionEnabled(false);
        FitTableColumns(table);
        //table.setDefaultRenderer(Object.class, r);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.getColumnModel().getColumns();
        table.setModel(model);
        for (int i = 0; i < titles.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(197);
        }
        table.updateUI();
        FitTableColumns(table);
        scrollPane.setViewportView(table);

    }

    private void FitTableColumns(JTable table) {
        JTableHeader header = table.getTableHeader();
        int rowCount = table.getRowCount();

        Enumeration columns = table.getColumnModel().getColumns();
        while(columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) table.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(table, column.getIdentifier()
                            , false, false, -1, col).getPreferredSize().getWidth();
            if(width<80){
                width=80;
            }
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table,
                        table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                if(Math.max(width, preferedWidth)>=80){
                    width = Math.max(width, preferedWidth);
                }
            }
            header.setResizingColumn(column); // 此行很重要
            column.setWidth(width + table.getIntercellSpacing().width);
        }
    }
}
