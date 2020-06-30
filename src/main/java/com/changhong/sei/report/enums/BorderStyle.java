package com.changhong.sei.report.enums;

/**
 * @desc：边框样式
 * @author：zhaohz
 * @date：2020/6/30 9:43
 */
public enum BorderStyle {
	solid,dashed,doublesolid;
	public static BorderStyle toBorderStyle(String name){
		if(name.equals("double")){
			return BorderStyle.doublesolid;
		}else{
			return BorderStyle.valueOf(name);
		}
	}
	@Override
	public String toString() {
		if(this.equals(BorderStyle.doublesolid)){
			return "double";
		}
		return super.toString();
	}
}
