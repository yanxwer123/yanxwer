package com.kld.app.view.acceptance;

import com.kld.app.service.*;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Common;
import com.kld.app.util.SysConfig;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.util.DateUtil;
import com.kld.gsm.util.V20Utils;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by niyang on 2016/2/15.
 */
public class JhysTank  extends JPanel{
    private MonitorTimeInventoryService monitorTimeInventoryService;
    private IAcceptanceOdRegisterService odRegisterService;
    private List<SysManageOilGunInfo> gunInfos;
    private Map<Integer,JButton> gunmap;
    private List<JComponent> btns=new ArrayList<JComponent>();
    private JPanel panelw;
    private JPanel panel_3;
    private JPanel panel_4;
    private String canno;
    private JLabel ygbhLabel;
    private JLabel jytjLabel;
    private JLabel ktjLabel;
    private JLabel pjwdLabel;
    private JLabel stjLabel;
    private JLabel yszgLabel;
    private JLabel sgLabel;
    private JLabel bztjLabel;
    private JLabel yxyssLabel;
    private JButton btnNewButton;
    private JButton button;
    private JButton cancelbtn;
    private JButton wyBtn;
    private JLabel minute;
    private JLabel second;
    private Double qcvt=null;
    private AcceptanceOdRegisterInfo acceptanceOdRegisterInfo;
    private IAcceptanceOdRegisterInfoService odRegisterInfoService;
    private DailyTradeAccountService dailyTradeAccountService;
    private SysmanageService sysmanageService;
    private IAcceptanceDeliveryService deliveryService;
    private AcceptSevices acceptSevices;
    private AcceptanceDeliveryBills cbill;
    private JTable qcsjTable;
    private JTextField bcxyssField;
    private JTextField fyssField;
    private JButton button_3;
    private String OIL_TYPE_1;
    private double duringSales = 0;
    private JCheckBox cb1;
    private JCheckBox cb2;
    private JLabel finishLabel;
    private JLabel label_21;
    private JCheckBox checkBox;
    private String OIL_TYPE;
    private JRadioButton dgrbutton;
    private JRadioButton gcrbutton;
    private int tguncount;

    private static final Logger LOG = Logger.getLogger(JhysTank.class);
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JhysTank(String canno,AcceptanceDeliveryBills bill,String OIL_TYPE_1,HashMap<String,JComponent> Components){
        this.canno=canno;
        this.cbill=bill;
        JButton btncomplete=(JButton)Components.get("btncomplete");
        if (btncomplete!=null){
            this.button_3=btncomplete;
        }

        JCheckBox checkBox=(JCheckBox)Components.get("checkbox");
        if(checkBox!=null){
            this.checkBox=checkBox;
        }

        JRadioButton dgbtn=(JRadioButton)Components.get("dg");
        if (dgbtn!=null){
            this.dgrbutton=dgbtn;
        }

        JRadioButton gcbtn=(JRadioButton)Components.get("gc");
        if (gcbtn!=null){
            this.gcrbutton=gcbtn;
        }


        this.OIL_TYPE=OIL_TYPE_1;
        this.dailyTradeAccountService = Context.getInstance().getBean(DailyTradeAccountService.class);
        this.odRegisterService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        this.odRegisterInfoService =  Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
        this.deliveryService = Context.getInstance().getBean(IAcceptanceDeliveryService.class);
        init();
    }

    public void init(){
        this.setPreferredSize(new Dimension(657, 252));
        this.setBackground(Color.cyan);
        this.setSize(657, 252);
        this.setLayout(null);
        this.setBorder(new MatteBorder(1, 0, 0, 0, Color.lightGray));
        this.setBackground(UIManager.getColor("Button.light"));
        getoiltype();
        Addtank();
        Addgun();
        Addqchc();
        Addbtns();
        selectinfo();
    }

