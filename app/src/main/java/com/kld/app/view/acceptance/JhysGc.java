package com.kld.app.view.acceptance;

import com.kld.app.service.IAcceptanceOdRegisterService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.DoubleDocument;
import com.kld.gsm.ATG.domain.AcceptanceDeliveryBills;
import com.kld.gsm.ATG.domain.AcceptanceOdRegister;
import com.kld.gsm.ATG.service.AcceptSevices;
import com.kld.gsm.util.V20Utils;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by niyang on 2016/2/23.
 */
public class JhysGc extends JPanel {
    private JTextField realGetLField;
    private JTextField realGetLV20;
    private JTextField totalHeight;
    private JTextField waterHeight;
    private JTextField ysr;
    private JTextField oiltemp;
    private JButton SaveBtn;
    private JButton btnComplete;

    private AcceptSevices acceptSevices;
    private IAcceptanceOdRegisterService odRegisterService;
    private AcceptanceOdRegister od;

    private AcceptanceDeliveryBills cbill;
    private String OIL_TYPE_1;

    private JTextField gcwdField;

    public JhysGc(AcceptanceDeliveryBills bill,String oiltype,JButton btncomplete){
        this.cbill=bill;
        this.OIL_TYPE_1=oiltype;
        this.btnComplete=btncomplete;
        Init();
        LoadData();
    }

    private void LoadData() {
        if(odRegisterService==null){
            odRegisterService= Context.getInstance().getBean(IAcceptanceOdRegisterService.class);
        }
        od=odRegisterService.selectByPrimaryKey(cbill.getDeliveryno());

        if (od!=null&&od.getServicelevel()==1){
            realGetLField.setText(od.getRealreceivel()!=null?od.getRealreceivel().toString():"");
            oiltemp.setText(od.getCantrucktemp()!=null?od.getCantrucktemp().toString():"");
            realGetLV20.setText(od.getRealGetLV20()!=null?od.getRealGetLV20().toString():"");
            totalHeight.setText(od.getHeighttotal()!=null?od.getHeighttotal().toString():"");
            waterHeight.setText(od.getHeightwater()!=null?od.getHeightwater().toString():"");
            ysr.setText(od.getCalculateoperator());
        }
    }
    private void checkGcCompleteBtn(AcceptanceOdRegister odRegister){
        if (odRegister.getServicelevel()==1&&odRegister.getHeightwater()!=null&&odRegister.getHeighttotal()!=null){

        }
    }

    private void Init() {
        this.setPreferredSize(new Dimension(660, 252));
        this.setSize(660, 252);
        this.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("实收升数(L):");
        lblNewLabel_1.setBounds(5, 10, 76, 31);
        this.add(lblNewLabel_1);

        realGetLField = new JTextField();
        realGetLField.setBounds(93, 15, 86, 25);
        this.add(realGetLField);
        realGetLField.setDocument(new DoubleDocument());
        realGetLField.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                setV20filed();
            }
        });

        JLabel lblNewLabel_3 = new JLabel("罐车温度(℃):");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_3.setBounds(213, 10, 86, 31);
        this.add(lblNewLabel_3);

        oiltemp = new JTextField();
        oiltemp.setBounds(302, 15, 86, 25);
        this.add(oiltemp);
        oiltemp.setDocument(new DoubleDocument());
        oiltemp.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                setV20filed();
            }
        });

        //油水总高
        JLabel lblNewLabel_4 = new JLabel("油水总高(mm):");
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_4.setBounds(407, 10, 86, 31);
        this.add(lblNewLabel_4);

        totalHeight = new JTextField();
        totalHeight.setBounds(498, 15, 86, 25);
        this.add(totalHeight);
        totalHeight.setDocument(new DoubleDocument());

        //实收V20
        JLabel lblNewLabel_5 = new JLabel("实收V20(L):");
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_5.setBounds(5, 51, 76, 31);
        this.add(lblNewLabel_5);

        realGetLV20 = new JTextField();
        realGetLV20.setBounds(93, 52, 86, 25);
        this.add(realGetLV20);
        realGetLV20.setDocument(new DoubleDocument());

        //水高
        JLabel lblNewLabel_6 = new JLabel("水高(mm):");
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_6.setBounds(213, 51, 86, 31);
        this.add(lblNewLabel_6);

        waterHeight = new JTextField();
        waterHeight.setBounds(302, 52, 86, 25);
        this.add(waterHeight);
        waterHeight.setDocument(new DoubleDocument());

        //验收员
        JLabel lblNewLabel_7 = new JLabel("验收员:");
        lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_7.setBounds(407, 51, 86, 31);
        this.add(lblNewLabel_7);

        ysr = new JTextField();
        ysr.setBounds(498, 52, 86, 25);
        this.add(ysr);
        ysr.setDocument(new DoubleDocument());
        //btn
        SaveBtn=new JButton("保存");
        SaveBtn.setBounds(498,89,75,25);
        this.add(SaveBtn);
        SaveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb=new StringBuilder();
                 if (realGetLField.getText().trim().length()>0){
                     od.setRealgetl(Double.parseDouble(realGetLField.getText().trim()));
                     od.setRealreceivel(Double.parseDouble(realGetLField.getText().trim()));
                 }else{
                     sb.append("实收升数为空\n");
                 }
                if (realGetLV20.getText().trim().length()>0){
                    od.setRealGetLV20(Double.parseDouble(realGetLV20.getText().trim()));
                }else{
                    sb.append("实收V20为空\n");
                }
                if (oiltemp.getText().trim().length()>0){
                    od.setCantrucktemp(Double.parseDouble(oiltemp.getText().trim()));
                }else{
                    sb.append("罐车温度为空\n");
                }
                if (ysr.getText().trim().length()>0){
                    od.setCalculateoperator(ysr.getText().trim());
                }else{
                    sb.append("罐车温度为空\n");
                }
                if (totalHeight.getText().trim().length()>0){
                    od.setHeighttotal(Double.parseDouble(totalHeight.getText().trim()));
                }else{
                    sb.append("罐车温度为空\n");
                }
                if (waterHeight.getText().trim().length()>0){
                    od.setHeightwater(Double.parseDouble(waterHeight.getText().trim()));
                }else{
                    sb.append("罐车温度为空\n");
                }
                if (sb.length()>0){
                    JOptionPane.showMessageDialog(null, sb.toString(), "信息提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                od.setBegintime(new Date());
                odRegisterService.updateByPrimaryKeySelective(od);
                btnComplete.setEnabled(true);
            }
        });
    }

    private void setV20filed(){
        if (!realGetLField.getText().trim().isEmpty()&&!oiltemp.getText().trim().isEmpty()){
            realGetLV20.setText(getV20L(Double.parseDouble(oiltemp.getText().trim()),Double.parseDouble(realGetLField.getText().trim()))+"");
        }
    }

    private double getV20L(double VT, double V) {
        V20Utils v20Utils=new V20Utils();
        if (V==0.0){return 0.0;}
        if (OIL_TYPE_1.equals("03")){
            //柴油
            return  v20Utils.getDieV20(VT,V);
        }else {
            //汽油
            return  v20Utils.getGasV20(VT,V);
        }
    }
}
