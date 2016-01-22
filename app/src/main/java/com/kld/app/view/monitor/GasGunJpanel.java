package com.kld.app.view.monitor;

import com.kld.app.util.Common;
import com.kld.app.util.Constant;
import com.kld.gsm.ATG.domain.SysManageOilGunInfo;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:zjggle@gmail.com">Richard Zhou</a>
 * @version 1.0
 * @CreationTime: 2015-11-30 16:59
 * @Description:
 */
public class GasGunJpanel {

    SysManageOilGunInfo gunInfos;
    Integer oilcan;
    public JPanel panel;
    public JLabel Label2;//泵码
    public JLabel Label3;//qty
    public JLabel Label5;//amount
    public JLabel Label7;//Price
    public JButton hold1;//图片

    public GasGunJpanel() {
    }

    public GasGunJpanel(SysManageOilGunInfo gunInfos, Integer oilcan) {
        this.gunInfos = gunInfos;
        this.oilcan = oilcan;
        //System.out.println("抢号" + gunInfos.getOilgun() + "属于 油罐：[" + oilcan + " ]  ");
        panel = new JPanel();
        panel.setLayout(null);
        //System.out.println("----:" + gunInfos.toString());
        JLabel Label = new JLabel(gunInfos.getOilgun() + "#");
        Label.setFont(Constant.BOTTOMFONT);
        Label.setBounds(45, 10, 100, 12);
        Label.setForeground(new Color(Integer.decode(Constant.HOMEPAGECOCLER)));
        panel.add(Label);

        JLabel Label1 = new JLabel("油机号：" + gunInfos.getMacno().toString());
        Label1.setFont(Constant.BOTTOM_FONT);

      //  Label1.setBounds(6, 40, 80, 12);
        Label1.setBounds( 45, 28, 130, 12);
        Label1.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        panel.add(Label1);

//        Label2 = new JLabel("泵码：" + gunInfos.getInitpump());
//        Label2.setFont(Constant.BOTTOM_FONT);
//        Label2.setBounds(45, 28, 150, 12);
//        Label2.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
//        panel.add(Label2);

        Label3 = new JLabel("0.0");
        Label3.setFont(Constant.BOTTOM_FONT);
        Label3.setBounds(6, 43, 80, 12);
        Label3.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        panel.add(Label3);

        JLabel Label4 = new JLabel("升");
        Label4.setFont(Constant.BOTTOM_FONT);
        Label4.setBounds(80, 43, 80, 12);
        Label4.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        panel.add(Label4);

        Label5 = new JLabel("0.0");
        Label5.setFont(Constant.BOTTOM_FONT);
        Label5.setBounds(6, 57, 80, 12);
        Label5.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        panel.add(Label5);

        JLabel Label6 = new JLabel("元");
        Label6.setFont(Constant.BOTTOM_FONT);
        Label6.setBounds(80, 57, 80, 12);
        Label6.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        panel.add(Label6);

        Label7 = new JLabel("0.0");
        Label7.setFont(Constant.BOTTOM_FONT);
        Label7.setBounds(6, 71, 80, 12);
        Label7.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        panel.add(Label7);

        JLabel Label8 = new JLabel("元/升");
        Label8.setFont(Constant.BOTTOM_FONT);
        Label8.setBounds(80,71, 100, 12);
        Label8.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
        panel.add(Label8);


        //System.out.println("油枪状态： " + "init GunStatus");

        hold1 = new JButton();
        hold1.setBorderPainted(false);
        hold1.setContentAreaFilled(false);
        hold1.setIcon(Common.createImageIcon(this.getClass(), "green_8.png"));
        hold1.setBounds(3, 2, 38, 38);
        panel.add(hold1);

    }


}
