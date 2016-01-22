package com.kld.app.view.acceptance;

import com.kld.app.service.IAcceptanceDeliveryService;
import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.SysConfig;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.ATG.domain.SysManageOilType;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.ATG.service.SysManageDic;
import com.kld.gsm.ATG.service.SysmanageService;
import com.kld.gsm.util.DateUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 出库单查询
 */

public class CkdcxPage  extends  JPanel{
    private JPanel jPanel;
    private static final Logger LOG = Logger.getLogger(CkdcxPage.class);
    private JTable table = null;
    private static final int COLUMN_COUNT = 14;
    /*private static final String[] CKD_TITLES = new String[] { "序号", "出库单号", "出库时间", "发油油库", "目的油站", "油品", "发货温度",
            "原发数量（L）", "原发数量（吨）", "交运时间", "车牌号码", "出库铅封号","状态","备注" };*/
    private static final String[] CKD_TITLES = new String[] { "类型", "出库单号", "出库时间", "发油油库", "目的油站", "油品编码","油品名称", "发货温度(℃)",
            "原发升数(L)", "原发数量(t)", "交运时间", "车牌号码", "出库铅封号","状态" };
    private IAcceptanceDeliveryService deliveryService;
    private com.kld.gsm.ATG.service.AcceptSevices coreAcceptSevices;
    private com.kld.gsm.ATG.service.SysManageDic coreSysdic;
    private com.kld.gsm.ATG.service.SysmanageService coreSysmanage;
    private IAcceptanceDeliveryService iAcceptanceDeliveryService;
    private IAcceptanceOdRegisterService odRegisterService;
    public void setPanel(JPanel centerPanel) throws Exception{
        this.deliveryService = (IAcceptanceDeliveryService) Context.getInstance().getBean("acceptanceDeliveryServiceImpl");
        jPanel=centerPanel;
        LoadData(centerPanel);
    }

    private void LoadData(JPanel centerPanel) throws Exception {
        centerPanel.setLayout(null);
        centerPanel.removeAll();
        centerPanel.repaint();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 800, 390);
        centerPanel.add(scrollPane);

