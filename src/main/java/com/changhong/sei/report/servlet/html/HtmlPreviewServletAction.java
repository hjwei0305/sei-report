package com.changhong.sei.report.servlet.html;

import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.builds.ReportBuilder;
import com.changhong.sei.report.builds.webpaging.Page;
import com.changhong.sei.report.cache.CacheUtils;
import com.changhong.sei.report.cache.TempObjectCache;
import com.changhong.sei.report.chart.ChartData;
import com.changhong.sei.report.definition.Paper;
import com.changhong.sei.report.definition.ReportDefinition;
import com.changhong.sei.report.definition.searchform.FormPosition;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.exception.ReportDesignException;
import com.changhong.sei.report.export.*;
import com.changhong.sei.report.export.html.HtmlProducer;
import com.changhong.sei.report.export.html.HtmlReport;
import com.changhong.sei.report.export.html.SearchFormData;
import com.changhong.sei.report.expression.model.Report;
import com.changhong.sei.report.servlet.MobileUtils;
import com.changhong.sei.report.servlet.RenderPageServletAction;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * @desc：html预览
 * @author：zhaohz
 * @date：2020/7/7 9:31
 */
public class HtmlPreviewServletAction extends RenderPageServletAction {
	private ExportManager exportManager;
	private ReportBuilder reportBuilder;
	private ReportRender reportRender;
	private HtmlProducer htmlProducer=new HtmlProducer();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}else{
			VelocityContext context = new VelocityContext();
			HtmlReport htmlReport=null;
			String errorMsg=null;
			try{
				htmlReport=loadReport(req);
			}catch(Exception ex){
				if(!(ex instanceof ReportDesignException)){
					ex.printStackTrace();					
				}
				errorMsg=buildExceptionMessage(ex);
			}
			String title=buildTitle(req);
			context.put("title", title);
			if(htmlReport==null){
				context.put("content", "<div style='color:red'><strong>报表计算出错，错误信息如下：</strong><br><div style=\"margin:10px\">"+errorMsg+"</div></div>");
				context.put("error", true);
				context.put("searchFormJs", "");
				context.put("downSearchFormHtml", "");
				context.put("upSearchFormHtml", "");
			}else{
				SearchFormData formData=htmlReport.getSearchFormData();
				if(formData!=null){
					context.put("searchFormJs", formData.getJs());
					if(formData.getFormPosition().equals(FormPosition.up)){
						context.put("upSearchFormHtml", formData.getHtml());						
						context.put("downSearchFormHtml", "");						
					}else{
						context.put("downSearchFormHtml", formData.getHtml());						
						context.put("upSearchFormHtml", "");						
					}
				}else{
					context.put("searchFormJs", "");
					context.put("downSearchFormHtml", "");
					context.put("upSearchFormHtml", "");	
				}
				context.put("content", htmlReport.getContent());
				context.put("style", htmlReport.getStyle());
				context.put("reportAlign", htmlReport.getReportAlign());
				context.put("totalPage", htmlReport.getTotalPage()); 
				context.put("totalPageWithCol", htmlReport.getTotalPageWithCol()); 
				context.put("pageIndex", htmlReport.getPageIndex());
				context.put("chartDatas", convertJson(htmlReport.getChartDatas()));
				context.put("error", false);
				context.put("file", req.getParameter("_u"));
				context.put("intervalRefreshValue",htmlReport.getHtmlIntervalRefreshValue());
				String customParameters=buildCustomParameters(req);
				context.put("customParameters", customParameters);
				context.put("_t", "");
				Tools tools=null;
				if(MobileUtils.isMobile(req)){
					tools=new Tools(false);
					tools.setShow(false);
				}else{
					String toolsInfo=req.getParameter("_t");
					if(StringUtils.isNotBlank(toolsInfo)){
						tools=new Tools(false);
						if(toolsInfo.equals("0")){
							tools.setShow(false);
						}else{
							String[] infos=toolsInfo.split(",");
							for(String name:infos){
								tools.doInit(name);
							}						
						}
						context.put("_t", toolsInfo);
						context.put("hasTools", true);
					}else{
						tools=new Tools(true);
					}
				}
				context.put("tools", tools);
			}
			context.put("contextPath", req.getContextPath());
			resp.setContentType("text/html");
			resp.setCharacterEncoding("utf-8");
			Template template=ve.getTemplate("ureport-html/html-preview.html","utf-8");
			PrintWriter writer=resp.getWriter();
			template.merge(context, writer);
			writer.close();
		}
	}
	
	private String buildTitle(HttpServletRequest req){
		String title=req.getParameter("_title");
		if(StringUtils.isBlank(title)){
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
	
	public void loadData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HtmlReport htmlReport=loadReport(req);
		writeObjectToJson(resp, htmlReport);
	}

	public void loadPrintPages(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String file=req.getParameter("_u");
		file=decode(file);
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		Map<String, Object> parameters = buildParameters(req);
		ReportDefinition reportDefinition=null;
		if(file.equals(PREVIEW_KEY)){
			reportDefinition=(ReportDefinition) TempObjectCache.getObject(PREVIEW_KEY);
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
		FullPageData pageData= PageBuilder.buildFullPageData(report);
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
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		ReportDefinition report=null;
		if(file.equals(PREVIEW_KEY)){
			report=(ReportDefinition) TempObjectCache.getObject(PREVIEW_KEY);
			if(report==null){
				throw new ReportDesignException("Report data has expired.");
			}
		}else{
			report=reportRender.getReportDefinition(file);
		}
		Paper paper=report.getPaper();
		writeObjectToJson(resp, paper);
	}
	
	private HtmlReport loadReport(HttpServletRequest req) {
		Map<String, Object> parameters = buildParameters(req);
		HtmlReport htmlReport=null;
		String file=req.getParameter("_u");
		file=decode(file);
		String pageIndex=req.getParameter("_i");
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		if(file.equals(PREVIEW_KEY)){
			ReportDefinition reportDefinition=(ReportDefinition) TempObjectCache.getObject(PREVIEW_KEY);
			if(reportDefinition==null){
				throw new ReportDesignException("Report data has expired,can not do preview.");
			}
			Report report=reportBuilder.buildReport(reportDefinition, parameters);
			Map<String, ChartData> chartMap=report.getContext().getChartDataMap();
			if(chartMap.size()>0){
				CacheUtils.storeChartDataMap(chartMap);
			}
			htmlReport=new HtmlReport();
			String html=null;
			if(StringUtils.isNotBlank(pageIndex) && !pageIndex.equals("0")){
				Context context=report.getContext();
				int index=Integer.valueOf(pageIndex);
				SinglePageData pageData= PageBuilder.buildSinglePageData(index, report);
				List<Page> pages=pageData.getPages();
				if(pages.size()==1){
					Page page=pages.get(0);
					html=htmlProducer.produce(context,page,false);					
				}else{
					html=htmlProducer.produce(context,pages,pageData.getColumnMargin(),false);					
				}
				htmlReport.setTotalPage(pageData.getTotalPages());
				htmlReport.setPageIndex(index);
			}else{
				html=htmlProducer.produce(report);				
			}
			if(report.getPaper().isColumnEnabled()){
				htmlReport.setColumn(report.getPaper().getColumnCount());				
			}
			htmlReport.setChartDatas(report.getContext().getChartDataMap().values());			
			htmlReport.setContent(html);
			htmlReport.setTotalPage(report.getPages().size());
			htmlReport.setStyle(reportDefinition.getStyle());
			htmlReport.setSearchFormData(reportDefinition.buildSearchFormData(report.getContext().getDatasetMap(),parameters));
			htmlReport.setReportAlign(report.getPaper().getHtmlReportAlign().name());
			htmlReport.setHtmlIntervalRefreshValue(report.getPaper().getHtmlIntervalRefreshValue());
		}else{
			if(StringUtils.isNotBlank(pageIndex) && !pageIndex.equals("0")){
				int index=Integer.valueOf(pageIndex);
				htmlReport=exportManager.exportHtml(file,req.getContextPath(),parameters,index);								
			}else{
				htmlReport=exportManager.exportHtml(file,req.getContextPath(),parameters);				
			}
		}
		return htmlReport;
	}
	
	
	private String buildCustomParameters(HttpServletRequest req){
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
	
	public void setExportManager(ExportManager exportManager) {
		this.exportManager = exportManager;
	}
	
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}
	public void setReportRender(ReportRender reportRender) {
		this.reportRender = reportRender;
	}

	@Override
	public String url() {
		return "/preview";
	}
}
