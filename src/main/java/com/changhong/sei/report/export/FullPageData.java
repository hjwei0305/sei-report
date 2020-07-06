package com.changhong.sei.report.export;

import com.changhong.sei.report.builds.webpaging.Page;

import java.util.List;

/**
 * @desc：全部页的数据
 * @author：zhaohz
 * @date：2020/7/6 15:54
 */
public class FullPageData {
	private int totalPages;
	private int columnMargin;
	private List<List<Page>> pageList;
	
	public FullPageData(int totalPages, int columnMargin,List<List<Page>> pageList) {
		this.totalPages = totalPages;
		this.columnMargin = columnMargin;
		this.pageList = pageList;
	}
	public int getColumnMargin() {
		return columnMargin;
	}
	public List<List<Page>> getPageList() {
		return pageList;
	}
	public int getTotalPages() {
		return totalPages;
	}
}
