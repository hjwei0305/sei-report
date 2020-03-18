package com.changhong.sei.report.servlet;

import com.bstek.ureport.build.Context;
import com.bstek.ureport.build.ReportBuilder;
import com.bstek.ureport.build.paging.Page;
import com.bstek.ureport.cache.CacheUtils;
import com.bstek.ureport.chart.ChartData;
import com.bstek.ureport.console.MobileUtils;
import com.bstek.ureport.console.RenderPageServletAction;
import com.bstek.ureport.console.cache.TempObjectCache;
import com.bstek.ureport.console.exception.ReportDesignException;
import com.bstek.ureport.console.html.Tools;
import com.bstek.ureport.definition.Paper;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.definition.searchform.FormPosition;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.export.FullPageData;
import com.bstek.ureport.export.PageBuilder;
import com.bstek.ureport.export.ReportRender;
import com.bstek.ureport.export.SinglePageData;
import com.bstek.ureport.export.html.HtmlProducer;
import com.bstek.ureport.export.html.SearchFormData;
import com.bstek.ureport.model.Report;
import com.changhong.sei.report.model.PageProducer;
import com.changhong.sei.report.model.PageReport;
import com.changhong.sei.report.model.PageReportDto;
import com.changhong.sei.report.service.PageExportManager;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;

public class HtmlPageServletAction extends RenderPageServletAction {

