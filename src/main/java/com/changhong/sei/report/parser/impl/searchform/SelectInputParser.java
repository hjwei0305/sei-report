package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.definition.searchform.LabelPosition;
import com.changhong.sei.report.definition.searchform.Option;
import com.changhong.sei.report.definition.searchform.SelectInputComponent;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc：下拉选择框
 * @author：zhaohz
 * @date：2020/7/6 11:14
 */
public class SelectInputParser implements FormParser<SelectInputComponent> {
	@Override
	public SelectInputComponent parse(Element element) {
		SelectInputComponent select=new SelectInputComponent();
		select.setBindParameter(element.attributeValue("bind-parameter"));
		select.setLabel(element.attributeValue("label"));
		select.setType(element.attributeValue("type"));
		select.setLabelPosition(LabelPosition.valueOf(element.attributeValue("label-position")));
		String useDataset=element.attributeValue("use-dataset");
		if(StringUtils.isNotBlank(useDataset)){
			select.setUseDataset(Boolean.valueOf(useDataset));
			select.setDataset(element.attributeValue("dataset"));
			select.setLabelField(element.attributeValue("label-field"));
			select.setValueField(element.attributeValue("value-field"));
		}
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
		select.setOptions(options);
		return select;
	}
	@Override
	public boolean support(String name) {
		return name.equals("input-select");
	}
}
