package com.changhong.sei.report.builds.assertor;


/**
 * @desc：断言接口
 * @author：zhaohz
 * @date：2020/6/30 14:30
 */
public interface Assertor {
	boolean eval(Object left, Object right);
}
