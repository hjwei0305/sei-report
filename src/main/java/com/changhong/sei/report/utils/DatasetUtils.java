package com.changhong.sei.report.utils;

import com.changhong.sei.report.build.BindData;
import com.changhong.sei.report.build.Context;
import com.changhong.sei.report.build.aggregate.*;
import com.changhong.sei.report.enums.AggregateType;
import com.changhong.sei.report.exception.CellComputeException;
import com.changhong.sei.report.expression.model.expr.DatasetExpression;
import com.changhong.sei.report.model.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：数据及工具
 * @author：zhaohz
 * @date：2020/6/30 17:05
 */
public class DatasetUtils {
	private static Map<AggregateType, Aggregate> aggregates=new HashMap<AggregateType, Aggregate>();
	static{
		aggregates.put(AggregateType.group,new GroupAggregate());
		aggregates.put(AggregateType.select,new SelectAggregate());
		aggregates.put(AggregateType.reselect,new ReselectAggregate());
		aggregates.put(AggregateType.regroup,new RegroupAggregate());
		aggregates.put(AggregateType.avg,new AvgAggregate());
		aggregates.put(AggregateType.count,new CountAggregate());
		aggregates.put(AggregateType.sum,new SumAggregate());
		aggregates.put(AggregateType.min,new MinAggregate());
		aggregates.put(AggregateType.max,new MaxAggregate());
		aggregates.put(AggregateType.customgroup,new CustomGroupAggregate());
	}
	
	public static List<BindData> computeDatasetExpression(DatasetExpression expr, Cell cell, Context context){
		AggregateType aggregateType=expr.getAggregate();
		Aggregate aggregate=aggregates.get(aggregateType);
		if(aggregate!=null){
			return aggregate.aggregate(expr,cell,context);
		}else{			
			throw new CellComputeException("Unknow aggregate : "+aggregateType);
		}
	}
}
