package com.changhong.sei.report.expression.function;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.expression.model.data.BindDataListExpressionData;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @desc：最大值函数
 * @author：zhaohz
 * @date：2020/6/30 14:19
 */
public class MaxFunction implements Function {

	@Override
	public Object execute(List<ExpressionData<?>> dataList, Context context, Cell currentCell) {
		if(dataList==null || dataList.size()==0){
			return null;
		}
		BigDecimal value=null;
		for(ExpressionData<?> exprData:dataList){
			if(exprData instanceof ObjectListExpressionData){
				ObjectListExpressionData listExpr=(ObjectListExpressionData)exprData;
				List<?> list=listExpr.getData();
				for(Object obj:list){
					if(obj==null || StringUtils.isBlank(obj.toString())){
						continue;
					}
					BigDecimal bigData= Utils.toBigDecimal(obj);
					if(value==null){
						value=bigData;
					}else{
						int result=value.compareTo(bigData);
						if(result==-1){
							value=bigData;
						}						
					}
				}
			}else if(exprData instanceof ObjectExpressionData){
				Object obj=exprData.getData();
				if(obj!=null && StringUtils.isNotBlank(obj.toString())){
					value= Utils.toBigDecimal(obj);
				}
			}else if(exprData instanceof BindDataListExpressionData){
				BindDataListExpressionData bindDataList=(BindDataListExpressionData)exprData;
				List<BindData> list=bindDataList.getData();
				for(BindData bindData:list){
					Object obj=bindData.getValue();
					if(obj==null || StringUtils.isBlank(obj.toString())){
						continue;
					}
					BigDecimal bigData= Utils.toBigDecimal(obj);
					if(value==null){
						value=bigData;
					}else{
						int result=value.compareTo(bigData);
						if(result==-1){
							value=bigData;
						}						
					}
				}
			}
		}
		return value;
	}

	@Override
	public String name() {
		return "max";
	}
}
