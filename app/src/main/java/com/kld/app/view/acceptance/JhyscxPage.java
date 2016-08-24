package com.kld.app.view.acceptance;

import com.kld.app.service.IAcceptanceDeliveryService;
import com.kld.app.service.IAcceptanceOdRegisterInfoService;
import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.SysConfig;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageDepartment;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.Socket.Constants;
import com.kld.app.socket.ob.ConcreteWatched;
import com.kld.app.socket.ob.Watched;
import com.kld.app.socket.ob.Watcher;
import com.kld.gsm.Socket.protocol.GasMsg;
import com.kld.gsm.Socket.protocol.ResultMsg;
import com.kld.gsm.Socket.uitls.ResultUtils;
import com.kld.gsm.util.DateUtil;
import com.kld.gsm.util.JsonMapper;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * 进货验收单
 */

public class JhyscxPage extends  JPanel implements Watcher {
    private JPanel cpanel;
    SysManageCanInfoService tankInfoService=(SysManageCanInfoService) (Context.getInstance().getBean("SysManageTankInfoService"));
    private static final Logger LOG = Logger.getLogger(JhyscxPage.class);
    private static final String[] CKD_TITLES = new String[] { "序号", "出库单号","损溢率(‰)","卸入罐号","到站时间","验收模式","验收状态", "出库时间", "发油油库", "目的油站", "油品", "发货温度(℃)",
            "原发升数(L)", "原发数量(kg)", "交运时间", "车牌号码", "出库铅封号","班次" };
    private JTextField ckdhField;
    private JTextField ghField;
    private JTextField sylField;
    private JComboBox ygbhJComboBox = new JComboBox();
    private JComboBox sylCombo;
    private JXDatePicker xyrqqField;
    private JXDatePicker xyrqzField;
    private IAcceptanceOdRegisterInfoService service;
    private IAcceptanceDeliveryService deliveryService;
    private IAcceptanceOdRegisterService registerService;
    private AcceptSevices acceptSevices;
    private SysmanageService sysmanageService;
    private String cancelno;
    private JTable table;
    private JScrollPane scrollPane;
    private int timeout;
    private boolean timeoutflag=true;
    /**
     * @throws Exception
     * @wbp.parser.entryPoint
     */
    public void setPanel(JPanel centerPanel) throws Exception {
        cpanel=centerPanel;
        setYgbhComboBox();
        centerPanel.removeAll();
        centerPanel.repaint();
        centerPanel.setLayout(null);
        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 55, 800, 335);
        centerPanel.add(scrollPane);
        JPanel cxPanel = new JPanel();
        cxPanel.setBounds(0, 0, 800, 55);
        cxPanel.setLayout(null);
        centerPanel.add(cxPanel);
        JLabel ckdhLabel = new JLabel("出库单号：");
        ckdhLabel.setBounds(10, 7, 72, 15);
        cxPanel.add(ckdhLabel);

        ckdhField = new JTextField();
        ckdhField.setColumns(10);
        ckdhField.setBounds(85, 5, 150, 21);
        cxPanel.add(ckdhField);

        JLabel ghLabel = new JLabel("罐    号：");
        ghLabel.setBounds(10, 32, 72, 15);
        cxPanel.add(ghLabel);

        ygbhJComboBox.setBounds(85, 30, 150, 21);
        cxPanel.add(ygbhJComboBox);

        JLabel xyrqLabel = new JLabel("卸油日期：");
        xyrqLabel.setBounds(238, 7, 72, 15);
        cxPanel.add(xyrqLabel);


        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        xyrqqField = new JXDatePicker();
        xyrqqField.getEditor().setEditable(false);
        xyrqqField.setPreferredSize(new Dimension(200, 100));
        xyrqqField.setFormats(dateFormat);
        xyrqqField.setDate(cal.getTime());
        xyrqqField.setBounds(313, 5, 150, 21);
        cxPanel.add(xyrqqField);

        JLabel zLabel = new JLabel("至");
        zLabel.setBounds(465, 7, 20, 15);
        cxPanel.add(zLabel);

        xyrqzField = new JXDatePicker();
        xyrqzField.getEditor().setEditable(false);
        xyrqzField.setPreferredSize(new Dimension(200, 100));
        xyrqzField.setBounds(488, 5, 150, 21);
        xyrqzField.setDate(new Date());
        xyrqzField.setFormats(dateFormat);
        cxPanel.add(xyrqzField);
        cxPanel.updateUI();

        JLabel sylLabel = new JLabel("损 溢 率：");
        sylLabel.setBounds(238, 32, 72, 15);
        cxPanel.add(sylLabel);

