package com.changhong.sei.report.export;

import java.io.OutputStream;
import java.util.Map;

/**
 * @desc：导出配置接口
 * @author：zhaohz
 * @date：2020/7/6 15:46
 */
public interface ExportConfigure {
	OutputStream getOutputStream();
	String getFile();
	Map<String,Object> getParameters();
}
