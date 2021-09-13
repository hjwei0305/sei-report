package com.changhong.sei.report.definition;

import com.changhong.sei.report.builds.Dataset;
import com.changhong.sei.report.definition.datasource.DatasourceDefinition;
import com.changhong.sei.report.definition.searchform.RenderContext;
import com.changhong.sei.report.definition.searchform.SearchForm;
import com.changhong.sei.report.enums.Alignment;
import com.changhong.sei.report.enums.Band;
import com.changhong.sei.report.export.html.SearchFormData;
import com.changhong.sei.report.expression.model.Report;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.model.Column;
import com.changhong.sei.report.model.Row;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.*;

/**
 * @desc：报表模板Vo
 * @author：HASEE
 * @date：2020/6/30 10:59
 */
public class ReportDefinition implements Serializable{
	private static final long serialVersionUID = 5934291400824773809L;
	/**
	 * 报表全名，包含前后缀
	 */
	private String reportFullName;
	/**
	 * 纸张
	 */
	private Paper paper;
	/**
	 * 根元素模板
	 */
	private CellDefinition rootCell;
	/**
	 * 表头模板
	 */
	private HeaderFooterDefinition header;
	/**
	 * 表脚模板
	 */
	private HeaderFooterDefinition footer;
	/**
	 * 查询面板
	 */
	private SearchForm searchForm;
	/**
	 * 元素模板集合
	 */
	private List<CellDefinition> cells;
	/**
	 * 行模板集合
	 */
	private List<RowDefinition> rows;
	/**
	 * 列模板集合
	 */
	private List<ColumnDefinition> columns;
	/**
	 * 数据源模板集合
	 */
	private List<DatasourceDefinition> datasources;
	/**
	 * 查询面板Xml串
	 */
	private String searchFormXml;
	/**
	 * 样式css串
	 */
	@JsonIgnore
	private String style;

	/**
	 * 根据报表模板生成报表
	 * @return 报表
	 */
	public Report newReport() {
		Report report = new Report();
		report.setReportFullName(reportFullName);
		report.setPaper(paper);
		report.setHeader(header);
		report.setFooter(footer);
		List<Row> reportRows = new ArrayList<Row>();
		List<Column> reportColumns = new ArrayList<Column>();
		report.setRows(reportRows);
		report.setColumns(reportColumns);
		Map<Integer, Row> rowMap=new HashMap<Integer, Row>();
		int headerRowsHeight=0,footerRowsHeight=0,titleRowsHeight=0,summaryRowsHeight=0;
		for (RowDefinition rowDef : rows) {
			Row newRow=rowDef.newRow(reportRows);
			report.insertRow(newRow, rowDef.getRowNumber());
			rowMap.put(rowDef.getRowNumber(), newRow);
			Band band=rowDef.getBand();
			if(band!=null){
				if(band.equals(Band.headerrepeat)){
					report.getHeaderRepeatRows().add(newRow);
					headerRowsHeight+=newRow.getRealHeight();
				}else if(band.equals(Band.footerrepeat)){
					report.getFooterRepeatRows().add(newRow);
					footerRowsHeight+=newRow.getRealHeight();
				}else if(band.equals(Band.title)){
					report.getTitleRows().add(newRow);
					titleRowsHeight+=newRow.getRealHeight();
				}else if(band.equals(Band.summary)){
					report.getSummaryRows().add(newRow);
					summaryRowsHeight+=newRow.getRealHeight();
				}
			}
		}
		report.setRepeatHeaderRowHeight(headerRowsHeight);
		report.setRepeatFooterRowHeight(footerRowsHeight);
		report.setTitleRowsHeight(titleRowsHeight);
		report.setSummaryRowsHeight(summaryRowsHeight);
		Map<Integer, Column> columnMap=new HashMap<Integer, Column>();
		for (ColumnDefinition columnDef : columns) {
			Column newColumn=columnDef.newColumn(reportColumns);
			report.insertColumn(newColumn, columnDef.getColumnNumber());
			columnMap.put(columnDef.getColumnNumber(), newColumn);
		}
		Map<CellDefinition, Cell> cellMap=new HashMap<CellDefinition, Cell>();
		for (CellDefinition cellDef : cells) {
			Cell cell = cellDef.newCell();
			cellMap.put(cellDef, cell);
			Row targetRow=rowMap.get(cellDef.getRowNumber());
			cell.setRow(targetRow);
			targetRow.getCells().add(cell);
			Column targetColumn=columnMap.get(cellDef.getColumnNumber());
			cell.setColumn(targetColumn);
			targetColumn.getCells().add(cell);

			if(cellDef.getLeftParentCell()==null && cellDef.getTopParentCell()==null){
				report.setRootCell(cell);
			}
			report.addCell(cell);
		}
		for (CellDefinition cellDef : cells) {
			Cell targetCell=cellMap.get(cellDef);
			CellDefinition leftParentCellDef=cellDef.getLeftParentCell();
			if(leftParentCellDef!=null){
				targetCell.setLeftParentCell(cellMap.get(leftParentCellDef));
			}else{
				targetCell.setLeftParentCell(null);
			}
			CellDefinition topParentCellDef=cellDef.getTopParentCell();
			if(topParentCellDef!=null){
				targetCell.setTopParentCell(cellMap.get(topParentCellDef));
			}else{
				targetCell.setTopParentCell(null);
			}
		}
		for (CellDefinition cellDef : cells) {
			Cell targetCell=cellMap.get(cellDef);

			List<CellDefinition> rowChildrenCellDefinitions=cellDef.getRowChildrenCells();
			for(CellDefinition childCellDef:rowChildrenCellDefinitions){
				Cell childCell=cellMap.get(childCellDef);
				targetCell.addRowChild(childCell);
			}
			List<CellDefinition> columnChildrenCellDefinitions=cellDef.getColumnChildrenCells();
			for(CellDefinition childCellDef:columnChildrenCellDefinitions){
				Cell childCell=cellMap.get(childCellDef);
				targetCell.addColumnChild(childCell);
			}
		}
		return report;
	}

