package com.changhong.sei.report.export;

import java.io.OutputStream;
import java.util.Map;

/**
 * @desc：到处配置
 * @author：zhaohz
 * @date：2020/7/6 15:50
 */
public class ExportConfigureImpl implements ExportConfigure {
	private String file;
	private OutputStream outputStream;
	private Map<String,Object> parameters;
	public ExportConfigureImpl(String file,Map<String,Object> parameters,OutputStream outputStream) {
		this.file=file;
		this.parameters=parameters;
		this.outputStream=outputStream;
	}
	public OutputStream getOutputStream() {
		return outputStream;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public String getFile() {
		return file;
	}
}
