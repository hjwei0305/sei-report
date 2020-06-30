package com.changhong.sei.report.builds;

import java.util.List;

/**
 * @desc：绑定数据
 * @author：zhaohz
 * @date：2020/6/30 9:52
 */
public class BindData {
	private Object value;
	private Object label;
	private List<Object> dataList;
	public BindData(Object value) {
		this.value=value;
	}
	public BindData(Object value, List<Object> dataList) {
		this.value=value;
		this.dataList=dataList;
	}
	public BindData(Object value, Object label, List<Object> dataList) {
		this.value=value;
		this.label=label;
		this.dataList=dataList;
	}
	public Object getValue() {
		return value;
	}
	public List<Object> getDataList() {
		return dataList;
	}
	public Object getLabel() {
		return label;
	}
}
