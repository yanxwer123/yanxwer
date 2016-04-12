package com.kld.app.view.acceptance;

import com.kld.app.service.*;
import com.kld.app.springcontext.Context;
import com.kld.app.util.SysConfig;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.dao.SysManageDictDao;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.util.JsonMapper;
import com.kld.gsm.util.V20Utils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/**
 * Created by niyang on 2016/2/16.
 */
public class Jhys2 extends JPanel {
    @Autowired
    private SysManageDictDao sysManageDictDao;

    private IAcceptanceOdRegisterService odRegisterService;
    private IAcceptanceOdRegisterInfoService odRegisterInfoService;
    private DailyTradeAccountService dailyTradeAccountService;
    private IAcceptanceDeliveryService deliveryService;
    private SysManageCanInfoService sysManageCanInfoService;
    private java.util.List<SysManageOilGunInfo> gunInfos;
    private java.util.List<SysManageCanInfo> canInfos;
    private AlarmDailyLostService alarmDailyLostService;
    private MonitorTimeInventoryService monitorTimeInventoryService;
    private AlarmOilInContrastService alarmOilInContrastService;
    private AcceptSevices acceptSevices;
    private SysmanageService sysmanageService;
    private JTable table;
    private JPanel tablePanel;
    private JLabel tipLabel;
    JScrollPane scrollPane;
    private JPanel mainPanel;
    private AcceptanceDeliveryBills cbill;
    private JButton btnComplete;
    private HashMap<String,JhysTank>canlist;
    private JTextField btnckqf;
    private JTextField btnhkqf;
    private JhysNewPage jhysNewPage;
    private boolean iswhd;
    private AcceptanceOdRegister acceptanceOdRegister;
    private Timer timer2;
    private int timeout;
    private boolean timeoutflag=true;
    private String OIL_TYPE_1;
    private JRadioButton dgrbutton;
    private JRadioButton gcrbutton;
    private ButtonGroup bg;
    private JPanel canpanel;
    private boolean isdg=true;
    private boolean com=true;
    private int sywd=0;
    private String signs;
    private List<Map<String,?>> candata;
    private List<HashMap<String,Object>> gundata;
    private HashMap<String,Map<String,?>>candatamap;

    public String getSigns() {
        return signs;
    }

    public void setSigns(String signs) {
        this.signs = signs;
    }

    public HashMap<String, JhysTank> getCanlist() {
        return canlist;
    }

    public void setCanlist(HashMap<String, JhysTank> canlist) {
        this.canlist = canlist;
    }

    private static final Logger LOG = Logger.getLogger(JhysPage.class);

    public Jhys2(AcceptanceDeliveryBills bill,JhysNewPage newPage){
        cbill=bill;
        this.setSize(600, 530);
        this.setPreferredSize(new Dimension(600, 530));
        this.setLayout(null);
        jhysNewPage=newPage;

        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        this.setSigns(uuid.toString());

        if (Main.jhys2Map==null){
            Main.jhys2Map=new HashMap<String, Jhys2>();
        }
        Main.jhys2Map.put(signs,this);
        init();
        addScollpane();
        getoiltype();
        Addradiogroup();
        addCompletebtn();
        addCKD();
        Loadinfo();
        getyksf();
    }

    private void Loadinfo(){
        if (acceptanceOdRegister.getServicelevel()!=null&&acceptanceOdRegister.getServicelevel()==1){
            gcrbutton.doClick();
            if(acceptanceOdRegister.getHeightwater()!=null){
                gcrbutton.setEnabled(false);
                dgrbutton.setEnabled(false);
                btnComplete.setEnabled(true);
            }
        }else{
            addTanklist();
        }
    }

