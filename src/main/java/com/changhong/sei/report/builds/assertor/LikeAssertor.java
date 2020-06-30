package com.changhong.sei.report.builds.assertor;

/**
 * @desc：like断言
 * @author：zhaohz
 * @date：2020/6/30 14:33
 */
public class LikeAssertor implements Assertor {

	@Override
	public boolean eval(Object left, Object right) {
		if(left==null || right== null){
			return false;
		}
		if(left.equals(right)){
			return true;
		}
		return left.toString().indexOf(right.toString())>-1;
	}

}
