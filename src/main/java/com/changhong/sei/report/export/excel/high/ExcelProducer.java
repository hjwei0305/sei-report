package com.changhong.sei.report.export.excel.high;


import com.changhong.sei.report.export.excel.high.builder.ExcelBuilderDirect;
import com.changhong.sei.report.export.excel.high.builder.ExcelBuilderWithPaging;
import com.changhong.sei.report.expression.model.Report;

import java.io.OutputStream;

/**
 * @desc：Excel生产者
 * @author：zhaohz
 * @date：2020/7/6 14:03
 */
public class ExcelProducer {
	private ExcelBuilderWithPaging excelBuilderWithPaging=new ExcelBuilderWithPaging();
	private ExcelBuilderDirect excelBuilderDirect=new ExcelBuilderDirect();
	public void produceWithPaging(Report report, OutputStream outputStream) {
		doProduce(report, outputStream, true,false);
	}
	public void produce(Report report, OutputStream outputStream) {
		doProduce(report, outputStream, false,false);
	}
	public void produceWithSheet(Report report, OutputStream outputStream) {
		doProduce(report, outputStream, true,true);
	}
	
	private void doProduce(Report report, OutputStream outputStream, boolean withPaging, boolean withSheet) {
		if(withPaging){
			excelBuilderWithPaging.build(report, outputStream, withSheet);
		}else{
			excelBuilderDirect.build(report, outputStream);
		}
	}
}
