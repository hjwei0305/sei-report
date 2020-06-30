package com.changhong.sei.report.builds.webpaging;


import com.changhong.sei.report.model.Column;
import com.changhong.sei.report.model.Row;

import java.util.List;

/**
 * @desc：前端分页pageVo
 * @author：zhaohz
 * @date：2020/6/30 10:14
 */
public class Page {
	private List<Row> rows;
	private List<Column> columns;
	private HeaderFooter header;
	private HeaderFooter footer;

	public Page(List<Row> rows, List<Column> columns) {
		this.rows = rows;
		this.columns=columns;
	}
	public List<Row> getRows() {
		return rows;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public HeaderFooter getHeader() {
		return header;
	}
	public void setHeader(HeaderFooter header) {
		this.header = header;
	}
	public HeaderFooter getFooter() {
		return footer;
	}
	public void setFooter(HeaderFooter footer) {
		this.footer = footer;
	}
}
