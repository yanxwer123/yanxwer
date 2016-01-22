package com.kld.app.view.daily;

import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.service.impl.CheckSerivceImpl;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.view.acceptance.MyTable;
import com.kld.app.view.main.Main;
import com.kld.app.view.main.ZCTXFrame;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
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
    public  static Map realtime = new HashMap();
    SysManageCanInfoService sysManageCanInfoService = Context.getInstance().getBean(SysManageCanInfoService.class);


    ZCTXFrame zc = new ZCTXFrame();

    private JTable table;
    final String[] tableHeads = {
            "油品",
            "期初库存(L)",
            "卸油量(L)",
            "付油量(L)",
            "期末库存(L)",
            "实测库存(L)",
            "损耗量(L)",
            "损耗率(%)"
    };
    // table的数据
    private Object[][] data = new Object[0][tableHeads.length];
    private JPanel pdPagePanel;
    JComboBox ygbhJComboBox;
    private JTable probePartable;
    private JScrollPane scrollPane = new JScrollPane();
    //声明一个Map<oilno,dischargel>
    Map<String, Double> dischargeMap = new HashMap<String, Double>();
    Map<String, Double> literMap = new HashMap<String, Double>();

    public void setPanel(JPanel centerPanel) {

        centerPanel.setLayout(null);
        scrollPane.setBounds(0, 8, 800, 385);
        centerPanel.add(scrollPane);
        centerPanel.setVisible(true);

        pdPagePanel = new JPanel();
        pdPagePanel.setLayout(null);
        pdPagePanel.setBounds(0, 8, 800, 385);
        pdPagePanel.setVisible(true);
//        pdPagePanel.setBackground(new Color(Integer.decode(com.kld.app.util.Constant.CENTER_BG_COCLER)));
        centerPanel.add(pdPagePanel);

        CheckSerivceImpl checkSerivce = Context.getInstance().getBean(CheckSerivceImpl.class);
        //查询油品编码
        List<String> oilTypeList=checkSerivce.findUsedOilTypes();
        //最后一个日结的班次号
        String strShift = checkSerivce.findLastShift();

        //初始显示内容
        data = new Object[oilTypeList.size()][tableHeads.length];

        //初始化map中 油品对应的卸油量
        for (int i = 0; i < oilTypeList.size(); i++) {
            dischargeMap.put(oilTypeList.get(i), 0.0);
            literMap.put(oilTypeList.get(i), 0.0);
        }
        System.out.println("dischargeMap：" + oilTypeList.toString());
        //获取卸油量
        //Map:oilno,dischargel
        List<Map<String,Object>> dischargeList=checkSerivce.findDischargeL(strShift);
        System.out.println("dischargeList：" + dischargeList.toString());
        for (int i = 0; i < dischargeList.size(); i++) {
            addQuality(dischargeMap, dischargeList.get(i).get("oilno").toString(), Double.parseDouble(dischargeList.get(i).get("dischargel").toString()));
        }
        System.out.println("dischargeMap：" + dischargeMap.toString());
        //获取付油量
        System.out.println("Shift：" + strShift);
        List<Map<String,Object>> literList=checkSerivce.getLiter(strShift);
        System.out.println("literList：" + literList.toString());
        for (int i = 0; i < literList.size(); i++) {
            addQuality(literMap, literList.get(i).get("oilno").toString(), Double.parseDouble(literList.get(i).get("liter").toString()));
        }
        System.out.println("literMap：" + literMap.toString());

        for (int i = 0; i < oilTypeList.size(); i++) {
            data[i][0] = checkSerivce.selectOilNo(oilTypeList.get(i));
            Double dStock=checkSerivce.findRealStock(oilTypeList.get(i));
            System.out.println("RealStock：" + dStock);
            data[i][1] = dStock;
            Double disCharge=dischargeMap.get(oilTypeList.get(i));
            data[i][2] = df.format(dischargeMap.get(oilTypeList.get(i)));
            Double dLiter=literMap.get(oilTypeList.get(i));
            data[i][3] = df.format(literMap.get(oilTypeList.get(i)));
            data[i][4] = df.format(dStock + disCharge - dLiter);//期初+卸油-付油
            String oilno = oilTypeList.get(i);
            List<String>  oilnoList = sysManageCanInfoService.findByOilcan(oilno); //油品 需要根拘油品查到對應的油罐
            double kucun = 0.0;
            for(int no=0;no<oilnoList.size();no++) {
                if(Main.oilCanRealTime.size()>0&&Main.oilCanRealTime.get(oilnoList.get(no))!=null) {
                    kucun += Double.parseDouble(String.valueOf(Main.oilCanRealTime.get(oilnoList.get(no))));
                }
            }
            if (oilno != null && !oilno.equals("")) {
                if ( Main.oilCanRealTime.size()> 0) {

                    data[i][5] = df.format(kucun);
                    data[i][6] = df.format(dStock + disCharge - dLiter - kucun);
                    if(dLiter>1) {
                        data[i][7] = df.format((dStock + disCharge - dLiter - kucun) / dLiter * 100)+"%";
                    }
                    else
                    {
                        data[i][7] = df.format(100)+"%";
                    }
                } else {
                    data[i][5] = "实测库存未能获取";
                    data[i][6] = "";
                    data[i][7] = "";
                }
            }

        }


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
        while(columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) table.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(table, column.getIdentifier()
                            , false, false, -1, col).getPreferredSize().getWidth();
            if(width<98){
                width=98;
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
    @Override
    public void update(GasMsg gasMsg) {
        ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);

        if (gasMsg.getPid().equals("A15_10004")) {
            //System.out.println("update[04]");
            if (resultMsg.getResult().equals("0")) {

                for (int i = 0; i < resultMsg.getData().size(); i++) {
                    String oilcanno = ((Map) (resultMsg.getData().get(i))).get("uOilCanNo").toString();
                    SysManageCanInfoService sysManageCanInfoService = Context.getInstance().getBean(SysManageCanInfoService.class);
                    //String oilno = sysManageCanInfoService.findByOilcan(str);

                    if (!oilcanno.isEmpty()) {
                        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
                        String standCubage = decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("fOilStandCubage"));
                        //System.out.println("实时库存[" + oilno + "]：" + standCubage);
                         Main.oilCanRealTime.put(oilcanno, standCubage);
                       // System.out.println("TT油罐{" + oilcanno + "}" + "实时库存" + standCubage);

                    }
                }
               // System.out.println("update完成 :" + Main.realtime.toString());
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

