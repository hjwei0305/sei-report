package com.changhong.sei.report.definition.value;

import com.changhong.sei.report.expression.model.Condition;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

/**
 * @desc：分组明细
 * @author：zhaohz
 * @date：2020/6/30 16:54
 */
public class GroupItem {
	private String name;
	@JsonIgnore
	private Condition condition;
	/**
	 * 此属性给设计器使用，引擎不使用该属性
	 */
	private List<Condition> conditions;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public List<Condition> getConditions() {
		return conditions;
	}
	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}
}
