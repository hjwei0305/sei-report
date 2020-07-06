package com.changhong.sei.report.parser.impl.searchform;

import com.changhong.sei.report.definition.searchform.Component;
import com.changhong.sei.report.definition.searchform.FormPosition;
import com.changhong.sei.report.definition.searchform.SearchForm;
import com.changhong.sei.report.parser.Parser;
import org.dom4j.Element;

import java.util.List;

/**
 * @desc：查询框转换
 * @author：zhaohz
 * @date：2020/7/6 11:14
 */
public class SearchFormParser implements Parser<SearchForm> {
	@Override
	public SearchForm parse(Element element) {
		SearchForm form=new SearchForm();
		form.setFormPosition(FormPosition.valueOf(element.attributeValue("form-position")));
		List<Component> components= FormParserUtils.parse(element);
		form.setComponents(components);
		return form;
	}
}
