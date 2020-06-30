package com.changhong.sei.report.builds.assertor;

import com.changhong.sei.report.utils.Utils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * @desc：大于断言
 * @author：zhaohz
 * @date：2020/6/30 14:32
 */
public class GreatThenAssertor extends AbstractAssertor {

	@Override
	public boolean eval(Object left, Object right) {
		if(left==null || right==null){
			return false;
		}
		if(StringUtils.isBlank(left.toString()) || StringUtils.isBlank(right.toString())){
			return false;
		}
		BigDecimal leftObj= Utils.toBigDecimal(left);
		right=buildObject(right);
		BigDecimal rightObj= Utils.toBigDecimal(right);
		return leftObj.compareTo(rightObj)==1;
	}
}
