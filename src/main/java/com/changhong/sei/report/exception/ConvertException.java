package com.changhong.sei.report.exception;

/**
 * @desc：转换异常
 * @author：zhaohz
 * @date：2020/6/30 15:17
 */
public class ConvertException extends ReportException {
	private static final long serialVersionUID = 8681316352205087220L;
	public ConvertException(Exception ex) {
		super(ex);
	}
	public ConvertException(String msg) {
		super(msg);
	}
}
