package com.kld.gsm.ATGDevice.ATGManagerTest;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Harry
 * @version 1.0
 * @CreationTime: 2015/12/26 13:29
 * @Description:
 */
public class canshuPanel extends JPanel {
    //实时库存
    public static JTextField shidiankucunguanhaoTextField = new JTextField(10);
    //整点库存
    public static JTextField zhengdiankucunguanhaoTextField = new JTextField(10);
    public static JTextField zhengdiankucunshijianTextField = new JTextField("20150611121212");
    //进油信息采集
    public static JTextField jinyouxinxicaijiguanhao = new JTextField(10);
    public static JTextField jinyouxinxicaijishijian = new JTextField("20150611121212");
    //油罐报警采集
    public static JTextField ygbjguanhao = new JTextField(10);
    public static JTextField ygbjshijian = new JTextField("20150611121212");
    //设备故障采集
    public static JTextField sbgzguanhao = new JTextField(10);
    public static JTextField sbgzshijian = new JTextField("20150611121212");
    //3.6.3.6	液位仪对时
    public static JTextField ywydsshijian = new JTextField("20150611121212");
    //3.6.3.9	容积表(全罐表)上传
    public static JTextField rjbscguanhao = new JTextField(10);
    //容积表(全罐表)下发
    public static JTextField rjbxfguanhao = new JTextField(10);
    public static JTextField rjbxfbanbenhao = new JTextField(10);
    //启动静态液位异常测试
    public static JTextField qdywycguanhao = new JTextField(10);
    //停止静态液位异常测试
    public static JTextField tzywycguanhao = new JTextField(10);
    //静态液位异常测试报告
    public static JTextField bgywycguanhao = new JTextField(10);
    public static JTextField bgywycshijian = new JTextField(10);
    //设备基础信息
    public static JTextField sbjcxxguanhao = new JTextField(10);
    //高升转换
    public static JTextField gszhguanhao = new JTextField(10);
    public static JTextField gszhyszg = new JTextField(10);
    public static JTextField gszhsg = new JTextField(10);
    public static JTextField gszhpjwd = new JTextField(10);
    //探棒油罐关系配置
    public static JTextField strDeviceModelText = new JTextField(10);
    public static JTextField strProbeNoText      = new JTextField(10);
    public static JTextField uProbePortText     = new JTextField(10);
    public static JTextField uOilCanNoText      = new JTextField(10);
    public static JTextField strOilTypeText     = new JTextField(10);
    public static JTextField strOilNoText       = new JTextField(10);
    public static JTextField strOilNameText     = new JTextField(10);
    //液位仪开关记录
    public static JTextField uReqCountText = new JTextField(10);
    public static JTextField strDataTimeText = new JTextField("20150611121212");


