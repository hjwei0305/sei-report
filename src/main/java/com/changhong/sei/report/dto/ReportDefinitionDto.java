package com.changhong.sei.report.dto;

import com.changhong.sei.report.definition.*;
import com.changhong.sei.report.definition.datasource.DatasourceDefinition;
import com.changhong.sei.report.definition.searchform.SearchForm;
import com.changhong.sei.report.expression.model.Condition;
import com.changhong.sei.report.expression.model.condition.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：报表模板Dto
 * @author：zhaohz
 * @date：2021/3/1 11:23
 */
public class ReportDefinitionDto {
	private Paper paper;
	private HeaderFooterDefinition header;
	private HeaderFooterDefinition footer;
	private SearchForm searchForm;
	private String searchFormXml;
	private List<RowDefinition> rows;
	private List<ColumnDefinition> columns;
	private List<DatasourceDefinition> datasources;
	private Map<String, CellDefinition> cellsMap=new HashMap<String,CellDefinition>();
	public ReportDefinitionDto(ReportDefinition report) {
		this.paper=report.getPaper();
		this.header=report.getHeader();
		this.footer=report.getFooter();
		this.searchForm=report.getSearchForm();
		this.searchFormXml=report.getSearchFormXml();
		this.rows=report.getRows();
		this.columns=report.getColumns();
		this.datasources=report.getDatasources();
		for(CellDefinition cell:report.getCells()){
			List<ConditionPropertyItem> items = cell.getConditionPropertyItems();
			if(!CollectionUtils.isEmpty(items)){
				for(ConditionPropertyItem item:items){
					Condition condition = item.getCondition();
					List<Condition> conditions = item.getConditions();
					initCondition(condition);
					initConditions(conditions);
				}
			}
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

	private void initCondition(Condition condition){
		if(!ObjectUtils.isEmpty(condition)){
			if(condition instanceof BaseCondition){
				((BaseCondition)condition).setOperation(((BaseCondition)condition).getOp().toString());
			}else if(condition instanceof BothExpressionCondition){
				((BothExpressionCondition)condition).setOperation(((BothExpressionCondition)condition).getOp().toString());
			}else if(condition instanceof CellExpressionCondition){
				((CellExpressionCondition)condition).setOperation(((CellExpressionCondition)condition).getOp().toString());
			}else if(condition instanceof CurrentValueExpressionCondition){
				((CurrentValueExpressionCondition)condition).setOperation(((CurrentValueExpressionCondition)condition).getOp().toString());
			}else if(condition instanceof PropertyExpressionCondition){
				((PropertyExpressionCondition)condition).setOperation(((PropertyExpressionCondition)condition).getOp().toString());
			}
		}
	}

	private void initConditions(List<Condition> conditions){
		if(!CollectionUtils.isEmpty(conditions)){
			for (Condition condition:conditions){
				initCondition(condition);
			}
		}
	}
}