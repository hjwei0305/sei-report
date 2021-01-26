package com.changhong.sei.report.exception;

/**
 * @desc：设计异常
 * @author：zhaohz
 * @date：2020/7/6 17:39
 */
public class ReportDesignException extends ReportException {
	private static final long serialVersionUID = 4046240733455821337L;
	public ReportDesignException(Exception ex) {
		super(ex);
	}
	public ReportDesignException(String msg) {
		super(msg);
	}
}
