package com.kld.app.view.daily;

import com.kld.app.service.DailyOpoCountService;
import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.service.impl.*;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.app.view.acceptance.MyTable;
import com.kld.app.view.main.Main;
import com.kld.app.view.main.ZCTXFrame;
import com.kld.gsm.ATG.domain.DailyDailyBalance;
import com.kld.gsm.ATG.domain.DailyOpoCount;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;
import org.junit.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-12-30 12:30
 * @Description: 盘点 期初库存 |进货验收卸油量|付油量|期末库存|实测库存|损耗量|损耗率
 */
public class PdPage implements Watcher {
    private static final Logger log = Logger.getLogger(PdPage.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("######0.00");
    public static Map realtime = new HashMap();
    SysManageCanInfoService sysManageCanInfoService = Context.getInstance().getBean(SysManageCanInfoService.class);
    CheckSerivceImpl checkSerivce = Context.getInstance().getBean(CheckSerivceImpl.class);


    ZCTXFrame zc = new ZCTXFrame();

    private JTable table;
    String[] tableHeads = {
            "油罐",
            "期初库存(L)",
            "卸油量(L)",
            "付油量(L)",
            "期末库存(L)",
            "实测库存(L)",
            "损耗量(L)",
            "损耗率(%)"
    };
    // table的数据
    Object[][] data = new Object[0][tableHeads.length];
    private JPanel pdPagePanel;
    JComboBox ygbhJComboBox;
    private JTable probePartable;
    private JScrollPane scrollPane = new JScrollPane();
    //声明一个Map<oilno,dischargel>
    Map<String, Double> dischargeMap = new HashMap<String, Double>();
    Map<String, Double> literMap = new HashMap<String, Double>();
    JPanel panel1 = new JPanel();
    ;
    String beginTime;
    JLabel begin;
    JLabel end;

    public void setPanel(JPanel centerPanel) {

        centerPanel.setLayout(null);
        scrollPane.setBounds(0, 45, 800, 385);
        centerPanel.add(scrollPane);
        //========================加油罐======================================


        panel1.setLayout(null);
        panel1.setBounds(0, 0, 800, 300);
        centerPanel.add(panel1);
        JLabel startLab = new JLabel("日期：");
//		startLab.setForeground(Color.WHITE);
        startLab.setFont(Constant.BOTTOM_FONT);
        startLab.setBounds(15, 2, 40, 30);
//		startLab.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        panel1.add(startLab);

        //日期选择器
        final JXDatePicker beginDate = new JXDatePicker();
        beginDate.getEditor().setEditable(false);
        beginDate.setBounds(56, 10, 160, 20);
        beginDate.setFormats(dateFormat);
        beginDate.setDate(new Date());
        panel1.add(beginDate);
        panel1.updateUI();

        //油罐   油品

        JLabel jsfsLabel = new JLabel("盘点类型：");
        jsfsLabel.setFont(Constant.BOTTOM_FONT);
        jsfsLabel.setBounds(230, 12, 60, 12);
//        jsfsLabel.setForeground(Color.WHITE);
        panel1.add(jsfsLabel);

        final JComboBox jsfsLabelcomboBox = new JComboBox();
        jsfsLabelcomboBox.addItem("油罐");
        jsfsLabelcomboBox.addItem("油品");
        centerPanel.add(jsfsLabelcomboBox);
        jsfsLabelcomboBox.setBounds(295, 10, 160, 20);
        panel1.add(jsfsLabelcomboBox);


        // 日期选择器
        JButton cxbutton = new JButton("查询");
        cxbutton.setBounds(460, 10, 60, 30);

        panel1.add(cxbutton);
        begin = new JLabel("开始时间:");
        begin.setFont(Constant.BOTTOM_FONT);
        begin.setBounds(530, 0, 200, 30);
        panel1.add(begin);
        end = new JLabel("结束时间:");
        end.setFont(Constant.BOTTOM_FONT);
        end.setBounds(530, 12, 200, 30);
        panel1.add(end);
        cxbutton.addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent e) {
                //时间校验
                if (null == beginDate.getDate() || "".equals(beginDate.getDate())) {
                    JOptionPane.showMessageDialog(null, "请输入开始时间！", "", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Integer selectDate = Integer.valueOf(dateFormat.format(beginDate.getDate()).replaceAll("-", ""));
                Integer data = Integer.valueOf(dateFormat.format(new Date()).replaceAll("-", ""));
                if (selectDate > data) {
                    JOptionPane.showMessageDialog(null, "选择日期不能大于当前日期！", "", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }


                String formatDate = dateFormat.format(beginDate.getDate()).replaceAll("-", "") + "01";
                DailyOpoCountService dailyOpoCountService = Context.getInstance().getBean(DailyOpoCountService.class);

                beginTime = dailyOpoCountService.findByShift(formatDate);
                System.out.println("根据班次号[" + formatDate + "]获取到的时间" + beginTime);

                if (beginTime == null) {
                    begin.setText("开始时间:");

                } else {
                    begin.setText("开始时间: " + beginTime.substring(0, 19));
                    //begin.setText("开始时间:" + beginTime.substring(10, 19));

                }
//                end.setText("结束时间: " + new Date().toLocaleString().substring(9));
                Date endDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                end.setText("结束时间: " + sdf.format(endDate));

                //System.out.println(formatDate);
                if ("油罐".equals(jsfsLabelcomboBox.getSelectedItem())) {
                    findByOilCan(beginDate.getDate(), formatDate, "油罐");
                } else if ("油品".equals(jsfsLabelcomboBox.getSelectedItem())) {
                    findByOilCan(beginDate.getDate(), formatDate, "油品");
                }


            }

        });


        //========================加油罐======================================
        centerPanel.setVisible(true);

        pdPagePanel = new JPanel();
        pdPagePanel.setLayout(null);
        pdPagePanel.setBounds(0, 8, 800, 385);
        pdPagePanel.setVisible(true);
//        pdPagePanel.setBackground(new Color(Integer.decode(com.kld.app.util.Constant.CENTER_BG_COCLER)));
        centerPanel.add(pdPagePanel);


        probePartable = getTable(tableHeads, data);
        probePartable.removeAll();
        probePartable.repaint();
        probePartable = getTable(tableHeads, data);
        probePartable.updateUI();
        probePartable.setBounds(0, 0, 800, 300);
        scrollPane.setViewportView(probePartable);
        scrollPane.setViewportView(probePartable);
        centerPanel.setBackground(Color.WHITE);
        //System.out.println("盘点结束!");
        //zc.dispose();
    }


    private void findByOilCan(Date beginDate, String formatDate, String findType) {
        YgCheckSerivceImpl ygCheckSerivce = Context.getInstance().getBean(YgCheckSerivceImpl.class);
        AcceptanceOdRegisterInfoServiceImpl odRegisterInfoService = Context.getInstance().getBean(AcceptanceOdRegisterInfoServiceImpl.class);
        SysManageOilGunInfoImpl sysManageOilGunInfo = Context.getInstance().getBean(SysManageOilGunInfoImpl.class);
        DailyTradeAccountServiceImpl dailyTradeAccountService = Context.getInstance().getBean(DailyTradeAccountServiceImpl.class);
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");

        if (findType.equals("油罐")) {


            tableHeads[0] = findType;

            List<SysManageCanInfo> oilCan = ygCheckSerivce.findOilCan();
            //System.out.println("chadundao ：" + oilCan.toString());
            if (oilCan != null) {
                data = new Object[oilCan.size()][tableHeads.length];
                for (int i = 0; i < oilCan.size(); i++) {
                    double liter = 0.0;
                    double qimo = 0.0;
                    SysManageCanInfo sysManageCanInfo = oilCan.get(i);
                    data[i][0] = sysManageCanInfo.getOilcan();
                    //根据油罐对应的油品 到日平衡表中查询最新的一个日结信息 期初
//                Double dStock = checkSerivce.findRealStock(sysManageCanInfo.getOilno());
                    Double dStock = 0.0;
                    //System.out.println("班次号：" + formatDate);
                    String toOilL = checkSerivce.findByOilcan(formatDate, sysManageCanInfo.getOilcan() + "");
                    //System.out.println("查询到的期初" + toOilL);
                    if (toOilL != null) {
                        dStock = Double.valueOf(toOilL);
                    }
                    data[i][1] = decimalFormat.format(dStock);
                    qimo = dStock;
                    String dischargel = odRegisterInfoService.findByOilCan(sysManageCanInfo.getOilcan().toString(), beginDate, new Date());
                    if (dischargel == null) {
                        data[i][2] = "0.0";
                    } else {
                        data[i][2] = decimalFormat.format(Double.valueOf(dischargel));
                        qimo = dStock + Double.valueOf(dischargel);
                    }
                    //System.out.println("------------------");
                    //System.out.println("当前油罐：" + sysManageCanInfo.getOilcan());

                    List<SysManageOilGunInfo> oilGunList = sysManageOilGunInfo.findByOilCanNo(sysManageCanInfo.getOilcan() + "");
                    //System.out.println("oilgunList" + oilGunList.size());
                    if (oilGunList.size() > 0) {
                        for (SysManageOilGunInfo oilGunInfo : oilGunList) {
                            if (oilGunInfo.getOilcan().equals(sysManageCanInfo.getOilcan())) {
                                //System.out.println("油罐:" + sysManageCanInfo.getOilcan() + "对应的油枪：" + oilGunInfo.getOilgun());
                                //油枪 开始时间 结束时间
                                String str = dailyTradeAccountService.findByOilGun("00" + oilGunInfo.getOilgun() + "", beginDate, new Date());
                                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                //System.out.println("开始时间" + time.format(beginDate));
                                //System.out.println("结束时间" + time.format(new Date()));
                                //System.out.println("查到的付油量"+str);
                                if (str != null) {
                                    liter += Double.valueOf(str);
                                }
                            }
                        }
                        data[i][3] = decimalFormat.format(liter);

                    } else {
                        data[i][3] = "0.0";
                    }
                    qimo = qimo - liter;

                    data[i][4] = decimalFormat.format(qimo);//期初+卸油-付油
                    double kucun = 0.0;
                    //System.out.println("油罐号："+sysManageCanInfo.getOilcan());
                    if (Main.oilCanRealTime.size() > 0 && Main.oilCanRealTime.get(sysManageCanInfo.getOilcan() + "") != null) {
                        //System.out.println(sysManageCanInfo.getOilcan().getClass());
                        //System.out.println(sysManageCanInfo.getOilcan());

                        kucun += Double.parseDouble(String.valueOf(Main.oilCanRealTime.get(sysManageCanInfo.getOilcan() + "")));
                    }
                    if (Main.oilCanRealTime.size() > 0) {

                        data[i][5] = df.format(kucun);
                        data[i][6] = df.format(qimo - kucun);
                        if (liter > 1) {
                            data[i][7] = df.format((qimo - kucun) / liter * 100) + "%";
                        } else {
                            data[i][7] = df.format(100) + "%";
                        }
                    } else {
                        data[i][5] = "实测库存未能获取";
                        data[i][6] = "";
                        data[i][7] = "";
                    }
                }


                probePartable.removeAll();
                probePartable.repaint();
                probePartable = getTable(tableHeads, data);
                probePartable.setBounds(0, 0, 800, 300);

                probePartable.updateUI();
                scrollPane.setViewportView(probePartable);
            }
        } else {
            tableHeads[0] = findType;

            //查询油品编码
            List<String> oilTypeList = checkSerivce.findUsedOilTypes();
            //最后一个日结的班次号
            String strShift = checkSerivce.findLastShift();

            //初始显示内容
            data = new Object[oilTypeList.size()][tableHeads.length];

            //初始化map中 油品对应的卸油量
            for (int i = 0; i < oilTypeList.size(); i++) {
                dischargeMap.put(oilTypeList.get(i), 0.0);
                literMap.put(oilTypeList.get(i), 0.0);
            }
            //System.out.println("dischargeMap：" + oilTypeList.toString());
            //获取卸油量
            //Map:oilno,dischargel
            List<Map<String, Object>> dischargeList = checkSerivce.findDischargeL(strShift);
            //System.out.println("dischargeList：" + dischargeList.toString());
            for (int i = 0; i < dischargeList.size(); i++) {
                addQuality(dischargeMap, dischargeList.get(i).get("oilno").toString(), Double.parseDouble(dischargeList.get(i).get("dischargel").toString()));
            }
            //System.out.println("dischargeMap：" + dischargeMap.toString());
            //获取付油量
            //System.out.println("Shift：" + strShift);
            List<Map<String, Object>> literList = checkSerivce.getLiterByAccountDate();
            //System.out.println("literList：" + literList.toString());
            for (int i = 0; i < literList.size(); i++) {
                addQuality(literMap, literList.get(i).get("oilno").toString(), Double.parseDouble(literList.get(i).get("liter").toString()));
            }
            //System.out.println("literMap：" + literMap.toString());

            for (int i = 0; i < oilTypeList.size(); i++) {
                //System.out.println("油品编码：" + oilTypeList.get(i));
                data[i][0] = checkSerivce.selectOilNo(oilTypeList.get(i));
                //期初
//                Double dStock = checkSerivce.findRealStock(oilTypeList.get(i));
//                data[i][1] = dStock;
                Double dStock = 0.0;
                String dStockStr = checkSerivce.findToOilLByOilNo(formatDate, oilTypeList.get(i));
                if (dStockStr != null) {
                    dStock = Double.valueOf(dStockStr);
                }
                data[i][1] = dStock;
                //卸油量
//                Double disCharge = dischargeMap.get(oilTypeList.get(i));
//                data[i][2] = df.format(dischargeMap.get(oilTypeList.get(i)));
                Double disCharge = 0.0;
                String disChargeStr = odRegisterInfoService.findByOilNo(oilTypeList.get(i), beginDate, new Date());
                //System.out.println("卸油量" + disChargeStr);

                if (disChargeStr != null) {
                    disCharge = Double.valueOf(disChargeStr);
                }
                data[i][2] = decimalFormat.format(disCharge);


                //付油量

//                Double dLiter = literMap.get(oilTypeList.get(i));

                String str = dailyTradeAccountService.findByOilNo(oilTypeList.get(i), beginDate, new Date());
                //System.out.println("付油量   编码：" + oilTypeList.get(i));
                //System.out.println("付油量   开始日期：" + beginDate);
                //System.out.println("付油量   结束日期：" + new Date());

                //System.out.println("付油量" + str);

                Double dLiter = 0.0;
                if (str != null) {
                    dLiter = Double.valueOf(str);
                }
                data[i][3] = df.format(dLiter);


                data[i][4] = df.format(dStock + disCharge - dLiter);//期初+卸油-付油
                String oilno = oilTypeList.get(i);
                List<String> oilnoList = sysManageCanInfoService.findByOilcan(oilno); //油品 需要根拘油品查到對應的油罐
                // System.out.println(oilno+":油罐号："+ oilnoList.toString());
                double kucun = 0.0;
                for (int no = 0; no < oilnoList.size(); no++) {
                    if (Main.oilCanRealTime.size() > 0 && Main.oilCanRealTime.get(oilnoList.get(no)) != null) {
                        kucun += Double.parseDouble(String.valueOf(Main.oilCanRealTime.get(oilnoList.get(no))));
                    }
                }
                if (oilno != null && !oilno.equals("")) {
                    if (Main.oilCanRealTime.size() > 0) {

                        data[i][5] = df.format(kucun);
                        data[i][6] = df.format(dStock + disCharge - dLiter - kucun);
                        if (dLiter > 1) {
                            data[i][7] = df.format((dStock + disCharge - dLiter - kucun) / dLiter * 100) + "%";
                        } else {
                            data[i][7] = df.format(100) + "%";
                        }
                    } else {
                        data[i][5] = "实测库存未能获取";
                        data[i][6] = "";
                        data[i][7] = "";
                    }
                }

            }

            probePartable.removeAll();
            probePartable.repaint();
            probePartable = getTable(tableHeads, data);
            probePartable.setBounds(0, 0, 800, 300);

            probePartable.updateUI();
            scrollPane.setViewportView(probePartable);
        }

    }

    private JTable getTable(String[] titles, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, titles);
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
        FitTableColumns(table);
        table.setDefaultRenderer(Object.class, render);
       /* while (columns.hasMoreElements()) {
            TableColumn column = columns.nextElement();
            column.setPreferredWidth(150);
        }*/
        table.setRowSelectionAllowed(true);
        table.setBounds(30, 30, 740, 260);
        return table;
    }

