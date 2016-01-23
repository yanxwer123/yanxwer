package com.kld.app.view.main;

import com.kld.app.service.*;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Common;
import com.kld.app.util.Constant;
import com.kld.app.util.SysConfig;
import com.kld.app.view.acceptance.*;
import com.kld.app.view.alarm.*;
import com.kld.app.view.daily.*;
import com.kld.app.view.monitor.SdkcPage;
import com.kld.app.view.monitor.TankGunPumpCodeInformat;
import com.kld.app.view.sysmanage.*;
import com.kld.gsm.ATG.dao.AlarmMeasureLeakDao;
import com.kld.gsm.ATG.domain.*;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.ATGDevice.atg_stock_data_out_t;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.CapacTabBMsg;
import com.kld.gsm.Socket.protocol.CapacTabMsg;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import com.kld.gsm.util.V20Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

;

//
public class TablePanel extends JPanel implements Watcher {
    public JButton curBtn = new JButton();

    private static final Logger LOG = Logger.getLogger(TablePanel.class);
    JTabbedPane tabbedpane;
    JPanel pan1 = new JPanel();
    JPanel pan2 = new JPanel();
    JPanel pan3 = new JPanel();
    JPanel pan4 = new JPanel();
    JPanel pan5 = new JPanel();
    JPanel centerPanel;
    AlmPanel almPanel;
    private JhysPageWhdysFrame whdys;
    private JhysPage psdxx;
    private GlwhdPage whdPage;
    private JhyscxPageDetailFrame ckjhd;
    /* protected CkdcxPage ckdcxPage=Main.ckdcxPage;*/
    private JhyscxPage jhyscxPage;
    private StartAlarmFrame startAlarmFrame;//
    private ProbeParPanel probeParPanel;
    OilExcep oilExcep = new OilExcep();
    CubagePanel cubagePanel = new CubagePanel();
    YzbbPage yzbbPage = new YzbbPage();
    TankGunPumpCodeInformat tankGunPumpCodeInformat = new TankGunPumpCodeInformat();
    @Autowired
    private SysmanageService sysmanageService;

    int curtag = 0;
    Color curtagColor = Color.WHITE;// new Color(Integer.decode("#999999"));

    private void SwitchTabColor(int i) {
        tabbedpane.setForegroundAt(curtag, new Color(Integer.decode("#999999")));
        curtag = i;
        tabbedpane.setForegroundAt(curtag, curtagColor);
    }

