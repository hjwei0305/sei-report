package com.changhong.sei.report.model;

import com.changhong.sei.report.chart.ChartData;
import com.changhong.sei.report.dto.TableDto;
import com.changhong.sei.report.export.html.SearchFormData;

import java.io.Serializable;
import java.util.Collection;

public class PageReport implements Serializable {
    private Page page;
    private String content;
    private TableDto jsonContent;
    private String style;
    private int column;
    /*private int totalPage;
    private int pageIndex;*/
    private String reportAlign;
    private Collection<ChartData> chartDatas;
    private int htmlIntervalRefreshValue;
    private SearchFormData searchFormData;
    private String type;//1-预览,2-分页预览,3-物理分页预览

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TableDto getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(TableDto jsonContent) {
        this.jsonContent = jsonContent;
    }
    /*public int getTotalPage() {
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
    }*/

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    /*public int getTotalPageWithCol() {
        int totalPageWithCol = this.totalPage;
        if (this.column > 0) {
            totalPageWithCol = this.totalPage / this.column;
            int m = this.totalPage % this.column;
            if (m > 0) {
                ++totalPageWithCol;
            }
        }

        return totalPageWithCol;
    }*/

    public String getReportAlign() {
        return this.reportAlign;
    }

    public void setReportAlign(String reportAlign) {
        this.reportAlign = reportAlign;
    }

    public Collection<ChartData> getChartDatas() {
        return this.chartDatas;
    }

    public void setChartDatas(Collection<ChartData> chartDatas) {
        this.chartDatas = chartDatas;
    }

    public int getHtmlIntervalRefreshValue() {
        return this.htmlIntervalRefreshValue;
    }

    public void setHtmlIntervalRefreshValue(int htmlIntervalRefreshValue) {
        this.htmlIntervalRefreshValue = htmlIntervalRefreshValue;
    }

    public SearchFormData getSearchFormData() {
        return this.searchFormData;
    }

    public void setSearchFormData(SearchFormData searchFormData) {
        this.searchFormData = searchFormData;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
