package com.changhong.sei.report.definition.searchform;

import java.util.Arrays;
import java.util.List;

/**
 * @desc：单选框组件
 * @author：zhaohz
 * @date：2020/6/30 11:13
 */
public class RadioInputComponent extends InputComponent {
	private boolean optionsInline;
	private List<Option> options;
	@Override
	String inputHtml(RenderContext context) {
		StringBuilder sb=new StringBuilder();
		String name=getBindParameter();
		Object pvalue=context.getParameter(name)==null ? "" : context.getParameter(name);
		String[] data=pvalue.toString().split(",");
		List<String> list=Arrays.asList(data);
		sb.append("<div>");
		for(Option option:options){
			String value=option.getValue();
			String label=option.getLabel();
			String checked=list.contains(value) ? "checked" : "";
			if(this.optionsInline){
				sb.append("<span class='checkbox-inline' style='padding-top:0px;padding-left:2px;padding-top:0px'><input value='").append(value).append("' ").append(checked).append(" type='radio' name='").append(name).append("'> ").append(label).append("</span>");
			}else{
				sb.append("<span class='checkbox'><input value='").append(value).append("' type='radio' ").append(checked).append(" name='").append(name).append("' style='margin-left: auto'> <span style=\"margin-left:15px\">").append(label).append("</span></span>");
			}
		}
		sb.append("</div>");
		return sb.toString();
	}
	@Override
	public String initJs(RenderContext context) {
		String name=getBindParameter();
		StringBuilder sb=new StringBuilder();
		sb.append("formElements.push(");
		sb.append("function(){");
		sb.append("if(''==='").append(name).append("'){");
		sb.append("alert('单选框未绑定查询参数名，不能进行查询操作!');");
		sb.append("throw '单选框未绑定查询参数名，不能进行查询操作!'");
		sb.append("}");
		sb.append("return {");
		sb.append("\"").append(name).append("\":");
		sb.append("$(\"input[name='").append(getBindParameter()).append("']:checked\").val()");
		sb.append("}");
		sb.append("}");
		sb.append(");");
		return sb.toString();
	}
	public void setOptionsInline(boolean optionsInline) {
		this.optionsInline = optionsInline;
	}
	public boolean isOptionsInline() {
		return optionsInline;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public List<Option> getOptions() {
		return options;
	}
}
