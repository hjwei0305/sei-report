package com.changhong.sei.report.definition;

import com.changhong.sei.core.dto.serializer.EnumJsonSerializer;
import com.changhong.sei.report.enums.HtmlReportAlign;
import com.changhong.sei.report.enums.Orientation;
import com.changhong.sei.report.enums.PagingMode;
import com.changhong.sei.report.enums.PaperType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * @desc：纸张
 * @author：zhaohz
 * @date：2020/6/30 10:04
 */
public class Paper implements Serializable{
	private static final long serialVersionUID = -6153150083492704136L;
	/**
	 * 左边距
	 */
	private Integer leftMargin=90;
	/**
	 * 右边距
	 */
	private Integer rightMargin=90;
	/**
	 * 上边距
	 */
	private Integer topMargin=72;
	/**
	 * 下边距
	 */
	private Integer bottomMargin=72;
	/**
	 * 纸张类型
	 */
	private PaperType paperType;
	/**
	 * 前端分页方式
	 */
	@Enumerated(EnumType.STRING)
	@JsonSerialize(using = EnumJsonSerializer.class)
	private PagingMode pagingMode;
	/**
	 * 固定行数
	 */
	private Integer fixRows;
	/**
	 * 宽度
	 */
	private Integer width;
	/**
	 * 高度
	 */
	private Integer height;
	/**
	 * 打印方向
	 */
	@Enumerated(EnumType.STRING)
	@JsonSerialize(using = EnumJsonSerializer.class)
	private Orientation orientation;
	/**
	 * Html报表对齐方式
	 */
	@Enumerated(EnumType.STRING)
	@JsonSerialize(using = EnumJsonSerializer.class)
	private HtmlReportAlign htmlReportAlign= HtmlReportAlign.left;
	/**
	 * 套打背景图片路径
	 */
	private String bgImage;
	/**
	 * 是否分栏
	 */
	private Boolean columnEnabled = Boolean.FALSE;
	/**
	 * 分几栏
	 */
	private Integer columnCount=2;
	/**
	 * 栏之间的间距
	 */
	private Integer columnMargin=5;
	/**
	 * html页面自动刷新秒数
	 */
	private Integer htmlIntervalRefreshValue=0;

	public Integer getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(Integer leftMargin) {
		this.leftMargin = leftMargin;
	}

	public Integer getRightMargin() {
		return rightMargin;
	}

	public void setRightMargin(Integer rightMargin) {
		this.rightMargin = rightMargin;
	}

	public Integer getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(Integer topMargin) {
		this.topMargin = topMargin;
	}

	public Integer getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(Integer bottomMargin) {
		this.bottomMargin = bottomMargin;
	}

	public Integer getFixRows() {
		return fixRows;
	}

	public void setFixRows(Integer fixRows) {
		this.fixRows = fixRows;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Boolean getColumnEnabled() {
		return columnEnabled;
	}

	public void setColumnEnabled(Boolean columnEnabled) {
		this.columnEnabled = columnEnabled;
	}

	public Integer getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}

	public Integer getColumnMargin() {
		return columnMargin;
	}

	public void setColumnMargin(Integer columnMargin) {
		this.columnMargin = columnMargin;
	}

	public Integer getHtmlIntervalRefreshValue() {
		return htmlIntervalRefreshValue;
	}

	public void setHtmlIntervalRefreshValue(Integer htmlIntervalRefreshValue) {
		this.htmlIntervalRefreshValue = htmlIntervalRefreshValue;
	}

	public PaperType getPaperType() {
		return paperType;
	}
	public void setPaperType(PaperType paperType) {
		this.paperType = paperType;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public PagingMode getPagingMode() {
		return pagingMode;
	}
	public void setPagingMode(PagingMode pagingMode) {
		this.pagingMode = pagingMode;
	}

	public HtmlReportAlign getHtmlReportAlign() {
		return htmlReportAlign;
	}

	public void setHtmlReportAlign(HtmlReportAlign htmlReportAlign) {
		this.htmlReportAlign = htmlReportAlign;
	}

	public String getBgImage() {
		return bgImage;
	}

	public void setBgImage(String bgImage) {
		this.bgImage = bgImage;
	}
}