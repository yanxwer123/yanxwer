package com.kld.app.view.monitor;

import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.service.MonitorTimeInventoryService;
import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.socket.ob.Watcher;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Common;
import com.kld.app.util.Constant;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.MonitorTimeInventory;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;
import com.kld.gsm.ATG.domain.SysManageOilType;
import com.kld.gsm.MacLog.MacLogInfo;
import com.kld.gsm.Socket.Constants;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.JsonMapper;
import org.junit.Test;
import org.slf4j.Logger;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 实时罐枪监控
 *
 * @author YANGRL
 */
public class TankGunPumpCodeInformat implements Watcher {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(TankGunPumpCodeInformat.class);


    private JPanel centerPanel = null;

    public TankGunPumpCodeInformat() {
    }


    //状态图表
    private JPanel panel1 = new JPanel();
    List<MacLogInfo> listData = null;
    MonitorTimeInventoryService monitorTimeInventoryService = Context.getInstance().getBean(MonitorTimeInventoryService.class);
    JPanel mainPanel = new JPanel();
    public static JPanel blpanel1;
    public static JPanel blpanel2;
    public static JPanel blpanel3;

    // 保存油罐的位置信息  油罐号 -  位置
    static Map<Integer, Integer> positMap = new LinkedHashMap<Integer, Integer>();
    // 保存油罐的 空油水元素对象
    static Map<Integer, JpanelElement> elementMap = new LinkedHashMap<Integer, JpanelElement>();
    JpanelElement jpanelElement;//元素对象
    public static int Air = 40;
    public static int oil = 130;
    public static int w = 10;

    //空气高度
    public static int AirHeight = 0;
    //获取油品名称
    private IAcceptanceOdRegisterService acceptanceOdRegister = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);

    //region  保存界面油枪元素对象 -- 存储容器  Map <灌号,Map<抢号，界面元素对象>
    //endregion
    private static Map<String, Map<String, GasGunJpanel>> gunMap;

    //region   保存界面油罐元素对象   ---存储容器   Map<油罐号,元素对象>
    //endregion
    private static Map<String, JTable> oilCaninfo;
    JTable table;

    /**
     * -----持久层
     */

    SysManageCanInfoService sysManageCanInfoService = Context.getInstance().getBean(SysManageCanInfoService.class);

    //获取油罐信息
    List<SysManageCanInfo> CaninfoList = monitorTimeInventoryService.selectAll();
    //油枪信息
    List<SysManageOilGunInfo> oilGunList = monitorTimeInventoryService.selectAllOilGun();
    //  辨识油枪→油罐
    List<SysManageOilGunInfo> list;
    //在填充 油枪状态的时候 收集界面元素对象，后续去改变元素的值
   static Map<String, GasGunJpanel> jPanelMap;
    static boolean flag = true;


    //中部需要滚动条
    public void setPanel(JPanel centerPanel, final boolean bool) {
        mainPanel.removeAll();
        mainPanel.repaint();
        oilCaninfo = new LinkedHashMap<String, JTable>();
        gunMap = new LinkedHashMap<String, Map<String, GasGunJpanel>>();
        //   Iterator<Map.Entry<String, JTable>> iterator = oilCaninfo.entrySet().iterator();


        this.centerPanel = centerPanel;
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 800, 390);
        //this.centerPanel = new JPanel();
        centerPanel.setLayout(null);

        centerPanel.add(scrollPane);
        scrollPane.setVisible(true);
        mainPanel.setBackground(new Color(Integer.decode(Constant.CENTER_BG_COCLER)));
        scrollPane.setViewportView(mainPanel);

        mainPanel.setLayout(null);
        panel1.setLayout(null);
        panel1.setPreferredSize(new Dimension(1000, 60));
        panel1.setBounds(0, 0, 1200, 60);
        panel1.setBackground(new Color(Integer.decode(Constant.CENTER_BG_COCLER)));


        /**
         * -----持久层
         */
        CaninfoList = monitorTimeInventoryService.selectAll();
        //油枪信息
        oilGunList = monitorTimeInventoryService.selectAllOilGun();
