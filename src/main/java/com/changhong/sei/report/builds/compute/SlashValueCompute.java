package com.changhong.sei.report.builds.compute;

import com.changhong.sei.report.builds.BindData;
import com.changhong.sei.report.builds.Context;
import com.changhong.sei.report.definition.value.SlashValue;
import com.changhong.sei.report.enums.ValueType;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：斜线/斜杠值计算
 * @author：zhaohz
 * @date：2020/6/28 17:31
 */
public class SlashValueCompute implements ValueCompute {
	@Override
	public List<BindData> compute(Cell cell, Context context) {
		List<BindData> list=new ArrayList<BindData>();
		SlashValue v=(SlashValue)cell.getValue();
		Image img=new Image(v.getBase64Data(),"slash.png",0,0);
		BindData bindData=new BindData(img);
		list.add(bindData);
		/*String key=SlashBuilder.buildKey(context.getReport().getReportFullName(), cell.getName());
		Resource res=new Resource(key);
		BindData bindData=new BindData(res);
		list.add(bindData);*/
		return list;
	}


	@Override
	public ValueType type() {
		return ValueType.slash;
	}
}
