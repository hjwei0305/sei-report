package com.changhong.sei.report.exception;

/**
 * @desc：单元格计算异常
 * @author：zhaohz
 * @date：2020/6/30 15:15
 */
public class CellComputeException extends ReportException {
	private static final long serialVersionUID = -1363254031247074841L;
	public CellComputeException(Exception ex) {
		super(ex);
	}
	public CellComputeException(String msg) {
		super(msg);
	}
}
