package com.changhong.sei.report.font.timesnewroman;

import com.changhong.sei.report.export.pdf.font.FontRegister;

public class TimesNewRomanFontRegister implements FontRegister {

	public String getFontName() {
		return "Times New Roman";
	}

	public String getFontPath() {
		return "font/TIMES.TTF";
	}
}
