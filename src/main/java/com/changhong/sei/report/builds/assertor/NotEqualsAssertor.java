package com.changhong.sei.report.builds.assertor;

/**
 * @desc：不等于断言
 * @author：zhaohz
 * @date：2020/6/30 14:33
 */
public class NotEqualsAssertor extends AbstractAssertor {

	@Override
	public boolean eval(Object left, Object right) {
		if(left == null && right==null){
			return false;
		}
		if(left==null || right==null){
			return true;
		}
		right=buildObject(right);
		return !left.equals(right);
	}
}
