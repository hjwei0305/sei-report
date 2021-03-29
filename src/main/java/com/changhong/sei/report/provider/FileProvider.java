package com.changhong.sei.report.provider;

import com.changhong.sei.report.exception.ReportException;
import com.changhong.sei.report.provider.report.ReportFile;
import com.changhong.sei.report.provider.report.ReportProvider;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.*;

//@Component
@RefreshScope
public class FileProvider implements ReportProvider,ApplicationContextAware{
	@Value("${ureport.file.provider.name}")
	private String name;
	@Value("${ureport.file.provider.prefix}")
	private String prefix;
	@Value("${ureport.file.provider.dir}")
	private String fileStoreDir;
	@Value("${ureport.file.provider.disabled}")
	private boolean disabled;

	static String defaultPath;
	static {
		/*try {
			defaultPath = ResourceUtils.getFile("classpath:template").getPath();
		} catch (FileNotFoundException e) {*/
		defaultPath = System.getProperty("user.dir") + "/src/main/resources/template";
		//}
	}

	/*@Bean
	public ReportProvider fileReportProvider() {

		return new ReportProvider() {*/
			@Override
			public InputStream loadReport(String file) {
				if (file.startsWith(prefix)) {
					file = file.substring(prefix.length(), file.length());
				}
				String fullPath = fileStoreDir + "/" + file;
				try {
					return new FileInputStream(fullPath);
				} catch (FileNotFoundException e) {
					throw new ReportException(e);
				}
			}

			@Override
			public void deleteReport(String file) {
				if (file.startsWith(prefix)) {
					file = file.substring(prefix.length(), file.length());
				}
				String fullPath = fileStoreDir + "/" + file;
				File f = new File(fullPath);
				if (f.exists()) {
					f.delete();
				}
			}

			@Override
			public List<ReportFile> getReportFiles() {
				File file = new File(fileStoreDir);
				List<ReportFile> list = new ArrayList<ReportFile>();
				for (File f : file.listFiles()) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(f.lastModified());
					list.add(new ReportFile(f.getName(), calendar.getTime()));
				}
				Collections.sort(list, new Comparator<ReportFile>() {
					@Override
					public int compare(ReportFile f1, ReportFile f2) {
						return f2.getUpdateDate().compareTo(f1.getUpdateDate());
					}
				});
				return list;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public void saveReport(String file, String content) {
				if (file.startsWith(prefix)) {
					file = file.substring(prefix.length(), file.length());
				}
				String fullPath = fileStoreDir + "/" + file;
				FileOutputStream outStream = null;
				try {
					outStream = new FileOutputStream(new File(fullPath));
					IOUtils.write(content, outStream, "utf-8");
				} catch (Exception ex) {
					throw new ReportException(ex);
				} finally {
					if (outStream != null) {
						try {
							outStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}

			@Override
			public boolean disabled() {
				return disabled;
			}

			public String getPrefix() {
				return prefix;
			}
		/*};
	}*/

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (StringUtils.isBlank(fileStoreDir)){
			fileStoreDir = defaultPath;
		}
		File file = new File(fileStoreDir);
		if (file.exists()) {
			return;
		}
		if (applicationContext instanceof WebApplicationContext) {
			WebApplicationContext context = (WebApplicationContext) applicationContext;
			ServletContext servletContext = context.getServletContext();
			String basePath = servletContext.getRealPath("/");
			fileStoreDir = basePath + fileStoreDir;
			file = new File(fileStoreDir);
			if (!file.exists()) {
				fileStoreDir = defaultPath;
			}
		}
	}
}
