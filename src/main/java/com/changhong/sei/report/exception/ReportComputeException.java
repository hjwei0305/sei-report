package com.changhong.sei.report.exception;

/**
 * @desc：报表计算异常
 * @author：zhaohz
 * @date：2020/6/30 9:47
 */
public class ReportComputeException extends ReportException {
	private static final long serialVersionUID = -5079596691655241415L;
	public ReportComputeException(Exception ex) {
		super(ex);
	}
	public ReportComputeException(String msg) {
		super(msg);
	}
}
