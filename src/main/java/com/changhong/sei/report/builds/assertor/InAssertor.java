package com.changhong.sei.report.builds.assertor;

import java.util.List;

/**
 * @desc：in断言
 * @author：zhaohz
 * @date：2020/6/30 14:32
 */
public class InAssertor implements Assertor {

	@Override
	public boolean eval(Object left, Object right) {
		if(left == null || right == null){
			return false;
		}
		if(right instanceof List){
			List<?> list=(List<?>)right;
			for(Object obj:list){
				if(left.equals(obj)){
					return true;
				}
			}
			return false;
		}else if(right instanceof Object[]){
			Object[] objs=(Object[])right;
			for(Object obj:objs){
				if(left.equals(obj)){
					return true;
				}
			}
			return false;
		}else if(right instanceof String){
			String[] array=right.toString().split(",");
			for(String str:array){
				if(left.equals(str)){
					return true;
				}
			}
			return false;
		}
		return left.equals(right);
	}
}
