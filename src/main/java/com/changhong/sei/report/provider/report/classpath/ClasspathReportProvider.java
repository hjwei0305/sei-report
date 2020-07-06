package com.changhong.sei.report.provider.report.classpath;

import com.changhong.sei.report.exception.ReportException;
import com.changhong.sei.report.provider.report.ReportFile;
import com.changhong.sei.report.provider.report.ReportProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @desc：报表存储源为classpath
 * @author：zhaohz
 * @date：2020/7/6 16:00
 */
public class ClasspathReportProvider implements ReportProvider, ApplicationContextAware {
	private ApplicationContext applicationContext;
	@Override
	public InputStream loadReport(String file) {
		Resource resource=applicationContext.getResource(file);
		try {
			return resource.getInputStream();
		} catch (IOException e) {
			String newFileName=null;
			if(file.startsWith("classpath:")){
				newFileName="classpath*:"+file.substring(10,file.length());
			}else if(file.startsWith("classpath*:")){
				newFileName="classpath:"+file.substring(11,file.length());
			}
			if(newFileName!=null){
				try{
					return applicationContext.getResource(file).getInputStream();					
				}catch(IOException ex){
					throw new ReportException(e);
				}				
			}
			throw new ReportException(e);
		}
	}
	
	@Override
	public String getPrefix() {
		return "classpath";
	}
	
	@Override
	public void deleteReport(String file) {
	}
	
	@Override
	public void saveReport(String file,String content) {
	}
	@Override
	public List<ReportFile> getReportFiles() {
		return null;
	}
	
	@Override
	public boolean disabled() {
		return false;
	}
	
	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
}
