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

import com.kld.app.service.SysManageCanInfoService;
import com.kld.app.view.acceptance.MyTable;
import com.kld.gsm.ATG.dao.SysManageOilTypeDao;
import com.kld.gsm.ATG.domain.SysManageCanInfo;
import com.kld.gsm.ATG.domain.SysManageOilType;
import org.jdesktop.swingx.JXDatePicker;

import com.kld.app.service.AlarmShiftLostService;
import com.kld.app.service.SysManagePaTRelationService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.gsm.ATG.domain.AlarmShiftLost;
import com.kld.gsm.ATG.domain.SysManagePaTRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *@author xuchaoj E-mail:oscarxcc@163.com
 *@version 创建时间:2015-11-5下午09:54:40
 *类说明:交接班损溢预警
 */
public class HandDescAlarm {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private JScrollPane scrollPane = new JScrollPane();
	List<AlarmShiftLost> list = new ArrayList();
	JTable probePartable;
	JComboBox ygbhcomboBox;
	private JTable table;
	
	final String[] tableHeads = {
			"班次",
			"油罐编号",
			"油品",
			"接班油高(mm)",
			"接班油量(L)",
			"交班油高(mm)",
			"交班油量(L)",
			"交班水高(mm)",
			"交班水量(L)",

			"卸油量(L)",
			"付油量(L)",
			"交班帐存(L)",
			"损耗率(%)"
    };
    // table的数据
    Object[][] data = new Object[0][tableHeads.length+1];
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
		ygbhcomboBox.setEditable(false);
		ygbhcomboBox.setBounds(460, 0, 170, 30);
		panel1.add(ygbhcomboBox);
		panel1.updateUI();
		
		JButton cxbutton = new JButton("查询");
		cxbutton.setBounds(640, 0, 60, 30);
		panel1.add(cxbutton);
		selectAlarmShiftLost(beginDate, endDate, null);

		cxbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//时间校验
				if (null == beginDate.getDate() || "".equals(beginDate.getDate())) {
					JOptionPane.showMessageDialog(null, "请输入开始时间！", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (null == endDate.getDate() || "".equals(endDate.getDate())) {
					JOptionPane.showMessageDialog(null, "请输入结束时间！", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (endDate.getDate().compareTo(beginDate.getDate()) < 0) {
					JOptionPane.showMessageDialog(null, "开始时间不能晚于结束时间,请重新选择", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if ("全部".equals(ygbhcomboBox.getSelectedItem().toString())) {
					selectAlarmShiftLost(beginDate, endDate, null);

				} else {
					selectAlarmShiftLost(beginDate, endDate, ygbhcomboBox.getSelectedItem().toString());
				}

			}
		});
		getTable(tableHeads, data);
	/*	probePartable = getTable(tableHeads,data);
        scrollPane.setViewportView(probePartable);*/
        
	}

	private void selectAlarmShiftLost(JXDatePicker beginDate, JXDatePicker endDate,String oilcanno) {
		AlarmShiftLostService alarmShiftLostService = (AlarmShiftLostService) (Context.getInstance().getBean("alarmShiftLostService"));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate.getDate());
		calendar.add(calendar.DATE, 1);//

		list = alarmShiftLostService.selectByDate(beginDate.getDate(),calendar.getTime(),oilcanno);

		data = new Object[list.size()][tableHeads.length+1];
//				data[0] = tableHeads;
		for (int i = 0; i < list.size(); i++) {
			AlarmShiftLost info = list.get(i);
			/*"班次",
					"油罐编号",
					"油品",
					"接班油高(mm)",
					"接班油量(L)",
					"交班油高(mm)",
					"交班油量(L)",
					"交班水高(mm)",
					"交班水量(L)",
					"交班温度(℃)",
					"卸油量(L)",
					"付油量(L)",
					"交班帐存(L)",
					"损耗率(%)"*/
			data[i][0] = info.getShift();
			data[i][1] = info.getOilcanno();
			SysManageOilType sysManageOil=alarmShiftLostService.selectByPrimaryKey(info.getOilno());
			if(null==sysManageOil){
				info.setOilno("");
			}else {
				info.setOilno(sysManageOil.getOilname()+"");
			}
			data[i][2] = info.getOilno();
			data[i][3] = info.getStartoilheight();
			data[i][4] = info.getStartoill();
			data[i][5] = info.getEndoilheight();
			data[i][6] = info.getEndoill();
			data[i][7] = info.getEndwaterheight();
			data[i][8] = info.getEndwaterl();
			data[i][9] = info.getOildischarge();
			data[i][10] = info.getSale();
			DecimalFormat df=new DecimalFormat("###########0.00");
			data[i][11] = df.format(info.getInventory());
			data[i][12] = df.format(info.getLoss())+"%";
		}
		//probePartable = getTable(tableHeads,data);
		getTable(tableHeads,data);
//				probePartable.updateUI();
//				scrollPane.removeAll();
//				scrollPane.repaint();
//				scrollPane.add(probePartable);
		/*scrollPane.setViewportView(probePartable);
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
        	column.setPreferredWidth(150);
        }
        probePartable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  
        probePartable.setRowSelectionAllowed(true);  
        probePartable.setColumnSelectionAllowed(true); 
        
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
		FitTableColumns(table);

		//table.setDefaultRenderer(Object.class, r);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.getColumnModel().getColumns();
		table.setModel(model);
		for (int i = 0; i < tableHeads.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(197);
		}
		table.updateUI();
		FitTableColumns(table);

		scrollPane.setViewportView(table);
		//scrollPane.updateUI();
	}
	private void FitTableColumns(JTable table) {
		JTableHeader header = table.getTableHeader();
		int rowCount = table.getRowCount();

		Enumeration columns = table.getColumnModel().getColumns();
		while(columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) table.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(table, column.getIdentifier()
							, false, false, -1, col).getPreferredSize().getWidth();
			if(width<80){
				width=80;
			}
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table,
						table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				if(Math.max(width, preferedWidth)>=80){
					width = Math.max(width, preferedWidth);
				}
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + table.getIntercellSpacing().width);
		}
	}
}
