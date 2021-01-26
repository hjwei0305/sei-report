package com.changhong.sei.report.font.kaiti;

import com.changhong.sei.report.export.pdf.font.FontRegister;

public class KaiTiFontRegister implements FontRegister {

	public String getFontName() {
		return "楷体";
	}

	public String getFontPath() {
		return "font/SIMKAI.TTF";
	}
}
