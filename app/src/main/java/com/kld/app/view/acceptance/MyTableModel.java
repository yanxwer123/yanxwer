package com.kld.app.view.acceptance;

import javax.swing.table.AbstractTableModel;

/**
 * tablemodel
 */
public class MyTableModel extends AbstractTableModel {
	private String[] titles;
	private Object[][] data;

	public MyTableModel(String[] titles, Object[][] data) {
		this.titles = titles;
		this.data = data;
	}

	// 获取总的行数
	public int getColumnCount() {
		return titles.length;
	}

	// 获取总的列数
	public int getRowCount() {
		return data.length;
	}

	// 获取某行某列的单元格的值
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	// 设置列名
	@Override
	public String getColumnName(int column) {
		return titles[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// 这里可以处理具体的某个单元格是否可编辑
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data[rowIndex][columnIndex] = aValue;
	}
}
