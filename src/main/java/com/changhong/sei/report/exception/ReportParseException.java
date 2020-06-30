package com.changhong.sei.report.exception;

/**
 * @desc：报表解析异常
 * @author：zhaohz
 * @date：2020/6/30 15:20
 */
public class ReportParseException extends ReportException {
	private static final long serialVersionUID = -8757106306597844487L;
	public ReportParseException(Exception ex) {
		super(ex);
	}
	public ReportParseException(String msg) {
		super(msg);
	}
}