    public canshuPanel(){
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
        //是用来控制添加进的组件的显示位置
        s.fill = GridBagConstraints.BOTH;
        //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。
        JLabel shishikucunLabel = new JLabel("----------------实时库存-----------------");
        JLabel guanhaoLabel = new JLabel("罐号：");
        this.add(shishikucunLabel);
        this.add(guanhaoLabel);
        this.add(shidiankucunguanhaoTextField);
        s.gridwidth=0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        layout.setConstraints(shishikucunLabel, s);//设置组件
        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        layout.setConstraints(guanhaoLabel, s);//设置组件
        s.gridwidth=2;
        layout.setConstraints(shidiankucunguanhaoTextField, s);//设置组件
        JPanel b1= new JPanel();
        this.add(b1);
        s.gridwidth=0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        layout.setConstraints(b1, s);//设置组件
        JLabel zhengdiankucunLabel = new JLabel("----------------整点库存-----------------");
        JLabel zhengdiankucunLabel2 = new JLabel("罐号");
        JLabel zhengdiankucunshijianLabel = new JLabel("时间");
        this.add(zhengdiankucunLabel);
        this.add(zhengdiankucunLabel2);
        this.add(zhengdiankucunguanhaoTextField);
        this.add(zhengdiankucunshijianLabel);
        this.add(zhengdiankucunshijianTextField);
        s.gridwidth=0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        layout.setConstraints(zhengdiankucunLabel, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(zhengdiankucunLabel2, s);//设置组件
        layout.setConstraints(zhengdiankucunguanhaoTextField, s);//设置组件
        layout.setConstraints(zhengdiankucunshijianLabel, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(zhengdiankucunshijianTextField, s);//设置组件
        JLabel jinyouxinxicaijiLabel = new JLabel("----------------进油信息采集-----------------");
        JLabel jyxxcj1 = new JLabel("罐号：");
        JLabel jyxxcj2 = new JLabel("时间：");
        this.add(jinyouxinxicaijiLabel);
        this.add(jyxxcj1);
        this.add(jinyouxinxicaijiguanhao);
        this.add(jyxxcj2);
        this.add(jinyouxinxicaijishijian);
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(jinyouxinxicaijiLabel, s);//设置组件
        s.gridwidth=1;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(jyxxcj1, s);//设置组件
        layout.setConstraints(jinyouxinxicaijiguanhao, s);//设置组件
        layout.setConstraints(jyxxcj2, s);//设置组件
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(jinyouxinxicaijishijian, s);//设置组件
        JLabel ygbj = new JLabel("----------------油罐报警-----------------");
        JLabel ygbj1 = new JLabel("罐号：");
        JLabel ygbj2 = new JLabel("时间：");
        this.add(ygbj);
        this.add(ygbj1);
        this.add(ygbjguanhao);
        this.add(ygbj2);
        this.add(ygbjshijian);
        s.gridwidth=0;
        layout.setConstraints(ygbj, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ygbj1, s);//设置组件
        layout.setConstraints(ygbjguanhao, s);//设置组件
        layout.setConstraints(ygbj2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(ygbjshijian, s);//设置组件
        JLabel sbgz = new JLabel("----------------设备故障-----------------");
        JLabel sbgz1 = new JLabel("罐号：");
        JLabel sbgz2 = new JLabel("时间：");
        this.add(sbgz);
        this.add(sbgz1);
        this.add(sbgzguanhao);
        this.add(sbgz2);
        this.add(sbgzshijian);
        s.gridwidth=0;
        layout.setConstraints(sbgz, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(sbgz1, s);//设置组件
        layout.setConstraints(sbgzguanhao, s);//设置组件
        layout.setConstraints(sbgz2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(sbgzshijian, s);//设置组件
        JLabel ywyds = new JLabel("----------------液位仪对时-----------------");
        JLabel ywyds2 = new JLabel("时间：");
        this.add(ywyds);
        this.add(ywyds2);
        this.add(ywydsshijian);
        s.gridwidth=0;
        layout.setConstraints(ywyds, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ywyds2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(ywydsshijian, s);//设置组件
        JLabel rjbsc = new JLabel("----------------容积表上传-----------------");
        JLabel rjbsc1 = new JLabel("罐号");
        this.add(rjbsc);
        this.add(rjbsc1);
        this.add(rjbscguanhao);
        s.gridwidth=0;
        layout.setConstraints(rjbsc, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(rjbsc1, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(rjbscguanhao, s);//设置组件
        JLabel rjbxf = new JLabel("----------------容积表下发-----------------");
        JLabel rjbxf1 = new JLabel("罐号");
        JLabel rjbxf2 = new JLabel("版本号");
        this.add(rjbxf);
        this.add(rjbxf1);
        this.add(rjbxfguanhao);
        this.add(rjbxf2);
        this.add(rjbxfbanbenhao);

        s.gridwidth=0;
        layout.setConstraints(rjbxf, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(rjbxf1, s);//设置组件
        layout.setConstraints(rjbxfguanhao, s);//设置组件
        layout.setConstraints(rjbxf2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(rjbxfbanbenhao, s);//设置组件
        JLabel qdywyc = new JLabel("----------------启动液位异常-----------------");
        JLabel qdywyc1 = new JLabel("罐号");
        this.add(qdywyc);
        this.add(qdywyc1);
        this.add(qdywycguanhao);
        s.gridwidth=0;
        layout.setConstraints(qdywyc, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(qdywyc1, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(qdywycguanhao, s);//设置组件
        JLabel tzywyc = new JLabel("----------------停止液位异常-----------------");
        JLabel tzywyc1 = new JLabel("罐号");
        this.add(tzywyc);
        this.add(tzywyc1);
        this.add(tzywycguanhao);
        s.gridwidth=0;
        layout.setConstraints(tzywyc, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tzywyc1, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(tzywycguanhao, s);//设置组件
        JLabel bgywyc = new JLabel("----------------液位异常报告-----------------");
        JLabel bgywyc1 = new JLabel("罐号");
        JLabel bgywyc2 = new JLabel("时间");
        this.add(bgywyc);
        this.add(bgywyc1);
        this.add(bgywycguanhao);
        this.add(bgywyc2);
        this.add(bgywycshijian);
        s.gridwidth=0;
        layout.setConstraints(bgywyc, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(bgywyc1, s);//设置组件
        layout.setConstraints(bgywycguanhao, s);//设置组件
        layout.setConstraints(bgywyc2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(bgywycshijian, s);//设置组件
        JLabel sbjcxx = new JLabel("----------------设备基础信息-----------------");
        JLabel sbjcxx1 = new JLabel("罐号");
        this.add(sbjcxx);
        this.add(sbjcxx1);
        this.add(sbjcxxguanhao);
        s.gridwidth=0;
        layout.setConstraints(sbjcxx, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(sbjcxx1, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(sbjcxxguanhao, s);//设置组件
        JLabel gszh = new JLabel("----------------高升转换-----------------");
        JLabel gszh1 = new JLabel("罐号");
        JLabel gszh2 = new JLabel("油水总高");
        JLabel gszh3 = new JLabel("水高");
        JLabel gszh4 = new JLabel("平均,5点温度");
        this.add(gszh);
        this.add(gszh1);
        this.add(gszhguanhao);
        this.add(gszh2);
        this.add(gszhyszg);
        this.add(gszh3);
        this.add(gszhsg);
        this.add(gszh4);
        this.add(gszhpjwd);
        s.gridwidth=0;
        layout.setConstraints(gszh, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(gszh1, s);//设置组件
        layout.setConstraints(gszhguanhao, s);//设置组件
        layout.setConstraints(gszh2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(gszhyszg, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(gszh3, s);//设置组件
        layout.setConstraints(gszhsg, s);//设置组件
        layout.setConstraints(gszh4, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(gszhpjwd, s);//设置组件
        JLabel tbyggx = new JLabel("----------------探棒油罐关系配置-----------------");
        JLabel tbyggx1 = new JLabel("设备型号");
        JLabel tbyggx2 = new JLabel("探棒编号");
        JLabel tbyggx3 = new JLabel("探棒端口");
        JLabel tbyggx4 = new JLabel("油罐编号");
        JLabel tbyggx5 = new JLabel("油品类型");
        JLabel tbyggx6 = new JLabel("油品编码");
        JLabel tbyggx7 = new JLabel("油品名称");
        this.add(tbyggx);
        this.add(tbyggx1);
        this.add(strDeviceModelText);
        this.add(tbyggx2);
        this.add(strProbeNoText);
        this.add(tbyggx3);
        this.add(uProbePortText);
        this.add(tbyggx4);
        this.add(uOilCanNoText);
        this.add(tbyggx5);
        this.add(strOilTypeText);
        this.add(tbyggx6);
        this.add(strOilNoText);
        this.add(tbyggx7);
        this.add(strOilNameText);
        s.gridwidth=0;
        layout.setConstraints(tbyggx, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbyggx1, s);//设置组件
        layout.setConstraints(strDeviceModelText, s);//设置组件
        layout.setConstraints(tbyggx2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(strProbeNoText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbyggx3, s);//设置组件
        layout.setConstraints(uProbePortText, s);//设置组件
        layout.setConstraints(tbyggx4, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(uOilCanNoText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbyggx5, s);//设置组件
        layout.setConstraints(strOilTypeText, s);//设置组件
        layout.setConstraints(tbyggx6, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(strOilNoText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(tbyggx7, s);//设置组件
        layout.setConstraints(strOilNameText, s);//设置组件
        layout.setConstraints(new Label(), s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(new Label(), s);//设置组件
        JLabel ywykgjl = new JLabel("----------------液位仪开关记录-----------------");
        JLabel ywykgjl1 = new JLabel("返回条数");
        JLabel ywykgjl2 = new JLabel("时间");
        this.add(ywykgjl);
        this.add(ywykgjl1);
        this.add(uReqCountText);
        this.add(ywykgjl2);
        this.add(strDataTimeText);
        s.gridwidth=0;
        layout.setConstraints(ywykgjl, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ywykgjl1, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(uReqCountText, s);//设置组件
        s.gridwidth=1;
        layout.setConstraints(ywykgjl2, s);//设置组件
        s.gridwidth=0;
        layout.setConstraints(strDataTimeText, s);//设置组件

    }
}