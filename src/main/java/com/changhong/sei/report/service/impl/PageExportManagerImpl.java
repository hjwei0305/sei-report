package com.changhong.sei.report.service.impl;

import com.bstek.ureport.cache.CacheUtils;
import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.export.ExportConfigure;
import com.bstek.ureport.export.PageBuilder;
import com.bstek.ureport.export.ReportRender;
import com.bstek.ureport.export.SinglePageData;
import com.bstek.ureport.export.html.HtmlProducer;
import com.bstek.ureport.export.pdf.PdfProducer;
import com.bstek.ureport.model.Report;
import com.changhong.sei.report.model.Page;
import com.changhong.sei.report.model.PageProducer;
import com.changhong.sei.report.model.PageReport;
import com.changhong.sei.report.service.PageExportManager;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PageExportManagerImpl implements PageExportManager {
    @Resource(name = "ureport.reportRender")
    private ReportRender reportRender;
    private HtmlProducer htmlProducer = new HtmlProducer();
    private PdfProducer pdfProducer = new PdfProducer();
    private PageProducer pageProducer = new PageProducer();

    @Override
    public PageReport exportHtml(String file, String contextPath, Map<String, Object> parameters) {
        ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
        Report report=reportRender.render(reportDefinition, parameters);
        Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
        if(chartMap.size()>0){
            CacheUtils.storeChartDataMap(chartMap);
        }
        PageReport pageReport=new PageReport();
        String content=htmlProducer.produce(report);
        pageReport.setContent(content);
        if(reportDefinition.getPaper().isColumnEnabled()){
            pageReport.setColumn(reportDefinition.getPaper().getColumnCount());
        }
        pageReport.setStyle(reportDefinition.getStyle());
        pageReport.setSearchFormData(reportDefinition.buildSearchFormData(report.getContext().getDatasetMap(),parameters));
        pageReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
        pageReport.setChartDatas(report.getContext().getChartDataMap().values());
        pageReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
        pageReport.setType("1");
        return pageReport;
    }

    @Override
    public PageReport exportHtml(String file, String contextPath, Map<String, Object> parameters, int pageIndex) {
        ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
        Report report=reportRender.render(reportDefinition, parameters);
        Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
        if(chartMap.size()>0){
            CacheUtils.storeChartDataMap(chartMap);
        }
        SinglePageData pageData= PageBuilder.buildSinglePageData(pageIndex, report);
        List<com.bstek.ureport.build.paging.Page> pages = pageData.getPages();
        String content=null;
        if(pages.size()==1){
            content=htmlProducer.produce(report.getContext(),pages.get(0),false);
        }else{
            content=htmlProducer.produce(report.getContext(),pages,pageData.getColumnMargin(),false);
        }
        PageReport pageReport=new PageReport();
        pageReport.setContent(content);
        if(reportDefinition.getPaper().isColumnEnabled()){
            pageReport.setColumn(reportDefinition.getPaper().getColumnCount());
        }
        pageReport.setStyle(reportDefinition.getStyle());
        pageReport.setSearchFormData(reportDefinition.buildSearchFormData(report.getContext().getDatasetMap(),parameters));
        pageReport.setPage(new Page(pageIndex));
        pageReport.getPage().setTotal(pageData.getTotalPages());
        pageReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
        pageReport.setChartDatas(report.getContext().getChartDataMap().values());
        pageReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
        pageReport.setType("2");
        return pageReport;
    }

    @Override
    public PageReport exportHtml(String file, String contextPath, Map<String, Object> parameters, int pageIndex, int rows) throws ServletException, SQLException {
        ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
        Report report=reportRender.render(reportDefinition, parameters);
        Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
        if(chartMap.size()>0){
            CacheUtils.storeChartDataMap(chartMap);
        }
        PageReport pageReport=new PageReport();
        String content=htmlProducer.produce(report);
        pageReport.setContent(content);
        if(reportDefinition.getPaper().isColumnEnabled()){
            pageReport.setColumn(reportDefinition.getPaper().getColumnCount());
        }
        pageReport.setType("3");
        pageReport.setPage(pageProducer.produce(reportDefinition, parameters, pageIndex, rows));
        pageReport.setStyle(reportDefinition.getStyle());
        pageReport.setSearchFormData(reportDefinition.buildSearchFormData(report.getContext().getDatasetMap(),parameters));
        pageReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
        pageReport.setChartDatas(report.getContext().getChartDataMap().values());
        pageReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
        return pageReport;
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
    public void exportExcel(ExportConfigure config) {

    }

    @Override
    public void exportExcel97(ExportConfigure config) {

    }

    @Override
    public void exportExcelWithPaging(ExportConfigure config) {

    }

    @Override
    public void exportExcel97WithPaging(ExportConfigure config) {

    }

    @Override
    public void exportExcelWithPagingSheet(ExportConfigure config) {

    }

    @Override
    public void exportExcel97WithPagingSheet(ExportConfigure config) {

    }

    @Override
    public void exportWord(ExportConfigure config) {

    }
}