    public void selectinfo(){
        //region获取当前进货验收明细 用于恢复页面
        AcceptanceOdRegisterInfoKey key=new AcceptanceOdRegisterInfoKey();
        key.setManualNo(cbill.getDeliveryno());
        key.setOilcan(Integer.parseInt(canno));

        if (odRegisterInfoService==null){
            odRegisterInfoService=Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
        }
        acceptanceOdRegisterInfo=odRegisterInfoService.selectByPrimaryKey(key);
        if (acceptanceOdRegisterInfo==null){
            acceptanceOdRegisterInfo=new AcceptanceOdRegisterInfo();
        }else {
            //region 状态恢复
            qcsjTable.setValueAt(acceptanceOdRegisterInfo.getBeginoilheight(), 1, 0);
            qcsjTable.setValueAt(acceptanceOdRegisterInfo.getBeginoill(), 1, 1);
            if (acceptanceOdRegisterInfo.getBeginoill()!=null&&acceptanceOdRegisterInfo.getEndtime()==null){
                qcvt=acceptanceOdRegisterInfo.getBeginoill();
            }
            qcsjTable.setValueAt(acceptanceOdRegisterInfo.getBeginv20l(), 1, 2);
            qcsjTable.setValueAt(acceptanceOdRegisterInfo.getBegintemperature(), 1, 3);
            String begintime = DateUtil.getDate(acceptanceOdRegisterInfo.getBegintime() == null
                    ? null : acceptanceOdRegisterInfo.getBegintime(), "yyyy-MM-dd HH:mm:ss");
            qcsjTable.setValueAt(begintime, 1, 4);

            qcsjTable.setValueAt(acceptanceOdRegisterInfo.getEndoilheight(), 2, 0);
            qcsjTable.setValueAt(acceptanceOdRegisterInfo.getEndoill(), 2, 1);
            qcsjTable.setValueAt(acceptanceOdRegisterInfo.getEndv20l(), 2, 2);
            qcsjTable.setValueAt(acceptanceOdRegisterInfo.getEndtemperature(), 2, 3);
            String endtime = DateUtil.getDate(acceptanceOdRegisterInfo.getEndtime() == null
                    ? null : acceptanceOdRegisterInfo.getEndtime(), "yyyy-MM-dd HH:mm:ss");
            qcsjTable.setValueAt(endtime, 2, 4);

            //卸油升数
            if (acceptanceOdRegisterInfo.getDischargel()!=null) {
                bcxyssField.setText(acceptanceOdRegisterInfo.getDischargel().toString());
            }
            //期间付油
            if(acceptanceOdRegisterInfo.getDuringsales()!=null){
                fyssField.setText(acceptanceOdRegisterInfo.getDuringsales().toString());
            }
            //endregion
            cb1.setSelected(true);
            cb2.setSelected(true);
            cb1.setEnabled(false);
            cb2.setEnabled(false);
            UpdateButton3Status(acceptanceOdRegisterInfo.getManualNo());
            for (JComponent item : btns) {
                item.setVisible(false);
            }

            //设置按钮状态
            if (acceptanceOdRegisterInfo.getBegintime() != null && acceptanceOdRegisterInfo.getEndtime() != null) {
                //卸油结束
                finishLabel.setVisible(true);
                cancelbtn.setVisible(true);
            }
            if (acceptanceOdRegisterInfo.getBegintime() != null && acceptanceOdRegisterInfo.getEndtime() == null
                    && acceptanceOdRegisterInfo.getStablebegintime() == null) {
                //开始卸油后，但没有稳油
                wyBtn.setVisible(true);
                button.setText("结束卸油");
                button.setBounds(288, 220, 93, 23);
                button.setVisible(false);
                cancelbtn.setVisible(true);
                btnNewButton.setVisible(true);
            }
            if (acceptanceOdRegisterInfo.getBegintime() != null && acceptanceOdRegisterInfo.getEndtime() == null
                    && acceptanceOdRegisterInfo.getStablebegintime() != null) {
                //开始稳油，关闭前没有稳油
                //计算稳油倒计时时间
                Date now = new Date();
                Date date = acceptanceOdRegisterInfo.getStablebegintime();
                long s = now.getTime() - date.getTime();
                //todo  字典表读取数据

                if (s > Main.wysj) {
                    //稳油结束，结束卸油
                   /* button.setText("结束卸油");
                    button.doClick();
                    finishLabel.setVisible(true);
                    cancelbtn.setVisible(true);*/
                    minute.setText("0");
                    second.setText("20");
                } else {
                    long ls = Main.wysj - s;
                    long m = ls / 60000;
                    long ss = ls % 60000;
                    minute.setText(m + "");
                    second.setText(ss / 1000 + "");
                }
                    for (JComponent item : btns) {
                        item.setVisible(false);
                    }
                    minute.setVisible(true);
                    second.setVisible(true);
                    label_21.setVisible(true);
                    wyBtn.setText("开始稳油");
                    wyBtn.doClick();

            }
        }
        //endregion
    }

    private void getoiltype(){
        try {
            if(odRegisterService==null){
                odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
            }
            OIL_TYPE_1 = odRegisterService.selectOilType(cbill.getOilno()).getOiltype().toString();
        }catch (Exception e){
            LOG.error("获取油品类型："+e.getMessage());
        }
    }

