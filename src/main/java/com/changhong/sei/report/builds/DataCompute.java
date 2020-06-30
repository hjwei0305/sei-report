package com.changhong.sei.report.builds;

import com.changhong.sei.report.builds.compute.*;
import com.changhong.sei.report.definition.value.Value;
import com.changhong.sei.report.exception.ReportException;
import com.changhong.sei.report.model.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：数据计算
 * @author：zhaohz
 * @date：2020/6/28 17:19
 */
public class DataCompute {
	private static Map<String, ValueCompute> valueComputesMap = new HashMap<String, ValueCompute>();

	static{
		SimpleValueCompute simpleValueCompute=new SimpleValueCompute();
		valueComputesMap.put(simpleValueCompute.type().name(), simpleValueCompute);
		DatasetValueCompute datasetValueCompute=new DatasetValueCompute();
		valueComputesMap.put(datasetValueCompute.type().name(), datasetValueCompute);
		ExpressionValueCompute expressionValueCompute=new ExpressionValueCompute();
		valueComputesMap.put(expressionValueCompute.type().name(), expressionValueCompute);
		ImageValueCompute imageValueCompute=new ImageValueCompute();
		valueComputesMap.put(imageValueCompute.type().name(), imageValueCompute);
		SlashValueCompute slashValueCompute=new SlashValueCompute();
		valueComputesMap.put(slashValueCompute.type().name(), slashValueCompute);
		ZxingValueCompute zxingValueCompute=new ZxingValueCompute();
		valueComputesMap.put(zxingValueCompute.type().name(), zxingValueCompute);
		ChartValueCompute chartValueCompute=new ChartValueCompute();
		valueComputesMap.put(chartValueCompute.type().name(), chartValueCompute);
		
	}

	public static List<BindData> buildCellData(Cell cell, Context context) {
		context.resetVariableMap();
		Value value = cell.getValue();
		ValueCompute valueCompute=valueComputesMap.get(value.getType().name());
		if(valueCompute!=null){
			List<BindData> list= valueCompute.compute(cell, context);
			return list;
		}
		throw new ReportException("Unsupport value: "+value);
	}
}