	public String getStyle() {
		if(style==null){
			style=buildStyle();
		}
		return style;
	}

	private String buildStyle(){
		StringBuffer sb=new StringBuffer();
		for(CellDefinition cell:cells){
			CellStyle cellStyle=cell.getCellStyle();
			sb.append("._").append(cell.getName()).append("{");
			int colWidth=getColumnWidth(cell.getColumnNumber(),cell.getColSpan());
			sb.append("width:").append(colWidth).append("pt;");
			Alignment align=cellStyle.getAlign();
			if(align!=null){
				sb.append("text-align:").append(align.name()).append(";");
			}
			Alignment valign=cellStyle.getValign();
			if(valign!=null){
				sb.append("vertical-align:").append(valign.name()).append(";");
			}
			Float lineHeight=cellStyle.getLineHeight();
			if(Objects.nonNull(lineHeight)){
				sb.append("line-height:").append(lineHeight).append(";");
			}
			String bgcolor=cellStyle.getBgcolor();
			if(StringUtils.isNotBlank(bgcolor)){
				sb.append("background-color:rgb(").append(bgcolor).append(");");
			}
			String fontFamilty=cellStyle.getFontFamily();
			if(StringUtils.isNotBlank(fontFamilty)){
				sb.append("font-family:").append(fontFamilty).append(";");
			}
			int fontSize=cellStyle.getFontSize();
			sb.append("font-size:").append(fontSize).append("pt;");
			String foreColor=cellStyle.getForecolor();
			if(StringUtils.isNotBlank(foreColor)){
				sb.append("color:rgb(").append(foreColor).append(");");
			}
			Boolean bold=cellStyle.getBold(),italic=cellStyle.getItalic(),underline=cellStyle.getUnderline();
			if(bold!=null && bold){
				sb.append("font-weight:bold;");
			}
			if(italic!=null && italic){
				sb.append("font-style:italic;");
			}
			if(underline!=null && underline){
				sb.append("text-decoration:underline;");
			}
			Border border=cellStyle.getLeftBorder();
			if(border!=null){
				sb.append("border-left:").append(border.getStyle().name()).append(" ").append(border.getWidth()).append("px rgb(").append(border.getColor()).append(");");
			}
			border=cellStyle.getRightBorder();
			if(border!=null){
				sb.append("border-right:").append(border.getStyle().name()).append(" ").append(border.getWidth()).append("px rgb(").append(border.getColor()).append(");");
			}
			border=cellStyle.getTopBorder();
			if(border!=null){
				sb.append("border-top:").append(border.getStyle().name()).append(" ").append(border.getWidth()).append("px rgb(").append(border.getColor()).append(");");
			}
			border=cellStyle.getBottomBorder();
			if(border!=null){
				sb.append("border-bottom:").append(border.getStyle().name()).append(" ").append(border.getWidth()).append("px rgb(").append(border.getColor()).append(");");
			}
			sb.append("}");
		}
		return sb.toString();
	}

	public SearchFormData buildSearchFormData(Map<String, Dataset> datasetMap, Map<String, Object> parameters){
		if(searchForm==null){
			return null;
		}
		RenderContext context=new RenderContext(datasetMap,parameters);
		SearchFormData data=new SearchFormData();
		data.setFormPosition(searchForm.getFormPosition());
		data.setHtml(searchForm.toHtml(context));
		data.setJs(searchForm.toJs(context));
		data.setSearchFormXml(searchFormXml);
		return data;
	}

	private int getColumnWidth(int columnNumber,int colSpan){
		int width=0;
		if(colSpan>0)colSpan--;
		int start=columnNumber,end=start+colSpan;
		for(int i=start;i<=end;i++){
			for(ColumnDefinition col:columns){
				if(col.getColumnNumber()==i){
					width+=col.getWidth();
				}
			}
		}
		return width;
	}

	public String getReportFullName() {
		return reportFullName;
	}

	public void setReportFullName(String reportFullName) {
		this.reportFullName = reportFullName;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public CellDefinition getRootCell() {
		return rootCell;
	}

	public void setRootCell(CellDefinition rootCell) {
		this.rootCell = rootCell;
	}

	public HeaderFooterDefinition getHeader() {
		return header;
	}

	public void setHeader(HeaderFooterDefinition header) {
		this.header = header;
	}

	public HeaderFooterDefinition getFooter() {
		return footer;
	}

	public void setFooter(HeaderFooterDefinition footer) {
		this.footer = footer;
	}

	public SearchForm getSearchForm() {
		return searchForm;
	}

	public void setSearchForm(SearchForm searchForm) {
		this.searchForm = searchForm;
	}

	public List<RowDefinition> getRows() {
		return rows;
	}

	public void setRows(List<RowDefinition> rows) {
		this.rows = rows;
	}

	public List<ColumnDefinition> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}

	public List<CellDefinition> getCells() {
		return cells;
	}

	public void setCells(List<CellDefinition> cells) {
		this.cells = cells;
	}
	public void setDatasources(List<DatasourceDefinition> datasources) {
		this.datasources = datasources;
	}
	public List<DatasourceDefinition> getDatasources() {
		return datasources;
	}
	public String getSearchFormXml() {
		return searchFormXml;
	}
	public void setSearchFormXml(String searchFormXml) {
		this.searchFormXml = searchFormXml;
	}
}