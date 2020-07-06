package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.definition.searchform.Align;
import com.changhong.sei.report.definition.searchform.SubmitButtonComponent;
import org.dom4j.Element;

/**
 * @desc：查询按钮
 * @author：zhaohz
 * @date：2020/7/6 11:15
 */
public class SubmitButtonParser implements FormParser<SubmitButtonComponent> {
	@Override
	public SubmitButtonComponent parse(Element element) {
		SubmitButtonComponent btn=new SubmitButtonComponent();
		btn.setLabel(element.attributeValue("label"));
		btn.setStyle(element.attributeValue("style"));
		btn.setType(element.attributeValue("type"));
		String align=element.attributeValue("align");
		if(align!=null){
			btn.setAlign(Align.valueOf(align));
		}
		return btn;
	}
	@Override
	public boolean support(String name) {
		return name.equals("button-submit");
	}
}
