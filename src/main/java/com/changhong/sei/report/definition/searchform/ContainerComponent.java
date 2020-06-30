package com.changhong.sei.report.definition.searchform;

import java.util.List;

/**
 * @desc：容器组件
 * @author：zhaohz
 * @date：2020/6/30 11:09
 */
public abstract class ContainerComponent implements Component {
	protected List<Component> children;
	public void setChildren(List<Component> children) {
		this.children = children;
	}
	public List<Component> getChildren() {
		return children;
	}
}
