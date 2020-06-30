package com.changhong.sei.report.utils;

import java.math.BigDecimal;

/**
 * @desc：运算工具
 * @author：zhaohz
 * @date：2020/6/30 16:05
 */
public class ArithUtils {
	
	public static void main(String[] args) {
		BigDecimal a=new BigDecimal(-3960);
		BigDecimal b=new BigDecimal(-3080);
		System.out.println(a.subtract(b));
	}
	
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 加法
	 */
	public static Object add(Object first,Object second) {
		BigDecimal a= Utils.toBigDecimal(first);
		BigDecimal b= Utils.toBigDecimal(second);
		return a.add(b);
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 减法
	 */
	public static Object sub(Object first,Object second) {
		BigDecimal a= Utils.toBigDecimal(first);
		BigDecimal b= Utils.toBigDecimal(second);
		return a.subtract(b);
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 乘法
	 */
	public static Object mul(Object first,Object second) {
		BigDecimal a= Utils.toBigDecimal(first);
		BigDecimal b= Utils.toBigDecimal(second);
		BigDecimal c=a.multiply(b);
		return c.doubleValue();
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return 除法
	 */
	public static Object div(Object first,Object second) {
		BigDecimal a= Utils.toBigDecimal(first);
		BigDecimal b= Utils.toBigDecimal(second);
		BigDecimal c=a.divide(b,8,BigDecimal.ROUND_HALF_UP);
		return c.doubleValue();
	}
	/**
	 * @param first 第一个参数
	 * @param second 第二个参数
	 * @return complementation 求余
	 */
	public static Object com(Object first,Object second) {
		BigDecimal a= Utils.toBigDecimal(first);
		BigDecimal b= Utils.toBigDecimal(second);
		return a.doubleValue() % b.doubleValue();
	}
}
