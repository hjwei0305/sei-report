package com.changhong.sei.report.definition.searchform;

/**
 * @desc：按钮组件
 * @author：zhaohz
 * @date：2020/6/30 11:04
 */
public abstract class ButtonComponent implements Component {
	private String label;
	private String style;
	private Align align= Align.left;
	private String type;
	@Override
	public String toHtml(RenderContext context) {
		return "<div style='text-align:"+this.align+"'><button type=\"button\" id=\""+context.buildComponentId(this)+"\" class=\"btn "+style+" btn-sm\">"+label+"</button></div>";
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	@Override
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Align getAlign() {
		return align;
	}
	public void setAlign(Align align) {
		this.align = align;
	}
}
