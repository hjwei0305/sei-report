package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.definition.searchform.DateInputComponent;
import com.changhong.sei.report.definition.searchform.LabelPosition;
import org.dom4j.Element;

/**
 * @desc：时间格式输入
 * @author：zhaohz
 * @date：2020/7/7 10:18
 */
public class DatetimeInputParser implements FormParser<DateInputComponent> {
	@Override
	public DateInputComponent parse(Element element) {
		DateInputComponent component=new DateInputComponent();
		component.setBindParameter(element.attributeValue("bind-parameter"));
		component.setLabel(element.attributeValue("label"));
		component.setType(element.attributeValue("type"));
		component.setLabelPosition(LabelPosition.valueOf(element.attributeValue("label-position")));
		component.setFormat(element.attributeValue("format"));
		return component;
	}
	@Override
	public boolean support(String name) {
		return name.equals("input-datetime");
	}
}
