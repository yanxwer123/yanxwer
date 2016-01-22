package com.kld.app.view.sysmanage;

import com.kld.app.service.AlmService;
import com.kld.app.springcontext.Context;
import com.kld.app.view.alarm.AlarmTable;
import com.kld.gsm.ATG.domain.SysManageAlarmParameter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;

public class AlmPanel extends JPanel{
	JPanel almPanel;
	JTable almtable;
	public AlmPanel(){
		almPanel=new JPanel();
		final String[] tableHeads = {
				"油罐编号",
				"低液位预警(mm)",
				"低液位报警(mm)",
				"高液位预警(mm)",
				"高液位报警(mm)",
				"高水位预警(mm)",
				"高水位报警(mm)",
				"盗油报警(L/H)",
				"漏油报警(L/H)",
				"渗漏报警(L/H)",
				"高温报警(℃)",
				"低温报警(℃)",
				"设置时间"
		};

		AlmService almService =(AlmService) (Context.getInstance ().getBean("almService"));
		List<SysManageAlarmParameter> list=almService.selectAll();
		final Object[][] data =this.getList(list);
		//System.out.println("data----:"+data.length);
		almtable = new AlarmTable(data,tableHeads) ;
 		for (int i = 0; i < tableHeads.length; i++) {
			almtable.getColumnModel().getColumn(i).setPreferredWidth(134);
		}
		almtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		almtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scr =new JScrollPane(almtable);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		//table.setBorder(new LineBorder(new Color(0, 0, 0)));
		almtable.setRowHeight(20);
		almtable.setDefaultRenderer(Object.class, render);
		FitTableColumns(almtable);
		scr.setSize(800, 390);
		almPanel.setLayout(null);
		almPanel.add(scr);
		almPanel.setSize(800,470) ;
		almPanel.setVisible(true);
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

	public int getId(){
		int row=almtable.getSelectedRow();
		if(row!=-1) {
			int id = Integer.parseInt(String.valueOf(almtable.getValueAt(row, 0)));
			return id;
		}
		return -1;

	}

	public Object[][] getList(List<SysManageAlarmParameter> listInfo) {
		Object obj[][]=new Object[listInfo.size()][13];
		for (int i = 0; i < listInfo.size(); i++) {
			//System.out.println("预报警设置："+listInfo.get(i));
			obj[i][0]=listInfo.get(i).getOilcan();
			obj[i][1]=listInfo.get(i).getLowprealarm();
			obj[i][2]=listInfo.get(i).getLowalarm();
			obj[i][3]=listInfo.get(i).getHighprealarm();
			obj[i][4]=listInfo.get(i).getHighalarm();
			obj[i][5]=listInfo.get(i).getWateralarm();
			obj[i][6]=listInfo.get(i).getWaterhightalarm();

			obj[i][7]=listInfo.get(i).getStealoilalarm();        //盗油报警
			obj[i][8]=listInfo.get(i).getLeakoilalarm(); 		//漏油报警
			obj[i][9]=listInfo.get(i).getLeakageoilalarm();     //渗漏报警

			obj[i][10]=listInfo.get(i).getHightempalarm();
			obj[i][11]=listInfo.get(i).getLowtempalarm();
			SimpleDateFormat Datefo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(null!=listInfo.get(i).getLastoptime()&&!"".equals(listInfo.get(i).getLastoptime())){
				obj[i][12]=Datefo.format(listInfo.get(i).getLastoptime());
			}else {
				obj[i][12]="";
			}

		}
		return obj;
	}


	public void setPanel(JPanel centerPanel) {
		centerPanel.setBounds(0, 180, 800, 390);
		centerPanel.setLayout(new BorderLayout(0,0));
		centerPanel.add(this.almPanel);
		centerPanel.setVisible(true);
	}
}
