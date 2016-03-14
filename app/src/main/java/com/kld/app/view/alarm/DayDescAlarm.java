package com.kld.app.view.alarm;

import com.kld.app.service.AlarmDailyLostService;
import com.kld.app.springcontext.Context;
import com.kld.app.util.Constant;
import com.kld.app.view.acceptance.MyTable;
import com.kld.gsm.ATG.domain.AlarmDailyLost;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 *@author xuchaoj E-mail:oscarxcc@163.com
 *@version 创建时间:2015-11-5下午09:54:58
 *类说明:日结损溢预警
 */
public class DayDescAlarm {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	private JScrollPane scrollPane = new JScrollPane();
	List<AlarmDailyLost> list = new ArrayList();
	JTable probePartable;
	private JTable table;

	final String[] tableHeads= {
			"品种",
			"日期",
			"期初库存(L)",
			"出货单号",
			"进货数量(L)",
			"期间付出(L)",
			"期末库存(L)",
			"实测库存(L)",
			"损耗量(L)",
			"损耗率(%)"
	};
	//table数据
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

		GregorianCalendar gcNew = new GregorianCalendar();
		gcNew.set(Calendar.MONTH, gcNew.get(Calendar.MONTH) - 1);
		Date dtFrom=gcNew.getTime();

		//日期选择器
		final JXDatePicker beginDate=new JXDatePicker();
//		beginDate.setPreferredSize(new Dimension(200, 30));
		beginDate.getEditor().setEditable(false);
		beginDate.setBounds(46, 0, 150, 30);
		beginDate.setFormats(dateFormat1);
		beginDate.setDate(new Date());
		panel1.add(beginDate);

		JLabel a = new JLabel("至");
		a.setBounds(210, 0, 20, 30);
		panel1.add(a);

		final JXDatePicker endDate=new JXDatePicker();
		endDate.setPreferredSize(new Dimension(200, 30));
		endDate.getEditor().setEditable(false);
		endDate.setBounds(236, 0, 150, 30);
		endDate.setDate(new Date());
		endDate.setFormats(dateFormat1);
		panel1.add(endDate);

		panel1.updateUI();

		JButton cxbutton = new JButton("查询");
		cxbutton.setBounds(400, 0, 60, 30);
		panel1.add(cxbutton);
		BindData(beginDate.getDate(), endDate.getDate());
		panel1.updateUI();
		////System.out.println("打印初始时间为"+beginDate.getDate()+"jieshu:"+endDate.getDate());



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

				BindData(beginDate.getDate(), endDate.getDate());

			}

		});

		/*probePartable = getTable(tableHeads,data);
        scrollPane.setViewportView(probePartable);*/
		getTable(tableHeads,data);
	}

	private void BindData(Date beginDate,Date endDate)
	{
		AlarmDailyLostService alarmDailyLostService =
				(AlarmDailyLostService) (Context.getInstance().getBean("alarmDailyLostService"));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		calendar.add(calendar.DATE, 1);//
		List<AlarmDailyLost> dailyLostList = alarmDailyLostService.selectByDate(beginDate, calendar.getTime());
		data = new Object[dailyLostList.size()][tableHeads.length];
//				data[0] = tableHeads;
		for (int i = 0; i < dailyLostList.size(); i++) {
			AlarmDailyLost info = dailyLostList.get(i);
			////System.out.println(info.getAccountdate());
			DecimalFormat df=new DecimalFormat("0");
			data[i][0] =alarmDailyLostService.selectOilNo(info.getOilno());//品种
			data[i][1]=dateFormat.format(info.getAccountdate());
			data[i][2] = df.format(info.getDarlyankstock());
			data[i][3] = info.getDeliveryno();
			data[i][4] = df.format(info.getReceivel());
			data[i][5] = df.format(info.getTodayout());
			data[i][6] = df.format(info.getTodayendstock());//期末库存
			data[i][7] = df.format(info.getRealstock());
			DecimalFormat df1=new DecimalFormat("###########0.00");
			data[i][8] = df.format(info.getCost());
			data[i][9] = df1.format(info.getCostsent())+"%";
		}
		/*probePartable = getTable(tableHeads,data);
		scrollPane.setViewportView(probePartable);
		scrollPane.updateUI();*/
		getTable(tableHeads,data);
	}
	private void getTable(String[] titles,Object[][] data){
		/*MyTableModel model = new MyTableModel(tableHeads, data);
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
		//probePartable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		probePartable.setRowSelectionAllowed(true);
		probePartable.setColumnSelectionAllowed(false);

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
