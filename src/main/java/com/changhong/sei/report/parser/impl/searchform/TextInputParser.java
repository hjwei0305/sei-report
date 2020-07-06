package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.definition.searchform.LabelPosition;
import com.changhong.sei.report.definition.searchform.TextInputComponent;
import org.dom4j.Element;

/**
 * @desc：文本输入框
 * @author：zhaohz
 * @date：2020/7/6 11:16
 */
public class TextInputParser implements FormParser<TextInputComponent> {
	@Override
	public TextInputComponent parse(Element element) {
		TextInputComponent component=new TextInputComponent();
		component.setBindParameter(element.attributeValue("bind-parameter"));
		component.setLabel(element.attributeValue("label"));
		component.setType(element.attributeValue("type"));
		component.setLabelPosition(LabelPosition.valueOf(element.attributeValue("label-position")));
		return component;
	}
	@Override
	public boolean support(String name) {
		return name.equals("input-text");
	}
}
