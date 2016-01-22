package com.kld.app.view.alarm;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

import com.kld.app.service.AlarmMeasureLeakService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.main.Main;
import com.kld.gsm.util.DateUtil;

public class StopAlarmFrame{

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
    OilExcep oilExcep;
    public JDialog getFrame(OilExcep oilExcep) {
        this.oilExcep=oilExcep;
        return frame;
    }
     public void setFrame(JDialog frame) {
        this.frame = frame;
    }


    public StopAlarmFrame( ) {

    }

    /**
     * Create the application.
     */
    public StopAlarmFrame(Map map) {
        initialize(map);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(Map map) {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("报告文件");
        frame.setBounds(500, 100, 405, 569);
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

        ckdhField.setText(map.get("uOilCanNo").toString());//油罐编号
        String Status=map.get("uRevealStatus").toString();
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
        Date startd =new Date() ,endd=new Date();
        try {
            startd = DateUtil.convertStringToDate("yyyyMMdd", map.get("strStartDate").toString());
            endd=DateUtil.convertStringToDate("yyyyMMdd", map.get("strEndDate").toString());
            //Main.setStatus(startd.toString()+endd.toString());
            ////System.out.println(startd);
            ////System.out.println(endd);
        }catch (ParseException pe)
        {
            ////System.out.println("开始结束时间转换错误"+pe);
        }
        // SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String startDate= DateUtil.convertDateToString(startd);//  date.format(startd);
        String endDate =DateUtil.convertDateToString(endd);

        //SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        String startTime="",endTime="";
        String startTimetemp = map.get("strStartTime").toString();
        String endTimetemp = map.get("strEndTime").toString();
        startTime = startTimetemp.substring(0,2)+":"+startTimetemp.substring(2,4)+":"+startTimetemp.substring(4,6);// time.format(map.get("strStartTime").toString());
        endTime = endTimetemp.substring(0,2)+":"+endTimetemp.substring(2,4)+":"+endTimetemp.substring(4,6);// time.format(map.get("strStartTime").toString());
        qrdhField.setText(Status);//泄漏状态
        yfssField1.setText(map.get("fRevealRate").toString());//泄露速率

        yfssField.setText(startDate);//起始日期
        cphmField.setText(startTime);//起始时间
        fyykField.setText(map.get("fStartOilHeight").toString());//起始油水总高
        mdyzField.setText(map.get("fStartWaterHeight").toString());//起始水高
        mdyzField22.setText(String.valueOf(map.get("fStartOilTemp1")+","+map.get("fStartOilTemp2")+","+map.get("fStartOilTemp3")+","+map.get("fStartOilTemp4")+","+map.get("fStartOilTemp5")));
        mdyzField23.setText(endDate);//结束日期
        mdyzField24.setText(endTime);//结束时间
        mdyzField25.setText(map.get("fEndOilHeight").toString());//结束油水总高
        mdyzField26.setText(map.get("fEndWaterHeight").toString());//结束水高
        mdyzField27.setText(String.valueOf(map.get("fEndOilTemp1") + "," +map.get("fEndOilTemp2") + "," + map.get("fEndOilTemp3") + "," + map.get("fEndOilTemp4") + "," +map.get("fEndOilTemp5")));
        ////System.out.println("停止液位仪的赋值阶段完毕…………………………………………");

            Integer oilCanNo=(Integer)map.get("uOilCanNo");
            String s =(map.get("strStartDate").toString() + map.get("strStartTime").toString());
            String e =(map.get("strEndDate").toString()+map.get("strEndTime").toString());
            ////System.out.println("打印出时间的样子为"+s+"结束时间"+e);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try
        {
            Date startdate = sdf.parse(s);
            Date enddate = sdf.parse(e);
            ////System.out.println("打印出时间的样子2为" + startdate + "结束时间" + enddate);
            //更新前整理数据
            String revealstatus=map.get("uRevealStatus").toString();
            ////System.out.println("打印出revealstatus的样子2为" + revealstatus);
            String revealRate= map.get("fRevealRate").toString();
            ////System.out.println("打印出revealRate的样子为" + revealRate);
            Double startoilheight=(Double)map.get("fStartOilHeight");
            Double startwaterheight =(Double)map.get("fStartWaterHeight");
            Double startoiltemp1=(Double)map.get("fStartOilTemp1");
            Double startoiltemp2=(Double)map.get("fStartOilTemp2");
            Double startoiltemp3=(Double)map.get("fStartOilTemp3");
            Double startoiltemp4=(Double)map.get("fStartOilTemp4");
            Double startoiltemp5=(Double)map.get("fStartOilTemp5");
            Double endoilheight=(Double)map.get("fEndOilHeight");
            Double endwaterheight=(Double)map.get("fEndWaterHeight");
            Double endoiltemp1=(Double)map.get("fEndOilTemp1");
            Double endoiltemp2=(Double)map.get("fEndOilTemp2");
            Double endoiltemp3=(Double)map.get("fEndOilTemp3");
            Double endoiltemp4=(Double)map.get("fEndOilTemp4");
            Double endoiltemp5=(Double)map.get("fEndOilTemp5");
            Double fEndOilCubage=(Double)map.get("fEndOilCubage");
            Double fEndWaterBulk=(Double)map.get("fEndWaterBulk");

            HashMap map1=new HashMap();
            map1.put("oilCanNo",oilCanNo);
            map1.put("revealstatus",revealstatus);
            map1.put("revealRate",revealRate);
            map1.put("startoilheight",startoilheight);
            map1.put("startwaterheight",startwaterheight);
            map1.put("startoiltemp1",startoiltemp1);
            map1.put("startoiltemp2",startoiltemp2);
            map1.put("startoiltemp3",startoiltemp3);
            map1.put("startoiltemp4",startoiltemp4);
            map1.put("startoiltemp5",startoiltemp5);
            map1.put("endoilheight",endoilheight);
            map1.put("endwaterheight",endwaterheight);
            map1.put("endoiltemp1",endoiltemp1);
            map1.put("endoiltemp2",endoiltemp2);
            map1.put("endoiltemp3",endoiltemp3);
            map1.put("endoiltemp4",endoiltemp4);
            map1.put("endoiltemp5",endoiltemp5);
            map1.put("enddate",enddate);
            map1.put("startdate",startdate);
            map1.put("endoill", fEndOilCubage);
            map1.put("endwaterl",fEndWaterBulk);

            ////System.out.println("endoill : " + fEndOilCubage);
            ////System.out.println("endwaterl : "+fEndWaterBulk);
            ////System.out.println("开始更新数据…………………………………………"+map1.toString());
            //更新数据
            AlarmMeasureLeakService alarmMeasureLeakService=(AlarmMeasureLeakService) (Context.getInstance().getBean("alarmMeasureLeakService"));
            int a=alarmMeasureLeakService.updateEndDate(map1);

            if (Main.oilExcep==null){
                Main.oilExcep=new OilExcep();
            }
            Main.oilExcep.reload();
            ////System.out.println("更新数据结束…………………………………………"+a);
        }
        catch (ParseException e1)
        {
            ////System.out.println(e1.getMessage());
            ////System.out.println("时间转换或者更新错误，检查");
        }
    }
   }


