package com.changhong.sei.report.servlet.excel;

import com.changhong.sei.report.builds.ReportBuilder;
import com.changhong.sei.report.cache.TempObjectCache;
import com.changhong.sei.report.definition.ReportDefinition;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.exception.ReportDesignException;
import com.changhong.sei.report.exception.ReportException;
import com.changhong.sei.report.export.ExportConfigure;
import com.changhong.sei.report.export.ExportConfigureImpl;
import com.changhong.sei.report.export.ExportManager;
import com.changhong.sei.report.export.excel.high.ExcelProducer;
import com.changhong.sei.report.expression.model.Report;
import com.changhong.sei.report.servlet.BaseServletAction;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @desc：excel
 * @author：zhaohz
 * @date：2020/7/6 17:36
 */
public class ExportExcelServletAction extends BaseServletAction {
	private ReportBuilder reportBuilder;
	private ExportManager exportManager;
	private ExcelProducer excelProducer=new ExcelProducer();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method=retriveMethod(req);
		if(method!=null){
			invokeMethod(method, req, resp);
		}else{			
			buildExcel(req, resp,false,false);
		}
	}
	public void paging(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		buildExcel(req, resp, true, false);
	}
	
	public void sheet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		buildExcel(req, resp, false, true);
	}
	
	public void buildExcel(HttpServletRequest req, HttpServletResponse resp, boolean withPage, boolean withSheet) throws IOException {
		String file=req.getParameter("_u");
		file=decode(file);
		if(StringUtils.isBlank(file)){
			throw new ReportComputeException("Report file can not be null.");
		}
		OutputStream outputStream=resp.getOutputStream();
		try {
			String fileName=req.getParameter("_n");
			fileName=buildDownloadFileName(file, fileName, ".xlsx");
			resp.setContentType("application/octet-stream;charset=ISO8859-1");
			fileName=new String(fileName.getBytes("UTF-8"),"ISO8859-1");
			resp.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
			Map<String, Object> parameters = buildParameters(req);
			if(file.equals(PREVIEW_KEY)){
				ReportDefinition reportDefinition=(ReportDefinition) TempObjectCache.getObject(PREVIEW_KEY);
				if(reportDefinition==null){
					throw new ReportDesignException("Report data has expired,can not do export excel.");
				}
				Report report=reportBuilder.buildReport(reportDefinition, parameters);
				if(withPage){
					excelProducer.produceWithPaging(report, outputStream);
				}else if(withSheet){
					excelProducer.produceWithSheet(report, outputStream);
				}else{
					excelProducer.produce(report, outputStream);				
				}
			}else{
				ExportConfigure configure=new ExportConfigureImpl(file,parameters,outputStream);
				if(withPage){
					exportManager.exportExcelWithPaging(configure);
				}else if(withSheet){
					exportManager.exportExcelWithPagingSheet(configure);
				}else{
					exportManager.exportExcel(configure);
				}
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
		return "/excel";
	}
}