    private void FitTableColumns(JTable table) {
        JTableHeader header = table.getTableHeader();
        int rowCount = table.getRowCount();

        Enumeration columns = table.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) table.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(table, column.getIdentifier()
                            , false, false, -1, col).getPreferredSize().getWidth();
            if (width < 98) {
                width = 98;
            }
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table,
                        table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                if (Math.max(width, preferedWidth) >= 80) {
                    width = Math.max(width, preferedWidth);
                }
            }
            header.setResizingColumn(column); // 此行很重要
            column.setWidth(width + table.getIntercellSpacing().width);
        }
    }

    @Override
    public void update(GasMsg gasMsg) {
        ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);

        if (gasMsg.getPid().equals("A15_10004")) {
            ////System.out.println("update[04]");
            if (resultMsg.getResult().equals("0")) {

                for (int i = 0; i < resultMsg.getData().size(); i++) {
                    String oilcanno = ((Map) (resultMsg.getData().get(i))).get("uOilCanNo").toString();
                    SysManageCanInfoService sysManageCanInfoService = Context.getInstance().getBean(SysManageCanInfoService.class);
                    //String oilno = sysManageCanInfoService.findByOilcan(str);

                    if (!oilcanno.isEmpty()) {
                        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
                        String standCubage = decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("fOilStandCubage"));
                        ////System.out.println("实时库存[" + oilno + "]：" + standCubage);
                        Main.oilCanRealTime.put(oilcanno, standCubage);
                        // //System.out.println("TT油罐{" + oilcanno + "}" + "实时库存" + standCubage);

                    }
                }
                // //System.out.println("update完成 :" + Main.realtime.toString());
            }
        }
    }

    /**
     * @param oilno      油品编号
     * @param map        油品编号-付油量
     * @param dischargel 付油量
     *                   遍历油品编号 对应的更新付油量
     */
    public void addQuality(Map map, String oilno, double dischargel) {
        Iterator<Map.Entry<String, Double>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Double> entry = iterator.next();
            if (entry.getKey().equals(oilno)) {
                //System.out.println("---------------查到Map" + entry.getKey() + ":" + entry.getValue());
                double sumdischargel = entry.getValue() + dischargel;
                map.put(entry.getKey(), sumdischargel);
            }
        }
    }


}

