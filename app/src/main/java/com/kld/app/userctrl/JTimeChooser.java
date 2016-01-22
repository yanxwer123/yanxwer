package com.kld.app.userctrl;

/**
 * Created by luyan on 15/12/20.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Created by luyan on 15/12/20.
 */
public class JTimeChooser extends JDialog implements ActionListener {
    private static final long serialVersionUID = -3758522951261503946L;
    private static final int DEFAULT_WIDTH = 325;
    private static final int DEFAULT_HEIGHT = 280;
    private int showYears = 100;
    private JButton button1 = null;
    private JButton button2 = null;
    private JButton button3 = null;
    private JButton button4 = null;
    private JComboBox comboBox1 = null;
    private JComboBox comboBox2 = null;
    private Calendar calendar = null;
    private String[] years = null;
    private String[] months = null;
    private int year1;
    private int month1;
    private int day1;
    private JPanel panel = null;
    private String[] tits = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private JComboBox comboBox3 = null;
    private JComboBox comboBox4 = null;
    private JComboBox comboBox5 = null;
    private int rowlens = 5;
    private String title = "选择时间";
    private Point location = null;
    private boolean flag;

    public JTimeChooser(Dialog parent) {
        super(parent, true);
        this.setTitle(this.title);
        this.initDatas();
    }

    public JTimeChooser(Dialog parent, String title) {
        super(parent, title, true);
        this.initDatas();
    }

    public JTimeChooser(Dialog parent, String title, Point location) {
        super(parent, title, true);
        this.location = location;
        this.initDatas();
    }

    public JTimeChooser(Dialog parent, String title, Point location, int showYears) {
        super(parent, title, true);
        this.location = location;
        if(showYears > 0) {
            this.showYears = showYears;
        }

        this.initDatas();
    }

    public JTimeChooser(Frame parent) {
        super(parent, true);
        this.setTitle(this.title);
        this.initDatas();
    }

    public JTimeChooser(Frame parent, Point location) {
        super(parent, true);
        this.setTitle(this.title);
        this.location = location;
        this.initDatas();
    }

    public JTimeChooser(Frame parent, String title, Point location) {
        super(parent, title, true);
        this.location = location;
        this.initDatas();
    }

    public JTimeChooser(Frame parent, String title, Point location, int showYears) {
        super(parent, title, true);
        this.location = location;
        if(showYears > 0) {
            this.showYears = showYears;
        }

        this.initDatas();
    }

    public JTimeChooser(Dialog parent, Point location) {
        super(parent, true);
        this.setTitle(this.title);
        this.location = location;
        this.initDatas();
    }

    public JTimeChooser(Frame parent, String title) {
        super(parent, title, true);
        this.initDatas();
    }

    private void initDatas() {
        this.calendar = Calendar.getInstance();
        this.year1 = this.calendar.get(1);
        this.month1 = this.calendar.get(2);
        this.day1 = this.calendar.get(5);
        this.years = new String[this.showYears];
        this.months = new String[12];

        int start;
        for(start = 0; start < this.months.length; ++start) {
            this.months[start] = " " + this.formatDay(start + 1);
        }

        start = this.year1 - this.showYears / 2;

        for(int i = start; i < start + this.showYears; ++i) {
            this.years[i - start] = String.valueOf(i);
        }

        this.calendar.set(11, 0);
        this.calendar.set(12, 0);
        this.calendar.set(13, 0);
    }

