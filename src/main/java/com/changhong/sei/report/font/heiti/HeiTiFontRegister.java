package com.changhong.sei.report.font.heiti;

import com.changhong.sei.report.export.pdf.font.FontRegister;

public class HeiTiFontRegister implements FontRegister {

	public String getFontName() {
		return "黑体";
	}

	public String getFontPath() {
		return "font/SIMHEI.TTF";
	}
}
