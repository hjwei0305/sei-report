package com.changhong.sei.report.export;

import com.changhong.sei.report.expression.model.Report;

import java.io.OutputStream;

/**
 * @desc：预览生产者接口
 * @author：zhaohz
 * @date：2020/7/6 15:23
 */
public interface Producer {
	void produce(Report report, OutputStream outputStream);
}