    private Dimension getStartDimension(int width, int height) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim.width = dim.width / 2 - width / 2;
        dim.height = dim.height / 2 - height / 2;
        return dim;
    }

    public Calendar showTimeDialog() {
        this.initCompents();
        return this.calendar;
    }

    private void initCompents() {
        this.setLayout(new BorderLayout());
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(1));
        panel3.setBackground(Color.WHITE);
        this.showNorthPanel(panel3);
        this.add(panel3, "North");
        this.add(this.printCalendar(), "Center");
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        panel2.setLayout(new FlowLayout(1));
        this.showSouthPanel(panel2);
        this.add(panel2, "South");
        this.setSize(325, 280);
        if(this.location == null) {
            Dimension dim = this.getStartDimension(325, 280);
            this.setLocation(dim.width, dim.height);
        } else {
            this.setLocation(this.location);
        }

        this.setVisible(true);
    }

    private void showNorthPanel(JPanel panel) {
        this.button2 = new JButton("上一月");
        this.button2.setToolTipText("上一月");
        this.button2.addActionListener(this);
        panel.add(this.button2);
        this.comboBox1 = new JComboBox(this.years);
        this.comboBox1.setSelectedItem(String.valueOf(this.year1));
        this.comboBox1.setToolTipText("年份");
        this.comboBox1.setMaximumRowCount(this.rowlens);
        this.comboBox1.setActionCommand("year");
        this.comboBox1.addActionListener(this);
        panel.add(this.comboBox1);
        this.comboBox2 = new JComboBox(this.months);
        this.comboBox2.setSelectedItem(" " + this.formatDay(this.month1 + 1));
        this.comboBox2.setToolTipText("月份");
        this.comboBox2.setMaximumRowCount(this.rowlens);
        this.comboBox2.addActionListener(this);
        this.comboBox2.setActionCommand("month");
        panel.add(this.comboBox2);
        this.button3 = new JButton("下一月");
        this.button3.setToolTipText("下一月");
        this.button3.addActionListener(this);
        panel.add(this.button3);
    }

    private void showSouthPanel(JPanel panel) {
        JPanel panel_23 = new JPanel();
        panel_23.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JLabel label21 = new JLabel("时间:");
        label21.setForeground(Color.GRAY);
        panel_23.add(label21);
        this.comboBox3 = new JComboBox(this.getHours());
        this.comboBox3.setMaximumRowCount(this.rowlens);
        this.comboBox3.setToolTipText("时");
        this.comboBox3.setActionCommand("hour");
        this.comboBox3.addActionListener(this);

        panel_23.add(this.comboBox3);
        // JLabel label22 = new JLabel("时 ");
        // label22.setForeground(Color.GRAY);
        //  panel_23.add(label22);
        this.comboBox4 = new JComboBox(this.getMins());
        this.comboBox4.setToolTipText("分");
        this.comboBox4.setMaximumRowCount(this.rowlens);
        this.comboBox4.setActionCommand("minute");
        this.comboBox4.addActionListener(this);
        panel_23.add(this.comboBox4);
        // JLabel label23 = new JLabel("分 ");
        //  label23.setForeground(Color.GRAY);
        //  panel_23.add(label23);
        this.comboBox5 = new JComboBox(this.getMins());
        this.comboBox5.setToolTipText("秒");
        this.comboBox5.setActionCommand("second");
        this.comboBox5.addActionListener(this);
        this.comboBox5.setMaximumRowCount(this.rowlens);
        panel_23.add(this.comboBox5);
        //JLabel label24 = new JLabel("秒");
        // label24.setForeground(Color.GRAY);
        // panel_23.add(label24);
        this.button1 = new JButton("确定");
        this.button1.setToolTipText("确定");
        this.button1.addActionListener(this);
        panel_23.add(this.button1);
        panel.add(panel_23);

    }

    private Object[] getHours() {
        Object[] hs = new Object[24];

        for(int i = 0; i < hs.length; ++i) {
            hs[i] = Integer.valueOf(i);
        }

        return hs;
    }

    private Object[] getMins() {
        Object[] hs = new Object[60];

        for(int i = 0; i < hs.length; ++i) {
            hs[i] = Integer.valueOf(i);
        }

        return hs;
    }

    private JPanel printCalendar() {
        this.panel = new JPanel();
        this.panel.setLayout(new GridLayout(7, 7, 0, 0));
        this.panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        int year2 = this.calendar.get(1);
        int month2 = this.calendar.get(2);
        this.calendar.set(5, 1);
        int weekDay = this.calendar.get(7);
        JButton b = null;

        int count;
        for(count = 0; count < this.tits.length; ++count) {
            b = new JButton("<html><b>" + this.tits[count] + "</b></html>");
            b.setForeground(new Color(100, 0, 102));
            b.setEnabled(false);
            this.panel.add(b);
        }

        count = 0;

        int currday;
        for(currday = 1; currday < weekDay; ++currday) {
            b = new JButton(" ");
            b.setEnabled(false);
            this.panel.add(b);
            ++count;
        }

        boolean var9 = false;
        String dayStr = null;

        do {
            currday = this.calendar.get(5);
            dayStr = this.formatDay(currday);
            if(currday == this.day1 && this.month1 == month2 && this.year1 == year2) {
                b = new JButton("[" + dayStr + "]");
                b.setForeground(Color.RED);
            } else {
                b = new JButton(dayStr);
                b.setForeground(Color.BLUE);
            }

            ++count;
            b.setToolTipText(year2 + "-" + this.formatDay(month2 + 1) + "-" + dayStr);
            b.setBorder(BorderFactory.createEtchedBorder());
            b.addActionListener(this);
            this.panel.add(b);
            this.calendar.add(5, 1);
        } while(this.calendar.get(2) == month2);

        this.calendar.add(2, -1);
        if(!this.flag) {
            this.calendar.set(5, this.day1);
            this.flag = true;
        }

        for(int i = count; i < 42; ++i) {
            b = new JButton(" ");
            b.setEnabled(false);
            this.panel.add(b);
        }

        return this.panel;
    }

    private String formatDay(int day) {
        return day < 10?"0" + day:String.valueOf(day);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int value;
        int day9;
        int month5;
        if("下一月".equals(command)) {
            this.calendar.add(2, 1);
            value = this.calendar.get(1);
            day9 = this.year1 + this.showYears / 2 - 1;
            if(value > day9) {
                this.calendar.add(2, -1);
                return;
            }

            month5 = this.calendar.get(2) + 1;
            this.comboBox1.setSelectedItem(String.valueOf(value));
            this.comboBox2.setSelectedItem(" " + this.formatDay(month5));
            this.updatePanel();
        } else if("上一月".equals(command)) {
            this.calendar.add(2, -1);
            value = this.calendar.get(1);
            day9 = this.year1 - this.showYears / 2;
            if(value < day9) {
                this.calendar.add(2, 1);
                return;
            }

            month5 = this.calendar.get(2) + 1;
            this.comboBox1.setSelectedItem(String.valueOf(value));
            this.comboBox2.setSelectedItem(" " + this.formatDay(month5));
            this.updatePanel();
        } else if("确定".equals(command)) {
            this.dispose();
        } else {
            JButton value1;
            if(command.matches("^\\d+$")) {
                value1 = (JButton)e.getSource();
                if(this.button4 == null) {
                    this.button4 = value1;
                } else {
                    this.button4.setForeground(Color.BLUE);
                    this.button4.setFont(value1.getFont());
                    this.button4 = value1;
                }

                value1.setForeground(Color.BLACK);
                value1.setFont(this.button4.getFont().deriveFont(1));
                day9 = Integer.parseInt(command);
                this.calendar.set(5, day9);
            } else if(command.startsWith("[")) {
                value1 = (JButton)e.getSource();
                if(this.button4 == null) {
                    this.button4 = value1;
                } else {
                    this.button4.setForeground(Color.BLUE);
                    this.button4.setFont(value1.getFont());
                    this.button4 = value1;
                }

                value1.setForeground(Color.BLACK);
                value1.setFont(this.button4.getFont().deriveFont(1));
                this.calendar.set(5, this.day1);
            } else if("hour".equalsIgnoreCase(command)) {
                value = Integer.parseInt(this.comboBox3.getSelectedItem().toString().trim());
                this.calendar.set(11, value);
            } else if("minute".equalsIgnoreCase(command)) {
                value = Integer.parseInt(this.comboBox4.getSelectedItem().toString().trim());
                this.calendar.set(12, value);
            } else if("second".equalsIgnoreCase(command)) {
                value = Integer.parseInt(this.comboBox5.getSelectedItem().toString().trim());
                this.calendar.set(13, value);
            } else if("year".equalsIgnoreCase(command)) {
                value = Integer.parseInt(this.comboBox1.getSelectedItem().toString().trim());
                this.calendar.set(1, value);
                this.updatePanel();
            } else if("month".equalsIgnoreCase(command)) {
                value = Integer.parseInt(this.comboBox2.getSelectedItem().toString().trim());
                this.calendar.set(2, value - 1);
                this.updatePanel();
            }
        }

    }

    private void updatePanel() {
        this.remove(this.panel);
        this.add(this.printCalendar(), "Center");
        this.validate();
        this.repaint();
    }

    public int getShowYears() {
        return this.showYears;
    }
}

