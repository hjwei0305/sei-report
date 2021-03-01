package com.changhong.sei.report.enums;

import com.changhong.sei.annotation.Remark;

/**
 * @desc：边框样式
 * @author：zhaohz
 * @date：2020/6/30 9:43
 */
public enum BorderStyle {
	@Remark("实线")
	solid,
	@Remark("虚线")
	dashed,
	@Remark("双实线")
	doublesolid;
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
