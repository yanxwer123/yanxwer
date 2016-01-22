package com.kld.app.view.acceptance;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;


import com.kld.app.userctrl.JTimePanel;
import com.kld.app.util.*;
import com.kld.app.view.main.Main;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.util.V20Utils;
import org.apache.log4j.Logger;

import com.kld.app.service.DailyTradeAccountService;
import com.kld.app.service.IAcceptanceOdRegisterInfoService;
import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.domain.AcceptanceOdRegisterInfo;
import org.jdesktop.swingx.JXDatePicker;


/**
 * 进货验收--无货单验收
 */
public class JhysPageShlrFrame extends JOptionPane {
    private static final Logger LOG = Logger.getLogger(JhysPageShlrFrame.class);// 录入方式手工录入
    public static final int ENTERTYPE_FSGLR = 0;
    // 录入方式手工录入
    public static final int ENTERTYPE_SGLR = 1;
    // 开始卸油
    public static final String TRANSSTATUS_BEGIN = "B";
    // 结束卸油
    public static final String TRANSSTATUS_END = "E";
    private static  String OIL_TYPE_1 = "01";
    private static final int text_height = 21;
    private static final int text_width = 126;
    private JDialog frame;
    private JTextField yszgField;
    private AcceptanceDeliveryBills bills;
    private JhysPage jhys;
    private JTextField jytjField;
    private JTextField bztjField;
    private JTextField pjwdField;
    private JTextField hcYszgField;
    private JTextField hcJytjField;
    private JTextField hcBztjField;
    private JTextField hcPjwdField;
    private JTimePanel beginTime;
    private JTimePanel endTime;
    private DateChooser dateChooser;
    private String deliveryNo, oilNo;
    private Integer canNo;
    private Double yfss;
    private DailyTradeAccountService dailyTradeAccountService;
    private IAcceptanceOdRegisterService odRegisterService;
    private IAcceptanceOdRegisterInfoService odRegisterInfoService;
    private AcceptanceOdRegisterInfo acceptanceOdRegisterInfo;
    private CkdcxPage ckdcxPage;

