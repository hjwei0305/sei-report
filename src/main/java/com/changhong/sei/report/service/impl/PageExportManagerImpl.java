package com.changhong.sei.report.service.impl;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.cache.CacheUtils;
import com.changhong.sei.report.chart.ChartData;
import com.changhong.sei.report.definition.ReportDefinition;
import com.changhong.sei.report.dto.TableDto;
import com.changhong.sei.report.export.ExportConfigure;
import com.changhong.sei.report.export.PageBuilder;
import com.changhong.sei.report.export.ReportRender;
import com.changhong.sei.report.export.SinglePageData;
import com.changhong.sei.report.export.html.HtmlProducer;
import com.changhong.sei.report.export.pdf.PdfProducer;
import com.changhong.sei.report.expression.model.Report;
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
        TableDto jsonContent = pageProducer.buildTable(report.getContext(), report.getRows(), report.getColumns(), report.getRowColCellMap(), false, false);
        pageReport.setContent(content);
        pageReport.setJsonContent(jsonContent);
        if(reportDefinition.getPaper().getColumnEnabled()){
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
        List<com.changhong.sei.report.builds.webpaging.Page> pages = pageData.getPages();
        String content=null;
        TableDto jsonContent = null;
        if(pages.size()==1){
            com.changhong.sei.report.builds.webpaging.Page pag = pages.get(0);
            Context context = report.getContext();
            content=htmlProducer.produce(context,pag,false);
            jsonContent = pageProducer.buildTable(context, pag.getRows(), pag.getColumns(), context.getReport().getRowColCellMap(), false, true);
        }else{
            Context context = report.getContext();
            int columnMargin = pageData.getColumnMargin();
            content = htmlProducer.produce(context, pages, columnMargin, false);
            jsonContent = pageProducer.buildTableWithColumnMargin(context, pages ,columnMargin);
        }
        PageReport pageReport=new PageReport();
        pageReport.setContent(content);
        pageReport.setJsonContent(jsonContent);
        if(reportDefinition.getPaper().getColumnEnabled()){
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
    public PageReport exportHtml(String file, String contextPath, Map<String, Object> parameters, Page page) throws ServletException, SQLException {
        ReportDefinition reportDefinition=reportRender.getReportDefinition(file);
        Report report=reportRender.render(reportDefinition, parameters);
        Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
        if(chartMap.size()>0){
            CacheUtils.storeChartDataMap(chartMap);
        }
        PageReport pageReport=new PageReport();
        String content=htmlProducer.produce(report);
        TableDto jsonContent = pageProducer.buildTable(report.getContext(), report.getRows(), report.getColumns(), report.getRowColCellMap(), false, false);
        pageReport.setContent(content);
        pageReport.setJsonContent(jsonContent);
        if(reportDefinition.getPaper().getColumnEnabled()){
            pageReport.setColumn(reportDefinition.getPaper().getColumnCount());
        }
        pageReport.setType("3");
        pageReport.setPage(pageProducer.produce(reportDefinition, parameters, page));
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