package com.changhong.sei.report.expression.model.condition;

import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @desc：属性条件表达式
 * @author：zhaohz
 * @date：2020/6/30 15:25
 */
public class PropertyExpressionCondition extends BaseCondition {
	private ConditionType type= ConditionType.property;
	@JsonIgnore
	private String leftProperty;
	@JsonIgnore
	private Expression rightExpression;
	@Override
	Object computeLeft(Cell cell, Cell currentCell, Object obj, Context context) {
		if(StringUtils.isNotBlank(leftProperty)){
			return Utils.getProperty(obj, leftProperty);
		}else{
			return cell.getData();
		}
	}

	@Override
	Object computeRight(Cell cell, Cell currentCell, Object obj, Context context) {
		ExpressionData<?> exprData=rightExpression.execute(cell, currentCell,context);
		return extractExpressionData(exprData);
	}
	
	@Override
	public ConditionType getType() {
		return type;
	}
	
	public void setLeftProperty(String leftProperty) {
		this.leftProperty = leftProperty;
	}
	public void setRightExpression(Expression rightExpression) {
		this.rightExpression = rightExpression;
	}
	public String getLeftProperty() {
		return leftProperty;
	}
	public Expression getRightExpression() {
		return rightExpression;
	}
}
