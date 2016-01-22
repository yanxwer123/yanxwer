package com.kld.app.userctrl;

import com.kld.app.util.Common;
import com.kld.gsm.util.DateUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by luyan on 15/12/20.
 */
public class JTimePanel extends JPanel {
    final JDialog parent=new JDialog();
    final int txtwidth=126, txtheight=20,btwidth=24,btheight=24;
    private JTextField txt=new JTextField();
    private  JTimeChooser chooser;
    private Calendar d;
    private JButton bt;

    public JTimePanel()
    {
        this.setLayout(null);
        ImageIcon bticon=  Common.createImageIcon(super.getClass(), "calendar.png");
        //this.setBorder(new LineBorder(Color.BLUE,1,true));
        this.setBounds(0,0,btwidth+txtwidth,40);
        this.setPreferredSize(new Dimension(220, 30));

         bt=new JButton();
        txt.setBounds(0, 5, txtwidth, txtheight);
        txt.setEditable(false);
        txt.setBorder(new LineBorder(Color.GRAY, 1));
        bt.setBounds(txtwidth,2, btwidth, btheight);
         bt.setIcon(bticon);
        bt.setBorder(new LineBorder(Color.cyan,0));
        this.add(txt);
        this.add(bt);

        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point p = new Point(200, 200);
                chooser= new JTimeChooser(parent,"选择时间",p,21);

                d = chooser.showTimeDialog();
              /*  String strDate= d.get(Calendar.YEAR) + "-"
                        + (d.get(Calendar.MONTH) + 1) + "-"
                        + d.get(Calendar.DAY_OF_MONTH)
                        +"  "+d.get(Calendar.HOUR_OF_DAY)
                        +":"+d.get(Calendar.MINUTE)
                        +":"+d.get(Calendar.SECOND);*/
                StringBuilder sb=new StringBuilder();
                sb.append( d.get(Calendar.YEAR) + "-");
                sb.append(addzero(d.get(Calendar.MONTH) + 1) + "-"+ addzero(d.get(Calendar.DAY_OF_MONTH)));
                sb.append(" "+addzero(d.get(Calendar.HOUR_OF_DAY)));
                sb.append(":"+addzero(d.get(Calendar.MINUTE)));
                sb.append(":" + addzero(d.get(Calendar.SECOND)));

                txt.setText(sb.toString());
            }
        });

    }

    public String addzero(int i){
        if (i<10){
            return "0"+i;
        }else{
            return i+"";
        }
    }

    public Date getDate(){
        try {
            return DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss",txt.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDate(Date date){
        txt.setText(DateUtil.getDate(date,"yyyy-MM-dd HH:mm:ss"));
    }

}
