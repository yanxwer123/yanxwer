package com.kld.gsm.ATGDevice;
import com.kld.gsm.ATGDevice.ATGManagerTest.TbjzcsszPanel;
import com.kld.gsm.ATGDevice.ATGManagerTest.YbjszPanel;
import com.kld.gsm.ATGDevice.ATGManagerTest.canshuPanel;
import com.kld.gsm.coord.utils.ComparatorUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/11/19 20:30
 * @Description:
 */
public class ATGDeviceSwing extends JFrame {
    JTextField leixingt = new JTextField("1");
    JTextField ipt = new JTextField("192.168.0.128");
    JTextField portt = new JTextField("4001");
    JTextField rizhilujingt = new JTextField("/smc20/gsm/");
    JTextField chuankout = new JTextField("/dev/ttyS0");
    JTextField xinghao = new JTextField("FA_VPI");
    static String DeviceModel = "";
    static String ProbeNo="";
    public static JTextArea jTextArea = new JTextArea(20,50);
    public JButton shezhicanshu = new JButton("设置参数");
    public JButton tbcanshu = new JButton("探棒参数");
    public JButton ybjcanshu = new JButton("预报警参数");
    public static void main(String args[]) {
        ATGDeviceSwing g = new ATGDeviceSwing();
        g.setSize(800, 600);
        g.setLocationRelativeTo(null);
        g.setVisible(true);
        //g.pack();
        g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public ATGDeviceSwing() {
        JTextField t1 = new JTextField(20);
        JTextField t2 = new JTextField(20);
        JTextField t3 = new JTextField(20);
        JTextField t4 = new JTextField(20);

        JButton j1 = new JButton("初始化");
        JButton j2 = new JButton("实时库存");
        JButton j3 = new JButton("整点库存");
        JButton j4 = new JButton("进油信息采集");
        JButton j5 = new JButton("油罐报警采集");
        JButton j6 = new JButton("设备故障采集");
        JButton j7 = new JButton("液位仪对时");
        JButton j8 = new JButton("预报警设置");
        JButton j9 = new JButton("容积表上传");
        JButton j10 = new JButton("容积表下发");
        JButton j11 = new JButton("启动异常测试");
        JButton j12 = new JButton("停止异常测试");
        JButton j13 = new JButton("静态液位异常报告");
        JButton j14 = new JButton("设备基础信息");
        JButton j15 = new JButton("高升转换");
        JButton j16 = new JButton("液位仪开关记录");
        JButton j17 = new JButton("探棒校正参数设置");
        JButton j18 = new JButton("探棒油罐配置");
        JPanel main = new JPanel();
        main.setLayout(new FlowLayout());
        main.add(leixingt);
        main.add(ipt);
        main.add(portt);
        main.add(rizhilujingt);
        main.add(chuankout);
        main.add(xinghao);
        main.add(shezhicanshu);
        main.add(tbcanshu);
        main.add(ybjcanshu);

        SzcsAction szcsAction = new SzcsAction(jTextArea);
        shezhicanshu.addActionListener(szcsAction);
        TbcsAction tbcsAction = new TbcsAction(jTextArea);
        tbcanshu.addActionListener(tbcsAction);
        YbjAction ybjAction = new YbjAction(jTextArea);
        ybjcanshu.addActionListener(ybjAction);


        //初始化
        main.add(j1);
        ActionListener j1action = new J1Action(jTextArea,leixingt,ipt,portt,rizhilujingt,chuankout,xinghao);
        j1.addActionListener(j1action);
        main.add(j2);
        ActionListener j2action = new J2Action(jTextArea);
        j2.addActionListener(j2action);
        main.add(j3);
        ActionListener j3action = new J3Action(jTextArea);
        j3.addActionListener(j3action);
        main.add(j4);
        ActionListener j4action = new J4Action(jTextArea);
        j4.addActionListener(j4action);
        main.add(j5);
        ActionListener j5action = new J5Action(jTextArea);
        j5.addActionListener(j5action);
        main.add(j6);
        ActionListener j6action = new J6Action(jTextArea);
        j6.addActionListener(j6action);
        main.add(j7);
        ActionListener j7action = new J7Action(jTextArea);
        j7.addActionListener(j7action);
        main.add(j8);
        ActionListener j8action = new J8Action(jTextArea);
        j8.addActionListener(j8action);
        main.add(j9);
        ActionListener j9action = new J9Action(jTextArea);
        j9.addActionListener(j9action);
        main.add(j10);
        ActionListener j10action = new J10Action(jTextArea);
        j10.addActionListener(j10action);
        main.add(j11);
        ActionListener j11action = new J11Action(jTextArea);
        j11.addActionListener(j11action);
        main.add(j12);
        ActionListener j12action = new J12Action(jTextArea);
        j12.addActionListener(j12action);
        main.add(j13);
        ActionListener j13action = new J13Action(jTextArea);
        j13.addActionListener(j13action);
        main.add(j14);
        ActionListener j14action = new J14Action(jTextArea);
        j14.addActionListener(j14action);
        main.add(j15);
        ActionListener j15action = new J15Action(jTextArea);
        j15.addActionListener(j15action);
        main.add(j16);
        ActionListener j16action = new J16Action(jTextArea);
        j16.addActionListener(j16action);
        main.add(j17);
        ActionListener j17action = new J17Action(jTextArea);
        j17.addActionListener(j17action);
        main.add(j18);
        ActionListener j18action = new J18Action(jTextArea);
        j18.addActionListener(j18action);
        jTextArea.setSize(300,200);
        JScrollPane scroll = new JScrollPane(jTextArea);
        scroll.setSize(300,200);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        main.add(scroll);
        JButton jButton = new JButton("clearText");
        ActionListener cleartextarea = new clearTextArea(jTextArea);
        jButton.addActionListener(cleartextarea);
        main.add(jButton);
        this.add(main);
    }
}
class J1Action implements ActionListener{
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    JTextField leixingt;
    JTextField ipt;
    JTextField portt;
    JTextField rizhilujingt;
    JTextField chuankout;
    JTextField xinghao;
    public J1Action(JTextArea jTextArea,JTextField leixingt,JTextField ipt,JTextField portt,JTextField rizhilujingt,JTextField chuankout,JTextField xinghao){
        this.jTextArea = jTextArea;
        this.leixingt = leixingt;
        this.ipt = ipt;
        this.portt = portt;
        this.rizhilujingt = rizhilujingt;
        this.chuankout = chuankout;
        this.xinghao = xinghao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.1	初始化方法   done
        atg_init_in_t init = new atg_init_in_t();

        init.strDeviceType = this.xinghao.getText();
        init.uConnMode = Integer.parseInt(leixingt.getText());//1:网口 0：串口
        init.strSerialAddress = chuankout.getText();//"/dev/ttyS0";
        init.strSerialBaudRate = "9600";
        init.strSerialStopBit = "1";
        init.strSerialCheckBit = "e";
        init.strSerialDataBit = "7";
        init.strIPAddress = ipt.getText();//"192.168.0.211";
        init.strLogPath = rizhilujingt.getText();//"20151119.log";
        init.strIPPort = portt.getText();//"5656";
        jTextArea.append("java start~~~~~~~~~~~~~~~~");
        int ret = amg.init(init);
        jTextArea.setText("java start~~~~~~~~~~~~~~~~" + "\r\n");
        jTextArea.setWrapStyleWord(true);
        jTextArea.append("java init return ret:" + ret + "\r\n");
        jTextArea.append("init.uConnMode:"+init.uConnMode+"\r\n");
        jTextArea.append("init.strSerialAddress:"+init.strSerialAddress+"\r\n");
        jTextArea.append("init.strIPAddress:"+init.strIPAddress+"\r\n");
        jTextArea.append("init.strLogPath:"+init.strLogPath+"\r\n");
        jTextArea.append("init.strIPPort:"+init.strIPPort+"\r\n");
    }
}

class J2Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J2Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String guanhao = canshuPanel.shidiankucunguanhaoTextField.getText();
        String[] guanhaos = null;
        if(guanhao.indexOf(",")!=-1){
            guanhaos = guanhao.split(",");
        }
        //实时库存
        List<Integer> li = new ArrayList<Integer>();
        if(guanhaos!=null){
            for(String gh:guanhaos) {
                li.add(Integer.parseInt(gh));
            }
        }else{
            li.add(Integer.parseInt(guanhao));
        }
        List<atg_stock_data_out_t> atg_stock_data_out_ts = amg.getStock(li);
        jTextArea.setText("\r\n");
        jTextArea.append("Java String out 实时库存 len:" + atg_stock_data_out_ts.size());
        for(atg_stock_data_out_t a:atg_stock_data_out_ts) {
            jTextArea.append(a.toString3());
        }
    }
}
class J3Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J3Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.3.2	整点库存采集 done 联调成功 第二轮成功
        List<atg_timestock_data_in_t> li32 = new ArrayList<atg_timestock_data_in_t>();
        String guanhao = canshuPanel.zhengdiankucunguanhaoTextField.getText();
        String sj = canshuPanel.zhengdiankucunshijianTextField.getText();
        String[] guanhaos = null;
        if(guanhao.indexOf(",")!=-1){
            guanhaos = guanhao.split(",");
        }
        if(guanhaos!=null){
            for(String gh:guanhaos) {
                atg_timestock_data_in_t a14 = new atg_timestock_data_in_t();
                a14.uOilCanNo=Integer.parseInt(gh);
                a14.strDataTime=sj;
                a14.uReqCount=1;
                li32.add(a14);
            }
        }else{
            atg_timestock_data_in_t a14 = new atg_timestock_data_in_t();
            a14.uOilCanNo=Integer.parseInt(guanhao);
            a14.strDataTime=sj;
            a14.uReqCount=1;
            li32.add(a14);
        }
        jTextArea.append("JAVA Start 整点库存~~~~~~~~~~~~~~~~~"+"\r\n");
        List<atg_stock_data_out_t> l = amg.getTimeStock(li32);
        jTextArea.setText("\r\n");
        jTextArea.append("Java String out" + l.size());
        for(atg_stock_data_out_t a:l) {
            jTextArea.append(a.toString3());
        }
    }
}
class J4Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J4Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //进油信息查询
        List<atg_oilin_data_in_t> datas = new ArrayList<atg_oilin_data_in_t>();
        String guanhao = canshuPanel.jinyouxinxicaijiguanhao.getText();
        String sj = canshuPanel.jinyouxinxicaijishijian.getText();
        String[] guanhaos = null;
        if(guanhao.indexOf(",")!=-1){
            guanhaos = guanhao.split(",");
        }
        if(guanhaos!=null){
            for(String gh:guanhaos) {
                atg_oilin_data_in_t data = new atg_oilin_data_in_t();
                data.strDataTime = sj;
                data.uOilCanNO = Integer.parseInt(gh);
                data.uReqCount = 0;
                datas.add(data);
            }
        }else{
            atg_oilin_data_in_t data = new atg_oilin_data_in_t();
            data.strDataTime = sj;
            data.uOilCanNO = Integer.parseInt(guanhao);
            data.uReqCount = 0;
            datas.add(data);
        }
        List<atg_oilin_data_out_t> retatg_oilin_data_out_t = amg.getOilIn(datas);
        jTextArea.append("Java String out进油信息len:"+retatg_oilin_data_out_t.size()+"\r\n");
        for(atg_oilin_data_out_t a: retatg_oilin_data_out_t) {
            jTextArea.append(a.toString2());
        }
    }
}
class J5Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J5Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //油罐报警采集
        List<atg_alarm_data_in_t> alarmData = new ArrayList<atg_alarm_data_in_t>();
        String guanhao = canshuPanel.ygbjguanhao.getText();
        String sj = canshuPanel.ygbjshijian.getText();
        String[] guanhaos = null;
        if(guanhao.indexOf(",")!=-1){
            guanhaos = guanhao.split(",");
        }
        if(guanhaos!=null){
            for(String gh:guanhaos) {
                atg_alarm_data_in_t a = new atg_alarm_data_in_t();
                a.uOilCanNO=Integer.parseInt(gh);
                a.strDataTime=sj;
                a.uReqCount=0;
                alarmData.add(a);
            }
        }else{
            atg_alarm_data_in_t a = new atg_alarm_data_in_t();
            a.uOilCanNO=Integer.parseInt(guanhao);
            a.strDataTime=sj;
            a.uReqCount=0;
            alarmData.add(a);
        }
        List<atg_alarm_data_out_t> li5 =amg.getAlarm(alarmData);
        jTextArea.setText("Java out油罐报警采集" + li5.size() + "\r\n");
        for(atg_alarm_data_out_t b:li5) {
            jTextArea.append(b.toString2());
        }
    }
}
class J6Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J6Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //设备故障采集
        List<atg_failure_data_in_t> failureData = new ArrayList<atg_failure_data_in_t>();
        String guanhao = canshuPanel.sbgzguanhao.getText();
        String sj = canshuPanel.sbgzshijian.getText();
        String[] guanhaos = null;
        if(guanhao.indexOf(",")!=-1){
            guanhaos = guanhao.split(",");
        }
        if(guanhaos!=null){
            for(String gh:guanhaos) {
                atg_failure_data_in_t a11=  new atg_failure_data_in_t();
                a11.uOilCanNO=Integer.parseInt(gh);
                a11.strDataTime=sj;
                a11.uReqCount=0;
                failureData.add(a11);
            }
        }else{
            atg_failure_data_in_t a11=  new atg_failure_data_in_t();
            a11.uOilCanNO=Integer.parseInt(guanhao);
            a11.strDataTime=sj;
            a11.uReqCount=0;
            failureData.add(a11);
        }

        List<atg_failure_data_out_t> li1 = amg.getFailure(failureData);
        jTextArea.append("Java String out atg_failure_data_out_t:" + li1.size() + "\r\n");
        //根据date、time排序，小的时间在前，之后的更新操作不用判断时间
        ComparatorUtils comparator=new ComparatorUtils();
        Collections.sort(li1, comparator);
        for(atg_failure_data_out_t a: li1) {
            jTextArea.append(a.toString2());
        }
    }
}
class J7Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J7Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.3.6	液位仪对时 done 联调成功 第二轮成功
        //YYYYMMDDHHMMSS
        String a2=amg.checkTime(canshuPanel.ywydsshijian.getText());
        jTextArea.append("输入："+canshuPanel.ywydsshijian.getText()+
                "返回checkTime~~~~:"+a2+"\r\n");
    }
}
class J8Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J8Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //预报警设置
        List<atg_setalarm_data_in_t> setAlarmData = new ArrayList<atg_setalarm_data_in_t>();
        atg_setalarm_data_in_t a1 = new atg_setalarm_data_in_t();
        a1.fHighAlarm    =Double.parseDouble(YbjszPanel.fHighAlarmText.getText());
        a1.fHighWarning  =Double.parseDouble(YbjszPanel.fHighWarningText.getText());
        a1.fLowWarning   =Double.parseDouble(YbjszPanel.fLowWarningText.getText());
        a1.fLowAlarm     =Double.parseDouble(YbjszPanel.fLowAlarmText.getText());
        a1.fWaterAlarm   =Double.parseDouble(YbjszPanel.fWaterAlarmText.getText());
        a1.fWaterWarning =Double.parseDouble(YbjszPanel.fWaterWarningText.getText());
        a1.fThiefAlarm   =Double.parseDouble(YbjszPanel.fThiefAlarmText.getText());
        a1.fLeakAlarm    =Double.parseDouble(YbjszPanel.fLeakAlarmText.getText());
        a1.fPercolatingAlarm =Double.parseDouble(YbjszPanel.fPercolatingAlarmText.getText());
        a1.fHighTempAlarm    =Double.parseDouble(YbjszPanel.fHighTempAlarmText.getText());
        a1.fLowTempAlarm     =Double.parseDouble(YbjszPanel.fLowTempAlarmText.getText());
        a1.uOilCanNO=Integer.parseInt(YbjszPanel.ybjszguanhao.getText());
        setAlarmData.add(a1);
        String liatg_setalarm_data_in_t = amg.alarmSetter(setAlarmData);
        jTextArea.append("输入参数："+a1.toString2()+"\r\n");
        jTextArea.append("Java out liatg_setalarm_data_in_t:"+liatg_setalarm_data_in_t+"\r\n");
    }
}
class J9Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J9Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.3.9	容积表(全罐表)上传  done  联调成功 第二轮成功
        List<Integer> li = new ArrayList<Integer>();
        String guanhao = canshuPanel.rjbscguanhao.getText();
        String[] guanhaos = null;
        if(guanhao.indexOf(",")!=-1){
            guanhaos = guanhao.split(",");
        }
        if(guanhaos!=null){
            for(String gh:guanhaos) {
                li.add(Integer.parseInt(gh));
            }
        }else{
            li.add(Integer.parseInt(guanhao));
        }
        List<atg_capacity_data_in_t> ret1 = amg.getCapacityTable(li);
        jTextArea.append("Java out ret1.size~~~~"+ret1.size()+"\r\n");
        for(int i=0;i<ret1.size();i++) {
            jTextArea.append("Java out ret.get("+i+").uOilCanNO:" + ret1.get(i).uOilCanNO+"\r\n");
            jTextArea.append("Java out ret.get("+i+").strVersion:" + ret1.get(i).strVersion+"\r\n");
            jTextArea.append("Java out ret.get("+i+").uCapacitySize:" + ret1.get(i).uCapacitySize+"\r\n");
            List<atg_capacitytable_data_in_t> ret2 = ret1.get(i).pCapacityTableData;
            jTextArea.append("ret2~~~~~~~~~~~~~~~"+ret2.size()+"\r\n");
            for(int j=0;j<ret2.size();j++) {
                jTextArea.append(i+"Java out ret2.get("+j+").fLiter:" + ret2.get(j).fLiter+"\r\n");
                jTextArea.append(i+"Java out ret2.get("+j+").uHigh:" + ret2.get(j).uHigh+"\r\n");
            }
        }
    }
}
class J10Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J10Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String guanhao = canshuPanel.rjbxfguanhao.getText();
        String bbh = canshuPanel.rjbxfbanbenhao.getText();
        //3.6.3.10	容积表(全罐表)下发 done 联调成功 第二轮成功
        atg_capacity_data_in_t data = new atg_capacity_data_in_t();
        data.uCapacitySize=1;
        data.uOilCanNO=Integer.parseInt(guanhao);
        data.strVersion=bbh;
        ArrayList<atg_capacitytable_data_in_t> list = new ArrayList<atg_capacitytable_data_in_t>();
        for(int i=1;i<200;i++) {
            atg_capacitytable_data_in_t a = new atg_capacitytable_data_in_t();
            a.uHigh = 10*i;
            a.fLiter = 20*i+11.11;
            list.add(a);
        }
        data.pCapacityTableData = list;
        String ret2 = amg.setCapacityTable(data);
        jTextArea.append("容积表下发返回~~~~~~~~~~~~~~~~~~:"+ret2+"\r\n");
    }
}
class J11Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J11Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.3.11	启动静态液位异常测试 done 联调成功 第二轮成功
        String guanhao = canshuPanel.qdywycguanhao.getText();
        List<atg_startliquid_data_in_t> LiquidData =new ArrayList<atg_startliquid_data_in_t>();
        atg_startliquid_data_in_t a3 =new atg_startliquid_data_in_t();
        a3.uOilCanNo=Integer.parseInt(guanhao);
        a3.uTestType=0;
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date= new Date();
        a3.strDataTime=sd.format(date);
        a3.uTestDuration=2;
        a3.fTestRate=0.76;
        LiquidData.add(a3);
        String startLiquid =amg.startLiquid(LiquidData);
        jTextArea.append("java out startLiquid:"+startLiquid+"\r\n");
    }
}
class J12Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J12Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.3.12	停止静态液位异常测试  done 联调成功 第二轮成功
        String guanhao = canshuPanel.tzywycguanhao.getText();
        List<atg_stopliquid_data_in_t> liquidData = new ArrayList<atg_stopliquid_data_in_t>();
        atg_stopliquid_data_in_t a=  new atg_stopliquid_data_in_t();
        a.uOilCanNo=Integer.parseInt(guanhao);
        a.uTestType=0;
        liquidData.add(a);
        List<atg_liquidreport_data_out_t> li = amg.stopLiquid(liquidData);
        jTextArea.append("Java String out"+li.size()+"\r\n");
        for(int i=0;i<li.size();i++){
            jTextArea.append("uOilCanNo "+li.get(i).uOilCanNo+"\r\n");
            jTextArea.append("uRevealStatus"+li.get(i).uRevealStatus+"\r\n");
            jTextArea.append("fRevealRate "+li.get(i).fRevealRate+"\r\n");
            jTextArea.append("strStartDate "+li.get(i).strStartDate+"\r\n");
            jTextArea.append("strStartTime "+li.get(i).strStartTime+"\r\n");
            jTextArea.append("fStartOilHeight "+li.get(i).fStartOilHeight+"\r\n");
            jTextArea.append("fStartWaterHeight"+li.get(i).fStartWaterHeight+"\r\n");
            jTextArea.append("fStartOilTemp "+li.get(i).fStartOilTemp+"\r\n");
            jTextArea.append("fStartOilTemp1 "+li.get(i).fStartOilTemp1+"\r\n");
            jTextArea.append("fStartOilTemp2 "+li.get(i).fStartOilTemp2+"\r\n");
            jTextArea.append("fStartOilTemp3 "+li.get(i).fStartOilTemp3+"\r\n");
            jTextArea.append("fStartOilTemp4 "+li.get(i).fStartOilTemp4+"\r\n");
            jTextArea.append("fStartOilTemp5 "+li.get(i).fStartOilTemp5+"\r\n");
            jTextArea.append("fStartOilCubage"+li.get(i).fStartOilCubage+"\r\n");
            jTextArea.append("fStartOilStandCubage"+li.get(i).fStartOilStandCubage+"\r\n");
            jTextArea.append("fStartEmptyCubage "+li.get(i).fStartEmptyCubage+"\r\n");
            jTextArea.append("fStartWaterBulk "+li.get(i).fStartWaterBulk+"\r\n");
            jTextArea.append("strEndDate "+li.get(i).strEndDate+"\r\n");
            jTextArea.append("strEndTime "+li.get(i).strEndTime+"\r\n");
            jTextArea.append("fEndOilHeight"+li.get(i).fEndOilHeight+"\r\n");
            jTextArea.append("fEndWaterHeight"+li.get(i).fEndWaterHeight+"\r\n");
            jTextArea.append("fEndOilTemp "+li.get(i).fEndOilTemp+"\r\n");
            jTextArea.append("fEndOilTemp1"+li.get(i).fEndOilTemp1+"\r\n");
            jTextArea.append("fEndOilTemp2 "+li.get(i).fEndOilTemp2+"\r\n");
            jTextArea.append("fEndOilTemp3 "+li.get(i).fEndOilTemp3+"\r\n");
            jTextArea.append("fEndOilTemp4 "+li.get(i).fEndOilTemp4+"\r\n");
            jTextArea.append("fEndOilTemp5 "+li.get(i).fEndOilTemp5+"\r\n");
            jTextArea.append("fEndOilCubage "+li.get(i).fEndOilCubage+"\r\n");
            jTextArea.append("fEndOilStandCubage"+li.get(i).fEndOilStandCubage+"\r\n");
            jTextArea.append("fEndEmptyCubage"+li.get(i).fEndEmptyCubage+"\r\n");
            jTextArea.append("fEndWaterBulk"+li.get(i).fEndWaterBulk+"\r\n");
        }
    }
}
class J13Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J13Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.3.13	静态液位异常测试报告 done 第二轮成功
        String guanhao = canshuPanel.bgywycguanhao.getText();
        String sj = canshuPanel.bgywycshijian.getText();
        List<atg_liquidreport_data_in_t> liquidData3 = new ArrayList<atg_liquidreport_data_in_t>();
        atg_liquidreport_data_in_t a2=  new atg_liquidreport_data_in_t();
        a2.uOilCanNo=Integer.parseInt(guanhao);
        a2.strDataTime=sj;
        a2.uTestType=0;
        a2.uReqCount=1;
        liquidData3.add(a2);
        List<atg_liquidreport_data_out_t> li2 = amg.liquidReport(liquidData3);
        jTextArea.append("Java String out" + li2.size()+"\r\n");
        for(int i=0;i<li2.size();i++){
            jTextArea.append("li2.get("+i+").uOilCanNo          "+li2.get(i).uOilCanNo           +"\r\n");
            jTextArea.append("li2.get("+i+").uRevealStatus      "+li2.get(i).uRevealStatus       +"\r\n");
            jTextArea.append("li2.get("+i+").fRevealRate        "+li2.get(i).fRevealRate         +"\r\n");
            jTextArea.append("li2.get("+i+").strStartDate       "+li2.get(i).strStartDate        +"\r\n");
            jTextArea.append("li2.get("+i+").strStartTime       "+li2.get(i).strStartTime        +"\r\n");
            jTextArea.append("li2.get("+i+").fStartOilHeight    "+li2.get(i).fStartOilHeight     +"\r\n");
            jTextArea.append("li2.get("+i+").fStartWaterHeight  "+li2.get(i).fStartWaterHeight   +"\r\n");
            jTextArea.append("li2.get("+i+").fStartOilTemp     "+li2.get(i).fStartOilTemp      +"\r\n");
            jTextArea.append("li2.get("+i+").fStartOilTemp1     "+li2.get(i).fStartOilTemp1      +"\r\n");
            jTextArea.append("li2.get("+i+").fStartOilTemp2     "+li2.get(i).fStartOilTemp2      +"\r\n");
            jTextArea.append("li2.get("+i+").fStartOilTemp3     "+li2.get(i).fStartOilTemp3      +"\r\n");
            jTextArea.append("li2.get("+i+").fStartOilTemp4     "+li2.get(i).fStartOilTemp4      +"\r\n");
            jTextArea.append("li2.get("+i+").fStartOilTemp5     "+li2.get(i).fStartOilTemp5      +"\r\n");
            jTextArea.append("li2.get("+i+").fStartOilCubage    "+li2.get(i).fStartOilCubage     +"\r\n");
            jTextArea.append("li2.get("+i+").fStartOilStandCubag"+li2.get(i).fStartOilStandCubage+"\r\n");
            jTextArea.append("li2.get("+i+").fStartEmptyCubage  "+li2.get(i).fStartEmptyCubage   +"\r\n");
            jTextArea.append("li2.get("+i+").fStartWaterBulk    "+li2.get(i).fStartWaterBulk     +"\r\n");
            jTextArea.append("li2.get("+i+").strEndDate         "+li2.get(i).strEndDate          +"\r\n");
            jTextArea.append("li2.get("+i+").strEndTime         "+li2.get(i).strEndTime          +"\r\n");
            jTextArea.append("li2.get("+i+").fEndOilHeight      "+li2.get(i).fEndOilHeight       +"\r\n");
            jTextArea.append("li2.get("+i+").fEndWaterHeight    "+li2.get(i).fEndWaterHeight     +"\r\n");
            jTextArea.append("li2.get("+i+").fEndOilTemp1       "+li2.get(i).fEndOilTemp1        +"\r\n");
            jTextArea.append("li2.get("+i+").fEndOilTemp2       "+li2.get(i).fEndOilTemp2        +"\r\n");
            jTextArea.append("li2.get("+i+").fEndOilTemp3       "+li2.get(i).fEndOilTemp3        +"\r\n");
            jTextArea.append("li2.get("+i+").fEndOilTemp4       "+li2.get(i).fEndOilTemp4        +"\r\n");
            jTextArea.append("li2.get("+i+").fEndOilTemp5       "+li2.get(i).fEndOilTemp5        +"\r\n");
            jTextArea.append("li2.get("+i+").fEndOilCubage      "+li2.get(i).fEndOilCubage       +"\r\n");
            jTextArea.append("li2.get("+i+").fEndOilStandCubage "+li2.get(i).fEndOilStandCubage  +"\r\n");
            jTextArea.append("li2.get("+i+").fEndEmptyCubage    "+li2.get(i).fEndEmptyCubage     +"\r\n");
            jTextArea.append("li2.get("+i+").fEndWaterBulk      "+li2.get(i).fEndWaterBulk       +"\r\n");
        }
    }
}
class J14Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J14Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.3.14	设备基础信息 done    返回参数为OBJECT not LIST   联调成功 第二轮成功
        String guanhao = canshuPanel.sbjcxxguanhao.getText();
        String[] guanhaos = null;
        if(guanhao.indexOf(",")!=-1){
            guanhaos = guanhao.split(",");
        }
        //实时库存
        List<Integer> data = new ArrayList<Integer>();
        if(guanhaos!=null){
            for(String gh:guanhaos) {
                data.add(Integer.parseInt(gh));
            }
        }else{
            data.add(Integer.parseInt(guanhao));
        }
        atg_device_out_t ret1 = amg.getDeviceInfo(data);
        ATGDeviceSwing.DeviceModel = ret1.strDeviceModel;
        jTextArea.append("Java out ret.strDeviceModel:" + ret1.strDeviceModel+"\r\n");
        jTextArea.append("Java out ret.strEquipCode:" + ret1.strEquipCode+"\r\n");
        jTextArea.append("Java out ret.strSysVersion:" + ret1.strSysVersion + "\r\n");
        jTextArea.append("Java out ret.strMakeDate:" + ret1.strMakeDate + "\r\n");
        jTextArea.append("Java out ret.uRetCount:" + ret1.uRetCount + "\r\n");
        List<atg_device_data_out_t> ret2 = ret1.pDeviceData;
        for(int j=0;j<ret2.size();j++){
            ATGDeviceSwing.ProbeNo = ret2.get(j).strProbeNo;
            jTextArea.append("Java out ret.uOilCanNo:" + ret2.get(j).uOilCanNo+"\r\n");
            jTextArea.append("Java out ret.strProbeNo:" + ret2.get(j).strProbeNo+"\r\n");
            jTextArea.append("Java out ret.strProbeModel:" + ret2.get(j).strProbeModel+"\r\n");
        }
    }
}
class J15Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J15Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        String gszhguanhao = canshuPanel.gszhguanhao.getText();
        String gszhpjwd = canshuPanel.gszhpjwd.getText();
        String gszhsg = canshuPanel.gszhsg.getText();
        String gszhyszg = canshuPanel.gszhyszg.getText();
        //3.6.3.15	高升转换 done   atg_hightoliter_in_t的uCont要改为int型。否则C取不到值   联调成功 第二轮成功
        List<atg_capacity_data_in_t> capacityData = new ArrayList<atg_capacity_data_in_t>();
        List<atg_hightoliter_in_t> highToLiterData = new ArrayList<atg_hightoliter_in_t>();
        atg_hightoliter_in_t b =new atg_hightoliter_in_t();
        List<atg_capacitytable_data_in_t> d=new ArrayList<atg_capacitytable_data_in_t>();
        atg_capacity_data_in_t a12 = new atg_capacity_data_in_t();
        a12.uOilCanNO = Integer.parseInt(gszhguanhao);
        a12.uCapacitySize = 50;
        for(int i=0;i<50;i++) {
            atg_capacitytable_data_in_t c =new atg_capacitytable_data_in_t();
            c.fLiter = 2.1+i*10;
            c.uHigh = 3+i;
            d.add(c);
            //System.out.println("Java out c.fLiter:" + c.fLiter + "\r\n");
            //System.out.println("Java out c.uHigh:" + c.uHigh + "\r\n");
        }
        a12.pCapacityTableData=d;
        capacityData.add(a12);
        b.uCount=1;
        b.fTotalHeight=Double.parseDouble(gszhyszg);
        b.fWaterHeight=Double.parseDouble(gszhsg);
        String[] wds = gszhpjwd.split(",");
        b.fOilTemp = Double.parseDouble(wds[0]);
        b.fOilTemp1 = Double.parseDouble(wds[1]);
        b.fOilTemp2 = Double.parseDouble(wds[2]);
        b.fOilTemp3 = Double.parseDouble(wds[3]);
        b.fOilTemp4 = Double.parseDouble(wds[4]);
        b.fOilTemp5 = Double.parseDouble(wds[5]);
        b.pCapacityData=capacityData;
        highToLiterData.add(b);
        List<atg_hightoliter_data_out_t> li = amg.HightOLiter(highToLiterData);
        jTextArea.append("atg_hightoliter_data_out_t size:"+li.size()+"\r\n");
        for(int i=0;i<li.size();i++) {
            jTextArea.append(i+"atg_hightoliter_data_out_t uOilCanNo:"+li.get(i).uOilCanNo+"\r\n");
            jTextArea.append(i+"atg_hightoliter_data_out_t fOilCubage:"+li.get(i).fOilCubage+"\r\n");
            jTextArea.append(i+"atg_hightoliter_data_out_t fOilStandCubage:"+li.get(i).fOilStandCubage+"\r\n");
            jTextArea.append(i+"atg_hightoliter_data_out_t fEmptyCubage:"+li.get(i).fEmptyCubage+"\r\n");
            jTextArea.append(i+"atg_hightoliter_data_out_t fWaterBulk:"+li.get(i).fWaterBulk+"\r\n");
        }
    }
}
class J16Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;
    public J16Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.3.16	液位仪开关机记录 done 联调成功 第二轮成功
        List<atg_powerrecord_in_t> data = new ArrayList<atg_powerrecord_in_t>();
        atg_powerrecord_in_t a4 = new atg_powerrecord_in_t();
        a4.uReqCount=Integer.parseInt(canshuPanel.uReqCountText.getText());
        a4.strDataTime=canshuPanel.strDataTimeText.getText();
        data.add(a4);
        List<atg_powerrecord_data_out_t> ret4 = amg.getPowerRecord(data);
        for(int i=0;i<ret4.size();i++) {
            jTextArea.append("java out  strDate:" + ret4.get(i).strDate+"\r\n");
            jTextArea.append("java out  strTime:" + ret4.get(i).strTime+"\r\n");
            jTextArea.append("java out  strOperateType:" + ret4.get(i).strOperateType+"\r\n");
            jTextArea.append("java out  uOilCanNO:" + ret4.get(i).uOilCanNO+"\r\n");
            jTextArea.append("java out  fTotalHeight:" + ret4.get(i).fTotalHeight+"\r\n");
            jTextArea.append("java out  fWaterHeight:" + ret4.get(i).fWaterHeight+"\r\n");
            jTextArea.append("java out  fOilTemp:" + ret4.get(i).fOilTemp+"\r\n");
            jTextArea.append("java out  fOilTemp1:" + ret4.get(i).fOilTemp1+"\r\n");
            jTextArea.append("java out  fOilTemp2:" + ret4.get(i).fOilTemp2+"\r\n");
            jTextArea.append("java out  fOilTemp3:" + ret4.get(i).fOilTemp3+"\r\n");
            jTextArea.append("java out  fOilTemp4:" + ret4.get(i).fOilTemp4+"\r\n");
            jTextArea.append("java out  fOilTemp5:" + ret4.get(i).fOilTemp5+"\r\n");
            jTextArea.append("java out  fOilCubage:" + ret4.get(i).fOilCubage+"\r\n");
            jTextArea.append("java out  fOilStandCubage:" + ret4.get(i).fOilStandCubage+"\r\n");
            jTextArea.append("java out  fEmptyCubage:" + ret4.get(i).fEmptyCubage+"\r\n");
            jTextArea.append("java out  fWaterBulk:" + ret4.get(i).fWaterBulk+"\r\n");
        }
    }
}
class J17Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;

    public J17Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //3.6.2.1	探棒校正参数设置 done 联调成功 第二轮成功 a.uOilTy=5;//应该是String 类型  需要修改~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        ArrayList<atg_correction_data_in_t> inputData = new ArrayList<atg_correction_data_in_t>();
        atg_correction_data_in_t a = new atg_correction_data_in_t();
        a.strDeviceModel=TbjzcsszPanel.strDeviceModelText.getText();
        a.strProbeNo=TbjzcsszPanel.strProbeNoText.getText();
        a.uOilTy=TbjzcsszPanel.strOilTypeText.getText();//应该是String 类型  需要修改~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        a.fOilCorrection    =Double.parseDouble(TbjzcsszPanel.fOilCorrectionText.getText());
        a.fWaterCorrection  =Double.parseDouble(TbjzcsszPanel.fWaterCorrectionText.getText());
        a.fProbeOffset      =Double.parseDouble(TbjzcsszPanel.fProbeOffsetText.getText());
        a.fTiltOffset	    =Double.parseDouble(TbjzcsszPanel.fTiltOffsetText.getText());
        String[] scwd = TbjzcsszPanel.wdwdscText.getText().split(",");
        String[] tbwd = TbjzcsszPanel.wdwdtbText.getText().split(",");
            a.fTemp1        =Double.parseDouble(scwd[0]);
            a.fProbeTemp1	=Double.parseDouble(tbwd[0]);
        a.fTemp2            =Double.parseDouble(scwd[1]);
            a.fProbeTemp2	=Double.parseDouble(tbwd[1]);
        a.fTemp3            =Double.parseDouble(scwd[2]);
            a.fProbeTemp3	=Double.parseDouble(tbwd[2]);
        a.fTemp4            =Double.parseDouble(scwd[3]);
            a.fProbeTemp4	=Double.parseDouble(tbwd[3]);
        a.fTemp5            =Double.parseDouble(scwd[4]);
            a.fProbeTemp5	=Double.parseDouble(tbwd[4]);
            a.fInitDesnsity =Double.parseDouble(TbjzcsszPanel.fInitDesnsityText.getText());
            a.fInitHighDiff =Double.parseDouble(TbjzcsszPanel.fInitHighDiffText.getText());
        a.fCorrectionFactor =Double.parseDouble(TbjzcsszPanel.fCorrectionFactorText.getText());
        inputData.add(a);
        int ret1 = amg.setCorrection(inputData);
        jTextArea.setText("java setCorrection return ret:" + ret1 + "\r\n");
        jTextArea.append("a.fInitDesnsity==="+a.fInitDesnsity);
    }
}
class J18Action implements ActionListener {
    ATGDevice amg = new ATGDevice();
    JTextArea jTextArea;

