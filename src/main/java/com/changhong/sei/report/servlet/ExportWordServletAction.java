package com.changhong.sei.report.servlet;

import com.changhong.sei.report.builds.ReportBuilder;
import com.changhong.sei.report.cache.TempObjectCache;
import com.changhong.sei.report.definition.ReportDefinition;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.exception.ReportDesignException;
import com.changhong.sei.report.exception.ReportException;
import com.changhong.sei.report.export.ExportConfigure;
import com.changhong.sei.report.export.ExportConfigureImpl;
import com.changhong.sei.report.export.ExportManager;
import com.changhong.sei.report.export.word.high.WordProducer;
import com.changhong.sei.report.expression.model.Report;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @desc：导出word
 * @author：zhaohz
 * @date：2020/7/7 9:28
 */
public class ExportWordServletAction extends BaseServletAction {
	private ReportBuilder reportBuilder;
	private ExportManager exportManager;
	private WordProducer wordProducer=new WordProducer();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}else{			
			buildWord(req, resp);
		}
	}
	
	public void buildWord(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String file=req.getParameter("_u");
		file=decode(file);
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		OutputStream outputStream=resp.getOutputStream();
		try {
			String fileName=req.getParameter("_n");
			fileName=buildDownloadFileName(file, fileName, ".docx");
			fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");
			resp.setContentType("application/octet-stream;charset=ISO8859-1");
			resp.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
			Map<String, Object> parameters = buildParameters(req);
			if(file.equals(PREVIEW_KEY)){
				ReportDefinition reportDefinition=(ReportDefinition) TempObjectCache.getObject(PREVIEW_KEY);
				if(reportDefinition==null){
					throw new ReportDesignException("Report data has expired,can not do export word.");
				}
				Report report=reportBuilder.buildReport(reportDefinition, parameters);
				wordProducer.produce(report, outputStream);
			}else{
				ExportConfigure configure=new ExportConfigureImpl(file,parameters,outputStream);
				exportManager.exportWord(configure);
			}			
		}catch(Exception ex) {
			throw new ReportException(ex);
		}finally {			
			outputStream.flush();
			outputStream.close();
		}
	}
	
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}
	public void setExportManager(ExportManager exportManager) {
		this.exportManager = exportManager;
	}

	@Override
	public String url() {
		return "/word";
	}
}