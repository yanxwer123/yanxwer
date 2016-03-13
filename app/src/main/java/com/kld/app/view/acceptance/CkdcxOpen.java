package com.kld.app.view.acceptance;

import com.kld.app.service.IAcceptanceDeliveryService;
import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.ATG.domain.SysManageOilType;
import com.kld.gsm.util.DateUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by niyang on 2016/2/19.
 */
public class CkdcxOpen extends JOptionPane {
    private JDialog frame;
    private JTable table = null;
    private JhysNewPage jnewpage;
    private JButton okbtn;
    private JButton cancelbtn;
    private static final Logger LOG = Logger.getLogger(CkdcxPage.class);
    private static final int COLUMN_COUNT = 14;

    private static final String[] CKD_TITLES = new String[] { "类型", "出库单号", "出库时间", "发油油库",
            "目的油站", "油品编码","油品名称", "发货温度(℃)","原发升数(L)", "原发数量(t)", "交运时间",
            "车牌号码", "出库铅封号","状态" };
    private IAcceptanceDeliveryService deliveryService;
    private com.kld.gsm.ATG.service.AcceptSevices coreAcceptSevices;
    private com.kld.gsm.ATG.service.SysManageDic coreSysdic;
    private com.kld.gsm.ATG.service.SysmanageService coreSysmanage;
    private IAcceptanceDeliveryService iAcceptanceDeliveryService;
    private IAcceptanceOdRegisterService odRegisterService;
    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    public CkdcxOpen(JhysNewPage jhysNewPage){
        this.jnewpage=jhysNewPage;
        Init();
    }

    public void Init(){

        this.setLayout(null);
        this.removeAll();
        this.repaint();


        frame = new JDialog();
        frame.setModal(true);
        frame.getContentPane().setFont(new Font("宋体", Font.PLAIN, 12));
        frame.setTitle("选择出库单");
        frame.setBounds(100, 100, 800, 480);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.getContentPane().setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 800, 390);
        frame.add(scrollPane);
        okbtn=new JButton("确定");
        okbtn.setBounds(508,395,90,25);
        okbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //jnewpage.addbill();
                AcceptanceDeliveryBills bill=getSelectedRow();
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
                    jnewpage.addbill(bill);
                    frame.dispose();
                }catch (Exception e1){
                    LOG.error(this.getClass()+"jhys:"+e1.getMessage());
                }
            }
        });
        frame.add(okbtn);
        cancelbtn=new JButton("取消");
        cancelbtn.setBounds(638,395,90,25);
        cancelbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(cancelbtn);

        Main.setCenter(frame);

        try {
            // 获取出库单数据
            List<AcceptanceDeliveryBills> bills = getAcceptanceDeliveryBill();

            // 转为数组
            Object[][] billArray = getBillDataArray(bills);

            HashMap<String,Color> nameToColor=new HashMap<String, Color>();
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
         }
    }

    /**
     * 从系统获取未处理出库单信息
     *
     * @return
     * @throws Exception
     */
    public List<AcceptanceDeliveryBills> getAcceptanceDeliveryBill() throws Exception {
        List<AcceptanceDeliveryBills> bills = new ArrayList<AcceptanceDeliveryBills>();
        // 从本地系统查询数据
        if (deliveryService==null){
            deliveryService=Context.getInstance().getBean(IAcceptanceDeliveryService.class);
        }
        bills=deliveryService.getbillsfromLocalByIsComplete("0");
        return bills;
    }

    /**
     * 获取选中的出库单
     *
     * @return
     */
    public AcceptanceDeliveryBills getSelectedRow() {
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
            bill.setType("进货验收".equals(table.getValueAt(row,0))?"1":"2");
        } catch (NumberFormatException e) {
            LOG.error("数字格式错误"+e.getMessage());
        } catch (ParseException e) {
            LOG.error("日期事件格式错误"+e.getMessage());

        }
        // 进行相关处理
        return bill;
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
                billArray[i][8] = new DecimalFormat("0").format(bill.getPlanl());
                Double a = bill.getPlanton();
                if (a==null){
                    a=0.000;
                }
                billArray[i][9] = new DecimalFormat("0.000").format(a);
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
                        if (odRegister!=null&& ((bill.getType().equals("1")&&odRegister.getDeliveryno()!=null)
                                ||(!bill.getType().equals("1")&&odRegister.getDeliveryno()==null))){
                            billArray[i][13]="车进站";
                        }
                    }catch (Exception e){
                        LOG.error(this.getClass()+"billArray[i][12] Set value failed");
                    }
                }
            }
        }
        return billArray;
    }
}
