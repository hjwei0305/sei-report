/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.changhong.sei.report.model;

import com.bstek.ureport.Utils;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.CellDefinition;
import com.bstek.ureport.definition.ReportDefinition;
import com.bstek.ureport.definition.dataset.DatasetDefinition;
import com.bstek.ureport.definition.dataset.Parameter;
import com.bstek.ureport.definition.dataset.SqlDatasetDefinition;
import com.bstek.ureport.definition.datasource.BuildinDatasourceDefinition;
import com.bstek.ureport.definition.datasource.DataType;
import com.bstek.ureport.definition.datasource.DatasourceDefinition;
import com.bstek.ureport.definition.datasource.JdbcDatasourceDefinition;
import com.bstek.ureport.definition.value.DatasetValue;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.ExpressionUtils;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.expression.model.data.ObjectExpressionData;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.utils.ProcedureUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageProducer {

	public Page produce(ReportDefinition reportDefinition, Map<String, Object> parameters, int pageIndex, int rows) throws SQLException, ServletException {
		Page page = new Page(pageIndex, rows);
		page.setRecords(0);
		Cell cell = rootDataCell(reportDefinition);
		//获取查总行数的参数
		Map<String, Object> params = noPageParam(parameters);
		if(!ObjectUtils.isEmpty(cell)){
			List<DatasourceDefinition> datasourceList = reportDefinition.getDatasources();
			String datasetName = ((DatasetValue)cell.getValue()).getDatasetName();
			flag: for(DatasourceDefinition datasource:datasourceList){
				for(DatasetDefinition datasetDefinition:datasource.getDatasets()){
					if(datasetName.equals(datasetDefinition.getName())){
						Map<String, Object> parameterMap = buildParameters(((SqlDatasetDefinition) datasetDefinition).getParameters(), parameters);
						if (parameterMap.containsKey("startRow")&&parameterMap.containsKey("rows")) {
							Connection conn = buildConnect(datasource);
							String sql = "select count(1) as records from (" + sqlForUse((SqlDatasetDefinition) datasetDefinition, params) + ")";
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
			if(!"startRow".equals(name) && !"rows".equals(name)) {
				map.put(name, datatype.parse(value));
			}
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
		cell.setFillBlankRows(cellDef.isFillBlankRows());
		cell.setMultiple(cellDef.getMultiple());
		cell.setLinkUrlExpression(cellDef.getLinkUrlExpression());
		return cell;
	}
}