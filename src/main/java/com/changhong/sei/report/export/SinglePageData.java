package com.changhong.sei.report.export;

import com.changhong.sei.report.builds.webpaging.Page;

import java.util.List;

/**
 * @desc：单个页的数据
 * @author：zhaohz
 * @date：2020/7/6 15:55
 */
public class SinglePageData {
	private int totalPages;
	private int pageIndex;
	private List<Page> pages;
	private int columnMargin;
	
	public SinglePageData(int totalPages, int columnMargin,List<Page> pages) {
		this.totalPages = totalPages;
		this.columnMargin=columnMargin;
		this.pages = pages;
	}
	public SinglePageData(int totalPages, int pageIndex, int columnMargin,List<Page> pages) {
		this.totalPages = totalPages;
		this.pageIndex = pageIndex;
		this.columnMargin=columnMargin;
		this.pages = pages;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public List<Page> getPages() {
		return pages;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public int getColumnMargin() {
		return columnMargin;
	}
}
