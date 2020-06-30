package com.changhong.sei.report.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * @desc：报表预览请求vo实体
 * @author：zhaohz
 * @date：2020/5/8 16:30
 */
public class PreviewModel implements Serializable {
    //报表名
    private String _u;
    //物理分页
    private DataBasePageVo dPage = null;
    //前端分页
    private PageVo _i = null;
    //查询参数
    private Map<String, Object> params;

    public String get_u() {
        return _u;
    }

    public void set_u(String _u) {
        this._u = _u;
    }

    public DataBasePageVo getdPage() {
        return dPage;
    }

    public void setdPage(DataBasePageVo dPage) {
        this.dPage = dPage;
    }

    public PageVo get_i() {
        return _i;
    }

    public void set_i(PageVo _i) {
        this._i = _i;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