    private void init(){
        canpanel=new JPanel();
        canpanel.setBounds(0, 85, 550, 25);
        canpanel.setLayout(null);
        this.add(canpanel);

        if (deliveryService==null){
            deliveryService=Context.getInstance().getBean(IAcceptanceDeliveryService.class);
        }
        AcceptanceDeliveryBills bills=deliveryService.selectByPrimaryKey(cbill.getDeliveryno());
        if (bills==null){
            iswhd=true;
        }
        if(odRegisterService==null){
            odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        }
        acceptanceOdRegister=odRegisterService.selectByPrimaryKey(cbill.getDeliveryno());
        if (acceptanceOdRegister==null) {
            acceptanceOdRegister = new AcceptanceOdRegister();
            acceptanceOdRegister.setOilno(cbill.getOilno());
            acceptanceOdRegister.setManualNo(cbill.getDeliveryno());
            acceptanceOdRegister.setPlanl(cbill.getPlanl());

            if (cbill != null && !iswhd) {
                //有出库单进货验收 更新Deliveryno
                acceptanceOdRegister.setDeliveryno(cbill.getDeliveryno());
            }
            // 保存卸油主数据
            acceptanceOdRegister.setInstationtime(new Date());
            odRegisterService.insert(acceptanceOdRegister);
        }
        //UpdateButton3Status(cbill.getDeliveryno());
    }

