package com.changhong.sei.report.exception;

import com.changhong.sei.report.model.ReportCell;

/**
 * @desc：逻辑单元格展开异常
 * @author：zhaohz
 * @date：2020/6/30 15:18
 */
public class IllegalCellExpandException extends ReportException {
	private static final long serialVersionUID = -2442986317129037490L;

	public IllegalCellExpandException(ReportCell cell) {
		super("Cell expand is "+cell.getExpand()+" is invalid.");
	}
}