    public void Addtank(){
        //region 罐图形绘制
        //water
        panelw=new JPanel();
        panelw.setBackground(Color.decode("#09DFF7"));
        this.add(panelw);

        //oil
        panel_3 = new JPanel();
        panel_3.setBackground(Color.decode("#EC6C00"));
        panel_3.setBounds(11, 46, 54, 0);
        this.add(panel_3);

        panel_4 = new JPanel();
        panel_4.setBackground(Color.decode("#ffffff"));
        panel_4.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        panel_4.setBounds(10, 15, 56, 110);
        this.add(panel_4);
        //endregion

        //region 油罐要素文字说明
        JLabel lblNewLabel_12 = new JLabel("标准体积(L):");
        lblNewLabel_12.setBounds(70, 10, 100, 15);
        this.add(lblNewLabel_12);

        JLabel lblNewLabel_14 = new JLabel("净油体积(L):");
        lblNewLabel_14.setBounds(70, 30, 100, 15);
        this.add(lblNewLabel_14);

        JLabel lblNewLabel_15 = new JLabel("空体积(L):");
        lblNewLabel_15.setBounds(70, 50, 85, 15);
        this.add(lblNewLabel_15);

        JLabel label = new JLabel("平均温度(℃):");
        label.setBounds(70, 70, 108, 15);
        this.add(label);

        JLabel lbll = new JLabel("水高(mm):");
        lbll.setBounds(70, 90, 85, 15);
        this.add(lbll);
        JLabel yxss = new JLabel("已卸升数(L):");
        yxss.setForeground(Color.red);
        yxss.setBounds(70, 110, 85, 15);
        this.add(yxss);
        //endregion

        //region 油罐要素
        ygbhLabel = new JLabel(canno);
        ygbhLabel.setHorizontalAlignment(SwingConstants.LEFT);
        ygbhLabel.setBounds(35, 2, 100, 15);
        this.add(ygbhLabel);

        //标准体积
        bztjLabel=new JLabel("");
        bztjLabel.setVisible(true);
        bztjLabel.setBounds(160, 10, 100, 15);
        this.add(bztjLabel);

        //净油体积
        jytjLabel = new JLabel("");
        jytjLabel.setHorizontalAlignment(SwingConstants.LEFT);
        jytjLabel.setBounds(160, 30, 100, 15);
        this.add(jytjLabel);

        //空体积
        ktjLabel = new JLabel("");
        ktjLabel.setHorizontalAlignment(SwingConstants.LEFT);
        ktjLabel.setBounds(160, 50, 100, 15);
        this.add(ktjLabel);

        //平均温度
        pjwdLabel = new JLabel("");
        pjwdLabel.setHorizontalAlignment(SwingConstants.LEFT);
        pjwdLabel.setBounds(160, 70, 100, 15);
        this.add(pjwdLabel);

        //水高
        stjLabel = new JLabel("");
        stjLabel.setHorizontalAlignment(SwingConstants.LEFT);
        stjLabel.setVisible(false);
        stjLabel.setBounds(160, 90, 100, 15);
        this.add(stjLabel);

        //油水总高
        yszgLabel=new JLabel("");
        yszgLabel.setVisible(false);
        yszgLabel.setBounds(160, 90, 100, 15);
        this.add(yszgLabel);

        //水高
        sgLabel=new JLabel("");
        sgLabel.setVisible(true);
        sgLabel.setBounds(160, 90, 100, 15);
        this.add(sgLabel);

        //已卸油升数
        yxyssLabel=new JLabel("");
        yxyssLabel.setVisible(false);
        yxyssLabel.setBounds(160, 110, 100, 15);
        this.add(yxyssLabel);
        //endregion
    }

