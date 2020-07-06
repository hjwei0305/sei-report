package com.changhong.sei.report.parser.impl;

import com.changhong.sei.report.definition.ColumnDefinition;
import com.changhong.sei.report.parser.Parser;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

/**
 * @desc：列转换
 * @author：zhaohz
 * @date：2020/7/6 10:54
 */
public class ColumnParser implements Parser<ColumnDefinition> {
	@Override
	public ColumnDefinition parse(Element element) {
		ColumnDefinition col=new ColumnDefinition();
		col.setColumnNumber(Integer.valueOf(element.attributeValue("col-number")));
		String hide=element.attributeValue("hide");
		if(StringUtils.isNotBlank(hide)){
			col.setHide(Boolean.valueOf(hide));			
		}
		String width=element.attributeValue("width");
		if(StringUtils.isNotBlank(width)){
			col.setWidth(Integer.valueOf(width));
		}
		return col;
	}
}
