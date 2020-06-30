package com.changhong.sei.report.exception;

/**
 * @desc：报表异常
 * @author：zhaohz
 * @date：2020/6/30 9:48
 */
public class ReportException extends RuntimeException{
	private static final long serialVersionUID = 2970559370876740683L;
	public ReportException(String msg) {
		super(msg);
	}
	public ReportException(Exception ex) {
		super(ex);
		ex.printStackTrace();
	}
}
