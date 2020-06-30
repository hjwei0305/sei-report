package com.changhong.sei.report.definition;

import com.changhong.sei.report.model.Column;

import java.util.List;

/**
 * @desc：列模板
 * @author：zhaohz
 * @date：2020/6/30 11:19
 */
public class ColumnDefinition implements Comparable<com.bstek.ureport.definition.ColumnDefinition>{
	private int columnNumber;
	private int width;
	private boolean hide;
	
	protected Column newColumn(List<Column> columns){
		Column col=new Column(columns);
		col.setWidth(width);
		return col;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	@Override
	public int compareTo(com.bstek.ureport.definition.ColumnDefinition o) {
		return columnNumber-o.getColumnNumber();
	}
}
