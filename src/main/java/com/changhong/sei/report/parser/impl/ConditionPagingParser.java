package com.changhong.sei.report.parser.impl;

import com.changhong.sei.report.definition.ConditionPaging;
import com.changhong.sei.report.enums.PagingPosition;
import com.changhong.sei.report.parser.Parser;
import org.dom4j.Element;

/**
 * @desc：分页条件转换
 * @author：zhaohz
 * @date：2020/7/6 10:54
 */
public class ConditionPagingParser implements Parser<ConditionPaging> {
	@Override
	public ConditionPaging parse(Element element) {
		ConditionPaging paging=new ConditionPaging();
		String position=element.attributeValue("position");
		paging.setPosition(PagingPosition.valueOf(position));
		String line=element.attributeValue("line");
		paging.setLine(Integer.valueOf(line));
		return paging;
	}
}
