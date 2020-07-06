package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.definition.searchform.ResetButtonComponent;
import org.dom4j.Element;

/**
 * @desc：重置按钮转换
 * @author：zhaohz
 * @date：2020/7/6 11:13
 */
public class ResetButtonParser implements FormParser<ResetButtonComponent> {
	@Override
	public ResetButtonComponent parse(Element element) {
		ResetButtonComponent btn=new ResetButtonComponent();
		btn.setLabel(element.attributeValue("label"));
		btn.setStyle(element.attributeValue("style"));
		btn.setType(element.attributeValue("type"));
		return btn;
	}
	@Override
	public boolean support(String name) {
		return name.equals("button-reset");
	}
}
