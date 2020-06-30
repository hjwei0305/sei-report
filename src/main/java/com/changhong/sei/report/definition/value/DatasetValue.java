package com.changhong.sei.report.definition.value;

import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.expression.model.expr.DatasetExpression;

/**
 * @desc：数据集值
 * @author：zhaohz
 * @date：2020/6/30 16:51
 */
public class DatasetValue extends DatasetExpression implements Value {
	private static final long serialVersionUID = 1892973888854385049L;

	@Override
	public ValueType getType() {
		return ValueType.dataset;
	}
	
	@Override
	public String getValue() {
		StringBuffer sb=new StringBuffer();
		sb.append(getDatasetName());
		sb.append(".");
		sb.append(getAggregate().name());
		sb.append("(");
		String prop=getProperty();
		if(prop!=null){
			if(prop.length()>13){
				prop=prop.substring(0,10)+"...";
			}
			sb.append(prop);
		}
		sb.append(")");
		return sb.toString();
	}
}
