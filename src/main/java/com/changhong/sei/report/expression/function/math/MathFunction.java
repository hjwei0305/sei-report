package com.changhong.sei.report.expression.function.math;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.exception.ReportComputeException;
import com.changhong.sei.report.expression.function.Function;
import com.changhong.sei.report.expression.model.data.BindDataListExpressionData;
import com.changhong.sei.report.expression.model.data.ExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectExpressionData;
import com.changhong.sei.report.expression.model.data.ObjectListExpressionData;
import com.changhong.sei.report.utils.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc：数学函数抽象类
 * @author：zhaohz
 * @date：2020/7/6 9:01
 */
public abstract class MathFunction implements Function {
	protected List<BigDecimal> buildDataList(List<ExpressionData<?>> dataList){
		if(dataList==null || dataList.size()==0){
			throw new ReportComputeException("Function ["+name()+"] need a lot of data parameter.");
		}
		List<BigDecimal> list=new ArrayList<BigDecimal>();
		for(ExpressionData<?> data:dataList){
			if(data instanceof ObjectListExpressionData){
				ObjectListExpressionData objList=(ObjectListExpressionData)data;
				for(Object obj:objList.getData()){
					BigDecimal bigData= Utils.toBigDecimal(obj);
					if(bigData!=null){
						list.add(bigData);						
					}
				}
			}else if(data instanceof ObjectExpressionData){
				ObjectExpressionData objData=(ObjectExpressionData)data;
				BigDecimal bigData= Utils.toBigDecimal(objData.getData());
				if(bigData!=null){
					list.add(bigData);
				}
			}else if(data instanceof BindDataListExpressionData){
				BindDataListExpressionData bindDataListData=(BindDataListExpressionData)data;
				for(BindData bindData:bindDataListData.getData()){
					BigDecimal bigData= Utils.toBigDecimal(bindData.getValue());
					if(bigData!=null){
						list.add(bigData);
					}
				}
			}
		}
		return list;
	}
	
	protected BigDecimal buildBigDecimal(List<ExpressionData<?>> dataList) {
		if(dataList==null || dataList.size()==0){
			throw new ReportComputeException("Function ["+name()+"] need a data of number parameter.");
		}
		BigDecimal number=null;
		ExpressionData<?> data=dataList.get(0);
		if(data instanceof ObjectListExpressionData){
			ObjectListExpressionData listData=(ObjectListExpressionData)data;
			List<?> list=listData.getData();
			if(list==null || list.size()!=1){
				throw new ReportComputeException("Function ["+name()+"] need a data of number parameter.");
			}
			Object obj=list.get(0);
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			number= Utils.toBigDecimal(obj);
		}else if(data instanceof ObjectExpressionData){
			ObjectExpressionData objData=(ObjectExpressionData)data;
			Object obj=objData.getData();
			if(obj==null){
				throw new ReportComputeException("Function ["+name()+"] parameter can not be null.");
			}
			number= Utils.toBigDecimal(obj);
		}else{
			throw new ReportComputeException("Function ["+name()+"] need a data of number parameter.");
		}
		return number;
	}
}
