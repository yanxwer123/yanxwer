package com.kld.app.view.alarm;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class AlarmTable extends JTable {

	public AlarmTable(Object[][] values, String[] columns) {
		super(values, columns);
	}

	public AlarmTable(TableModel model){
		super(model);
	}
	@Override
	public JTableHeader getTableHeader() {

		JTableHeader tableHeader = super.getTableHeader();

		tableHeader.setReorderingAllowed(false);// 表格列不可移动

		DefaultTableCellRenderer hr = (DefaultTableCellRenderer) tableHeader
				.getDefaultRenderer();

		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);// 列名居中
		return tableHeader;
	}

	// 单元格可编辑
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
