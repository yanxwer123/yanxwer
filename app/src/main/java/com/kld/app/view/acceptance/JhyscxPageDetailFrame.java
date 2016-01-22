package com.kld.app.view.acceptance;

import com.kld.app.service.*;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.util.DoubleDocument;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.DateUtil;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;


/**
 * 进货验收单详情
 */

public class JhyscxPageDetailFrame extends JOptionPane implements Watcher {
    private JDialog frame;
    private static final Logger LOG = Logger.getLogger(JhyscxPage.class);

    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    private JTextField realReceiveL;
    private JTextField heightEmpey;
    private JTextField canTruckTemp;
    private JTextField heightTotal;
    private JTextField heightWater;
    private JTextField cubageTable;
    private JTextField plumbunBankOperator;
    private JTextField calculateOperator;
    private JTextField backBankNo;
    private JTextField planl;
    private JLabel begin;
    private JLabel end;
    private JLabel dischargeL;
    private JLabel fyss;
    private JScrollPane scrollPane;
    private AcceptanceOdRegister odRegister;
    private static List<AcceptanceOdRegisterInfo> odRegisterInfos;
    private IAcceptanceOdRegisterInfoService registerInfoService;
    private IAcceptanceDeliveryService deliveryService;
    private IAcceptanceOdRegisterService registerService;
    private AlarmDailyLostService alarmDailyLostService;
    private SysManageCanInfoService sysManageCanInfoService;
    //private IMonitorTimeInventoryService monitorTimeInventoryService;
    private List<SysManageOilGunInfo> oilGunList;
    private MonitorTimeInventoryService monitorTimeInventoryService;
    private boolean com = true;
    private JTable table;
    private JTable qcsjTable;
    private JTextField isFullDose;
    private JTextField serviceLevel;
    private static Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
    private static Map<String, Double> grmaps = new HashMap<String, Double>();

    /**
     * Create the application.
     */
    public JhyscxPageDetailFrame() {
        initialize();
    }

