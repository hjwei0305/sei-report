package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.definition.searchform.ColComponent;
import com.changhong.sei.report.definition.searchform.Component;
import com.changhong.sei.report.definition.searchform.GridComponent;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：表格转换
 * @author：zhaohz
 * @date：2020/7/6 11:12
 */
public class GridParser implements FormParser<GridComponent> {
	@Override
	public GridComponent parse(Element element) {
		GridComponent grid=new GridComponent();
		grid.setType(element.attributeValue("type"));
		grid.setShowBorder(Boolean.valueOf(element.attributeValue("show-border")));
		if(grid.isShowBorder()){
			grid.setBorderColor(element.attributeValue("border-color"));
			grid.setBorderWidth(Integer.valueOf(element.attributeValue("border-width")));			
		}
		List<ColComponent> cols=new ArrayList<ColComponent>();
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(!ele.getName().equals("col")){
				continue;
			}
			ColComponent col=new ColComponent();
			cols.add(col);
			col.setSize(Integer.valueOf(ele.attributeValue("size")));
			List<Component> components= FormParserUtils.parse(ele);
			col.setChildren(components);
		}
		grid.setCols(cols);
		return grid;
	}
	@Override
	public boolean support(String name) {
		return name.equals("grid");
	}
}