    public TablePanel(final JPanel centerPanel) {

        //注册观察者开始
        Main.watch.addWetcher("A", this);
        this.centerPanel = centerPanel;
        tabbedpane = new PerTabbedPane();
        tabbedpane.setUI(new PerTabbedPaneUI());
        tabbedpane.setBackground(new Color(Integer.decode(Constant.TAB_TOP_BG)));
        tabbedpane.setForeground(new Color(Integer.decode("#999999")));
        tabbedpane.setFont(Constant.TAB_FONT);

        tabbedpane.setBounds(0, 10, 800, 115);
        tabbedpane.setBorder(null);
        tabbedpane.setTabPlacement(JTabbedPane.TOP);
        tabbedpane.addTab("实时监控", pan1);
        tabbedpane.addTab("进货验收", pan2);
        tabbedpane.addTab("日常运行", pan3);
        tabbedpane.addTab("报警提醒", pan4);
        tabbedpane.addTab("系统管理", pan5);
//        if (Main.menuList.contains("ssjk")) {
//            tabbedpane.addTab("实时监控", pan1);
//        }
//        if (Main.menuList.contains("jhys")) {
//            tabbedpane.addTab("进货验收", pan2);
//
//        }
//        if (Main.menuList.contains("rcyx")) {
//            tabbedpane.addTab("日常运行", pan3);
//        }
//        if (Main.menuList.contains("bjtx")) {
//            tabbedpane.addTab("报警提醒", pan4);
//        }
//        if (Main.menuList.contains("xtsz")) {
//            tabbedpane.addTab("系统管理", pan5);
//        }

        tabbedpane.setSize(800, 120);
        tabbedpane.setVisible(true);
        SwitchTabColor(0);
        initPan1();
        initPan2();
        initPan3();
        initPan4();
        initPan5();
        //region 主页初始化
        tabbedpane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (curBtn != null) curBtn.setSelected(false);
                switch (selectedIndex) {
                    case 0:
                        Main.setStatus("实时监控");
                        centerPanel.removeAll();
                        centerPanel.repaint();
                        SwitchTabColor(0);
                        if (Main.menuList.contains("ssjk")) {
                            tankGunPumpCodeInformat.setPanel(centerPanel, true);
                        }
                        break;

                    case 1:
                        tankGunPumpCodeInformat.setPanel(centerPanel, false);
                        Main.setStatus("进货验收");
                        centerPanel.removeAll();
                        centerPanel.repaint();
                        SwitchTabColor(1);
                        if (Main.menuList.contains("jhys_ckdcx")) {
                            if (Main.ckdcxPage == null) {
                                Main.ckdcxPage = new CkdcxPage();
                            }

                            try {
                                Main.ckdcxPage.setPanel(centerPanel);
                                if (psdxx != null) {
                                    psdxx.getFrame().dispose();
                                }
                                if (whdPage != null) {
                                    whdPage.getFrame().dispose();
                                }
                                if (whdys != null) {
                                    whdys.getFrame().dispose();
                                }
                                if (ckjhd != null) {
                                    ckjhd.getFrame().dispose();
                                }
                            } catch (Exception e1) {
                                Toolkit.getDefaultToolkit().beep();
                                JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                            }
                        } /*else {
                            JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有出库单查询权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                        }*/
                        break;
                    case 2:
                        //停止请求
                        Main.setStatus("日常运行");
                        tankGunPumpCodeInformat.setPanel(centerPanel, false);
                        centerPanel.removeAll();
                        centerPanel.repaint();
                        SwitchTabColor(2);
                        if (Main.menuList.contains("rcyx_yzbb")) {
                            yzbbPage.setPanel(centerPanel);
                        }/* else {
                            JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有油站班报权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                        }*/
                        break;
                    case 3:
                        Main.setStatus("报警提醒");
                        tankGunPumpCodeInformat.setPanel(centerPanel, false);
                        centerPanel.removeAll();
                        centerPanel.repaint();
                        SwitchTabColor(3);
                        if (Main.menuList.contains("bjtx_kcyj")) {
                            try {
                                new StockAlarm().setPanel(centerPanel);
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } /*else {
                            JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有查看库存预警权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                        }*/
                        break;
                    case 4:
                        Main.setStatus("系统设置");
                        tankGunPumpCodeInformat.setPanel(centerPanel, false);
                        centerPanel.removeAll();
                        centerPanel.repaint();
                        SwitchTabColor(4);
                        if (Main.menuList.contains("xtsz_ywysz")) {
                            new LiquidPanel().setPanel(centerPanel);
                        } /*else {
                            JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                        }*/
                        break;
                }
            }
        });

        //endregion
    }

    //region 系统管理
    private void initPan5() {

        pan5.setLayout(null);
        pan5.setPreferredSize(new Dimension(800, 115));

        //region 液位仪设置
        final JButton ywyszbutton = new JButton();

        ywyszbutton.setBorderPainted(false);
        ywyszbutton.setContentAreaFilled(false);
        ywyszbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        ywyszbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        ywyszbutton.setIcon(Common.createImageIcon(this.getClass(), "search.png"));
        ywyszbutton.setBounds(30, 13, 32, 32);
        pan5.add(ywyszbutton);

        JLabel ywyszLabel = new JLabel("液位仪设置");
        ywyszLabel.setFont(Constant.BOTTOM_FONT);
        ywyszLabel.setBounds(22, 45, 72, 12);
        ywyszLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(ywyszLabel);

        JLabel sbjcxLabel = new JLabel("查看");
        sbjcxLabel.setFont(Constant.BOTTOM_FONT);
        sbjcxLabel.setBounds(92, 33, 36, 12);
        sbjcxLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(sbjcxLabel);
        //液位仪设备基础信息查看
        sbjcxLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("xtsz_ywysz")) {
                    Main.setStatus("查看液位仪设备基础信息");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    new SbjcxxcxPage().setPanel(centerPanel);
                }
                else {
                    JOptionPane.showMessageDialog(null, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JLabel sbjhqLabel = new JLabel("获取");
        sbjhqLabel.setFont(Constant.BOTTOM_FONT);
        sbjhqLabel.setBounds(92, 18, 36, 12);
        sbjhqLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(sbjhqLabel);
        SbjcxxhqMouseListener sbjcxxhqMouseListener = new SbjcxxhqMouseListener();
        sbjhqLabel.addMouseListener(sbjcxxhqMouseListener);
        //endregion

        //region 探棒设置
        final JButton tbjzcsszbutton = new JButton();
        tbjzcsszbutton.setBorderPainted(false);
        tbjzcsszbutton.setContentAreaFilled(false);
        tbjzcsszbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        tbjzcsszbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        tbjzcsszbutton.setIcon(Common.createImageIcon(this.getClass(), "search.png"));
        tbjzcsszbutton.setBounds(140, 13, 32, 32);
        pan5.add(tbjzcsszbutton);

        JLabel tbjzcsszLabel = new JLabel("探棒设置");
        tbjzcsszLabel.setFont(Constant.BOTTOM_FONT);
        tbjzcsszLabel.setBounds(130, 45, 72, 12);
        tbjzcsszLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(tbjzcsszLabel);


        JLabel xzLabel = new JLabel("添加");
        xzLabel.setFont(Constant.BOTTOM_FONT);
        xzLabel.setBounds(190, 18, 36, 12);
        xzLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(xzLabel);

        //探棒设置
        xzLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("xtsz_tbjzcssz")) {
                    if (probeParPanel != null) {
                        Main.setStatus("探棒校正参数设置");
                        JDialog framesys = new JDialog();
                        framesys.setResizable(false);
                        framesys.setLayout(null);
                        framesys.setTitle("探棒设置");
                        Main.setCenter(framesys);
                        ProbeParOptionPanel panel = new ProbeParOptionPanel();
                        panel.setPanel(probeParPanel, framesys, centerPanel);
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JLabel glLabel = new JLabel("修改");
        glLabel.setFont(Constant.BOTTOM_FONT);
        glLabel.setBounds(190, 33, 36, 12);
        glLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(glLabel);
        //修改探棒设置
        if (Main.menuList.contains("xtsz_ybjsz")) {

            glLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (probeParPanel == null) {
                        JOptionPane.showMessageDialog(centerPanel, "请选择一条探棒设置信息", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    try {
                        Main.setStatus("修改探棒设置");
                        JDialog framesys = new JDialog();
                        framesys.setLayout(null);
                        framesys.setTitle("探棒设置修改");
                        framesys.setResizable(false);
                        Main.setCenter(framesys);
                        ProbeParOptionPanel panel = new ProbeParOptionPanel();
                        panel.setPanel2(probeParPanel, framesys, centerPanel);
                    } catch (Exception e1) {
                        e1.printStackTrace();

                    }
                }
            });
        } else {
            glLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        JLabel scLabel = new JLabel("删除");
        scLabel.setFont(Constant.BOTTOM_FONT);
        scLabel.setBounds(190, 48, 36, 12);
        scLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(scLabel);
        //删除探棒设置信息
        if (Main.menuList.contains("xtsz_tbjzcssz")) {

            scLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (probeParPanel == null) {
                        JOptionPane.showMessageDialog(centerPanel, "请选择一条探棒设置信息", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    try {
                        int option = JOptionPane.showConfirmDialog(centerPanel, "是否删除？", "信息提示", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION)
                            probeParPanel.deleteInfo();
                    } catch (Exception e1) {
                        e1.printStackTrace();

                    }
                    Main.setStatus("探棒设置信息已删除");
                }
            });
        } else {
            scLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

                }
            });
        }

        //endregion

        //region 容积表
        final JButton rjbbutton = new JButton();
        rjbbutton.setBorderPainted(false);
        rjbbutton.setContentAreaFilled(false);
        rjbbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        rjbbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        rjbbutton.setIcon(Common.createImageIcon(this.getClass(), "search.png"));
        rjbbutton.setBounds(250, 13, 32, 32);
        pan5.add(rjbbutton);

        JLabel rjbLabel = new JLabel("容积表");
        rjbLabel.setFont(Constant.BOTTOM_FONT);
        rjbLabel.setBounds(250, 45, 72, 12);
        rjbLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(rjbLabel);

 /*
        JButton fgxbutton3 = new JButton();
        fgxbutton3.setBorderPainted(false);
        fgxbutton3.setContentAreaFilled(false);
        fgxbutton3.setIcon(Common.createImageIcon(this.getClass(), "fgx.jpg"));
        fgxbutton3.setBounds(290, 53, 140, 3);
        pan5.add(fgxbutton3);

        JLabel rjbLabel1 = new JLabel("容积表");
        rjbLabel1.setFont(Constant.BOTTOM_FONT);
        rjbLabel1.setBounds(290, 59, 96, 12);
        rjbLabel1.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(rjbLabel1);*/


        JLabel xzLabel2 = new JLabel("下载");
        xzLabel2.setFont(Constant.BOTTOM_FONT);
        xzLabel2.setBounds(300, 18, 36, 12);
        xzLabel2.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(xzLabel2);
        RjbhqMouseListener rjbhqMouseListener = new RjbhqMouseListener(cubagePanel, centerPanel);
        xzLabel2.addMouseListener(rjbhqMouseListener);

        JLabel glLabel2 = new JLabel("设置");
        glLabel2.setFont(Constant.BOTTOM_FONT);
        glLabel2.setBounds(300, 33, 36, 12);
        glLabel2.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(glLabel2);

        JLabel rjbckLabel = new JLabel("查看");
        rjbckLabel.setFont(Constant.BOTTOM_FONT);
        rjbckLabel.setBounds(300, 48, 36, 12);
        rjbckLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(rjbckLabel);
        RjbckMouseListener rjbckMouseListener = new RjbckMouseListener(cubagePanel);
        rjbckLabel.addMouseListener(rjbckMouseListener);
        //endregion

        //region 预报警设置
        final JButton ybjszbutton = new JButton();
        ybjszbutton.setBorderPainted(false);
        ybjszbutton.setContentAreaFilled(false);
        ybjszbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        ybjszbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        ybjszbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        ybjszbutton.setIcon(Common.createImageIcon(this.getClass(), "search.png"));
        ybjszbutton.setBounds(368, 13, 32, 32);
        pan5.add(ybjszbutton);

        JLabel ybjszLabel = new JLabel("预报警设置");
        ybjszLabel.setFont(Constant.BOTTOM_FONT);
        ybjszLabel.setBounds(360, 45, 72, 12);
        ybjszLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(ybjszLabel);


        JLabel glLabel1 = new JLabel("下载");
        glLabel1.setFont(Constant.BOTTOM_FONT);

        glLabel1.setBounds(430, 18, 36, 12);
        glLabel1.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(glLabel1);

        //预报警信息下载--超时容错处置？？
        glLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("xtsz_ybjsz")) {
                    try {
                        Main.setStatus("下载预报警设置信息");
                        if (sysmanageService == null) {
                            sysmanageService = Context.getInstance().getBean(SysmanageService.class);
                        }
                        SysManageDepartment dept = sysmanageService.getdeptinfo();
                        int ret = sysmanageService.GetAlarmParbackInt(SysConfig.regmoteIp(), dept.getSinopecnodeno());
                        //ybjszbutton.doClick();
                        centerPanel.removeAll();
                        centerPanel.repaint();
                        curBtn.setSelected(false);
                        curBtn = ybjszbutton;
                        curBtn.setSelected(true);
                        almPanel = new AlmPanel();
                        almPanel.setPanel(centerPanel);
                        JOptionPane.showMessageDialog(null, "下载到" + ret + "条记录", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, "下载预报警设置信息失败", "信息提示", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

                }

            }
        });

        JLabel glLabel3 = new JLabel("设置");
        glLabel3.setFont(Constant.BOTTOM_FONT);

        glLabel3.setBounds(430, 33, 36, 12);
        glLabel3.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(glLabel3);
        //预报警信息设置
        if (Main.menuList.contains("xtsz_ybjsz")) {

            glLabel3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Main.setStatus("预报警信息设置");
                    JDialog dialog = new JDialog();
                    dialog.setLayout(null);
                    dialog.setTitle("预报警设置");
                    dialog.setModal(true);
                    AlmOptionPanel panel = new AlmOptionPanel();
                    panel.setSelectedRowID(almPanel.getId());
                    panel.setPanel(dialog);


                }
            });
        } else {
            glLabel3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

                }
            });
        }

        //endregion

        //region 注册
        final JButton yzzcbutton = new JButton();
        yzzcbutton.setBorderPainted(false);
        yzzcbutton.setContentAreaFilled(false);
        yzzcbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        yzzcbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        yzzcbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        yzzcbutton.setIcon(Common.createImageIcon(this.getClass(), "search.png"));
        yzzcbutton.setBounds(470, 13, 32, 32);
//        yzzcbutton.setBounds(650, 5, 32, 32);
        pan5.add(yzzcbutton);
        YzzcMouseListener yzzcMouseListener = new YzzcMouseListener();
        yzzcbutton.addMouseListener(yzzcMouseListener);

        JLabel yzzcLabel = new JLabel("初始化");
        yzzcLabel.setFont(Constant.BOTTOM_FONT);
        yzzcLabel.setBounds(480, 45, 72, 12);
        yzzcLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan5.add(yzzcLabel);

