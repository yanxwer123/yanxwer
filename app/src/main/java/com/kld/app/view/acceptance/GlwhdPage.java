package com.kld.app.view.acceptance;

import com.kld.app.service.AlarmOilInContrastService;
import com.kld.app.service.IAcceptanceDeliveryService;
import com.kld.app.service.IAcceptanceOdRegisterInfoService;
import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.util.SysConfig;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.dao.SysManageDictDao;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.DateUtil;
import com.kld.gsm.util.JsonMapper;
import com.kld.gsm.util.V20Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * 关联无货单
 */

public class GlwhdPage extends JOptionPane implements WindowListener,Watcher {

    @Autowired
    private SysManageDictDao sysManageDictDao;
    private   String OIL_TYPE_1 = "01";
    private static final Logger LOG = Logger.getLogger(GlwhdPage.class);
    private AlarmOilInContrastService alarmOilInContrastService;
    private SysmanageService sysmanageService;
    private JDialog frame;
    private JTable table;
    private AcceptSevices acceptSevices;
    private JPanel centermain;

    public JPanel getCentermain() {
        return centermain;
    }

    public void setCentermain(JPanel centermain) {
        this.centermain = centermain;
    }

    private String billno;
    private int timeout=5;
    private boolean timeoutflag=true;

    private String manualbillno;
    private CkdcxPage ckdcxPage;

    public CkdcxPage getCkdcxPage() {
        return ckdcxPage;
    }
    private IAcceptanceOdRegisterService odRegisterService;
    public void setCkdcxPage(CkdcxPage ckdcxPage) {
        this.ckdcxPage = ckdcxPage;
    }
    public String getBillno() {
        return billno;
    }

    private AcceptanceDeliveryBills bill;
    public void setBillno(String billno) {
        this.billno = billno;
        LoadData(billno);
    }

    /**
     * Create the application.
     */
    public GlwhdPage() {
        initialize();
    }

    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    private IAcceptanceDeliveryService iAcceptanceDeliveryService;

    private IAcceptanceOdRegisterService iAcceptanceOdRegisterService;

    private IAcceptanceOdRegisterInfoService iAcceptanceOdRegisterInfoService;



    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        //region注册观察者开始

        Watched watch = ConcreteWatched.getInstance();
        //Watcher watcher = new GlwhdPage();
        watch.addWetcher("A", this);
        ////System.out.println("注册观察者");
        //endregion

        frame = new JDialog();
        frame.setModal(true);
        frame.getContentPane().setFont(new Font("宋体", Font.PLAIN, 12));
        frame.setTitle("关联无货单");
        frame.setBounds(100, 100, 698, 368);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.getContentPane().setLayout(null);

        Main.setCenter(frame);
        JLabel psdLabel = new JLabel("验收登记信息");
        psdLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        psdLabel.setBounds(0, 0, 682, 25);
        frame.getContentPane().add(psdLabel);

