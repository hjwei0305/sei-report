package com.changhong.sei.report.servlet.importexcel;

import com.changhong.sei.report.cache.TempObjectCache;
import com.changhong.sei.report.definition.ReportDefinition;
import com.changhong.sei.report.servlet.RenderPageServletAction;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：excel模板导入
 * @author：zhaohz
 * @date：2020/7/6 16:59
 */
public class ImportExcelServletAction extends RenderPageServletAction {
	private List<ExcelParser> excelParsers=new ArrayList<ExcelParser>();
	public ImportExcelServletAction(){
		excelParsers.add(new HSSFExcelParser());
		excelParsers.add(new XSSFExcelParser());
	}
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tempDir=System.getProperty("java.io.tmpdir");
		FileItemFactory factory=new DiskFileItemFactory(1000240,new File(tempDir));
		ServletFileUpload upload=new ServletFileUpload(factory);
		ReportDefinition report=null;
		String errorInfo=null;
		try {
			List<FileItem> items=upload.parseRequest(req);
			for(FileItem item:items){
				String fieldName=item.getFieldName();
				String name=item.getName().toLowerCase();
				if(fieldName.equals("_excel_file") && (name.endsWith(".xls") || name.endsWith(".xlsx"))){
					InputStream inputStream=item.getInputStream();
					for(ExcelParser parser:excelParsers){
						if(parser.support(name)){
							report=parser.parse(inputStream);
							break;
						}
					}
					inputStream.close();
					break;
				}
			}
			errorInfo="请选择一个合法的Excel导入";
		} catch (Exception e) {
			e.printStackTrace();
			errorInfo=e.getMessage();
		}
		Map<String,Object> result=new HashMap<String,Object>();
		if(report!=null){
			result.put("result", true);
			TempObjectCache.putObject("classpath:template/template.ureport.xml", report);
		}else{
			result.put("result", false);
			if(errorInfo!=null){
				result.put("errorInfo", errorInfo);
			}
		}
		writeObjectToJson(resp, result);
	}
	
	@Override
	public String url() {
		return "/import";
	}
}
