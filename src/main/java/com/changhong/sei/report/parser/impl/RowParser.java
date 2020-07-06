package com.changhong.sei.report.parser.impl;

import com.changhong.sei.report.definition.RowDefinition;
import com.changhong.sei.report.enums.Band;
import com.changhong.sei.report.parser.Parser;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

/**
 * @desc：行转换
 * @author：zhaohz
 * @date：2020/7/6 11:03
 */
public class RowParser implements Parser<RowDefinition> {
	@Override
	public RowDefinition parse(Element element) {
		RowDefinition row=new RowDefinition();
		row.setRowNumber(Integer.valueOf(element.attributeValue("row-number")));
		String height=element.attributeValue("height");
		if(StringUtils.isNotBlank(height)){
			row.setHeight(Integer.valueOf(height));
		}
		String band=element.attributeValue("band");
		if(StringUtils.isNotBlank(band)){
			row.setBand(Band.valueOf(band));
		}
		return row;
	}
}