    private void Addradiogroup(){
        JPanel line=new JPanel();
        line.setBounds(0, 84, 664, 2);
        line.setBorder(new MatteBorder(1, 0, 0, 0, Color.lightGray));
        this.add(line);
        dgrbutton=new JRadioButton("地罐交接",true);
        gcrbutton=new JRadioButton("罐车交接",false);
        dgrbutton.setBounds(2,60,90,20);
        gcrbutton.setBounds(95,60,90,20);
        bg=new ButtonGroup();
        bg.add(dgrbutton);
        bg.add(gcrbutton);
        this.add(dgrbutton);
        this.add(gcrbutton);
        dgrbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isdg==false){
                    isdg=true;
                    acceptanceOdRegister.setServicelevel(0);
                    SaveServer();
                    addTanklist();
                }
            }
        });

        gcrbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isdg == true) {
                    isdg = false;
                    acceptanceOdRegister.setServicelevel(1);
                    SaveServer();
                    addGC();
                }
            }
        });
    }

    private void SaveServer(){
        if (odRegisterService==null){
            odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        }
        odRegisterService.updateByPrimaryKeySelective(acceptanceOdRegister);
    }

    private void addCompletebtn() {
        //region 调用ctrl超时定时器
        timer2=new Timer(1000,null);
        timer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeout--;
                LOG.info(timeout);
                if (timeoutflag && timeout < 0) {
                    timer2.stop();
                    btnComplete.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "完成卸油失败", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (!timeoutflag) {
                    btnComplete.setEnabled(true);
                    timer2.stop();
                }
            }
        });
        //endregion
        btnComplete=new JButton("完成卸油");
        btnComplete.setEnabled(false);
        btnComplete.setBounds(574, 60, 80, 23);
        this.add(btnComplete);
        btnComplete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //jhysNewPage.commplete();
                //根据3.1号会议要求修改
               /* if (btnhkqf.getText().trim().equals("") || btnckqf.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "请填写出库铅封和回空铅封", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }*/
                acceptanceOdRegister = odRegisterService.selectByPrimaryKey(cbill.getDeliveryno());

                // region更新卸油主表信息
                TableModel tableModel = table.getModel();
                double yfss =cbill.getPlanl();// Double.parseDouble(tableModel.getValueAt(0, 3).toString());
                double yfssv20 = 0.0;
                if (tableModel.getValueAt(0, 4) != null && !tableModel.getValueAt(0, 4).toString().equals("")) {
                    double yfwd = Double.parseDouble(tableModel.getValueAt(0, 4).toString());
                    yfssv20 = getV20L(OIL_TYPE_1, yfwd, yfss);
                }
                //地罐交接
                if ((acceptanceOdRegister.getServicelevel() == null || acceptanceOdRegister.getServicelevel() == 0) && yfssv20 > 0 && tableModel.getValueAt(0, 4) != null && !tableModel.getValueAt(0, 4).toString().equals("")) {
                    //获取一堆计算的东西
                    Map result = odRegisterService.getodreglossrate(yfss, yfssv20, tableModel.getValueAt(0, 0).toString());
                    System.out.print(result.toString());
                    acceptanceOdRegister.setRealgetl(Double.parseDouble(result.get("Dischargel").toString()));
                    acceptanceOdRegister.setDuringsales(Double.parseDouble(result.get("DuringSales").toString()));
                    acceptanceOdRegister.setRealGetLV20(Double.parseDouble(result.get("V20").toString()));
                    acceptanceOdRegister.setDischargeloss(Double.parseDouble(result.get("loss").toString()));
                    acceptanceOdRegister.setDischargerate(Double.parseDouble(result.get("lossreate").toString()));
                    acceptanceOdRegister.setDischargeLossV20(Double.parseDouble(result.get("v20loss").toString()));
                    Double shsp = Double.parseDouble(result.get("shsp").toString());
                    if (shsp < 0) shsp = 0d;
                    acceptanceOdRegister.setIndemnityloss(shsp);
                    acceptanceOdRegister.setDischargeRateV20(Double.parseDouble(result.get("V20lossrate").toString()));
                }
                //罐车交接
                if (acceptanceOdRegister.getServicelevel() != null
                        && acceptanceOdRegister.getServicelevel() == 1&&!iswhd) {
                    if (sysManageDictDao == null) {
                        sysManageDictDao = Context.getInstance().getBean(SysManageDictDao.class);
                    }
                    Double syl = Double.parseDouble(sysManageDictDao.selectByCode("sylycsz").getValue());
                    Double shsp = (yfssv20 - acceptanceOdRegister.getRealGetLV20()) - (yfssv20 * syl);
                    if (shsp > 0) {
                        acceptanceOdRegister.setIndemnityloss(shsp);
                    }
                    acceptanceOdRegister.setDischargeloss(yfss - acceptanceOdRegister.getRealgetl());
                    acceptanceOdRegister.setDischargerate(acceptanceOdRegister.getDischargeloss() / yfss);
                    acceptanceOdRegister.setDischargeLossV20(yfssv20 - acceptanceOdRegister.getRealGetLV20());
                    if (acceptanceOdRegister.getDischargeLossV20() == 0.0 || yfssv20 == 0.0) {
                        acceptanceOdRegister.setDischargeRateV20(0.0);
                    } else {
                        acceptanceOdRegister.setDischargeRateV20(acceptanceOdRegister.getDischargeLossV20() / yfssv20);
                    }
                }
                if (!iswhd) {
                    acceptanceOdRegister.setDeliveryno(tableModel.getValueAt(0, 0).toString());
                }
                acceptanceOdRegister.setPlanl(cbill.getPlanl());
                acceptanceOdRegister.setManualNo(tableModel.getValueAt(0, 0).toString());
                acceptanceOdRegister.setCreatetime(new Date());
                acceptanceOdRegister.setTranstatus("0");
                acceptanceOdRegister.setIsdel(0);
                acceptanceOdRegister.setEndtime(new Date());
                acceptanceOdRegister.setBackbankno(btnhkqf.getText().trim());
                acceptanceOdRegister.setPlumbunbankoperator(btnckqf.getText().trim());

                acceptanceOdRegister.setIsfulldose(sywd);
                acceptanceOdRegister.setOilno(tableModel.getValueAt(0, 1).toString());
                odRegisterService.updateByPrimaryKeySelective(acceptanceOdRegister);
                //endregion

                // region 通知主调度
                btnComplete.setEnabled(false);
                timeout = SysConfig.CtrlTimeOut();
                jhysNewPage.commplete(cbill.getDeliveryno());
                timer2.start();
                //endregion
            }
        });
    }

    private void getoiltype() {
        try {
            if(odRegisterService==null){
                odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
            }
            OIL_TYPE_1 = odRegisterService.selectOilType(cbill.getOilno()).getOiltype().toString();
        }catch (Exception e){
            LOG.error("获取油品类型："+e.getMessage());
        }
    }
    /**
     * 出库单
    * */
    public void addCKD(){
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBounds(0, 0, 664, 60);
        tablePanel.setBackground(Color.BLUE);
        this.add(tablePanel);
        if (table==null)table=new JTable();

        Object[][] list=new Object[1][6];
        list[0][0]=cbill.getDeliveryno();
        list[0][1]=cbill.getOilno();
        //region 取油品名称
        if (alarmDailyLostService==null){
            alarmDailyLostService= Context.getInstance().getBean(AlarmDailyLostService.class);
        }
        list[0][2]=alarmDailyLostService.selectOilNo(cbill.getOilno());
        // endregion new DecimalFormat("######0.000").format(bill.get("PlanTon"));
        list[0][3]=new DecimalFormat("0").format(cbill.getPlanl());
        list[0][4]=cbill.getDeliverytemp();
        list[0][5]=cbill.getPlanton();

        //罐车交接原发升数
        //yfssField.setText(bill.getPlanl() == null ? "" : bill.getPlanl().toString());

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

    public void getyksf(){
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (acceptSevices==null){
                        acceptSevices=Context.getInstance().getBean(AcceptSevices.class);
                    }
                    //region 油库实发温度接口
                    SysmanageRealgiveoil realgiveoil= acceptSevices.getbydeliveryno(cbill.getDeliveryno());
                    if (realgiveoil==null){
                        realgiveoil=acceptSevices.getsjfyl(SysConfig.regmoteIp(), cbill.getDeliveryno());
                    }
                    if (realgiveoil!=null&&realgiveoil.getWd()!=null&&realgiveoil.getSjfyl()!=null) {
                        sywd=1;
                        cbill.setDeliverytemp(realgiveoil.getWd());
                        cbill.setDensity(realgiveoil.getMd());
                        cbill.setPlanl(Double.parseDouble(realgiveoil.getSjfyl()));
                        TableModel tableModel = table.getModel();
                        tableModel.setValueAt(cbill.getPlanl(), 0, 3);
                        tableModel.setValueAt(cbill.getDeliverytemp(), 0, 4);
                    }
                    //endregion
                }
            }).start();
        }catch (Exception e){
            LOG.error("油库实发接口："+e.getMessage());
        }
    }


    /**
    *罐列表
    * */
    public void addTanklist(){
        LOG.info(new Date());
        canpanel.removeAll();
        canpanel.repaint();
        JLabel labelcan=new JLabel("油罐:");
        labelcan.setBounds(5, 2, 30, 22);
        canpanel.add(labelcan);

        tipLabel=new JLabel();
        tipLabel.setBounds(453, 2, 110, 20);
        tipLabel.setForeground(Color.red);
        canpanel.add(tipLabel);

        JLabel labelck=new JLabel("出库铅封:");
        labelck.setBounds(220, 60, 60, 20);
        this.add(labelck);

        btnckqf=new JTextField();
        btnckqf.setBounds(282, 60, 110, 20);
        this.add(btnckqf);

        JLabel labelhk=new JLabel("回空铅封:");
        labelhk.setBounds(393, 60, 60, 20);
        this.add(labelhk);

        btnhkqf=new JTextField();
        btnhkqf.setBounds(453, 60, 110, 20);
        this.add(btnhkqf);
        if (acceptSevices==null){
            acceptSevices=Context.getInstance().getBean(AcceptSevices.class);
        }
        LOG.info("1:"+new Date());
        List<AcceptanceOdRegisterInfo> infos=acceptSevices.selectAcceptanceOdRegisterInfo(cbill.getDeliveryno());
        if (infos!=null&& infos.size()>0){
            gcrbutton.setEnabled(false);
            dgrbutton.setEnabled(false);
        }
        LOG.info("2:" + new Date());
        final HashMap<String,String> infomap=new HashMap<String, String>();
        for (AcceptanceOdRegisterInfo item:infos){
            infomap.put(item.getOilcan().toString(),item.getOilcan().toString());
        }
        if (sysManageCanInfoService==null){
            sysManageCanInfoService=Context.getInstance().getBean(SysManageCanInfoService.class);
        }
        LOG.info("3:" + new Date());
        List<SysManageCanInfo> canInfos=sysManageCanInfoService.selectByOilNo(cbill.getOilno());
        LOG.info("4:" + new Date());
        mainPanel=new JPanel();
        scrollPane.setViewportView(mainPanel);
        mainPanel.setLayout(null);

        canlist=new HashMap<String, JhysTank>();
        LOG.info("5:" + new Date());
        for (int i=0;i<canInfos.size();i++){
            LOG.info("5:" + i + ":" + new Date());
            final JCheckBox checkBox=new JCheckBox(canInfos.get(i).getOilcan().toString()+"#");
            checkBox.setBounds(30 + i * 52, 4, 52, 20);
            canpanel.add(checkBox);
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    JCheckBox source = (JCheckBox) e.getItemSelectable();
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        HashMap<String, JComponent> components = new HashMap<String, JComponent>();
                        components.put("btncomplete", btnComplete);
                        components.put("checkbox", source);
                        components.put("dg", dgrbutton);
                        components.put("gc", gcrbutton);
                        LOG.info("7:" + new Date());
                        JhysTank jhysTank = new JhysTank(source.getText().replace("#", ""), cbill, OIL_TYPE_1, components);
                        LOG.info("8:" + new Date());
                        String canno = source.getText().replace("#", "");
                        canlist.put(canno, jhysTank);
                        if (null == infomap.get(source.getText().replace("#", "")) && candata != null && candata.size() > 0) {
                            jhysTank.UpdateGun(gundata);
                            if (candatamap != null && candatamap.size() > 0) {
                                jhysTank.UpdateTank(candatamap.get(canno));
                            }
                        } else {
                            LOG.info("candata is null");
                            //checkBox.setSelected(false);
                        }
                    } else {
                        canlist.remove(source.getText().replace("#", ""));
                    }
                    if (checkKR(cbill.getPlanl()) < 0) {
                        //JOptionPane.showMessageDialog(null, "空容小于卸油量", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        tipLabel.setText("空容小于卸油量");
                    } else {
                        tipLabel.setText("");
                    }
                    LoadMainPanel();
                }
            });
            if(null!=infomap.get(canInfos.get(i).getOilcan().toString())){
                checkBox.doClick();
                checkBox.setEnabled(false);
            }
        }
        canpanel.updateUI();
        LOG.info("6:" + new Date());
    }

    public void LoadMainPanel(){
        LOG.info("LoadMainPanel 1:" + new Date());
        mainPanel.removeAll();
        mainPanel.repaint();
        int panelHeight = 252;
        mainPanel.setPreferredSize(new Dimension(657, canlist.size() * panelHeight + 20));
        mainPanel.setBounds(0, 0, 657, canlist.size() * panelHeight);

        int i=0;
        for (Map.Entry<String, JhysTank> entry : canlist.entrySet()) {
             JhysTank tank=entry.getValue();
             int top = panelHeight * i + 2*i;
             tank.setBounds(0, top, 657, panelHeight);
             mainPanel.add(tank);
             i++;
        }
        LOG.info("LoadMainPanel 2:" + new Date());
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

    public void update(GasMsg gasMsg) {
        //region gun status
        if (gasMsg.getPid().equals("A15_10002")){
            LOG.info("油枪状态Ctrl");
            //油枪状态
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List<HashMap<String,Object>> macLogInfos=resultMsg.getData();
            gundata=macLogInfos;
            HashMap<String,JhysTank> canlist=Main.jhys2Map.get(this.getSigns()).getCanlist();
//            LOG.info("gun canlist:"+canlist==null?"0":canlist.size());
            if (canlist!=null&&canlist.size()>0) {
                Iterator iter = canlist.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    JhysTank val = (JhysTank) entry.getValue();
                    LOG.info("load gun");
                    val.UpdateGun(macLogInfos);
                }
            }
        }
        //endregion

        //region tankstatus
        if (gasMsg.getPid().equals("A15_10004")){
            LOG.info("回调实时库存Ctrl");
            JsonMapper json=new JsonMapper();
            ResultMsg msg=json.fromJson(gasMsg.getMessage(), ResultMsg.class);
            //根据选定的canlist，调用Jhsytank.updateTank()
            //实时库存
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            candata= resultMsg.getData();
            for (int m=0;m<candata.size();m++){
                // region获取罐号
                Map<String,?> map=(Map) (resultMsg.getData().get(m));
                String canno=map.get("uOilCanNo").toString();
                HashMap<String,JhysTank> canlist=Main.jhys2Map.get(signs).getCanlist();
                LOG.info("tank canlist:"+canlist==null?"0":canlist.size());
                if (canlist!=null&&canlist.size()>0) {
                    JhysTank tank = canlist.get(canno);
                    if (tank != null) {
                        LOG.info("load tank");
                        tank.UpdateTank(map);
                        if (candatamap==null){
                            candatamap=new HashMap<String, Map<String, ?>>();
                        }
                        candatamap.put(canno,map);
                    }
                }
                //endregion
            }
        }
        //endregion

        //region notice Ctrl jhys
        if(gasMsg.getPid().equals("A15_10005")) {
            LOG.info("进货验收ctrl回调");

            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List<String> deliveryno = resultMsg.getData();
            if (deliveryno != null && deliveryno.get(0) != null && deliveryno.get(0).toString().equals(cbill.getDeliveryno())) {
                if ("1".equals(resultMsg.getResult())) {
                    timeout = -1;
                    return;
                }
                timeoutflag = false;
                if (iswhd) {
                    AcceptanceNoBills noBills = new AcceptanceNoBills();
                    noBills.setDeliveryno(cbill.getDeliveryno());
                    AcceptanceNoBills tnobill = deliveryService.getNobillBykey(noBills.getDeliveryno());
                    if (tnobill != null) {
                        noBills = tnobill;
                    }
                    noBills.setIscomplete("1");
                    deliveryService.insertNobills(noBills);
                } else {
                    AcceptanceDeliveryBills bill = new AcceptanceDeliveryBills();
                    bill.setDeliveryno(cbill.getDeliveryno());
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
                    addOilinContrat(acceptanceOdRegister);
                } catch (Exception e2) {
                    System.out.println(e2.getMessage());
                }
                //todo completed = true;
                //jhysNewPage.commplete(cbill.getDeliveryno());

                jhysNewPage.yhys2close(cbill.getDeliveryno());
            }
        }
        //endregion

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

    private void addGC(){
        canpanel.removeAll();
        canpanel.repaint();
        mainPanel=new JPanel();
        scrollPane.setViewportView(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 657, 500);
        JhysGc jhysGc=new JhysGc(cbill,OIL_TYPE_1,btnComplete);
        mainPanel.add(jhysGc);
    }

    private void addScollpane() {
        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 112, 664, 416);
        scrollPane.setVisible(true);
        this.add(scrollPane);
    }

    private Double checkKR(Double Vt){
        Double krsum=0.0;
        if (canlist!=null&&canlist.size()>0&&candata!=null&&candata.size()>0) {
            for (int m = 0; m < candata.size(); m++) {
                // region获取罐号
                Map<String, ?> map = (Map) (candata.get(m));
                String canno1 = map.get("uOilCanNo").toString();
                JhysTank tank = canlist.get(canno1);
                if (tank != null) {
                    Double dktj = Double.parseDouble(new DecimalFormat("0").format(map.get("fEmptyCubage")).toString());
                    krsum += dktj;
                }
            }
            return krsum - Vt;
        }else {
            return 0d;
        }
    }
}
