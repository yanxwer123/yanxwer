package com.kld.gsm.ATGDevice.ATGManagerTest;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/12/26 16:36
 * @Description:
 */
public class YbjszPanel extends JPanel{
    //预报警设置
    public static JTextField ybjszguanhao    = new JTextField(10);
    public static JTextField fHighAlarmText    = new JTextField(10);
    public static JTextField fHighWarningText  = new JTextField(10);
    public static JTextField fLowWarningText   = new JTextField(10);
    public static JTextField fLowAlarmText     = new JTextField(10);
    public static JTextField fWaterAlarmText   = new JTextField(10);
    public static JTextField fWaterWarningText = new JTextField(10);
    public static JTextField fThiefAlarmText   = new JTextField(10);
    public static JTextField fLeakAlarmText    = new JTextField(10);
    public static JTextField fPercolatingAlarmText = new JTextField(10);
    public static JTextField fHighTempAlarmText    = new JTextField(10);
    public static JTextField fLowTempAlarmText     = new JTextField(10);
    public YbjszPanel(){
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
        //是用来控制添加进的组件的显示位置
        s.fill = GridBagConstraints.BOTH;
        JLabel ybjsz = new JLabel("----------------预报警设置-----------------");
        JLabel ybjsz1 = new JLabel("油罐编号");
        JLabel ybjsz2 = new JLabel("低液位预警,单位：毫米");
        JLabel ybjsz3 = new JLabel("低液位报警,单位：毫米");
        JLabel ybjsz4 = new JLabel("高液位预警,单位：毫米");
        JLabel ybjsz5 = new JLabel("高液位报警,单位：毫米");
        JLabel ybjsz6 = new JLabel("高水位报警，,单位：毫米");
        JLabel ybjsz12 = new JLabel("高水位预警，,单位：毫米");
        JLabel ybjsz7 = new JLabel("盗油报警,单位：升/小时，默认300L/H");
        JLabel ybjsz8 = new JLabel("漏油报警,单位：升/小时，默认60L/H");
        JLabel ybjsz9 = new JLabel("渗漏报警,单位：升/小时，默认0.8L/H");
        JLabel ybjsz10 = new JLabel("高温报警，单位：摄氏度。温度>=55");
        JLabel ybjsz11 = new JLabel("低温报警，单位：摄氏度。温度<=-10");
        this.add(ybjsz);
        this.add(ybjsz1);
        this.add(ybjszguanhao);
        this.add(ybjsz2);
        this.add(fLowWarningText);
        this.add(ybjsz3);
        this.add(fLowAlarmText);
        this.add(ybjsz4);
        this.add(fHighWarningText);
        this.add(ybjsz5);
        this.add(fHighAlarmText);
        this.add(ybjsz6);
        this.add(fWaterAlarmText);
        this.add(ybjsz7);
        this.add(fThiefAlarmText);
        this.add(ybjsz8);
        this.add(fLeakAlarmText);
        this.add(ybjsz9);
        this.add(fPercolatingAlarmText);
        this.add(ybjsz10);
        this.add(fHighTempAlarmText);
        this.add(ybjsz11);
        this.add(fLowTempAlarmText);
        this.add(ybjsz12);
        this.add(fWaterWarningText);

        s.gridwidth=0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        layout.setConstraints(ybjsz, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ybjsz1, s);//设置组件
        layout.setConstraints(ybjszguanhao, s);//设置组件
        layout.setConstraints(ybjsz2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fLowWarningText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ybjsz3, s);//设置组件
        layout.setConstraints(fLowAlarmText, s);//设置组件
        layout.setConstraints(ybjsz4, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fHighWarningText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ybjsz5, s);//设置组件
        layout.setConstraints(fHighAlarmText, s);//设置组件
        layout.setConstraints(ybjsz6, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fWaterAlarmText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ybjsz7, s);//设置组件
        layout.setConstraints(fThiefAlarmText, s);//设置组件
        layout.setConstraints(ybjsz8, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fLeakAlarmText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ybjsz9, s);//设置组件
        layout.setConstraints(fPercolatingAlarmText, s);//设置组件
        layout.setConstraints(ybjsz10, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fHighTempAlarmText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ybjsz11, s);//设置组件
        layout.setConstraints(fLowTempAlarmText, s);//设置组件
        layout.setConstraints(ybjsz12, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fWaterWarningText, s);//设置组件
    }

}
