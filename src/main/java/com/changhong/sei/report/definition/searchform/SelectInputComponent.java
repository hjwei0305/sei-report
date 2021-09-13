package com.changhong.sei.report.definition.searchform;

import com.changhong.sei.report.builds.Dataset;
import com.changhong.sei.report.exception.DatasetUndefinitionException;
import com.changhong.sei.report.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @desc：下拉选择框
 * @author：zhaohz
 * @date：2020/6/30 11:15
 */
public class SelectInputComponent extends InputComponent {
	private boolean useDataset;
	private String dataset;
	private String labelField;
	private String valueField;
	private List<Option> options;
	@Override
	String inputHtml(RenderContext context) {
		String name=getBindParameter();
		Object pvalue=context.getParameter(name)==null ? "" : context.getParameter(name);
		StringBuilder sb=new StringBuilder();
		sb.append("<select style=\"padding:3px;height:28px\" id='").append(context.buildComponentId(this)).append("' name='").append(name).append("' class='form-control'>");
		if(useDataset && StringUtils.isNotBlank(dataset)){
			Dataset ds=context.getDataset(dataset);
			if(ds==null){
				throw new DatasetUndefinitionException(dataset);
			}
			for(Object obj:ds.getData()){
				Object label= Utils.getProperty(obj, labelField);
				Object value= Utils.getProperty(obj, valueField);
				String selected=value.equals(pvalue) ? "selected" : "";
				sb.append("<option value='").append(value).append("' ").append(selected).append(">").append(label).append("</option>");
			}
			if(pvalue.equals("")){
				sb.append("<option value='' selected></option>");
			}
		}else{
			for(Option option:options){
				String value=option.getValue();
				String selected=value.equals(pvalue) ? "selected" : "";
				sb.append("<option value='").append(value).append("' ").append(selected).append(">").append(option.getLabel()).append("</option>");
			}
			if(pvalue.equals("")){
				sb.append("<option value='' selected></option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}
	@Override
	public String initJs(RenderContext context) {
		String name=getBindParameter();
		StringBuilder sb=new StringBuilder();
		sb.append("formElements.push(");
		sb.append("function(){");
		sb.append("if(''==='").append(name).append("'){");
		sb.append("alert('列表框未绑定查询参数名，不能进行查询操作!');");
		sb.append("throw '列表框未绑定查询参数名，不能进行查询操作!'");
		sb.append("}");
		sb.append("return {");
		sb.append("\"").append(name).append("\":");
		sb.append("$('#").append(context.buildComponentId(this)).append("').val()");
		sb.append("}");
		sb.append("}");
		sb.append(");");
		return sb.toString();
	}
	public boolean isUseDataset() {
		return useDataset;
	}
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	public String getLabelField() {
		return labelField;
	}
	public void setLabelField(String labelField) {
		this.labelField = labelField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public void setUseDataset(boolean useDataset) {
		this.useDataset = useDataset;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public List<Option> getOptions() {
		return options;
	}
}
