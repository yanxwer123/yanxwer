package com.kld.gsm.ATGDevice.ATGManagerTest;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/12/26 16:04
 * @Description:
 */
public class TbjzcsszPanel extends JPanel {
    public static JTextField strDeviceModelText = new JTextField(10);
    public static JTextField strProbeNoText = new JTextField(10);
    public static JTextField strOilTypeText = new JTextField(10);
    public static JTextField fOilCorrectionText = new JTextField(10);
    public static JTextField fWaterCorrectionText = new JTextField(10);
    public static JTextField fProbeOffsetText = new JTextField(10);
    public static JTextField fTiltOffsetText = new JTextField(10);
    public static JTextField fInitDesnsityText = new JTextField(10);
    public static JTextField fInitHighDiffText = new JTextField(10);
    public static JTextField fCorrectionFactorText = new JTextField(10);
    public static JTextField wdwdscText = new JTextField(10);
    public static JTextField wdwdtbText = new JTextField(10);


    public TbjzcsszPanel(){
        JLabel tbjzcs = new JLabel("----------------探棒校正参数设置-----------------");
        JLabel tbjzcssz = new JLabel("设备型号");
        JLabel tbjzcssz1 = new JLabel("探棒编号");
        JLabel tbjzcssz2 = new JLabel("油品类型");
        JLabel tbjzcssz3 = new JLabel("油位0点校正（毫米）");
        JLabel tbjzcssz4 = new JLabel("水位0点校正（毫米）");
        JLabel tbjzcssz5 = new JLabel("探棒偏移（毫米）");
        JLabel tbjzcssz6 = new JLabel("倾斜偏移（毫米）");
        JLabel tbjzcssz7 = new JLabel("初始密度(kg/m3)");
        JLabel tbjzcssz8 = new JLabel("初始高度差(mm)");
        JLabel tbjzcssz9 = new JLabel("油位与密度位之间的高度差");
        JLabel tbjzcssz10 = new JLabel("密度的修正系数");
        JLabel tbjzcssz11 = new JLabel("5点温度实测");
        JLabel tbjzcssz12 = new JLabel("5点温度探棒");
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
        //是用来控制添加进的组件的显示位置
        s.fill = GridBagConstraints.BOTH;
        this.add(tbjzcs);
        this.add(tbjzcssz1);
        this.add(strDeviceModelText);
        this.add(tbjzcssz2);
        this.add(strProbeNoText);
        this.add(tbjzcssz3);
        this.add(strOilTypeText);
        this.add(tbjzcssz4);
        this.add(fOilCorrectionText);
        this.add(tbjzcssz5);
        this.add(fWaterCorrectionText);
        this.add(tbjzcssz6);
        this.add(fProbeOffsetText);
        this.add(tbjzcssz7);
        this.add(fTiltOffsetText);
        this.add(tbjzcssz8);
        this.add(fInitDesnsityText);
        this.add(tbjzcssz9);
        this.add(fInitHighDiffText);
        this.add(tbjzcssz10);
        this.add(fCorrectionFactorText);
        this.add(tbjzcssz11);
        this.add(wdwdscText);
        this.add(tbjzcssz12);
        this.add(wdwdtbText);
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(tbjzcs, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbjzcssz1, s);//设置组件
        layout.setConstraints(strDeviceModelText, s);//设置组件
        layout.setConstraints(tbjzcssz2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(strProbeNoText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbjzcssz3, s);//设置组件
        layout.setConstraints(strOilTypeText, s);//设置组件
        layout.setConstraints(tbjzcssz4, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fOilCorrectionText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbjzcssz5, s);//设置组件
        layout.setConstraints(fWaterCorrectionText, s);//设置组件
        layout.setConstraints(tbjzcssz6, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fProbeOffsetText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbjzcssz7, s);//设置组件
        layout.setConstraints(fTiltOffsetText, s);//设置组件
        layout.setConstraints(tbjzcssz8, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fInitDesnsityText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbjzcssz9, s);//设置组件
        layout.setConstraints(fInitHighDiffText, s);//设置组件
        layout.setConstraints(tbjzcssz10, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(fCorrectionFactorText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbjzcssz11, s);//设置组件
        layout.setConstraints(wdwdscText, s);//设置组件
        layout.setConstraints(tbjzcssz12, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(wdwdtbText, s);//设置组件
    }
}
