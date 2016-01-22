package com.kld.app.view.alarm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.kld.app.service.SysManageDictService;
import com.kld.app.view.acceptance.MyTable;
import com.kld.gsm.ATG.domain.SysManageDict;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import com.kld.app.service.AlarmEquipmentService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.gsm.ATG.domain.AlarmEquipment;
/**
 *@author xuchaoj E-mail:oscarxcc@163.com
 *@version 创建时间:2015-11-5下午09:50:15
 *类说明:设备故障
 */
public class EquipFault {
	private static final Logger LOG = Logger.getLogger(EquipFault.class);
	SimpleDateFormat dateSelFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss ");
	private JScrollPane scrollPane = new JScrollPane();
	List<AlarmEquipment> list = new ArrayList();
	private JTable table;

	SysManageDictService sysManageDictService = Context.getInstance().getBean(SysManageDictService.class);
	JTable probePartable;
	JComboBox ygbhcomboBox;
	
	final String[] tableHeads = {
			"油罐编号",
			"开始报警时间",
			"结束报警时间",
			"故障类型",
			"设备代码",
			"设备品牌",
			"探棒型号",
			"备注"
    };
    // table的数据
    Object[][] data = new Object[0][tableHeads.length];
    
	public void setPanel(JPanel centerPanel) throws Exception{
		
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
		final JXDatePicker beginDate=new JXDatePicker();
		beginDate.getEditor().setEditable(false);
		beginDate.setBounds(46, 0, 150, 30);
		beginDate.setDate(new Date());
		beginDate.setFormats(dateSelFormat);
		panel1.add(beginDate);
		
		JLabel a = new JLabel("至");
		a.setBounds(210, 0, 20, 30);
		panel1.add(a);
		
		final JXDatePicker endDate=new JXDatePicker();
		endDate.getEditor().setEditable(false);
		endDate.setPreferredSize(new Dimension(200, 30));
		endDate.setBounds(236, 0, 150, 30);
		endDate.setDate(new Date());
		endDate.setFormats(dateSelFormat);
		panel1.add(endDate);
		
		
		JLabel hold1Label1 = new JLabel("故障类型：");
		hold1Label1.setFont(Constant.BOTTOM_FONT); 
		hold1Label1.setBounds(400, 0, 80, 30);
		hold1Label1.setForeground(new Color(Integer.decode(Constant.HOMEPAGE_COCLER)));
		panel1.add(hold1Label1);

		ygbhcomboBox = new JComboBox();
		final SysManageDictService dictService =(SysManageDictService) (Context.getInstance().getBean(SysManageDictService.class));
		List<String> Namelst=dictService.selectSBByDictID(39); //39表示故障类型
		ygbhcomboBox.addItem("全部");//默认第一行为全部
		for (String item:Namelst){
			ygbhcomboBox.addItem(item);
		}
		ygbhcomboBox.setBounds(460, 0, 170, 30);
		panel1.add(ygbhcomboBox);
		panel1.updateUI();

		JButton cxbutton = new JButton("查询");
		cxbutton.setBounds(640, 0, 60, 30);
		panel1.add(cxbutton);
		listSelect(beginDate.getDate(), endDate.getDate());
		cxbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
				listSelect(beginDate.getDate(), endDate.getDate());
			}
		});
		/*probePartable = getTable(tableHeads,data);
		scrollPane.setViewportView(probePartable);*/
		getTable(tableHeads,data);
	}

	private void listSelect(Date beginDate, Date endDate) {
		AlarmEquipmentService alarmEquipmentService = (AlarmEquipmentService) (Context.getInstance().getBean("alarmEquipmentService"));
		final SysManageDictService dictService =(SysManageDictService) (Context.getInstance().getBean(SysManageDictService.class));
		String Value = dictService.selectByName(ygbhcomboBox.getSelectedItem().toString());
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		calendar.add(calendar.DATE, 1);
		if ("全部".equals(ygbhcomboBox.getSelectedItem().toString())) {
			Value=null;
		}
		list = alarmEquipmentService.selectByDate(beginDate, calendar.getTime(), Value);
		data = new Object[list.size()][tableHeads.length];
		for (int i = 0; i < list.size(); i++) {
			AlarmEquipment info = list.get(i);
			data[i][0] = info.getOilcan();
			data[i][1] = (null == info.getStartalarmtime() || "".equals(info.getStartalarmtime().toString())) ? "" : dateFormat.format(info.getStartalarmtime());
			data[i][2] = (null == info.getEndalarmtime() || "".equals(info.getEndalarmtime().toString())) ? "" : dateFormat.format(info.getEndalarmtime());
			if(null!=info.getFailuretype() && !"".equals(info.getFailuretype())){
				Integer failuretype1 = Integer.parseInt(info.getFailuretype().toString());
				data[i][3] = dictService.selectBySort2(failuretype1);
			}else {
				data[i][3] = null;
			}
			data[i][4] = info.getEquipcode();
			data[i][5] = info.getEquipbrand();
			data[i][6] = info.getProbemodel();
			data[i][7] = info.getRemark();
		}
		/*probePartable = getTable(tableHeads, data);
		scrollPane.setViewportView(probePartable);
		scrollPane.updateUI()*/;
		getTable(tableHeads, data);
	}


	private void getTable(String[] titles,Object[][] data){
		
      /*  AlarmTableModel model = new AlarmTableModel(tableHeads, data);
        probePartable = new JTable(model);
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
		//table.setDefaultRenderer(Object.class, r);
		FitTableColumns(table);

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
			if(width<90){
				width=90;
			}
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table,
						table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				if(Math.max(width, preferedWidth)>=90){
					width = Math.max(width, preferedWidth);
				}
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + table.getIntercellSpacing().width);
		}
	}

}
