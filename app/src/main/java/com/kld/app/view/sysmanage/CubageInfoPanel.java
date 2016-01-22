package com.kld.app.view.sysmanage;


import com.kld.app.service.SysCubageService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.SuperDoubleDocument;
import com.kld.gsm.ATG.domain.SysManageCubageInfo;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/12/19 16:38
 * @Description:
 */
public class CubageInfoPanel extends JPanel {
    Logger logger = Logger.getLogger(CubageInfoPanel.class);
    JTable jTable = new JTable();
    JScrollPane scrollPane;
    public DefaultTableModel model;
    CubagePanel cubagePanel;
    public JTable jtable = new JTable() ;
    public JTextField gaodu1Text = new JTextField(10);
    JLabel gaoduLabel = new JLabel("高度：");
    public JTextField gaodu2Text = new JTextField(10);
    JLabel zhiLabel =  new JLabel("  至  ");
    JButton cxButton = new JButton("查询");
    final String[] tableHeads = {
            "高度(mm)",
            "体积(L)"
    };

    Object[][] data = {
            {
                    "",
                    ""
            }
    };
    public CubageInfoPanel(CubagePanel cubagePanel){
        this.cubagePanel = cubagePanel;

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
        // tcr.setHorizontalAlignment(JLabel.CENTER);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
        jTable.setDefaultRenderer(Object.class, tcr);
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(gaoduLabel);
        SuperDoubleDocument superDoubleDocument = new SuperDoubleDocument(4,2);
        superDoubleDocument.isfs=false;
        SuperDoubleDocument superDoubleDocument2 = new SuperDoubleDocument(4,2);
        superDoubleDocument2.isfs=false;
        gaodu1Text.setDocument(superDoubleDocument);
        jPanel.add(gaodu1Text);
        jPanel.add(zhiLabel);
        gaodu2Text.setDocument(superDoubleDocument2);
        jPanel.add(gaodu2Text);
        jPanel.add(cxButton);
        this.add(jPanel);
        SysCubageService sysCubageService =(SysCubageService) (Context.getInstance().getBean("sysCubageService"));
        int selectRow = cubagePanel.probePartable.getSelectedRow();
        int oilcanno = (Integer) cubagePanel.probePartable.getValueAt(selectRow, 0);//罐号
        String version = (String) cubagePanel.probePartable.getValueAt(selectRow, 1);//版本号
        SysManageCubageInfo sysManageCubageInfo = new SysManageCubageInfo();
        sysManageCubageInfo.setOilcan(oilcanno);
        sysManageCubageInfo.setVersion(version);
        java.util.List<SysManageCubageInfo> sysManageCubageInfoList = sysCubageService.selectCubageInfo(sysManageCubageInfo);
        data = new Object[sysManageCubageInfoList.size()][tableHeads.length];
        for (int i = 0; i < sysManageCubageInfoList.size(); i++) {
            SysManageCubageInfo s = sysManageCubageInfoList.get(i);
            data[i][0] = s.getHeight();
            data[i][1] = s.getLiter();
        }
        ButtonListener buttonListener = new ButtonListener(cubagePanel,this);
        cxButton.addActionListener(buttonListener);
        jTable.updateUI();
        model =new DefaultTableModel(data,tableHeads);
        jtable.setModel(model);
        scrollPane =new JScrollPane(jtable);
        this.setLayout(new BorderLayout());
        this.add(jPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}

class ButtonListener implements ActionListener{
    Logger logger = Logger.getLogger(ButtonListener.class);
    CubageInfoPanel cubageInfoPanel;
    CubagePanel cubagePanel;
    SysCubageService sysCubageService =(SysCubageService) (Context.getInstance().getBean("sysCubageService"));

    public ButtonListener(CubagePanel cubagePanel,CubageInfoPanel cubageInfoPanel) {
        this.cubageInfoPanel = cubageInfoPanel;
        this.cubagePanel = cubagePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectRow = cubagePanel.probePartable.getSelectedRow();
        int oilcanno = (Integer) cubagePanel.probePartable.getValueAt(selectRow, 0);//罐号
        String version = (String) cubagePanel.probePartable.getValueAt(selectRow, 1);//版本号
        Map map = new HashMap();
        map.put("oilcan", oilcanno);
        map.put("version", version);
        if(cubageInfoPanel.gaodu1Text.getText()!=null&&!"".equals(cubageInfoPanel.gaodu1Text.getText())&&cubageInfoPanel.gaodu2Text.getText()!=null&&!"".equals(cubageInfoPanel.gaodu2Text.getText())){
            if(Double.parseDouble(cubageInfoPanel.gaodu1Text.getText())>Double.parseDouble(cubageInfoPanel.gaodu2Text.getText())) {
                JOptionPane.showMessageDialog(null, "请输入正确的高度区间", "", JOptionPane.INFORMATION_MESSAGE);
                  return;
            }
        }

        if(cubageInfoPanel.gaodu1Text.getText()!=null&&!"".equals(cubageInfoPanel.gaodu1Text.getText())) {
            map.put("height1", cubageInfoPanel.gaodu1Text.getText());
        }else{
            map.put("height1",null);
        }
        if(cubageInfoPanel.gaodu2Text.getText()!=null&&!"".equals(cubageInfoPanel.gaodu2Text.getText())) {
            map.put("height2", cubageInfoPanel.gaodu2Text.getText());
        }else {
            map.put("height2",null);
        }
        logger.info("CubageInfoPanel:map.get(\"oilcanno\")"+map.get("oilcan"));
        logger.info("CubageInfoPanel:map.get(\"version\")"+map.get("version"));
        logger.info("CubageInfoPanel:map.get(\"height1\")"+map.get("height1"));
        logger.info("CubageInfoPanel:map.get(\"height2\")"+map.get("height2"));
        java.util.List<SysManageCubageInfo> sysManageCubageInfoList = sysCubageService.selectCubageInfoByPar(map);
        cubageInfoPanel.data = new Object[sysManageCubageInfoList.size()][cubageInfoPanel.tableHeads.length];
        logger.info("CubageInfoPanel:sysManageCubageInfoList.size()"+sysManageCubageInfoList.size());
        for (int i = 0; i < sysManageCubageInfoList.size(); i++) {
            SysManageCubageInfo s = sysManageCubageInfoList.get(i);
            cubageInfoPanel.data[i][0] = s.getHeight();
            cubageInfoPanel.data[i][1] = s.getLiter();
        }
        cubageInfoPanel.jTable.setModel(cubageInfoPanel.model);
        for (int i = 0; i < cubageInfoPanel.tableHeads.length; i++) {
            cubageInfoPanel.jTable.getColumnModel().getColumn(i).setPreferredWidth(160);
        }
        //System.out.println("probePartable.updateUI()");
        cubageInfoPanel.model =new DefaultTableModel(cubageInfoPanel.data,cubageInfoPanel.tableHeads);
        cubageInfoPanel.jtable.setModel(cubageInfoPanel.model);
        cubageInfoPanel.jTable.updateUI();
    }
}