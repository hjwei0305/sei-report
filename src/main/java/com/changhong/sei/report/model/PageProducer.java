package com.changhong.sei.report.model;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.chart.ChartData;
import com.changhong.sei.report.definition.Border;
import com.changhong.sei.report.definition.CellDefinition;
import com.changhong.sei.report.definition.CellStyle;
import com.changhong.sei.report.definition.ReportDefinition;
import com.changhong.sei.report.definition.dataset.DatasetDefinition;
import com.changhong.sei.report.definition.dataset.Parameter;
import com.changhong.sei.report.definition.dataset.SqlDatasetDefinition;
import com.changhong.sei.report.definition.datasource.BuildinDatasourceDefinition;
import com.changhong.sei.report.definition.datasource.DataType;
import com.changhong.sei.report.definition.datasource.DatasourceDefinition;
import com.changhong.sei.report.definition.datasource.JdbcDatasourceDefinition;
import com.changhong.sei.report.definition.value.DatasetValue;
import com.changhong.sei.report.dto.CellDto;
import com.changhong.sei.report.dto.RowDto;
import com.changhong.sei.report.dto.TableDto;
import com.changhong.sei.report.enums.Alignment;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.BindDataListExpressionData;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.utils.ExpressionUtils;
import com.changhong.sei.report.utils.ProcedureUtils;
import com.changhong.sei.report.utils.Utils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageProducer {

	public Page produce(ReportDefinition reportDefinition, Map<String, Object> parameters, int pageIndex, int rows) throws SQLException, ServletException {
		Page page = new Page(pageIndex, rows);
		page.setRecords(0);
		Cell cell = rootDataCell(reportDefinition);

		if(!ObjectUtils.isEmpty(cell)){
			List<DatasourceDefinition> datasourceList = reportDefinition.getDatasources();
			String datasetName = ((DatasetValue)cell.getValue()).getDatasetName();
			flag: for(DatasourceDefinition datasource:datasourceList){
				for(DatasetDefinition datasetDefinition:datasource.getDatasets()){
					if(datasetName.equals(datasetDefinition.getName())){
						Map<String, Object> parameterMap = buildParameters(((SqlDatasetDefinition) datasetDefinition).getParameters(), parameters);
						//获取查总行数的参数
						Map<String, Object> params = noPageParam(parameterMap);
						if (parameterMap.containsKey("startRow")&&parameterMap.containsKey("rows")) {
							Connection conn = buildConnect(datasource);
							String s = sqlForUse((SqlDatasetDefinition) datasetDefinition, params);
							String sql = "select count(1) as records from (" + s.replaceAll("limit :startRow,:rows","") + ")";
							String databaseProductName = conn.getMetaData().getDatabaseProductName().trim();
							if (!"Oracle".equals(databaseProductName)) {
								sql +=  " as sei_count";
							}
							System.out.println("sql:" + sql);
							List<Map<String, Object>> result = getResult(sql, params, conn);
							int records = Integer.valueOf(result.get(0).get("records").toString());
							page.setRecords(records);
							System.out.println("total:" + page.getTotal());
							break flag;
						}else{
							throw new ServletException("请配置数据集'"+datasetName+"'的分页参数'startRow'和'rows'!");
						}
					}
				}
			}
		}
		return page;
	}

	private Cell rootDataCell(ReportDefinition reportDefinition){
		Cell cell = null;
		for (CellDefinition cellDef : reportDefinition.getCells()) {
			if(cellDef.getLeftParentCell()==null && cellDef.getTopParentCell()==null){
				if(cellDef.getValue() instanceof DatasetValue) {
					cell = newCell(cellDef);
				}
			}
		}
		return cell;
	}

	private Connection buildConnect(DatasourceDefinition datasource) {
		Connection conn=null;
		if(datasource instanceof JdbcDatasourceDefinition) {
			String dsName = datasource.getName();
			JdbcDatasourceDefinition ds = (JdbcDatasourceDefinition) datasource;
			try {
				conn = DriverManager.getConnection(ds.getUrl(), ds.getUsername(), ds.getPassword());
				if(conn==null){
					throw new ReportComputeException("JDBC datasource ["+dsName+"] not exist.");
				}
			} catch (SQLException e) {
				throw new ReportComputeException("JDBC datasource ["+dsName+"] not exist.");
			}
		}else if(datasource instanceof BuildinDatasourceDefinition){
			String dsName=datasource.getName();
			try{
				conn = Utils.getBuildinConnection(dsName);
				if(conn==null){
					throw new ReportComputeException("Buildin datasource ["+dsName+"] not exist.");
				}
			}catch (Exception e){
				throw new ReportComputeException("Buildin datasource ["+dsName+"] not exist.");
			}
		}
		return conn;
	}

	private List<Map<String,Object>> getResult(String sql, Map<String, Object> parameters, Connection conn) throws ServletException {
		try{
			if(ProcedureUtils.isProcedure(sql)){
				return ProcedureUtils.procedureQuery(sql, parameters, conn);
			}else{
				DataSource dataSource=new SingleConnectionDataSource(conn,false);
				NamedParameterJdbcTemplate jdbc=new NamedParameterJdbcTemplate(dataSource);
				return jdbc.queryForList(sql, parameters);
			}
		}catch (Exception e){
			throw new ServletException(e.toString());
		}finally {
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	private String sqlForUse(SqlDatasetDefinition dataset, Map<String, Object> parameters) {
		String sqlForUse = dataset.getSql();
		Map<String, Object> parameterMap = buildParameters(dataset.getParameters(), parameters);
		Expression sqlExpression = null;
		if(sqlForUse.startsWith(ExpressionUtils.EXPR_PREFIX) && sqlForUse.endsWith(ExpressionUtils.EXPR_SUFFIX)){
			String s=sqlForUse.substring(2,sqlForUse.length()-1);
			sqlExpression=ExpressionUtils.parseExpression(s);
		}
		Context context=new Context(null,parameterMap);
		if(sqlExpression!=null){
			sqlForUse=executeSqlExpr(sqlExpression, context);
		}else{
			Pattern pattern=Pattern.compile("\\$\\{.*?\\}");
			Matcher matcher=pattern.matcher(sqlForUse);
			while(matcher.find()){
				String substr=matcher.group();
				String sqlExpr=substr.substring(2,substr.length()-1);
				Expression expr=ExpressionUtils.parseExpression(sqlExpr);
				String result=executeSqlExpr(expr, context);
				sqlForUse=sqlForUse.replace(substr, result);
			}
		}
		return sqlForUse;
	}

	private String executeSqlExpr(Expression sqlExpr,Context context){
		String sqlForUse=null;
		ExpressionData<?> exprData=sqlExpr.execute(null, null, context);
		if(exprData instanceof ObjectExpressionData){
			ObjectExpressionData data=(ObjectExpressionData)exprData;
			Object obj=data.getData();
			if(obj!=null){
				String s=obj.toString();
				s=s.replaceAll("\\\\", "");
				sqlForUse=s;
			}
		}
		return sqlForUse;
	}

	private Map<String,Object> buildParameters(List<Parameter> parameters, Map<String,Object> params){
		Map<String,Object> map=new HashMap<String,Object>();
		for(Parameter param:parameters){
			String name=param.getName();
			DataType datatype=param.getType();
			Object value=param.getDefaultValue();
			if(params!=null && params.containsKey(name)){
				value=params.get(name);
			}
			map.put(name, datatype.parse(value));
		}
		return map;
	}

	private Map<String, Object> noPageParam(Map<String, Object> parameters){
		Map<String, Object> params = new HashMap<>();
		for(Map.Entry<String, Object> entry:parameters.entrySet()){
			if(!"startRow".equals(entry.getKey())&&!"rows".equals(entry.getKey())){
				params.put(entry.getKey(), entry.getValue());
			}
		}
		return params;
	}

	private Cell newCell(CellDefinition cellDef){
		Cell cell=new Cell();
		cell.setValue(cellDef.getValue());
		cell.setName(cellDef.getName());
		cell.setRowSpan(cellDef.getRowSpan());
		cell.setColSpan(cellDef.getColSpan());
		cell.setExpand(cellDef.getExpand());
		cell.setCellStyle(cellDef.getCellStyle());
		cell.setNewBlankCellsMap(cellDef.getNewBlankCellsMap());
		cell.setIncreaseSpanCellNames(cellDef.getIncreaseSpanCellNames());
		cell.setNewCellNames(cellDef.getNewCellNames());
		cell.setDuplicateRange(cellDef.getDuplicateRange());
		cell.setLinkParameters(cellDef.getLinkParameters());
		cell.setLinkTargetWindow(cellDef.getLinkTargetWindow());
		cell.setLinkUrl(cellDef.getLinkUrl());
		cell.setConditionPropertyItems(cellDef.getConditionPropertyItems());
		cell.setFillBlankRows(cellDef.getFillBlankRows());
		if (cellDef.getFillBlankRows()) {
			cell.setMultiple(cellDef.getMultiple());
		}
		cell.setLinkUrlExpression(cellDef.getLinkUrlExpression());
		return cell;
	}

	public TableDto buildTable(Context context, List<Row> rows, List<Column> columns, Map<Row, Map<Column, Cell>> cellMap, boolean breakPage,boolean forPage) {
		TableDto table = new TableDto();
		int tableWidth=buildTableWidth(columns);
		table.setWidth(tableWidth);
		String bgImage=context.getReport().getPaper().getBgImage();
		if(!StringUtils.isEmpty(bgImage)){
			table.setBgImage(bgImage);
		}
		Row row;
		Cell cell;
		RowDto ro;
		CellDto cel;
		List<RowDto> rowList = new ArrayList<>();
		List<CellDto> cellList;
		int colSize=columns.size();
		int rowSize=rows.size();
		for(int i=0;i<rowSize;i++){
			row=rows.get(i);
			ro = null;
			if(!forPage && row.isForPaging()){
				continue;
			}
			int height=row.getRealHeight();
			if(height<1){
				continue;
			}
			ro = new RowDto();
			ro.setHeight(height);
			cellList = new ArrayList<>();
			for(int j=0;j<colSize;j++){
				Column col=columns.get(j);
				cell = null;
				if(cellMap.containsKey(row)){
					Map<Column,Cell> colMap=cellMap.get(row);
					if(colMap.containsKey(col)){
						cell=colMap.get(col);
					}
				}
				if(cell==null || (!forPage && cell.isForPaging())){
					continue;
				}
				cel = new CellDto();
				int colSpan = cell.getColSpan();
				int rowSpan = cell.getRowSpan();
				cel.setColSpan(colSpan);
				cel.setRowSpan(rowSpan);
				if(forPage){
					cel.setRowSpan(cell.getPageRowSpan());
				}
				cel.setName(cell.getName());
				String style=buildCustomStyle(cell);
				cel.setStyle(style);
				String linkURL=cell.getLinkUrl();
				if(!StringUtils.isEmpty(linkURL)){
					Expression urlExpression=cell.getLinkUrlExpression();
					if(urlExpression!=null){
						ExpressionData<?> exprData=urlExpression.execute(cell, cell, context);
						if(exprData instanceof BindDataListExpressionData){
							BindDataListExpressionData listExprData=(BindDataListExpressionData)exprData;
							List<BindData> bindDataList=listExprData.getData();
							if(bindDataList!=null && bindDataList.size()>0){
								Object data=bindDataList.get(0).getValue();
								if(data!=null){
									linkURL=data.toString();
								}
							}
						}else if(exprData instanceof ObjectExpressionData){
							ObjectExpressionData objExprData=(ObjectExpressionData)exprData;
							Object data=objExprData.getData();
							if(data!=null){
								linkURL=data.toString();
							}
						}else if(exprData instanceof ObjectListExpressionData){
							ObjectListExpressionData objListExprData=(ObjectListExpressionData)exprData;
							List<?> list=objListExprData.getData();
							if(list!=null && list.size()>0){
								Object data=list.get(0);
								if(data!=null){
									linkURL=data.toString();
								}
							}
						}
					}
					String urlParameter=cell.buildLinkParameters(context);
					if(!StringUtils.isEmpty(urlParameter)) {
						if(linkURL.indexOf("?")==-1){
							linkURL+="?"+urlParameter;
						}else{
							linkURL+="&"+urlParameter;
						}
					}
					String target=cell.getLinkTargetWindow();
					if(StringUtils.isEmpty(target))target="_self";
					cel.setLinkUrl(linkURL);
					cel.setLinkTarget(target);
				}
				Object obj=(cell.getFormatData()== null) ? "" : cell.getFormatData();
				if(obj instanceof Image){
					Image img=(Image)obj;
					String path=img.getPath();
					String imageType="image/png";
					if(!StringUtils.isEmpty(path)){
						path=path.toLowerCase();
						if(path.endsWith(".jpg") || path.endsWith(".jpeg")){
							imageType="image/jpeg";
						}else if(path.endsWith(".gif")){
							imageType="image/gif";
						}
					}
					cel.setContent("<img src=\"data:"+imageType+";base64,"+img.getBase64Data()+"\">");
				}else if(obj instanceof ChartData){
					ChartData chartData=(ChartData)obj;
					String canvasId=chartData.getId();
					int width=col.getWidth()-2;
					if(colSpan>0){
						width=buildWidth(columns,j,colSpan)-2;
					}
					if(rowSpan>0){
						height=buildHeight(rows,i,rowSpan)-2;
					}else{
						height-=2;
					}
					StringBuffer chart = new StringBuffer("<div style=\"position: relative;width:"+width+"pt;height:"+height+"pt\">");
					chart.append("<canvas id=\""+canvasId+"\" style=\"width:"+width+"px !important;height:"+height+"px !important\"></canvas>");
					chart.append("</div>");
					cel.setContent(chart.toString());
				}else{
					String text=obj.toString();
					text= StringEscapeUtils.escapeHtml4(text);
					text=text.replaceAll("\r\n", "<br>");
					text=text.replaceAll("\n", "<br>");
					text=text.replaceAll(" ", "&nbsp;");
					cel.setContent(text);
				}
				cellList.add(cel);
			}
			ro.setCellList(cellList);
			rowList.add(ro);
			table.setRowList(rowList);
		}
		return table;
	}

	public TableDto buildTableWithColumnMargin(Context context, List<com.changhong.sei.report.builds.webpaging.Page> pages, int columnMargin){
		TableDto jsonContent = new TableDto();
		int pageSize = pages.size();
		List<TableDto> tableList = new ArrayList<>();
		TableDto table = null;
		int singleTableWidth = buildTableWidth(((com.changhong.sei.report.builds.webpaging.Page)pages.get(0)).getColumns());
		int tableWidth = singleTableWidth * pageSize + columnMargin * (pageSize - 1);
		String bgImage = context.getReport().getPaper().getBgImage();
		jsonContent.setPageBreak("page-break");
		jsonContent.setWidth(tableWidth);
		jsonContent.setBgImage(bgImage);
		for(int i=0;i<pageSize;i++){
			com.changhong.sei.report.builds.webpaging.Page pag = pages.get(i);
			table = buildTable(context, pag.getRows(), pag.getColumns(), context.getReport().getRowColCellMap(), false, true);
			tableList.add(table);
		}
		jsonContent.setTableList(tableList);
		return jsonContent;
	}

	private int buildTableWidth(List<Column> columns){
		int width=0;
		for(Column col:columns){
			width+=col.getWidth();
		}
		return width;
	}

	private String buildCustomStyle(Cell cell){
		CellStyle style=cell.getCustomCellStyle();
		CellStyle rowStyle=cell.getRow().getCustomCellStyle();
		CellStyle colStyle=cell.getColumn().getCustomCellStyle();
		if(style==null && rowStyle==null && colStyle==null)return "";
		StringBuilder sb=new StringBuilder();
		String forecolor=null;
		if(style!=null){
			forecolor=style.getForecolor();
		}
		if(rowStyle!=null){
			forecolor=rowStyle.getForecolor();
		}
		if(colStyle!=null){
			forecolor=colStyle.getForecolor();
		}
		if(!StringUtils.isEmpty(forecolor)){
			sb.append("color:rgb("+forecolor+");");
		}
		String bgcolor=null;
		if(style!=null){
			bgcolor=style.getBgcolor();
		}
		if(rowStyle!=null){
			bgcolor=rowStyle.getBgcolor();
		}
		if(colStyle!=null){
			bgcolor=colStyle.getBgcolor();
		}
		if(!StringUtils.isEmpty(bgcolor)){
			sb.append("background-color:rgb("+bgcolor+");");
		}
		String fontFamily=null;
		if(style!=null){
			fontFamily=style.getFontFamily();
		}
		if(rowStyle!=null){
			fontFamily=rowStyle.getFontFamily();
		}
		if(colStyle!=null){
			fontFamily=colStyle.getFontFamily();
		}
		if(!StringUtils.isEmpty(fontFamily)){
			sb.append("font-family:"+fontFamily+";");
		}
		int fontSize=0;
		if(Objects.nonNull(style) && Objects.nonNull(style.getFontSize())){
			fontSize=style.getFontSize();
		}
		if(rowStyle!=null){
			fontSize=rowStyle.getFontSize();
		}
		if(colStyle!=null){
			fontSize=colStyle.getFontSize();
		}
		if(fontSize>0){
			sb.append("font-size:"+fontSize+"pt;");
		}
		Boolean bold=null;
		if(style!=null){
			bold=style.getBold();
		}
		if(rowStyle!=null){
			bold=rowStyle.getBold();
		}
		if(colStyle!=null){
			bold=colStyle.getBold();
		}
		if(bold!=null){
			if(bold){
				sb.append("font-weight:bold;");
			}else{
				sb.append("font-weight:normal;");
			}
		}
		Boolean italic=null;
		if(style!=null){
			italic=style.getItalic();
		}
		if(rowStyle!=null){
			italic=rowStyle.getItalic();
		}
		if(colStyle!=null){
			italic=colStyle.getItalic();
		}
		if(italic!=null){
			if(italic){
				sb.append("font-style:italic;");
			}else{
				sb.append("font-style:normal;");

			}
		}
		Boolean underline=null;
		if(style!=null){
			underline=style.getUnderline();
		}
		if(rowStyle!=null){
			underline=rowStyle.getUnderline();
		}
		if(colStyle!=null){
			underline=colStyle.getUnderline();
		}
		if(underline!=null){
			if(underline){
				sb.append("text-decoration:underline;");
			}else{
				sb.append("text-decoration:none;");
			}
		}
		Alignment align=null;
		if(style!=null){
			align=style.getAlign();
		}
		if(rowStyle!=null){
			align=rowStyle.getAlign();
		}
		if(colStyle!=null){
			align=colStyle.getAlign();
		}
		if(align!=null){
			sb.append("text-align:"+align.name()+";");
		}
		Alignment valign=null;
		if(style!=null){
			valign=style.getValign();
		}
		if(rowStyle!=null){
			valign=rowStyle.getValign();
		}
		if(colStyle!=null){
			valign=colStyle.getValign();
		}
		if(valign!=null){
			sb.append("vertical-align:"+valign.name()+";");
		}
		Border border=null;
		if(style!=null){
			border=style.getLeftBorder();
		}
		if(border!=null){
			sb.append("border-left:"+border.getStyle().name()+" "+border.getWidth()+"px rgb("+border.getColor()+");");
		}
		Border rightBorder=null;
		if(style!=null){
			rightBorder=style.getRightBorder();
		}
		if(rightBorder!=null){
			sb.append("border-right:"+rightBorder.getStyle().name()+" "+rightBorder.getWidth()+"px rgb("+rightBorder.getColor()+");");
		}
		Border topBorder=null;
		if(style!=null){
			topBorder=style.getTopBorder();
		}
		if(topBorder!=null){
			sb.append("border-top:"+topBorder.getStyle().name()+" "+topBorder.getWidth()+"px rgb("+topBorder.getColor()+");");
		}
		Border bottomBorder=null;
		if(style!=null){
			bottomBorder=style.getBottomBorder();
		}
		if(bottomBorder!=null){
			sb.append("border-bottom:"+bottomBorder.getStyle().name()+" "+bottomBorder.getWidth()+"px rgb("+bottomBorder.getColor()+");");
		}
		if(sb.length()>0){
			int colWidth=cell.getColumn().getWidth();
			sb.append("width:"+colWidth+"pt");
			sb.insert(0, "style=\"");
			sb.append("\"");
		}
		return sb.toString();
	}

	private int buildWidth(List<Column> columns,int colIndex,int colSpan){
		int width=0;
		int start=colIndex,end=colIndex+colSpan;
		for(int i=start;i<end;i++){
			Column col=columns.get(i);
			width+=col.getWidth();
		}
		return width;
	}

	private int buildHeight(List<Row> rows,int rowIndex,int rowSpan){
		int height=0;
		int start=rowIndex,end=rowIndex+rowSpan;
		for(int i=start;i<end;i++){
			Row row=rows.get(i);
			height+=row.getRealHeight();
		}
		return height;
	}
}