package com.changhong.sei.report.definition;

import com.changhong.sei.report.enums.Alignment;
import com.changhong.sei.report.export.pdf.font.FontBuilder;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.awt.*;
import java.io.Serializable;

/**
 * @desc：单元格样式
 * @author：zhaohz
 * @date：2020/6/28 14:30
 */
public class CellStyle implements Serializable{

	/**
	 * 背景色
	 */
	private String bgcolor;
	/**
	 * 前景色，文本或字体颜色
	 */
	private String forecolor;
	/**
	 * 字体尺寸
	 */
	private Integer fontSize;
	/**
	 * 字体描述
	 */
	private String fontFamily;
	/**
	 * 展示格式
	 */
	private String format;
	/**
	 * 行高
	 */
	private Float lineHeight;
	/**
	 * 左右对齐方式
	 */
	private Alignment align;
	/**
	 * 上下对齐方式
	 */
	private Alignment valign;
	/**
	 * 加粗
	 */
	private Boolean bold;
	/**
	 * 斜体
	 */
	private Boolean italic;
	/**
	 * 下划线
	 */
	private Boolean underline;
	/**
	 * todo
	 */
	private Boolean wrapCompute;
	/**
	 * 左边框
	 */
	private Border leftBorder;
	/**
	 * 右边框
	 */
	private Border rightBorder;
	/**
	 * 上边框
	 */
	private Border topBorder;
	/**
	 * 下边框
	 */
	private Border bottomBorder;

	/**
	 * 字体
	 */
	@JsonIgnore
	private Font font;

	public Border getLeftBorder() {
		return leftBorder;
	}

	public void setLeftBorder(Border leftBorder) {
		this.leftBorder = leftBorder;
	}

	public Border getRightBorder() {
		return rightBorder;
	}

	public void setRightBorder(Border rightBorder) {
		this.rightBorder = rightBorder;
	}

	public Border getTopBorder() {
		return topBorder;
	}

	public void setTopBorder(Border topBorder) {
		this.topBorder = topBorder;
	}

	public Border getBottomBorder() {
		return bottomBorder;
	}

	public void setBottomBorder(Border bottomBorder) {
		this.bottomBorder = bottomBorder;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getForecolor() {
		return forecolor;
	}

	public void setForecolor(String forecolor) {
		this.forecolor = forecolor;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Alignment getAlign() {
		return align;
	}

	public void setAlign(Alignment align) {
		this.align = align;
	}

	public Alignment getValign() {
		return valign;
	}

	public void setValign(Alignment valign) {
		this.valign = valign;
	}

	public Boolean getBold() {
		return bold;
	}

	public void setBold(Boolean bold) {
		this.bold = bold;
	}

	public Boolean getItalic() {
		return italic;
	}

	public void setItalic(Boolean italic) {
		this.italic = italic;
	}

	public Boolean getUnderline() {
		return underline;
	}

	public void setUnderline(Boolean underline) {
		this.underline = underline;
	}

	public Boolean getWrapCompute() {
		return wrapCompute;
	}

	public void setWrapCompute(Boolean wrapCompute) {
		this.wrapCompute = wrapCompute;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Float getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(Float lineHeight) {
		this.lineHeight = lineHeight;
	}

	@JsonIgnore
	public Font getFont(){
		if(this.font==null){
			int fontStyle=Font.PLAIN;
			if((bold!=null && bold) && (italic!=null && italic)){
				fontStyle=Font.BOLD|Font.ITALIC;				
			}else if(bold!=null && bold){
				fontStyle=Font.BOLD;							
			}else if(italic!=null && italic){
				fontStyle=Font.ITALIC;							
			}
			String fontName=fontFamily;
			if(StringUtils.isBlank(fontName)){
				fontName="宋体";
			}
			this.font= FontBuilder.getAwtFont(fontName, fontStyle, new Float(fontSize));
		}
		return this.font;
	}
}
