package com.changhong.sei.report.dto;

import java.io.Serializable;

/**
 * @desc：元素Dto
 * @author：zhaohz
 * @date：2021/3/1 11:22
 */
public class CellDto implements Serializable{
    /**
     * rowSpan
     */
    private Integer rowSpan;

    /**
     * colSpan
     */
    private Integer colSpan;

    /**
     * 单元格名称
     * td的class设置为class='_'+name
     */
    private String name;

    /**
     * 样式
     */
    private String style;

    /**
     * 链接地址
     */
    private String linkUrl;

    /**
     * 链接target
     */
    private String linkTarget;

    /**
     * 单元格内容
     */
    private String content;

    public Integer getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Integer getColSpan() {
        return colSpan;
    }

    public void setColSpan(Integer colSpan) {
        this.colSpan = colSpan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkTarget() {
        return linkTarget;
    }

    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
