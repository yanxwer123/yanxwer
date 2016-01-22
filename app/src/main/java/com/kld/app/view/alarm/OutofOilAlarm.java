package com.kld.app.view.alarm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.kld.app.view.acceptance.MyTable;
import org.jdesktop.swingx.JXDatePicker;

import com.kld.app.service.AlarmOilInContrastService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.gsm.ATG.domain.AlarmOilInContrast;

/**
 *@author 徐超 E-mail:oscarxcc@163.com
 *@version 创建时间:2015-11-5下午09:53:07
 *类说明:进油损耗预警
 */
public class OutofOilAlarm {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private JScrollPane scrollPane = new JScrollPane();
	List<AlarmOilInContrast> list = new ArrayList();
	JTable probePartable;
	private JTable table;
	
	final String[] tableHeads = {
			"出库单号",
			"原发升数(L)",
			"实收升数(L)",
			"损耗量(L)",
			"损耗率(%)"
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
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd-HHmmss");
		//日期选择器
		final JXDatePicker beginDate=new JXDatePicker();
		beginDate.getEditor().setEditable(false);
		beginDate.setBounds(46, 0, 150, 30);
		beginDate.setFormats(dateFormat);
		beginDate.setDate(new Date());
		panel1.add(beginDate);

		JLabel a = new JLabel("至");
		a.setBounds(210, 0, 20, 30);
		panel1.add(a);
		
		final JXDatePicker endDate=new JXDatePicker();
		endDate.getEditor().setEditable(false);
		endDate.setPreferredSize(new Dimension(200, 30));
		endDate.setBounds(236, 0, 150, 30);
		endDate.setFormats(dateFormat);
		endDate.setDate(new Date());
		panel1.add(endDate);
		panel1.updateUI();
		
		JButton cxbutton = new JButton("查询");
		cxbutton.setBounds(400, 0, 60, 30);
		panel1.add(cxbutton);
		selectAlarmOilInContrast(beginDate.getDate(), endDate.getDate());

		cxbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				//时间校验
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
				selectAlarmOilInContrast(beginDate.getDate(), endDate.getDate());
			}
		});
		
		//probePartable = getTable(tableHeads,data);
		getTable(tableHeads,data);
        //scrollPane.setViewportView(probePartable);
        
	}

	private void selectAlarmOilInContrast(Date beginDate, Date endDate) {
		AlarmOilInContrastService alarmOilInContrastService =
				(AlarmOilInContrastService) (Context.getInstance().getBean("alarmOilInContrastService"));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		calendar.add(calendar.DATE, 1);//

		list = alarmOilInContrastService.selectByDate(beginDate, calendar.getTime());
		data = new Object[list.size()][tableHeads.length];
//				data[0] = tableHeads;
		for (int i = 0; i < list.size(); i++) {
			AlarmOilInContrast info = list.get(i);
			data[i][0] = info.getDeliveryno();
			data[i][1] = info.getPlanl();
			data[i][2] = info.getRealrecieve();
			DecimalFormat df=new DecimalFormat("###########0.00");
			data[i][3] = df.format(info.getLoss());
			data[i][4] = df.format(info.getLossrate()) +"%";
		}
		//probePartable =
		getTable(tableHeads,data);
//				probePartable.updateUI();
//				scrollPane.removeAll();
//				scrollPane.repaint();
//				scrollPane.add(probePartable);
		/*scrollPane.setViewportView(probePartable);
		scrollPane.updateUI();*/
	}

	private void getTable(String[] titles,Object[][] data){
		DefaultTableModel model = new DefaultTableModel(data,tableHeads);
		/*AlarmTableModel model = new AlarmTableModel(tableHeads, data);
		JTable probePartable = new JTable(model);
		probePartable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		probePartable.getTableHeader().setReorderingAllowed(false);//不拖动
		
		Enumeration<TableColumn> columns = probePartable.getColumnModel().getColumns();
		TableColumn firstColumn = columns.nextElement();
		firstColumn.setWidth(60);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(JLabel.CENTER);
		probePartable.setDefaultRenderer(Object.class, render);
		probePartable.setRowHeight(20);
		probePartable.setBorder(new LineBorder(new Color(0, 0, 0)));
		while (columns.hasMoreElements()){
			TableColumn column = columns.nextElement();
			column.setPreferredWidth(200);
		}
		probePartable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  
		probePartable.setRowSelectionAllowed(true);  
		probePartable.setColumnSelectionAllowed(true);  
		
		return probePartable;*/
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
