package com.changhong.sei.report.exception;

/**
 * @desc：单元格不存在异常
 * @author：zhaohz
 * @date：2020/6/30 15:17
 */
public class CellNotExistException extends ReportException {
	private static final long serialVersionUID = -2436297948073253411L;
	public CellNotExistException(String cellName) {
		super("Cell ["+cellName+"] not exist.");
	}
}