//        //System.out.println("油枪信息================================");
//        for (int i = 0; i < oilGunList.size(); i++) {
//            //System.out.println("Gun:" + oilGunList.get(i));
//        }
//        //System.out.println("油枪信息================================");
        int size = CaninfoList.size();
        ////System.out.println("油罐表  记录：:" + size);
        List<SysManageOilGunInfo> gunInfos;
        MonitorTimeInventory info;

        //region  填充油罐信息\\
        if (size > 0) {
            mainPanel.setPreferredSize(new Dimension(1200, 50 + size * 230));//关键代码,设置JPanel的大小
            mainPanel.setBounds(0, 0, 1200, size * 500);
            //根据油品编号查询油品名称
            List<SysManageOilType> types = acceptanceOdRegister.selectUseOilType();

            for (int i = 0; i < size; i++) {
                positMap.put(CaninfoList.get(i).getOilcan(), i);

                jPanelMap = new LinkedHashMap<String, GasGunJpanel>();
                //根据油罐号拿到 油罐库存信息 进行填充
                //info = monitorTimeInventoryService.selectByPrimaryKey(CaninfoList.get(i).getOilcan());
                //时点库存有数据 填充，  没有库存就不填写
                String typeName = "";
                if (!types.isEmpty()) {
                    for (SysManageOilType oilType : types) {
                        if (CaninfoList.get(i).getOilno().equals(oilType.getOilno())) {
                            typeName = new String(oilType.getOilname().getBytes());
                            //System.out.println(typeName);
                        }
                    }

                }
              //  if (info != null) {

                    if (typeName.equals("")) {
                        typeName = "无";
                    }

                   /* Object[][] playerInfo = {
                            {"油罐编号", CaninfoList.get(i).getOilcan()},
                            {"油品", typeName},
                            {"净油体积(L)", info.getOill()},
                            {"标准体积(L)", info.getStandardl()},
                            {"空体积(L)", info.getVolumeempty()},
                            {"油水总高(mm)", info.getHeighttotal()},
                            {"水高(mm)", info.getHeightwater()},
                            {"水体积(L)", info.getWaterl()},
                            {"平均温度(℃)", info.getTemperature()}
                    };
                    String[] Names = {"", ""};
                    table = new JTable(playerInfo, Names);
                    oilCaninfo.put(CaninfoList.get(i).getOilcan().toString(), table);

                } else {*/
                    Object[][] playerInfo = {
                            {"油罐编号", CaninfoList.get(i).getOilcan()},
                            {"油品", typeName},
                            {"净油体积(L)", "0.0"},
                            {"标准体积(L)", "0.0"},
                            {"空体积(L)", "0.0"},
                            {"油水总高(mm)", "0.0"},
                            {"水高(mm)", "0.0"},
                            {"水体积(L)", "0.0"},
                            {"平均温度(℃)", "0.0"}
                    };
                    String[] Names = {"", ""};
                    table = new JTable(playerInfo, Names);
                    //保存油罐元素
                    oilCaninfo.put(CaninfoList.get(i).getOilcan().toString(), table);
                //}


                table.setFont(Constant.YGXX_FONT);
                table.setEnabled(false);//文字不可修改
                table.setRowHeight(19);
                table.getColumnModel().getColumn(0).setPreferredWidth(100);
                table.getColumnModel().getColumn(1).setPreferredWidth(100);
                table.getTableHeader().setVisible(false);
                table.setPreferredScrollableViewportSize(new Dimension(550, 60));// 设置此表视口的首选大小。
                table.setPreferredSize(new Dimension(200, 188));
                table.setBounds(20, 40 + i * 230, 200, 188);
                mainPanel.add(table);
                int h = 40 + i * 230;
                //左侧比例
                blpanel1 = new JPanel();
                blpanel1.setBackground(Color.decode("#A8D183"));
                //               blpanel1.setBounds(10, 40 + i * 230, 10, Air); //空气高度
                blpanel1.setBounds(10, 40 + i * 230, 10, 188); //空气高度
                mainPanel.add(blpanel1);
                blpanel2 = new JPanel();
                blpanel2.setBackground(Color.decode("#EC6C00"));
                blpanel2.setBounds(10, 40 + i * 230 + Air, 10, 188 - (Air + w));
                mainPanel.add(blpanel2);
                blpanel3 = new JPanel();
                blpanel3.setBackground(Color.decode("#09DFF7"));
                blpanel3.setBounds(10, 40 + i * 230 + Air + (188 - (Air + w)), 10, 188 - (Air + (188 - (Air + w))));
                mainPanel.add(blpanel3);

                jpanelElement = new JpanelElement();
                jpanelElement.setAirJpanel(blpanel1);
                jpanelElement.setOilJpanel(blpanel2);
                jpanelElement.setWatJpanel(blpanel3);
                //System.out.println("blpanel1" + blpanel1);
                //System.out.println("blpanel2"+blpanel2);
                //System.out.println("blpanel3"+blpanel3);
                elementMap.put(CaninfoList.get(i).getOilcan(), jpanelElement);

                //判断油罐对应的油枪
                ////System.out.println("info oilcan : " + info.getOilcan().toString());
                for (int z = 0; z < oilGunList.size(); z++) {
                    ////System.out.println(oilGunList.get(z).getOilcan());
                }

                gunInfos = getOilGunByOilCan(CaninfoList.get(i).getOilcan().toString(), oilGunList);

                if (gunInfos.size() < 1) {
                    //  ////System.out.println("guninfos is null");

                    continue;
                }
                ////System.out.println("-=:" + gunInfos.size());
                for (int j = 0; j < gunInfos.size(); j++) {
                    //////System.out.println("j" + j + "  ===:" + gunInfos.get(j));
                    GasGunJpanel panel = new GasGunJpanel(gunInfos.get(j), CaninfoList.get(i).getOilcan());
                    jPanelMap.put(gunInfos.get(j).getOilgun().toString(), panel);

                    panel.panel.setBounds(223 + j % 4 * 140, 40 + j / 4 * 97 + i * 230, 135, 90);
                    mainPanel.add(panel.panel);
                }
                ////System.out.println("油罐号为:【" + CaninfoList.get(i).getOilcan() + "】 存放的元素： " + jPanelMap.toString());
                gunMap.put(CaninfoList.get(i).getOilcan().toString(), jPanelMap);
                ////System.out.println("Map:【" + CaninfoList.get(i).getOilcan() + "】 ： " + gunMap.toString());
            }
            ////System.out.println("油罐元素::" + oilCaninfo.toString());
            //region 更新油罐信息
            // new SyncOilTank(oilCaninfo);

            //endregion

            //todo 加载 油罐信息后  启动线程 获取实时罐枪状态及蹦码信息
            //    centerPanel.setLayout(null);
            //region 注册观察者

            // ////System.out.println("TankGunPumpCodeInformat-------------");
            //  ////System.out.println("cc:" + cc);
            //  ////System.out.println("watch:" + Main.watch);
            //  Watcher watcher = new TankGunPumpCodeInformat();
            Main.watch.addWetcher("A", this);
            // ////System.out.println("-------------------------------------------");
            //endregion
            //  更新油枪信息 油罐信息
            new Thread(new Runnable() {
                GasMsg gasMsg;

                @Override
                public void run() {
                    int i = 0;
                    flag = bool;
                    //  //System.out.println("传进来的是：" + bool);
                    boolean gasbool = true;
                    while (flag) {
                        try {

                            gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10002.toString());
                            // System.out.println("request[02]:" + gasMsg);
                             //System.out.println("request[02]" );
                            Main.CC.writeAndFlush(gasMsg);
                            i++;
                            //油罐
                            if (i == 10 || gasbool) {
                                gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10004.toString());
                                //System.out.println("request[04]:" + gasMsg);

                                Main.CC.writeAndFlush(gasMsg);
                                i = 0;
                                gasbool = false;
                            }
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

//endregion
        //region top填充

        JLabel hold1Label = new JLabel("空闲/上班/挂枪");
        hold1Label.setFont(Constant.BOTTOM_FONT);
        hold1Label.setBounds(20, 10, 84, 12);
        hold1Label.setForeground(new Color(Integer.decode(Constant.SSJK_COCLER)));
        panel1.add(hold1Label);

        JButton hold1 = new JButton();
        hold1.setBorderPainted(false);
        hold1.setContentAreaFilled(false);
        hold1.setIcon(Common.createImageIcon(this.getClass(), "up.png"));
        hold1.setBounds(104, 5, 32, 32);
        panel1.add(hold1);

        JLabel hold1Label1 = new JLabel("提枪");
        hold1Label1.setFont(Constant.BOTTOM_FONT);
        hold1Label1.setBounds(150, 10, 24, 12);
        hold1Label1.setForeground(new Color(Integer.decode(Constant.SSJK_COCLER)));
        panel1.add(hold1Label1);

        JButton hold = new JButton();
        hold.setBorderPainted(false);
        hold.setContentAreaFilled(false);
        hold.setIcon(Common.createImageIcon(this.getClass(), "down.png"));
        hold.setBounds(180, 5, 32, 32);
        panel1.add(hold);
        mainPanel.add(panel1);
        scrollPane.updateUI();
//endregion
    }


    //回调
    @Override
    public void update(GasMsg gasMsg) {


// TODO Auto-generated method stub
        ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage().toString(), ResultMsg.class);
//region   A15_10004  油罐实时同步  次/5s

        if (gasMsg.getPid().equals("A15_10004")) {
            //logger.info("GasCan:" + gasMsg.getMessage());
            //System.out.println("update[04]");///System.out.println("A15_10004 jion on "+gasMsg);
            if (resultMsg.getResult().equals("0")) {
                ////System.out.println("A15_10004查询成功");
                //拿到List
                int size = resultMsg.getData().size();
                ////System.out.println("Ctrl return atg_stock_data_out_t_List.size" + size);
                for (int i = 0; i < resultMsg.getData().size(); i++) {
                    String str = ((Map) (resultMsg.getData().get(i))).get("uOilCanNo").toString();
                    ////System.out.println("----" + str);
                    if (!str.isEmpty()) {
                        JTable jTable = oilCaninfo.get(str + "");
                        if (jTable != null) {
                            TableModel tableModel = jTable.getModel();
                            DecimalFormat decimalFormat = new DecimalFormat("######0.00");
                            tableModel.setValueAt(decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("fOilCubage")), 2, 1);
                            tableModel.setValueAt(decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("fOilStandCubage")), 3, 1);
                            tableModel.setValueAt(decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("fEmptyCubage")), 4, 1);
                            tableModel.setValueAt(decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("fTotalHeight")), 5, 1);
                            tableModel.setValueAt(decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("fWaterHeight")), 6, 1);
                            tableModel.setValueAt(decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("fWaterBulk")), 7, 1);
                            tableModel.setValueAt(decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("fOilTemp")), 8, 1);
                            //System.out.println("water" + ((Map) (resultMsg.getData().get(i))).get("fWaterBulk").toString());
                            //System.out.println("oil" + ((Map) (resultMsg.getData().get(i))).get("fOilCubage").toString());
                            //System.out.println("air" + ((Map) (resultMsg.getData().get(i))).get("fEmptyCubage").toString());
                           // List<String> oilcan = sysManageCanInfoService.findByOilcan(str);

                             Main.oilCanRealTime.put(str, ((Map) (resultMsg.getData().get(i))).get("fOilStandCubage"));
                            double water = Double.parseDouble(((Map) (resultMsg.getData().get(i))).get("fWaterBulk").toString());

                            double oil = Double.parseDouble(((Map) (resultMsg.getData().get(i))).get("fOilCubage").toString());

                            double air = Double.parseDouble(((Map) (resultMsg.getData().get(i))).get("fEmptyCubage").toString());
//                            System.out.println("【str】"+str);
//                            System.out.println("【water】"+water);
//                            System.out.println("【oil】"+oil);
//                            System.out.println("【air】"+air);
                            updateBlpanel(Integer.valueOf(str), water, oil, air);
                        }
                    }
                }
            } else if (resultMsg.getResult().equals("1")) {
                logger.error("A15_10004 return fail");
            }
        }
