package com.kld.app.view.alarm;

import javax.swing.*;

import com.kld.app.service.AlarmMeasureLeakService;
import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.springcontext.Context;
import com.kld.gsm.ATG.domain.AlarmMeasureLeak;
import com.kld.gsm.ATG.domain.AlarmMeasureLeakKey;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetChartFrame  {
    private JDialog frame;
    private JTextField yfssField1;
    private JTextField ckdhField;
    private JTextField qrdhField;
    private JTextField yfssField;
    private JTextField cphmField;
    private JTextField fyykField;
    private JTextField mdyzField;
    private JTextField mdyzField22;
    private JTextField mdyzField23;
    private JTextField mdyzField24;
    private JTextField mdyzField25;
    private JTextField mdyzField26;
    private JTextField mdyzField27;

    public JDialog getFrame(int oilcanno, Date startdate) {
        return frame;
    }

    public void setFrame(JDialog frame) {
        this.frame = frame;
    }

    /**
     * Create the application.
     */
    public  GetChartFrame(int oilcanno, Date startdate) {
        initialize(oilcanno, startdate);

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(int oilcanno, Date startdate)  {
        ////System.out.println("打印传过来的参数"+oilcanno+"时间为：：：："+startdate);
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("报告文件");
        frame.setBounds(500, 100, 405, 519);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
//        frame.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("Button.light"));
        panel.setBounds(0, 0, 403, 490);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel ckdhLabel = new JLabel("油罐编号：");
        ckdhLabel.setBounds(20, 20, 92, 15);
        panel.add(ckdhLabel);

        ckdhField = new JTextField();
        ckdhField.setEnabled(false);
        ckdhField.setBounds(130, 18, 255, 21);
        panel.add(ckdhField);
        ckdhField.setColumns(10);

        JLabel qrdhLabel = new JLabel("泄漏状态：");
        qrdhLabel.setBounds(20, 50, 92, 15);
        panel.add(qrdhLabel);

        qrdhField = new JTextField(10);
        qrdhField.setColumns(10);
        qrdhField.setEnabled(false);
        qrdhField.setBounds(130, 48, 255, 21);
        panel.add(qrdhField);

        JLabel ypLabel = new JLabel("泄露速率(L/H)：");
        ypLabel.setBounds(20, 80, 92, 15);
        panel.add(ypLabel);

        JLabel xuxianLabel = new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        xuxianLabel.setBounds(20, 110, 405, 15);
        panel.add(xuxianLabel);

        yfssField1 = new JTextField(10);
        yfssField1.setEnabled(false);
        yfssField1.setBounds(130, 78, 255, 21);
        panel.add(yfssField1);

        JLabel yfssLabel = new JLabel("起始日期：");
        yfssLabel.setBounds(20, 140, 92, 15);
        panel.add(yfssLabel);

        yfssField = new JTextField(10);
        yfssField.setEnabled(false);
        yfssField.setColumns(10);
        yfssField.setBounds(130, 138, 255, 21);
        panel.add(yfssField);

        JLabel cphmLabel = new JLabel("起始时间：");
        cphmLabel.setBounds(20, 170, 92, 15);
        panel.add(cphmLabel);

        cphmField = new JTextField();
        cphmField.setEnabled(false);
        cphmField.setColumns(10);
        cphmField.setBounds(130, 168, 255, 21);
        panel.add(cphmField);

        JLabel fyykLabel = new JLabel("起始油水总高(mm)：");
        fyykLabel.setBounds(20, 200, 130, 15);
        panel.add(fyykLabel);

        fyykField = new JTextField();
        fyykField.setEnabled(false);
        fyykField.setColumns(10);
        fyykField.setBounds(130, 198, 255, 21);
        panel.add(fyykField);

        JLabel mkyzLabel = new JLabel("起始水高(mm)：");
        mkyzLabel.setBounds(20, 230, 92, 15);
        panel.add(mkyzLabel);

        mdyzField = new JTextField();
        mdyzField.setEnabled(false);
        mdyzField.setColumns(10);
        mdyzField.setBounds(130, 228, 255, 21);
        panel.add(mdyzField);

        JLabel mkyzLabel22 = new JLabel("起始5点温度(℃)：");
        mkyzLabel22.setBounds(20, 260, 130, 15);
        panel.add(mkyzLabel22);

        mdyzField22 = new JTextField();
        mdyzField22.setEnabled(false);
        mdyzField22.setColumns(10);
        mdyzField22.setBounds(130, 258, 255, 21);
        panel.add(mdyzField22);

        JLabel xuxianLabel2 = new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        xuxianLabel2.setBounds(20, 290, 405, 15);
        panel.add(xuxianLabel2);

        JLabel mkyzLabel23 = new JLabel("结束日期：");
        mkyzLabel23.setBounds(20, 320, 92, 15);
        panel.add(mkyzLabel23);

        mdyzField23 = new JTextField();
        mdyzField23.setEnabled(false);
        mdyzField23.setColumns(10);
        mdyzField23.setBounds(130, 318, 255, 21);
        panel.add(mdyzField23);

        JLabel mkyzLabel24 = new JLabel("结束时间：");
        mkyzLabel24.setBounds(20, 350, 92, 15);
        panel.add(mkyzLabel24);

        mdyzField24 = new JTextField();
        mdyzField24.setEnabled(false);
        mdyzField24.setColumns(10);
        mdyzField24.setBounds(130, 348, 255, 21);
        panel.add(mdyzField24);

        JLabel mkyzLabel25 = new JLabel("结束油水总高(mm)：");
        mkyzLabel25.setBounds(20, 380, 130, 15);
        panel.add(mkyzLabel25);

        mdyzField25 = new JTextField();
        mdyzField25.setEnabled(false);
        mdyzField25.setColumns(10);
        mdyzField25.setBounds(130, 378, 255, 21);
        panel.add(mdyzField25);

        JLabel mkyzLabel26 = new JLabel("结束水高(mm)：");
        mkyzLabel26.setBounds(20, 410, 92, 15);
        panel.add(mkyzLabel26);

        mdyzField26 = new JTextField();
        mdyzField26.setEnabled(false);
        mdyzField26.setColumns(10);
        mdyzField26.setBounds(130, 408, 255, 21);
        panel.add(mdyzField26);

        JLabel mkyzLabel27 = new JLabel("结束5点温度(℃)：");
        mkyzLabel27.setBounds(20, 440, 130, 15);
        panel.add(mkyzLabel27);

        mdyzField27 = new JTextField();
        mdyzField27.setEnabled(false);
        mdyzField27.setColumns(10);
        mdyzField27.setBounds(130, 438, 255, 21);
        panel.add(mdyzField27);
        AlarmMeasureLeakService alarmMeasureLeakService =(AlarmMeasureLeakService)(Context.getInstance().getBean("alarmMeasureLeakService"));
        //查询报告，根据开始时间和油罐号
        AlarmMeasureLeakKey alarm =new AlarmMeasureLeak();
        alarm.setOilcanno(oilcanno);
        alarm.setStartdate(startdate);
        AlarmMeasureLeak alarmMeasureLeak= alarmMeasureLeakService.selectByPrimaryKey(alarm);
        if(null==alarmMeasureLeak){
            ////System.out.println("未查询到记录，请查找原因或者核对表中记录…………");
            return;
        }
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        String start=date.format(alarmMeasureLeak.getStartdate());
        String startDate=start.substring(0, 10);
        String startTime=start.substring(10,19);
        String end=date.format(alarmMeasureLeak.getEnddate());
        String endDate=end.substring(0, 10);
        String endTime=end.substring(10,19);
        ckdhField.setText(String.valueOf(alarmMeasureLeak.getOilcanno()));//油罐编号
        String Status=String.valueOf(alarmMeasureLeak.getRevealstatus());
        //泄漏状态：0：不泄漏，1:渗漏，2：漏油，3：盗油
        if("0".equals(Status)){
            Status="不泄漏";
        }
        if("1".equals(Status)){
            Status="渗漏";
        }
        if("2".equals(Status)){
            Status="漏油";
        }
        if("3".equals(Status)){
            Status="盗油";
        }
            qrdhField.setText(Status);//泄漏状态
            yfssField1.setText(String.valueOf(alarmMeasureLeak.getRevealrate()));//泄露速率
            yfssField.setText(startDate);//起始日期
            cphmField.setText(startTime);//起始时间
            fyykField.setText(String.valueOf(alarmMeasureLeak.getStartoilheight()));//起始油水总高
            mdyzField.setText(String.valueOf(alarmMeasureLeak.getStartwaterheight()));//起始水高
            mdyzField22.setText(String.valueOf(alarmMeasureLeak.getStartoiltemp1()+","+alarmMeasureLeak.getStartoiltemp2()+","+alarmMeasureLeak.getStartoiltemp3()+","+alarmMeasureLeak.getStartoiltemp4()+","+alarmMeasureLeak.getStartoiltemp5()));
            mdyzField23.setText(endDate);//结束日期
            mdyzField24.setText(endTime);//结束时间
            mdyzField25.setText(String.valueOf(alarmMeasureLeak.getEndoilheight()));//结束油水总高
            mdyzField26.setText(String.valueOf(alarmMeasureLeak.getEndwaterheight()));//结束水高
            mdyzField27.setText(String.valueOf(alarmMeasureLeak.getEndoiltemp1()+","+alarmMeasureLeak.getEndoiltemp2()+","+alarmMeasureLeak.getEndoiltemp3()+","+alarmMeasureLeak.getEndoiltemp4()+","+alarmMeasureLeak.getEndoiltemp5()));
    }

}