    public J18Action(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //探棒油罐配置
        List<atg_probecan_data_in_t> inputData2 = new ArrayList<atg_probecan_data_in_t>();
        atg_probecan_data_in_t a3 = new atg_probecan_data_in_t();
        a3.strDeviceModel=canshuPanel.strDeviceModelText.getText();
        a3.strProbeNo=canshuPanel.strProbeNoText.getText();
        a3.uProbePort=Integer.parseInt(canshuPanel.uProbePortText.getText());
        a3.uOilCanNo=Integer.parseInt(canshuPanel.uOilCanNoText.getText());
        a3.uOilType=canshuPanel.strOilTypeText.getText();//~~~~~~~~~~~~~~~~~~~~增加strOilType String类型~~~~~~~~~~~~~~~~~
        a3.strOilNo=canshuPanel.strOilNoText.getText();
        a3.strOilName=canshuPanel.strOilNameText.getText();//~~~~~~~~~~~~~~~~~~~~增加strOilName String类型~~~~~~~~~~~~~~~~~
        inputData2.add(a3);
        int ret11 = amg.setProbe(inputData2);
        jTextArea.append("java setProbe return ret:" + ret11+"\r\n");
    }
}
class clearTextArea implements ActionListener{
    JTextArea jTextArea ;
    public clearTextArea(JTextArea jTextArea){
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        jTextArea.setText("clear~~");
    }

}
class SzcsAction implements ActionListener{
    JTextArea jTextArea ;
    public SzcsAction(JTextArea jTextArea){
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame jFrame = new JFrame();
        canshuPanel canshupanel = new canshuPanel();
        jFrame.add(canshupanel);
        jFrame.setSize(800,600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        //jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
class TbcsAction implements ActionListener{
    JTextArea jTextArea ;
    public TbcsAction(JTextArea jTextArea){
        this.jTextArea = jTextArea;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame jFrame = new JFrame();
        TbjzcsszPanel tbjzcsszPanel = new TbjzcsszPanel();
        jFrame.add(tbjzcsszPanel);
        jFrame.setSize(800, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
class YbjAction implements ActionListener{
        JTextArea jTextArea ;
    public YbjAction(JTextArea jTextArea){
        this.jTextArea = jTextArea;
        }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame jFrame = new JFrame();
        YbjszPanel ybjszPanel = new YbjszPanel();
        jFrame.add(ybjszPanel);
        jFrame.setSize(800, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}