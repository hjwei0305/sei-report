package com.changhong.sei.report.export;

import com.changhong.sei.report.builds.webpaging.Page;
import com.changhong.sei.report.cache.CacheUtils;
import com.changhong.sei.report.chart.ChartData;
import com.changhong.sei.report.definition.ReportDefinition;
import com.changhong.sei.report.export.excel.high.ExcelProducer;
import com.changhong.sei.report.export.excel.low.Excel97Producer;
import com.changhong.sei.report.export.html.HtmlProducer;
import com.changhong.sei.report.export.html.HtmlReport;
import com.changhong.sei.report.export.pdf.PdfProducer;
import com.changhong.sei.report.export.word.high.WordProducer;
import com.changhong.sei.report.expression.model.Report;

import java.util.List;
import java.util.Map;

/**
 * @desc：导出
 * @author：zhaohz
 * @date：2020/7/6 15:50
 */
public class ExportManagerImpl implements ExportManager {
	private ReportRender reportRender;
	private HtmlProducer htmlProducer=new HtmlProducer();
	private WordProducer wordProducer=new WordProducer();
	private ExcelProducer excelProducer=new ExcelProducer();
	private Excel97Producer excel97Producer=new Excel97Producer();
	private PdfProducer pdfProducer=new PdfProducer();
	@Override
	public HtmlReport exportHtml(String file, String contextPath, Map<String, Object> parameters) {
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
		if(chartMap.size()>0){
			CacheUtils.storeChartDataMap(chartMap);
		}
		HtmlReport htmlReport=new HtmlReport();
		String content=htmlProducer.produce(report);
		htmlReport.setContent(content);
		if(reportDefinition.getPaper().getColumnEnabled()){
			htmlReport.setColumn(reportDefinition.getPaper().getColumnCount());
		}
		htmlReport.setStyle(reportDefinition.getStyle());
		htmlReport.setSearchFormData(reportDefinition.buildSearchFormData(report.getContext().getDatasetMap(),parameters));
		htmlReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
		htmlReport.setChartDatas(report.getContext().getChartDataMap().values());
		htmlReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
		return htmlReport;
	}
	
	@Override
	public HtmlReport exportHtml(String file, String contextPath, Map<String, Object> parameters, int pageIndex) {
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
		if(chartMap.size()>0){
			CacheUtils.storeChartDataMap(chartMap);
		}
		SinglePageData pageData= PageBuilder.buildSinglePageData(pageIndex, report);
		List<Page> pages=pageData.getPages();
		String content=null;
		if(pages.size()==1){
			content=htmlProducer.produce(report.getContext(),pages.get(0),false);
		}else{
			content=htmlProducer.produce(report.getContext(),pages,pageData.getColumnMargin(),false);
		}
		HtmlReport htmlReport=new HtmlReport();
		htmlReport.setContent(content);
		if(reportDefinition.getPaper().getColumnEnabled()){
			htmlReport.setColumn(reportDefinition.getPaper().getColumnCount());
		}
		htmlReport.setStyle(reportDefinition.getStyle());
		htmlReport.setSearchFormData(reportDefinition.buildSearchFormData(report.getContext().getDatasetMap(),parameters));
		htmlReport.setPageIndex(pageIndex);
		htmlReport.setTotalPage(pageData.getTotalPages());
		htmlReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
		htmlReport.setChartDatas(report.getContext().getChartDataMap().values());
		htmlReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
		return htmlReport;
	}
	@Override
	public void exportPdf(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		pdfProducer.produce(report, config.getOutputStream());
	}
	@Override
	public void exportWord(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		wordProducer.produce(report, config.getOutputStream());
	}
	@Override
	public void exportExcel(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		excelProducer.produce(report, config.getOutputStream());
	}

	@Override
	public void exportExcel97(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		excel97Producer.produce(report, config.getOutputStream());
	}

	@Override
	public void exportExcelWithPaging(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		excelProducer.produceWithPaging(report, config.getOutputStream());
	}
	@Override
	public void exportExcel97WithPaging(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		excel97Producer.produceWithPaging(report, config.getOutputStream());
	}

	@Override
	public void exportExcelWithPagingSheet(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		excelProducer.produceWithSheet(report, config.getOutputStream());
	}
	
	@Override
	public void exportExcel97WithPagingSheet(ExportConfigure config) {
		String file=config.getFile();
		Map<String, Object> parameters=config.getParameters();
		ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
		Report report=reportRender.render(reportDefinition, parameters);
		excel97Producer.produceWithSheet(report, config.getOutputStream());
	}
	
	public void setReportRender(ReportRender reportRender) {
		this.reportRender = reportRender;
	}
}