        final JButton confirmBtn = new JButton("确定");
        confirmBtn.setBounds(579, 297, 93, 23);
        frame.getContentPane().add(confirmBtn);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        //region 调用ctrl超时定时器
        final Timer timer=new Timer(1000,null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeout--;
                ////System.out.println(timeout);
                if(timeoutflag&&timeout<0){
                    timer.stop();
                    confirmBtn.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "通知管控系统失败", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (!timeoutflag){
                    confirmBtn.setEnabled(true);
                    timer.stop();
                }
            }
        });
        //endregion
        //region complete jhys event
        confirmBtn.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                timeout= SysConfig.CtrlTimeOut();
                if (table==null)return;
                int selectRow= table.getSelectedRow();// 取得用户所选行的行数

                TableModel tableModel = table.getModel();

                if (selectRow < 0) {
                }else {
                    manualbillno=tableModel.getValueAt(selectRow,0).toString();
                  /*  if (Math.abs(bill.getPlanl()-Double.parseDouble(tableModel.getValueAt(selectRow, 1).toString()))>1){
                        JOptionPane.showMessageDialog(null, "原发升数不同", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }*/
                    confirmBtn.setEnabled(false);
                    //region  通知主调度
                    try {
                        timeout=SysConfig.CtrlTimeOut();

                        GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10005.toString());
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("1", manualbillno.trim());
                        map.put("2", "3");
                        map.put("3",bill.getDeliveryno());
                        ResultMsg msg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
                        List s = new ArrayList();
                        s.add(map);
                        msg.setData(s);
                        gasMsg.setMessage(new JsonMapper().toJson(msg));
                        //System.out.println(Main.sign + ",出库单关联无货单 send:" + gasMsg.toString());
                        Main.CC.writeAndFlush(gasMsg);
                        timer.start();
                    } catch (Exception e2) {
                        ////System.out.println("通知主调度失败");
                    }
                    //endregion

                }

            }
        });
        //endregion
    }

    public void LoadData(String billno){

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 24, 682, 263);
        frame.getContentPane().add(scrollPane);
        this.billno=billno;

        this.iAcceptanceDeliveryService=Context.getInstance().getBean(IAcceptanceDeliveryService.class);

        //获取出库单信息
        bill=iAcceptanceDeliveryService.selectByPrimaryKey(billno);

        //获取无货单的卸油登记
         List<AcceptanceOdRegister>ods=iAcceptanceDeliveryService.getNobillOds(bill.getOilno());
        if (ods==null||ods.isEmpty()){
            JOptionPane.showMessageDialog(this, "没有找到相关数据", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            frame.setVisible(false);
            frame.dispose();
            return;
        }else
        {
        final String[] headers = {"手工出库单号","原发升数(L)","车牌号码","开始卸油时间","结束卸油时间","实收升数(L)","损耗量(L)","损耗率(%)"};
        final Object[][] data =getODDataArray(ods, headers.length);
         DefaultTableModel model=new DefaultTableModel(data,headers);
        table = new MyTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            table.setRowHeight(20);
            table.setCellSelectionEnabled(false);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setRowSelectionAllowed(true);
            table.getColumnModel().getColumns();
            table.setModel(model);
            for (int i = 0; i < headers.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(150);
            }
            table.updateUI();

            scrollPane.setViewportView(table);
        }
    }

    private Object[][] getODDataArray(List<AcceptanceOdRegister> ods ,Integer COLUMN_COUNT) {

        this.iAcceptanceDeliveryService= Context.getInstance().getBean(IAcceptanceDeliveryService.class);
        Object[][] billArray = null;
        if (!ods.isEmpty()) {
            billArray = new Object[ods.size()][COLUMN_COUNT];
            for (int i = 0; i < ods.size(); i++) {
                AcceptanceOdRegister od = ods.get(i);
                billArray[i][0] = od.getManualNo(); //显示手工单号的值
                billArray[i][1] = od.getPlanl();
                billArray[i][2] =iAcceptanceDeliveryService.getNobillBykey(od.getManualNo()).getCardno();
                billArray[i][3] = DateUtil.getDate(od.getBegintime(), "yyyy-MM-dd HH:mm:ss");
                billArray[i][4] = DateUtil.getDate(od.getEndtime(), "yyyy-MM-dd HH:mm:ss");
                billArray[i][5] = od.getRealgetl();
                billArray[i][6] = od.getDischargeloss();
                billArray[i][7] = od.getDischargerate();
            }
        }
        return billArray;
    }

    //region window events
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

    }

    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     *
     * @param e
     */
    @Override
    public void windowClosed(WindowEvent e) {
        try {
            centermain.removeAll();
            centermain.repaint();
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

    @Override
    public void update(GasMsg gasMsg) {
        if (gasMsg.getPid().equals("A15_10005")){
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            if ("1".equals(resultMsg.getResult())) {timeout=-1;return;}
            timeoutflag=false;
            //timeout=-1;
            if (iAcceptanceOdRegisterInfoService==null){
                iAcceptanceOdRegisterInfoService=Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
            }
            iAcceptanceOdRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
            AcceptanceOdRegister odg=iAcceptanceOdRegisterService.selectByPrimaryKey(manualbillno);

            AcceptanceNoBills noBills=iAcceptanceOdRegisterService.selectNobill(manualbillno);
            noBills.setIscomplete("2");

            odg.setTranstatus("0");
            odg.setDeliveryno(billno);
            //iAcceptanceOdRegisterService.updateByPrimaryKey(odg);
            iAcceptanceOdRegisterService.UpdateNobill(noBills);
            List<AcceptanceOdRegisterInfo> infos=iAcceptanceOdRegisterInfoService.selectbydeliveryno(odg.getManualNo());
            for (AcceptanceOdRegisterInfo item:infos){
                item.setDeliveryno(billno);
                iAcceptanceOdRegisterInfoService.merge(item);
            }

            bill.setIscomplete("1");
            bill.setRelevancedelveryno(odg.getManualNo());// 更新出库单无货单表
            iAcceptanceDeliveryService.updateByPrimaryKey(bill);

            double yfss = bill.getPlanl() == null ? 0 : bill.getPlanl();
            double yfssv20 = 0.0;

            try{

                if (acceptSevices==null){
                    acceptSevices=Context.getInstance().getBean(AcceptSevices.class);
                }
                //region 油库实发温度接口
                SysmanageRealgiveoil realgiveoil= acceptSevices.getbydeliveryno(bill.getDeliveryno());
                if (realgiveoil==null){
                    realgiveoil=acceptSevices.getsjfyl(SysConfig.regmoteIp(), bill.getDeliveryno());
                }
                if (realgiveoil!=null&&realgiveoil.getWd()!=null&&realgiveoil.getSjfyl()!=null) {
                    bill.setDeliverytemp(realgiveoil.getWd());
                    bill.setDensity(realgiveoil.getMd());
                    bill.setPlanl(Double.parseDouble(realgiveoil.getSjfyl()));
                }
            }catch(Exception e){
                LOG.error("get res："+e.getMessage());
            }

            double yfwd = bill.getDeliverytemp() == null ? 0 : bill.getDeliverytemp();
            yfssv20 = getV20L(OIL_TYPE_1, yfwd, yfss);

            if (odg.getServicelevel() != null && odg.getServicelevel() == 1) {
                if(sysManageDictDao==null){
                    sysManageDictDao=Context.getInstance().getBean(SysManageDictDao.class);
                }
                Double syl = Double.parseDouble(sysManageDictDao.selectByCode("sylycsz").getValue());
                Double shsp = (yfssv20 - odg.getRealGetLV20()) - (yfssv20 * syl);
                if (shsp > 0) {
                    odg.setIndemnityloss(shsp);
                }
                odg.setDischargeloss(yfss - odg.getRealgetl());
                odg.setDischargerate(odg.getDischargeloss() / yfss);
                odg.setDischargeLossV20(yfssv20 - odg.getRealGetLV20());
                odg.setDischargeRateV20(odg.getDischargeLossV20() / yfssv20);

            }else {
                //region  计算损益
                if (odRegisterService == null) {
                    odRegisterService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
                }
                //设置油品类型
                try {
                    OIL_TYPE_1 = odRegisterService.selectOilType(bill.getOilno()).getOiltype().toString();
                } catch (Exception e) {
                    LOG.error("获取油品类型：" + e.getMessage());
                }

                Map result = odRegisterService.getodreglossrate(yfss, yfssv20, bill.getDeliveryno());
                System.out.print(result.toString());
                try {
                    odg.setRealgetl(Double.parseDouble(result.get("Dischargel").toString()));
                    odg.setDuringsales(Double.parseDouble(result.get("DuringSales").toString()));
                    odg.setRealGetLV20(Double.parseDouble(result.get("V20").toString()));
                    odg.setDischargeloss(Double.parseDouble(result.get("loss").toString()));
                    odg.setDischargerate(Double.parseDouble(result.get("lossreate").toString()));
                    odg.setDischargeLossV20(Double.parseDouble(result.get("v20loss").toString()));
                    Double shsp = Double.parseDouble(result.get("shsp").toString());

                    if (shsp < 0) shsp = 0d;
                    odg.setIndemnityloss(shsp);
                    odg.setDischargeRateV20(Double.parseDouble(result.get("V20lossrate").toString()));
                } catch (Exception e) {
                    LOG.error("计算损益失败" + e.getMessage());
                }
                //endregion
            }
            try {
                iAcceptanceOdRegisterService.updateByPrimaryKey(odg);
                addOilinContrat(odg);

                //region
                try
                {
                    LOG.info("delivery begin");
                    if (sysmanageService==null){
                        sysmanageService=Context.getInstance().getBean(sysmanageService.getClass());
                    }
                    if (acceptSevices==null) {
                        acceptSevices = Context.getInstance().getBean(AcceptSevices.class);
                    }
                    SysManageDepartment sysManageDepartment=sysmanageService.getdeptinfo();
                    if (sysManageDepartment!=null) {
                        acceptSevices.sendOdreg(SysConfig.regmoteIp(), sysManageDepartment.getSinopecnodeno());
                    }
                    LOG.info("delivery end");
                }
                catch (Exception ex)
                {
                    LOG.error("delivery failed"+ex.getMessage());
                }
                //endregion


                ckdcxPage.reLoad();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            frame.setVisible(false);
            frame.dispose();
        }
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
}