    @Resource(name = "ureport.pageExportManager")
    private PageExportManager pageExportManager;
    @Resource(name = "ureport.reportBuilder")
    private ReportBuilder reportBuilder;
    @Resource(name = "ureport.reportRender")
    private ReportRender reportRender;
    private HtmlProducer htmlProducer = new HtmlProducer();
    private PageProducer pageProducer = new PageProducer();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method=retriveMethod(request);
        if(method!=null){
            invokeMethod(method, request, response);
        }else {
            VelocityContext context = new VelocityContext();
            PageReport pageReport = null;
            String errorMsg = null;
            try {
                pageReport = loadReport(request);
            } catch (Exception ex) {
                if (!(ex instanceof ReportDesignException)) {
                    ex.printStackTrace();
                }
                errorMsg = buildExceptionMessage(ex);
            }
            String title = buildTitle(request);
            context.put("title", title);
            if (pageReport == null) {
                context.put("content", "<div style='color:red'><strong>报表计算出错，错误信息如下：</strong><br><div style=\"margin:10px\">" + errorMsg + "</div></div>");
                context.put("error", true);
                context.put("searchFormJs", "");
                context.put("downSearchFormHtml", "");
                context.put("upSearchFormHtml", "");
            } else {
                SearchFormData formData = pageReport.getSearchFormData();
                if (formData != null) {
                    context.put("searchFormJs", formData.getJs());
                    if (formData.getFormPosition().equals(FormPosition.up)) {
                        context.put("upSearchFormHtml", formData.getHtml());
                        context.put("downSearchFormHtml", "");
                    } else {
                        context.put("downSearchFormHtml", formData.getHtml());
                        context.put("upSearchFormHtml", "");
                    }
                } else {
                    context.put("searchFormJs", "");
                    context.put("downSearchFormHtml", "");
                    context.put("upSearchFormHtml", "");
                }
                context.put("content", pageReport.getContent());
                context.put("style", pageReport.getStyle());
                context.put("reportAlign", pageReport.getReportAlign());
                //context.put("totalPage", pageReport.getTotalPage());
                context.put("total", ObjectUtils.isEmpty(pageReport.getPage()) ? 0 : pageReport.getPage().getTotal());
                context.put("page", ObjectUtils.isEmpty(pageReport.getPage()) ? 0 : pageReport.getPage().getPage());
                context.put("rows", ObjectUtils.isEmpty(pageReport.getPage()) ? 0 : pageReport.getPage().getRows());
                context.put("records", ObjectUtils.isEmpty(pageReport.getPage()) ? 0 : pageReport.getPage().getRecords());
                //context.put("rows", ObjectUtils.isEmpty(pageReport.getPage()) ? 0 : pageReport.getPage().getRows());
                //context.put("records", ObjectUtils.isEmpty(pageReport.getPage()) ? 0 : pageReport.getPage().getRecords());
                context.put("chartDatas", convertJson(pageReport.getChartDatas()));
                context.put("error", false);
                context.put("file", request.getParameter("_u"));
                context.put("intervalRefreshValue", pageReport.getHtmlIntervalRefreshValue());
                String customParameters = buildCustomParameters(request, pageReport.getType());
                context.put("customParameters", customParameters);
                context.put("_t", "");
                context.put("type", pageReport.getType());
                Tools tools = null;
                if (MobileUtils.isMobile(request)) {
                    tools = new Tools(false);
                    tools.setShow(false);
                } else {
                    String toolsInfo = request.getParameter("_t");
                    if (!StringUtils.isEmpty(toolsInfo)) {
                        tools = new Tools(false);
                        if (toolsInfo.equals("0")) {
                            tools.setShow(false);
                        } else {
                            String[] infos = toolsInfo.split(",");
                            for (String name : infos) {
                                tools.doInit(name);
                            }
                        }
                        context.put("_t", toolsInfo);
                        context.put("hasTools", true);
                    } else {
                        tools = new Tools(true);
                    }
                }
                context.put("tools", tools);
            }
            context.put("contextPath", request.getContextPath());
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            Template template = ve.getTemplate("static/html-page.html", "utf-8");
            PrintWriter writer = response.getWriter();
            template.merge(context, writer);
            writer.close();
        }
    }

    private PageReport loadReport(HttpServletRequest request) throws ServletException, SQLException {
        Map<String, Object> parameters = buildParameters(request);
        PageReport pageReport = null;
        String file=request.getParameter("_u");
        file=decode(file);
        if(StringUtils.isEmpty(file)){
            throw new ReportComputeException("Report file can not be null.");
        }
        String type = request.getParameter("_type");
        String pageIndex = request.getParameter("_i");
        if (!"3".equals(type)) {
            if (parameters.containsKey("page")) {
                parameters.remove("page");
            }
            if (parameters.containsKey("rows")) {
                parameters.remove("rows");
            }
        }
        int page = 0;
        int rows = 0;
        if (parameters.containsKey("page") && parameters.containsKey("rows")) {
            String pageNum = parameters.get("page").toString();
            String size = parameters.get("rows").toString();
            if (!pageNum.equals("0") && !size.equals("0")) {
                page = 1;
                rows = 30;
                try {
                    page = Integer.valueOf(pageNum);
                    rows = Integer.valueOf(size);
                } catch (Exception e) {
                    throw new ServletException("page和rows必须是整数");
                }
                //重构parameters，把page参数替换成startRow参数
                rebuildParam(parameters, (page-1)*rows);
            }
        }
        if(file.equals(PREVIEW_KEY)){
            ReportDefinition reportDefinition=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
            if(reportDefinition==null){
                throw new ReportDesignException("Report data has expired,can not do preview.");
            }
            Report report=reportBuilder.buildReport(reportDefinition, parameters);
            Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
            if(chartMap.size()>0){
                CacheUtils.storeChartDataMap(chartMap);
            }
            pageReport=new PageReport();
            String html=null;
            if("2".equals(type)) {
                if (!StringUtils.isEmpty(pageIndex) && !pageIndex.equals("0")) {
                    Context context = report.getContext();
                    int index = Integer.valueOf(pageIndex);
                    SinglePageData pageData = PageBuilder.buildSinglePageData(index, report);
                    List<Page> pages = pageData.getPages();
                    if (pages.size() == 1) {
                        Page pag = pages.get(0);
                        html = htmlProducer.produce(context, pag, false);
                    } else {
                        html = htmlProducer.produce(context, pages, pageData.getColumnMargin(), false);
                    }
                    pageReport.setPage(new com.changhong.sei.report.model.Page(index));
                    pageReport.getPage().setTotal(pageData.getTotalPages());
                } else {
                    html = htmlProducer.produce(report);
                }
            }else{
                if(page!=0&&rows!=0){
                    pageReport.setPage(pageProducer.produce(reportDefinition, parameters, page, rows));
                }
                html = htmlProducer.produce(report);
            }
            if(report.getPaper().isColumnEnabled()){
                pageReport.setColumn(report.getPaper().getColumnCount());
            }
            pageReport.setType(type==null?"1":type);
            pageReport.setChartDatas(report.getContext().getChartDataMap().values());
            pageReport.setContent(html);
            pageReport.setStyle(reportDefinition.getStyle());
            pageReport.setSearchFormData(reportDefinition.buildSearchFormData(report.getContext().getDatasetMap(),parameters));
            pageReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
            pageReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
        }else {
            if("2".equals(type)) {
                if (!StringUtils.isEmpty(pageIndex) && !pageIndex.equals("0")) {
                    int index = 1;
                    try {
                        index = Integer.valueOf(pageIndex);
                    } catch (Exception e) {
                        throw new ServletException("_i必须是整数");
                    }
                    pageReport = pageExportManager.exportHtml(file, request.getContextPath(), parameters, index);
                }
            }else if("3".equals(type)){
                if (page!=0&&rows!=0) {
                    pageReport = pageExportManager.exportHtml(file, request.getContextPath(), parameters, page, rows);
                }
            }
            if(pageReport==null) pageReport = pageExportManager.exportHtml(file, request.getContextPath(), parameters);
        }
        return pageReport;
    }

    private void rebuildParam(Map<String, Object> parameters, int startRow) {
        if(parameters.containsKey("page")){
            parameters.remove("page");
            parameters.put("startRow", startRow);
        }
    }

    private String buildTitle(HttpServletRequest req){
        String title=req.getParameter("_title");
        if(StringUtils.isEmpty(title)){
            title=req.getParameter("_u");
            title=decode(title);
            int point=title.lastIndexOf(".ureport.xml");
            if(point>-1){
                title=title.substring(0,point);
            }
            if(title.equals("p")){
                title="设计中报表";
            }
        }else{
            title=decode(title);
        }
        return title+"-ureport";
    }

    private String convertJson(Collection<ChartData> data){
        if(data==null || data.size()==0){
            return "";
        }
        ObjectMapper mapper=new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(data);
            return json;
        } catch (Exception e) {
            throw new ReportComputeException(e);
        }
    }

    public void loadData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        PageReport pageReport=loadReport(req);
        writeObjectToJson(resp, new PageReportDto(pageReport));
    }

    public void loadPrintPages(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file=req.getParameter("_u");
        file=decode(file);
        if(StringUtils.isEmpty(file)){
            throw new ReportComputeException("Report file can not be null.");
        }
        Map<String, Object> parameters = buildParameters(req);
        ReportDefinition reportDefinition=null;
        if(file.equals(PREVIEW_KEY)){
            reportDefinition=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
            if(reportDefinition==null){
                throw new ReportDesignException("Report data has expired,can not do export excel.");
            }
        }else{
            reportDefinition=reportRender.getReportDefinition(file);
        }
        Report report=reportBuilder.buildReport(reportDefinition, parameters);
        Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
        if(chartMap.size()>0){
            CacheUtils.storeChartDataMap(chartMap);
        }
        FullPageData pageData=PageBuilder.buildFullPageData(report);
        StringBuilder sb=new StringBuilder();
        List<List<Page>> list=pageData.getPageList();
        Context context=report.getContext();
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                List<Page> columnPages=list.get(i);
                if(i==0){
                    String html=htmlProducer.produce(context,columnPages,pageData.getColumnMargin(),false);
                    sb.append(html);
                }else{
                    String html=htmlProducer.produce(context,columnPages,pageData.getColumnMargin(),false);
                    sb.append(html);
                }
            }
        }else{
            List<Page> pages=report.getPages();
            for(int i=0;i<pages.size();i++){
                Page page=pages.get(i);
                if(i==0){
                    String html=htmlProducer.produce(context,page, false);
                    sb.append(html);
                }else{
                    String html=htmlProducer.produce(context,page, true);
                    sb.append(html);
                }
            }
        }
        Map<String,String> map=new HashMap<String,String>();
        map.put("html", sb.toString());
        writeObjectToJson(resp, map);
    }

    public void loadPagePaper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file=req.getParameter("_u");
        file=decode(file);
        if(StringUtils.isEmpty(file)){
            throw new ReportComputeException("Report file can not be null.");
        }
        ReportDefinition report=null;
        if(file.equals(PREVIEW_KEY)){
            report=(ReportDefinition)TempObjectCache.getObject(PREVIEW_KEY);
            if(report==null){
                throw new ReportDesignException("Report data has expired.");
            }
        }else{
            report=reportRender.getReportDefinition(file);
        }
        Paper paper=report.getPaper();
        writeObjectToJson(resp, paper);
    }

    private String buildCustomParameters(HttpServletRequest req, String type){
        StringBuilder sb=new StringBuilder();
        Enumeration<?> enumeration=req.getParameterNames();
        while(enumeration.hasMoreElements()){
            Object obj=enumeration.nextElement();
            if(obj==null){
                continue;
            }
            String name=obj.toString();
            String value=req.getParameter(name);
            if(name==null || value==null || (name.startsWith("_") && !name.equals("_n"))){
                continue;
            }
            if("1".equals(type)){
                if("page".equals(name) || "rows".equals(name))continue;
            }
            if(sb.length()>0){
                sb.append("&");
            }
            sb.append(name);
            sb.append("=");
            sb.append(value);
        }
        return sb.toString();
    }

    private String buildExceptionMessage(Throwable throwable){
        Throwable root=buildRootException(throwable);
        StringWriter sw=new StringWriter();
        PrintWriter pw=new PrintWriter(sw);
        root.printStackTrace(pw);
        String trace=sw.getBuffer().toString();
        trace=trace.replaceAll("\n", "<br>");
        pw.close();
        return trace;
    }

    @Override
    public String url() {
        return "/report";
    }
}