//endregion
//region  A15_10002 油枪同步    次/0.5s
        if (gasMsg.getPid().equals("A15_10002")) {
            logger.info("update[02]" + gasMsg.toString());
            if (resultMsg.getResult().equals("1")) {
                logger.info("no resultMsg ");
                return;
            }
            logger.info("resultMsg.   Data  ["+resultMsg.getData().size() +"]");

            for (int i = 0; i < resultMsg.getData().size(); i++) {

                String gunNum = ((Map) (resultMsg.getData().get(i))).get("GunNum").toString();//油枪号
                logger.info("["+i+"]" + "gunNum" + gunNum);
                String GunStatus;
                if (null != ((Map) (resultMsg.getData().get(i))).get("GunStatus")) {
                    GunStatus = ((Map) (resultMsg.getData().get(i))).get("GunStatus").toString();
                } else {
                    GunStatus = "卡插入";
                }


                logger.info("处理" + gunNum + "号枪 状态" + GunStatus + "---[" + i + "]");

//todo 拿到油枪号，查询油枪号属于的油罐，根据油罐去查map中的元素值，然后更新元素
                double doubPumpReadout = 0.0;


                for (int j = 0; j < oilGunList.size(); j++) {

                    //判断获取油抢号
                    String oilgun = oilGunList.get(j).getOilgun().toString();
                   // System.out.println("oilgun :" + oilgun + "------------ gunNum :" + gunNum);
                    if (oilgun.equals(gunNum)) {
                        //根据油枪号拿到元素值
                        String no = oilGunList.get(j).getOilcan().toString();
                        Map<String, GasGunJpanel> jPanelMap = gunMap.get(no);
                          //System.out.println("油罐"+no+"进来判断"+gunNum+"号枪状态");
                        if (GunStatus.equals("提枪")) {
                            logger.info("[更新油枪" + oilgun + "提枪状态]");
                            DecimalFormat decimalFormat = new DecimalFormat("######0.00");

                            String amount = ((Map) (resultMsg.getData().get(i))).get("amount") == null ? "0.0" :decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("amount")).toString();//元
                            String qty = ((Map) (resultMsg.getData().get(i))).get("qty") == null ? "0.0" :decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("qty")).toString();//升
                            String Price =  ((Map) (resultMsg.getData().get(i))).get("Price") == null ? "0.0" :decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("Price")).toString();//元/升
                            logger.info("GasGun:" + gasMsg.getMessage());
                            //System.out.println("[更新油枪" + oilgun + "amout[" + amount + "]");
                            //System.out.println("[更新油枪" + oilgun + "qty[" + qty + "]");
                            //System.out.println("[更新油枪" + oilgun + "Price[" + Price + "]");


                            jPanelMap.get(oilgun).Label3.setText(qty);
                            jPanelMap.get(oilgun).Label5.setText(amount);
                            jPanelMap.get(oilgun).Label7.setText(Price);
                            jPanelMap.get(oilgun).hold1.setIcon(Common.createImageIcon(this.getClass(), "gas-gun.png"));

                        }else {
                            jPanelMap.get(oilgun).hold1.setIcon(Common.createImageIcon(this.getClass(), "green_8.png"));
                            Map map=(Map)resultMsg.getData().get(i);
                            DecimalFormat decimalFormat = new DecimalFormat("######0.00");

                            if (map!=null&&map.get("amount") != null&&map.get("qty") != null&&map.get("Price") != null) {
                                String amount = ((Map) (resultMsg.getData().get(i))).get("amount") == null ? "0.0" : decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("amount")).toString();//元
                                String qty = ((Map) (resultMsg.getData().get(i))).get("qty") == null ? "0.0" : decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("qty")).toString();//升
                                String Price = ((Map) (resultMsg.getData().get(i))).get("Price") == null ? "0.0" : decimalFormat.format(((Map) (resultMsg.getData().get(i))).get("Price")).toString();//元/升

                                logger.info("qty:"+qty);
                                jPanelMap.get(oilgun).Label3.setText(qty);
                                jPanelMap.get(oilgun).Label5.setText(amount);
                                jPanelMap.get(oilgun).Label7.setText(Price);
                            }
                            //String pumpReadout = ((Map) (resultMsg.getData().get(i))).get("PumpReadout").toString();//泵码
                            //   doubPumpReadout = Double.parseDouble(pumpReadout);
                            //java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
                            //  DecimalFormat decimalFormat = new DecimalFormat("######0.00");
                            //jPanelMap.get(oilgun).Label3.setText(qty);
                            //jPanelMap.get(oilgun).Label5.setText(amount);
                            //jPanelMap.get(oilgun).Label7.setText(Price);


                            //System.out.println("[" + oilgun + "号油枪<挂枪>]" + "amout[" + amount + "]");
                            //System.out.println("[" + oilgun + "号油枪<挂枪>]" + "qty[" + qty + "]");
                            //System.out.println("[" + oilgun + "号油枪<挂枪>]" + "Price[" + Price + "]");
                            //  jPanelMap.get(gunNum).Label2.setText("泵码： " + decimalFormat.format(doubPumpReadout));

                        }
                        //System.out.println("结束一次循环"+j);
                        // break;
                    }
                }
            }
        }
