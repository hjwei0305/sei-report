package com.changhong.sei.report.exception;

/**
 * @desc：单元格依赖异常
 * @author：zhaohz
 * @date：2020/6/30 15:16
 */
public class CellDependencyException extends ReportException {

	private static final long serialVersionUID = 5765713360910995235L;

	public CellDependencyException() {
		super("Report cells has cyclic dependency.");
	}
}
