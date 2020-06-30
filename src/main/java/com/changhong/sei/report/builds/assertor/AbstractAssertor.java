package com.changhong.sei.report.builds.assertor;

import java.util.List;

/**
 * @desc：断言抽象类
 * @author：zhaohz
 * @date：2020/6/30 14:30
 */
public abstract class AbstractAssertor implements Assertor {
	protected Object buildObject(Object obj){
		if(obj==null){
			return obj;
		}
		if(obj instanceof List){
			List<?> list=(List<?>)obj;
			if(list.size()==1){
				return list.get(0);
			}
		}
		return obj;
	}
}