    private void initialize() {
        // Font font=new Font("宋体",Font,36);
        frame = new JDialog();
        frame.setModal(true);
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window has been closed.
             *
             * @param e
             */
            @Override
            public void windowClosed(WindowEvent e) {
                com = false;
            }
        });
        frame.setTitle("进货验收查看");
        frame.setResizable(false);
        frame.setBounds(100, 100, 660, 600);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        Main.setCenter(frame);
        // 禁用close功能tf.setUndecorated(true);
        // 不显示标题栏,最大化,最小化,退出按钮
        // frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG
        // );
        frame.getContentPane().setLayout(null);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBounds(0, 0, 660, 65);
        frame.getContentPane().add(tablePanel);
        DefaultTableModel model = new DefaultTableModel(new Object[1][5], new String[]{"出库单号", "来油油品", "原发升数(L)", "发货温度(℃)", "原发数量(t)"});
        table = new MyTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, render);
        table.setRowHeight(25);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
   /*     while (columns.hasMoreElements()) {
            TableColumn column = columns.nextElement();
            column.setPreferredWidth(120);
        }*/
        for (int i = 0; i < 5; i++) {
            if (i == 1) {
                table.getColumnModel().getColumn(i).setPreferredWidth(150);
            }
        }
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        tablePanel.add(table, BorderLayout.CENTER);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 60, 660, 505);
        frame.getContentPane().add(tabbedPane);

        JPanel dgjjPanel = new JPanel();
        tabbedPane.addTab(" 地罐交接 ", null, dgjjPanel, null);
        dgjjPanel.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 660, 470);
        dgjjPanel.add(scrollPane);
        scrollPane.setVisible(true);

        //initOilCan(mainPanel);

        JPanel gcjjPanel = new JPanel();
        gcjjPanel.setLocation(-20, 0);
        gcjjPanel.setBackground(UIManager.getColor("Button.light"));
        gcjjPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        tabbedPane.addTab(" 罐车交接", null, gcjjPanel, null);
        gcjjPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.control);
        panel.setBounds(10, 10, 660, 266);

        gcjjPanel.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("原发升数(L):");
        lblNewLabel.setBounds(34, 10, 80, 31);
        panel.add(lblNewLabel);

        planl = new JTextField();
        planl.setBounds(120, 10, 115, 31);
        planl.setDocument(new DoubleDocument());
        panel.add(planl);
        planl.setEnabled(false);
        planl.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("实收升数(L):");
        lblNewLabel_1.setBounds(289, 10, 80, 31);
        panel.add(lblNewLabel_1);

        realReceiveL = new JTextField();
        realReceiveL.setBounds(377, 10, 115, 31);
        panel.add(realReceiveL);
        realReceiveL.setEnabled(false);
        realReceiveL.setDocument(new DoubleDocument());
        realReceiveL.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("空高(mm):");
        lblNewLabel_2.setBounds(53, 51, 64, 31);
        panel.add(lblNewLabel_2);

        heightEmpey = new JTextField();
        heightEmpey.setBounds(120, 48, 115, 31);
        panel.add(heightEmpey);
        heightEmpey.setEnabled(false);
        heightEmpey.setColumns(10);
        heightEmpey.setDocument(new DoubleDocument());

        JLabel lblNewLabel_3 = new JLabel("罐车温度(℃):");
        lblNewLabel_3.setBounds(282, 51, 80, 31);
        panel.add(lblNewLabel_3);

        canTruckTemp = new JTextField();
        canTruckTemp.setBounds(377, 48, 115, 31);
        panel.add(canTruckTemp);
        canTruckTemp.setColumns(10);
        canTruckTemp.setDocument(new DoubleDocument());
        canTruckTemp.setEnabled(false);

        JLabel lblNewLabel_4 = new JLabel("油水总高(mm):");
        lblNewLabel_4.setBounds(29, 92, 100, 31);
        panel.add(lblNewLabel_4);

        heightTotal = new JTextField();
        heightTotal.setBounds(120, 89, 115, 31);
        panel.add(heightTotal);
        heightTotal.setDocument(new DoubleDocument());
        heightTotal.setColumns(10);
        heightTotal.setEnabled(false);

        JLabel lblNewLabel_5 = new JLabel("水高(mm):");
        lblNewLabel_5.setBounds(308, 92, 80, 31);
        panel.add(lblNewLabel_5);

        heightWater = new JTextField();
        heightWater.setBounds(377, 89, 115, 31);
        panel.add(heightWater);
        heightWater.setColumns(10);
        heightWater.setDocument(new DoubleDocument());
        heightWater.setEnabled(false);

        JLabel lblNewLabel_6 = new JLabel("容积表:");
        lblNewLabel_6.setBounds(44, 133, 64, 31);
        lblNewLabel_6.setVisible(false);
        panel.add(lblNewLabel_6);

        cubageTable = new JTextField();
        cubageTable.setBounds(120, 130, 115, 31);
        panel.add(cubageTable);
        cubageTable.setVisible(false);
        cubageTable.setColumns(10);
        cubageTable.setEnabled(false);

        JLabel lblNewLabel_7 = new JLabel("铅封员:");
        lblNewLabel_7.setBounds(319, 133, 80, 31);
        panel.add(lblNewLabel_7);

        plumbunBankOperator = new JTextField();
        plumbunBankOperator.setBounds(377, 130, 115, 31);
        panel.add(plumbunBankOperator);
        plumbunBankOperator.setColumns(10);
        plumbunBankOperator.setEnabled(false);

        JLabel lblNewLabel_8 = new JLabel("计量员:");
        lblNewLabel_8.setBounds(63, 171, 64, 31);
        panel.add(lblNewLabel_8);

        calculateOperator = new JTextField();
        calculateOperator.setBounds(120, 171, 115, 31);
        panel.add(calculateOperator);
        calculateOperator.setColumns(10);
        calculateOperator.setEnabled(false);

        JLabel lblNewLabel_9 = new JLabel("回空铅封号:");
        lblNewLabel_9.setBounds(296, 174, 80, 31);
        panel.add(lblNewLabel_9);

        backBankNo = new JTextField();
        backBankNo.setBounds(377, 171, 115, 31);
        panel.add(backBankNo);
        backBankNo.setColumns(10);
        backBankNo.setEnabled(false);

        JLabel lblNewLabel_10 = new JLabel("数量情况:");
        lblNewLabel_10.setBounds(34, 215, 60, 31);
        lblNewLabel_10.setVisible(false);
        panel.add(lblNewLabel_10);

        isFullDose = new JTextField();
        isFullDose.setVisible(false);
        isFullDose.setBounds(120, 215, 115, 31);

        isFullDose.setEditable(false);
        panel.add(isFullDose);

        JLabel lblNewLabel_11 = new JLabel("服务质量:");
        //lblNewLabel_11.setBounds(289, 212, 80, 31);
        lblNewLabel_11.setBounds(53, 133, 64, 31);
        panel.add(lblNewLabel_11);

        serviceLevel = new JTextField();
        //serviceLevel.setBounds(377, 212, 115, 31);
        serviceLevel.setBounds(120, 130, 115, 31);
        serviceLevel.setEnabled(false);
        panel.add(serviceLevel);


        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
    }

    @SuppressWarnings("rawtypes")
    public void initOilCan(String billno) {
        com = true;
        //region注册观察者开始

        Watched watch = ConcreteWatched.getInstance();
        //Watcher watcher = new JhyscxPageDetailFrame();
        watch.addWetcher("A", this);
        ////System.out.println("注册观察者");
        //endregion
        odRegister = getOdreg(billno);
        if (odRegister.getDeliveryno() == null) {
            billno = odRegister.getManualNo();
        }
        odRegisterInfos = getOdRegisterInfos(billno);
        //region 取油品名称
        if (alarmDailyLostService == null) {
            alarmDailyLostService = Context.getInstance().getBean(AlarmDailyLostService.class);
        }
        //SysManageCanInfo caninfo=registerService
        if (sysManageCanInfoService == null) {
            sysManageCanInfoService = Context.getInstance().getBean(SysManageCanInfoService.class);
        }
        List<SysManageCanInfo> canInfos = sysManageCanInfoService.selectByOilNo(odRegister.getOilno());
        for (SysManageCanInfo item : canInfos) {
            grmaps.put(item.getOilcan().toString(), item.getCubage());
        }
        String oilname = alarmDailyLostService.selectOilNo(odRegister.getOilno());

        // endregion
        JPanel mainPanel = new JPanel();
        scrollPane.setViewportView(mainPanel);
        mainPanel.setLayout(null);
        // 查询卸油信息
        List<AcceptanceOdRegisterInfo> list = odRegisterInfos;
        int panelHeight = 180;
        int top = 0;
        mainPanel.setPreferredSize(new Dimension(620, list.size() * panelHeight));
        mainPanel.setBounds(0, 0, 624, list.size() * panelHeight);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();

        //region 初始化罐
        for (int i = 0; i < list.size(); i++) {

            Map<String, Object> map = new HashMap<String, Object>();
            top = panelHeight * i + 10;
            JPanel panel_1 = new JPanel();
            panel_1.setBackground(UIManager.getColor("Button.light"));
            panel_1.setBounds(10, top, 619, panelHeight);
            panel_1.setBorder(new MatteBorder(1, 0, 0, 0, Color.lightGray));
            //panel_1.setBackground(Color.CYAN);
            mainPanel.add(panel_1);
            panel_1.setLayout(null);

            //region 罐信息
            JLabel lblNewLabel_12 = new JLabel("油罐编号:");
            lblNewLabel_12.setBounds(94, 10, 66, 15);
            panel_1.add(lblNewLabel_12);

            AcceptanceOdRegisterInfo info1 = list.get(i);
            JLabel ygno = new JLabel(info1.getOilcan().toString());
            ygno.setBounds(176, 10, 60, 15);
            panel_1.add(ygno);

            map.put("ygno", ygno);

            JLabel lblNewLabel_13 = new JLabel("油品:");
            lblNewLabel_13.setBounds(94, 30, 54, 15);
            panel_1.add(lblNewLabel_13);

            JLabel oil = new JLabel(oilname);
            oil.setBounds(130, 30, 160, 15);
            panel_1.add(oil);
            map.put("oil", oil);

            JLabel lblNewLabel_14 = new JLabel("净油体积(L):");
            lblNewLabel_14.setBounds(94, 50, 99, 15);
            panel_1.add(lblNewLabel_14);

            JLabel jytj = new JLabel("");
            jytj.setBounds(176, 50, 60, 15);
            panel_1.add(jytj);
            map.put("jytj", jytj);

            JLabel lblNewLabel_15 = new JLabel("空体积(L):");
            lblNewLabel_15.setBounds(94, 70, 85, 15);
            panel_1.add(lblNewLabel_15);

            JLabel ktj = new JLabel("");
            ktj.setBounds(176, 70, 60, 15);
            panel_1.add(ktj);
            map.put("ktj", ktj);

            JLabel label = new JLabel("平均温度(℃):");
            label.setBounds(94, 90, 99, 15);
            panel_1.add(label);


            JLabel pjwd = new JLabel("");
            pjwd.setBounds(176, 90, 60, 15);
            panel_1.add(pjwd);
            map.put("pjwd", pjwd);


            JLabel lbll = new JLabel("水体积(L)：");
            lbll.setBounds(94, 110, 85, 15);
            panel_1.add(lbll);

            JLabel stj = new JLabel("");
            stj.setBounds(176, 110, 60, 15);
            panel_1.add(stj);
            map.put("stj", stj);
            //endregion

            //region 液位数据
            JPanel qcsjTablePanel = new JPanel();
            qcsjTablePanel.setBounds(270, 15, 385, 60);
            panel_1.add(qcsjTablePanel);
            qcsjTablePanel.setLayout(null);
            //qcsjTablePanel.setBackground(Color.red);
            DefaultTableModel qcsjModel = new DefaultTableModel(new Object[][]{
                    {"油水总高(mm)", "净油体积(L)", "标准体积(L)", "平均温度(℃)"}, {"", "", "", ""}, {"", "", "", ""}}, new String[]{"油水总高(mm)", "净油体积(L)", "标准体积(L)", "平均温度(℃)"});
            qcsjTable = new JTable(qcsjModel);
            qcsjTable.setBorder(new LineBorder(new Color(0, 0, 0)));
            qcsjTable.setBounds(40, 0, 307, 60);
            //qcsjTable.setBackground(Color.BLUE);
            qcsjTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            Enumeration<TableColumn> columns = qcsjTable.getColumnModel().getColumns();
            qcsjTable.setDefaultRenderer(Object.class, render);
            qcsjTable.setRowHeight(20);
            while (columns.hasMoreElements()) {
                TableColumn column = columns.nextElement();
                column.setPreferredWidth(60);
                column.setCellRenderer(render);
            }
            qcsjTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            qcsjTable.setRowSelectionAllowed(true);
            qcsjTable.setColumnSelectionAllowed(true);


            qcsjTablePanel.add(qcsjTable);

            SetTable(qcsjTable, list.get(i));

            JPanel panel_7 = new JPanel();
            panel_7.setLayout(null);
            panel_7.setBorder(new MatteBorder(1, 1, 0, 0, Color.black));
            panel_7.setBackground(Color.WHITE);
            panel_7.setBounds(0, 0, 40, 40);
            qcsjTablePanel.add(panel_7);

            JLabel label_15 = new JLabel("前尺");
            label_15.setBounds(8, 12, 54, 15);
            panel_7.add(label_15);

            JPanel panel_8 = new JPanel();
            panel_8.setLayout(null);

            panel_8.setBorder(new MatteBorder(1, 1, 1, 0, Color.black));
            panel_8.setBackground(Color.WHITE);
            panel_8.setBounds(0, 40, 40, 20);
            qcsjTablePanel.add(panel_8);

            JLabel label_16 = new JLabel("后尺");
            label_16.setBounds(8, 2, 54, 15);
            panel_8.add(label_16);

            JLabel label_1 = new JLabel("开始时间：");
            label_1.setBounds(290, 90, 69, 23);
            panel_1.add(label_1);

            JLabel label_2 = new JLabel("结束时间：");
            label_2.setBounds(290, 110, 69, 23);
            panel_1.add(label_2);

            JLabel label_3 = new JLabel("卸油升数(L)：");
            label_3.setBounds(272, 133, 95, 23);
            panel_1.add(label_3);

            JLabel label_4 = new JLabel("付油升数(L)：");
            label_4.setBounds(272, 156, 95, 23);
            panel_1.add(label_4);

            //endregion

            //region 时间
            begin = new JLabel();
            begin.setBounds(365, 91, 150, 21);
            panel_1.add(begin);
            begin.setText(list.get(i).getBegintime() == null ? "" : DateUtil.getDate(list.get(i).getBegintime(), "yyyy-MM-dd HH:mm:ss"));

            end = new JLabel();
            end.setBounds(365, 110, 150, 21);
            panel_1.add(end);
            end.setText(list.get(i).getEndtime() == null ? "" : DateUtil.getDate(list.get(i).getEndtime(), "yyyy-MM-dd HH:mm:ss"));

            dischargeL = new JLabel();
            dischargeL.setBounds(364, 134, 108, 21);
            panel_1.add(dischargeL);
            dischargeL.setText(list.get(i).getDischargel() == null ? "" : list.get(i).getDischargel().toString());

            fyss = new JLabel();
            fyss.setBounds(364, 158, 108, 21);
            panel_1.add(fyss);
            fyss.setText(list.get(i).getDuringsales() == null ? "" : list.get(i).getDuringsales().toString());
            //endregion

            //region 罐图形绘制

            //water
            JPanel panelw = new JPanel();
            panelw.setBackground(Color.decode("#09DFF7"));
            panel_1.add(panelw);


            //oil
            JPanel panel_3 = new JPanel();
            panel_3.setBackground(Color.decode("#EC6C00"));
            panel_3.setBounds(11, 46, 72, 78);
            panel_1.add(panel_3);

            JPanel panel_4 = new JPanel();
            panel_4.setBackground(Color.decode("#A8D183"));
            panel_4.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            panel_4.setBounds(10, 10, 74, 115);
            panel_1.add(panel_4);


            map.put("wpanel", panelw);
            map.put("oilpanel", panel_3);

            //endregion

            maps.put(list.get(i).getOilcan().toString(), map);
        }
        //endregion

        //region 开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                ////System.out.println("线程启动");
                while (com) {
                    i++;
                    try {
                        GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
                        // System.out.println("can send:"+i + gasMsg.toString());
                        Main.CC.writeAndFlush(gasMsg);
                    } catch (Exception e) {
                        LOG.error(this.getClass() + "获取实时罐存异常：" + e.getMessage());
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        //endregion
    }


    /*
    * 初始化出库单，加载进货验收主数据
    * */
    private AcceptanceOdRegister getOdreg(String billno) {
        if (deliveryService == null) {
            deliveryService = Context.getInstance().getBean(IAcceptanceDeliveryService.class);
        }
        showGCInfo(billno);
        AcceptanceDeliveryBills deliveryBill = deliveryService.selectByPrimaryKey(billno);
        if (deliveryBill == null) {
            deliveryBill = new AcceptanceDeliveryBills();
            //出库单表没有查询到 去nobill表查询
            AcceptanceNoBills noBills = deliveryService.getNobillBykey(billno);
            deliveryBill.setDeliveryno(noBills.getDeliveryno());
            deliveryBill.setPlanl(noBills.getPlanl());
            deliveryBill.setPlanton(noBills.getPlanton());
            deliveryBill.setOilno(noBills.getOilno());
            deliveryBill.setFromdepotname(noBills.getFromoildepot());
            deliveryBill.setTonodeno(noBills.getTonodeno());

        }
        TableModel model = table.getModel();
        model.setValueAt(billno, 0, 0);
        //  model.setValueAt(deliveryBill.getOilno(), 0, 1);
        //region 取油品名称
        if (alarmDailyLostService == null) {
            alarmDailyLostService = Context.getInstance().getBean(AlarmDailyLostService.class);
        }

        String ss = alarmDailyLostService.selectOilNo(deliveryBill.getOilno());
        model.setValueAt(ss, 0, 1);
        // endregion
        model.setValueAt(deliveryBill.getPlanl(), 0, 2);
        model.setValueAt(deliveryBill.getDeliverytemp(), 0, 3);
        model.setValueAt(deliveryBill.getPlanton(), 0, 4);

        if (registerService == null) {
            registerService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        }
        AcceptanceOdRegister odRegister = registerService.selectByPrimaryKey(billno);

        return odRegister;
    }


    /*
    *
    * 加载进货验收明细数据
    * */
    private List<AcceptanceOdRegisterInfo> getOdRegisterInfos(String billno) {
        if (registerInfoService == null) {
            registerInfoService = Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
        }
        return registerInfoService.selectByDeliveryNoDate(billno, null, null);
    }

    /**
     * 显示卸油详细信息
     *
     * @param deliveryNo
     */
    public void showGCInfo(String deliveryNo) {
        // 罐车交接信息
        if (registerService == null) {
            registerService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        }
        AcceptanceOdRegister odRegister = registerService.selectByPrimaryKey(deliveryNo);

        if (odRegister != null) {
            planl.setText(odRegister.getPlanl() == null ? "" : odRegister.getPlanl().toString());
            realReceiveL.setText(odRegister.getRealreceivel() == null ? "" : odRegister.getRealreceivel().toString());
            heightEmpey.setText(odRegister.getHeightempey() == null ? "" : odRegister.getHeightempey().toString());
            canTruckTemp.setText(odRegister.getCantrucktemp() == null ? "" : odRegister.getCantrucktemp().toString());
            heightTotal.setText(odRegister.getHeighttotal() == null ? "" : odRegister.getHeighttotal().toString());
            heightWater.setText(odRegister.getHeightwater() == null ? "" : odRegister.getHeightwater().toString());
            cubageTable.setText(odRegister.getCubagetable() == null ? "" : odRegister.getCubagetable().toString());
            plumbunBankOperator.setText(odRegister.getPlumbunbankoperator() == null ? "" : odRegister.getPlumbunbankoperator());
            calculateOperator.setText(odRegister.getCalculateoperator() == null ? "" : odRegister.getCalculateoperator().toString());
            backBankNo.setText(odRegister.getBackbankno() == null ? "" : odRegister.getBackbankno());
            String services = "";
            switch (odRegister.getServicelevel()) {
                case 0:
                    services = "优";
                    break;
                case 1:
                    services = "良";
                    break;
                case 2:
                    services = "一般";
                    break;
                case 3:
                    services = "差";
                    break;
                default:
                    services = "优";
            }
            serviceLevel.setText(services);
        }
    }


    public void UpdateCanpic(Double wpercent, Double opercent, JPanel wpanel, JPanel oil) {
        //water
        Double wh = 115 * wpercent;
        Integer wheight = wh.intValue();
        Integer wy = 115 - wheight + 10;
        wpanel.setBounds(10, wy, 72, wheight - 1);
        ////System.out.println("wy:"+wy+",wheight:"+wheight);
        //oil
        Double oh = 115 * opercent;
        Integer oheight = oh.intValue();
        Integer oy = 115 - oheight + 10 - wheight;
        oil.setBounds(10, oy, 72, oheight - 1);
        ////System.out.println("wy:" + wy + ",wheight:" + wheight);

    }

    private void SetTable(JTable table, AcceptanceOdRegisterInfo info) {

        DecimalFormat df = new DecimalFormat("######0.00");

        table.setValueAt(info.getBeginoilheight() == null ? "" : info.getBeginoilheight().toString(), 1, 0);
        table.setValueAt(info.getBeginoill() == null ? "" : info.getBeginoill().toString(), 1, 1);
        table.setValueAt(info.getBeginv20l() == null ? "" : Double.parseDouble(df.format(info.getBeginv20l())), 1, 2);
        table.setValueAt(info.getEndtemperature() == null ? "" : info.getEndtemperature().toString(), 1, 3);
        table.setValueAt(info.getEndoilheight() == null ? "" : info.getEndoilheight().toString(), 2, 0);
        table.setValueAt(info.getEndoill() == null ? "" : info.getEndoill().toString(), 2, 1);
        table.setValueAt(info.getEndv20l() == null ? "" : Double.parseDouble(df.format(info.getEndv20l())), 2, 2);
        table.setValueAt(info.getEndtemperature() == null ? "" : info.getEndtemperature().toString(), 2, 3);

    }

    @Override
    public void update(GasMsg gasMsg) {

        if (oilGunList == null || oilGunList.isEmpty()) {
            if (monitorTimeInventoryService == null) {
                monitorTimeInventoryService = Context.getInstance().getBean(MonitorTimeInventoryService.class);
            }
            oilGunList = monitorTimeInventoryService.selectAllOilGun();
        }
        if (gasMsg.getPid().equals("A15_10004")) {
            //实时库存
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List<Map<String, ?>> candata = resultMsg.getData();
            //region update caninfo
            for (int m = 0; m < candata.size(); m++) {
                boolean flag = false;
                for (AcceptanceOdRegisterInfo info : odRegisterInfos) {
                    if (info.getOilcan().toString().equals(candata.get(m).get("uOilCanNo").toString())) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    continue;
                }
                Map<String, Object> map = maps.get(candata.get(m).get("uOilCanNo").toString());

                JLabel stj = (JLabel) map.get("stj");
                JLabel pjwd = (JLabel) map.get("pjwd");
                JLabel jytj = (JLabel) map.get("jytj");
                JLabel ktj = (JLabel) map.get("ktj");
                //JLabel oil=(JLabel)map.get("oil");
                JLabel ygbh = (JLabel) map.get("ygno");
                JPanel wpanel = (JPanel) map.get("wpanel");
                JPanel oilpanel = (JPanel) map.get("oilpanel");

                DecimalFormat df = new DecimalFormat("######0.00");

                ygbh.setText(String.valueOf(candata.get(m).get("uOilCanNo")));
                Double dstj = Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fWaterBulk").toString())));
                stj.setText(String.valueOf(dstj));
                Double dpjwd = Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fOilTemp").toString())));
                pjwd.setText(String.valueOf(dpjwd));
                Double djytj = Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fOilCubage").toString())));
                jytj.setText(String.valueOf(djytj));
                Double dktj = Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fEmptyCubage").toString())));
                ktj.setText(String.valueOf(dktj));
                //oil.setText(String.valueOf());


                //double gr=grmaps.get(candata.get(m).get("uOilCanNo").toString());
                double wp = Double.parseDouble(candata.get(m).get("fWaterBulk").toString());
                double ol = Double.parseDouble(candata.get(m).get("fOilCubage").toString());
                double kk = Double.parseDouble(candata.get(m).get("fEmptyCubage").toString());
                double sum = wp + ol + kk;
                if (sum > 0) {
                    UpdateCanpic(wp / sum, ol / sum, wpanel, oilpanel);
                }
            }
            //endregion

        }
    }
}
