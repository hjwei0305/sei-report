package com.changhong.sei.report.servlet.importexcel;

import com.changhong.sei.report.definition.ReportDefinition;

import java.io.InputStream;

/**
 * @desc：excel转换器抽象类
 * @author：zhaohz
 * @date：2020/7/6 16:50
 */
public abstract class ExcelParser {
	public abstract ReportDefinition parse(InputStream inputStream) throws Exception;
	public abstract boolean support(String name);
}