        sylCombo = new JComboBox();
        sylCombo.addItem("请选择");
        sylCombo.addItem("大于");
        sylCombo.addItem("大于等于");
        sylCombo.addItem("等于");
        sylCombo.addItem("小于");
        sylCombo.addItem("小于等于");
        sylCombo.setBounds(313, 30, 150, 21);
        cxPanel.add(sylCombo);

        sylField = new JTextField();
        sylField.setColumns(10);
        sylField.setBounds(488, 30, 150, 21);
        cxPanel.add(sylField);

        JButton xzsjBtn = new JButton("查询");
        String deliveryNo = ckdhField.getText().trim();
        Date xyrqq = xyrqqField.getDate();
        Date xyrqz = xyrqzField.getDate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(xyrqz);
        calendar.add(calendar.DATE, 1);//
        queryRegisterInfo(deliveryNo, xyrqq,  calendar.getTime(),ygbhJComboBox.getSelectedItem().toString(),sylCombo.getSelectedItem().toString(),sylField.getText());

        xzsjBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String deliveryNo = ckdhField.getText().trim();
                Date xyrqq = xyrqqField.getDate();
                Date xyrqz = xyrqzField.getDate();
                if(null==xyrqq || "".equals(xyrqq)){
                    JOptionPane.showMessageDialog(null, "请输入开始时间！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else if (null==xyrqz || "".equals(xyrqz)){
                    JOptionPane.showMessageDialog(null, "请输入结束时间！", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (xyrqz.compareTo(xyrqq) < 0) {
                    JOptionPane.showMessageDialog(null, "开始时间不能晚于结束时间,请重新选择", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                try {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(xyrqz);
                    calendar.add(calendar.DATE, 1);//
                    queryRegisterInfo(deliveryNo, xyrqq,  calendar.getTime(),ygbhJComboBox.getSelectedItem().toString(),sylCombo.getSelectedItem().toString(),sylField.getText());
                } catch (Exception e1) {
                    LOG.error(e1);
                }
            }
        });
        xzsjBtn.setBounds(650, 30, 83, 23);
        cxPanel.add(xzsjBtn);
        queryRegisterInfo(deliveryNo, xyrqq,  calendar.getTime(),ygbhJComboBox.getSelectedItem().toString(),sylCombo.getSelectedItem().toString(),sylField.getText());
    }

    public void LoadData(){
        String deliveryNo = ckdhField.getText().trim();
        Date xyrqq = xyrqqField.getDate();
        Date xyrqz = xyrqzField.getDate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(xyrqz);
        calendar.add(calendar.DATE, 1);//
        try {
            queryRegisterInfo(deliveryNo, xyrqq,  calendar.getTime(),ygbhJComboBox.getSelectedItem().toString(),sylCombo.getSelectedItem().toString(),sylField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询写有信息
     * @param deliveryNo
     * @param xyrqq
     * @param xyrqz
     */
    public void queryRegisterInfo(String deliveryNo, Date xyrqq, Date xyrqz,String oilcan,String symbol,String dischargeRate) throws Exception {
        try {
            // 查询卸油信息
            if (service == null) {
                service =  Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
            }
            if (deliveryService == null) {
                deliveryService =  Context.getInstance().getBean(IAcceptanceDeliveryService.class);
            }
            if (registerService == null) {
                registerService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
            }
            if ("".equals(deliveryNo)){
                deliveryNo = null;
            }
            if(table!=null){
                scrollPane.remove(table);
            }
            if("全部".equals(oilcan)){
                oilcan="";
            }
            List<HashMap<String,?>> list = registerService.selectjhysbynoanddate(deliveryNo, xyrqq, xyrqz,oilcan,symbol,dischargeRate);
            table=getTable(list);
            table.updateUI();
            scrollPane.setViewportView(table);
            table.updateUI();
        } catch (Exception e1) {
            LOG.error("查询卸油记录失败", e1);
            throw new Exception("查询卸油记录失败", e1);
        }
    }



    /**
     * 作废进货单验收
     * */
    public void deleteJhdys(){

        LOG.info("作废进货验收");
        int selectRow= table.getSelectedRow();// 取得用户所选行的行数
        TableModel tableModel = table.getModel();

        //region注册观察者开始

        Watched watch = ConcreteWatched.getInstance();
        watch.addWetcher("A", this);
        //endregion

        //region 调用ctrl超时定时器
        final Timer timer2=new Timer(1000,null);
        timer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeout--;
                if (timeoutflag && timeout < 0) {
                    timer2.stop();
                    JOptionPane.showMessageDialog(null, "作废卸油失败", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (!timeoutflag) {
                    timer2.stop();
                }
            }
        });
        //endregion


        if (selectRow<0){
            JOptionPane.showMessageDialog(this, "请选择一条记录", "信息提示", JOptionPane.ERROR_MESSAGE);
        }else  {
            String billno=tableModel.getValueAt(selectRow,1).toString();
            Object[] options = {"确定","取消"};
            int response=JOptionPane.showOptionDialog(null, "确定要作废出库单号为" +billno+"的卸油记录", "作废卸油登记单", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (response==0){
                if (registerService == null) {
                    registerService =  Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
                }
                AcceptanceOdRegister odRegister=registerService.selectByPrimaryKey(billno);
                if (odRegister!=null){
                    if (odRegister.getShift()==null||"".equals(odRegister.getShift())){
                        //nothing
                    }else{
                        JOptionPane.showMessageDialog(this, "已做班结的卸油记录，不允许作废。", "信息提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                cancelno=odRegister.getManualNo();
                try {
                    GasMsg gasMsg = ResultUtils.getInstance().sendSUCCESS(Main.sign, new ArrayList(), Constants.PID_Code.A15_10005.toString());
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("1", billno);
                    map.put("2", "1");

                    ResultMsg msg =new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
                    List s = new ArrayList();
                    s.add(map);
                    msg.setData(s);
                    gasMsg.setMessage(new JsonMapper().toJson(msg));
                    Main.CC.writeAndFlush(gasMsg);
                }catch (Exception e2){
                    ////System.out.println("通知主调度失败");
                }
            }else{

            }
        }
    }
    /**
     * 打印进货单
     */
    public void printJhdys(){
        if(table==null||table.getRowCount()==0)return;
        int selectRow= table.getSelectedRow();// 取得用户所选行的行数
        TableModel tableModel = table.getModel();

        if (selectRow <0){
            JOptionPane.showMessageDialog(this, "请选择一条记录", "信息提示", JOptionPane.INFORMATION_MESSAGE);
        }else  {
            JhyscxPageDetailFrame jhck=new JhyscxPageDetailFrame();
            PrintUIComponent myFrame = new PrintUIComponent();
            myFrame.init(tableModel.getValueAt(selectRow,1).toString());
        }
    }
    /**
     * 查看打印进货单
     */
    public void ckJhdys(){
        // System.out.println("打印进货验收");
        if(table==null||table.getRowCount()==0)return;
        int selectRow= table.getSelectedRow();// 取得用户所选行的行数
        TableModel tableModel = table.getModel();

        if (selectRow<0){
            JOptionPane.showMessageDialog(this, "请选择一条记录", "信息提示", JOptionPane.INFORMATION_MESSAGE);
        }else  {
            JhyscxPageDetailFrame jhck=new JhyscxPageDetailFrame();
            //System.out.println("出库单号"+tableModel.getValueAt(selectRow,1).toString());
            SeeUIComponent myFrame = new SeeUIComponent();
            myFrame.init(tableModel.getValueAt(selectRow, 1).toString());
            // jhck.initOilCan(tableModel.getValueAt(selectRow,1).toString());
            // jhck.getFrame().setVisible(true);
        }
    }

    public void showdetail(){
        if(table==null||table.getRowCount()== 0)return;
        int selectRow= table.getSelectedRow();// 取得用户所选行的行数
        TableModel tableModel = table.getModel();

        if (selectRow<0){
            JOptionPane.showMessageDialog(this, "请选择一条记录", "信息提示", JOptionPane.INFORMATION_MESSAGE);
        }else  {
            JhyscxPageDetailFrame jhck=new JhyscxPageDetailFrame();
            jhck.initOilCan(tableModel.getValueAt(selectRow, 1).toString());
            jhck.getFrame().setVisible(true);
        }
    }

    public String getSelectedDeliveryNo(){
        TableModel model = table.getModel();
        int selectRows = table.getSelectedRows().length;// 取得用户所选行的行数
        if (selectRows > 1) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int selectedRowIndex = table.getSelectedRow(); // 取得用户所选单行
        if (selectedRowIndex == -1){
            return null;
        }
        return model.getValueAt(selectedRowIndex, 1).toString().trim();
    }

    private Object[][] getDataArray(List<HashMap<String,?>> bills) {
        Object[][] billArray = null;
        if (!bills.isEmpty()) {
            billArray = new Object[bills.size()][CKD_TITLES.length];
            for (int i = 0; i < bills.size(); i++) {
                HashMap<String,?> bill = bills.get(i);
//                { "序号", "出库单号","损益率","卸入罐号","到站时间","验收模式","验收状态", "出库时间", "发油油库", "目的油站", "油品", "发货温度(℃)",
//                        "原发升数(L)", "原发数量(t)", "交运时间", "车牌号码", "出库铅封号","班次" };
                billArray[i][0] = i;
                billArray[i][1] = bill.get("DeliveryNo").toString();
                Date DeliveryTime=(Date)bill.get("DeliveryTime");
                //损益率，卸入罐号，到站时间，验收模式，验收状态
                if(bill.get("Dischargerate")==null||"".equals(bill.get("Dischargerate"))){
                    billArray[i][2] = "";
                }else {
                    //bill.get("Dischargerate").toString()
                    billArray[i][2] = new DecimalFormat("######0.00").format(Double.parseDouble(bill.get("Dischargerate").toString()) * 1000);
                }
                billArray[i][3] = bill.get("OilCan");
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //billArray[i][4] = isNotEmpty(bill.get("ArrivalTime"))?sd.format(bill.get("ArrivalTime")):"";
                billArray[i][5] = "1".equals(bill.get("ServiceLevel")+"")?"罐车交接":"地罐交接";
                if(!isNotEmpty(bill.get("BeginTime"))) {
                    billArray[i][6] ="未验收";
                }else if(isNotEmpty(bill.get("BeginTime"))&& !isNotEmpty(bill.get("EndTime"))){
                    billArray[i][6] ="验收中";
                }else if(isNotEmpty(bill.get("BeginTime")) &&isNotEmpty(bill.get("EndTime"))){
                    billArray[i][6] ="已验收";
                }
                if (DeliveryTime!=null){
                    billArray[i][7]=DateUtil.getDate(DeliveryTime,"yyyy-MM-dd HH:mm:ss");
                }

                billArray[i][8] = bill.get("FromDepotName");
                billArray[i][9] = bill.get("ToStationName");
                billArray[i][10] = bill.get("OilName");
                billArray[i][11] = bill.get("DeliveryTemp");

                billArray[i][12] = new DecimalFormat("0").format(bill.get("PlanL"));
                billArray[i][13] = new DecimalFormat("######0").format(bill.get("PlanTon"));

                Date ShipmentTime =(Date)bill.get("ShipmentTime");
                if (ShipmentTime!=null){
                    billArray[i][14]=DateUtil.getDate(ShipmentTime, "yyyy-MM-dd HH:mm:ss");
                }
                billArray[i][15] = bill.get("CarNo");
               // billArray[i][16] = bill.get("OutSealNo");
                //region 获取班次号
                if (registerService == null) {
                    registerService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
                }
                AcceptanceOdRegister odRegister=registerService.selectByManualNoADeliveryNo(bill.get("DeliveryNo").toString());
                if (odRegister!=null){
                    billArray[i][16]=odRegister.getPlumbunbankoperator();
                }
                billArray[i][4]=isNotEmpty(odRegister.getInstationtime())?sd.format(odRegister.getInstationtime()):"";
                billArray[i][17]=odRegister.getShift()==null?"":odRegister.getShift().toString();
                //endregion
            }
        }
        return billArray;
    }

    private JTable getTable(List list){

        Object[][] billArray=getDataArray(list);
        DefaultTableModel model=new DefaultTableModel(billArray,CKD_TITLES);
        table=new MyTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        table.setRowHeight(20);
        table.setDefaultRenderer(Object.class, render);
        FitTableColumns(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(model);
        TableColumn column= table.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setWidth(0);
        column.setPreferredWidth(0);
        for (int i = 0; i < CKD_TITLES.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
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
            if(width<80){
                width=80;
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
        if (gasMsg.getPid().equals("A15_10005")){
            ResultMsg resultMsg = new JsonMapper().fromJson(gasMsg.getMessage(), ResultMsg.class);
            if ("1".equals(resultMsg.getResult())) {timeout=-1;return;}
            timeoutflag=false;
            registerService.cancelAcceptOdreg(cancelno);
            try {
                if (sysmanageService==null){
                    sysmanageService=Context.getInstance().getBean(SysmanageService.class);
                }
                SysManageDepartment department=sysmanageService.getdeptinfo();
                if (acceptSevices==null){
                    acceptSevices=Context.getInstance().getBean(AcceptSevices.class);
                }
                acceptSevices.noticeCenterDelbillno(SysConfig.regmoteIp(),cancelno,department.getSinopecnodeno());
            } catch (Exception es) {
                LOG.error(es.getMessage());
            }
            try {
                setPanel(cpanel);
                LoadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isNotEmpty(Object s){
        if(s!=null&&!"".equals(s)){
            return true;
        }
        return false;
    }
    private void setYgbhComboBox()
    {

        ygbhJComboBox.removeAllItems();
        List<SysManageCanInfo> CanInfoList=tankInfoService.selectAll();
        ygbhJComboBox.addItem("全部");
        for (SysManageCanInfo item:CanInfoList){
            ygbhJComboBox.addItem(item.getOilcan());
        }
    }
}
