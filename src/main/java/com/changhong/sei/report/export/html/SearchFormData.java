package com.changhong.sei.report.export.html;

import com.changhong.sei.report.definition.searchform.FormPosition;

/**
 * @desc：searchForm返前端数据
 * @author：zhaohz
 * @date：2020/6/30 13:36
 */
public class SearchFormData {
	private String html;
	private String js;
	private String searchFormXml;
	private FormPosition formPosition;
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getJs() {
		return js;
	}
	public void setJs(String js) {
		this.js = js;
	}
	public FormPosition getFormPosition() {
		return formPosition;
	}
	public void setFormPosition(FormPosition formPosition) {
		this.formPosition = formPosition;
	}
	public String getSearchFormXml() {
		return searchFormXml;
	}
	public void setSearchFormXml(String searchFormXml) {
		this.searchFormXml = searchFormXml;
	}
}