    public void UpdateTank(Map<String,?> map){
        LOG.info("Updatetank begin:"+new Date());
        try {
            DecimalFormat df = new DecimalFormat("######0.00");
            Double dstj = Double.parseDouble(df.format(Double.parseDouble(new DecimalFormat("0").format(map.get("fWaterBulk")).toString())));
            stjLabel.setText(String.valueOf(dstj));
            Double dpjwd = Double.parseDouble(df.format(Double.parseDouble(map.get("fOilTemp").toString())));
            pjwdLabel.setText(String.valueOf(dpjwd));
            Double djytj = Double.parseDouble(df.format(Double.parseDouble(new DecimalFormat("0").format(map.get("fOilCubage")).toString())));
            jytjLabel.setText(String.valueOf(djytj));
            Double dktj = Double.parseDouble(df.format(Double.parseDouble(new DecimalFormat("0").format(map.get("fEmptyCubage")).toString())));
            ktjLabel.setText(String.valueOf(dktj));
            Double dyszg = Double.parseDouble(df.format(Double.parseDouble(map.get("fTotalHeight").toString())));
            yszgLabel.setText(String.valueOf(dyszg));
            Double dbztj = Double.parseDouble(df.format(Double.parseDouble(map.get("fOilStandCubage").toString())));
            bztjLabel.setText(String.valueOf(dbztj));
            Double dsg = Double.parseDouble(df.format(Double.parseDouble(map.get("fWaterHeight").toString())));
            sgLabel.setText(String.valueOf(dsg));


            if (qcvt != null) {
                Double dyxss = Double.parseDouble(df.format(djytj - qcvt));
                yxyssLabel.setText(dyxss + "");
                yxyssLabel.setVisible(true);
            }


            double wp = Double.parseDouble(map.get("fWaterBulk").toString());
            double ol = Double.parseDouble(map.get("fOilCubage").toString());
            double kk = Double.parseDouble(map.get("fEmptyCubage").toString());
            double sum = wp + ol + kk;
            if (sum > 0) {
                UpdateCanpic(wp / sum, ol / sum, panelw, panel_3);
            }
        }catch (Exception e) {
            LOG.error(e.getMessage());
        }
        LOG.info("Updatetank end:" + new Date());
    }

    public void UpdateGun(List<HashMap<String,Object>> macLogInfos){
        LOG.info("UpdateGun begin:"+new Date());
        if (gunmap != null && !gunmap.isEmpty()) {
         int guncount=0;
        for (int i=0;i<macLogInfos.size();i++) {
                int gunno=Integer.parseInt(macLogInfos.get(i).get("GunNum").toString());
                JButton gun=gunmap.get(gunno);
                if (gun!=null){
                    if (macLogInfos.get(i).get("GunStatus").toString().equals("提枪")){
                        gun.setIcon(Common.createImageIcon(this.getClass(), "gas-gun.png"));
                        guncount+=1;
                    } else {
                        gun.setIcon(Common.createImageIcon(this.getClass(), "green_8.png"));
                    }
                }
            }
            tguncount=guncount;
        }
        LOG.info("UpdateGun end:" + new Date());
    }

    public void UpdateCanpic(Double wpercent,Double opercent, JPanel wpanel,JPanel oil){
        //water
        Double  wh=110 * wpercent;
        Integer wheight=wh.intValue();
        Integer wy=110-wheight+15;
        wpanel.setBounds(11,wy,54,wheight-1);

        //oil
        Double  oh=110 * opercent;
        Integer oheight=oh.intValue();
        Integer oy=110-oheight+15-wheight;
        oil.setBounds(11, oy, 54, oheight - 1);

    }

    public void  Addgun(){
        if (monitorTimeInventoryService==null){
            monitorTimeInventoryService= Context.getInstance().getBean(MonitorTimeInventoryService.class);
        }
        gunInfos=monitorTimeInventoryService.selectAllOilGun();

        List<SysManageOilGunInfo> guns=new ArrayList<SysManageOilGunInfo>();

        //region渲染油枪
        for (SysManageOilGunInfo gunInfo:gunInfos){
            if (gunInfo.getOilcan().toString().equals(canno)){
                guns.add(gunInfo);
            }
        }
        gunmap=new HashMap<Integer, JButton>();
        for(int j=0;j<guns.size();j++){
            JLabel jgunno = new JLabel(guns.get(j).getOilgun().toString()+"#");
            jgunno.setBounds(285 + j % 4 * 54, 10 + j / 4 * 32, 20, 20);
            jgunno.setBackground(Color.GREEN);
            jgunno.setHorizontalTextPosition(SwingConstants.CENTER);
            this.add(jgunno);

            JButton panel_7 = new JButton();
            panel_7.setBorderPainted(false);
            panel_7.setContentAreaFilled(false);
            panel_7.setIcon(Common.createImageIcon(this.getClass(), "green_8.png"));
            panel_7.setBounds(303 + j % 4 * 54, 5 + j / 4 * 32, 30, 35);
            this.add(panel_7);
            gunmap.put(guns.get(j).getOilgun(),panel_7);
        }
    }

