package com.kld.app.view.alarm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.view.acceptance.MyTable;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import org.jdesktop.swingx.JXDatePicker;

import com.kld.app.service.AlarmMeasureLeakService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.gsm.ATG.domain.AlarmMeasureLeak;

/**
 *@author 徐超 E-mail:oscarxcc@163.com
 *@version 创建时间:2015-11-5下午09:55:34
 *类说明:侧漏预警/液位异常
 */
public class OilExcep {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	private JScrollPane scrollPane = new JScrollPane();
	List<AlarmMeasureLeak> list = new ArrayList();
	public JTable probePartable;
	JComboBox ygbhcomboBox;
	public JTable table;
	JButton cxbutton;
	final JXDatePicker endDate=new JXDatePicker();
	final JXDatePicker beginDate=new JXDatePicker();
	
	final String[] tableHeads = {
			"油罐编号",
			"开始时间",
			"结束时间",
			"是否生成报告"
    };
    // table的数据
    Object[][] data = new Object[0][tableHeads.length];
    
	public void setPanel(JPanel centerPanel){
		
		centerPanel.setLayout(null);
		scrollPane.setBounds(0, 50, 800, 340);
		centerPanel.add(scrollPane);
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		
		panel1.setBounds(0, 0, 800, 300);
		centerPanel.add(panel1);

		JLabel startLab = new JLabel("日期：");
		startLab.setFont(Constant.BOTTOM_FONT); 
		startLab.setBounds(5, 0, 40, 30);
		startLab.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel1.add(startLab);


		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
		//日期选择器

		beginDate.getEditor().setEditable(false);
		beginDate.setBounds(46, 0, 150, 30);
		beginDate.setFormats(dateFormat1);
		beginDate.setDate(new Date());
		panel1.add(beginDate);

		JLabel a = new JLabel("至");
		a.setBounds(210, 0, 20, 30);
		panel1.add(a);


		endDate.getEditor().setEditable(false);
		endDate.setPreferredSize(new Dimension(200, 30));
		endDate.setBounds(236, 0, 150, 30);
		endDate.setFormats(dateFormat1);
		endDate.setDate(new Date());
		panel1.add(endDate);
		
		
		JLabel hold1Label1 = new JLabel("油罐编号：");
		hold1Label1.setFont(Constant.BOTTOM_FONT); 
		hold1Label1.setBounds(400, 0, 80, 30);
		hold1Label1.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel1.add(hold1Label1);

		ygbhcomboBox = new JComboBox();
		SysManageCanInfoService tankInfoService=(SysManageCanInfoService) (Context.getInstance().getBean("SysManageTankInfoService"));
		List<SysManageCanInfo> TankInfoLst=tankInfoService.selectAll();
		ygbhcomboBox.addItem("全部");
		for (SysManageCanInfo item:TankInfoLst){
			ygbhcomboBox.addItem(item.getOilcan());
		}
		ygbhcomboBox.setBounds(460, 0, 170, 30);
		panel1.add(ygbhcomboBox);
		panel1.updateUI();
		
		cxbutton = new JButton("查询");
		cxbutton.setBounds(640, 0, 60, 30);
		panel1.add(cxbutton);
		selectAlarmMeasureLeak(beginDate.getDate(), endDate.getDate(), null);

		cxbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				//时间校验
				getData(beginDate, endDate);

			}
		});

		getTable(tableHeads, data);
		/*probePartable = getTable(tableHeads,data);
        scrollPane.setViewportView(probePartable);*/
        
	}

	private void getData(JXDatePicker beginDate, JXDatePicker endDate) {
		if(null==beginDate.getDate() || "".equals(beginDate.getDate())){
            JOptionPane.showMessageDialog(null, "请输入开始时间！", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }else if (null==endDate.getDate() || "".equals(endDate.getDate())){
            JOptionPane.showMessageDialog(null, "请输入结束时间！", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
		if (endDate.getDate().compareTo(beginDate.getDate())<0){
            JOptionPane.showMessageDialog(null, "开始时间不能晚于结束时间,请重新选择", "", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

		if("全部".equals(ygbhcomboBox.getSelectedItem().toString())){
            selectAlarmMeasureLeak(beginDate.getDate(),endDate.getDate(),null);
        }else{
            selectAlarmMeasureLeak(beginDate.getDate(),endDate.getDate(),ygbhcomboBox.getSelectedItem().toString());
        }
	}

	public void reload(){
		//cxbutton.doClick();
		getData(beginDate, endDate);
		getTable(tableHeads, data);
	}

	private void selectAlarmMeasureLeak(Date beginDate, Date endDate, String oilcan) {
		////System.out.println("dayinchu"+oilcan);
		AlarmMeasureLeakService alarmDailyLostService =
				(AlarmMeasureLeakService) (Context.getInstance().getBean("alarmMeasureLeakService"));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		calendar.add(calendar.DATE, 1);//
		list = alarmDailyLostService.selectByDate(beginDate, calendar.getTime(),oilcan);
		data = new Object[list.size()][tableHeads.length];
//				data[0] = tableHeads;
		for (int i = 0; i < list.size(); i++) {
			AlarmMeasureLeak info = list.get(i);
			data[i][0] = info.getOilcanno();
			data[i][1] = dateFormat.format(info.getStartdate());
			if(null==info.getEnddate() || "".equals(info.getEnddate())){
				data[i][2] = null;
			}else{
				data[i][2] = dateFormat.format(info.getEnddate());
			}
			if (null==info.getEndoill()){
				data[i][3] = "否";
			}else{
				data[i][3] = "是";
			}
		}
		getTable(tableHeads,data);
		//probePartable = getTable(tableHeads,data);
//				probePartable.updateUI();
//				scrollPane.removeAll();
//				scrollPane.repaint();
//				scrollPane.add(probePartable);
	/*	scrollPane.setViewportView(probePartable);
		scrollPane.updateUI();*/
	}

	private void getTable(String[] titles,Object[][] data){
	        
	       /* AlarmTableModel model = new AlarmTableModel(tableHeads, data);
	        JTable probePartable = new JTable(model);
	        probePartable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        probePartable.getTableHeader().setReorderingAllowed(false);//不拖动
	        
	        Enumeration<TableColumn> columns = probePartable.getColumnModel().getColumns();
	        TableColumn firstColumn = columns.nextElement();
	        firstColumn.setWidth(60);
	        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	        render.setHorizontalAlignment(SwingConstants.CENTER);
	        probePartable.setDefaultRenderer(Object.class, render);
	        probePartable.setRowHeight(20);
	        probePartable.setBorder(new LineBorder(new Color(0, 0, 0)));
	        while (columns.hasMoreElements()){
	        	TableColumn column = columns.nextElement();
	        	column.setPreferredWidth(260);
	        }
	       *//* probePartable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        probePartable.setRowSelectionAllowed(true);  
	        probePartable.setColumnSelectionAllowed(true);  *//*
	        
	        return probePartable;*/

		DefaultTableModel model = new DefaultTableModel(data,tableHeads);
		table=new MyTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		//table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(20);
		table.setDefaultRenderer(Object.class, render);
		table.setCellSelectionEnabled(false);

		//table.setDefaultRenderer(Object.class, r);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.getColumnModel().getColumns();
		table.setModel(model);
		for (int i = 0; i < tableHeads.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(197);
		}
		table.updateUI();
		scrollPane.setViewportView(table);
	 }

}
