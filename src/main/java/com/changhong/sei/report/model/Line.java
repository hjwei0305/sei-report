package com.changhong.sei.report.model;

import com.changhong.sei.report.definition.CellStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：行或列
 * @author：zhaohz
 * @date：2020/6/28 15:01
 */
public abstract class Line{
	private CellStyle customCellStyle;
	private List<Cell> cells=new ArrayList<Cell>();

	public CellStyle getCustomCellStyle() {
		return customCellStyle;
	}

	public void setCustomCellStyle(CellStyle customCellStyle) {
		this.customCellStyle = customCellStyle;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
}