    public boolean iswhd;
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public JDialog getFrame() {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    public CkdcxPage getCkdcxPage() {
        return ckdcxPage;
    }

    public void setCkdcxPage(CkdcxPage ckdcxPage) {
        this.ckdcxPage = ckdcxPage;
    }

    public AcceptanceOdRegisterInfo getAcceptanceOdRegisterInfo() {
        return acceptanceOdRegisterInfo;
    }

    public JhysPage getJhys() {
        return jhys;
    }

    public void setJhys(JhysPage jhys) {
        this.jhys = jhys;
    }

    /**
     * Create the application.
     * 
     * @param oilNo
     * @param deliveryNo
     */

    public JhysPageShlrFrame(String deliveryNo, String oilNo, Integer canNo, Double yfss,AcceptanceDeliveryBills bill,AcceptanceOdRegisterInfo info) {

        this.deliveryNo = deliveryNo;
        this.oilNo = oilNo;
        this.canNo = canNo;
        this.yfss = yfss;
        this.bills=bill;
        this.acceptanceOdRegisterInfo=info;
        initialize();
        this.odRegisterService = (IAcceptanceOdRegisterService) Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        this.odRegisterInfoService = (IAcceptanceOdRegisterInfoService) Context.getInstance().getBean(IAcceptanceOdRegisterInfoService.class);
        this.dailyTradeAccountService = (DailyTradeAccountService) Context.getInstance().getBean(DailyTradeAccountService.class);
        OIL_TYPE_1=odRegisterService.selectOilType(bill.getOilno()).getOiltype().toString();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("手工录入");
        frame.setBounds(100, 100, 368, 441);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.getContentPane().setLayout(null);

        Main.setCenter(frame);
        JPanel qcsj = new JPanel();
        qcsj.setBackground(UIManager.getColor("Button.light"));
        qcsj.setBounds(10, 10, 342, 144);
        frame.getContentPane().add(qcsj);
        qcsj.setLayout(null);

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 22, 342, 112);
        qcsj.add(separator);

        JLabel qcsjLabel = new JLabel("前尺数据");
        qcsjLabel.setBounds(10, 3, 54, 15);
        qcsj.add(qcsjLabel);

        JLabel yszg = new JLabel("油水总高(mm):");
        yszg.setBounds(50, 33, 85, 15);
        qcsj.add(yszg);

        JLabel jytj = new JLabel("净油体积(L):");
        jytj.setBounds(59, 58, 85, 15);
        qcsj.add(jytj);

        JLabel bztj = new JLabel("标准体积(L):");
        //bztj.setBounds(60, 85, 75, 15);
        bztj.setBounds(59, 113, 85, 15);
        qcsj.add(bztj);

        JLabel pjwd = new JLabel("平均温度(℃):");
        //pjwd.setBounds(60, 113, 75, 15);
        pjwd.setBounds(50, 85, 85, 15);
        qcsj.add(pjwd);

        SuperDoubleDocument superDoubleDocument=new SuperDoubleDocument(3,2);
        SuperDoubleDocument superDoubleDocument2=new SuperDoubleDocument(3,2);
        superDoubleDocument.isfs=true;
        superDoubleDocument.set_max(100d);
        superDoubleDocument.set_min(-100d);
        superDoubleDocument2.isfs=true;
        superDoubleDocument2.set_max(100d);
        superDoubleDocument2.set_min(-100d);
        final List<JTextField> txts=new ArrayList<JTextField>();
        yszgField = new JTextField();
        yszgField.setBounds(139, 27, text_width, text_height);
        yszgField.setDocument(new SuperDoubleDocument(6,2));
        qcsj.add(yszgField);
        yszgField.setColumns(10);
        txts.add(yszgField);
        yszgField.setText(acceptanceOdRegisterInfo.getBeginoilheight() == null ? "" : acceptanceOdRegisterInfo.getBeginoilheight().toString());

        jytjField = new JTextField();
        jytjField.setColumns(10);
        jytjField.setDocument(new SuperDoubleDocument(6,2));
        jytjField.setBounds(139, 55, text_width, text_height);
        qcsj.add(jytjField);
        txts.add(jytjField);
        jytjField.setText(acceptanceOdRegisterInfo.getBeginoill() == null ? null : acceptanceOdRegisterInfo.getBeginoill().toString());

        bztjField = new JTextField();
        bztjField.setColumns(10);
        //bztjField.setDocument(new DoubleDocument());
        //bztjField.setBounds(129, 83, text_width, text_height);
        bztjField.setBounds(139, 113, text_width, text_height);
        bztjField.setText(acceptanceOdRegisterInfo.getBeginv20l() == null ? null : acceptanceOdRegisterInfo.getBeginv20l().toString());
        bztjField.setEnabled(false);
        qcsj.add(bztjField);
        txts.add(bztjField);


        pjwdField = new JTextField();
        pjwdField.setDocument(superDoubleDocument);
        pjwdField.setColumns(10);
        //pjwdField.setBounds(129, 113, text_width, text_height);
        pjwdField.setBounds(139, 83, text_width, text_height);
        qcsj.add(pjwdField);
        txts.add(pjwdField);
        pjwdField.setText(acceptanceOdRegisterInfo.getBegintemperature() == null ? "" : acceptanceOdRegisterInfo.getBegintemperature().toString());


        JPanel hcsj = new JPanel();
        hcsj.setBackground(UIManager.getColor("Button.light"));
        hcsj.setBounds(10, 167, 342, 144);
        frame.getContentPane().add(hcsj);
        hcsj.setLayout(null);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(0, 23, 342, 121);
        hcsj.add(separator_1);

        JLabel hcsjLabel = new JLabel("后尺数据");
        hcsjLabel.setBounds(10, 5, 54, 15);
        hcsj.add(hcsjLabel);

        JLabel hcYszg = new JLabel("油水总高(mm):");
        hcYszg.setBounds(50, 33, 85, 15);
        hcsj.add(hcYszg);

        JLabel hcJytj = new JLabel("净油体积(L):");
        hcJytj.setBounds(59, 58, 85, 15);
        hcsj.add(hcJytj);

        JLabel hcBztj = new JLabel("标准体积(L):");
        hcBztj.setBounds(59, 113, 85, 15);
        hcsj.add(hcBztj);

        JLabel hcPjwd = new JLabel("平均温度(℃):");
        //hcPjwd.setBounds(60, 113, 75, 15);
        hcPjwd.setBounds(50, 85, 85, 15);
        hcsj.add(hcPjwd);

        hcYszgField = new JTextField();
        hcYszgField.setColumns(10);
        hcYszgField.setDocument(new SuperDoubleDocument(6, 2));
        hcYszgField.setBounds(139, 30, text_width, text_height);
        hcsj.add(hcYszgField);

        hcJytjField = new JTextField(111);
        hcJytjField.setColumns(10);
        hcJytjField.setDocument(new SuperDoubleDocument(6, 2));
        hcJytjField.setBounds(139, 55, text_width, text_height);
        hcsj.add(hcJytjField);

        hcBztjField = new JTextField();
        hcBztjField.setColumns(10);
        //hcBztjField.setDocument(new DoubleDocument());
        hcBztjField.setEnabled(false);
        //hcBztjField.setBounds(129, 82, text_width, text_height);
        hcBztjField.setBounds(139, 110, text_width, text_height);
        hcsj.add(hcBztjField);

        hcPjwdField = new JTextField();
        hcPjwdField.setColumns(10);

        hcPjwdField.setDocument(superDoubleDocument2);
        //hcPjwdField.setBounds(129, 110, text_width, text_height);
        hcPjwdField.setBounds(139, 82, text_width, text_height);
        hcsj.add(hcPjwdField);

        JLabel kssj = new JLabel("开始时间:");
        kssj.setBounds(69, 321, 75, 15);
        frame.getContentPane().add(kssj);



        JLabel jssj = new JLabel("结束时间:");
        jssj.setBounds(69, 346, 75, 15);
        frame.getContentPane().add(jssj);

        beginTime = new JTimePanel();
        beginTime.setBounds(138, 318, 200 + 20, 30);
        //beginTime.setVisible(false);
        frame.getContentPane().add(beginTime);
        beginTime.setDate(acceptanceOdRegisterInfo.getBegintime());

      /*  JTimePanel betime=new JTimePanel();
        betime.setBounds(138, 318, 200+20, 30);
        frame.getContentPane().add(betime);
*/
        endTime = new JTimePanel();
        endTime.setBounds(138, 346, 200 + 20, 30);
        frame.getContentPane().add(endTime);

        //region qcjytj事件

        jytjField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
              Setqc();
            }
        });
        //endregion

        //region qcpjwd事件
        pjwdField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                Setqc();
            }
        });
        //endregion

         //region hcjytj
        hcJytjField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                Sethc();
            }
        });
        //endregion

        //region hcpjwd
        hcPjwdField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                Sethc();
            }
        });
        //endregion

        //region 保存
        JButton button = new JButton("保存");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("".equals(yszgField.getText())||"".equals(jytjField.getText())||"".equals(pjwdField.getText())
                        ||"".equals(hcYszgField.getText())||"".equals(hcJytjField.getText())||"".equals(hcPjwdField.getText())
                        ||beginTime.getDate()==null||endTime.getDate()==null){
                    JOptionPane.showMessageDialog(null, "请将数据填写完整", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (Double.parseDouble(bztjField.getText())>Double.parseDouble(hcBztjField.getText())){
                    JOptionPane.showMessageDialog(null, "后尺升数小于前尺", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (beginTime.getDate().getTime()>=endTime.getDate().getTime()){
                    JOptionPane.showMessageDialog(null, "结束时间要晚于开始时间", "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                acceptanceOdRegisterInfo = new AcceptanceOdRegisterInfo();
                acceptanceOdRegisterInfo.setManualNo(bills.getDeliveryno());
                acceptanceOdRegisterInfo.setDeliveryno(bills.getDeliveryno());
                // 保存卸油明细表信息
                acceptanceOdRegisterInfo.setOilcan(canNo);
                acceptanceOdRegisterInfo.setOilno(bills.getOilno());
                acceptanceOdRegisterInfo.setBeginoilheight(yszgField.getText().trim().isEmpty() ? null : Double.parseDouble(yszgField.getText().trim()));
                acceptanceOdRegisterInfo.setBeginoill(jytjField.getText().trim().isEmpty() ? null : Double.parseDouble(jytjField.getText().trim()));
                acceptanceOdRegisterInfo.setBeginv20l(Double.parseDouble(bztjField.getText().trim()));
                acceptanceOdRegisterInfo.setBegintemperature(pjwdField.getText().trim().isEmpty() ? null : Double.parseDouble(pjwdField.getText().trim()));
                acceptanceOdRegisterInfo.setEndoilheight(hcYszgField.getText().trim().isEmpty() ? null : Double.parseDouble(hcYszgField.getText().trim()));
                acceptanceOdRegisterInfo.setEndoill(hcJytjField.getText().trim().isEmpty() ? null : Double.parseDouble(hcJytjField.getText().trim()));
                acceptanceOdRegisterInfo.setEndv20l(Double.parseDouble(hcBztjField.getText().trim()));
                acceptanceOdRegisterInfo.setEndtemperature(hcPjwdField.getText().trim().isEmpty() ? null : Double.parseDouble(hcPjwdField.getText().trim()));
                double duringSales = 0;

                acceptanceOdRegisterInfo.setBegintime(beginTime.getDate());
                acceptanceOdRegisterInfo.setEndtime(endTime.getDate());
                 //Map literMap = dailyTradeAccountService.selectDuringSales(canNo, beginTime.getDate(),endTime.getDate());
                Map literMap =dailyTradeAccountService.GetSaleOilSumByCanNoAndDate(canNo.toString(), beginTime.getDate(),endTime.getDate());
                    if (literMap != null){
                        duringSales = Double.parseDouble(literMap.get("Liter") == null ? "0" : literMap.get("Liter").toString());
                    }
               // duringSales=dailyTradeAccountService.GetSaleOilSumByCanNoAndDate(canNo.toString(), beginTime.getDate(),endTime.getDate());

                acceptanceOdRegisterInfo.setDuringsales(duringSales);
                double realGetL = acceptanceOdRegisterInfo.getEndv20l()
                        - acceptanceOdRegisterInfo.getBeginv20l() + duringSales;
                double realGetV20 = acceptanceOdRegisterInfo.getEndv20l() - acceptanceOdRegisterInfo.getBeginv20l() + duringSales;
                
                acceptanceOdRegisterInfo.setTranstatus(JhysPage.TRANSSTATUS_END);
                acceptanceOdRegisterInfo.setEntertype(JhysPage.ENTERTYPE_SGLR);
                acceptanceOdRegisterInfo.setDischargel(realGetL);
                acceptanceOdRegisterInfo.setCreatetime(new Date());
                acceptanceOdRegisterInfo.setIsdel(0);

                odRegisterInfoService.merge(acceptanceOdRegisterInfo);
                //jhys=new JhysPage();
                jhys= Main.jhys;
                try {
                    jhys.setCkdcxPage(ckdcxPage);
                    jhys.setCkdxx(bills,iswhd);
                    jhys.getFrame().setVisible(true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                frame.setVisible(false);
                frame.dispose();
            }
        });
        //endregion

        button.setBounds(51, 380, 93, 23);
        frame.getContentPane().add(button);

        JButton button_1 = new JButton("取消");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //jhys=new JhysPage();
                jhys=Main.jhys;
                try {
                    jhys.setCkdcxPage(jhys.getCkdcxPage());
                    //jhys.setCkdxx(bill,iswhd);
                    jhys.setCkdxx(bills,iswhd);
                   // jhys.initOilTankInfo();
                    //jhys.getFrame().setVisible(true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                frame.setVisible(false);
                //frame.dispose();
            }
        });
        button_1.setBounds(198, 380, 93, 23);
        frame.getContentPane().add(button_1);
    }
    private double getV20L(String oilType,double vt, double v20) {
        DecimalFormat df = new DecimalFormat("######0.00");   
        if (OIL_TYPE_1.equals(oilType)){
            return Double.parseDouble(df.format((vt - v20) * 0.0012 * vt));
        }else{
            return Double.parseDouble(df.format((vt - v20) * 0.0008 * vt));
        }
    }


    private void Setqc(){
        if (!jytjField.getText().trim().isEmpty()&&!pjwdField.getText().trim().isEmpty()){
            Double vt=Double.parseDouble(jytjField.getText().trim());
            Double pjwd=Double.parseDouble(pjwdField.getText().trim());
            V20Utils v20Utils=new V20Utils();

            if (OIL_TYPE_1.equals("03")){
                //柴油
            bztjField.setText(v20Utils.getDieV20(pjwd,vt).toString());
            }
            if (OIL_TYPE_1.equals("01")){
                //汽油
                bztjField.setText(v20Utils.getGasV20(pjwd,vt).toString());
            }

        }
    }

    private void Sethc(){
        if (!hcJytjField.getText().trim().isEmpty()&&!hcPjwdField.getText().trim().isEmpty()){
            Double vt=Double.parseDouble(hcJytjField.getText().trim());
            Double pjwd=Double.parseDouble(hcPjwdField.getText().trim());
            V20Utils v20Utils=new V20Utils();

            if (OIL_TYPE_1.equals("03")){
                //柴油
                hcBztjField.setText(v20Utils.getDieV20(pjwd,vt).toString());
            }
            if (OIL_TYPE_1.equals("01")){
                //汽油
                hcBztjField.setText(v20Utils.getGasV20(pjwd,vt).toString());
            }

        }
    }

    public void UpdateOdreg(String manualno){
        AcceptanceOdRegister od=odRegisterService.selectByPrimaryKey(manualno);
        //regionselect begin time
        AcceptanceOdRegisterInfo info=odRegisterInfoService.selectbegintime(manualno);
        if (info==null){
            od.setBegintime(null);
            od.setEndtime(null);
            return;
        }else{
            od.setBegintime(info.getBegintime());

        }

        //endregion

        //region select endtime
        AcceptanceOdRegisterInfo info1=odRegisterInfoService.selectendtime(manualno);
        if (info1==null){
            od.setBegintime(null);
            od.setEndtime(null);
            return;
        }else{
            od.setEndtime(info.getEndtime());

        }
        odRegisterService.updateByPrimaryKey(od);
        //endregion
    }

}
