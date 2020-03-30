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
     * table写为"<table class='page-break' border='0' style='margin:auto;border-collapse:collapse;width:"+width+"pt"+bgStyle+"'>"
     */
    private String pageBreak;

    /**
     * 分栏的子表格
     */
    private List<TableDto> tableList;

    /**
     * 行
     */
    private List<RowDto> rowList;

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
        json.put("tableList", this.tableList);
        json.put("rowList", this.rowList);
        return json.toJSONString();
    }
}