        try {
            // 获取出库单数据
            List<AcceptanceDeliveryBills> bills = getAcceptanceDeliveryBill();
           /* if (bills.isEmpty()){
                LOG.info("bill is empty");
                return;
            }*/
            // 转为数组
            Object[][] billArray = getBillDataArray(bills);

            HashMap<String,Color>nameToColor=new HashMap<String, Color>();
            Color cc=Color.decode("#EE9897");
            Color jz=Color.decode("#DFEAF0");
            nameToColor.put("未卸油",Color.white);
            nameToColor.put("卸油中", cc);
            nameToColor.put("车进站",jz);

            ColorTableCellRenderer  r  =  new  ColorTableCellRenderer(13,nameToColor);
            r.setHorizontalAlignment(JLabel.CENTER);

            DefaultTableModel model=new DefaultTableModel(billArray,CKD_TITLES);
            table=new MyTable();
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setRowHeight(20);
            table.setCellSelectionEnabled(false);
            table.setDefaultRenderer(Object.class, r);

            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setRowSelectionAllowed(true);
            table.getColumnModel().getColumns();
            table.setModel(model);
            for (int i = 0; i < CKD_TITLES.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(150);
            }
            table.updateUI();
            scrollPane.setViewportView(table);
        } catch (Exception e) {
            LOG.error("获取出库单信息失败", e);
            throw new Exception("获取出库单信息失败", e);
        }
    }

    public void reLoad() throws Exception{
        LoadData(jPanel);
    }

    public void delete()  throws Exception{
        AcceptanceDeliveryBills bill=getSelectedRow();
        if (bill ==null){
            return;
        }else{
            int row = table.getSelectedRow();
            if (table.getValueAt(row, 13).toString().equals("卸油中")||table.getValueAt(row, 13).toString().equals("车进站")){
                JOptionPane.showMessageDialog(this,"处理中的记录不允许删除", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                return ;
            }
            Object[] options = {"确定","取消"};
            int response=JOptionPane.showOptionDialog(null, "确定要删除出库单记录", "删除出库单", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (response==0){
            this.deliveryService=(IAcceptanceDeliveryService)Context.getInstance().getBean(IAcceptanceDeliveryService.class);
            deliveryService.deleteByPrimaryKey(bill.getDeliveryno());
            setPanel(jPanel);}
            else{
                return;
            }
        }
    }
    public void DownLoadFromCenter() throws Exception{
        this.deliveryService=(IAcceptanceDeliveryService)Context.getInstance().getBean(IAcceptanceDeliveryService.class);
        this.coreAcceptSevices=(AcceptSevices)Context.getInstance().getBean(AcceptSevices.class);
        this.coreSysmanage=(SysmanageService) Context.getInstance().getBean(SysmanageService.class);
        this.coreSysdic=(SysManageDic)Context.getInstance().getBean(SysManageDic.class);
        //获取中心服务器地址
        //com.kld.gsm.ATG.domain.SysManageDict dic=coreSysdic.GetByCode("zxfwqdz");
        //获取站级编码
        com.kld.gsm.ATG.domain.SysManageDepartment dep=coreSysmanage.getdeptinfo();
        int i=0;
        try {
            i=coreAcceptSevices.getbillsfromcenter(SysConfig.regmoteIp(), dep.getSinopecnodeno());
        }catch (Exception e){
            throw new Exception("访问中心服务器失败:"+SysConfig.regmoteIp(),e);
        }
        JOptionPane.showMessageDialog(this, "下载到" + i + "条记录。", "信息提示", JOptionPane.INFORMATION_MESSAGE);
        LoadData(jPanel);
        LOG.info("xiazai");
    }

    /**
     * 进货验收
     * */
    public void jhys(JhysPage jhysPage) throws Exception {
        AcceptanceDeliveryBills bill=getinfo();
        if (odRegisterService==null){
            odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        }
        try{
            AcceptanceOdRegister odRegister= odRegisterService.selectByPrimaryKey(bill.getDeliveryno());
            if (odRegister==null){
                Object[] options = {"确定","取消"};
                int response=JOptionPane.showOptionDialog(null, "请确认罐车是否进站", "进货验收", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (response!=0){
                    return;
                }
            }
        }catch (Exception e){
            LOG.error(this.getClass()+"jhys:"+e.getMessage());
        }


        if (bill==null)return;
        boolean iswhd=false;
        if (deliveryService==null){
            deliveryService=Context.getInstance().getBean(IAcceptanceDeliveryService.class);
        }
        AcceptanceDeliveryBills bills=deliveryService.selectByPrimaryKey(bill.getDeliveryno());
        if (bills==null){
                iswhd=true;
        }

        if (bill==null)return;
        jhysPage.setCkdcxPage(this);
        Main.ckdcxPage=this;
        jhysPage.setCkdxx(bill,iswhd);
        //jhysPage.initOilTankInfo();
        jhysPage.getFrame().setVisible(true);
    }

    public AcceptanceDeliveryBills getinfo() throws Exception {
        AcceptanceDeliveryBills bill=getSelectedRow();
        return bill;
    }

    public void wlhdgl(GlwhdPage whdPage){
        AcceptanceDeliveryBills bill;
        try{
            bill = getinfo();
            if(bill==null)return;
            if (odRegisterService==null){
                odRegisterService=Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
            }
            try{
                AcceptanceOdRegister odRegister= odRegisterService.selectByPrimaryKey(bill.getDeliveryno());
                if (odRegister!=null){
                  JOptionPane.showMessageDialog(this,"处理中的出库单："+bill.getDeliveryno()+"无法建立关联。", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }catch (Exception e){
                LOG.error("WHDGL:"+e.getMessage());
            }
            if (bill.getType().equals("2")){
                JOptionPane.showMessageDialog(this,"手工出库单："+bill.getDeliveryno()+"无法建立关联。", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }catch (Exception eq){
            return;
        }

        if (bill==null)return;
        if (whdPage == null || !whdPage.getFrame().isActive()) {
            if (iAcceptanceDeliveryService==null){
                iAcceptanceDeliveryService=Context.getInstance().getBean(IAcceptanceDeliveryService.class);
            }
            List<AcceptanceOdRegister>ods=iAcceptanceDeliveryService.getNobillOds(bill.getOilno());
            if (ods==null||ods.isEmpty()){
                JOptionPane.showMessageDialog(this, "没有找到相关数据", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            whdPage = new GlwhdPage();
            whdPage.setCkdcxPage(this);
            whdPage.setBillno(bill.getDeliveryno());

            whdPage.getFrame().setVisible(true);
        }
    }

    /**
     * 从erp系统获取未处理出库单信息
     * 
     * @return
     * @throws Exception
     */
    public List<AcceptanceDeliveryBills> getAcceptanceDeliveryBill() throws Exception {
        List<AcceptanceDeliveryBills> bills = new ArrayList<AcceptanceDeliveryBills>();
        // 从本地系统查询数据
        bills=deliveryService.getbillsfromLocalByIsComplete("0");
        return bills;
    }

    /**
     * 转化出库单为数组
     * @param bills
     * @return
     */
    private Object[][] getBillDataArray(List<AcceptanceDeliveryBills> bills) {
        Object[][] billArray = null;
        if (!bills.isEmpty()) {
            billArray = new Object[bills.size()][COLUMN_COUNT];
            for (int i = 0; i < bills.size(); i++) {
                AcceptanceDeliveryBills bill = bills.get(i);
                billArray[i][0] = bill.getType().equals("1")?"进货验收":"无货单验收";;
                billArray[i][1] = bill.getDeliveryno();
                billArray[i][2] = DateUtil.getDate(bill.getDeliverytime(), "yyyy-MM-dd HH:mm:ss");
                billArray[i][3] = bill.getFromdepotname();
                billArray[i][4] = bill.getTostationname();
                billArray[i][5] = bill.getOilno();
               try {
                    if (odRegisterService==null){
                        odRegisterService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
                    }
                    SysManageOilType oilType=odRegisterService.selectOilType(bill.getOilno());
                    if (oilType!=null){
                        billArray[i][6] = oilType.getOilname();
                    }
                }catch (Exception e){
                    LOG.error(e.getMessage());
                }
                billArray[i][7] = bill.getDeliverytemp();
                billArray[i][8] = bill.getPlanl();
                billArray[i][9] = bill.getPlanton();
                billArray[i][10] = DateUtil.getDate(bill.getShipmenttime(), "yyyy-MM-dd HH:mm:ss");
                billArray[i][11] = bill.getCarno();
                billArray[i][12] = bill.getOutsealno();
                billArray[i][13]=bill.getBegintime()!=null?"卸油中":"未卸油";
                if(bill.getBegintime()==null) {
                    if (odRegisterService == null) {
                        odRegisterService = Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
                    }
                    try {
                        AcceptanceOdRegister odRegister = odRegisterService.selectByPrimaryKey(bill.getDeliveryno());
                        if (odRegister!=null&& (bill.getType().equals("1")&&odRegister.getDeliveryno()!=null
                                ||(!bill.getType().equals("1")&&odRegister.getDeliveryno()==null))){
                            billArray[i][13]="车进站";
                        }
                    }catch (Exception e){
                        LOG.error(this.getClass()+"billArray[i][12] Set value failed");
                    }
                }

                //billArray[i][13]=bill.getType().equals("1")?"":"无货单验收";
            }
        }
        return billArray;
    }

    /**
     * 获取选中的出库单
     * 
     * @return
     */
    public AcceptanceDeliveryBills getSelectedRow() throws Exception {
        AcceptanceDeliveryBills bill = new AcceptanceDeliveryBills();
        int selectRows = table.getSelectedRows().length;// 取得用户所选行的行数
        TableModel tableModel = table.getModel();
        if (selectRows > 1) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int row = table.getSelectedRow(); // 取得用户所选单行
        if (row == -1){
            JOptionPane.showMessageDialog(this,"请选择一条记录", "信息提示", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        try {
            bill.setDeliveryno(tableModel.getValueAt(row, 1).toString());
            ////System.out.println("选择出库单号："+bill.getDeliveryno());
            if (!table.getValueAt(row, 2).toString().isEmpty())
            {
                Date dt=null;
                dt =DateUtil.convertStringToDate(table.getValueAt(row, 2).toString());
                bill.setDeliverytime(dt);
            }
            bill.setFromdepotname(table.getValueAt(row, 3) == null ? null : table.getValueAt(row, 3).toString());
            bill.setTostationname(table.getValueAt(row, 4) == null ? null : table.getValueAt(row, 4).toString());
            bill.setOilno(table.getValueAt(row, 5) == null ? null : table.getValueAt(row, 5).toString());
            if (table.getValueAt(row, 7)!=null&&!table.getValueAt(row, 7).toString().isEmpty())
            {
                bill.setDeliverytemp(Double.parseDouble(table.getValueAt(row, 7) == null ? null : table.getValueAt(row, 7).toString()));
            }
            if (table.getValueAt(row, 8)!=null&&!table.getValueAt(row, 8).toString().isEmpty()){
                bill.setPlanl(Double.parseDouble(table.getValueAt(row, 8) == null ? null : table.getValueAt(row, 8).toString()));
            }

            if (null!=table.getValueAt(row, 9)&&!table.getValueAt(row, 9).toString().isEmpty()){
                bill.setPlanton(Double.parseDouble(table.getValueAt(row, 9) == null ? null : table.getValueAt(row, 9).toString()));
            }
            if (null!=table.getValueAt(row, 10)&&!table.getValueAt(row, 10).toString().isEmpty()){
                Date jt=null;
                jt  =DateUtil.convertStringToDate(table.getValueAt(row,10).toString());
                bill.setShipmenttime(jt);
            }
            bill.setCarno(table.getValueAt(row, 11) == null ? null : table.getValueAt(row, 11).toString());
            bill.setOutsealno(table.getValueAt(row, 12) == null ? null : table.getValueAt(row, 12).toString());
/*
            bill.setType("".equals(table.getValueAt(row,13))?"1":"2");
*/
            bill.setType("进货验收".equals(table.getValueAt(row,0))?"1":"2");
        } catch (NumberFormatException e) {
            LOG.error("数字格式错误");
            throw new Exception("数字格式错误", e);
        } catch (ParseException e) {
            LOG.error("日期事件格式错误");
            throw new Exception("日期事件格式错误", e);
        }
        // 进行相关处理
        return bill;
    }
}


