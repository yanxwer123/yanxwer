package com.kld.app.view.alarm;

import com.kld.app.service.AlarmDailyLostService;
import com.kld.app.service.AlarmSaleOutService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.app.view.acceptance.MyTable;
import com.kld.gsm.ATG.domain.AlarmSaleOut;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 *@author xuchaoj E-mail:oscarxcc@163.com
 *@version 创建时间:2015-11-5下午09:51:07
 *类说明:脱销预警
 */
public class StockoutAlarm {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	private JScrollPane scrollPane = new JScrollPane();
	List<AlarmSaleOut> list = new ArrayList();
	JTable probePartable;
	private JTable table;
	
	final String[] tableHeads = {
			"油品",
			"检测时间",
			"当前体积(L)",
			"可销售体积(L)",
			"时均销量(L)",
			"预计可销售时间(h)",
			"开始报警时间",
			"结束报警时间"
			
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
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd-HHmmss");
		//日期选择器
		final JXDatePicker beginDate=new JXDatePicker();
		beginDate.getEditor().setEditable(false);
		beginDate.setBounds(46, 0, 150, 30);
		beginDate.setFormats(dateFormat1);
		beginDate.setDate(new Date());
		panel1.add(beginDate);

		JLabel a = new JLabel("至");
		a.setBounds(210, 0, 20, 30);
		panel1.add(a);

		final JXDatePicker endDate=new JXDatePicker();
		endDate.getEditor().setEditable(false);
		endDate.setPreferredSize(new Dimension(200, 30));
		endDate.setBounds(236, 0, 150, 30);
		endDate.setFormats(dateFormat1);
		endDate.setDate(new Date());
		panel1.add(endDate);
		panel1.updateUI();



		JButton cxbutton = new JButton("查询");
		cxbutton.setBounds(400, 0, 60, 30);
		panel1.add(cxbutton);
		selectAlarmSaleOut(beginDate, endDate);

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
				selectAlarmSaleOut(beginDate, endDate);
				getTable(tableHeads, data);
				/*probePartable = getTable(tableHeads,data);
//				probePartable.updateUI();
//				scrollPane.removeAll();
//				scrollPane.repaint();
//				scrollPane.add(probePartable);
				scrollPane.setViewportView(probePartable);
		        scrollPane.updateUI();*/
			}
		});
		
		//probePartable = getTable(tableHeads,data);
		getTable(tableHeads, data);
		//scrollPane.setViewportView(probePartable);
	}

	private void selectAlarmSaleOut(JXDatePicker beginDate, JXDatePicker endDate) {
		AlarmSaleOutService alarmEquipmentService = (AlarmSaleOutService) (Context.getInstance().getBean("alarmSaleOutService"));
		AlarmDailyLostService alarmDailyLostService =
				(AlarmDailyLostService) (Context.getInstance().getBean("alarmDailyLostService"));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate.getDate());
		calendar.add(calendar.DATE, 1);//

		list = alarmEquipmentService.selectByDate(beginDate.getDate(), calendar.getTime());
		data = new Object[list.size()][tableHeads.length];
//				data[0] = tableHeads;
		for (int i = 0; i < list.size(); i++) {
			AlarmSaleOut info = list.get(i);
			//data[i][0] = info.getOilno();
			data[i][0] = alarmDailyLostService.selectOilNo(info.getOilno());//油品类型
			data[i][1] = dateFormat.format(info.getMesasuretime());
			data[i][2] = info.getNowvolume();
			data[i][3] = info.getCansalevolume();
			//data[i][4] = info.getDayaveragesales();
			data[i][4] = info.getHouraveragesales();
			data[i][5] = info.getPredictHours();
			data[i][6] = dateFormat.format(info.getStartalarmtime());
			if(info.getEndalarmtime()!=null) {
				data[i][7] = dateFormat.format(info.getEndalarmtime());
			}else
			{
				data[i][7]="";
			}
		}

	}

	private void getTable(String[] titles,Object[][] data){
		
        /*AlarmTableModel model = new AlarmTableModel(tableHeads, data);
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
			if(width<95){
				width=95;
			}
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table.getCellRenderer(row, col).getTableCellRendererComponent(table,
						table.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				if(Math.max(width, preferedWidth)>=95){
					width = Math.max(width, preferedWidth);
				}
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + table.getIntercellSpacing().width);
		}
	}
}
