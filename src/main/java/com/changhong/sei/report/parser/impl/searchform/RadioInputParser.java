package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.definition.searchform.LabelPosition;
import com.changhong.sei.report.definition.searchform.Option;
import com.changhong.sei.report.definition.searchform.RadioInputComponent;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：单选框转换
 * @author：zhaohz
 * @date：2020/7/6 11:13
 */
public class RadioInputParser implements FormParser<RadioInputComponent> {
	@Override
	public RadioInputComponent parse(Element element) {
		RadioInputComponent radio=new RadioInputComponent();
		radio.setBindParameter(element.attributeValue("bind-parameter"));
		radio.setOptionsInline(Boolean.valueOf(element.attributeValue("options-inline")));
		radio.setLabel(element.attributeValue("label"));
		radio.setType(element.attributeValue("type"));
		radio.setLabelPosition(LabelPosition.valueOf(element.attributeValue("label-position")));
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
		radio.setOptions(options);
		return radio;
	}
	@Override
	public boolean support(String name) {
		return name.equals("input-radio");
	}
}
