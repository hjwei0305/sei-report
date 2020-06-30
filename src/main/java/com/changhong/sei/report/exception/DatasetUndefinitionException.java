package com.changhong.sei.report.exception;

/**
 * @desc：数据集未定义异常
 * @author：zhaohz
 * @date：2020/6/30 11:16
 */
public class DatasetUndefinitionException extends ReportException {
	private static final long serialVersionUID = -1897331038232057797L;

	public DatasetUndefinitionException(String datasetName) {
		super("Dataset ["+datasetName+"] not definition.");
	}
}
