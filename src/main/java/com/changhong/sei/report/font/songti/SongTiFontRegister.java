package com.changhong.sei.report.font.songti;

import com.changhong.sei.report.export.pdf.font.FontRegister;

public class SongTiFontRegister implements FontRegister {

	public String getFontName() {
		return "宋体";
	}

	public String getFontPath() {
		return "font/SIMSUN.TTC";
	}
}
