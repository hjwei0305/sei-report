package com.changhong.sei.report.expression.model.condition;

/**
 * @desc：表达式连接符
 * @author：zhaohz
 * @date：2020/6/30 15:25
 */
public enum Join {
	and,or;
	public static Join parse(String join){
		if(join.equals("and") || join.equals("&&")){
			return and;
		}
		if(join.equals("or") || join.equals("||")){
			return or;
		}
		throw new IllegalArgumentException("Unknow join : "+join);
	}
}