    public void  Addqchc(){
        // 前尺数据
        JPanel qcsjTablePanel = new JPanel();
        qcsjTablePanel.setBounds(5, 140, 500, 76);
        //qcsjTablePanel.setBackground(Color.cyan);
        this.add(qcsjTablePanel);
        qcsjTablePanel.setLayout(null);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableModel qcsjModel = new DefaultTableModel(new Object[][] {
                { "油水总高(mm)", "净油体积(L)", "标准体积(L)", "平均温度(℃)","时间" }, { "", "", "", "","" }, { "", "", "", "","" } },new String[] { "油水总高(mm)", "净油体积(L)", "标准体积(L)", "平均温度(℃)","时间" });
        qcsjTable = new JTable(qcsjModel);
        qcsjTable.setBorder(new LineBorder(new Color(0, 0, 0)));
        qcsjTable.setBounds(37, 0, 461, 75);
        qcsjTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Enumeration<TableColumn> columns = qcsjTable.getColumnModel().getColumns();
        qcsjTable.setDefaultRenderer(Object.class, render);
        qcsjTable.setRowHeight(25);
        int i=0;
        while (columns.hasMoreElements()) {
            TableColumn column = columns.nextElement();
            if (i==0||i==3) {
                column.setPreferredWidth(80);
                column.setWidth(80);
                column.setMaxWidth(80);
                column.setMinWidth(80);
            }
            if (i==1||i==2){
                column.setPreferredWidth(69);
                column.setWidth(69);
                column.setMaxWidth(69);
                column.setMinWidth(69);
            }
            if(i==4){
                column.setPreferredWidth(162);
                column.setWidth(162);
                column.setMaxWidth(162);
                column.setMinWidth(162);
            }
            column.setCellRenderer(render);
            i++;
        }


        qcsjTable.setEnabled(false);
        qcsjTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        qcsjTable.setRowSelectionAllowed(true);
        qcsjTable.setColumnSelectionAllowed(true);
        qcsjTablePanel.add(qcsjTable);

        JPanel panel_19 = new JPanel();
        panel_19.setBorder(new MatteBorder(1, 1, 1, 0, Color.black));
        panel_19.setBackground(Color.WHITE);
        panel_19.setBounds(0, 0, 50, 25);
        qcsjTablePanel.add(panel_19);
        panel_19.setLayout(null);

        JPanel panel_21 = new JPanel();
        panel_21.setBorder(new MatteBorder(0, 1, 0, 0, Color.black));
        panel_21.setBackground(Color.WHITE);
        panel_21.setBounds(0, 25, 50, 25);
        qcsjTablePanel.add(panel_21);
        panel_21.setLayout(null);

        JLabel label_15 = new JLabel("前尺");
        label_15.setBounds(8, 2, 54, 15);
        panel_21.add(label_15);

        JPanel panel_20 = new JPanel();
        panel_20.setBorder(new MatteBorder(1, 1, 1, 0, Color.black));
        panel_20.setBackground(Color.WHITE);
        panel_20.setBounds(0, 50, 50, 25);
        qcsjTablePanel.add(panel_20);
        panel_20.setLayout(null);

        JLabel label_16 = new JLabel("后尺");
        label_16.setBounds(8, 2, 54, 15);
        panel_20.add(label_16);


        JLabel label_3 = new JLabel("卸油升数：");
        label_3.setBounds(515, 142, 60, 23);
        this.add(label_3);

        JLabel label_4 = new JLabel("付油升数：");
        label_4.setBounds(515, 165, 60, 23);
        label_4.setForeground(Color.red);
        this.add(label_4);

        bcxyssField = new JTextField();
        bcxyssField.setEnabled(false);
        bcxyssField.setColumns(10);
        bcxyssField.setBounds(575, 142, 75, 21);
        this.add(bcxyssField);

        fyssField = new JTextField();
        fyssField.setEditable(false);
        fyssField.setEnabled(false);
        fyssField.setColumns(10);
        fyssField.setForeground(Color.red);
        fyssField.setBounds(575, 165, 75, 21);
        this.add(fyssField);
    }

