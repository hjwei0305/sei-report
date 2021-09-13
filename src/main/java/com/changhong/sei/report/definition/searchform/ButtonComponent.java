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
		return new StringBuilder()
				.append("<div style='text-align:")
				.append(this.align)
				.append("'><button type=\"button\" id=\"")
				.append(context.buildComponentId(this))
				.append("\" class=\"btn ")
				.append(style)
				.append(" btn-sm\">")
				.append(label)
				.append("</button></div>")
				.toString();
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
