package com.changhong.sei.report.font.couriernew;

import com.changhong.sei.report.export.pdf.font.FontRegister;

public class CourierNewFontRegister implements FontRegister {

	public String getFontName() {
		return "Courier New";
	}

	public String getFontPath() {
		return "font/COUR.TTF";
	}
}
