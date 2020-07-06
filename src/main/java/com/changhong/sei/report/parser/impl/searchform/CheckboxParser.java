package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.definition.searchform.CheckboxInputComponent;
import com.changhong.sei.report.definition.searchform.LabelPosition;
import com.changhong.sei.report.definition.searchform.Option;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：复选框转换
 * @author：zhaohz
 * @date：2020/7/6 11:03
 */
public class CheckboxParser implements FormParser<CheckboxInputComponent> {
	@Override
	public CheckboxInputComponent parse(Element element) {
		CheckboxInputComponent checkbox=new CheckboxInputComponent();
		checkbox.setBindParameter(element.attributeValue("bind-parameter"));
		checkbox.setOptionsInline(Boolean.valueOf(element.attributeValue("options-inline")));
		checkbox.setLabel(element.attributeValue("label"));
		checkbox.setType(element.attributeValue("type"));
		checkbox.setLabelPosition(LabelPosition.valueOf(element.attributeValue("label-position")));
		List<Option> options=new ArrayList<Option>();
		for(Object obj:element.elements()){
			if(obj==null || !(obj instanceof Element)){
				continue;
			}
			Element ele=(Element)obj;
			if(!ele.getName().equals("option")){
				continue;
			}
			Option option=new Option();
			options.add(option);
			option.setLabel(ele.attributeValue("label"));
			option.setValue(ele.attributeValue("value"));
		}
		checkbox.setOptions(options);
		return checkbox;
	}
	@Override
	public boolean support(String name) {
		return name.equals("input-checkbox");
	}
}
