package com.changhong.sei.report.definition.searchform;

/**
 * @desc：查询按钮
 * @author：zhaohz
 * @date：2020/6/30 11:16
 */
public class SubmitButtonComponent extends ButtonComponent {
	@Override
	public String initJs(RenderContext context) {
		StringBuilder sb=new StringBuilder();
		sb.append("$('#"+context.buildComponentId(this)+"').click(function(){");
		sb.append("doSearch();");
		sb.append("});");
		return sb.toString();
	}
}
