package com.changhong.sei.report.definition.dataset;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.build.Dataset;
import com.changhong.sei.report.expression.model.Expression;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc：SQL数据集模板
 * @author：zhaohz
 * @date：2020/6/30 13:39
 */
public class SqlDatasetDefinition implements DatasetDefinition {
	private static final long serialVersionUID = -1134526105416805870L;
	private String name;
	private String sql;
	private List<Parameter> parameters;
	private List<Field> fields;
	private Expression sqlExpression;
	public Dataset buildDataset(Map<String,Object> parameterMap, Connection conn){
		String sqlForUse=sql;
		Context context=new Context(null,parameterMap);
		if(sqlExpression!=null){
			sqlForUse=executeSqlExpr(sqlExpression, context);
		}else{
			Pattern pattern=Pattern.compile("\\$\\{.*?\\}");
			Matcher matcher=pattern.matcher(sqlForUse);
			while(matcher.find()){
				String substr=matcher.group();
				String sqlExpr=substr.substring(2,substr.length()-1);
				Expression expr= ExpressionUtils.parseExpression(sqlExpr);
				String result=executeSqlExpr(expr, context);
				sqlForUse=sqlForUse.replace(substr, result);
			}
		}
		Utils.logToConsole("RUNTIME SQL:"+sqlForUse);
		Map<String, Object> pmap = buildParameters(parameterMap);
		if(ProcedureUtils.isProcedure(sqlForUse)){
			List<Map<String,Object>> result = ProcedureUtils.procedureQuery(sqlForUse,pmap,conn);
			return new Dataset(name,result);
		}
		SingleConnectionDataSource datasource=new SingleConnectionDataSource(conn,false);
		NamedParameterJdbcTemplate jdbcTemplate=new NamedParameterJdbcTemplate(datasource);
		List<Map<String,Object>> list= jdbcTemplate.queryForList(sqlForUse, pmap);
		return new Dataset(name,list);
	}

	private String executeSqlExpr(Expression sqlExpr, Context context){
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


	private Map<String,Object> buildParameters(Map<String,Object> params){
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

	@Override
	public List<Field> getFields() {
		return fields;
	}
	
	public void setSqlExpression(Expression sqlExpression) {
		this.sqlExpression = sqlExpression;
	}
	
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getSql() {
		return sql;
	}
}
