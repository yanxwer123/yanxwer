package com.kld.app.userctrl;

import com.kld.app.util.Common;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Created by luyan on 15/12/20.
 */
public class JCalendarPanel extends JPanel {
    final JDialog parent=new JDialog();
    final int txtwidth=120, txtheight=30;
    JCalendarPanel( )
    {
        this.setLayout(null);
        ImageIcon bticon=Common.createImageIcon(JCalendarPanel.class,"7.png");
        //this.setBorder(new LineBorder(Color.BLUE,2,true));
        this.setBounds(0,0,160,40);
        this.setPreferredSize(new Dimension(220, 30));
        final JTextField txt=new JTextField();
        final JButton bt=new JButton();
        txt.setBounds(5, 5, txtwidth, txtheight);
        txt.setEditable(false);
        txt.setBorder(new LineBorder(Color.GRAY, 1));
        bt.setBounds(120, 5, 30, 30);
        this.add(txt);
        this.add(bt);
        bt.setIcon(bticon);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = new Point(200, 200);
                JCalendarChooser chooser = new JCalendarChooser(parent, "选择日期", p, 60);
                Calendar d = chooser.showCalendarDialog();

                String strDate = d.get(Calendar.YEAR) + "-"
                        + (d.get(Calendar.MONTH) + 1) + "-"
                        + d.get(Calendar.DAY_OF_MONTH);
                txt.setText(strDate);
            }
        });
    }

}
