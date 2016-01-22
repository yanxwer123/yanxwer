package com.kld.app.view.daily;

import com.kld.app.service.DailyStationShiftInfoService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.acceptance.MyTable;
import com.kld.app.view.acceptance.MyTableModel;
import com.kld.gsm.ATG.domain.DailyStationShiftInfo;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
/**
 * 油站班报
 * @author YANGRL
 *
 */
public class YzbbPage {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static final String[] CKD_TITLES = new String[]{"班次","接班人","交班人","交班时间"};
	private Object[][] billArray = new Object[0][CKD_TITLES.length];
	private JScrollPane scrollPane;
	public JTable table;
	JScrollPane scr;
     public List<DailyStationShiftInfo> list;
	//中部需要滚动条
	public void setPanel(JPanel centerPanel){
		centerPanel.setLayout(null);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 40, 800, 350);
		centerPanel.setBackground(Color.WHITE);
		centerPanel.add(scrollPane);
		JLabel rqlab = new JLabel("日期：");
//		rqlab.setForeground(Color.WHITE);
		centerPanel.add(rqlab);
		rqlab.setBounds(10, 5, 40, 25);

		//日期选择器
		final JXDatePicker beginDate=new JXDatePicker();
		beginDate.getEditor().setEditable(false);
		beginDate.setDate(new Date());
		beginDate.setFormats(dateFormat);
		beginDate.setPreferredSize(new Dimension(200, 100));
		centerPanel.add(beginDate);
		beginDate.setBounds(50, 5, 150, 25);

		JLabel zhilab = new JLabel("至");
//		zhilab.setForeground(Color.WHITE);
		centerPanel.add(zhilab);
		zhilab.setBounds(210, 5, 20, 25);

		final JXDatePicker endDate=new JXDatePicker();
		endDate.getEditor().setEditable(false);
		endDate.setDate(new Date());
		endDate.setFormats(dateFormat);
		endDate.setPreferredSize(new Dimension(200, 100));
		centerPanel.add(endDate);
		endDate.setBounds(230, 5, 150, 25);

		centerPanel.updateUI();
		JButton cxbutton = new JButton("查询");
		centerPanel.add(cxbutton);
		cxbutton.setBounds(405, 5, 80, 25);

		SelectDate(beginDate.getDate(),endDate.getDate());
		cxbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DailyStationShiftInfoService dailyStationShiftInfoService =
						(DailyStationShiftInfoService) (Context.getInstance().getBean("dailyStationShiftInfoService"));

				//时间校验dddd
				if (null == beginDate.getDate() || "".equals(beginDate.getDate())) {
					JOptionPane.showMessageDialog(null, "请输入开始时间", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (null == endDate.getDate() || "".equals(endDate.getDate())) {
					JOptionPane.showMessageDialog(null, "请输入结束时间", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (endDate.getDate().compareTo(beginDate.getDate()) < 0) {
					JOptionPane.showMessageDialog(null, "开始时间不能晚于结束时间,请重新选择", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//System.out.println(beginDate.getDate());
				//System.out.println(endDate.getDate());
				SelectDate(beginDate.getDate(), endDate.getDate());
				/*list = dailyStationShiftInfoService.selectByDate(beginDate.getDate(), calendar.getTime());
				billArray = new Object[list.size()][CKD_TITLES.length];
				for (int i = 0; i < list.size(); i++) {
					DailyStationShiftInfo info = list.get(i);
					billArray[i][0] = info.getShift();
					billArray[i][1] = info.getSuccessor();
					SimpleDateFormat Datefo = new SimpleDateFormat("yyyy-MM-dd");
					//billArray[i][2] = Datefo.format(info.getSucceedtime()  );
					billArray[i][2] = info.getShiftoperator();
					billArray[i][3] = Datefo.format(info.getShifttime());
					//System.out.println("i:" + i + "!info.getShifttime():" + info.getShifttime());
				}*/
				/*table = getTable(CKD_TITLES, billArray);*/

//				probePartable.updateUI()  ;
//				scrollPane.removeAll() ;
//				scrollPane.repaint();
//				scrollPane.add(probePartable);
			/*	scrollPane.setViewportView(table);
				scrollPane.updateUI();*/
				/*for (int i = 0; i < CKD_TITLES.length; i++) {
					table.getColumnModel().getColumn(i).setPreferredWidth(114);
				}
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				scr = new JScrollPane(table);
				scr.setSize(800, 350);
				scrollPane.setLayout(null);
				scrollPane.add(scr);
				scrollPane.setSize(800, 350);
				scrollPane.setVisible(true);*/
			}
		});
		/*table = getTable(CKD_TITLES,billArray);
		scrollPane.setViewportView(table);*/
		/*table = new JTable(new Object[][]{},CKD_TITLES) ;
		for (int i = 0; i < CKD_TITLES.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(114);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scr =new JScrollPane(table);
		scr.setSize( 800, 350);
		scrollPane.setLayout(null);
		scrollPane.add(scr);
		scrollPane.setSize( 800, 350);
		scrollPane.setVisible(true);*/
	}

	private void SelectDate(Date beginDate,Date endDate)
	{
		DailyStationShiftInfoService dailyStationShiftInfoService =
				(DailyStationShiftInfoService) (Context.getInstance().getBean("dailyStationShiftInfoService"));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(endDate);
		calendar.add(calendar.DATE, 1);//
		list = dailyStationShiftInfoService.selectByDate(beginDate,calendar.getTime());
		billArray = new Object[list.size()][CKD_TITLES.length];
		for (int i = 0; i < list.size(); i++) {
			DailyStationShiftInfo info = list.get(i);
			billArray[i][0] = info.getShift();
			billArray[i][1] = info.getSuccessor();
			SimpleDateFormat Datefo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//billArray[i][2] = Datefo.format(info.getSucceedtime()  );
			billArray[i][2] = info.getShiftoperator();
			billArray[i][3] = Datefo.format(info.getShifttime());
			//System.out.println("i:" + i + "!info.getShifttime():" + info.getShifttime());
		}
		table = getTable(CKD_TITLES,billArray);
		table.updateUI();
		scrollPane.setViewportView(table);
		//scrollPane.updateUI();
	}


	private JTable getTable(String[] titles,Object[][] data){
		MyTableModel model = new MyTableModel(titles, data);
		JTable table=new MyTable(model);
		//选中一行
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//table添加行监听
//        SelectionListener listener = new SelectionListener(table);
//        table.getSelectionModel().addListSelectionListener(listener);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
		TableColumn firstColumn = columns.nextElement();
		firstColumn.setPreferredWidth(198);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		//table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setRowHeight(20);
		table.setDefaultRenderer(Object.class, render);
		while (columns.hasMoreElements()){
			TableColumn column = columns.nextElement();
			column.setPreferredWidth(198);
		}
//        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//table.setRowSelectionAllowed(true);
//        table.setColumnSelectionAllowed(true);
		//table.setBounds(30, 30, 740, 260);
		return table;
	}

    }

class CkMouseListener extends MouseAdapter {
	YzbbPage yzbbPage;

	public CkMouseListener(YzbbPage yzbbPage) {
		this.yzbbPage = yzbbPage;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("table.getSelectedRow():"+yzbbPage.table.getSelectedRow());
		if(yzbbPage.table.getSelectedRow()>0){
			YzbbMxPage psdxx = new YzbbMxPage(yzbbPage.list.get(yzbbPage.table.getSelectedRow()-1));
			psdxx.getFrame().setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"请选择一行数据！");
		}
	}
}