//endregion
    }

    @Test
    public void test() {
        String str = "11.111";
        double douStr = 1.111;
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        ////System.out.println("douStr:" + decimalFormat.format(douStr));
    }

    //region 判断油罐所属的油枪信息表
    public List<SysManageOilGunInfo> getOilGunByOilCan(String oilcan, List<SysManageOilGunInfo> oilGunList) {
        list = new ArrayList<SysManageOilGunInfo>();
        if (oilGunList.size() < 1) {
            ////System.out.println("getOilGunByOilCan  < 1");
            return list;
        }
        for (int i = 0; i < oilGunList.size(); i++) {
            //System.out.println("油罐号 :" + oilcan);
            //System.out.println("油枪表中的油罐号 : " + oilGunList.get(i).getOilcan());
            if (oilGunList.get(i).getOilcan().toString().equals(oilcan)) {
                //System.out.println("保存到当前的油抢表 oilgun :" + oilGunList.get(i).getOilgun());
                //oilGunList.remove(oilGunList.get(i));
                list.add(oilGunList.get(i));
            }
        }
        //System.out.println(" oilgun sum  : " + list.size());
        for (SysManageOilGunInfo sysManageOilGunInfo : list) {
            //System.out.println("new List :" + sysManageOilGunInfo.toString());
        }
        return list;
    }
