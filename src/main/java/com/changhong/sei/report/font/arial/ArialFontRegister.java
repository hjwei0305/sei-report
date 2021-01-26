package com.changhong.sei.report.font.arial;

import com.changhong.sei.report.export.pdf.font.FontRegister;

/**
 * @desc：
 * @author：zhaohz
 * @date：2020/7/7 10:22
 */
public class ArialFontRegister implements FontRegister {

	public String getFontName() {
		return "Arial";
	}

	public String getFontPath() {
		return "font/ARIAL.TTF";
	}
}