    public void  Addbtns(){
        //region btns
        btnNewButton = new JButton("手工录入");
        btnNewButton.setBounds(10, 220, 93, 23);
        btns.add(btnNewButton);
        this.add(btnNewButton);

        button = new JButton("开始卸油");
        button.setBounds(110, 220, 93, 23);
        btns.add(button);
        this.add(button);

        cancelbtn = new JButton("作废卸油");
        cancelbtn.setVisible(false);
        cancelbtn.setBounds(487, 220, 93, 23);
        btns.add(cancelbtn);
        this.add(cancelbtn);

        wyBtn = new JButton("开始稳油");
        wyBtn.setVisible(false);
        wyBtn.setBounds(388, 220, 93, 23);
        btns.add(wyBtn);
        this.add(wyBtn);
        /*
         * 稳油时间分钟
         * */
        long m = Main.wysj / 60000;
        long ss = Main.wysj % 60000;
        minute = new JLabel(m+"");

        minute.setHorizontalAlignment(SwingConstants.CENTER);
        minute.setFont(new Font("\u9ED1\u4F53", minute.getFont().getStyle() | Font.BOLD, 18));
        minute.setBounds(213, 220, 35, 33);
        minute.setVisible(false);
        btns.add(minute);
        this.add(minute);

        label_21 = new JLabel("：");
        label_21.setHorizontalAlignment(SwingConstants.CENTER);
        label_21.setFont(new Font("\u9ED1\u4F53", label_21.getFont().getStyle(), 18));
        label_21.setBounds(244, 222, 20, 20);
        label_21.setVisible(false);
        btns.add(label_21);
        this.add(label_21);

        /*
        * 稳油时间秒
        * */
        second = new JLabel(ss / 1000+"");
        second.setHorizontalAlignment(SwingConstants.CENTER);
        second.setFont(new Font("\u9ED1\u4F53", second.getFont().getStyle() | Font.BOLD, 18));
        second.setBounds(250, 220, 35, 33);
        second.setVisible(false);
        btns.add(second);
        this.add(second);

        finishLabel=new JLabel("稳油完成!");
        finishLabel.setVisible(false);
        finishLabel.setBounds(210, 220, 93, 23);
        finishLabel.setBackground(Color.black);
        btns.add(finishLabel);
        finishLabel.setVisible(false);
        this.add(finishLabel);

        cb1 = new JCheckBox("油枪已停止付油", false);
        cb1.setBounds(234, 110, 120, 18);
        cb1.setBackground(Color.decode("#ffffff"));
        this.add(cb1);

        cb2 = new JCheckBox("卸前已稳油", false);
        cb2.setBackground(Color.decode("#ffffff"));
        cb2.setBounds(360, 110, 120, 18);
        this.add(cb2);
        //region 定时器
        final Timer timer = new Timer(1000, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int m = Integer.parseInt(minute.getText());
                int s = Integer.parseInt(second.getText());
                if (m == 0 && s == 0) {
                        /*button.setEnabled(true);*/
                    button.setEnabled(true);
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
        //endregion
        //region btn event

        //region 稳油事件
        wyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (acceptanceOdRegisterInfo.getStablebegintime() == null) {
                    acceptanceOdRegisterInfo.setStablebegintime(new Date());
                }
                for (JComponent item : btns) {
                    item.setVisible(false);
                }
                minute.setVisible(true);
                second.setVisible(true);
                label_21.setVisible(true);
                button.setBounds(288, 220, 93, 23);
                button.setEnabled(false);
                button.setText("稳油中");
                //button.setVisible(true);
                timer.start();
                // wyBtn.setEnabled(false);
                //保存稳油开始时间
                if (odRegisterInfoService == null) {
                    odRegisterInfoService = Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
                }
                odRegisterInfoService.merge(acceptanceOdRegisterInfo);

            }
        });
        //endregion

        //region手工录入事件
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (button.isVisible()&&button.getText()=="开始卸油") {
                    acceptanceOdRegisterInfo.setCreatetime(new Date());
                }else{
                    acceptanceOdRegisterInfo.setBeginoilheight(Double.parseDouble(qcsjTable.getValueAt(1, 0).toString()));
                    acceptanceOdRegisterInfo.setBeginoill(Double.parseDouble(qcsjTable.getValueAt(1,1).toString()));
                    acceptanceOdRegisterInfo.setBeginv20l(Double.parseDouble(qcsjTable.getValueAt(1, 2).toString()));
                    acceptanceOdRegisterInfo.setBegintemperature( Double.parseDouble(qcsjTable.getValueAt(1,3).toString()));
                    try {
                        acceptanceOdRegisterInfo.setBegintime(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss",qcsjTable.getValueAt(1,4).toString()));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
                acceptanceOdRegisterInfo.setOilno(cbill.getOilno());
                acceptanceOdRegisterInfo.setDeliveryno(cbill.getDeliveryno());
                acceptanceOdRegisterInfo.setManualNo(cbill.getDeliveryno());
                acceptanceOdRegisterInfo.setOilcan(Integer.parseInt(ygbhLabel.getText().trim()));
                acceptanceOdRegisterInfo.setIsdel(0);
                acceptanceOdRegisterInfo.setEntertype(1);
                Main.jhysNewPage.StopThread();
                JhysPageShlrFrame window = new JhysPageShlrFrame(cbill.getDeliveryno(), cbill.getOilno(), Integer.parseInt(ygbhLabel.getText().trim()),cbill.getPlanl(),cbill,acceptanceOdRegisterInfo);
                //todo
                window.getFrame().setVisible(true);
                reload();
            }
        });
        //endregion

