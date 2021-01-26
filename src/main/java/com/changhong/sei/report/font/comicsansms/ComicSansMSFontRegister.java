package com.changhong.sei.report.font.comicsansms;

import com.changhong.sei.report.export.pdf.font.FontRegister;

public class ComicSansMSFontRegister implements FontRegister {

	public String getFontName() {
		return "Comic Sans MS";
	}

	public String getFontPath() {
		return "font/COMIC.TTF";
	}
}
