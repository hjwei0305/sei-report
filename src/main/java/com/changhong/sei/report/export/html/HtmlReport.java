package com.changhong.sei.report.export.html;

import com.changhong.sei.report.chart.ChartData;

import java.util.Collection;

/**
 * @desc：htlm格式报表输出实体
 * @author：zhaohz
 * @date：2020/7/6 14:21
 */
public class HtmlReport {
	private String content;
	private String style;
	private int totalPage;
	private int pageIndex;
	private int column;
	private String reportAlign;
	private Collection<ChartData> chartDatas;
	private int htmlIntervalRefreshValue;
	private SearchFormData searchFormData;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}

	public int getTotalPageWithCol() {
		int totalPageWithCol=totalPage;
		if(column>0){
			totalPageWithCol=totalPage / column;
			int m=totalPage % column;
			if(m>0){
				totalPageWithCol++;
			}
		}
		return totalPageWithCol;
	}
	public String getReportAlign() {
		return reportAlign;
	}
	public void setReportAlign(String reportAlign) {
		this.reportAlign = reportAlign;
	}
	public Collection<ChartData> getChartDatas() {
		return chartDatas;
	}
	public void setChartDatas(Collection<ChartData> chartDatas) {
		this.chartDatas = chartDatas;
	}
	public int getHtmlIntervalRefreshValue() {
		return htmlIntervalRefreshValue;
	}
	public void setHtmlIntervalRefreshValue(int htmlIntervalRefreshValue) {
		this.htmlIntervalRefreshValue = htmlIntervalRefreshValue;
	}
	public SearchFormData getSearchFormData() {
		return searchFormData;
	}
	public void setSearchFormData(SearchFormData searchFormData) {
		this.searchFormData = searchFormData;
	}
}
