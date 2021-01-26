package com.changhong.sei.report.servlet.designer;

import java.util.List;
import java.util.Map;

/**
 * @desc：数据及数据
 * @author：zhaohz
 * @date：2020/7/7 9:35
 */
public class DataResult {
	private List<Map<String,Object>> data;
	private List<String> fields;
	private int total;
	private int currentTotal;
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentTotal() {
		return currentTotal;
	}
	public void setCurrentTotal(int currentTotal) {
		this.currentTotal = currentTotal;
	}
}