        //region 作废卸油事件
        cancelbtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 取消卸油
                // 删除卸油明细表数据
                AcceptanceOdRegisterInfoKey key = new AcceptanceOdRegisterInfoKey();
                key.setManualNo(cbill.getDeliveryno());
                key.setOilcan(Integer.parseInt(ygbhLabel.getText().trim()));
                odRegisterInfoService.deleteByPrimaryKey(key);
                acceptanceOdRegisterInfo=new AcceptanceOdRegisterInfo();
                for (JComponent item : btns) {
                    item.setVisible(false);
                }
                checkBox.setEnabled(true);
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
                    for (int col = 0; col < 5; col++) {
                        qcsjTable.setValueAt("", row, col);
                    }
                }
                bcxyssField.setText("");
                fyssField.setText("");
                qcvt=null;
                yxyssLabel.setText("");
                UpdateButton3Status(cbill.getDeliveryno());
                //endregion
            }
        });
        //endregion

        //region开始卸油事件
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (button.getText().equals("开始卸油")) {
                    String manualno=CheckTank(canno);
                    if (manualno!=null){
                        JOptionPane.showMessageDialog(null, "出库单:"+manualno+"，正在使用"+canno+"#罐卸油", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    if (!(cb1.isSelected() && cb2.isSelected())) {
                        JOptionPane.showMessageDialog(null, "开始卸油前请确认油枪已停止付油和卸油前稳油", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    //判断油枪状态
                    if(tguncount>0){
                        JOptionPane.showMessageDialog(null, "油枪正在付油，不允许卸油", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    //判断页面是否取到值
                    if (yszgLabel.getText().trim().isEmpty()||jytjLabel.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(null, "请等待页面取得液位仪数据后，开始卸油", "信息提示", JOptionPane.INFORMATION_MESSAGE);
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
                    checkBox.setEnabled(false);
                    wyBtn.setVisible(true);
                    cancelbtn.setVisible(true);
                    cb1.setEnabled(false);
                    cb2.setEnabled(false);
                    btnNewButton.setVisible(true);
                        /*更新其他按钮状态结束*/
                    // 保存出库单信息
                    // 获取油罐前尺数据

                    double heightTotal = yszgLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(yszgLabel.getText().trim());
                    double watetHeight=sgLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(sgLabel.getText().trim());
                    double oilL = jytjLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(jytjLabel.getText().trim());
                    double stardardL = bztjLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(bztjLabel.getText().trim());
                    double temperature = pjwdLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(pjwdLabel.getText().trim());
                    TableModel tableModel1 = qcsjTable.getModel();
                    tableModel1.setValueAt(heightTotal, 1, 0);
                    tableModel1.setValueAt(oilL, 1, 1);
                    qcvt=oilL;
                    //todo
                    tableModel1.setValueAt(stardardL,1,2);
                    //tableModel1.setValueAt(getV20L(OIL_TYPE_1, temperature, oilL),1, 2);
                    tableModel1.setValueAt(temperature, 1, 3);
                    tableModel1.setValueAt(dateFormat.format(new Date()), 1, 4);
                    qcsjTable.updateUI();

                    acceptanceOdRegisterInfo.setCreatetime(new Date());
                    acceptanceOdRegisterInfo.setDeliveryno(cbill.getDeliveryno());
                    acceptanceOdRegisterInfo.setManualNo(cbill.getDeliveryno());
                    acceptanceOdRegisterInfo.setOilcan(Integer.parseInt(ygbhLabel.getText().trim()));
                    acceptanceOdRegisterInfo.setBeginoilheight(heightTotal);
                    acceptanceOdRegisterInfo.setBeginwaterheight(watetHeight);
                    acceptanceOdRegisterInfo.setOilno(cbill.getOilno());
                    acceptanceOdRegisterInfo.setBeginoill(oilL);
                    //acceptanceOdRegisterInfo.setBeginv20l(getV20L(OIL_TYPE_1, temperature, oilL));
                    acceptanceOdRegisterInfo.setBeginv20l(stardardL);
                    acceptanceOdRegisterInfo.setBegintemperature(temperature);
                    acceptanceOdRegisterInfo.setBegintime(new Date());
                    acceptanceOdRegisterInfo.setIsdel(0);
                    acceptanceOdRegisterInfo.setEntertype(0);
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
                    //endTime.setText(dateFormat.format(new Date()));
                    bcxyssField.setEnabled(true);
                    // 获取油罐后尺数据
                    double heightTotal = yszgLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(yszgLabel.getText().trim());
                    double oilL = jytjLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(jytjLabel.getText().trim());
                    double watetHeight=sgLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(sgLabel.getText().trim());
                    double stardardL = bztjLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(bztjLabel.getText().trim());
                    double temperature = pjwdLabel.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(pjwdLabel.getText().trim());
                    TableModel tableModel = qcsjTable.getModel();
                    tableModel.setValueAt(heightTotal, 2, 0);
                    tableModel.setValueAt(oilL, 2, 1);
                    qcvt=null;
                    //todo
                    tableModel.setValueAt(stardardL, 2, 2);
                    //tableModel.setValueAt(getV20L(OIL_TYPE_1, temperature, oilL), 2, 2);
                    tableModel.setValueAt(temperature, 2, 3);
                    tableModel.setValueAt(dateFormat.format(new Date()),2,4);
                    qcsjTable.updateUI();

                    //TableModel tableModel1 = table.getModel();
                    acceptanceOdRegisterInfo.setDeliveryno(cbill.getDeliveryno());
                    acceptanceOdRegisterInfo.setOilcan(Integer.parseInt(ygbhLabel.getText().trim()));
                    acceptanceOdRegisterInfo.setEndoilheight(heightTotal);
                    acceptanceOdRegisterInfo.setEndoill(oilL);
                    acceptanceOdRegisterInfo.setEndv20l(stardardL);
                    acceptanceOdRegisterInfo.setEndtemperature(temperature);
                    acceptanceOdRegisterInfo.setEndtime(new Date());
                    acceptanceOdRegisterInfo.setEndwaterheight(watetHeight);
                    try {
                        duringSales = 0.0;
                        Map salemap = dailyTradeAccountService.GetSaleOilSumByCanNoAndDate(acceptanceOdRegisterInfo.getOilcan().toString(), acceptanceOdRegisterInfo.getBegintime(), acceptanceOdRegisterInfo.getEndtime());
                        if (salemap!=null&&salemap.get("Liter") != null) {
                            duringSales = Double.parseDouble(salemap.get("Liter").toString());
                        }
                        //todo
                        //fyssField.setText(duringSales+"");
                        System.out.println("卸油中销售" + duringSales);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    acceptanceOdRegisterInfo.setDuringsales(duringSales);
                    Double bcsy = Double.parseDouble(tableModel.getValueAt(2, 2).toString())
                            - Double.parseDouble(tableModel.getValueAt(1, 2).toString()) + duringSales;
                    DecimalFormat df2 = new DecimalFormat("######0.00");
                    bcxyssField.setText(Double.parseDouble(df2.format(bcsy)) + "");
                    acceptanceOdRegisterInfo.setDischargel("".equals(bcxyssField.getText().trim()) ? 0d : Double.parseDouble(bcxyssField.getText().trim()));
                    acceptanceOdRegisterInfo.setTranstatus("0");
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
        //endregion
    }

    private String CheckTank(String canno) {
        if (acceptSevices==null){
            acceptSevices=Context.getInstance().getBean(AcceptSevices.class);
        }
        List<AcceptanceOdRegisterInfo> infos=acceptSevices.selectUncompleteInfo();
        if (infos!=null&&infos.size()>0){
            for (AcceptanceOdRegisterInfo info:infos){
                if (info.getOilcan().toString().equals(canno)){
                    return info.getManualNo();
                }
            }
            return null;
        }
        return null;
    }

    private void  reload(){
        this.removeAll();
        this.repaint();
        init();
        Main.jhysNewPage.OpenThread();
    }

    private void UpdateButton3Status(String billno) {
        if (odRegisterInfoService == null) {
            odRegisterInfoService = Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
        }
        List<AcceptanceOdRegisterInfo> infos=odRegisterInfoService.selectbydeliveryno(billno);
        if (infos.size()>0){
            UpdateRadiobtns(true);
        }else{
            UpdateRadiobtns(false);
        }
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

    private void UpdateRadiobtns(boolean ishasdg){
        if (ishasdg){
            dgrbutton.setEnabled(false);
            gcrbutton.setEnabled(false);
        }else{
            dgrbutton.setEnabled(true);
            gcrbutton.setEnabled(true);
        }
    }

    public void UpdateOdreg(String manualno){
        if(odRegisterService==null){
            odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        }
        AcceptanceOdRegister od=odRegisterService.selectByPrimaryKey(manualno);
        //regionselect begin time
        if (odRegisterInfoService == null) {
            odRegisterInfoService = Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
        }
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
            LOG.error("通知卸油状态异常："+e.getMessage());
        }
    }
}
