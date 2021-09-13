package com.changhong.sei.report.servlet.designer;

import com.changhong.sei.report.cache.CacheUtils;
import com.changhong.sei.report.cache.TempObjectCache;
import com.changhong.sei.report.definition.ReportDefinition;
import com.changhong.sei.report.dsl.ReportParserLexer;
import com.changhong.sei.report.dsl.ReportParserParser;
import com.changhong.sei.report.exception.ReportDesignException;
import com.changhong.sei.report.export.ReportRender;
import com.changhong.sei.report.expression.ErrorInfo;
import com.changhong.sei.report.expression.ScriptErrorListener;
import com.changhong.sei.report.parser.ReportParser;
import com.changhong.sei.report.provider.report.ReportProvider;
import com.changhong.sei.report.servlet.RenderPageServletAction;
import com.changhong.sei.report.utils.ReportUtils;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * @desc：设计器
 * @author：zhaohz
 * @date：2020/7/7 9:38
 */
public class DesignerServletAction extends RenderPageServletAction {
	private ReportRender reportRender;
	private ReportParser reportParser;
	private List<ReportProvider> reportProviders=new ArrayList<ReportProvider>();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}else{
			VelocityContext context = new VelocityContext();
			context.put("contextPath", req.getContextPath());
			resp.setContentType("text/html");
			resp.setCharacterEncoding("utf-8");
			Template template=ve.getTemplate("static/designer.html","utf-8");
			PrintWriter writer=resp.getWriter();
			template.merge(context, writer);
			writer.close();
		}
	}
	public void scriptValidation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content=req.getParameter("content");
		CharStream antlrInputStream = CharStreams.fromString(content);
		//ANTLRInputStream antlrInputStream=new ANTLRInputStream(text);
		ReportParserLexer lexer=new ReportParserLexer(antlrInputStream);
		CommonTokenStream tokenStream=new CommonTokenStream(lexer);
		ReportParserParser parser=new ReportParserParser(tokenStream);
		ScriptErrorListener errorListener=new ScriptErrorListener();
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);
		parser.expression();
		List<ErrorInfo> infos=errorListener.getInfos();
		writeObjectToJson(resp, infos);
	}
	
	public void conditionScriptValidation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content=req.getParameter("content");
		CharStream antlrInputStream = CharStreams.fromString(content);
		//ANTLRInputStream antlrInputStream=new ANTLRInputStream(text);
		ReportParserLexer lexer=new ReportParserLexer(antlrInputStream);
		CommonTokenStream tokenStream=new CommonTokenStream(lexer);
		ReportParserParser parser=new ReportParserParser(tokenStream);
		ScriptErrorListener errorListener=new ScriptErrorListener();
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);
		parser.expr();
		List<ErrorInfo> infos=errorListener.getInfos();
		writeObjectToJson(resp, infos);
	}

	public void parseDatasetName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String expr=req.getParameter("expr");
		CharStream antlrInputStream = CharStreams.fromString(expr);
		//ANTLRInputStream antlrInputStream=new ANTLRInputStream(text);
		ReportParserLexer lexer=new ReportParserLexer(antlrInputStream);
		CommonTokenStream tokenStream=new CommonTokenStream(lexer);
		ReportParserParser parser=new ReportParserParser(tokenStream);
		parser.removeErrorListeners();
		ReportParserParser.DatasetContext ctx=parser.dataset();
		String datasetName=ctx.Identifier().getText();
		Map<String,String> result=new HashMap<String,String>();
		result.put("datasetName", datasetName);
		writeObjectToJson(resp, result);
	}
	public void savePreviewData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content=req.getParameter("content");
		content=decodeContent(content);
		try (InputStream inputStream= IOUtils.toInputStream(content,"utf-8")) {
			ReportDefinition reportDef = reportParser.parse(inputStream, "p");
			reportRender.rebuildReportDefinition(reportDef);
			TempObjectCache.putObject(PREVIEW_KEY, reportDef);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void loadReport(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String file=req.getParameter("file");
		if(file==null){
			throw new ReportDesignException("Report file can not be null.");
		}
		file= ReportUtils.decodeFileName(file);
		Object obj= TempObjectCache.getObject(file);
		if(obj!=null && obj instanceof ReportDefinition){
			ReportDefinition reportDef=(ReportDefinition)obj;
			TempObjectCache.removeObject(file);
			writeObjectToJson(resp, new ReportDefinitionWrapper(reportDef));
		}else{
			ReportDefinition reportDef=reportRender.parseReport(file);
			writeObjectToJson(resp, new ReportDefinitionWrapper(reportDef));
		}
	}

	public void deleteReportFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String file=req.getParameter("file");
		if(file==null){
			throw new ReportDesignException("Report file can not be null.");
		}
		ReportProvider targetReportProvider=null;
		for(ReportProvider provider:reportProviders){
			if(file.startsWith(provider.getPrefix())){
				targetReportProvider=provider;
				break;
			}
		}
		if(targetReportProvider==null){
			throw new ReportDesignException("File ["+file+"] not found available report provider.");
		}
		targetReportProvider.deleteReport(file);
	}


	public void saveReportFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String file=req.getParameter("file");
		file= ReportUtils.decodeFileName(file);
		String content=req.getParameter("content");
		content=decodeContent(content);
		ReportProvider targetReportProvider=null;
		for(ReportProvider provider:reportProviders){
			if(file.startsWith(provider.getPrefix())){
				targetReportProvider=provider;
				break;
			}
		}
		if(targetReportProvider==null){
			throw new ReportDesignException("File ["+file+"] not found available report provider.");
		}
		targetReportProvider.saveReport(file, content);
		try (InputStream inputStream= IOUtils.toInputStream(content,"utf-8")) {
			ReportDefinition reportDef = reportParser.parse(inputStream, file);
			reportRender.rebuildReportDefinition(reportDef);
			CacheUtils.cacheReportDefinition(file, reportDef);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void loadReportProviders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		writeObjectToJson(resp, reportProviders);
	}
	
	public void setReportRender(ReportRender reportRender) {
		this.reportRender = reportRender;
	}
	
	public void setReportParser(ReportParser reportParser) {
		this.reportParser = reportParser;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		super.setApplicationContext(applicationContext);
		Collection<ReportProvider> providers=applicationContext.getBeansOfType(ReportProvider.class).values();
		for(ReportProvider provider:providers){
			if(provider.disabled() || provider.getName()==null){
				continue;
			}
			reportProviders.add(provider);
		}
	}

	@Override
	public String url() {
		return "/designer";
	}
}
