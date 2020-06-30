package com.changhong.sei.report.definition.searchform;

/**
 * @desc：重置按钮
 * @author：zhaohz
 * @date：2020/6/30 11:14
 */
public class ResetButtonComponent extends ButtonComponent {
	@Override
	public String toHtml(RenderContext context) {
		return "<button type=\"reset\" id=\""+context.buildComponentId(this)+"\" class=\"btn "+getStyle()+" btn-sm\">"+getLabel()+"</button>";
	}
	@Override
	public String initJs(RenderContext context) {
		return "";
	}
}
