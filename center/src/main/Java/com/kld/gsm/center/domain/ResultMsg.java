package com.kld.gsm.center.domain;

import java.io.Serializable;
import java.util.List;

public class ResultMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean result;//处理状态
	private Object data;//其他数据
	private String msg;//消息
	private List<?> rows;//bootstarp分页设置每一行数据
	private Long total;//分页设置总数
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(long total2) {
		this.total = total2;
	}
}
