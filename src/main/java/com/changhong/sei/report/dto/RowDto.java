package com.changhong.sei.report.dto;

import java.io.Serializable;
import java.util.List;

public class RowDto implements Serializable{
    /**
     * 行高
     */
    private Integer height;

    /**
     * 元素
     */
    private List<CellDto> cellList;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<CellDto> getCellList() {
        return cellList;
    }

    public void setCellList(List<CellDto> cellList) {
        this.cellList = cellList;
    }
}
