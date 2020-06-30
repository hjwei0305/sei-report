package com.changhong.sei.report.vo;

import java.io.Serializable;

/**
 * @desc：物理分页vo实体
 * @author：zhaohz
 * @date：2020/5/8 16:35
 */
public class DataBasePageVo implements Serializable {
    //当前页
    private Integer page;
    //每页条数
    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
