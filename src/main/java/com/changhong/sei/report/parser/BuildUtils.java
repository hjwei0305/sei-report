package com.changhong.sei.report.parser;

import com.changhong.sei.report.definition.CellDefinition;

/**
 * @desc：构建单元格工具类
 * @author：zhaohz
 * @date：2020/7/6 10:47
 */
public class BuildUtils {
	public static int buildRowNumberEnd(CellDefinition cell, int rowNumber){
		int rowSpan=cell.getRowSpan();
		rowSpan=rowSpan>0 ? rowSpan-1 : rowSpan;
		return rowNumber+rowSpan;
	}
	public static int buildColNumberEnd(CellDefinition cell, int colNumber){
		int colSpan=cell.getColSpan();
		colSpan=colSpan>0 ? colSpan-1 : colSpan;
		return colNumber+colSpan;
	}
}
