package com.kld.app.view.acceptance;

import com.kld.app.service.*;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Common;
import com.kld.app.util.DoubleDocument;
import com.kld.app.util.SysConfig;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.DateUtil;
import com.kld.gsm.util.JsonMapper;
import com.kld.gsm.util.V20Utils;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class JhysPage extends JOptionPane implements Watcher,WindowListener {
     //region 属性定义
    private static  String OIL_TYPE_1 = "01";
    private static final Logger LOG = Logger.getLogger(JhysPage.class);
    // 录入方式手工录入
    public static final int ENTERTYPE_FSGLR = 0;
    // 录入方式手工自动
    public static final int ENTERTYPE_SGLR = 1;
    // 开始卸油
    public static final String TRANSSTATUS_FAILD = "0";
    // 结束卸油
    public static final String TRANSSTATUS_END = "0";

    public static final Integer ISDEL_UNDELETE = 0;
    public static final Integer ISDEL_DELETE = 1;
    private IAcceptanceOdRegisterService odRegisterService;
    private IAcceptanceOdRegisterInfoService odRegisterInfoService;
    private DailyTradeAccountService dailyTradeAccountService;
    private IAcceptanceDeliveryService deliveryService;
    private SysManageCanInfoService sysManageCanInfoService;
    private List<SysManageOilGunInfo> gunInfos;
    private List<SysManageCanInfo> canInfos;
    private AlarmDailyLostService alarmDailyLostService;
    private MonitorTimeInventoryService monitorTimeInventoryService;
    private AlarmOilInContrastService alarmOilInContrastService;
    private AcceptSevices acceptSevices;
    private SysmanageService sysmanageService;

/*    private Integer caninfoflag=0; //0 更新全部，1 更新前尺  2更新后尺
    private Integer currentCarno; //要更新的罐*/
    private boolean iswhd;

    //private static Channel cc = Main.CC;

    private AcceptanceDeliveryBills cbill;

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private JDialog frame;
    private JTable table;
    private JXDatePicker date;
    private JTextField shift;
    private JComboBox slqk;
    private JComboBox fwzl;
    private double realGetL = 0;
    private double duringSales = 0;
    private int enterType = ENTERTYPE_FSGLR;
    private boolean completed = false;
    private boolean com=true;
    private JPanel tablePanel;
    private JTextField yfssField;
    private JTextField realGetLField;
    private JTextField kgField;
    private JTextField gcwdField;
    private JTextField yszgField;
    private JTextField sgField;
    private JTextField rjbField;
    private JTextField qfyField;
    private JTextField jlyField;
    private JTextField hkqfhField;
    private CkdcxPage ckdcxPage;
    private AcceptanceOdRegister odRegister;
    private JButton button_3;
    private double realGetV20 = 0;
    private double dischargeLossV20 = 0;
    private double dischargeRateV20 = 0;
    private String Completebillno;
    private static Map<String,Map<String,Object>> maps;
    private int timeout;
    private boolean timeoutflag=true;
    private static Map<String,Double>grmaps=new HashMap<String, Double>();
    JScrollPane scrollPane = new JScrollPane();
    JPanel mainPanel = new JPanel();
    private Map<String, Double> params = new HashMap<String, Double>();
    public boolean getTranStatus(){
        return completed;
    }
    public JDialog getFrame() {
        return frame;
    }
    public void setFrame(JDialog frame) {
        this.frame = frame;
    }
    public CkdcxPage getCkdcxPage() {
        return ckdcxPage;
    }
    public void setCkdcxPage(CkdcxPage ckdcxPage) {
        this.ckdcxPage = ckdcxPage;
    }

    //endregion
    /**
     * Create the application.
     */
    public JhysPage() {
        if (maps==null){
            maps= new HashMap<String, Map<String, Object>>();
        }
        initialize();
        this.odRegisterService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        this.odRegisterInfoService =  Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
        this.dailyTradeAccountService = Context.getInstance().getBean(DailyTradeAccountService.class);
        this.deliveryService = Context.getInstance().getBean(IAcceptanceDeliveryService.class);
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window has been closed.
             *
             * @param e
             */
            @Override
            public void windowClosed(WindowEvent e) {
                com=false;
            }

        });
    }

    public JhysPage(int enterType) {
        this();
        this.enterType = enterType;
    }


    public void initialize() {
        Main.jhys=this;
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("进货验收");
        frame.setResizable(false);
        frame.setBounds(100, 100, 670, 600);
        // frame.setUndecorated(true);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        Main.setCenter(frame);
        // 禁用close功能tf.setUndecorated(true);
        // 不显示标题栏,最大化,最小化,退出按钮
        // frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG
        // );
        frame.getContentPane().setLayout(null);
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBounds(0, 0, 664, 60);
        tablePanel.setBackground(Color.BLUE);
        frame.getContentPane().add(tablePanel);

        button_3 = new JButton("完成卸油");
        //button_3.setComponentZOrder(dgjjPanel,10000);
        button_3.setEnabled(false);
        button_3.setBounds(544, 70, 93, 23);
        //button_3.setBorder(BorderFactory.createLineBorder(Color.cyan));
        frame.getContentPane().add(button_3);

        //region 页面渲染
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(null);
        tabbedPane.setBounds(0, 60, 670, 518);
        //tabbedPane.setBackground(Color.red);
        //tabbedPane.setBackground(Color.BLUE);
        frame.getContentPane().add(tabbedPane);

        JPanel dgjjPanel = new JPanel();
        tabbedPane.addTab(" 地罐交接 ", dgjjPanel);
        dgjjPanel.setLayout(null);
        dgjjPanel.setBackground(Color.cyan);
        dgjjPanel.setPreferredSize(new Dimension(664, 518));

        scrollPane.setBounds(0, 0, 664, 489);
        dgjjPanel.add(scrollPane);
        scrollPane.setVisible(true);
        

        scrollPane.setViewportView(mainPanel);
        mainPanel.setLayout(null);
        try {
            if (sysManageCanInfoService==null){
                sysManageCanInfoService=Context.getInstance().getBean(SysManageCanInfoService.class);
            }
        } catch (Exception e1) {
            LOG.error(e1);
            e1.printStackTrace();
        }

        JPanel gcjjPanel = new JPanel();
        gcjjPanel.setLocation(-20, 0);
        gcjjPanel.setBackground(UIManager.getColor("Button.light"));
        gcjjPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        tabbedPane.addTab(" 罐车交接（选填） ", null, gcjjPanel, null);
        gcjjPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.control);
        panel.setBounds(10, 10, 570, 266);
        gcjjPanel.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("原发升数(L):");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setBounds(34, 10, 76, 31);
        panel.add(lblNewLabel);

        yfssField = new JTextField();
        yfssField.setBounds(120, 15, 115, 25);
        yfssField.setDocument(new DoubleDocument());
        panel.add(yfssField);
        yfssField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("实收升数(L):");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_1.setBounds(289, 10, 76, 31);
        panel.add(lblNewLabel_1);

        realGetLField = new JTextField();
        realGetLField.setBounds(377, 15, 115, 25);
        panel.add(realGetLField);
        realGetLField.setDocument(new DoubleDocument());
        realGetLField.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("空高(mm):");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_2.setBounds(34, 51, 76, 31);
        panel.add(lblNewLabel_2);

        kgField = new JTextField();
        kgField.setBounds(120, 52, 115, 26);
        panel.add(kgField);
        kgField.setDocument(new DoubleDocument());
        kgField.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("罐车温度(℃):");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_3.setBounds(279, 51, 86, 31);
        panel.add(lblNewLabel_3);

        gcwdField = new JTextField();
        gcwdField.setBounds(377, 52, 115, 25);
        panel.add(gcwdField);
        gcwdField.setDocument(new DoubleDocument());
        gcwdField.setColumns(10);

        JLabel yszgLabel = new JLabel("油水总高(mm):");
        yszgLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        yszgLabel.setBounds(25, 92, 85, 31);
        panel.add(yszgLabel);

        yszgField = new JTextField();
        yszgField.setBounds(120, 93, 115, 26);
        panel.add(yszgField);
        yszgField.setDocument(new DoubleDocument());
        yszgField.setColumns(10);
        yfssField.setEnabled(false);

        JLabel lblNewLabel_5 = new JLabel("水高(mm):");
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_5.setBounds(290, 92, 76, 31);
        panel.add(lblNewLabel_5);

        sgField = new JTextField();
        sgField.setBounds(377, 93, 115, 25);
        sgField.setDocument(new DoubleDocument());
        panel.add(sgField);
        sgField.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("容积表:");
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_6.setBounds(34, 130, 76, 31);
        lblNewLabel_6.setVisible(false);
        panel.add(lblNewLabel_6);

        rjbField = new JTextField();
        rjbField.setBounds(120, 130, 115, 25);
        panel.add(rjbField);
        rjbField.setVisible(false);
        rjbField.setColumns(10);

        JLabel lblNewLabel_7 = new JLabel("铅封员:");
        lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_7.setBounds(288, 133, 76, 31);
        panel.add(lblNewLabel_7);

        qfyField = new JTextField();
        qfyField.setBounds(377, 130, 115, 25);
        panel.add(qfyField);
        qfyField.setColumns(10);

        JLabel lblNewLabel_8 = new JLabel("计量员:");
        lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_8.setBounds(33, 171, 76, 31);
        panel.add(lblNewLabel_8);

        jlyField = new JTextField();
        jlyField.setBounds(120, 171, 115, 25);
        panel.add(jlyField);
        jlyField.setColumns(10);

        JLabel lblNewLabel_9 = new JLabel("回空铅封号:");
        lblNewLabel_9.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_9.setBounds(273, 174, 91, 31);
        panel.add(lblNewLabel_9);

        hkqfhField = new JTextField();
        hkqfhField.setBounds(377, 171, 115, 25);
        panel.add(hkqfhField);
        hkqfhField.setColumns(10);

        JLabel lblNewLabel_10 = new JLabel("数量情况:");
        lblNewLabel_10.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_10.setBounds(34, 215, 76, 31);
        lblNewLabel_10.setVisible(false);
        panel.add(lblNewLabel_10);

        DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
        ComboboxItem item1 = new ComboboxItem("0", "足");
        ComboboxItem item2 = new ComboboxItem(OIL_TYPE_1, "不足");
        slqk = new JComboBox(boxModel);
        //slqk.setBounds(120, 215, 115, 25);
        slqk.setEditable(false);
        boxModel.addElement(item1);
        boxModel.addElement(item2);
        slqk.setVisible(false);
        panel.add(slqk);

        JLabel lblNewLabel_11 = new JLabel("服务质量:");
        lblNewLabel_11.setHorizontalAlignment(SwingConstants.RIGHT);
        //lblNewLabel_11.setBounds(289, 212, 76, 31);
        lblNewLabel_11.setBounds(33, 130, 76, 31);
        panel.add(lblNewLabel_11);

        DefaultComboBoxModel fwzlboxModel = new DefaultComboBoxModel();
        ComboboxItem fwzlItem1 = new ComboboxItem("0", "优");
        ComboboxItem fwzlItem2 = new ComboboxItem("1", "良");
        ComboboxItem fwzlItem3 = new ComboboxItem("2", "一般");
        ComboboxItem fwzlItem4 = new ComboboxItem("3", "差");
        fwzlboxModel.addElement(fwzlItem1);
        fwzlboxModel.addElement(fwzlItem2);
        fwzlboxModel.addElement(fwzlItem3);
        fwzlboxModel.addElement(fwzlItem4);
        fwzl = new JComboBox(fwzlboxModel);
        //fwzl.setBounds(377, 215, 115, 25);
        fwzl.setBounds(120, 130, 115, 25);
       // fwzl.setBackground(Color.cyan);
        fwzl.setEditable(false);
        panel.add(fwzl);
        //endregion

        //region 调用ctrl超时定时器
        final Timer timer2=new Timer(1000,null);
        timer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeout--;
                LOG.info(timeout);
                if (timeoutflag && timeout < 0) {
                    timer2.stop();
                    button_3.setEnabled(true);
                    /*cc.close();
                    cc=Main.reLink();*/
                    JOptionPane.showMessageDialog(null, "完成卸油失败", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (!timeoutflag) {
                    button_3.setEnabled(true);
                    timer2.stop();
                }
            }
        });
        //endregion

        //region 完成卸油事件

        button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // region更新卸油主表信息
                AcceptanceOdRegister acceptanceOdRegister = new AcceptanceOdRegister();
                TableModel tableModel = table.getModel();
                double yfss = Double.parseDouble(tableModel.getValueAt(0, 3).toString());
                if (tableModel.getValueAt(0, 4)!=null&&!tableModel.getValueAt(0, 4).toString().equals("")){
                    double yfwd=Double.parseDouble(tableModel.getValueAt(0, 4).toString());
                    yfss=getV20L(OIL_TYPE_1,yfss,yfwd);
                }
                if (yfss > 0&&tableModel.getValueAt(0, 4)!=null&&!tableModel.getValueAt(0, 4).toString().equals("")) {
                    //获取一堆计算的东西
                    Map result = odRegisterService.getodreglossrate(yfss, tableModel.getValueAt(0, 0).toString());
                    System.out.print(result.toString());
                    acceptanceOdRegister.setRealgetl(Double.parseDouble(result.get("Dischargel").toString()));
                    acceptanceOdRegister.setDuringsales(Double.parseDouble(result.get("DuringSales").toString()));
                    acceptanceOdRegister.setRealGetLV20(Double.parseDouble(result.get("V20").toString()));
                    acceptanceOdRegister.setDischargeloss(Double.parseDouble(result.get("loss").toString()));
                    acceptanceOdRegister.setDischargerate(Double.parseDouble(result.get("lossreate").toString()));
                    acceptanceOdRegister.setDischargeLossV20(Double.parseDouble(result.get("v20loss").toString()));

                    /*acceptanceOdRegister.setDuringsales(Double.parseDouble(result.get("DuringSales").toString()));*/


                    Double shsp = Double.parseDouble(result.get("shsp").toString());
                    if (shsp < 0) shsp = 0d;
                    acceptanceOdRegister.setIndemnityloss(shsp);
                    acceptanceOdRegister.setDischargeRateV20(Double.parseDouble(result.get("V20lossrate").toString()));
                }

                if (!iswhd) {
                    acceptanceOdRegister.setDeliveryno(tableModel.getValueAt(0, 0).toString());
                }
                acceptanceOdRegister.setPlanl(cbill.getPlanl());
                acceptanceOdRegister.setManualNo(tableModel.getValueAt(0, 0).toString());
                acceptanceOdRegister.setCreatetime(new Date());// textField_16.getText());
                acceptanceOdRegister.setTranstatus(TRANSSTATUS_END);
                acceptanceOdRegister.setIsdel(ISDEL_UNDELETE);
               /* acceptanceOdRegister.setDuringsales(params.get("duringSales"));*/
                acceptanceOdRegister.setEndtime(new Date());
                if (yfssField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setPlanl(yfssField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(yfssField.getText().trim()));
                }
                if (realGetLField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setRealreceivel(realGetLField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(realGetLField.getText().trim()));
                }
                if (kgField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setHeightempey(kgField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(kgField.getText().trim()));
                }
                if (yszgField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setHeighttotal(yszgField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(yszgField.getText().trim()));
                }
                if (gcwdField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setCantrucktemp(gcwdField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(gcwdField.getText().trim()));
                }
                if (sgField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setHeightwater(sgField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(sgField.getText().trim()));
                }
                if (rjbField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setCubagetable(rjbField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(rjbField.getText().trim()));
                }
                if (qfyField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setPlumbunbankoperator(qfyField.getText().trim());
                }
                if (jlyField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setCalculateoperator(jlyField.getText());
                }
                if (hkqfhField.getText().trim().length() > 0) {
                    acceptanceOdRegister.setBackbankno(hkqfhField.getText());
                }
                if (slqk.getSelectedItem().toString().trim().length() > 0) {
                    acceptanceOdRegister.setIsfulldose(Integer.parseInt(((ComboboxItem) slqk.getSelectedItem()).getKey()));
                }
                if (fwzl.getSelectedItem().toString().trim().length() > 0) {
                    acceptanceOdRegister.setServicelevel(Integer.parseInt(((ComboboxItem) fwzl.getSelectedItem()).getKey()));
                }
                acceptanceOdRegister.setOilno(tableModel.getValueAt(0, 1).toString());
               // System.out.print(acceptanceOdRegister.toString());
                odRegisterService.updateByPrimaryKeySelective(acceptanceOdRegister);
                //endregion

                odRegister=acceptanceOdRegister;

                //region 更新出库单和无货单信息，标记完成
                Completebillno = tableModel.getValueAt(0, 0).toString();

               /* if (iswhd) {
                    AcceptanceNoBills noBills = new AcceptanceNoBills();
                    noBills.setDeliveryno(billno);
                    AcceptanceNoBills tnobill = deliveryService.getNobillBykey(noBills.getDeliveryno());
                    if (tnobill != null) {
                        noBills = tnobill;
                    }
                    noBills.setIscomplete("1");
                    deliveryService.insertNobills(noBills);
                } else {
                    AcceptanceDeliveryBills bill = new AcceptanceDeliveryBills();
                    bill.setDeliveryno(billno);
                    AcceptanceDeliveryBills tbill = deliveryService.selectByPrimaryKey(bill.getDeliveryno());
                    if (tbill != null) {
                        bill = tbill;
                    }
                    bill.setIscomplete("1");
                    deliveryService.updateByPrimaryKey(bill);
                }*/
                //endregion

                // region 通知主调度
                try {
                    button_3.setEnabled(false);
                    timeout=SysConfig.CtrlTimeOut();
                    //cc = Main.CC;
                    System.out.println(Main.CC.hashCode());
                    GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10005.toString());
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("1", Completebillno);
                    map.put("2", "0");
                    ResultMsg msg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
                    List s = new ArrayList();
                    s.add(map);
                    msg.setData(s);
                    gasMsg.setMessage(new JsonMapper().toJson(msg));
                    System.out.println(Main.sign + ",卸油完成 send:" + gasMsg.toString());
                    Main.CC.writeAndFlush(gasMsg);
                    timer2.start();
                } catch (Exception e2) {
                    System.out.println("通知主调度失败");
                }
                //endregion

               /* if (ckdcxPage != null) {
                    try {
                        ckdcxPage.reLoad();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        System.out.println("出库单刷新异常！");
                    }
                }
                completed = true;
                frame.dispose();*/
            }
        });
        //endregion


        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
    }

    /**
     * 初始化油罐组件
     * @param oilCanList
     * @param
     * @return
     */
    private void initOilCan(List<SysManageCanInfo> oilCanList, JPanel mainPanel) throws Exception{

        DecimalFormat df = new DecimalFormat("######0.00");

        //region注册观察者开始

       /* Watched watch = ConcreteWatched.getInstance();*/
        //Watcher watcher = new JhysPage();
        Main.watch.addWetcher("A", this);
        System.out.println("注册观察者");
        //endregion

        com=true;

        canInfos=oilCanList;
        SysManageCanInfo currentcan=new SysManageCanInfo();
        if (oilCanList.isEmpty())return;
        int panelHeight = 252;
        int top = 0;
        if (monitorTimeInventoryService==null){
            Context.getInstance().getBean(MonitorTimeInventoryService.class);
        }
        mainPanel.setPreferredSize(new Dimension(594, oilCanList.size() * panelHeight+20));
        mainPanel.setBounds(0, 0, 620, 5 * panelHeight);

        String oilname="";
        if (!oilCanList.isEmpty()){
            oilname=alarmDailyLostService.selectOilNo(oilCanList.get(0).getOilno());
        }
        for (int i = 0; i < oilCanList.size();i++){
            grmaps.put(oilCanList.get(i).getOilcan().toString(),oilCanList.get(i).getCubage());
            Map canmap=new HashMap<String,JComponent>();
            currentcan=canInfos.get(i);

            //region获取当前进货验收明细 用于恢复页面
            AcceptanceOdRegisterInfoKey key=new AcceptanceOdRegisterInfoKey();
            key.setManualNo(cbill.getDeliveryno());
            key.setOilcan(currentcan.getOilcan());

            if (odRegisterInfoService==null){
                odRegisterInfoService=Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
            }
            AcceptanceOdRegisterInfo oldinfo=odRegisterInfoService.selectByPrimaryKey(key);
            //endregion

            /*MonitorTimeInventory info = monitorTimeInventoryService.selectByPrimaryKey(oilCanList.get(i).getOilcan());*/
            top = panelHeight * i + 10;
            JPanel panel_1 = new JPanel();
            panel_1.setBackground(UIManager.getColor("Button.light"));
            panel_1.setBounds(10, top, 630, panelHeight);
            mainPanel.add(panel_1);
            panel_1.setLayout(null);
    
          /*  JPanel panel_3 = new JPanel();
            panel_3.setBackground(Color.YELLOW);
            panel_3.setBounds(10, 46, 74, 79);
            panel_1.add(panel_3);

            JPanel panel_4 = new JPanel();
            panel_4.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            panel_4.setBackground(Color.WHITE);
            panel_4.setBounds(10, 10, 74, 35);
            panel_1.add(panel_4);*/


            //region 罐图形绘制

            //water
            JPanel panelw=new JPanel();
            panelw.setBackground(Color.decode("#09DFF7"));
            panel_1.add(panelw);

            //oil
            JPanel panel_3 = new JPanel();
            panel_3.setBackground(Color.decode("#EC6C00"));
            panel_3.setBounds(11, 46, 72, 0);
            panel_1.add(panel_3);

            JPanel panel_4 = new JPanel();
            panel_4.setBackground(Color.decode("#ffffff"));
            panel_4.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            panel_4.setBounds(10, 10, 74, 115);
            panel_1.add(panel_4);


            //endregion
            
            JLabel lblNewLabel_12 = new JLabel("油罐编号:");
            lblNewLabel_12.setBounds(90, 10, 66, 15);
            panel_1.add(lblNewLabel_12);
    
            JLabel lblNewLabel_13 = new JLabel("油品:");
            //lblNewLabel_13.setHorizontalAlignment(SwingConstants.RIGHT);
            lblNewLabel_13.setBounds(90, 30, 54, 15);
            panel_1.add(lblNewLabel_13);
    
            JLabel lblNewLabel_14 = new JLabel("净油体积(L):");
            lblNewLabel_14.setBounds(90, 50, 100, 15);
            panel_1.add(lblNewLabel_14);
    
            JLabel lblNewLabel_15 = new JLabel("空体积(L):");
            lblNewLabel_15.setBounds(90, 70, 85, 15);
            panel_1.add(lblNewLabel_15);
    
            JLabel label = new JLabel("平均温度(℃):");
            label.setBounds(90, 90, 108, 15);
            panel_1.add(label);
    
            JLabel lbll = new JLabel("水体积(L):");
            lbll.setBounds(90, 110, 85, 15);
            panel_1.add(lbll);
    
            // 油枪
    
            // 前尺数据
            JPanel qcsjTablePanel = new JPanel();
            qcsjTablePanel.setBounds(10, 140, 374, 80);
            panel_1.add(qcsjTablePanel);
            qcsjTablePanel.setLayout(null);
            DefaultTableCellRenderer render = new DefaultTableCellRenderer();
            render.setHorizontalAlignment(SwingConstants.CENTER);
            DefaultTableModel qcsjModel = new DefaultTableModel(new Object[][] {
                    { "油水总高(mm)", "净油体积(L)", "标准体积(L)", "平均温度(℃)" }, { "", "", "", "" }, { "", "", "", "" } },new String[] { "油水总高(mm)", "净油体积(L)", "标准体积(L)", "平均温度(℃)" });
            final JTable qcsjTable = new JTable(qcsjModel);
            qcsjTable.setBorder(new LineBorder(new Color(0, 0, 0)));
            qcsjTable.setBounds(37, 0, 337, 75);
            qcsjTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            Enumeration<TableColumn> columns = qcsjTable.getColumnModel().getColumns();
            qcsjTable.setDefaultRenderer(Object.class, render);
            qcsjTable.setRowHeight(25);
            while (columns.hasMoreElements()) {
                TableColumn column = columns.nextElement();
                column.setPreferredWidth(63);
                column.setCellRenderer(render);
            }
            qcsjTable.setEnabled(false);
            qcsjTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            qcsjTable.setRowSelectionAllowed(true);
            qcsjTable.setColumnSelectionAllowed(true);
            qcsjTablePanel.add(qcsjTable);
    
            JPanel panel_19 = new JPanel();
            panel_19.setBorder(new MatteBorder(1, 1, 0, 0, Color.black));
            panel_19.setBackground(Color.WHITE);
            panel_19.setBounds(0, 0, 50, 50);
            qcsjTablePanel.add(panel_19);
            panel_19.setLayout(null);
    
            JLabel label_15 = new JLabel("前尺");
            label_15.setBounds(8, 12, 54, 15);
            panel_19.add(label_15);
    
            JPanel panel_20 = new JPanel();
            panel_20.setBorder(new MatteBorder(1, 1, 1, 0, Color.black));
            panel_20.setBackground(Color.WHITE);
            panel_20.setBounds(0, 50, 50, 25);
            qcsjTablePanel.add(panel_20);
            panel_20.setLayout(null);
    
            JLabel label_16 = new JLabel("后尺");
            label_16.setBounds(8, 2, 54, 15);
            panel_20.add(label_16);
    
            JLabel label_1 = new JLabel("开始时间：");
            label_1.setBounds(387, 133, 69, 23);
            panel_1.add(label_1);
    
            JLabel label_2 = new JLabel("结束时间：");
            label_2.setBounds(387, 162, 69, 23);
            panel_1.add(label_2);
    
            JLabel label_3 = new JLabel("卸油升数：");
            label_3.setBounds(387, 191, 95, 23);
            panel_1.add(label_3);

            JLabel label_4 = new JLabel("付油升数：");
            label_4.setBounds(387, 220, 95, 23);
            label_4.setForeground(Color.red);
            panel_1.add(label_4);

            //region 页面按钮渲染
            final List<JComponent> btns=new ArrayList<JComponent>();
            final JButton btnNewButton = new JButton("手工录入");
            btnNewButton.setBounds(10, 220, 93, 23);
            btns.add(btnNewButton);
            panel_1.add(btnNewButton);

            final AcceptanceOdRegisterInfo acceptanceOdRegisterInfo = new AcceptanceOdRegisterInfo();
            final JButton button = new JButton("开始卸油");
            button.setBounds(110, 220, 93, 23);
            btns.add(button);
            panel_1.add(button);


            final JButton cancelbtn = new JButton("作废卸油");
            cancelbtn.setVisible(false);
            cancelbtn.setBounds(288, 220, 93, 23);
            btns.add(cancelbtn);
            panel_1.add(cancelbtn);


            final JButton wyBtn = new JButton("开始稳油");
            wyBtn.setVisible(false);
            wyBtn.setBounds(110, 220, 93, 23);
            btns.add(wyBtn);
            panel_1.add(wyBtn);

            /*
            * 稳油时间分钟
            * */
            final JLabel minute = new JLabel("15");
            minute.setHorizontalAlignment(SwingConstants.CENTER);
            minute.setFont(new Font("\u9ED1\u4F53", minute.getFont().getStyle() | Font.BOLD, 18));
            minute.setBounds(213, 220, 35, 33);
            minute.setVisible(false);
            btns.add(minute);
            panel_1.add(minute);

            final JLabel label_21 = new JLabel("：");
            label_21.setHorizontalAlignment(SwingConstants.CENTER);
            label_21.setFont(new Font("\u9ED1\u4F53", label_21.getFont().getStyle(), 18));
            label_21.setBounds(244, 222, 20, 20);
            label_21.setVisible(false);
            btns.add(label_21);
            panel_1.add(label_21);

            /*
            * 稳油时间秒
            * */
            final JLabel second = new JLabel("00");
            second.setHorizontalAlignment(SwingConstants.CENTER);
            second.setFont(new Font("\u9ED1\u4F53", second.getFont().getStyle() | Font.BOLD, 18));
            second.setBounds(250, 220, 35, 33);
            second.setVisible(false);
            btns.add(second);
            panel_1.add(second);

            final JLabel finishLabel=new JLabel("卸油完成!");
            finishLabel.setVisible(false);
            finishLabel.setBounds(210, 220, 93, 23);
            finishLabel.setBackground(Color.black);
            btns.add(finishLabel);
            finishLabel.setVisible(false);
            panel_1.add(finishLabel);


            final JCheckBox cb1 = new JCheckBox("油枪已停止付油", false);
            cb1.setBounds(234,110,120,18);
            cb1.setBackground(Color.decode("#ffffff"));
            /*cb1.addItemListener(this);*/
            panel_1.add(cb1);

            final JCheckBox cb2 = new JCheckBox("卸前已稳油", false);
            cb2.setBackground(Color.decode("#ffffff"));
            cb2.setBounds(360, 110, 120, 18);
            panel_1.add(cb2);

            //endregion


            //region卸油时间,卸油升数
            final JTextField beginTime = new JTextField();
            beginTime.setEnabled(false);
            beginTime.setBounds(448, 135, 158, 21);
            panel_1.add(beginTime);
            beginTime.setColumns(10);


            final JTextField endTime = new JTextField();
            endTime.setEnabled(false);
            endTime.setColumns(10);
            endTime.setBounds(448, 164, 158, 21);
            panel_1.add(endTime);

            /*本次卸油升数*/
            final JTextField bcxyssField = new JTextField();
            //bcxyssField.setEditable(false);
            bcxyssField.setEnabled(false);
            bcxyssField.setColumns(10);
            bcxyssField.setBounds(448, 193, 158, 21);
            panel_1.add(bcxyssField);

            final JTextField fyssField = new JTextField();
            fyssField.setEditable(false);
            fyssField.setEnabled(false);
            fyssField.setColumns(10);
            fyssField.setForeground(Color.red);
            fyssField.setBounds(448, 222, 158, 21);
            panel_1.add(fyssField);

            //endregion

            final List<SysManageOilGunInfo> guns=new ArrayList<SysManageOilGunInfo>();

            //region渲染油枪
            for (SysManageOilGunInfo gunInfo:gunInfos){
                if (gunInfo.getOilcan().equals(oilCanList.get(i).getOilcan())){
                    guns.add(gunInfo);
                }
            }

            for(int j=0;j<guns.size();j++){
                    JLabel jgunno = new JLabel(guns.get(j).getOilgun().toString());
                    jgunno.setBounds(285 + j % 4 * 70, 10 + j / 4 * 32, 30, 20);
                    jgunno.setBorder(BorderFactory.createLineBorder(Color.lightGray));
                    jgunno.setBackground(Color.GREEN);
                    jgunno.setHorizontalTextPosition(SwingConstants.CENTER);
                    panel_1.add(jgunno);

                   /* JPanel panel_7 = new JPanel();
                    panel_7.setBackground(Color.GREEN);
                    //panel_7.setIcon(Common.createImageIcon(this.getClass(), "green_8.png"));
                    panel_7.setEnabled(false);
                    panel_7.setBounds(317 + j % 4 * 70, 10 + j / 4 * 32, 30, 30);
                    panel_1.add(panel_7);*/

                 JButton panel_7 = new JButton();
                 panel_7.setBorderPainted(false);
                 panel_7.setContentAreaFilled(false);
                 panel_7.setIcon(Common.createImageIcon(this.getClass(), "green_8.png"));
                 panel_7.setBounds(317 + j % 4 * 70, 10 + j / 4 * 32, 30, 30);
                 panel_1.add(panel_7);

                    canmap.put(guns.get(j).getOilgun(),panel_7);
            }
            canmap.put("table",table);

           //endregion

            //region 定时器
            final Timer timer = new Timer(1000, null);
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int m = Integer.parseInt(minute.getText());
                    int s = Integer.parseInt(second.getText());
                    if (m == 0 && s == 0) {
                        /*button.setEnabled(true);*/
                        button.setText("结束卸油");
                        button.doClick();
                        finishLabel.setVisible(true);
                        cancelbtn.setVisible(true);

                        timer.stop();
                    } else {
                        if (s == 0) {
                            s = 59;
                            m--;
                        } else {
                            s--;
                        }
                        if (m < 10) {
                            minute.setText("0" + m);
                        } else {
                            minute.setText(m + "");
                        }
                        if (s < 10) {
                            second.setText("0" + s);
                        } else {
                            second.setText(s + "");
                        }
                    }
                }
            });
            //endregion

            //region 稳油事件
            wyBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (acceptanceOdRegisterInfo.getStablebegintime()==null)
                    {
                        acceptanceOdRegisterInfo.setStablebegintime(new Date());
                    }
                    for (JComponent item : btns) {
                        item.setVisible(false);
                    }
                    minute.setVisible(true);
                    second.setVisible(true);
                    label_21.setVisible(true);
                    button.setBounds(288, 220, 93, 23);
                    button.setText("结束稳油");
                    button.setVisible(true);
                    timer.start();
                    // wyBtn.setEnabled(false);
                    //保存稳油开始时间
                    if (odRegisterInfoService==null){
                        odRegisterInfoService=Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
                    }
                    odRegisterInfoService.merge(acceptanceOdRegisterInfo);

                }
            });
            //endregion

            //region 油罐要素
            final JLabel ygbhLabel = new JLabel("");
            ygbhLabel.setHorizontalAlignment(SwingConstants.LEFT);
            ygbhLabel.setBounds(180, 10, 100, 15);
            panel_1.add(ygbhLabel);
    
            JLabel oilNoLabel = new JLabel(oilname);
            oilNoLabel.setHorizontalAlignment(SwingConstants.LEFT);
            oilNoLabel.setBounds(120, 30, 160, 15);
            panel_1.add(oilNoLabel);
    
            final JLabel jytjLabel = new JLabel("");
            jytjLabel.setHorizontalAlignment(SwingConstants.LEFT);
            jytjLabel.setBounds(180, 50, 100, 15);
            panel_1.add(jytjLabel);
    
            JLabel ktjLabel = new JLabel("");
            ktjLabel.setHorizontalAlignment(SwingConstants.LEFT);
            ktjLabel.setBounds(180, 70, 100, 15);
            panel_1.add(ktjLabel);
    
            final JLabel pjwdLabel = new JLabel("");
            pjwdLabel.setHorizontalAlignment(SwingConstants.LEFT);
            pjwdLabel.setBounds(180, 90, 100, 15);
            panel_1.add(pjwdLabel);
    
            JLabel stjLabel = new JLabel("");
            stjLabel.setHorizontalAlignment(SwingConstants.LEFT);
            stjLabel.setBounds(180, 110, 100, 15);
            panel_1.add(stjLabel);

            final JLabel yszgLabel=new JLabel("");
            yszgLabel.setVisible(false);
            yszgLabel.setBounds(180, 110, 100, 15);
            panel_1.add(yszgLabel);

            final JLabel bztjLabel=new JLabel("");
            bztjLabel.setVisible(false);
            bztjLabel.setBounds(180, 110, 100, 15);
            panel_1.add(bztjLabel);
            //endregion

            canmap.put("ygbh", ygbhLabel);
            canmap.put("oilno", oilNoLabel);
            canmap.put("jytj", jytjLabel);
            canmap.put("ktj", ktjLabel);
            canmap.put("pjwd", pjwdLabel);
            canmap.put("stj",stjLabel);
            canmap.put("yszg",yszgLabel);
            canmap.put("bztj",bztjLabel);
            canmap.put("wpanel",panelw);
            canmap.put("oilpanel",panel_3);

            // 设置界面面油罐信息

            ygbhLabel.setText(oilCanList.get(i).getOilcan().toString());
           // oilNoLabel.setText(info.getOilno());
          /*  jytjLabel.setText(info.getOill().toString());
            ktjLabel.setText(info.getVolumeempty().toString());
            pjwdLabel.setText("25");
            stjLabel.setText("30");*/

            //region 加载关闭窗体前的数据
            if (oldinfo!=null){
                acceptanceOdRegisterInfo.setDeliveryno(oldinfo.getDeliveryno());
                acceptanceOdRegisterInfo.setManualNo(oldinfo.getManualNo());
                acceptanceOdRegisterInfo.setOilno(oldinfo.getOilno());
                acceptanceOdRegisterInfo.setTranstatus(oldinfo.getTranstatus());
                acceptanceOdRegisterInfo.setStableendtime(oldinfo.getStableendtime());
                acceptanceOdRegisterInfo.setStablebegintime(oldinfo.getStablebegintime());
                acceptanceOdRegisterInfo.setBeginoilheight(oldinfo.getBeginoilheight());
                acceptanceOdRegisterInfo.setBeginoill(oldinfo.getBeginoill());
                acceptanceOdRegisterInfo.setBegintemperature(oldinfo.getBegintemperature());
                acceptanceOdRegisterInfo.setBegintime(oldinfo.getBegintime());
                acceptanceOdRegisterInfo.setBeginv20l(oldinfo.getBeginv20l());
                acceptanceOdRegisterInfo.setEndoilheight(oldinfo.getEndoilheight());
                acceptanceOdRegisterInfo.setEndoill(oldinfo.getEndoill());
                acceptanceOdRegisterInfo.setEndtemperature(oldinfo.getEndtemperature());
                acceptanceOdRegisterInfo.setEndtime(oldinfo.getEndtime());
                acceptanceOdRegisterInfo.setEndv20l(oldinfo.getEndv20l());
                acceptanceOdRegisterInfo.setEntertype(oldinfo.getEntertype());
                acceptanceOdRegisterInfo.setIsbeforestable(oldinfo.getIsbeforestable());
                acceptanceOdRegisterInfo.setIsdel(oldinfo.getIsdel());
                acceptanceOdRegisterInfo.setForcecancelstable(oldinfo.getForcecancelstable());
                acceptanceOdRegisterInfo.setDuringsales(oldinfo.getDuringsales());
                acceptanceOdRegisterInfo.setShift(oldinfo.getShift());
                acceptanceOdRegisterInfo.setDischargel(oldinfo.getDischargel());
                acceptanceOdRegisterInfo.setCreatetime(oldinfo.getCreatetime());
                acceptanceOdRegisterInfo.setOilcan(oldinfo.getOilcan());


                //加载关闭窗体前的数据
                qcsjTable.setValueAt(oldinfo.getBeginoilheight(), 1, 0);
                qcsjTable.setValueAt(oldinfo.getBeginoill(), 1, 1);
                qcsjTable.setValueAt(oldinfo.getBeginv20l(), 1, 2);
                qcsjTable.setValueAt(oldinfo.getBegintemperature(), 1, 3);

                qcsjTable.setValueAt(oldinfo.getEndoilheight(),2,0);
                qcsjTable.setValueAt(oldinfo.getEndoill(),2,1);
                qcsjTable.setValueAt(oldinfo.getEndv20l(),2,2);
                qcsjTable.setValueAt(oldinfo.getEndtemperature(), 2, 3);

                beginTime.setText(DateUtil.getDate(oldinfo.getBegintime() == null ? null : oldinfo.getBegintime(), "yyyy-MM-dd HH:mm:ss"));
                endTime.setText(DateUtil.getDate(oldinfo.getEndtime() == null ? null : oldinfo.getEndtime(), "yyyy-MM-dd HH:mm:ss"));

                bcxyssField.setText(oldinfo.getDischargel() == null ? "" : oldinfo.getDischargel().toString());
                fyssField.setText(oldinfo.getDuringsales()== null ? "" : oldinfo.getDuringsales().toString());
                cb1.setSelected(true);
                cb2.setSelected(true);
                cb1.setEnabled(false);
                cb2.setEnabled(false);
                UpdateButton3Status(oldinfo.getManualNo());
                for (JComponent item:btns){
                    item.setVisible(false);
                }
                //设置按钮状态
                if (oldinfo.getBegintime()!=null&&oldinfo.getEndtime()!=null){
                    //卸油结束
                    finishLabel.setVisible(true);
                    cancelbtn.setVisible(true);
                    /*cb1.setSelected(true);
                    cb2.setSelected(true);
                    cb1.setEnabled(false);
                    cb2.setEnabled(false);*/

                    acceptanceOdRegisterInfo.setDeliveryno(oldinfo.getDeliveryno());
                    acceptanceOdRegisterInfo.setManualNo(oldinfo.getManualNo());
                    acceptanceOdRegisterInfo.setOilno(oldinfo.getOilno());
                    acceptanceOdRegisterInfo.setTranstatus(oldinfo.getTranstatus());
                    acceptanceOdRegisterInfo.setStableendtime(null);
                    acceptanceOdRegisterInfo.setStablebegintime(null);
                    acceptanceOdRegisterInfo.setBeginoilheight(null);
                    acceptanceOdRegisterInfo.setBeginoill(null);
                    acceptanceOdRegisterInfo.setBegintemperature(null);
                    acceptanceOdRegisterInfo.setBegintime(null);
                    acceptanceOdRegisterInfo.setBeginv20l(null);
                    acceptanceOdRegisterInfo.setEndoilheight(null);
                    acceptanceOdRegisterInfo.setEndoill(null);
                    acceptanceOdRegisterInfo.setEndtemperature(null);
                    acceptanceOdRegisterInfo.setEndtime(null);
                    acceptanceOdRegisterInfo.setEndv20l(null);
                    acceptanceOdRegisterInfo.setEntertype(oldinfo.getEntertype());
                    acceptanceOdRegisterInfo.setIsbeforestable(0);
                    acceptanceOdRegisterInfo.setIsdel(0);
                    acceptanceOdRegisterInfo.setForcecancelstable(0);
                    acceptanceOdRegisterInfo.setDuringsales(null);
                    acceptanceOdRegisterInfo.setShift(null);
                    acceptanceOdRegisterInfo.setDischargel(null);
                    acceptanceOdRegisterInfo.setCreatetime(oldinfo.getCreatetime());
                    acceptanceOdRegisterInfo.setOilcan(oldinfo.getOilcan());
                }
                if (oldinfo.getBegintime()!=null&&oldinfo.getEndtime()==null&&oldinfo.getStablebegintime()==null){
                    //开始卸油后，但没有稳油
                    wyBtn.setVisible(true);
                    button.setText("结束卸油");
                    button.setBounds(288, 220, 93, 23);
                    button.setVisible(true);
                    btnNewButton.setVisible(true);
                }
                if (oldinfo.getBegintime()!=null&&oldinfo.getEndtime()==null&&oldinfo.getStablebegintime()!=null){
                    //开始稳油，关闭前没有稳油
                    //计算稳油倒计时时间
                  /*  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date now = df.parse(new Date().toString());
                    Date date=df.parse(oldinfo.getStablebegintime().toString())*/;
                    Date now=new Date();
                    Date date=oldinfo.getStablebegintime();
                    long s=now.getTime()-date.getTime();
                    if (s>900000){
                        //稳油结束，结束卸油
                        button.setText("结束卸油");
                        button.doClick();
                        finishLabel.setVisible(true);
                        cancelbtn.setVisible(true);
                    }else{
                        long ls=900000-s;
                        long m=ls/60000;
                        long ss=ls%60000;
                        minute.setText(m + "");
                        second.setText(ss / 1000 + "");
                        for (JComponent item:btns){
                            item.setVisible(false);
                        }
                        minute.setVisible(true);
                        second.setVisible(true);
                        label_21.setVisible(true);
                        wyBtn.setText("开始稳油");
                        wyBtn.doClick();

                    }
                    System.out.println(s);
                }
            }
            //endregion 加载关闭窗体前的数据结束

            Main.jhys=this;
            //region手工录入事件
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    TableModel tableModel = table.getModel();
                    if (button.isVisible()&&button.getText()=="开始卸油") {
                        acceptanceOdRegisterInfo.setCreatetime(new Date());
                    }else{
                        acceptanceOdRegisterInfo.setBeginoilheight(Double.parseDouble(qcsjTable.getValueAt(1, 0).toString()));
                        acceptanceOdRegisterInfo.setBeginoill(Double.parseDouble(qcsjTable.getValueAt(1,1).toString()));
                        acceptanceOdRegisterInfo.setBeginv20l(Double.parseDouble(qcsjTable.getValueAt(1, 2).toString()));
                        acceptanceOdRegisterInfo.setBegintemperature( Double.parseDouble(qcsjTable.getValueAt(1,3).toString()));
                        try {
                            acceptanceOdRegisterInfo.setBegintime(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss",beginTime.getText()));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                    acceptanceOdRegisterInfo.setOilno(cbill.getOilno());
                    acceptanceOdRegisterInfo.setDeliveryno(tableModel.getValueAt(0, 0).toString());
                    acceptanceOdRegisterInfo.setManualNo(tableModel.getValueAt(0, 0).toString());
                    acceptanceOdRegisterInfo.setOilcan(Integer.parseInt(ygbhLabel.getText().trim()));
                    acceptanceOdRegisterInfo.setIsdel(0);
                    acceptanceOdRegisterInfo.setEntertype(enterType);

                    JhysPageShlrFrame window = new JhysPageShlrFrame(tableModel.getValueAt(0, 0).toString(), tableModel
                            .getValueAt(0, 1).toString(), Integer.parseInt(ygbhLabel.getText().trim()), Double
                            .parseDouble(tableModel.getValueAt(0, 3).toString()),cbill,acceptanceOdRegisterInfo);
                    window.iswhd=iswhd;
                    window.setCkdcxPage(ckdcxPage);
                    window.getFrame().setVisible(true);
                    //frame.setVisible(false);
                    //frame.dispose();

                }
            });
            //endregion

            //region 作废卸油事件
            cancelbtn.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 取消卸油
                    // 删除卸油主表数据
                    TableModel tableModel = table.getModel();
                    String deliveryNo = tableModel.getValueAt(0, 0).toString();
                    //odRegisterService.deleteByPrimaryKey(deliveryNo);
                    // 删除卸油明细表数据
                    AcceptanceOdRegisterInfoKey key = new AcceptanceOdRegisterInfoKey();
                    key.setManualNo(cbill.getDeliveryno());
                    key.setOilcan(Integer.parseInt(ygbhLabel.getText().trim()));
                    odRegisterInfoService.deleteByPrimaryKey(key);
                    for (JComponent item : btns) {
                        item.setVisible(false);
                    }
                    btnNewButton.setVisible(true);
                    button.setText("开始卸油");
                    button.setBounds(110, 220, 93, 23);
                    button.setVisible(true);
                    cb1.setEnabled(true);
                    cb2.setEnabled(true);
                    cb1.setSelected(false);
                    cb2.setSelected(false);
                    minute.setText("15");
                    second.setText("00");
                    for (int row = 1; row < 3; row++) {
                        for (int col = 0; col < 4; col++) {
                            qcsjTable.setValueAt("", row, col);
                        }
                    }
                    beginTime.setText("");
                    endTime.setText("");
                    bcxyssField.setText("");
                    //region 更新完成卸油状态
                    acceptanceOdRegisterInfo.setEndtime(null);
                    acceptanceOdRegisterInfo.setBegintime(null);
                    acceptanceOdRegisterInfo.setBeginv20l(null);
                    acceptanceOdRegisterInfo.setBeginoilheight(null);
                    acceptanceOdRegisterInfo.setStablebegintime(null);
                    acceptanceOdRegisterInfo.setStableendtime(null);
                    acceptanceOdRegisterInfo.setBeginoill(null);
                    acceptanceOdRegisterInfo.setBegintemperature(null);
                    acceptanceOdRegisterInfo.setDischargel(null);
                    acceptanceOdRegisterInfo.setCreatetime(null);
                    acceptanceOdRegisterInfo.setEndoill(null);
                    acceptanceOdRegisterInfo.setEndoilheight(null);
                    acceptanceOdRegisterInfo.setEndv20l(null);
                    acceptanceOdRegisterInfo.setEndtemperature(null);
                    acceptanceOdRegisterInfo.setForcecancelstable(1);
                    UpdateButton3Status(cbill.getDeliveryno());
                    //endregion
                }
            });
            //endregion

            //region开始卸油事件
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (button.getText().equals("开始卸油")) {
                        if (!(cb1.isSelected() && cb2.isSelected())) {
                            JOptionPane.showMessageDialog(null, "开始卸油前请确认油枪已停止付油和卸油前稳油", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        if (Main.CC == null) {
                            JOptionPane.showMessageDialog(null, "获取液位仪数据出现故障，请手动录入", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        /*更新其他按钮状态开始*/
                        for (JComponent item : btns) {
                            item.setVisible(false);
                        }
                        wyBtn.setVisible(true);
                        cancelbtn.setVisible(true);
                        cb1.setEnabled(false);
                        cb2.setEnabled(false);
                        btnNewButton.setVisible(true);
                        /*更新其他按钮状态结束*/
                        beginTime.setText(dateFormat.format(new Date()));
                        // 保存出库单信息
                        TableModel tableModel = table.getModel();
                        // 获取油罐前尺数据

                        double heightTotal = yszgLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(yszgLabel.getText().trim());
                        double oilL = jytjLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(jytjLabel.getText().trim());
                        double stardardL = bztjLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(bztjLabel.getText().trim());
                        double temperature = pjwdLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(pjwdLabel.getText().trim());
                        TableModel tableModel1 = qcsjTable.getModel();
                        tableModel1.setValueAt(heightTotal, 1, 0);
                        tableModel1.setValueAt(oilL, 1, 1);
                        tableModel1.setValueAt(getV20L(OIL_TYPE_1, temperature, oilL),1, 2);
                        tableModel1.setValueAt(temperature, 1, 3);
                        qcsjTable.updateUI();

                        acceptanceOdRegisterInfo.setCreatetime(new Date());
                        acceptanceOdRegisterInfo.setDeliveryno(tableModel.getValueAt(0, 0).toString());
                        acceptanceOdRegisterInfo.setManualNo(tableModel.getValueAt(0, 0).toString());
                        acceptanceOdRegisterInfo.setOilcan(Integer.parseInt(ygbhLabel.getText().trim()));
                        acceptanceOdRegisterInfo.setBeginoilheight(heightTotal);
                        acceptanceOdRegisterInfo.setOilno(cbill.getOilno());
                        acceptanceOdRegisterInfo.setBeginoill(oilL);
                        acceptanceOdRegisterInfo.setBeginv20l(getV20L(OIL_TYPE_1, temperature, oilL));
                        acceptanceOdRegisterInfo.setBegintemperature(temperature);
                        acceptanceOdRegisterInfo.setBegintime(new Date());
                        acceptanceOdRegisterInfo.setIsdel(0);
//                        acceptanceOdRegisterInfo.setTranstatus(TRANSSTATUS_BEGIN);
                        acceptanceOdRegisterInfo.setEntertype(enterType);
                        // 保存卸油明细信息
                        odRegisterInfoService.insert(acceptanceOdRegisterInfo);
                        UpdateButton3Status(acceptanceOdRegisterInfo.getManualNo());
                        //region 通知湖南开始卸油
                        try {
                            transfor(acceptanceOdRegisterInfo.getDeliveryno(), acceptanceOdRegisterInfo.getOilcan().toString(), "2");

                        } catch (Exception es) {
                            System.out.println("notice hn");
                        }
                        //endregion
                    } else {
                        acceptanceOdRegisterInfo.setStableendtime(new Date());
                        //acceptanceOdRegisterInfo = new AcceptanceOdRegisterInfo();
                        if (button.getText().equals("结束卸油")) {
                            for (JComponent item : btns) {
                                item.setVisible(false);
                            }

                            acceptanceOdRegisterInfo.setForcecancelstable(0);
                            finishLabel.setVisible(true);
                            cancelbtn.setVisible(true);
                        } else {
                            //强制结束稳油
                            timer.stop();
                            button.setVisible(false);
                            cancelbtn.setVisible(true);
                            finishLabel.setVisible(true);
                            label_21.setVisible(false);
                            minute.setVisible(false);
                            second.setVisible(false);
                            acceptanceOdRegisterInfo.setForcecancelstable(1);
                        }
                        endTime.setText(dateFormat.format(new Date()));
                        bcxyssField.setEnabled(true);
                        // 获取油罐后尺数据


                        double heightTotal = yszgLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(yszgLabel.getText().trim());
                        double oilL = jytjLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(jytjLabel.getText().trim());
                        double stardardL = bztjLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(bztjLabel.getText().trim());
                        double temperature = pjwdLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(pjwdLabel.getText().trim());
                        TableModel tableModel = qcsjTable.getModel();
                        tableModel.setValueAt(heightTotal, 2, 0);
                        tableModel.setValueAt(oilL, 2, 1);
                        tableModel.setValueAt(getV20L(OIL_TYPE_1, temperature, oilL), 2, 2);
                        tableModel.setValueAt(temperature, 2, 3);
                        qcsjTable.updateUI();

                        TableModel tableModel1 = table.getModel();
                        acceptanceOdRegisterInfo.setDeliveryno(tableModel1.getValueAt(0, 0).toString());
                        acceptanceOdRegisterInfo.setOilcan(Integer.parseInt(ygbhLabel.getText().trim()));
                        acceptanceOdRegisterInfo.setEndoilheight(heightTotal);
                        acceptanceOdRegisterInfo.setEndoill(oilL);
                        acceptanceOdRegisterInfo.setEndv20l(getV20L(OIL_TYPE_1, temperature, oilL));
                        acceptanceOdRegisterInfo.setEndtemperature(temperature);
                        acceptanceOdRegisterInfo.setEndtime(new Date());
                        try {
                           /* Map literMap = dailyTradeAccountService.GetSaleOilSumByCanNoAndDate(1, dateFormat.parse(beginTime.getText()), dateFormat.parse(endTime.getText()));
                            if (literMap == null) {
                                duringSales = 0;
                            } else {
                                duringSales = Double.parseDouble(literMap.get("liter") == null ? "0" : literMap.get("liter").toString());
                            }*/
                            duringSales = 0.0;
                            Map salemap = dailyTradeAccountService.GetSaleOilSumByCanNoAndDate(acceptanceOdRegisterInfo.getOilcan().toString(), dateFormat.parse(beginTime.getText()), dateFormat.parse(endTime.getText()));
                            if (salemap!=null&&salemap.get("Liter") != null) {
                                duringSales = Double.parseDouble(salemap.get("Liter").toString());
                            }
                            fyssField.setText(duringSales+"");
                            System.out.println("卸油中销售" + duringSales);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        acceptanceOdRegisterInfo.setDuringsales(duringSales);
                        //region oldcode
                        //params.put("duringSales", duringSales + (params.get("duringSales") == null ? 0 : (Double) params.get("duringSales")));
                        /*realGetL = Double.parseDouble(tableModel.getValueAt(2, 1).toString())
                                - Double.parseDouble(tableModel.getValueAt(1, 1).toString()) + (params.get("realGetL") == null ? 0 : (Double) params.get("realGetL"));
                        params.put("realGetL", realGetL);
                        realGetV20 = Double.parseDouble(tableModel.getValueAt(2, 2).toString())
                                - Double.parseDouble(tableModel.getValueAt(1, 2).toString()) + (params.get("realGetV20") == null ? 0 : (Double) params.get("realGetV20"));
                        params.put("realGetV20", realGetV20);*/
                        //endregion
                        Double bcsy = Double.parseDouble(tableModel.getValueAt(2, 2).toString())
                                - Double.parseDouble(tableModel.getValueAt(1, 2).toString()) + duringSales;
                        DecimalFormat df2 = new DecimalFormat("######0.00");
                        bcxyssField.setText(Double.parseDouble(df2.format(bcsy)) + "");
                        acceptanceOdRegisterInfo.setDischargel("".equals(bcxyssField.getText().trim()) ? 0d : Double.parseDouble(bcxyssField.getText().trim()));
                        acceptanceOdRegisterInfo.setTranstatus(TRANSSTATUS_END);
                        // 更新卸油明细表
                        odRegisterInfoService.updateByPrimaryKeySelective(acceptanceOdRegisterInfo);
                        //region 更新完成卸油状态
                        UpdateButton3Status(acceptanceOdRegisterInfo.getManualNo());
                        //endregion
                        //region 通知湖南结束卸油
                        try {
                            transfor(acceptanceOdRegisterInfo.getDeliveryno(), acceptanceOdRegisterInfo.getOilcan().toString(), "3");
                        } catch (Exception es) {
                            System.out.println("notice hn");
                        }
                        //endregion
                    }
                }
            });
            //endregion

            maps.put(oilCanList.get(i).getOilcan().toString(),canmap);
        }
        //region 开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                //cc=Main.reLink();

                System.out.println(com);
                while (com) {
                    i++;
                    try {
                        if (Main.CC == null) {
                            System.out.println("cc is null");
                            LOG.info("cc is null");
                            //cc = Main.reLink();
                        } else {
                            //cc = Main.reLink();
                            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10002.toString());
                            Main.CC.writeAndFlush(gasMsg);
                            if (i == 5) {
                                i = 0;
                                gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
                                Main.CC.writeAndFlush(gasMsg);
                                //cc = TcpClient.getInstance().getChannel(Main.IP, Main.Port);
                            }
                            if (i == 1) {
                                gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
                                Main.CC.writeAndFlush(gasMsg);
                            }
                        }
                    }catch (Exception e){
                        LOG.info("获取罐枪实时状态线程异常："+e.getMessage());
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

    private void UpdateButton3Status(String billno) {
        List<AcceptanceOdRegisterInfo> infos=odRegisterInfoService.selectbydeliveryno(billno);
        UpdateOdreg(billno);
        try {
            if(Main.ckdcxPage!=null){
                Main.ckdcxPage.reLoad();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("refalsh failed!");
        }
        if (infos==null||infos.size()==0){
            button_3.setEnabled(false);
            return;
        }
        for (AcceptanceOdRegisterInfo item:infos){
            if (item.getBegintime()!=null&&item.getEndtime()!=null){

            }else {
                button_3.setEnabled(false);
                return;
            }
        }
        button_3.setEnabled(true);


    }

    private double getV20L(String oilType,double vt, double V) {
        V20Utils v20Utils=new V20Utils();
        if (V==0.0){return 0.0;}
        if (OIL_TYPE_1.equals("03")){
            //柴油
            return  v20Utils.getDieV20(vt, V);
        }else {
            //汽油
            return  v20Utils.getGasV20(vt,V);
        }
    }

    /**
     * @description 超损耗索赔 报警
     * */
    private void addOilinContrat(AcceptanceOdRegister odRegister){
        if(odRegister.getIndemnityloss()>0){
            if (alarmOilInContrastService==null){
                alarmOilInContrastService=Context.getInstance().getBean(AlarmOilInContrastService.class);
            }
            AlarmOilInContrast alarmOilInContrast=new AlarmOilInContrast();
            alarmOilInContrast.setOilno(odRegister.getOilno());
            alarmOilInContrast.setDuringsales(odRegister.getDuringsales());
            alarmOilInContrast.setDeliveryno(odRegister.getManualNo());
            alarmOilInContrast.setLoss(odRegister.getDischargeLossV20());
            alarmOilInContrast.setLossrate(odRegister.getDischargeRateV20()*100);
            alarmOilInContrast.setPlanl(odRegister.getPlanl());
            alarmOilInContrast.setRealrecieve(odRegister.getRealGetLV20());
            alarmOilInContrast.setTranstatus("0");
            alarmOilInContrast.setCreattime(new Date());
            System.out.print(alarmOilInContrast.toString());
            alarmOilInContrastService.insert(alarmOilInContrast);
        }
    }

    /**
     * 设置配送单信息
     * 
     * @param bill
     * @throws Exception
     */
    public void setCkdxx(AcceptanceDeliveryBills bill,boolean iswhd) throws Exception {
        this.iswhd=iswhd;
        mainPanel.removeAll();
        mainPanel.repaint();
        cbill=bill;
        tablePanel.removeAll();
        tablePanel.repaint();
        getTable(bill,tablePanel);

        try {
            if (sysManageCanInfoService==null){
                sysManageCanInfoService=Context.getInstance().getBean(SysManageCanInfoService.class);
            }
            List<SysManageCanInfo> canInfos=sysManageCanInfoService.selectByOilNo(cbill.getOilno());
            if (monitorTimeInventoryService==null){
                monitorTimeInventoryService=Context.getInstance().getBean(MonitorTimeInventoryService.class);
            }
            if(odRegisterService==null){
                odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
            }
            //设置油品类型
            try {
                OIL_TYPE_1 = odRegisterService.selectOilType(bill.getOilno()).getOiltype().toString();
            }catch (Exception e){
                LOG.error("获取油品类型："+e.getMessage());
            }
            AcceptanceOdRegister acceptanceOdRegister = new AcceptanceOdRegister();

            acceptanceOdRegister.setOilno(bill.getOilno());
            acceptanceOdRegister.setManualNo(bill.getDeliveryno());
            acceptanceOdRegister.setPlanl(bill.getPlanl());

            if (bill!=null&&!iswhd){
                //有出库单进货验收 更新Deliveryno
                acceptanceOdRegister.setDeliveryno(bill.getDeliveryno());
            }
            // 保存卸油主数据
             if (odRegisterService.selectByPrimaryKey(bill.getDeliveryno())==null)
             {
               /*  acceptanceOdRegister.setCreatetime(new Date());
                 acceptanceOdRegister.setBegintime(new Date());*/
                 //注释上面的代码，改为卸油事件触发更新时间
                 acceptanceOdRegister.setInstationtime(new Date());
                odRegisterService.insert(acceptanceOdRegister);
             }
            gunInfos=monitorTimeInventoryService.selectAllOilGun();
            initOilCan(canInfos, mainPanel);
            UpdateButton3Status(bill.getDeliveryno());
        } catch (Exception e1) {
            LOG.error(e1);
            e1.printStackTrace();
        }
    }

    /**
     * 初始化油罐信息
     * 
     * @throws Exception
     */
    public void initOilTankInfo() throws Exception {
        TableModel tableModel = table.getModel();
        String oilNo = tableModel.getValueAt(0, 1).toString().trim();
        // 获取油罐信息,通过主调度获取油罐信息
        List<MonitorTimeInventory> oilCanList = new ArrayList<MonitorTimeInventory>();

//        if (oilCanList.isEmpty()) {
//            // Toolkit.getDefaultToolkit().beep();
//            // JOptionPane.showMessageDialog(frame, "没有对应油品的油罐", "信息提示",
//            // JOptionPane.INFORMATION_MESSAGE);
//            throw new Exception("没有对应油品的油罐");
//        }
    }

    public boolean initOilGunInfo(Integer oilCan) throws Exception {
        return true;
    }

    public void  UpdateCanpic(Double wpercent,Double opercent, JPanel wpanel,JPanel oil){
        //water
        Double  wh=115 * wpercent;
        Integer wheight=wh.intValue();
        Integer wy=115-wheight+10;
        wpanel.setBounds(11,wy,72,wheight-1);

        //oil
        Double  oh=115 * opercent;
        Integer oheight=oh.intValue();
        Integer oy=115-oheight+10-wheight;
        oil.setBounds(11,oy,72,oheight-1);

    }

    @Override
    public void update(GasMsg gasMsg) {

        if (monitorTimeInventoryService==null){
            monitorTimeInventoryService=Context.getInstance().getBean(MonitorTimeInventoryService.class);
        }
        if (odRegisterService==null){
            odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        }
        List<SysManageOilType> types=odRegisterService.selectUseOilType();
        List<SysManageOilGunInfo> oilGunList = monitorTimeInventoryService.selectAllOilGun();

        //region gun status
        if (gasMsg.getPid().equals("A15_10002")){
            System.out.println("油枪状态Ctrl");
            //油枪状态
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List<MacLogInfo> macLogInfos=resultMsg.getData();
            for (int i =0;i<macLogInfos.size();i++) {
                // region获取罐号
                String canno="";
                for (SysManageOilGunInfo item :oilGunList){
                    Map<String,?> map=(Map) (resultMsg.getData().get(i));
                    if (item.getOilgun()==Integer.parseInt(map.get("GunNum").toString())){
                        canno=item.getOilcan().toString();
                        break;
                    }
                }
                //endregion
                if ("".equals(canno)){
                    continue;
                }
                //region update gun status
                Map<String,Object> gunmap=maps.get(canno);

                if (gunmap!=null&&!gunmap.isEmpty()){
                    Map<String,?> map=(Map) (resultMsg.getData().get(i));
                    JButton gp=(JButton)gunmap.get(map.get("GunNum"));
                    if (map.get("GunStatus").toString().equals("提枪")){
                        //gp.setBackground(Color.yellow);
                        gp.setIcon(Common.createImageIcon(this.getClass(), "gas-gun.png"));
                    }else{
                       /* gp.setBackground(Color.green);*/
                        gp.setIcon(Common.createImageIcon(this.getClass(), "green_8.png"));
                    }
                }
                //endregion

            }

        }
        //endregion

        //region tankstatus
        if (gasMsg.getPid().equals("A15_10004")){
            System.out.println("回调实时库存Ctrl");
            //实时库存
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List<Map<String,?>> candata= resultMsg.getData();
                for (int m=0;m<candata.size();m++){
                    // region获取罐号
                    String canno="";
                    for (SysManageCanInfo item :canInfos){
                        Map<String,?> map=(Map) (resultMsg.getData().get(m));
                        if (item.getOilcan()==Integer.parseInt(map.get("uOilCanNo").toString())){
                            canno=item.getOilcan().toString();
                            break;
                        }
                    }
                    //endregion
                    if ("".equals(canno)){
                        continue;
                    }
                    Map<String,Object>map=maps.get(candata.get(m).get("uOilCanNo").toString());

                    JLabel stj=(JLabel)map.get("stj");
                    JLabel pjwd=(JLabel)map.get("pjwd");
                    JLabel jytj=(JLabel)map.get("jytj");
                    JLabel ktj=(JLabel)map.get("ktj");
                    JLabel yszg=(JLabel)map.get("yszg");
                    JLabel bztj=(JLabel)map.get("bztj");
                    JPanel wpanel=(JPanel)map.get("wpanel");
                    JPanel oilpanel=(JPanel)map.get("oilpanel");

                    DecimalFormat    df   = new DecimalFormat("######0.00");
                   /* DecimalFormat decimalFormat = new DecimalFormat("######0.00");
                    System.out.println(" DecimalFormat --[oill]"+ Double.parseDouble(decimalFormat.format(oill)));
                    return oill == null ? 0.00 : Double.parseDouble(decimalFormat.format(oill));*/
                    Double dstj=Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fWaterBulk").toString())));
                    stj.setText(String.valueOf(dstj));
                    Double dpjwd=Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fOilTemp").toString())));
                    pjwd.setText(String.valueOf(dpjwd));
                    Double djytj=Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fOilCubage").toString())));
                    jytj.setText(String.valueOf(djytj));
                    Double dktj=Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fEmptyCubage").toString())));
                    ktj.setText(String.valueOf(dktj));
                    Double dyszg=Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fTotalHeight").toString())));
                    yszg.setText(String.valueOf(dyszg));
                    Double dbztj=Double.parseDouble(df.format(Double.parseDouble(candata.get(m).get("fOilStandCubage").toString())));
                    bztj.setText(String.valueOf(dbztj));


                    double gr=grmaps.get(candata.get(m).get("uOilCanNo").toString());
                    double wp=Double.parseDouble(candata.get(m).get("fWaterBulk").toString());
                    double ol=Double.parseDouble(candata.get(m).get("fOilCubage").toString());
                    double kk=Double.parseDouble(candata.get(m).get("fEmptyCubage").toString());
                    double sum=wp+ol+kk;
                    if (sum>0){
                        UpdateCanpic(wp/sum,ol/sum,wpanel,oilpanel);
                    }
                }
            //取单个罐的实时库存，至前尺或后尺

        }
        //endregion

        //region notice Ctrl jhys
        if(gasMsg.getPid().equals("A15_10005")){
            System.out.println("进货验收ctrl回调");
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            if ("1".equals(resultMsg.getResult())) {timeout=-1;return;}
            timeoutflag=false;
            if (iswhd) {
                AcceptanceNoBills noBills = new AcceptanceNoBills();
                noBills.setDeliveryno(Completebillno);
                AcceptanceNoBills tnobill = deliveryService.getNobillBykey(noBills.getDeliveryno());
                if (tnobill != null) {
                    noBills = tnobill;
                }
                noBills.setIscomplete("1");
                deliveryService.insertNobills(noBills);
            } else {
                AcceptanceDeliveryBills bill = new AcceptanceDeliveryBills();
                bill.setDeliveryno(Completebillno);
                AcceptanceDeliveryBills tbill = deliveryService.selectByPrimaryKey(bill.getDeliveryno());
                if (tbill != null) {
                    bill = tbill;
                }
                bill.setIscomplete("1");
                deliveryService.updateByPrimaryKey(bill);
            }

            if (Main.ckdcxPage != null) {
                try {
                    Main.ckdcxPage.reLoad();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    System.out.println("出库单刷新异常！");
                }
            }

            //进油损益报警
            try {
                addOilinContrat(odRegister);
            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
            completed = true;
            frame.dispose();
        }
        //endregion
    }

    public void getTable(AcceptanceDeliveryBills bill, JPanel tablePanel){
        if (table==null)table=new JTable();
    /*    MyTableModel tableModel = (MyTableModel) table.getModel();
        tableModel.setValueAt(bill.getDeliveryno(), 0, 0);
        tableModel.setValueAt(bill.getOilno(), 0, 1);
        tableModel.setValueAt(bill.getPlanl(), 0, 2);
        tableModel.setValueAt(bill.getDeliverytemp(), 0, 3);
        tableModel.setValueAt(bill.getPlanton(), 0, 4);*/
        Object[][] list=new Object[1][6];
        list[0][0]=bill.getDeliveryno();
        list[0][1]=bill.getOilno();
        //region 取油品名称
        if (alarmDailyLostService==null){
            alarmDailyLostService=Context.getInstance().getBean(AlarmDailyLostService.class);
        }
        list[0][2]=alarmDailyLostService.selectOilNo(bill.getOilno());
        // endregion
        list[0][3]=bill.getPlanl();
        list[0][4]=bill.getDeliverytemp();
        list[0][5]=bill.getPlanton();

        //罐车交接原发升数
        yfssField.setText(bill.getPlanl() == null ? "" : bill.getPlanl().toString());

        System.out.println(list);

        DefaultTableModel model = new DefaultTableModel(list,new String[] { "出库单号", "油品编码","油品名称", "原发升数(L)", "发货温度(℃)", "原发数量(t)" });
        table = new MyTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, render);
        table.setCellSelectionEnabled(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        tablePanel.add(table, BorderLayout.CENTER);
        for (int i = 0; i < 6; i++) {
            if (i==2){
            table.getColumnModel().getColumn(i).setPreferredWidth(150);
            }
        }
        tablePanel.updateUI();
    }

    public void transfor(String billno,String canno,String status){
        if (sysmanageService==null){
            sysmanageService=Context.getInstance().getBean(SysmanageService.class);
        }
        SysManageDepartment department=sysmanageService.getdeptinfo();

        if (acceptSevices==null){
            acceptSevices=Context.getInstance().getBean(AcceptSevices.class);
        }
        try{
        OdregStatus odregStatus=new OdregStatus();
        odregStatus.setNodeno(department.getSinopecnodeno());
        odregStatus.setOilcan(canno);
        odregStatus.setOpttime(new Date());
        odregStatus.setStatus(status);
        odregStatus.setDeliveryno(billno);
        acceptSevices.transodRegStatus(SysConfig.regmoteIp().toString(),odregStatus);}
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void UpdateOdreg(String manualno){
        AcceptanceOdRegister od=odRegisterService.selectByPrimaryKey(manualno);
        //regionselect begin time
        AcceptanceOdRegisterInfo info=odRegisterInfoService.selectbegintime(manualno);
        if (info==null){
            od.setBegintime(null);
        }else{
            od.setBegintime(info.getBegintime());

        }

        //endregion

        //region select endtime
        AcceptanceOdRegisterInfo info1=odRegisterInfoService.selectendtime(manualno);
        if (info1==null){
            od.setEndtime(null);
        }else{
           // od.setEndtime(info.getEndtime());

        }
        odRegisterService.updateByPrimaryKey(od);
        //endregion
    }

    //region windowsevents
    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     *
     * @param e
     */
    @Override
    public void windowClosed(WindowEvent e) {
        try {
            System.out.println("windows Closed");
            ckdcxPage.reLoad();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Invoked the first time a window is made visible.
     *
     * @param e
     */
    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Invoked when the user attempts to close the window
     * from the window's system menu.
     *
     * @param e
     */
    @Override
    public void windowClosing(WindowEvent e) {
        try {
            System.out.println("windows closing");
            ckdcxPage.reLoad();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Invoked when a window is changed from a normal to a
     * minimized state. For many platforms, a minimized window
     * is displayed as the icon specified in the window's
     * iconImage property.
     *
     * @param e
     * @see Frame#setIconImage
     */
    @Override
    public void windowIconified(WindowEvent e) {

    }

    /**
     * Invoked when a window is changed from a minimized
     * to a normal state.
     *
     * @param e
     */
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    /**
     * Invoked when the Window is set to be the active Window. Only a Frame or
     * a Dialog can be the active Window. The native windowing system may
     * denote the active Window or its children with special decorations, such
     * as a highlighted title bar. The active Window is always either the
     * focused Window, or the first Frame or Dialog that is an owner of the
     * focused Window.
     *
     * @param e
     */
    @Override
    public void windowActivated(WindowEvent e) {

    }

    /**
     * Invoked when a Window is no longer the active Window. Only a Frame or a
     * Dialog can be the active Window. The native windowing system may denote
     * the active Window or its children with special decorations, such as a
     * highlighted title bar. The active Window is always either the focused
     * Window, or the first Frame or Dialog that is an owner of the focused
     * Window.
     *
     * @param e
     */
    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    //endregion
}
