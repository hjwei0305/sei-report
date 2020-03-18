package com.changhong.sei.report.model;

import java.io.Serializable;

public class Page implements Serializable{
    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页显示条数
     */
    private Integer rows;

    /**
     * 总条数
     */
    private Integer records;

    /**
     * 总页数
     */
    private Integer total;

    public Integer getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public Integer getTotal() {
        if(rows>0&&records>0){
            total = records/rows;
            if(records%rows>0){
                total++;
            }
        }
        return total>0?total:1;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Page(int page){
        this.page = page;
        this.rows = 0;
        this.total = 0;
        this.records = 0;
    }

    public Page(int page, int rows){
        this.page = page;
        this.rows = rows;
        this.total = 0;
        this.records = 0;
    }

    public Page(int page, int rows, int total, int records){
        this.page = page;
        this.rows = rows;
        this.total = total;
        this.records = records;
    }
}
