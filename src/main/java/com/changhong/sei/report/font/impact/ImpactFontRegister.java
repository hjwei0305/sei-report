package com.changhong.sei.report.font.impact;

import com.changhong.sei.report.export.pdf.font.FontRegister;

public class ImpactFontRegister implements FontRegister {

	public String getFontName() {
		return "Impact";
	}

	public String getFontPath() {
		return "font/IMPACT.TTF";
	}
}
