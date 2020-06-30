package com.changhong.sei.report.vo;

import java.io.Serializable;

/**
 * @desc：前端分页vo实体
 * @author：zhaohz
 * @date：2020/5/8 16:35
 */
public class PageVo implements Serializable{
    //当前页
    private Integer _i;

    public Integer get_i() {
        return _i;
    }

    public void set_i(Integer _i) {
        this._i = _i;
    }
}