//endregion

    /**
     * @param oilno 油罐号
     * @param wat   水体积
     * @param oil   油体积
     * @param air   空体积
     */
    public void updateBlpanel(int oilno, double wat, double oil, double air) {
        if (wat < 0.00000001) {
            wat= 0.00000001;
         }
        if (oil < 0.00000001) {
            oil= 0.00000001;
        }
        if (air < 0.00000001) {
            air= 0.00000001;
        }
        //System.out.println("油罐:[" + oilno + "]");
        double sumV = wat + oil + air;
        //System.out.println("----------------------");
        //System.out.println("水体积:" + wat);
        //System.out.println("油体积:" + oil);
        //System.out.println("空体积:" + air);
        //System.out.println("=========");
        //System.out.println("总体积:" + sumV);
        //System.out.println("=========");
        double wh = 188 / sumV * wat;//水
        double oh = 188 / sumV * oil;//油
        double ah = 188 / sumV * air;//空
        //System.out.println("水高度" + wh);
        //System.out.println("空高度" + ah);
        //System.out.println("油高度" + oh);

        int i = positMap.get(oilno);
        //System.out.println("根据油罐 "+oilno+"查到的 i :"+i);
        JpanelElement jpanelElement = elementMap.get(oilno);
        //  System.out.println("elementMap  ==="+elementMap.get(oilno));
        updateFill(i, jpanelElement, ah, oh, wh);
    }

    public void updateFill(int i, JpanelElement jpanelElement, double ah, double oh, double wh) {
        int air = Integer.parseInt(new java.text.DecimalFormat("0").format(ah));
        int wat = Integer.parseInt(new java.text.DecimalFormat("0").format(wh));
        int oil = Integer.parseInt(new java.text.DecimalFormat("0").format(oh));
        //System.out.println("air："+air);
        //System.out.println("wat："+wat);
        //System.out.println("oil："+oil);
        blpanel1 = jpanelElement.getAirJpanel();
        blpanel2 = jpanelElement.getOilJpanel();
        blpanel3 = jpanelElement.getWatJpanel();
//        blpanel1.removeAll();
        // blpanel1.repaint();
//          blpanel2.removeAll();
        //   blpanel2.repaint();
//        blpanel3.removeAll();
        //   blpanel3.repaint();

        blpanel1.setBackground(Color.decode("#A8D183"));
        blpanel2.setBackground(Color.decode("#EC6C00"));
        blpanel3.setBackground(Color.decode("#09DFF7"));
        blpanel1.setBounds(10, 40 + i * 230, 10, air); //空气高度

        if ((air + wat) > 180) {
            blpanel2.setBounds(10, 40 + i * 230 + air, 10, 0);
            ;//油
        } else {
            blpanel2.setBounds(10, 40 + i * 230 + air, 10, oil);
            ;//油
        }

        blpanel3.setBounds(10, 40 + i * 230 + air + (188 - (air + wat)), 10, wat);//水

    }

    @Test
    public void get1() {
        // updateBlpanel("1", 120, 1500, 380);
        double a = 1.5;

        //System.out.println(Integer.parseInt(new java.text.DecimalFormat("0").format(a)));
    }
}

class JpanelElement {
    JPanel watJpanel; //水JPanel
    JPanel oilJpanel;//油JPanel
    JPanel airJpanel;//空JPanel

    public JPanel getWatJpanel() {
        return watJpanel;
    }

    public void setWatJpanel(JPanel watJpanel) {
        this.watJpanel = watJpanel;
    }

    public JPanel getOilJpanel() {
        return oilJpanel;
    }

    public void setOilJpanel(JPanel oilJpanel) {
        this.oilJpanel = oilJpanel;
    }

    public JPanel getAirJpanel() {
        return airJpanel;
    }

    public void setAirJpanel(JPanel airJpanel) {
        this.airJpanel = airJpanel;
    }

    @Override
    public String toString() {
        return "Jpanel{" +
                ", watJpanel=" + watJpanel +
                ", oilJpanel=" + oilJpanel +
                ", airJpanel=" + airJpanel +
                '}';
    }
}
