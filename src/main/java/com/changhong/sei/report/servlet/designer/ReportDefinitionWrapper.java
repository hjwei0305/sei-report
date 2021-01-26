package com.changhong.sei.report.servlet.designer;

import com.changhong.sei.report.definition.*;
import com.changhong.sei.report.definition.datasource.DatasourceDefinition;
import com.changhong.sei.report.definition.searchform.SearchForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：原ureport报表展示的模板vo
 * @author：zhaohz
 * @date：2020/7/7 9:40
 */
public class ReportDefinitionWrapper {
	private Paper paper;
	private HeaderFooterDefinition header;
	private HeaderFooterDefinition footer;
	private SearchForm searchForm;
	private String searchFormXml;
	private List<RowDefinition> rows;
	private List<ColumnDefinition> columns;
	private List<DatasourceDefinition> datasources;
	private Map<String, CellDefinition> cellsMap=new HashMap<String, CellDefinition>();
	public ReportDefinitionWrapper(ReportDefinition report) {
		this.paper=report.getPaper();
		this.header=report.getHeader();
		this.footer=report.getFooter();
		this.searchForm=report.getSearchForm();
		this.searchFormXml=report.getSearchFormXml();
		this.rows=report.getRows();
		this.columns=report.getColumns();
		this.datasources=report.getDatasources();
		for(CellDefinition cell:report.getCells()){
			cellsMap.put(cell.getRowNumber()+","+cell.getColumnNumber(), cell);
		}
	}
	public List<ColumnDefinition> getColumns() {
		return columns;
	}
	public List<DatasourceDefinition> getDatasources() {
		return datasources;
	}
	public HeaderFooterDefinition getFooter() {
		return footer;
	}
	public HeaderFooterDefinition getHeader() {
		return header;
	}
	public Paper getPaper() {
		return paper;
	}
	public SearchForm getSearchForm() {
		return searchForm;
	}
	public void setSearchForm(SearchForm searchForm) {
		this.searchForm = searchForm;
	}
	public Map<String, CellDefinition> getCellsMap() {
		return cellsMap;
	}
	public List<RowDefinition> getRows() {
		return rows;
	}
	public String getSearchFormXml() {
		return searchFormXml;
	}
	public void setSearchFormXml(String searchFormXml) {
		this.searchFormXml = searchFormXml;
	}
}
