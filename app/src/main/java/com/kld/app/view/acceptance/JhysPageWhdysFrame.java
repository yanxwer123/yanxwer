package com.kld.app.view.acceptance;

import com.kld.app.service.IAcceptanceDeliveryService;
import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.*;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public class JhysPageWhdysFrame  extends JFrame{

    private JDialog frame;
    private JComboBox ypBox;
    private JTextField ckdhField;
    private JTextField qrdhField;
    private JTextField yfssField;
    private JTextField cphmField;
    private JTextField fyykField;
    private JTextField mdyzField;
    private JTextField yfslField;
    private JButton xyBtn;
    private JhysPage psdxx;
    private static final Logger LOG = Logger.getLogger(JhyscxPage.class);
    private IAcceptanceDeliveryService deliveryService;
    private com.kld.gsm.ATG.service.SysManageDic sysManageDic;
    private com.kld.gsm.ATG.service.AcceptSevices acceptSevices;
    private com.kld.gsm.ATG.service.SysmanageService sysmanageService;
    private IAcceptanceOdRegisterService acceptanceOdRegister;
    private AcceptanceDeliveryBills bill;

    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    /**
     * Create the application.
     */
    public JhysPageWhdysFrame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("无货单验收");
        frame.setBounds(100, 100, 409, 409);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        // frame.setUndecorated(true);
        Main.setCenter(frame);
        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("Button.light"));
        panel.setBounds(0, 0, 403, 380);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 30, 403, 254);
        panel.add(separator);

        JLabel label = new JLabel("出库单信息");
        label.setBounds(10, 5, 72, 15);
        panel.add(label);

        JLabel ckdhLabel = new JLabel("出库单号:");
        ckdhLabel.setBounds(20, 43, 72, 15);
        panel.add(ckdhLabel);

        JLabel qrdhLabel = new JLabel("确认单号:");
        qrdhLabel.setBounds(20, 77, 72, 15);
        panel.add(qrdhLabel);

        final JLabel ypLabel = new JLabel("油品名称:");
        ypLabel.setBounds(20, 112, 72, 15);
        panel.add(ypLabel);

        JLabel yfssLabel = new JLabel("原发升数(L):");
        yfssLabel.setBounds(20, 147, 72, 20);
        panel.add(yfssLabel);

        JLabel yfslLabel=new JLabel("原发数量(t):");
        yfslLabel.setBounds(20, 187, 72, 20);
        panel.add(yfslLabel);


        JLabel cphmLabel = new JLabel("车牌号码:");
        cphmLabel.setBounds(20, 227, 72, 20);
        panel.add(cphmLabel);

        JLabel fyykLabel = new JLabel("发油油库:");
        fyykLabel.setBounds(20, 260, 72, 20);
        panel.add(fyykLabel);

        JLabel mkyzLabel = new JLabel("目的油站:");
        mkyzLabel.setBounds(20, 301, 72, 20);
        panel.add(mkyzLabel);

        xyBtn = new JButton("开始卸油");
        xyBtn.setBounds(147, 336, 93, 23);
        panel.add(xyBtn);


        ckdhField = new JTextField();
        ckdhField.setBounds(90, 40, 136, 25);
        ckdhField.setDocument(new SuperStringDocument(20));
        panel.add(ckdhField);
        ckdhField.setColumns(10);
        ckdhField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                if (xyBtn.isEnabled()) {
                    setdefault();
                }
            }
        });

        qrdhField = new JTextField();
        qrdhField.setColumns(10);
        qrdhField.setDocument(new SuperStringDocument(20));
        qrdhField.setBounds(90, 74, 136, 25);
        panel.add(qrdhField);

        qrdhField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                if (xyBtn.isEnabled()) {
                    setdefault();
                }
            }
        });

        JButton xzsjBtn = new JButton("下载数据");
        xzsjBtn.setBounds(230, 73, 83, 23);
        xzsjBtn.setMargin(new Insets(2, 3, 2, 3));
        panel.add(xzsjBtn);
        xzsjBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String chdh = ckdhField.getText().trim();
                String qrdh = qrdhField.getText().trim();
                if ("".equals(ckdhField.getText().trim())){
                    JOptionPane.showMessageDialog(frame, "请填写出库单号", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!chdh.equalsIgnoreCase(qrdh)) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(frame, "出库单号和确认单号不一致", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //下载出库单
                if (sysManageDic==null){
                    sysManageDic=Context.getInstance().getBean(com.kld.gsm.ATG.service.SysManageDic.class);
                }
                if (sysmanageService==null){
                    sysmanageService=Context.getInstance().getBean(com.kld.gsm.ATG.service.SysmanageService.class);
                }
                if (acceptSevices==null){
                    acceptSevices=Context.getInstance().getBean(com.kld.gsm.ATG.service.AcceptSevices.class);
                }
                if (acceptanceOdRegister==null){
                    acceptanceOdRegister=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
                }

                List<SysManageOilType> types=acceptanceOdRegister.selectUseOilType();
                //清除缓存
               // ypBox.removeAllItems();
                //ypBox.removeAll();
               // ypBox.repaint();
                //绑定油品
                if (!types.isEmpty()){
                    ypBox.removeAllItems();
                    for (SysManageOilType oilType:types){
                        String s2=oilType.getOilname();
                        ComboboxItem item=new ComboboxItem(oilType.getOilno(),s2);

                        ypBox.addItem(item);
                    };
                }
                String host= SysConfig.regmoteIp();
                SysManageDepartment dept=sysmanageService.getdeptinfo();
                if (dept!=null){
                    mdyzField.setText(dept.getNodetag());
                }
                try {
                    //region 1.本地查询
                    AcceptanceDeliveryBills billitem = acceptSevices.getbillfromlocal(ckdhField.getText().trim());
                    AcceptanceOdRegister od = acceptanceOdRegister.selectByPrimaryKey(ckdhField.getText().trim());
                    if (billitem != null||od!=null) {
                        String msg = "";
                        if (od != null) {
                            //已经卸油
                            if (od.getBegintime() != null && od.getEndtime() != null) {
                                msg = "已完成卸油的出库单号";
                            } else {
                                msg = "已存在的出库单号,请前往出库单查询，进行进货验收";
                            }
                        }else
                        {
                            msg="已存在的出库单号,请前往出库单查询，进行进货验收";
                        }
                        JOptionPane.showMessageDialog(null, msg, "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    //endregion
                    //region 2.服务器查询
                    bill = acceptSevices.getDebillByNo(host, qrdhField.getText().trim());
                    if (bill != null && !"".equals(bill.getDeliveryno())) {
                        //下载到单号，没有卸油
                        ComboboxItem itemd = new ComboboxItem();
                        //开始检测油品类型是否能接收
                        boolean chk = false;
                        for (SysManageOilType type : types) {
                            if (type.getOilno().trim().equals(bill.getOilno().trim())) {
                                chk = true;
                                itemd = new ComboboxItem(type.getOilno(), type.getOilname());
                                //break;
                            }
                        }
                        //检测油品结束
                        if (!chk) {
                            JOptionPane.showMessageDialog(null, "没有油罐可以接收的油品类型。", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        //设置已选择box
                        if (itemd != null) {
                            ypBox.setSelectedItem(itemd);
                        }
                        yfssField.setText(bill.getPlanl().toString());
                        cphmField.setText(bill.getCarno().toString());
                        fyykField.setText(bill.getFromdepotname());
                        mdyzField.setText(bill.getTostationname());
                        yfslField.setText(bill.getPlanton() == null ? "" : bill.getPlanton().toString());
                        xyBtn.setEnabled(true);
                    } else {
                        //无出库单
                        //无货单卸油
                        JOptionPane.showMessageDialog(null, "没有找到可用的出库单，请手动填写出库单数据。", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        ypBox.setEnabled(true);
                        yfssField.setEnabled(true);
                        cphmField.setEnabled(true);
                        fyykField.setEnabled(true);
                        //mdyzField.setEnabled(true);
                        yfslField.setEnabled(true);
                        xyBtn.setEnabled(true);
                    }

                    //endregion
                }catch (Exception e1){
                    e1.printStackTrace();
                    LOG.info("下载失败", e1);
                }
                //region oldcode
                /*try{
                     bill=acceptSevices.getDebillByNo(host, qrdhField.getText().trim());

                    if (bill!=null&&!"".equals(bill.getDeliveryno())) {
                        //比较本地已卸油记录
                     *//*   AcceptanceOdRegister od = acceptanceOdRegister.selectByPrimaryKey(bill.getDeliveryno());
                        if (od != null) {
                            //已经卸油
                            String msg="";
                            if(od.getEndtime()!=null){
                                msg="已完成卸油";
                            }else
                            {
                                msg="正在卸油";
                            }
                            JOptionPane.showMessageDialog(null, msg, "信息", JOptionPane.INFORMATION_MESSAGE);
                            return;*//*
                        }else  {

                           AcceptanceDeliveryBills billitem= acceptSevices.getbillfromlocal(ckdhField.getText().trim());
                            if (billitem!=null){
                                //本地已存在要下载的单号
                                JOptionPane.showMessageDialog(null, "已存在的出库单号,请前往出库单查询，进行进货验收。", "信息", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                            //下载到单号，没有卸油
                            ComboboxItem itemd=new ComboboxItem();
                            //开始检测油品类型是否能接收
                            boolean chk=false;
                            for (SysManageOilType type:types){
                                if (type.getOilno().trim().equals(bill.getOilno().trim())){
                                    chk=true;
                                    itemd=new ComboboxItem(type.getOilno(),type.getOilname());
                                    //break;
                                }
                            }
                            //检测油品结束
                            if (!chk){
                                JOptionPane.showMessageDialog(null, "没有油罐可以接收的油品类型。", "信息", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                            //设置已选择box
                            if (itemd!=null){
                                ypBox.setSelectedItem(itemd);
                            }
                            yfssField.setText(bill.getPlanl().toString());
                            cphmField.setText(bill.getCarno().toString());
                            fyykField.setText(bill.getFromdepotname());
                            mdyzField.setText(bill.getTostationname());
                            xyBtn.setEnabled(true);
                        }
                    }else  {
                        //无货单卸油
                        JOptionPane.showMessageDialog(null, "没有找到可用的出库单，请手动填写出库单数据。", "信息", JOptionPane.INFORMATION_MESSAGE);
                        ypBox.setEnabled(true);
                        yfssField.setEnabled(true);
                        cphmField.setEnabled(true);
                        fyykField.setEnabled(true);
                        mdyzField.setEnabled(true);
                        xyBtn.setEnabled(true);
                    }
                }
                catch (Exception e1){
                    e1.printStackTrace();
                    LOG.info("下载失败", e1);

                }*/
                //endregion
            }
        });

      /*  JButton sglrBtn = new JButton("手工录入");
        sglrBtn.setBounds(315, 73, 83, 23);
        sglrBtn.setMargin(new Insets(2, 3, 2, 3));
        panel.add(sglrBtn);*/

        ypBox = new JComboBox();
        ypBox.setEnabled(false);
        ypBox.setBounds(90, 109, 260, 25);
        panel.add(ypBox);

        yfssField = new JTextField();
        yfssField.setDocument(new SuperDoubleDocument(6,2));
        yfssField.setEnabled(false);
        yfssField.setColumns(10);
        yfssField.setBounds(90, 144, 260, 25);
        panel.add(yfssField);

        yfslField=new JTextField();
        yfslField.setDocument(new SuperDoubleDocument(6,2));
        yfslField.setEnabled(false);
        yfslField.setColumns(10);
        yfslField.setBounds(90, 184, 260, 25);
        panel.add(yfslField);

        cphmField = new JTextField();
        cphmField.setDocument(new SuperStringDocument(16));
        cphmField.setEnabled(false);
        cphmField.setColumns(10);
        cphmField.setBounds(90, 224, 260, 25);
        panel.add(cphmField);

        fyykField = new JTextField();
        fyykField.setEnabled(false);
        fyykField.setDocument(new SuperStringDocument(30));
        fyykField.setColumns(10);
        fyykField.setBounds(90, 259, 260, 25);
        panel.add(fyykField);

        mdyzField = new JTextField();
        mdyzField.setEnabled(false);
        mdyzField.setDocument(new SuperStringDocument(60));
        mdyzField.setColumns(10);
        mdyzField.setBounds(90, 299, 260, 25);
        panel.add(mdyzField);


        xyBtn.setEnabled(false);
        //region 卸油事件
        xyBtn.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        if (yfssField.getText() == null || "".equals(yfssField.getText().trim()) || cphmField.getText()==null||
                                                "".equals(cphmField.getText().trim())||fyykField.getText()==null||"".equals(fyykField.getText().trim())
                                                ||"".equals(yfslField.getText().trim())) {
                                            JOptionPane.showMessageDialog(null, "请将数据补充填写完整。", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                                            return;
                                        }
                                        Object[] options = {"确定", "取消"};
                                        int response = JOptionPane.showOptionDialog(null, "是否开始卸油", "确认卸油", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                        if (response == 0) {
                                            try {
                                                if (bill == null) {
                                                    //nothing
                                                }

                                                //region bill setvalue
                                                if (bill == null || "".equals(bill.getDeliveryno())) {
                                                    bill = new AcceptanceDeliveryBills();
                                                    bill.setDeliveryno(ckdhField.getText().trim());
                                                    bill.setOilno(((ComboboxItem) ypBox.getSelectedItem()).getKey());
                                                    bill.setPlanl(Double.parseDouble(yfssField.getText().trim()));

                                                    AcceptanceNoBills acceptanceNoBills = new AcceptanceNoBills();
                                                    acceptanceNoBills.setArrivetime(new Date());
                                                    acceptanceNoBills.setCardno(cphmField.getText().trim());
                                                    acceptanceNoBills.setConfirmno(qrdhField.getText().trim());
                                                    acceptanceNoBills.setCreatetime(new Date());
                                                    acceptanceNoBills.setDeliveryno(ckdhField.getText().trim());
                                                    acceptanceNoBills.setFromoildepot(fyykField.getText().trim());
                                                    acceptanceNoBills.setOilname(ypBox.getSelectedItem().toString());
                                                    acceptanceNoBills.setOilno(((ComboboxItem) ypBox.getSelectedItem()).getKey());
                                                    acceptanceNoBills.setPlanl(Double.parseDouble(yfssField.getText().trim()));
                                                    acceptanceNoBills.setTonodeno(mdyzField.getText().trim());
                                                    acceptanceNoBills.setPlanton(Double.parseDouble(yfslField.getText().trim()));
                                                    acceptanceNoBills.setIscomplete("0");

                                                    bill.setPlanl(acceptanceNoBills.getPlanl());
                                                    bill.setArrivaltime(acceptanceNoBills.getArrivetime());
                                                    bill.setCarno(acceptanceNoBills.getCardno());
                                                    bill.setFromdepotname(acceptanceNoBills.getFromoildepot());
                                                    bill.setTostationname(acceptanceNoBills.getTonodeno());
                                                    bill.setPlanton(acceptanceNoBills.getPlanton());

                                                    //保存无货单登记信息 to nobills  table

                                                    if (deliveryService == null) {
                                                        deliveryService = Context.getInstance().getBean(IAcceptanceDeliveryService.class);
                                                    }
                                                    //判定无货单号，有无重复
                                                    AcceptanceNoBills noBills = deliveryService.getNobillBykey(acceptanceNoBills.getDeliveryno());
                                                    if (noBills != null) {
                                                        JOptionPane.showMessageDialog(null, "已存在的单号，卸油请前往出库单查询页面。", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                                                        return;
                                                    }
                                                    deliveryService.insertNobills(acceptanceNoBills);


                                                } else {
                                                    // 保存出库单
                                                    deliveryService = (IAcceptanceDeliveryService) Context.getInstance().getBean("acceptanceDeliveryServiceImpl");
                                                    deliveryService.insert(bill);
                                                }
                                                // 调用卸油流程
                                                if (Main.jhys == null) {
                                                    Main.jhys = new JhysPage(JhysPage.ENTERTYPE_SGLR);
                                                }
                                                //endregion

                                                Main.jhys.setCkdxx(bill, true);
                                                Main.jhys.initOilTankInfo();
                                            } catch (Exception e1) {
                                                Toolkit.getDefaultToolkit().beep();
                                                JOptionPane.showMessageDialog(frame, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                                                return;
                                            }
                                            Main.jhys.getFrame().setVisible(true);
                                            frame.setVisible(false);
                                            frame.dispose();
                                        }
                                        if(response==-1){
                                            //nothing
                                            return;
                                        }



                                    }
                                }

        );
        //endregion

        //region window closing listener
        frame.addWindowListener(new

                                        WindowAdapter() {
                                            public void windowClosing(WindowEvent e) {
                                                frame.dispose();
                                            }
                                        }

        );
        //endregion

        }

    private void setdefault(){
        xyBtn.setEnabled(false);
        ypBox.removeAllItems();
        ypBox.setEnabled(false);
        yfssField.setText("");
        yfssField.setEnabled(false);

        yfslField.setText("");
        yfslField.setEnabled(false);

        cphmField.setText("");
        cphmField.setEnabled(false);

        fyykField.setText("");
        fyykField.setEnabled(false);

        mdyzField.setText("");
        mdyzField.setEnabled(false);
    }
    }
