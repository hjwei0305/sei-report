package com.changhong.sei.report.expression.model.expr.ifelse;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.enums.Op;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.expression.model.data.*;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.ExpressionUtils;

import java.util.List;

/**
 * @desc：条件表达式
 * @author：zhaohz
 * @date：2020/6/30 15:34
 */
public class ExpressionCondition {
	private Expression left;
	private Op op;
	private Expression right;
	public ExpressionCondition(Expression left, Op op, Expression right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}
	public boolean eval(Context context, Cell cell, Cell currentCell){
		ExpressionData<?> leftData=left.execute(cell,currentCell, context);
		ExpressionData<?> rightData=right.execute(cell,currentCell,context);
		Object leftObj=getData(leftData);
		Object rightObj=getData(rightData);
		return ExpressionUtils.conditionEval(op, leftObj, rightObj);
	}
	
	private Object getData(ExpressionData<?> data){
		if(data instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)data;
			return objData.getData();
		}else if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData exprData=(ObjectListExpressionData)data;
			List<?> list=exprData.getData();
			StringBuffer sb=new StringBuffer();
			for(Object obj:list){
				if(sb.length()>0){
					sb.append(",");
				}
				sb.append(obj);
			}
			return sb.toString();
		}else if(data instanceof NoneExpressionData){
			return null;
		}else if(data instanceof BindDataListExpressionData){
			BindDataListExpressionData bindDataList=(BindDataListExpressionData)data;
			List<BindData> list=bindDataList.getData();
			if(list.size()==1){
				return list.get(0).getValue();
			}else{
				StringBuffer sb=new StringBuffer();
				for(BindData bindData:list){
					if(sb.length()>0){
						sb.append(",");
					}
					sb.append(bindData.getValue());
				}
				return sb.toString();
			}
		}else{
			throw new ReportComputeException("Unknow data : "+data);
		}
	}
	public Expression getLeft() {
		return left;
	}
	public Expression getRight() {
		return right;
	}
}
