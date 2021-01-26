package com.changhong.sei.report.font.yahei;

import com.changhong.sei.report.export.pdf.font.FontRegister;

public class YaheiFontRegister implements FontRegister {

	public String getFontName() {
		return "微软雅黑";
	}

	public String getFontPath() {
		return "font/msyh.ttc";
	}
}
