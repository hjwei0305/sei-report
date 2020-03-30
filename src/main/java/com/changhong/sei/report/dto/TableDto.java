package com.changhong.sei.report.dto;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

public class TableDto implements Serializable{
    /**
     * 表格宽度
     */
    private Integer width;

    /**
     * 背景图片
     * 如果有值，bgStyle=";background:url("+bgImage+") no-repeat";
     * table写为"<table border='0' style='margin:auto;border-collapse:collapse;width:"+width+"pt"+bgStyle+"'>"
     */
    private String bgImage;

    /**
     * 分栏样式，如果不为空，则把表格class设置为这个值
     * bgStyle=";background:url("+bgImage+") no-repeat";
     * table写为"<table border='0' class='page-break' style='margin:auto;border-collapse:collapse;width:"+width+"pt"+bgStyle+"'>"
     */
    private String pageBreak;

    /**
     * 分栏表格间的间隔
     * "<td style='width:"+columnMargin+"pt'></td>"
     */
    private Integer columnMargin;

    /**
     * 分栏的子表格
     * "<td style='width:"+singleTableWidth+"pt;vertical-align:top'>"
     * 内容为tableList中的元素
     * "</td>"
     */
    private List<TableDto> tableList;

    /**
     * 行
     */
    private List<RowDto> rowList;

    public Integer getColumnMargin() {
        return columnMargin;
    }

    public void setColumnMargin(Integer columnMargin) {
        this.columnMargin = columnMargin;
    }

    public String getPageBreak() {
        return pageBreak;
    }

    public void setPageBreak(String pageBreak) {
        this.pageBreak = pageBreak;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public List<TableDto> getTableList() {
        return tableList;
    }

    public void setTableList(List<TableDto> tableList) {
        this.tableList = tableList;
    }

    public List<RowDto> getRowList() {
        return rowList;
    }

    public void setRowList(List<RowDto> rowList) {
        this.rowList = rowList;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("width", this.width);
        json.put("bgImage", this.bgImage);
        json.put("pageBreak", this.pageBreak);
        json.put("columnMargin", this.columnMargin);
        json.put("tableList", this.tableList);
        json.put("rowList", this.rowList);
        return json.toJSONString();
    }
}
