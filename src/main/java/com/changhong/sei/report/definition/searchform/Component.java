package com.changhong.sei.report.definition.searchform;

/**
 * @desc：查询组件接口
 * @author：zhaohz
 * @date：2020/6/30 11:05
 */
public interface Component {
	String toHtml(RenderContext context);
	String initJs(RenderContext context);
	String getType();
}
