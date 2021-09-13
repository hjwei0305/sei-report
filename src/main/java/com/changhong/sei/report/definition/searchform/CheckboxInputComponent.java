package com.changhong.sei.report.definition.searchform;

import java.util.Arrays;
import java.util.List;

/**
 * @desc：复选框组件
 * @author：zhaohz
 * @date：2020/6/30 11:07
 */
public class CheckboxInputComponent extends InputComponent {
	private boolean optionsInline;
	private List<Option> options;
	@Override
	String inputHtml(RenderContext context) {
		StringBuilder sb=new StringBuilder();
		String name=getBindParameter();
		Object pvalue=context.getParameter(name)==null ? "" : context.getParameter(name);
		String[] data=pvalue.toString().split(",");
		List<String> list=Arrays.asList(data);
		for(Option option:options){
			String value=option.getValue();
			String label=option.getLabel();
			String checked=list.contains(value) ? "checked" : "";
			if(this.optionsInline){
				sb.append("<span class='checkbox-inline' style='padding-top:0px'><input value='").append(value).append("' type='checkbox' ").append(checked).append(" name='").append(name).append("'>").append(label).append("</span>");
			}else{
				sb.append("<span class='checkbox'><input type='checkbox' value='").append(value).append("' name='").append(name).append("' ").append(checked).append(" style='margin-left: auto'><span style=\"margin-left:15px\">").append(label).append("</span></span>");
			}
		}
		return sb.toString();
	}
	@Override
	public String initJs(RenderContext context) {
		String name=getBindParameter();
		StringBuilder sb=new StringBuilder();
		sb.append("formElements.push(");
		sb.append("function(){");
		sb.append("if(''==='").append(name).append("'){");
		sb.append("alert('复选框未绑定查询参数名，不能进行查询操作!');");
		sb.append("throw '复选框未绑定查询参数名，不能进行查询操作!'");
		sb.append("}");
		sb.append("var names='';");
		sb.append("$(\"input[name='").append(getBindParameter()).append("']:checked\").each(function(index,item){");
		sb.append("if(names===''){names+=$(item).val();}else{names+=','+$(item).val();}");
		sb.append("});");
		sb.append("return {");
		sb.append("\"").append(name).append("\":names");
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