//endregion

        pan5.add(ywyszbutton);
        ywyszbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("xtsz_ywysz")) {
                    Main.setStatus("液位仪设置");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = ywyszbutton;
                    ywyszbutton.setSelected(true);
                    new LiquidPanel().setPanel(centerPanel);
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        pan5.add(tbjzcsszbutton);
        if (Main.menuList.contains("xtsz_tbjzcssz")) {

            tbjzcsszbutton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Main.setStatus("探棒设置");
                    probeParPanel = new ProbeParPanel();
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = tbjzcsszbutton;
                    curBtn.setSelected(true);

                    probeParPanel.setPanel(centerPanel);

                }
            });
        } else {
            tbjzcsszbutton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

                }
            });
        }

        pan5.add(rjbbutton);
        if (Main.menuList.contains("xtsz_rjb")) {

            rjbbutton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Main.setStatus("容积表查看");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = rjbbutton;
                    curBtn.setSelected(true);
                    cubagePanel.setPanel(centerPanel);
                }
            });
        } else {
            rjbbutton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

                }
            });
        }


        pan5.add(ybjszbutton);
        if (Main.menuList.contains("xtsz_ybjsz")) {

            ybjszbutton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Main.setStatus("预报警设置信息查看");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = ybjszbutton;
                    curBtn.setSelected(true);
                    almPanel = new AlmPanel();
                    almPanel.setPanel(centerPanel);
                }
            });
        } else {
            ybjszbutton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

                }
            });
        }
        // pan5.add(xtzdszbutton);
        MouseListener glLabel2MouseListener = new GlLabel2MouseListener(cubagePanel);

        glLabel2.addMouseListener(glLabel2MouseListener);

    }

    //endregion
    //region 报警提醒
    private void initPan4() {
        pan4.setLayout(null);
        pan4.setPreferredSize(new Dimension(800, 115));


        final JButton kcyjbutton = new JButton();
        kcyjbutton.setBorderPainted(false);
        kcyjbutton.setContentAreaFilled(false);
        kcyjbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        kcyjbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        kcyjbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        kcyjbutton.setIcon(Common.createImageIcon(this.getClass(), "warnning.png"));
        kcyjbutton.setBounds(30, 13, 32, 32);
        pan4.add(kcyjbutton);

        JLabel kcyjLabel = new JLabel("库存预警");
        kcyjLabel.setFont(Constant.BOTTOM_FONT);
        kcyjLabel.setBounds(22, 45, 72, 12);
        kcyjLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(kcyjLabel);


        final JButton sbgzbutton = new JButton();
        sbgzbutton.setBorderPainted(false);
        sbgzbutton.setContentAreaFilled(false);
        sbgzbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        sbgzbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        sbgzbutton.setIcon(Common.createImageIcon(this.getClass(), "warnning.png"));
        sbgzbutton.setBounds(97, 13, 32, 32);
        pan4.add(sbgzbutton);

        JLabel jhysLabel = new JLabel("设备故障");
        jhysLabel.setFont(Constant.BOTTOM_FONT);
        jhysLabel.setBounds(87, 45, 72, 12);
        jhysLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(jhysLabel);

        final JButton txyjbutton = new JButton();
        txyjbutton.setBorderPainted(false);
        txyjbutton.setContentAreaFilled(false);
        txyjbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        txyjbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        txyjbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        txyjbutton.setIcon(Common.createImageIcon(this.getClass(), "warnning.png"));
        txyjbutton.setBounds(165, 13, 32, 32);
        pan4.add(txyjbutton);

        JLabel txyjLabel = new JLabel("脱销预警");
        txyjLabel.setFont(Constant.BOTTOM_FONT);
        txyjLabel.setBounds(155, 45, 72, 12);
        txyjLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(txyjLabel);


        final JButton qcgcdbbutton = new JButton();
        qcgcdbbutton.setBorderPainted(false);
        qcgcdbbutton.setContentAreaFilled(false);
        qcgcdbbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        qcgcdbbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        qcgcdbbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        qcgcdbbutton.setIcon(Common.createImageIcon(this.getClass(), "warnning.png"));
        qcgcdbbutton.setBounds(244, 13, 32, 32);
        pan4.add(qcgcdbbutton);

        JLabel qcgcdbLabel = new JLabel("枪出罐出对比");
        qcgcdbLabel.setFont(Constant.BOTTOM_FONT);
        qcgcdbLabel.setBounds(222, 45, 72, 12);
        qcgcdbLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(qcgcdbLabel);

        final JButton jyshbutton = new JButton();
        jyshbutton.setBorderPainted(false);
        jyshbutton.setContentAreaFilled(false);
        jyshbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        jyshbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        jyshbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        jyshbutton.setIcon(Common.createImageIcon(this.getClass(), "warnning.png"));
        jyshbutton.setBounds(332, 13, 32, 32);
        pan4.add(jyshbutton);

        JLabel jyshLabel = new JLabel("进油损耗预警");
        jyshLabel.setFont(Constant.BOTTOM_FONT);
        jyshLabel.setBounds(312, 45, 72, 12);
        jyshLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(jyshLabel);

        final JButton jjbsybutton = new JButton();
        jjbsybutton.setBorderPainted(false);
        jjbsybutton.setContentAreaFilled(false);
        jjbsybutton.setPressedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        jjbsybutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        jjbsybutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        jjbsybutton.setIcon(Common.createImageIcon(this.getClass(), "warnning.png"));
        jjbsybutton.setBounds(432, 13, 32, 32);
        pan4.add(jjbsybutton);

        JLabel jjbsyLabel = new JLabel("交接班损溢预警");
        jjbsyLabel.setFont(Constant.BOTTOM_FONT);
        jjbsyLabel.setBounds(405, 45, 84, 12);
        jjbsyLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(jjbsyLabel);

        final JButton rjsybutton = new JButton();
        rjsybutton.setBorderPainted(false);
        rjsybutton.setContentAreaFilled(false);
        rjsybutton.setPressedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        rjsybutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        rjsybutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        rjsybutton.setIcon(Common.createImageIcon(this.getClass(), "warnning.png"));
        rjsybutton.setBounds(528, 13, 32, 32);
        pan4.add(rjsybutton);

        JLabel rjsyLabel = new JLabel("日结损溢预警");
        rjsyLabel.setFont(Constant.BOTTOM_FONT);
        rjsyLabel.setBounds(510, 45, 72, 12);
        rjsyLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(rjsyLabel);

        final JButton clbutton = new JButton();
        clbutton.setBorderPainted(false);
        clbutton.setContentAreaFilled(false);
        clbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        clbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        clbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "warnning-.png"));
        clbutton.setIcon(Common.createImageIcon(this.getClass(), "warnning.png"));
        clbutton.setBounds(605, 13, 32, 32);
        pan4.add(clbutton);
        JLabel clLabel = new JLabel("静态液位");
        clLabel.setFont(Constant.BOTTOM_FONT);
        clLabel.setBounds(600, 45, 72, 12);
        clLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(clLabel);


        JLabel xzLabel = new JLabel("启动");
        xzLabel.setFont(Constant.BOTTOM_FONT);
        xzLabel.setBounds(660, 18, 72, 12);
        xzLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(xzLabel);

        JLabel glLabel = new JLabel("结束");
        glLabel.setFont(Constant.BOTTOM_FONT);
        glLabel.setBounds(660, 33, 72, 12);
        glLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(glLabel);


        JLabel scLabel = new JLabel("查看");
        scLabel.setFont(Constant.BOTTOM_FONT);
        scLabel.setBounds(660, 48, 72, 12);
        scLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan4.add(scLabel);

        //库存预警

        kcyjbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("bjtx_kcyj")) {
                    Main.setStatus("库存预警信息查看");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = kcyjbutton;
                    curBtn.setSelected(true);
                    try {
                        new StockAlarm().setPanel(centerPanel);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //设备报警
        sbgzbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("bjtx_sbgz")) {
                    Main.setStatus("设备报警信息查看");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = sbgzbutton;
                    curBtn.setSelected(true);
                    try {
                        new EquipFault().setPanel(centerPanel);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //脱销预警
        txyjbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("bjtx_txyj")) {
                    Main.setStatus("脱销预警");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = txyjbutton;
                    curBtn.setSelected(true);
                    try {
                        new StockoutAlarm().setPanel(centerPanel);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //枪出罐出对比/动态液位异常
        qcgcdbbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("bjtx_qcgcdb")) {
                    Main.setStatus("枪出罐出对比");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = qcgcdbbutton;
                    curBtn.setSelected(true);
                    try {
                        new GunsTankComparation().setPanel(centerPanel);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //进油损耗预警
        jyshbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("bjtx_jyshyj")) {
                    Main.setStatus("进油损溢预警");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = jyshbutton;
                    curBtn.setSelected(true);
                    try {
                        new OutofOilAlarm().setPanel(centerPanel);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //交接班损益预警
        jjbsybutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("bjtx_jjbsyyj")) {
                    Main.setStatus("交接班损溢预警");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = jjbsybutton;
                    curBtn.setSelected(true);
                    try {
                        new HandDescAlarm().setPanel(centerPanel);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        //日结损溢预警
        rjsybutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("bjtx_rjsyyj")) {
                    Main.setStatus("日结损溢预警");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = rjsybutton;
                    curBtn.setSelected(true);
                    try {
                        new DayDescAlarm().setPanel(centerPanel);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //region 静态液位异常
        clbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("bjtx_ywyc")) {
                    Main.setStatus("静态液位异常查看");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = clbutton;
                    curBtn.setSelected(true);
                    try {
                        //Main.oilExcep=oilExcep;
                        if (Main.oilExcep == null) {
                            Main.oilExcep = new OilExcep();
                        }
                        Main.oilExcep.setPanel(centerPanel);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        xzLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //启动静态液位异常测试
                if (Main.menuList.contains("bjtx_ywyc")) {
               /* centerPanel.removeAll();
                centerPanel.repaint();*/
                    if (Main.oilExcep == null) {
                        Main.oilExcep = new OilExcep();

                    }
                    //region clbutton event
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = clbutton;
                    curBtn.setSelected(true);
                    try {
                        //Main.oilExcep=oilExcep;
                        if (Main.oilExcep == null) {
                            Main.oilExcep = new OilExcep();
                        }
                        Main.oilExcep.setPanel(centerPanel);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //endregion
                    //clbutton.doClick();
                    startAlarmFrame = new StartAlarmFrame();
                    try {
                        Main.setStatus("启动静态液位异常检测");
                        startAlarmFrame.getFrame().setModal(true);
                        Main.setCenter(startAlarmFrame.getFrame());
                        startAlarmFrame.getFrame().setVisible(true);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JsyjMouseListener Jsyj = new JsyjMouseListener(oilExcep);
        glLabel.addMouseListener(Jsyj);

        CkbgMouseListener Ckbg = new CkbgMouseListener(oilExcep);
        scLabel.addMouseListener(Ckbg);
        //endregion

    }

    //endregion
    //region 日常运行
    private void initPan3() {
        pan3.setLayout(null);
        pan3.setPreferredSize(new Dimension(800, 115));

        final JButton yzbbbutton = new JButton();
        yzbbbutton.setBorderPainted(false);
        yzbbbutton.setContentAreaFilled(false);
        yzbbbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "Outbound-order-.png"));
        yzbbbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "Outbound-order-.png"));
        yzbbbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "Outbound-order-.png"));
        yzbbbutton.setIcon(Common.createImageIcon(this.getClass(), "Outbound-order.png"));
        yzbbbutton.setBounds(30, 13, 32, 32);
        pan3.add(yzbbbutton);
        //油站班报
        yzbbbutton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("rcyx_yzbb")) {
                    Main.setStatus("油站班报");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = yzbbbutton;
                    curBtn.setSelected(true);
                    yzbbPage.setPanel(centerPanel);
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JLabel yzbbLabel = new JLabel("油站班报");
        yzbbLabel.setFont(Constant.BOTTOM_FONT);
        yzbbLabel.setBounds(22, 45, 72, 12);
        yzbbLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan3.add(yzbbLabel);


        JLabel yzbbLabel2 = new JLabel("查看");
        yzbbLabel2.setFont(Constant.BOTTOM_FONT);
        yzbbLabel2.setBounds(72, 18, 32, 12);
        yzbbLabel2.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan3.add(yzbbLabel2);
        RjbckMouseListener rjbckMouseListener = new RjbckMouseListener(cubagePanel);
        CkMouseListener ckMouseListener = new CkMouseListener(yzbbPage);
        yzbbLabel2.addMouseListener(ckMouseListener);

        final JButton jyjlbutton = new JButton();
        jyjlbutton.setBorderPainted(false);
        jyjlbutton.setContentAreaFilled(false);
        jyjlbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "7-.png"));
        jyjlbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "7-.png"));
        jyjlbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "7-.png"));
        jyjlbutton.setIcon(Common.createImageIcon(this.getClass(), "7.png"));
        jyjlbutton.setBounds(125, 13, 32, 32);
        pan3.add(jyjlbutton);
        //交易记录
        jyjlbutton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("rcyx_jyjl")) {
                    Main.setStatus("交易记录查询");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = jyjlbutton;
                    curBtn.setSelected(true);
                    new JyjlPage().setPanel(centerPanel);
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JLabel jyjlLabel = new JLabel("交易记录");
        jyjlLabel.setFont(Constant.BOTTOM_FONT);
        jyjlLabel.setBounds(120, 45, 72, 12);
        jyjlLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan3.add(jyjlLabel);


        final JButton rphbbutton = new JButton();
        rphbbutton.setBorderPainted(false);
        rphbbutton.setContentAreaFilled(false);
        rphbbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "copy-.png"));
        rphbbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "copy-.png"));
        rphbbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "copy-.png"));
        rphbbutton.setIcon(Common.createImageIcon(this.getClass(), "copy.png"));
        rphbbutton.setBounds(220, 13, 32, 32);
        pan3.add(rphbbutton);
        //日平衡表
        rphbbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("rcyx_rphb")) {
                    Main.setStatus("日平衡表");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = rphbbutton;
                    curBtn.setSelected(true);
                    new RphbPage().setPanel(centerPanel);
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JLabel rphbLabel = new JLabel("日平衡表");
        rphbLabel.setFont(Constant.BOTTOM_FONT);
        rphbLabel.setBounds(218, 45, 72, 12);
        rphbLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan3.add(rphbLabel);


        final JButton pd = new JButton();
        pd.setBorderPainted(false);
        pd.setContentAreaFilled(false);
        pd.setPressedIcon(Common.createImageIcon(this.getClass(), "copy-.png"));
        pd.setRolloverIcon(Common.createImageIcon(this.getClass(), "copy-.png"));
        pd.setSelectedIcon(Common.createImageIcon(this.getClass(), "copy-.png"));
        pd.setIcon(Common.createImageIcon(this.getClass(), "copy.png"));
        pd.setBounds(320, 13, 32, 32);
        pan3.add(pd);
        //盘点
        pd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("rcyx_rphb")) {
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    Main.watch.addWetcher("A", new PdPage());
                    GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
                    System.out.println("request[04]:" + gasMsg);
                    Main.CC.writeAndFlush(gasMsg);
                    curBtn.setSelected(false);
                    curBtn = pd;
                    curBtn.setSelected(true);
                    new PdPage().setPanel(centerPanel);
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JLabel pdLabel = new JLabel("盘点");
        pdLabel.setFont(Constant.BOTTOM_FONT);
        pdLabel.setBounds(320, 45, 72, 12);
        pdLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan3.add(pdLabel);
    }

    //endregion
    //region 进货验收
    private void initPan2() {
        pan2.setLayout(null);
        final JButton ckdcxbutton = new JButton();

        ckdcxbutton.setBorderPainted(false);
        ckdcxbutton.setContentAreaFilled(false);
        ckdcxbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "Outbound-order-.png"));
        ckdcxbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "Outbound-order-.png"));
        ckdcxbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "Outbound-order-.png"));
        ckdcxbutton.setIcon(Common.createImageIcon(this.getClass(), "Outbound-order.png"));
        ckdcxbutton.setBounds(30, 13, 32, 32);

        pan2.add(ckdcxbutton);

        JLabel ckdcxLabel = new JLabel("出库单查询");
        ckdcxLabel.setFont(Constant.BOTTOM_FONT);
        ckdcxLabel.setBounds(22, 45, 72, 12);
        ckdcxLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan2.add(ckdcxLabel);


        JLabel xzLabel = new JLabel("下载");
        xzLabel.setFont(Constant.BOTTOM_FONT);
        xzLabel.setBounds(100, 10, 40, 12);
        xzLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));

        pan2.add(xzLabel);

        JLabel glLabel = new JLabel("关联");
        glLabel.setFont(Constant.BOTTOM_FONT);
        glLabel.setBounds(100, 25, 40, 12);
        glLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan2.add(glLabel);

        JLabel scLabel = new JLabel("删除");
        scLabel.setFont(Constant.BOTTOM_FONT);
        scLabel.setBounds(100, 38, 40, 12);
        scLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan2.add(scLabel);

        final JButton jhysbutton = new JButton();
        jhysbutton.setBorderPainted(false);
        jhysbutton.setContentAreaFilled(false);
        jhysbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "Purchase-acceptance-.png"));
        jhysbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "Purchase-acceptance-.png"));
        jhysbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "Purchase-acceptance-.png"));
        jhysbutton.setIcon(Common.createImageIcon(this.getClass(), "Purchase-acceptance.png"));
        jhysbutton.setBounds(157, 13, 32, 32);
        pan2.add(jhysbutton);

        JLabel jhysLabel = new JLabel("进货验收");
        jhysLabel.setFont(Constant.BOTTOM_FONT);
        jhysLabel.setBounds(147, 45, 72, 12);
        jhysLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan2.add(jhysLabel);

        final JButton whdysbutton = new JButton();
        whdysbutton.setBorderPainted(false);
        whdysbutton.setContentAreaFilled(false);
        whdysbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "wuhuodanyanshou-.png"));
        whdysbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "wuhuodanyanshou-.png"));
        whdysbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "wuhuodanyanshou-.png"));
        whdysbutton.setIcon(Common.createImageIcon(this.getClass(), "wuhuodanyanshou.png"));
        whdysbutton.setBounds(222, 13, 32, 32);
        pan2.add(whdysbutton);

        JLabel whdysLabel = new JLabel("无货单验收");
        whdysLabel.setFont(Constant.BOTTOM_FONT);
        whdysLabel.setBounds(212, 45, 72, 12);
        whdysLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan2.add(whdysLabel);

        final JButton jhyscxbutton = new JButton();
        jhyscxbutton.setBorderPainted(false);
        jhyscxbutton.setContentAreaFilled(false);
        jhyscxbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        jhyscxbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        jhyscxbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "search-.png"));
        jhyscxbutton.setIcon(Common.createImageIcon(this.getClass(), "search.png"));
        jhyscxbutton.setBounds(320, 13, 32, 32);
        pan2.add(jhyscxbutton);

        JLabel jhyscxLabel = new JLabel("进货验收查询");
        jhyscxLabel.setFont(Constant.BOTTOM_FONT);
        jhyscxLabel.setBounds(300, 45, 72, 12);
        jhyscxLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan2.add(jhyscxLabel);

        JLabel ckLabel1 = new JLabel("查看");
        ckLabel1.setFont(Constant.BOTTOM_FONT);
        ckLabel1.setBounds(400, 10, 40, 12);
        ckLabel1.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan2.add(ckLabel1);

        JLabel glLabel1 = new JLabel("作废卸油");
        glLabel1.setFont(Constant.BOTTOM_FONT);
        glLabel1.setBounds(400, 25, 60, 12);
        glLabel1.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan2.add(glLabel1);

        JLabel glLabel2 = new JLabel("打印");
        glLabel2.setFont(Constant.BOTTOM_FONT);
        glLabel2.setBounds(400, 40, 60, 12);
        glLabel2.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan2.add(glLabel2);

        ckdcxbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("jhys_ckdcx")) {
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = ckdcxbutton;
                    curBtn.setSelected(true);
                    if (Main.ckdcxPage == null) {
                        Main.ckdcxPage = new CkdcxPage();
                    }
                    try {
                        Main.setStatus("出库单查询");
                        Main.ckdcxPage.setPanel(centerPanel);
                        if (psdxx != null) {
                            psdxx.getFrame().dispose();
                        }
                        if (whdPage != null) {
                            whdPage.getFrame().dispose();
                        }
                        if (whdys != null) {
                            whdys.getFrame().dispose();
                        }
                        if (ckjhd != null) {
                            ckjhd.getFrame().dispose();
                        }
                    } catch (Exception e1) {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        xzLabel.addMouseListener(new MouseAdapter() {

            /**
             * {@inheritDoc}
             *@description 下载按钮注册
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("jhys_ckdcx")) {
                    try {
                        Main.setStatus("下载出库单");
                        if (Main.ckdcxPage == null)
                            Main.ckdcxPage = new CkdcxPage();
                        Main.ckdcxPage.setPanel(centerPanel);
                        Main.ckdcxPage.DownLoadFromCenter();
                        //whdPage.getFrame().setVisible(true);
                    } catch (Exception e1) {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*
        * 关联无货单按钮事件注册
		* */

        glLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("jhys_ckdcx")) {
                    Main.setStatus("关联无货单");
                    if (Main.ckdcxPage == null) {
                        JOptionPane.showMessageDialog(centerPanel, "请点击出库单查询，选择一条出库单", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    Main.ckdcxPage.wlhdgl(null);

                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //作废进货验收

        glLabel1.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("jhys_jhyscx")) {
                    Main.setStatus("作废进货验收单");
                    if (jhyscxPage == null) {
                        JOptionPane.showMessageDialog(centerPanel, "请点击进货验收查询，选择一条进货验收单", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    try {
                        jhyscxPage.deleteJhdys();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //打印

        glLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("jhys_jhyscx")) {
                    Main.setStatus("打印出库单");
                    if (psdxx != null) {
                        psdxx.getFrame().dispose();
                    }
                    if (whdPage != null) {
                        whdPage.getFrame().dispose();
                    }
                    if (whdys != null) {
                        whdys.getFrame().dispose();
                    }
                    try {
                        if (jhyscxPage == null) {
                            centerPanel.removeAll();
                            centerPanel.repaint();
                            jhyscxPage = new JhyscxPage();
                            jhyscxPage.setPanel(centerPanel);
                        } else {
                            jhyscxPage.printJhdys();
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        //出库单查询-删除
        scLabel.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("jhys_ckdcx")) {
                    if (Main.ckdcxPage == null) {
                        JOptionPane.showMessageDialog(centerPanel, "请点击出库单查询，选择一条出库单", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    try {
                        Main.ckdcxPage.delete();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //进货验收
        jhysbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("jhys_jhys")) {
                    curBtn.setSelected(false);
                    curBtn = jhysbutton;
                    curBtn.setSelected(true);
                    try {
                        Main.setStatus("进货验收");
                        //  ckdcxPage.setPanel(centerPanel);
                        if (whdys != null) {
                            whdys.getFrame().dispose();
                        }
                        if (whdPage != null) {
                            whdPage.getFrame().dispose();
                        }
                        if (ckjhd != null) {
                            ckjhd.getFrame().dispose();
                        }

                        if (Main.ckdcxPage == null || Main.ckdcxPage.isVisible() == false) {
                            centerPanel.removeAll();
                            centerPanel.repaint();
                            Main.ckdcxPage = new CkdcxPage();
                            Main.ckdcxPage.setPanel(centerPanel);
                        }

                        if (psdxx == null || psdxx.getTranStatus()) {
                            psdxx = new JhysPage();
                            Main.jhys = psdxx;
                        }
                        Main.ckdcxPage.jhys(psdxx);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //无货单验收
        whdysbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              /*  centerPanel.removeAll();
                centerPanel.repaint();
				new CkdcxPage().setPanel(centerPanel);*/
                if (Main.menuList.contains("jhys_whdys")) {
                    Main.setStatus("无货单验收");
                    curBtn.setSelected(false);
                    curBtn = whdysbutton;
                    curBtn.setSelected(true);
                    if (psdxx != null) {
                        psdxx.getFrame().dispose();
                    }
                    if (whdPage != null) {
                        whdPage.getFrame().dispose();
                    }
                    if (ckjhd != null) {
                        ckjhd.getFrame().dispose();
                    }
                    if (whdys == null || !whdys.getFrame().isActive()) {
                        whdys = new JhysPageWhdysFrame();
                    }
                    whdys.getFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 进货验收查询
        jhyscxbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("jhys_jhyscx")) {
                    Main.setStatus("进货验收查询");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = jhyscxbutton;
                    curBtn.setSelected(true);
                    try {
                        jhyscxPage = new JhyscxPage();
                        jhyscxPage.setPanel(centerPanel);
                        jhyscxPage.LoadData();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (psdxx != null) {
                        psdxx.getFrame().dispose();
                    }
                    if (whdPage != null) {
                        whdPage.getFrame().dispose();
                    }
                    if (whdys != null) {
                        whdys.getFrame().dispose();
                    }
                    if (ckjhd != null) {
                        ckjhd.getFrame().dispose();
                    }
                    if (Main.ckdcxPage != null) {
                        //Main.ckdcxPage = null;
                    }

                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // 查看进货
        ckLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("jhys_jhyscx")) {
                    Main.setStatus("查看进货验收");
                    if (psdxx != null) {
                        psdxx.getFrame().dispose();
                    }
                    if (whdPage != null) {
                        whdPage.getFrame().dispose();
                    }
                    if (whdys != null) {
                        whdys.getFrame().dispose();
                    }
                    try {
                        if (jhyscxPage == null) {
                            centerPanel.removeAll();
                            centerPanel.repaint();
                            jhyscxPage = new JhyscxPage();
                            jhyscxPage.setPanel(centerPanel);
                        } else {
                            jhyscxPage.showdetail();
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(centerPanel, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }

    //endregion
    //region 实时监控
    private void initPan1() {
        if (Main.menuList.contains("ssjk_ssgqjk")) {
            centerPanel.removeAll();
            centerPanel.repaint();
            tankGunPumpCodeInformat.setPanel(centerPanel, true);
        }
        pan1.setLayout(null);
        pan1.setPreferredSize(new Dimension(800, 115));
        final JButton ssgqjkbutton = new JButton();
        ssgqjkbutton.setBorderPainted(false);
        ssgqjkbutton.setContentAreaFilled(false);
        ssgqjkbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "4--.png"));
        ssgqjkbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "4--.png"));
        ssgqjkbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "4--.png"));
        ssgqjkbutton.setIcon(Common.createImageIcon(this.getClass(), "4-.png"));
        ssgqjkbutton.setBounds(50, 13, 32, 32);

        pan1.add(ssgqjkbutton);

        JLabel ssgqjkLabel = new JLabel("实时罐枪监控");
        ssgqjkLabel.setFont(Constant.BOTTOM_FONT);//文字风格
        ssgqjkLabel.setBounds(30, 45, 72, 12);//文字宽度
        ssgqjkLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));//文字颜色
        pan1.add(ssgqjkLabel);


        JLabel zdkcLabel = new JLabel("时点库存");
        zdkcLabel.setFont(Constant.BOTTOM_FONT);
        zdkcLabel.setBounds(133, 45, 72, 12);
        zdkcLabel.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        pan1.add(zdkcLabel);

        final JButton zdkckbutton = new JButton();
        zdkckbutton.setBorderPainted(false);
        zdkckbutton.setContentAreaFilled(false);
        zdkckbutton.setPressedIcon(Common.createImageIcon(this.getClass(), "7-.png"));
        zdkckbutton.setRolloverIcon(Common.createImageIcon(this.getClass(), "7-.png"));
        zdkckbutton.setSelectedIcon(Common.createImageIcon(this.getClass(), "7-.png"));
        zdkckbutton.setIcon(Common.createImageIcon(this.getClass(), "7.png"));
        zdkckbutton.setBounds(140, 13, 32, 32);
        pan1.add(zdkckbutton);


        //实时监控的panel
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        // 实时罐枪数据及泵码信息

        ssgqjkbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("ssjk_ssgqjk")) {
                    Main.setStatus("实时监控");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = ssgqjkbutton;
                    curBtn.setSelected(true);
                    tankGunPumpCodeInformat.setPanel(centerPanel, true);
                } else {
                    JOptionPane.showMessageDialog(null, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        zdkckbutton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Main.menuList.contains("ssjk_sdkc")) {
                    Main.setStatus("时点库存查看");
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    curBtn.setSelected(false);
                    curBtn = zdkckbutton;
                    curBtn.setSelected(true);
                    new SdkcPage().setPanel(centerPanel);
                } else {
                    JOptionPane.showMessageDialog(null, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

    }
    //endregion

    public static void main(String args[]) {
//		JFrame frame = new JFrame() ;
//		frame.setLayout(null);
//		TablePanel panel=new TablePanel();
//		frame.add(panel.tabbedpane) ;
//		frame.setSize(800,600) ;
//		frame.setLocation(300,200) ;
//		frame.setVisible(true) ;
//		frame.addWindowListener(new WindowAdapter(){
//			public void windowClosing(WindowEvent a){
//				System.exit(1) ;
//			}
//		}) ;
    }

    //region 预报警设置--下发液位仪返回
    @Override
    public void update(GasMsg gasMsg) {
        if ("A15_10010".equals(gasMsg.getPid())) {
            ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            if ("1".equals(data.getResult())) {
                Main.setStatus("预报警设置下发液位仪失败");
                JOptionPane.showMessageDialog(null, "操作失败！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            } else if ("0".equals(data.getResult())) {
                Main.setStatus("预报警设置下发液位仪成功");
                JOptionPane.showMessageDialog(null, "操作成功！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    //endregion
}

//容积表设置
class GlLabel2MouseListener implements MouseListener, Watcher {
    static ZCTXFrame zcRJ = new ZCTXFrame();

    CubagePanel cubagePanel;

    public GlLabel2MouseListener() {
    }

    public GlLabel2MouseListener(CubagePanel cubagePanel) {
        this.cubagePanel = cubagePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Main.menuList.contains("xtsz_rjb")) {
            Main.watch.addWetcher("A", this);
            Object[] options = {"确定", "取消"};
            int selectRow = cubagePanel.probePartable.getSelectedRow();
            if (selectRow < 0) {
                JOptionPane.showMessageDialog(null, "请先选择一行记录！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int oilcanno = (Integer) cubagePanel.probePartable.getValueAt(selectRow, 0);
            String version = (String) cubagePanel.probePartable.getValueAt(selectRow, 1);
            String opername = Main.oprname;//"admin";// (String)cubagePanel.probePartable.getValueAt(selectRow,3);
            /*if (Main.USERMAP != null && Main.USERMAP.size() > 0) {
                opername = Main.USERMAP.get("oprname").toString();
            }*/
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cubagePanel.date = new Date();
            String now = sd.format(cubagePanel.date);
            int response = JOptionPane.showOptionDialog(cubagePanel,
                    "操作人：" + opername + "\n操作时间：" + now + "\n版本号：" + version + "\n油罐编号：" + oilcanno + "\n", "液位仪应用系统",
                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (response == 0) {
                SysManageCubage sysManageCubage = new SysManageCubage();
                sysManageCubage.setVersion(version);
                sysManageCubage.setOilcan(oilcanno);
                sysManageCubage.setSetman(opername);
                // Channel cc = Main.reLink();
                Main.setStatus("容积表设置中...");

                if (Main.CC == null) {
                    //System.out.println("Link Netty Server FAll");
                    JOptionPane.showMessageDialog(null, "连接失败！", "", JOptionPane.INFORMATION_MESSAGE);
                    Main.clearStatus();
                    return;
                } else {
                    SysManageCubageInfo sysManageCubageInfo = new SysManageCubageInfo();
                    sysManageCubageInfo.setVersion(sysManageCubage.getVersion());
                    sysManageCubageInfo.setOilcan(oilcanno);
                    //查询容积明细表
                    SysCubageService sysCubageService = (SysCubageService) (Context.getInstance().getBean("sysCubageService"));
                    java.util.List<SysManageCubageInfo> sysManageCubageInfoList = sysCubageService.selectCubageInfo(sysManageCubageInfo);
                    CapacTabMsg capacTabMsg = new CapacTabMsg();
                    capacTabMsg.setOilNo(sysManageCubage.getOilcan());
                    capacTabMsg.setOperation(1);//下发到液位仪
                    capacTabMsg.setStrVersion(sysManageCubage.getVersion());
                    ArrayList<CapacTabBMsg> sysManageCubageInfoArrayList = new ArrayList<CapacTabBMsg>();
                    for (SysManageCubageInfo s : sysManageCubageInfoList) {
                        CapacTabBMsg capacTabBMsg = new CapacTabBMsg();
                        capacTabBMsg.setHeight(s.getHeight());
                        capacTabBMsg.setLiter(s.getLiter());
                        sysManageCubageInfoArrayList.add(capacTabBMsg);
                    }
                    capacTabMsg.setCapacTabBMsgs(sysManageCubageInfoArrayList);
                    ArrayList<CapacTabMsg> list = new ArrayList<CapacTabMsg>();
                    list.add(capacTabMsg);
                    GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, list, Constants.PID_Code.A15_10009.toString());

                    // System.out.println("send:" + gasMsg.toString());
                    Main.CC.writeAndFlush(gasMsg);

                    zcRJ.setsInfo("正在设置容积表，请稍后...");
                    Main.setCenter(zcRJ);
                    zcRJ.setVisible(true);

                }
                return;
            }
        } else {

            JOptionPane.showMessageDialog(null, "用户[" +
                    Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
        }
        /*
        JFrame framesys = new JFrame("设置");
        SetCapacityTablePanel panel = new SetCapacityTablePanel(cubagePanel,framesys);
        framesys.setLayout(new BorderLayout());
        framesys.add(panel, BorderLayout.CENTER);
        framesys.setSize(300, 200);
        framesys.setLocation(600, 200);
        framesys.setVisible(true);
        */
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void update(GasMsg gasMsg) {
        if ("A15_10009".equals(gasMsg.getPid())) {
            ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            if ("1".equals(data.getResult())) {
                Main.setStatus("容积表设置失败，请重试！");
                JOptionPane.showMessageDialog(null, "容积表设置失败，请重试！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            } else if ("0".equals(data.getResult())) {
                int selectRow = cubagePanel.probePartable.getSelectedRow();
                int oilcanno = (Integer) cubagePanel.probePartable.getValueAt(selectRow, 0);
                String version = (String) cubagePanel.probePartable.getValueAt(selectRow, 1);
                String opername = "admin";
                if (Main.USERMAP != null && Main.USERMAP.size() > 0) {
                    opername = Main.USERMAP.get("oprname").toString();
                }
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SysManageCubage sysManageCubage = new SysManageCubage();
                sysManageCubage.setVersion(version);
                sysManageCubage.setOilcan(oilcanno);
                sysManageCubage.setSetman(opername);
                sysManageCubage.setSettime(cubagePanel.date);
                sysManageCubage.setInused(1);
                sysManageCubage.setSetstate(1);
                ISetCapacityTableService setCapacityTableService = Context.getInstance().getBean(ISetCapacityTableService.class);
                setCapacityTableService.updateByPrimaryKeySelective(sysManageCubage);
                Main.setStatus("容积表设置完成");
                JOptionPane.showMessageDialog(null, "操作成功！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                cubagePanel.cubageQueryBut.doClick(0);
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    zcRJ.setVisible(false);
                }
            });
        }
    }
}

//region 容积表查看
class RjbckMouseListener extends MouseAdapter {
    CubagePanel cubagePanel;
    SysCubageService sysCubageService = (SysCubageService) (Context.getInstance().getBean("sysCubageService"));

    public RjbckMouseListener(CubagePanel cubagePanel) {
        this.cubagePanel = cubagePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Main.menuList.contains("xtsz_rjb")) {
            Main.setStatus("容积表查看");
            int selectRow = cubagePanel.probePartable.getSelectedRow();
            if (selectRow < 0) {
                JOptionPane.showMessageDialog(null, "请先选择一行记录！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int oilcanno = (Integer) cubagePanel.probePartable.getValueAt(selectRow, 0);//罐号
            String version = (String) cubagePanel.probePartable.getValueAt(selectRow, 1);//版本号
            SysManageCubageInfoKey sysManageCubageInfo = new SysManageCubageInfoKey();
            sysManageCubageInfo.setVersion(version);
            sysManageCubageInfo.setOilcan(oilcanno);
            java.util.List<SysManageCubageInfo> sysManageCubageInfoList = sysCubageService.selectCubageInfo(sysManageCubageInfo);
            final String[] tableHeads = {
                    "高度(mm)",
                    "升数(L)"
            };
            Object[][] data = new Object[sysManageCubageInfoList.size()][tableHeads.length];
            for (int i = 0; i < sysManageCubageInfoList.size(); i++) {
                SysManageCubageInfo s = sysManageCubageInfoList.get(i);
                data[i][0] = s.getHeight();
                data[i][1] = s.getLiter();
            }
            AlarmTableModel model = new AlarmTableModel(tableHeads, data);
            JTable jTable = new JTable(model);
            jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jTable.getTableHeader().setReorderingAllowed(false);//不拖动
            CubageInfoPanel cubageInfoPanel = new CubageInfoPanel(cubagePanel);
            JDialog dialog = new JDialog();
            dialog.setTitle("容积表查看");
            dialog.setLayout(new BorderLayout());
//        JScrollPane scrollPane = new JScrollPane(jTable);
            dialog.add(cubageInfoPanel, BorderLayout.CENTER);
            dialog.setModal(true);
            dialog.setSize(500, 340);
            Main.setCenter(dialog);
            dialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "用户[" +
                    Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

        }
    }
}

//endregion
//注册，启动罐枪基础信息同步
class YzzcMouseListener extends MouseAdapter implements Watcher {

    static ZCTXFrame zc = new ZCTXFrame();


    @Override
    public void mouseClicked(MouseEvent e) {
        if (Main.menuList.contains("xtsz_rjb")) {
            Main.setStatus("正在初始化，请稍后...");
            //注册观察者开始
            YzzcMouseListener yzzc = new YzzcMouseListener();
            Main.watch.addWetcher("A", yzzc);
            System.out.println("qidong");


            //System.out.println("发送" + Thread.currentThread().getId());

            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10011.toString());
            System.out.println("send:" + gasMsg.toString());
            Main.CC.writeAndFlush(gasMsg);

            zc.setsInfo("正在初始化，请稍后...");
            Main.setCenter(zc);

            zc.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void update(GasMsg gasMsg) {
        if ("A15_10011".equals(gasMsg.getPid())) {
            JPanel YzzcJpanel = new JPanel();
            ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            //System.out.println("进入update返回");

            if ("1" == data.getResult()) {
                JOptionPane.showMessageDialog(YzzcJpanel, "初始化失败！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                Main.setStatus("初始化失败，请重试。");
                return;
            }
            JOptionPane.showMessageDialog(YzzcJpanel, "初始化成功！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            Main.setStatus("初始化成功。");
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    zc.setVisible(false);
                }
            });
            YzzcJpanel.setLayout(null);
            YzzcJpanel.setSize(800, 500);
            YzzcJpanel.setVisible(true);
        }
    }

}

//容积表获取
class RjbhqMouseListener extends MouseAdapter implements Watcher {
    CubagePanel cubagePanel;
    JPanel centerPanel;

    public RjbhqMouseListener(CubagePanel cubagePanel, JPanel centerPanel) {
        this.cubagePanel = cubagePanel;
        this.centerPanel = centerPanel;
    }


    //	Channel cc = TcpClient.getInstance().getChannel(Constant.IP, Constant.PORT);
    @Override
    public void mouseClicked(MouseEvent e) {
        if (Main.menuList.contains("xtsz_rjb")) {
            //调用方法，更新从省中心拿来的数据
            String ip = SysConfig.regmoteIp();
            //SysManageDic dic = Context.getInstance().getBean(SysManageDic.class);
            //dic.GetByCode("zxfwqdz");
            SysmanageService sysmanageService = Context.getInstance().getBean(SysmanageService.class);
            SysManageDepartment sysManageDepartment = sysmanageService.getdeptinfo();
            int ret = sysmanageService.GetCubgeByNodeNobackInt(ip, sysManageDepartment.getSinopecnodeno());
            if (ret > 0) {
                JOptionPane.showMessageDialog(null, "下载到" + ret + "条记录", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                cubagePanel.cubageQueryBut.doClick(0);
            } else {
                JOptionPane.showMessageDialog(null, "下载到0条记录！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(centerPanel, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

        }

    }

    //获取容积表信息
    @Override
    public void update(GasMsg gasMsg) {
        //System.out.println("进入获取的update方法1~~~~~~~~~~~~~~~~~~~");
        if ("A15_10009".equals(gasMsg.getPid())) {
            //System.out.println("A15_10009~~~~~~~~~~~~~~~~~~");
            ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            //System.out.println("data.getResult()~~~~~~~~~~~~~~~~~~" + data.getResult());
            if (!"1".equals(data.getResult())) {

                JOptionPane.showMessageDialog(null, "操作失败！", "信息提示", JOptionPane.INFORMATION_MESSAGE);

                return;
            }
            JOptionPane.showMessageDialog(null, "操作成功！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    centerPanel.removeAll();
                    centerPanel.repaint();
                    cubagePanel.setPanel(centerPanel);
                    cubagePanel.cubageQueryBut.doClick();
                }
            });
        }
    }
}

//查看油站班报
class CkMouseListener extends MouseAdapter {
    YzbbPage yzbbPage;

    public CkMouseListener(YzbbPage yzbbPage) {
        this.yzbbPage = yzbbPage;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("table.getSelectedRow():" + yzbbPage.table.getSelectedRow());

        if (Main.menuList.contains("rcyx_yzbb")) {
            if (yzbbPage.table.getSelectedRow() >= 0) {
                Main.setStatus("查看油站班报");
                YzbbMxPage psdxx = new YzbbMxPage(yzbbPage.list.get(yzbbPage.table.getSelectedRow()));
                Main.setCenter(psdxx.getFrame());
                psdxx.getFrame().setModal(true);
                psdxx.getFrame().setVisible(true);
            } else {

                JOptionPane.showMessageDialog(null, "请选择一条记录！", "信息提示", JOptionPane.ERROR_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(null, "用户[" +
                    Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

        }

    }
}

//结束预警
class JsyjMouseListener extends MouseAdapter implements Watcher {
    private IquidService iquidService;
    private Integer canno;
    OilExcep oilExcep;
    private IAcceptanceOdRegisterService odRegisterService;
    private DailyTradeAccountService dailyTradeAccountService;
    private AlarmMeasureLeakService alarmMeasureLeakService;
    private AlmService almService;
    private String oiltype;
    private Date startDate;
    static ZCTXFrame zc = new ZCTXFrame();

    public JsyjMouseListener(OilExcep oilExcep) {
        this.oilExcep = oilExcep;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Main.menuList.contains("bjtx_ywyc")) {
            int oilcanno;

            int selectRow = 0;
            if (Main.oilExcep != null && Main.oilExcep.table != null) {
                selectRow = Main.oilExcep.table.getSelectedRow();
            } else {
                //Main.setStatus("未获取到表格对象.");
            }
            if (selectRow < 0) {
                JOptionPane.showMessageDialog(null, "请选择一条记录", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                oilcanno = (Integer) Main.oilExcep.table.getValueAt(selectRow, 0);//油罐号
                String enddate = (String) Main.oilExcep.table.getValueAt(selectRow, 2);//结束时间
                if (null != enddate) {
                    JOptionPane.showMessageDialog(null, "该条记录已经停止，请选择正确记录", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                canno=oilcanno;
                //region 判断是否未达到指定时间
                String starttime = Main.oilExcep.table.getValueAt(selectRow, 1).toString();
                SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date start = null;
                try {
                    start = sd1.parse(starttime);
                    startDate=start;
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHH");
                double st = Double.parseDouble(sd.format(start));
                Date date = new Date();
                double end = Double.parseDouble(sd.format(date));
                Object[] options = {"确定", "取消"};
                AlarmMeasureLeakDao alarmMeasureLeakDao = Context.getInstance().getBean(AlarmMeasureLeakDao.class);
                int time = alarmMeasureLeakDao.selectByOilCanNo(oilcanno);
                if (end - st < time) {
                    int response = JOptionPane.showOptionDialog(null,
                            "没有达到测漏时长" + time + "个小时，是否确认结束？", "液位仪应用系统",
                            JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                            options, options[0]);
                    if (response !=0) {
                        return;
                    }
                }
                //endregion 判断是否为达到指定时间
                //region 判定连接类型控制台or探棒直连
                if (iquidService==null){
                    iquidService=Context.getInstance().getBean(IquidService.class);
                }
                SysManageIquidInstrument iquidInstrument=iquidService.selectLast();

                //endregion

                Main.setStatus("正在生成静态液位报告，请稍候...");
                //JsyjMouseListener Jsyj = new JsyjMouseListener(Main.oilExcep);
                Main.watch.addWetcher("A", this);
                if(iquidInstrument.getWorktype().equals("控制台采集")) {
                    //region 控制台采集end
                    //System.out.println("测试走到这里的");
                    Integer oilno = oilcanno;
                    HashMap map = new HashMap();
                    map.put("oilno", oilno);
                    java.util.List list = new ArrayList();
                    list.add(map);
                    GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, list, Constants.PID_Code.A15_10013.toString());
                    //System.out.println("send 13");
                    Main.CC.writeAndFlush(gasMsg);


                    //endregion
                }else{
                    //region  探棒直连
                    GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
                    Main.CC.writeAndFlush(gasMsg);
                    //endregion
                }
                zc.setsInfo("正在结束预警，请稍后...");
                Main.setCenter(zc);
                zc.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);

        }
    }

    @Override
    public void update(GasMsg gasMsg) {
        //System.out.println("return 13");
        if ("A15_10013".equals(gasMsg.getPid())) {
            //region 控制台
            ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);

            if (data.getResult().equals("1")) {
                JOptionPane.showMessageDialog(null, "停止失败", "信息提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (null == data.getData() || "".equals(data.getData())) {
                //System.out.println("no data ");
                //TODO 弹出页面提示停止失败，请重新选择记录

                JOptionPane.showMessageDialog(null, "停止失败", "信息提示", JOptionPane.ERROR_MESSAGE);

                return;
            } else if (data.getData().size() > 0) {
                Main.setStatus("已生成静态液位报告。");
                //System.out.println("进入停止液位仪的赋值阶段…………………………………………");
                //System.out.println("data" + data.getData().toString());
                List<Map> list = data.getData();
                Map map = list.get(0);
                if (Main.oilExcep == null) {
                    Main.oilExcep = new OilExcep();
                }
                Main.oilExcep.reload();
                StopAlarmFrame stopAlaramFrame = new StopAlarmFrame(map);
                JDialog dialog = stopAlaramFrame.getFrame(oilExcep);
                Main.setCenter(dialog);
                dialog.setVisible(true);
                //System.out.println("走到这里了" + map);

            }else{
                //
                AlarmMeasureLeakService alarmMeasureLeakService = (AlarmMeasureLeakService) (Context.getInstance().getBean("alarmMeasureLeakService"));
                List<AlarmMeasureLeak> alarmMeasureLeakList = alarmMeasureLeakService.selecthasStartByOilcan(canno);
                if (alarmMeasureLeakList!=null&&alarmMeasureLeakList.size()>0){
                    AlarmMeasureLeak item=alarmMeasureLeakList.get(0);
                    item.setEnddate(new Date());
                    item.setValid(0);
                    alarmMeasureLeakService.updateByPrimaryKeySelective(item);
                }

                if (Main.oilExcep == null) {
                    Main.oilExcep = new OilExcep();
                }
                Main.oilExcep.reload();
                Main.setStatus("预警结束。");
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    zc.setVisible(false);
                }
            });
            //endregion
        }
        if ("A15_10004".equals(gasMsg.getPid())){
            //region 探棒直连
            Integer oilcanno;
            Date startdate;
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            List<Map<String, ?>> candata = resultMsg.getData();
            DecimalFormat df=new DecimalFormat("######.00");
            for (int m = 0; m < candata.size(); m++) {
                if (canno==Integer.parseInt(candata.get(m).get("uOilCanNo").toString())) {
                    oilcanno=canno;
                    //得到该罐的罐存
                    Map map=candata.get(m);
                    atg_stock_data_out_t stock=(atg_stock_data_out_t)mapToObject(atg_stock_data_out_t.class, map);
                    SysManageCanInfoService sysManageCanInfoService=Context.getInstance().getBean(SysManageCanInfoService.class);
                    SysManageCanInfo canInfo=sysManageCanInfoService.selectbycanno(canno);
                    //获取油品类型
                    if (canInfo!=null){
                        if (odRegisterService==null){
                            odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
                        }
                        oiltype=odRegisterService.selectOilType(canInfo.getOilno()).getOiltype().toString();
                    }
                    //获取记录
                    if(alarmMeasureLeakService==null){
                        alarmMeasureLeakService=Context.getInstance().getBean(AlarmMeasureLeakService.class);
                    }
                    AlarmMeasureLeak alarmMeasureLeak=alarmMeasureLeakService.selectinfoByCanNo(canno);
                    alarmMeasureLeak.setEndoill(getV20L(oiltype,stock.fOilTemp,stock.fOilCubage));
                    alarmMeasureLeak.setEndoill(stock.fOilCubage);
                    alarmMeasureLeak.setEndoilheight(stock.fTotalHeight);
                    alarmMeasureLeak.setEndwaterheight(stock.fWaterHeight);
                    alarmMeasureLeak.setEndoiltemp1(stock.fOilTemp1);
                    alarmMeasureLeak.setEndoiltemp2(stock.fOilTemp2);
                    alarmMeasureLeak.setEndoiltemp3(stock.fOilTemp3);
                    alarmMeasureLeak.setEndoiltemp4(stock.fOilTemp4);
                    alarmMeasureLeak.setEndoiltemp5(stock.fOilTemp5);
                    alarmMeasureLeak.setEndwaterl(stock.fWaterBulk);



                    //期间有无卸油,无付油
                    IAcceptanceOdRegisterInfoService infoService=Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
                    List<AcceptanceOdRegisterInfo>infos=infoService.selecbycanno(canno,alarmMeasureLeak.getStartdate(),alarmMeasureLeak.getEnddate());

                    Double duringSales=0.0;
                    if(dailyTradeAccountService==null){
                        dailyTradeAccountService=Context.getInstance().getBean(DailyTradeAccountService.class);
                    }
                    Map salemap = dailyTradeAccountService.GetSaleOilSumByCanNoAndDate(canno.toString(), startDate, new Date());
                    if (salemap!=null&&salemap.get("Liter") != null) {
                        duringSales = Double.parseDouble(salemap.get("Liter").toString());
                    }
                    if (duringSales>0||infos==null||infos.size()==0){
                        //无效
                        alarmMeasureLeak.setValid(0);
                    }else{
                        alarmMeasureLeak.setValid(1);
                    }
                    //时间间隔
                    Double time=(new Date().getTime()-startDate.getTime())/(1000*3600.0);

                    //计算速率

                    Double rate=(alarmMeasureLeak.getEndoill()-alarmMeasureLeak.getStartoill())/time;
                    rate=Double.parseDouble(df.format(rate));
                    alarmMeasureLeak.setRevealrate(rate.toString());
                    //判定类型
                    if (almService==null){
                        almService=Context.getInstance().getBean(AlmService.class);
                    }
                    SysManageAlarmParameter parameter= almService.selectByPrimaryKey(canno);
                    //泄漏状态：0：不泄漏，1:渗漏，2：漏油，3：盗油
                    if(parameter!=null&&parameter.getStealoilalarm()!=null&&parameter.getLeakageoilalarm()!=null&&parameter.getLeakoilalarm()!=null) {
                        if (rate > parameter.getStealoilalarm()) {
                            alarmMeasureLeak.setRevealstatus("3");
                        }
                        if (rate > parameter.getLeakoilalarm() && rate < parameter.getStealoilalarm()) {
                            alarmMeasureLeak.setRevealstatus("2");
                        }
                        if (rate > parameter.getLeakageoilalarm() && rate < parameter.getLeakoilalarm()) {
                            alarmMeasureLeak.setRevealstatus("1");
                        }
                        if (rate < parameter.getLeakageoilalarm()) {
                            alarmMeasureLeak.setRevealstatus("0");
                        }
                    }
                    alarmMeasureLeak.setEnddate(new Date());
                    //入库
                    alarmMeasureLeakService.updateByPrimaryKeySelective(alarmMeasureLeak);
                    if (Main.oilExcep == null) {
                        Main.oilExcep = new OilExcep();
                    }
                    Main.setStatus("预警结束。");
                    Main.oilExcep.reload();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            zc.setVisible(false);
                        }
                    });
                    startdate=alarmMeasureLeak.getStartdate();
                    GetChartFrame getChartFrame = new GetChartFrame(oilcanno, startdate);
                    JDialog dialog = getChartFrame.getFrame(oilcanno, startdate);
                    Main.setCenter(dialog);
                    dialog.setVisible(true);
                    break;
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        zc.setVisible(false);
                    }
                });

            }
            //endregion
        }
    }
    private double getV20L(String oilType,double vt, double V) {
        V20Utils v20Utils=new V20Utils();
        if (V==0.0){return 0.0;}
        if (oilType.equals("03")){
            //柴油
            return  v20Utils.getDieV20(vt, V);
        }else {
            //汽油
            return  v20Utils.getGasV20(vt,V);
        }
    }
    public  Object mapToObject(Class clazz, Map<String,Object> map){
        if(null == map){
            return null;
        }
        Field[] fields = clazz.getDeclaredFields(); //取到类下所有的属性，也就是变量名
        Field field;
        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        for(int i=0; i<fields.length; i++){
            field = fields[i];
            String fieldName = field.getName();
            //把属性的第一个字母处理成大写
            String stringLetter=fieldName.substring(0, 1).toUpperCase();
            //取得set方法名，比如setBbzt
            String setterName="set"+stringLetter+fieldName.substring(1);
            //真正取得set方法。
            Method setMethod = null;
            Class fieldClass = field.getType();
            try {
                if(isHaveSuchMethod(clazz, setterName)){
                    if(fieldClass == String.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, String.valueOf(map.get(fieldName)));//为其赋值
                    }else if(fieldClass == Integer.class || fieldClass == int.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Integer.parseInt(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Boolean.class || fieldClass == boolean.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Boolean.getBoolean(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Short.class || fieldClass == short.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Short.parseShort(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Long.class || fieldClass == long.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Long.parseLong(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Double.class || fieldClass == double.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Double.parseDouble(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == Float.class || fieldClass == float.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, Float.parseFloat(String.valueOf(map.get(fieldName))));//为其赋值
                    }else if(fieldClass == BigInteger.class ){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, BigInteger.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));//为其赋值
                    }else if(fieldClass == BigDecimal.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        setMethod.invoke(o, BigDecimal.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));//为其赋值
                    }else if(fieldClass == Date.class){
                        setMethod = clazz.getMethod(setterName, fieldClass);
                        if(map.get(fieldName).getClass() == java.sql.Date.class){
                            setMethod.invoke(o, new Date(((java.sql.Date)map.get(fieldName)).getTime()));//为其赋值
                        }else if(map.get(fieldName).getClass() == java.sql.Time.class){
                            setMethod.invoke(o, new Date(((java.sql.Time)map.get(fieldName)).getTime()));//为其赋值
                        }else if(map.get(fieldName).getClass() == java.sql.Timestamp.class){
                            setMethod.invoke(o, new Date(((java.sql.Timestamp)map.get(fieldName)).getTime()));//为其赋值
                        }
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }   catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return o;
    }
    public  boolean isHaveSuchMethod(Class<?> clazz, String methodName){
        Method[] methodArray = clazz.getMethods();
        boolean result = false;
        if(null != methodArray){
            for(int i=0; i<methodArray.length; i++){
                if(methodArray[i].getName().equals(methodName)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}

//查看报告
class CkbgMouseListener extends MouseAdapter {
    OilExcep oilExcep;

    public CkbgMouseListener(OilExcep oilExcep) {
        this.oilExcep = oilExcep;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Main.menuList.contains("bjtx_ywyc")) {
            try {
                int oilcanno;
                int selectRow = 0;
                selectRow = Main.oilExcep.table.getSelectedRow();
                if (selectRow < 0) {
                    JOptionPane.showMessageDialog(null, "请选择一条记录", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {

                    oilcanno = (Integer) Main.oilExcep.table.getValueAt(selectRow, 0);//油罐号
                    String s = (String) Main.oilExcep.table.getValueAt(selectRow, 1);
                    String d = (String) Main.oilExcep.table.getValueAt(selectRow, 3);
                    if ("否".equals(d)) {
                        JOptionPane.showMessageDialog(null, "没有报告，请核对后选择", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date startdate = sdf.parse(s);

                    GetChartFrame getChartFrame = new GetChartFrame(oilcanno, startdate);
                    JDialog dialog = getChartFrame.getFrame(oilcanno, startdate);
                    Main.setCenter(dialog);
                    dialog.setVisible(true);
                }
            } catch (ParseException s) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
        }
    }
}

//获取设备基础信息
class SbjcxxhqMouseListener extends MouseAdapter implements Watcher {
    static ZCTXFrame zc = new ZCTXFrame();

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Main.menuList.contains("xtsz_ywysz")) {
            Main.watch.addWetcher("A", this);
            //System.out.println("进入获取设备基础信息....");
            Main.setStatus("正在获取设备基础信息,请稍后...");
            GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10015.toString());
            Main.CC.writeAndFlush(gasMsg);

            zc.setsInfo("正在获取，请稍后...");
            Main.setCenter(zc);
            zc.setVisible(true);
        }else {
            JOptionPane.showMessageDialog(null, "用户[" + Main.oprname + "]没有此权限!", "错误提示", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void update(GasMsg gasMsg) {
        if (Constants.PID_Code.A15_10015.toString().equals(gasMsg.getPid())) {
            ResultMsg data = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            if ("0".equals(data.getResult())) {
                JOptionPane.showMessageDialog(null, "获取成功", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                Main.setStatus("设备基础信息获取成功");
            } else {
                JOptionPane.showMessageDialog(null, "获取失败", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                Main.setStatus("设备基础信息获取失败，请重新尝试");
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    zc.setVisible(false);
                }
            });
        }
    }

}